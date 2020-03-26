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
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
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
    private static final int EVENT_RADIO_AVAILABLE = 5;
    private static final int EVENT_UNSOL_MANUAL_PROVISION_STATUS_CHANGED = 2;
    private static final String EXTRA_NEW_PROVISION_STATE = "newProvisionState";
    private static final int GENERIC_FAILURE = -1;
    private static final int INVALID_INPUT = -2;
    private static final String LOG_TAG = "QtiUiccCardProvisioner";
    private static final int REQUEST_IN_PROGRESS = -3;
    private static final int SUCCESS = 0;
    private static final boolean VDBG = false;
    private static int mNumPhones;
    private static AtomicBoolean mRequestInProgress = new AtomicBoolean(VDBG);
    private static UiccController mUiccController = null;
    private static QtiUiccCardProvisioner sInstance;
    private static Object sManualProvLock = new Object();
    private IccCardStatus.CardState[] mCardState;
    private final CommandsInterface[] mCis;
    private Context mContext;
    private boolean[] mIsIccIdQueryPending;
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

        /* access modifiers changed from: package-private */
        public boolean equals(UiccProvisionStatus provisionStatus) {
            if (provisionStatus.getUserPreference() == getUserPreference() && provisionStatus.getCurrentState() == getCurrentState()) {
                return QtiUiccCardProvisioner.DBG;
            }
            return QtiUiccCardProvisioner.VDBG;
        }

        /* access modifiers changed from: package-private */
        public int getUserPreference() {
            return this.userPreference;
        }

        /* access modifiers changed from: package-private */
        public void setUserPreference(int pref) {
            this.userPreference = pref;
        }

        /* access modifiers changed from: package-private */
        public int getCurrentState() {
            return this.currentState;
        }

        /* access modifiers changed from: package-private */
        public void setCurrentState(int state) {
            this.currentState = state;
        }

        public String toString() {
            return "User pref " + this.userPreference + " Current pref " + this.currentState;
        }
    }

    public static QtiUiccCardProvisioner make(Context context, CommandsInterface[] cis) {
        if (sInstance == null) {
            sInstance = new QtiUiccCardProvisioner(context, cis);
        } else {
            Log.wtf(LOG_TAG, "QtiUiccCardProvisioner.make() should be called once");
        }
        return sInstance;
    }

    public static QtiUiccCardProvisioner make(Context context) {
        if (sInstance == null) {
            sInstance = new QtiUiccCardProvisioner(context, (CommandsInterface[]) null);
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

    private QtiUiccCardProvisioner(Context context, CommandsInterface[] cis) {
        this.mContext = context;
        mNumPhones = ((TelephonyManager) this.mContext.getSystemService("phone")).getPhoneCount();
        logd(" Invoking constructor, no of phones = " + mNumPhones);
        this.mCis = cis;
        int i = mNumPhones;
        this.mProvisionStatus = new UiccProvisionStatus[i];
        this.mOldProvisionStatus = new UiccProvisionStatus[i];
        this.mSimIccId = new String[i];
        this.mSimFullIccId = new String[i];
        this.mCardState = new IccCardStatus.CardState[i];
        this.mIsIccIdQueryPending = new boolean[i];
        for (int index = 0; index < mNumPhones; index++) {
            this.mSimIccId[index] = null;
            this.mSimFullIccId[index] = null;
            this.mProvisionStatus[index] = new UiccProvisionStatus();
            this.mCardState[index] = IccCardStatus.CardState.CARDSTATE_ABSENT;
            this.mIsIccIdQueryPending[index] = VDBG;
            this.mOldProvisionStatus[index] = new UiccProvisionStatus();
            this.mCis[index].registerForAvailable(this, 5, Integer.valueOf(index));
        }
        mUiccController = UiccController.getInstance();
        mUiccController.registerForIccChanged(this, 1, (Object) null);
        this.mQtiRilInterface = QtiRilInterface.getInstance(context);
        this.mQtiRilInterface.registerForServiceReadyEvent(this, 3, (Object) null);
        this.mQtiRilInterface.registerForUnsol(this, 2, (Object) null);
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
        int phoneId = -1;
        int i = msg.what;
        if (i == 1) {
            AsyncResult ar = (AsyncResult) msg.obj;
            if (ar == null || ar.result == null) {
                loge("Error: Invalid card index EVENT_ICC_CHANGED ");
            } else {
                updateIccAvailability(((Integer) ar.result).intValue());
            }
        } else if (i == 2) {
            org.codeaurora.telephony.utils.AsyncResult asyncresult = (org.codeaurora.telephony.utils.AsyncResult) msg.obj;
            if (asyncresult == null || asyncresult.result == null) {
                loge("Error: empty result, UNSOL_MANUAL_PROVISION_STATUS_CHANGED");
            } else {
                handleUnsolManualProvisionEvent((Message) asyncresult.result);
            }
        } else if (i == 3) {
            AsyncResult ar2 = (AsyncResult) msg.obj;
            if (ar2 == null || ar2.result == null) {
                loge("Error: empty result, EVENT_OEM_HOOK_SERVICE_READY");
            } else if (((Boolean) ar2.result).booleanValue()) {
                queryAllUiccProvisionInfo();
            }
        } else if (i == 4) {
            AsyncResult ar3 = (AsyncResult) msg.obj;
            String iccId = null;
            String fullIccId = null;
            if (ar3 != null) {
                phoneId = ((Integer) ar3.userObj).intValue();
                if (ar3.result != null) {
                    byte[] data = (byte[]) ar3.result;
                    fullIccId = IccUtils.bchToString(data, 0, data.length);
                    iccId = IccUtils.stripTrailingFs(fullIccId);
                } else {
                    logd("Exception in GET iccId[" + phoneId + "] " + ar3.exception);
                }
            }
            if (phoneId >= 0 && phoneId < mNumPhones) {
                this.mIsIccIdQueryPending[phoneId] = VDBG;
                if (!TextUtils.isEmpty(iccId)) {
                    this.mSimFullIccId[phoneId] = fullIccId;
                    QtiSubscriptionInfoUpdater.getInstance().addSubInfoRecord(phoneId, iccId);
                    String[] strArr = this.mSimIccId;
                    strArr[phoneId] = iccId;
                    if (strArr[phoneId] != null && isAllCardProvisionInfoReceived()) {
                        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
                        int[] subIds = QtiSubscriptionController.getInstance().getSubId(phoneId);
                        if (!(subIds == null || subIds.length == 0 || !subscriptionManager.isActiveSubscriptionId(subIds[0]))) {
                            QtiSubscriptionInfoUpdater.getInstance().updateUserPreferences();
                        }
                    }
                    UiccProvisionStatus[] uiccProvisionStatusArr = this.mOldProvisionStatus;
                    if (uiccProvisionStatusArr != null && !uiccProvisionStatusArr[phoneId].equals(this.mProvisionStatus[phoneId])) {
                        logd(" broadcasting ProvisionInfo, phoneId = " + phoneId);
                        broadcastManualProvisionStatusChanged(phoneId, getCurrentProvisioningStatus(phoneId));
                        this.mOldProvisionStatus[phoneId] = this.mProvisionStatus[phoneId];
                        return;
                    }
                    return;
                }
                logi(" EVENT_GET_ICCID_DONE, ICCID is empty, phoneId = " + phoneId);
            }
        } else if (i != 5) {
            loge("Error: hit default case " + msg.what);
        } else {
            AsyncResult ar4 = (AsyncResult) msg.obj;
            if (ar4 != null) {
                int phoneId2 = ((Integer) ar4.userObj).intValue();
                logd("RADIO_AVAILABLE for phone: " + phoneId2);
                if (isAllCardProvisionInfoReceived()) {
                    int[] subIds2 = QtiSubscriptionController.getInstance().getSubId(phoneId2);
                    if (!(subIds2 == null || subIds2.length == 0 || !QtiSubscriptionController.getInstance().isActiveSubId(subIds2[0])) || (this.mSimIccId[phoneId2] == null && getCurrentProvisioningStatus(phoneId2) == -2)) {
                        QtiSubscriptionController.getInstance().updateUserPreferences();
                    }
                    this.mCis[phoneId2].unregisterForAvailable(this);
                }
            }
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
            logi(" Unsol: rspId " + rspId + " slotId " + msg.arg1);
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
            logd(" query  provision info, card state[" + index + "] = " + this.mCardState[index]);
            if (this.mCardState[index] == IccCardStatus.CardState.CARDSTATE_PRESENT && !this.mIsIccIdQueryPending[index]) {
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
            logi("Oem hook service is not ready yet " + phoneId);
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
                    logd(" queryUiccProvisionInfo: useSimIORequest=  " + useSimIORequest);
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
                            logi(" queryUiccProvisionInfo, ICCID[" + phoneId + "] is null");
                        }
                    }
                }
            } else {
                logi(" queryUiccProvisionInfo, uiccCard[" + phoneId + "] object is null");
            }
        }
        logd(" queryUiccProvisionInfo, provisionStatus[" + phoneId + "] = " + this.mProvisionStatus[phoneId]);
        if (!oldStatus.equals(this.mProvisionStatus[phoneId])) {
            if (this.mSimIccId[phoneId] != null && isAllCardProvisionInfoReceived()) {
                int[] subIds = QtiSubscriptionController.getInstance().getSubId(phoneId);
                SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
                if (!(subIds == null || subIds.length == 0 || !subscriptionManager.isActiveSubscriptionId(subIds[0]))) {
                    QtiSubscriptionInfoUpdater.getInstance().updateUserPreferences();
                }
            }
            if (!useSimIORequest || this.mSimIccId[phoneId] != null) {
                logd(" broadcasting ProvisionInfo, phoneId = " + phoneId);
                broadcastManualProvisionStatusChanged(phoneId, getCurrentProvisioningStatus(phoneId));
                this.mOldProvisionStatus[phoneId] = this.mProvisionStatus[phoneId];
            }
        }
    }

    private void loadIccId(int phoneId) {
        IccFileHandler fileHandler;
        UiccCard uiccCard = mUiccController.getUiccCard(phoneId);
        if (uiccCard != null) {
            UiccCardApplication validApp = null;
            int numApps = uiccCard.getNumApplications();
            int i = 0;
            while (true) {
                if (i < numApps) {
                    UiccCardApplication app = uiccCard.getApplicationIndex(i);
                    if (app != null && app.getType() != IccCardApplicationStatus.AppType.APPTYPE_UNKNOWN) {
                        validApp = app;
                        break;
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (validApp != null && (fileHandler = validApp.getIccFileHandler()) != null) {
                this.mIsIccIdQueryPending[phoneId] = DBG;
                fileHandler.loadEFTransparent(12258, obtainMessage(4, Integer.valueOf(phoneId)));
            }
        }
    }

    private void updateIccAvailability(int phoneId) {
        if (!isValidSlotId(phoneId)) {
            loge("Invalid phone Index!!! " + phoneId);
            return;
        }
        IccCardStatus.CardState cardState = IccCardStatus.CardState.CARDSTATE_ABSENT;
        UiccSlot newSlot = mUiccController.getUiccSlotForPhone(phoneId);
        if (newSlot != null) {
            IccCardStatus.CardState newState = newSlot.getCardState();
            logd("updateIccAvailability, card state[" + phoneId + "] = " + newState);
            this.mCardState[phoneId] = newState;
            int currentState = getCurrentProvisioningStatus(phoneId);
            if (this.mCardState[phoneId] == IccCardStatus.CardState.CARDSTATE_PRESENT && ((this.mSimIccId[phoneId] == null || currentState == -1 || currentState == -2) && !this.mIsIccIdQueryPending[phoneId])) {
                queryUiccProvisionInfo(phoneId, DBG);
            } else if ((this.mCardState[phoneId] == IccCardStatus.CardState.CARDSTATE_ABSENT && !newSlot.isStateUnknown()) || this.mCardState[phoneId] == IccCardStatus.CardState.CARDSTATE_ERROR) {
                synchronized (sManualProvLock) {
                    this.mProvisionStatus[phoneId].setUserPreference(-2);
                    this.mProvisionStatus[phoneId].setCurrentState(-2);
                    this.mOldProvisionStatus[phoneId].setUserPreference(-2);
                    this.mOldProvisionStatus[phoneId].setCurrentState(-2);
                    this.mSimIccId[phoneId] = null;
                    this.mSimFullIccId[phoneId] = null;
                    this.mManualProvisionChangedRegistrants.notifyRegistrants(new AsyncResult((Object) null, Integer.valueOf(phoneId), (Throwable) null));
                }
                if (isAllCardProvisionInfoReceived()) {
                    QtiSubscriptionInfoUpdater.getInstance().updateUserPreferences();
                }
            }
        } else {
            logd("updateIccAvailability, uicc card null, ignore " + phoneId);
        }
    }

    private void broadcastManualProvisionStatusChanged(int phoneId, int newProvisionState) {
        Intent intent = new Intent(ACTION_UICC_MANUAL_PROVISION_STATUS_CHANGED);
        intent.putExtra("phone", phoneId);
        intent.putExtra(EXTRA_NEW_PROVISION_STATE, newProvisionState);
        this.mContext.sendBroadcast(intent);
        this.mManualProvisionChangedRegistrants.notifyRegistrants(new AsyncResult((Object) null, Integer.valueOf(phoneId), (Throwable) null));
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
        logd(" activateUiccCard: phoneId = " + slotId);
        if (!this.mQtiRilInterface.isServiceReady()) {
            logi("Oem hook service is not ready yet " + slotId);
            return -1;
        }
        enforceModifyPhoneState("activateUiccCard");
        int activateStatus = 0;
        if (!canProcessRequest(slotId)) {
            return -2;
        }
        if (getCurrentProvisioningStatus(slotId) == 1) {
            logd(" Uicc card in slot[" + slotId + "] already activated ");
            return 0;
        } else if (isFlexMapInProgress() || !mRequestInProgress.compareAndSet(VDBG, DBG)) {
            return -3;
        } else {
            boolean retVal = this.mQtiRilInterface.setUiccProvisionPreference(1, slotId);
            if (!retVal) {
                activateStatus = -1;
            } else {
                synchronized (sManualProvLock) {
                    this.mProvisionStatus[slotId].setCurrentState(1);
                }
            }
            logi(" activation result[" + slotId + "] = " + retVal);
            mRequestInProgress.set(VDBG);
            return activateStatus;
        }
    }

    public int deactivateUiccCard(int slotId) {
        logd(" deactivateUiccCard: phoneId = " + slotId);
        if (!this.mQtiRilInterface.isServiceReady()) {
            logi("Oem hook service is not ready yet " + slotId);
            return -1;
        }
        enforceModifyPhoneState("deactivateUiccCard");
        int deactivateState = 0;
        if (!canProcessRequest(slotId)) {
            return -2;
        }
        if (getCurrentProvisioningStatus(slotId) == 0) {
            logd(" Uicc card in slot[" + slotId + "] already in deactive state ");
            return 0;
        } else if (isFlexMapInProgress() || !mRequestInProgress.compareAndSet(VDBG, DBG)) {
            return -3;
        } else {
            boolean retVal = this.mQtiRilInterface.setUiccProvisionPreference(0, slotId);
            if (!retVal) {
                deactivateState = -1;
            } else {
                synchronized (sManualProvLock) {
                    this.mProvisionStatus[slotId].setCurrentState(0);
                }
            }
            logi(" deactivation result[" + slotId + "] = " + retVal);
            mRequestInProgress.set(VDBG);
            return deactivateState;
        }
    }

    private void enforceModifyPhoneState(String message) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE", message);
    }

    private boolean canProcessRequest(int slotId) {
        if (mNumPhones > 1 && isValidSlotId(slotId)) {
            return DBG;
        }
        loge("Request can't be processed, slotId " + slotId + " numPhones " + mNumPhones);
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
        logd("isFlexMapInProgress: = " + retVal);
        return retVal;
    }

    public boolean isAnyProvisionRequestInProgress() {
        return mRequestInProgress.get();
    }

    public boolean isAllCardProvisionInfoReceived() {
        for (int index = 0; index < mNumPhones; index++) {
            int provPref = getCurrentProvisioningStatus(index);
            if (provPref == -1 || (this.mSimIccId[index] != null && provPref == -2)) {
                logd("isAllCardProvisionInfoReceived, prov pref[" + index + "] = " + provPref);
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
