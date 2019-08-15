package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.uicc.IccCardApplicationStatus.AppType;
import com.android.internal.telephony.uicc.IccCardStatus.CardState;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UiccSlot;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;

public class QtiUiccCardProvisioner extends Handler {
    private static final String ACTION_UICC_MANUAL_PROVISION_STATUS_CHANGED = "org.codeaurora.intent.action.ACTION_UICC_MANUAL_PROVISION_STATUS_CHANGED";
    private static final boolean DBG = true;
    private static final int EVENT_GET_ICCID_DONE = 4;
    private static final int EVENT_ICC_CHANGED = 1;
    private static final int EVENT_OEM_HOOK_SERVICE_READY = 3;
    private static final int EVENT_UNSOL_MANUAL_PROVISION_STATUS_CHANGED = 2;
    private static final String EXTRA_NEW_PROVISION_STATE = "newProvisionState";
    private static final int GENERIC_FAILURE = -1;
    private static final int INVALID_INPUT = -2;
    private static final String LOG_TAG = "QtiUiccCardProvisioner";
    private static final int REQUEST_IN_PROGRESS = -3;
    private static final int SUCCESS = 0;
    private static final boolean VDBG = false;
    private static final int mNumPhones = TelephonyManager.getDefault().getPhoneCount();
    private static AtomicBoolean mRequestInProgress = new AtomicBoolean(VDBG);
    private static UiccController mUiccController = null;
    private static QtiUiccCardProvisioner sInstance;
    private static Object sManualProvLock = new Object();
    private CardState[] mCardState;
    private Context mContext;
    private boolean[] mIsIccIdQueryPending = new boolean[mNumPhones];
    private RegistrantList mManualProvisionChangedRegistrants = new RegistrantList();
    private UiccProvisionStatus[] mOldProvisionStatus;
    private UiccProvisionStatus[] mProvisionStatus;
    private QtiRilInterface mQtiRilInterface;
    private String[] mSimFullIccId;
    private String[] mSimIccId;

    @VisibleForTesting
    public static class UiccProvisionStatus {
        public static final int CARD_NOT_PRESENT = -2;
        public static final int INVALID_STATE = -1;
        public static final int NOT_PROVISIONED = 0;
        public static final int PROVISIONED = 1;
        private int currentState = -1;
        private int userPreference = -1;

        UiccProvisionStatus() {
        }

        /* access modifiers changed from: 0000 */
        public boolean equals(UiccProvisionStatus provisionStatus) {
            if (provisionStatus.getUserPreference() == getUserPreference() && provisionStatus.getCurrentState() == getCurrentState()) {
                return QtiUiccCardProvisioner.DBG;
            }
            return QtiUiccCardProvisioner.VDBG;
        }

        /* access modifiers changed from: 0000 */
        public int getUserPreference() {
            return this.userPreference;
        }

        /* access modifiers changed from: 0000 */
        public void setUserPreference(int pref) {
            this.userPreference = pref;
        }

        /* access modifiers changed from: 0000 */
        public int getCurrentState() {
            return this.currentState;
        }

        /* access modifiers changed from: 0000 */
        public void setCurrentState(int state) {
            this.currentState = state;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("User pref ");
            sb.append(this.userPreference);
            sb.append(" Current pref ");
            sb.append(this.currentState);
            return sb.toString();
        }
    }

    public static QtiUiccCardProvisioner make(Context context) {
        if (sInstance == null) {
            sInstance = new QtiUiccCardProvisioner(context);
        } else {
            Log.wtf(LOG_TAG, "QtiUiccCardProvisioner.make() should be called once");
        }
        return sInstance;
    }

    @VisibleForTesting
    public void dispose() {
        logd(" disposing... ");
        mUiccController.unregisterForIccChanged(this);
        mUiccController = null;
        this.mQtiRilInterface.unRegisterForServiceReadyEvent(this);
        this.mQtiRilInterface.unRegisterForUnsol(this);
        this.mQtiRilInterface = null;
    }

    public static QtiUiccCardProvisioner getInstance() {
        if (sInstance == null) {
            Log.e(LOG_TAG, "QtiUiccCardProvisioner.getInstance called before make");
        }
        return sInstance;
    }

    private QtiUiccCardProvisioner(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(" Invoking constructor, no of phones = ");
        sb.append(mNumPhones);
        logd(sb.toString());
        this.mContext = context;
        this.mProvisionStatus = new UiccProvisionStatus[mNumPhones];
        this.mOldProvisionStatus = new UiccProvisionStatus[mNumPhones];
        this.mSimIccId = new String[mNumPhones];
        this.mSimFullIccId = new String[mNumPhones];
        this.mCardState = new CardState[mNumPhones];
        for (int index = 0; index < mNumPhones; index++) {
            this.mSimIccId[index] = null;
            this.mSimFullIccId[index] = null;
            this.mProvisionStatus[index] = new UiccProvisionStatus();
            this.mCardState[index] = CardState.CARDSTATE_ABSENT;
            this.mIsIccIdQueryPending[index] = VDBG;
            this.mOldProvisionStatus[index] = new UiccProvisionStatus();
        }
        mUiccController = UiccController.getInstance();
        mUiccController.registerForIccChanged(this, 1, null);
        this.mQtiRilInterface = QtiRilInterface.getInstance(context);
        this.mQtiRilInterface.registerForServiceReadyEvent(this, 3, null);
        this.mQtiRilInterface.registerForUnsol(this, 2, null);
    }

    public void registerForManualProvisionChanged(Handler handler, int what, Object obj) {
        Registrant r = new Registrant(handler, what, obj);
        synchronized (this.mManualProvisionChangedRegistrants) {
            this.mManualProvisionChangedRegistrants.add(r);
            r.notifyRegistrant();
        }
    }

    public void unregisterForManualProvisionChanged(Handler handler) {
        synchronized (this.mManualProvisionChangedRegistrants) {
            this.mManualProvisionChangedRegistrants.remove(handler);
        }
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar == null || ar.result == null) {
                    loge("Error: Invalid card index EVENT_ICC_CHANGED ");
                    return;
                } else {
                    updateIccAvailability(((Integer) ar.result).intValue());
                    return;
                }
            case 2:
                AsyncResult ar2 = (AsyncResult) msg.obj;
                if (ar2 == null || ar2.result == null) {
                    loge("Error: empty result, UNSOL_MANUAL_PROVISION_STATUS_CHANGED");
                    return;
                } else {
                    handleUnsolManualProvisionEvent((Message) ar2.result);
                    return;
                }
            case 3:
                AsyncResult ar3 = (AsyncResult) msg.obj;
                if (ar3 == null || ar3.result == null) {
                    loge("Error: empty result, EVENT_OEM_HOOK_SERVICE_READY");
                    return;
                } else if (((Boolean) ar3.result).booleanValue()) {
                    queryAllUiccProvisionInfo();
                    return;
                } else {
                    return;
                }
            case 4:
                AsyncResult ar4 = (AsyncResult) msg.obj;
                String iccId = null;
                String fullIccId = null;
                int phoneId = -1;
                if (ar4 != null) {
                    phoneId = ((Integer) ar4.userObj).intValue();
                    if (ar4.result != null) {
                        byte[] data = (byte[]) ar4.result;
                        fullIccId = IccUtils.bchToString(data, 0, data.length);
                        iccId = IccUtils.stripTrailingFs(fullIccId);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Exception in GET iccId[");
                        sb.append(phoneId);
                        sb.append("] ");
                        sb.append(ar4.exception);
                        logd(sb.toString());
                    }
                }
                if (phoneId >= 0 && phoneId < mNumPhones) {
                    this.mIsIccIdQueryPending[phoneId] = VDBG;
                    if (!TextUtils.isEmpty(iccId)) {
                        this.mSimFullIccId[phoneId] = fullIccId;
                        QtiSubscriptionInfoUpdater.getInstance().addSubInfoRecord(phoneId, iccId);
                        this.mSimIccId[phoneId] = iccId;
                        if (this.mSimIccId[phoneId] != null && isAllCardProvisionInfoReceived()) {
                            int[] subIds = QtiSubscriptionController.getInstance().getSubId(phoneId);
                            if (!(subIds == null || subIds.length == 0 || !QtiSubscriptionController.getInstance().isActiveSubId(subIds[0]))) {
                                QtiSubscriptionController.getInstance().updateUserPreferences();
                            }
                        }
                        if (this.mOldProvisionStatus != null && !this.mOldProvisionStatus[phoneId].equals(this.mProvisionStatus[phoneId])) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(" broadcasting ProvisionInfo, phoneId = ");
                            sb2.append(phoneId);
                            logd(sb2.toString());
                            broadcastManualProvisionStatusChanged(phoneId, getCurrentProvisioningStatus(phoneId));
                            this.mOldProvisionStatus[phoneId] = this.mProvisionStatus[phoneId];
                            return;
                        }
                        return;
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(" EVENT_GET_ICCID_DONE, ICCID is empty, phoneId = ");
                    sb3.append(phoneId);
                    logi(sb3.toString());
                    return;
                }
                return;
            default:
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Error: hit default case ");
                sb4.append(msg.what);
                loge(sb4.toString());
                return;
        }
    }

    private void handleUnsolManualProvisionEvent(Message msg) {
        if (msg == null || msg.obj == null) {
            loge("Null data received in handleUnsolManualProvisionEvent");
            return;
        }
        ByteBuffer payload = ByteBuffer.wrap((byte[]) msg.obj);
        payload.order(ByteOrder.nativeOrder());
        int rspId = payload.getInt();
        int slotId = msg.arg1;
        if (isValidSlotId(slotId) && rspId == 525316) {
            StringBuilder sb = new StringBuilder();
            sb.append(" Unsol: rspId ");
            sb.append(rspId);
            sb.append(" slotId ");
            sb.append(msg.arg1);
            logi(sb.toString());
            queryUiccProvisionInfo(slotId, VDBG);
            int dataSlotId = SubscriptionManager.getSlotIndex(SubscriptionManager.getDefaultDataSubscriptionId());
            if (slotId == dataSlotId && getCurrentProvisioningStatus(dataSlotId) == 1) {
                logd("Set dds after SSR");
                QtiRadioCapabilityController.getInstance().setDdsIfRequired(VDBG);
            }
        }
    }

    private void queryAllUiccProvisionInfo() {
        for (int index = 0; index < mNumPhones; index++) {
            StringBuilder sb = new StringBuilder();
            sb.append(" query  provision info, card state[");
            sb.append(index);
            sb.append("] = ");
            sb.append(this.mCardState[index]);
            logd(sb.toString());
            if (this.mCardState[index] == CardState.CARDSTATE_PRESENT && !this.mIsIccIdQueryPending[index]) {
                queryUiccProvisionInfo(index, DBG);
            }
        }
    }

    public String getUiccIccId(int slotId) {
        return this.mSimIccId[slotId];
    }

    public String getUiccFullIccId(int slotId) {
        return this.mSimFullIccId[slotId];
    }

    private void queryUiccProvisionInfo(int phoneId, boolean useSimIORequest) {
        if (!this.mQtiRilInterface.isServiceReady() || !isValidSlotId(phoneId)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Oem hook service is not ready yet ");
            sb.append(phoneId);
            logi(sb.toString());
            return;
        }
        UiccProvisionStatus oldStatus = this.mProvisionStatus[phoneId];
        UiccProvisionStatus subStatus = this.mQtiRilInterface.getUiccProvisionPreference(phoneId);
        if (!(subStatus.getCurrentState() == -1 || subStatus.getUserPreference() == -1)) {
            synchronized (sManualProvLock) {
                this.mProvisionStatus[phoneId] = subStatus;
            }
        }
        if (this.mSimIccId[phoneId] == null) {
            UiccCard uiccCard = mUiccController.getUiccCard(phoneId);
            if (uiccCard != null) {
                String fullIccId = uiccCard.getIccId();
                String iccId = IccUtils.stripTrailingFs(fullIccId);
                if (!TextUtils.isEmpty(iccId)) {
                    this.mSimFullIccId[phoneId] = fullIccId;
                    QtiSubscriptionInfoUpdater.getInstance().addSubInfoRecord(phoneId, iccId);
                    this.mSimIccId[phoneId] = iccId;
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" queryUiccProvisionInfo: useSimIORequest=  ");
                    sb2.append(useSimIORequest);
                    logd(sb2.toString());
                    if (useSimIORequest) {
                        loadIccId(phoneId);
                    } else {
                        String fullIccId2 = this.mQtiRilInterface.getUiccIccId(phoneId);
                        String iccId2 = IccUtils.stripTrailingFs(fullIccId2);
                        if (!TextUtils.isEmpty(iccId2)) {
                            this.mSimFullIccId[phoneId] = fullIccId2;
                            QtiSubscriptionInfoUpdater.getInstance().addSubInfoRecord(phoneId, iccId2);
                            this.mSimIccId[phoneId] = iccId2;
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(" queryUiccProvisionInfo, ICCID[");
                            sb3.append(phoneId);
                            sb3.append("] is null");
                            logi(sb3.toString());
                        }
                    }
                }
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(" queryUiccProvisionInfo, uiccCard[");
                sb4.append(phoneId);
                sb4.append("] object is null");
                logi(sb4.toString());
            }
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append(" queryUiccProvisionInfo, provisionStatus[");
        sb5.append(phoneId);
        sb5.append("] = ");
        sb5.append(this.mProvisionStatus[phoneId]);
        logd(sb5.toString());
        if (!oldStatus.equals(this.mProvisionStatus[phoneId])) {
            if (this.mSimIccId[phoneId] != null && isAllCardProvisionInfoReceived()) {
                int[] subIds = QtiSubscriptionController.getInstance().getSubId(phoneId);
                if (!(subIds == null || subIds.length == 0 || !QtiSubscriptionController.getInstance().isActiveSubId(subIds[0]))) {
                    QtiSubscriptionController.getInstance().updateUserPreferences();
                }
            }
            if (!useSimIORequest || this.mSimIccId[phoneId] != null) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(" broadcasting ProvisionInfo, phoneId = ");
                sb6.append(phoneId);
                logd(sb6.toString());
                broadcastManualProvisionStatusChanged(phoneId, getCurrentProvisioningStatus(phoneId));
                this.mOldProvisionStatus[phoneId] = this.mProvisionStatus[phoneId];
            }
        }
    }

    private void loadIccId(int phoneId) {
        UiccCard uiccCard = mUiccController.getUiccCard(phoneId);
        if (uiccCard != null) {
            UiccCardApplication validApp = null;
            int numApps = uiccCard.getNumApplications();
            int i = 0;
            while (true) {
                if (i >= numApps) {
                    break;
                }
                UiccCardApplication app = uiccCard.getApplicationIndex(i);
                if (app != null && app.getType() != AppType.APPTYPE_UNKNOWN) {
                    validApp = app;
                    break;
                }
                i++;
            }
            if (validApp != null) {
                IccFileHandler fileHandler = validApp.getIccFileHandler();
                if (fileHandler != null) {
                    this.mIsIccIdQueryPending[phoneId] = DBG;
                    fileHandler.loadEFTransparent(12258, obtainMessage(4, Integer.valueOf(phoneId)));
                }
            }
        }
    }

    private void updateIccAvailability(int phoneId) {
        if (!isValidSlotId(phoneId)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid phone Index!!! ");
            sb.append(phoneId);
            loge(sb.toString());
            return;
        }
        CardState cardState = CardState.CARDSTATE_ABSENT;
        UiccSlot newSlot = mUiccController.getUiccSlotForPhone(phoneId);
        if (newSlot != null) {
            CardState newState = newSlot.getCardState();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("updateIccAvailability, card state[");
            sb2.append(phoneId);
            sb2.append("] = ");
            sb2.append(newState);
            logd(sb2.toString());
            this.mCardState[phoneId] = newState;
            int currentState = getCurrentProvisioningStatus(phoneId);
            if (this.mCardState[phoneId] == CardState.CARDSTATE_PRESENT && ((this.mSimIccId[phoneId] == null || currentState == -1 || currentState == -2) && !this.mIsIccIdQueryPending[phoneId])) {
                queryUiccProvisionInfo(phoneId, DBG);
            } else if ((this.mCardState[phoneId] == CardState.CARDSTATE_ABSENT && !newSlot.isStateUnknown()) || this.mCardState[phoneId] == CardState.CARDSTATE_ERROR) {
                synchronized (sManualProvLock) {
                    this.mProvisionStatus[phoneId].setUserPreference(-2);
                    this.mProvisionStatus[phoneId].setCurrentState(-2);
                    this.mOldProvisionStatus[phoneId].setUserPreference(-2);
                    this.mOldProvisionStatus[phoneId].setCurrentState(-2);
                    this.mSimIccId[phoneId] = null;
                    this.mSimFullIccId[phoneId] = null;
                    this.mManualProvisionChangedRegistrants.notifyRegistrants(new AsyncResult(null, Integer.valueOf(phoneId), null));
                }
                if (isAllCardProvisionInfoReceived()) {
                    QtiSubscriptionController.getInstance().updateUserPreferences();
                }
            }
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("updateIccAvailability, uicc card null, ignore ");
        sb3.append(phoneId);
        logd(sb3.toString());
    }

    private void broadcastManualProvisionStatusChanged(int phoneId, int newProvisionState) {
        Intent intent = new Intent(ACTION_UICC_MANUAL_PROVISION_STATUS_CHANGED);
        intent.putExtra("phone", phoneId);
        intent.putExtra(EXTRA_NEW_PROVISION_STATE, newProvisionState);
        this.mContext.sendBroadcast(intent);
        this.mManualProvisionChangedRegistrants.notifyRegistrants(new AsyncResult(null, Integer.valueOf(phoneId), null));
    }

    private int getCurrentProvisioningStatus(int slotId) {
        int currentState;
        synchronized (sManualProvLock) {
            currentState = this.mProvisionStatus[slotId].getCurrentState();
        }
        return currentState;
    }

    public int getCurrentUiccCardProvisioningStatus(int slotId) {
        if (mNumPhones == 1 && isValidSlotId(slotId)) {
            return 1;
        }
        if (canProcessRequest(slotId)) {
            return getCurrentProvisioningStatus(slotId);
        }
        return -1;
    }

    public int getUiccCardProvisioningUserPreference(int slotId) {
        int userPref;
        if (mNumPhones == 1 && isValidSlotId(slotId)) {
            return 1;
        }
        if (!canProcessRequest(slotId)) {
            return -1;
        }
        synchronized (sManualProvLock) {
            userPref = this.mProvisionStatus[slotId].getUserPreference();
        }
        return userPref;
    }

    public int activateUiccCard(int slotId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" activateUiccCard: phoneId = ");
        sb.append(slotId);
        logd(sb.toString());
        if (!this.mQtiRilInterface.isServiceReady()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Oem hook service is not ready yet ");
            sb2.append(slotId);
            logi(sb2.toString());
            return -1;
        }
        enforceModifyPhoneState("activateUiccCard");
        int activateStatus = 0;
        if (!canProcessRequest(slotId)) {
            activateStatus = -2;
        } else if (getCurrentProvisioningStatus(slotId) == 1) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" Uicc card in slot[");
            sb3.append(slotId);
            sb3.append("] already activated ");
            logd(sb3.toString());
        } else if (isFlexMapInProgress() || !mRequestInProgress.compareAndSet(VDBG, DBG)) {
            activateStatus = -3;
        } else {
            boolean retVal = this.mQtiRilInterface.setUiccProvisionPreference(1, slotId);
            if (!retVal) {
                activateStatus = -1;
            } else {
                synchronized (sManualProvLock) {
                    this.mProvisionStatus[slotId].setCurrentState(1);
                }
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(" activation result[");
            sb4.append(slotId);
            sb4.append("] = ");
            sb4.append(retVal);
            logi(sb4.toString());
            mRequestInProgress.set(VDBG);
        }
        return activateStatus;
    }

    public int deactivateUiccCard(int slotId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" deactivateUiccCard: phoneId = ");
        sb.append(slotId);
        logd(sb.toString());
        if (!this.mQtiRilInterface.isServiceReady()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Oem hook service is not ready yet ");
            sb2.append(slotId);
            logi(sb2.toString());
            return -1;
        }
        enforceModifyPhoneState("deactivateUiccCard");
        int deactivateState = 0;
        if (!canProcessRequest(slotId)) {
            deactivateState = -2;
        } else if (getCurrentProvisioningStatus(slotId) == 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(" Uicc card in slot[");
            sb3.append(slotId);
            sb3.append("] already in deactive state ");
            logd(sb3.toString());
        } else if (isFlexMapInProgress() || !mRequestInProgress.compareAndSet(VDBG, DBG)) {
            deactivateState = -3;
        } else {
            boolean retVal = this.mQtiRilInterface.setUiccProvisionPreference(0, slotId);
            if (!retVal) {
                deactivateState = -1;
            } else {
                synchronized (sManualProvLock) {
                    this.mProvisionStatus[slotId].setCurrentState(0);
                }
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(" deactivation result[");
            sb4.append(slotId);
            sb4.append("] = ");
            sb4.append(retVal);
            logi(sb4.toString());
            mRequestInProgress.set(VDBG);
        }
        return deactivateState;
    }

    private void enforceModifyPhoneState(String message) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE", message);
    }

    private boolean canProcessRequest(int slotId) {
        if (mNumPhones > 1 && isValidSlotId(slotId)) {
            return DBG;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Request can't be processed, slotId ");
        sb.append(slotId);
        sb.append(" numPhones ");
        sb.append(mNumPhones);
        loge(sb.toString());
        return VDBG;
    }

    private boolean isValidSlotId(int slotId) {
        if (slotId < 0 || slotId >= mNumPhones) {
            return VDBG;
        }
        return DBG;
    }

    public boolean isFlexMapInProgress() {
        QtiRadioCapabilityController rcController = QtiRadioCapabilityController.getInstance();
        if (rcController == null) {
            return VDBG;
        }
        boolean retVal = rcController.isSetNWModeInProgress();
        StringBuilder sb = new StringBuilder();
        sb.append("isFlexMapInProgress: = ");
        sb.append(retVal);
        logd(sb.toString());
        return retVal;
    }

    public boolean isAnyProvisionRequestInProgress() {
        return mRequestInProgress.get();
    }

    public boolean isAllCardProvisionInfoReceived() {
        for (int index = 0; index < mNumPhones; index++) {
            int provPref = getCurrentProvisioningStatus(index);
            if (provPref == -1 || (this.mSimIccId[index] != null && provPref == -2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("isAllCardProvisionInfoReceived, prov pref[");
                sb.append(index);
                sb.append("] = ");
                sb.append(provPref);
                logd(sb.toString());
                return VDBG;
            }
        }
        return DBG;
    }

    private void logd(String string) {
        Rlog.d(LOG_TAG, string);
    }

    private void logi(String string) {
        Rlog.i(LOG_TAG, string);
    }

    private void loge(String string) {
        Rlog.e(LOG_TAG, string);
    }
}
