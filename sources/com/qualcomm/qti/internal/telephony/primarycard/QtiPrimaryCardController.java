package com.qualcomm.qti.internal.telephony.primarycard;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings.Global;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SubscriptionController;
import com.qualcomm.qti.internal.telephony.QtiPhoneUtils;
import com.qualcomm.qti.internal.telephony.QtiRilInterface;
import com.qualcomm.qti.internal.telephony.QtiSubscriptionController;
import com.qualcomm.qti.internal.telephony.QtiUiccCardProvisioner;

public class QtiPrimaryCardController extends Handler {
    private static final String ACTION_PRIMARY_CARD_CHANGED_IN_SERVICE = "org.codeaurora.intent.action.PRIMARY_CARD_CHANGED_IN_SERVICE";
    private static final String ACTION_RADIO_CAPABILITY_UPDATED = "org.codeaurora.intent.action.ACTION_RADIO_CAPABILITY_UPDATED";
    private static final String ACTION_SET_PRIMARY_CARD_DONE = "org.codeaurora.intent.action.ACTION_SET_PRIMARY_CARD_DONE";
    private static final boolean DBG = true;
    private static final int EVENT_ALL_CARDS_INFO_AVAILABLE = 1;
    private static final int EVENT_GET_NWMODE_DONE = 4;
    private static final int EVENT_PRIMARY_CARD_SET_DONE = 5;
    private static final int EVENT_SERVICE_STATE_CHANGED = 7;
    private static final int EVENT_SET_NWMODE_DONE = 3;
    private static final int EVENT_SET_PRIMARY_SUB = 6;
    private static final int EVENT_SET_RADIO_CAPABILITY_DONE = 2;
    private static final int FWK_PRIMARY_CARD_REQUEST = 1000;
    private static final String LOG_TAG = "QtiPcController";
    private static final String PRIMARYCARD_SUBSCRIPTION_KEY = "primarycard_sub";
    private static final String PRIMARY_CARD_RESULT = "result";
    private static final String SETTING_USER_PREF_DATA_SUB = "user_preferred_data_sub";
    private static final int USER_PRIMARY_CARD_REQUEST = 1001;
    private static final boolean VDBG = false;
    private static final int[] sCmccIins = {898600, 898602, 898607, 898608, 898521, 898212};
    private static final int[] sCtIins = {898603, 898611};
    private static QtiPrimaryCardController sInstance;
    private boolean mCardChanged = VDBG;
    QtiCardInfoManager mCardInfoMgr;
    private CommandsInterface[] mCi;
    private Message mCmdMessage;
    private final Context mContext;
    private String[] mCurrentIccIds;
    private boolean mIsLpluslSupport = VDBG;
    private boolean mPcTriggeredFlexMapDone = VDBG;
    QtiPrimaryCardUtils mPcUtils;
    private Phone[] mPhone;
    private int[] mPrefNwModes;
    /* access modifiers changed from: private */
    public int mPrefPrimarySlot = -1;
    private PrimaryCardState mPrimaryCardState = PrimaryCardState.IDLE;
    QtiPrimaryCardPriorityHandler mPriorityHandler;
    private boolean mPriorityMatch = VDBG;
    private QtiRilInterface mQtiRilInterface;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            QtiPrimaryCardController qtiPrimaryCardController = QtiPrimaryCardController.this;
            StringBuilder sb = new StringBuilder();
            sb.append("Recieved intent ");
            sb.append(action);
            qtiPrimaryCardController.logd(sb.toString());
            if ("android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE".equals(action)) {
                QtiPrimaryCardController qtiPrimaryCardController2 = QtiPrimaryCardController.this;
                qtiPrimaryCardController2.sendMessage(qtiPrimaryCardController2.obtainMessage(2));
            }
        }
    };
    private int[] mRetryArray = {2, 5, 10, 20, 30};
    private int mRetryCount;
    /* access modifiers changed from: private */
    public int mRetryPrimarySlot;
    SubsidyLockSettingsObserver mSubsidyLockSettingsObserver;

    public enum PrimaryCardState {
        IDLE,
        IN_PROGRESS,
        PENDING_DUE_TO_PC_IN_PROGRESS,
        PENDING_DUE_TO_FLEXMAP_IN_PROGRESS
    }

    public static void init(Context context, Phone[] phones, CommandsInterface[] ci) {
        synchronized (QtiPrimaryCardController.class) {
            if (sInstance == null && QtiPrimaryCardUtils.isPrimaryCardFeatureEnabled(context)) {
                sInstance = new QtiPrimaryCardController(context, phones, ci);
            }
        }
    }

    public static QtiPrimaryCardController getInstance() {
        QtiPrimaryCardController qtiPrimaryCardController;
        synchronized (QtiPrimaryCardController.class) {
            if (sInstance != null) {
                qtiPrimaryCardController = sInstance;
            } else {
                throw new RuntimeException("QtiPrimaryCardController was not initialized!");
            }
        }
        return qtiPrimaryCardController;
    }

    private QtiPrimaryCardController(Context context, Phone[] phones, CommandsInterface[] ci) {
        this.mContext = context;
        this.mPhone = phones;
        this.mCi = ci;
        this.mPcUtils = QtiPrimaryCardUtils.init(this.mContext);
        this.mPriorityHandler = new QtiPrimaryCardPriorityHandler(this.mContext);
        this.mCardInfoMgr = QtiCardInfoManager.init(this.mContext, ci);
        this.mCardInfoMgr.registerAllCardsInfoAvailable(this, 1, null);
        this.mPrefNwModes = new int[QtiPrimaryCardUtils.PHONE_COUNT];
        this.mCurrentIccIds = new String[QtiPrimaryCardUtils.PHONE_COUNT];
        resetPrimaryCardParams();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE");
        if (SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled()) {
            this.mSubsidyLockSettingsObserver = new SubsidyLockSettingsObserver(this.mContext);
            this.mSubsidyLockSettingsObserver.observe(this.mCardInfoMgr, this.mPriorityHandler);
        }
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.mQtiRilInterface = QtiRilInterface.getInstance(context);
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                logd("on EVENT_ALL_CARDS_INFO_AVAILABLE");
                setPrimaryCardIfRequired(VDBG);
                return;
            case 2:
                logd("on EVENT_SET_RADIO_CAPABILITY_DONE");
                handleSetRadioCapsDone();
                return;
            case 3:
                logd("on EVENT_SET_NWMODE_DONE");
                handleSetNwModeDone(msg);
                return;
            case 4:
                logd("on EVENT_GET_NWMODE_DONE");
                handleGetNwModeDone(msg);
                return;
            case 5:
                logd("on EVENT_PRIMARY_CARD_SET_DONE");
                handleOnSetPrimaryCardDone(msg);
                return;
            case 6:
                logd("on EVENT_SET_PRIMARY_SUB ");
                setPrimaryCardIfRequired(DBG);
                break;
            case 7:
                break;
            default:
                return;
        }
        logd("on EVENT_SERVICE_STATE_CHANGED ");
        handleServiceStateChanged(msg);
    }

    private void handleSetRadioCapsDone() {
        if (this.mPrimaryCardState == PrimaryCardState.PENDING_DUE_TO_FLEXMAP_IN_PROGRESS) {
            this.mPrimaryCardState = PrimaryCardState.IDLE;
            logd("Flex mapping completed, try setting primary card now");
            setPrimaryCardIfRequired(VDBG);
        } else if (this.mPrimaryCardState == PrimaryCardState.IN_PROGRESS || this.mPrimaryCardState == PrimaryCardState.PENDING_DUE_TO_PC_IN_PROGRESS) {
            logd("Primary card trigerred Flex Mapping completed.");
            this.mPcTriggeredFlexMapDone = DBG;
        }
    }

    private void handleSetNwModeDone(Message msg) {
        AsyncResult ar = (AsyncResult) msg.obj;
        int index = Integer.valueOf(msg.arg1).intValue();
        int requestType = Integer.valueOf(msg.arg2).intValue();
        StringBuilder sb = new StringBuilder();
        sb.append("set ");
        sb.append(this.mPrefNwModes[index]);
        sb.append(" for slot ");
        sb.append(index);
        sb.append(" done, ");
        sb.append(ar.exception);
        logd(sb.toString());
        if (ar.exception != null) {
            int i = this.mRetryCount + 1;
            this.mRetryCount = i;
            int[] iArr = this.mRetryArray;
            if (i > iArr.length || requestType != 1000) {
                resetSetNwModeFailureCount();
                for (int i2 = 0; i2 < QtiPrimaryCardUtils.PHONE_COUNT; i2++) {
                    this.mPhone[i2].getPreferredNetworkType(obtainMessage(4, Integer.valueOf(i2)));
                }
                sendSetPrimaryCardResult(2);
            } else {
                int delay = iArr[this.mRetryCount - 1] * 1000;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Scheduling retry for failed set primary card request: ");
                sb2.append(delay);
                sb2.append(" ms");
                logd(sb2.toString());
                this.mRetryPrimarySlot = this.mPrefPrimarySlot;
                postDelayed(new Runnable() {
                    public void run() {
                        if (QtiPrimaryCardController.this.mRetryPrimarySlot == QtiPrimaryCardController.this.mPrefPrimarySlot) {
                            QtiPrimaryCardController.this.logd("Retrying setPrimaryCardIfRequired request");
                            QtiPrimaryCardController.this.setPrimaryCardIfRequired(QtiPrimaryCardController.VDBG, QtiPrimaryCardController.DBG);
                            return;
                        }
                        QtiPrimaryCardController.this.logd("Primary card slot changed, skip retry");
                        QtiPrimaryCardController.this.resetSetNwModeFailureCount();
                    }
                }, (long) delay);
            }
            return;
        }
        if (this.mRetryCount > 0) {
            resetSetNwModeFailureCount();
        }
        if (this.mPcTriggeredFlexMapDone || index == this.mPrefPrimarySlot) {
            setDdsOnPrimaryCardIfRequired();
            sendSetPrimaryCardResult(0);
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("set NwMode[");
            sb3.append(this.mPrefNwModes[this.mPrefPrimarySlot]);
            sb3.append("] on Primarycard:");
            sb3.append(this.mPrefPrimarySlot);
            logd(sb3.toString());
            Phone[] phoneArr = this.mPhone;
            int i3 = this.mPrefPrimarySlot;
            phoneArr[i3].setPreferredNetworkType(this.mPrefNwModes[i3], obtainMessage(3, i3, requestType));
        }
    }

    /* access modifiers changed from: private */
    public void resetSetNwModeFailureCount() {
        this.mRetryCount = 0;
    }

    private void handleGetNwModeDone(Message msg) {
        int modemNwMode = -1;
        AsyncResult ar = (AsyncResult) msg.obj;
        int index = ((Integer) ar.userObj).intValue();
        if (ar.exception == null) {
            modemNwMode = ((int[]) ar.result)[0];
            saveNwModesToDB(modemNwMode, index);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("got nwMode:");
        sb.append(modemNwMode);
        sb.append(" on slot");
        sb.append(index);
        sb.append(", saved to DB, ");
        sb.append(ar.exception);
        logd(sb.toString());
    }

    private void setDdsOnPrimaryCardIfRequired() {
        SubscriptionController subCtrlr = SubscriptionController.getInstance();
        int subId = subCtrlr.getSubIdUsingPhoneId(this.mPrefPrimarySlot);
        subCtrlr.setDefaultDataSubId(subId);
        Global.putInt(this.mContext.getContentResolver(), SETTING_USER_PREF_DATA_SUB, subId);
        StringBuilder sb = new StringBuilder();
        sb.append("Cofigure DDS on ");
        sb.append(subId);
        logd(sb.toString());
    }

    private void sendSetPrimaryCardResult(int responseCode) {
        if (this.mCmdMessage != null) {
            AsyncResult.forMessage(this.mCmdMessage, null, CommandException.fromRilErrno(responseCode));
            this.mCmdMessage.sendToTarget();
            this.mCmdMessage = null;
        }
        if (responseCode == 0) {
            saveCardIccIdInfoInSp();
            notifySetPrimaryCardDone(DBG);
            QtiPrimaryCardUtils.savePrimarySlotToDB(this.mPrefPrimarySlot);
            broadcastPrimarySlotServiceChanged(this.mPrefPrimarySlot);
        } else {
            notifySetPrimaryCardDone(VDBG);
        }
        if (this.mPrimaryCardState == PrimaryCardState.PENDING_DUE_TO_PC_IN_PROGRESS) {
            this.mPrimaryCardState = PrimaryCardState.IDLE;
            logi("Primary Card request completed, check for pending reqeusts");
            setPrimaryCardIfRequired(VDBG);
        } else if (this.mPrimaryCardState == PrimaryCardState.IN_PROGRESS) {
            this.mPrimaryCardState = PrimaryCardState.IDLE;
        }
    }

    /* access modifiers changed from: 0000 */
    public void broadcastPrimarySlotServiceChanged(int slotId) {
        if (!SubscriptionManager.isValidSlotIndex(slotId)) {
            StringBuilder sb = new StringBuilder();
            sb.append(" Error!!! Invalid slotId ");
            sb.append(slotId);
            logd(sb.toString());
            return;
        }
        this.mPhone[slotId].unregisterForServiceStateChanged(this);
        ServiceState ss = this.mPhone[slotId].getServiceState();
        if (ss == null || (ss.getState() != 0 && (ss.getDataRegState() != 0 || ss.getDataNetworkType() == 18))) {
            this.mPhone[slotId].registerForServiceStateChanged(this, 7, new Integer(slotId));
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" broadcastPrimarySlotServiceChanged, slotId ");
            sb2.append(slotId);
            logd(sb2.toString());
            Intent intent = new Intent(ACTION_PRIMARY_CARD_CHANGED_IN_SERVICE);
            intent.putExtra("phone", slotId);
            this.mContext.sendBroadcast(intent);
        }
    }

    private void handleServiceStateChanged(Message msg) {
        AsyncResult ar = (AsyncResult) msg.obj;
        if (ar != null) {
            int currentPrimarySlot = QtiPrimaryCardUtils.getCurrentPrimarySlotFromDB(this.mContext);
            int slotId = ((Integer) ar.userObj).intValue();
            if (SubscriptionManager.isValidSlotIndex(slotId)) {
                this.mPhone[slotId].unregisterForServiceStateChanged(this);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(" Error, Invalid slotId ");
                sb.append(slotId);
                logd(sb.toString());
            }
            broadcastPrimarySlotServiceChanged(currentPrimarySlot);
        }
    }

    private void notifySetPrimaryCardDone(boolean isPass) {
        StringBuilder sb = new StringBuilder();
        sb.append("notifySetPrimaryCardDone: Set Primary Card SUCCESS: ");
        sb.append(isPass);
        logd(sb.toString());
        Intent intent = new Intent(ACTION_SET_PRIMARY_CARD_DONE);
        intent.putExtra(PRIMARY_CARD_RESULT, isPass);
        intent.putExtra("phone", this.mPrefPrimarySlot);
        this.mContext.sendBroadcast(intent);
    }

    private void handleOnSetPrimaryCardDone(Message msg) {
        AsyncResult ar = (AsyncResult) msg.obj;
        int index = ((Integer) ar.userObj).intValue();
        if (ar.exception == null) {
            QtiPrimaryCardUtils.savePrimarySlotToDB(index);
            broadcastPrimarySlotServiceChanged(index);
            int userSelectionMode = QtiPrimaryCardUtils.getUserSelectionMode();
            boolean enableUserSelection = VDBG;
            int numCmccCards = 0;
            for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
                if (isCardMatchesIins(this.mCardInfoMgr.getCardInfo(i).getIccId(), sCmccIins)) {
                    numCmccCards++;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("userSelectionMode = ");
            sb.append(userSelectionMode);
            sb.append(", mPriorityMatch");
            sb.append(this.mPriorityMatch);
            sb.append(", numCmccCards = ");
            sb.append(numCmccCards);
            logd(sb.toString());
            if (userSelectionMode == 3 || (userSelectionMode == 2 && this.mPriorityMatch)) {
                enableUserSelection = DBG;
            }
            if (this.mContext.getResources().getBoolean(17891499)) {
                if (numCmccCards == 0) {
                    enableUserSelection = DBG;
                }
                QtiPrimaryCardUtils.saveEnableUserSelectioninDB(enableUserSelection);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("handleOnSetPrimaryCardDone: enableUserSelection =");
                sb2.append(enableUserSelection);
                sb2.append(", mCardChanged:");
                sb2.append(this.mCardChanged);
                sb2.append(", numCardsValid:");
                sb2.append(numCardsValid());
                logd(sb2.toString());
                if (enableUserSelection && this.mCardChanged && numCardsValid() > 1 && this.mPriorityMatch) {
                    startLTEConifgActivity();
                }
                return;
            }
            QtiPrimaryCardUtils.saveEnableUserSelectioninDB(enableUserSelection);
            boolean subsidyLockFeatureEnabled = SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled();
            boolean isPermanentlyUnlocked = DBG;
            if (subsidyLockFeatureEnabled) {
                SubsidyLockSettingsObserver subsidyLockSettingsObserver = this.mSubsidyLockSettingsObserver;
                isPermanentlyUnlocked = SubsidyLockSettingsObserver.isPermanentlyUnlocked(this.mContext);
            }
            if (enableUserSelection && this.mCardChanged && numCardsValid() > 1 && isPermanentlyUnlocked) {
                startLTEConifgActivity();
            }
        }
    }

    private void startLTEConifgActivity() {
        Intent intent = new Intent("codeaurora.intent.action.ACTION_LTE_CONFIGURE");
        intent.setFlags(813694976);
        this.mContext.startActivity(intent);
    }

    public void saveUserSelectionMode() {
        int userSelectionMode = QtiPrimaryCardUtils.getUserSelectionMode();
        boolean enableUserSelection = VDBG;
        if (userSelectionMode == 3 || (userSelectionMode == 2 && this.mPriorityMatch)) {
            enableUserSelection = DBG;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("saveUserSelectionMode: enableUserSelection =");
        sb.append(enableUserSelection);
        logd(sb.toString());
        QtiPrimaryCardUtils.saveEnableUserSelectioninDB(enableUserSelection);
    }

    private int numCardsValid() {
        int numCount = 0;
        for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
            if (this.mCardInfoMgr.getCardInfo(i).getIccId() != null) {
                numCount++;
            }
        }
        return numCount;
    }

    private void updateDdsPreferenceInDb() {
        boolean disableDds = VDBG;
        if (QtiPrimaryCardUtils.isCmccPrimaryCardFeatureEnabled(this.mContext)) {
            int numCmccCards = 0;
            for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
                if (isCardMatchesIins(this.mCardInfoMgr.getCardInfo(i).getIccId(), sCmccIins)) {
                    numCmccCards++;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("numCmccCards: ");
            sb.append(numCmccCards);
            logi(sb.toString());
            if (numCmccCards == 1) {
                logi("updateDdsPreferenceInDb: Disable DDS in UI.");
                disableDds = DBG;
            }
        }
        if (QtiPrimaryCardUtils.disableDds() != 0) {
            disableDds = DBG;
        }
        QtiPrimaryCardUtils.saveDisableDdsPreferenceInDB(disableDds);
    }

    private boolean isCardMatchesIins(String iccId, int[] iins) {
        if (iccId == null || iccId.length() < 6) {
            return VDBG;
        }
        int cardIin = Integer.parseInt(iccId.substring(0, 6));
        for (int iin : iins) {
            if (iin == cardIin) {
                return DBG;
            }
        }
        return VDBG;
    }

    private void resetPrimaryCardParams() {
        this.mPriorityMatch = VDBG;
        this.mCmdMessage = null;
        this.mPcTriggeredFlexMapDone = VDBG;
        for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
            this.mPrefNwModes[i] = QtiPrimaryCardUtils.getDefaultNwMode();
        }
    }

    public void trySetPrimarySub() {
        sendMessage(obtainMessage(6));
    }

    private void setPrimaryCardIfRequired(boolean force) {
        setPrimaryCardIfRequired(force, VDBG);
    }

    /* access modifiers changed from: private */
    public void setPrimaryCardIfRequired(boolean force, boolean isRetryRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append("setPrimaryCardIfRequired: force: ");
        sb.append(force);
        logd(sb.toString());
        if ((this.mPrimaryCardState == PrimaryCardState.IN_PROGRESS || this.mPrimaryCardState == PrimaryCardState.PENDING_DUE_TO_PC_IN_PROGRESS) && !isRetryRequest) {
            this.mPrimaryCardState = PrimaryCardState.PENDING_DUE_TO_PC_IN_PROGRESS;
            logi("Primary Card setting in progress. WAIT!");
        } else if (QtiUiccCardProvisioner.getInstance().isFlexMapInProgress() || this.mPrimaryCardState == PrimaryCardState.PENDING_DUE_TO_FLEXMAP_IN_PROGRESS) {
            this.mPrimaryCardState = PrimaryCardState.PENDING_DUE_TO_FLEXMAP_IN_PROGRESS;
            logi("Flex Map in progress. WAIT!");
        } else if (QtiUiccCardProvisioner.getInstance().isAnyProvisionRequestInProgress()) {
            logi("Manual provisioning in progress. EXIT!");
        } else {
            boolean isCardChanged = isCardsInfoChanged();
            boolean isSubsidyLockFeatureEnabled = SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled();
            boolean isSetable = VDBG;
            this.mPriorityHandler.loadCurrentPriorityConfigs((isSubsidyLockFeatureEnabled || force) || isCardChanged);
            this.mPrefPrimarySlot = this.mPriorityHandler.getPrefPrimarySlot();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("mPrefPrimarySlot: setPrimaryCardIfRequired: ");
            sb2.append(this.mPrefPrimarySlot);
            loge(sb2.toString());
            if (!this.mQtiRilInterface.getLpluslSupportStatus() && this.mPrefPrimarySlot != -1) {
                isSetable = true;
            }
            QtiPrimaryCardUtils.savePrimarySetable(isSetable);
            if (isCardChanged || isRetryRequest || force) {
                this.mCardChanged = isCardChanged;
                resetPrimaryCardParams();
                updateDdsPreferenceInDb();
                int i = this.mPrefPrimarySlot;
                if (i == -2) {
                    this.mPrefPrimarySlot = QtiPrimaryCardUtils.getDefaultPrimarySlot();
                    this.mPriorityMatch = DBG;
                } else if (i < 0) {
                    logi("Both slots do not have cards with priority config defined. EXIT!");
                    if (isRetryRequest) {
                        sendSetPrimaryCardResult(2);
                        resetSetNwModeFailureCount();
                    }
                    return;
                }
                int i2 = this.mPrefPrimarySlot;
                setPrimaryCardOnSlot(i2, obtainMessage(5, Integer.valueOf(i2)), isRetryRequest);
                return;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append("primary card ");
            sb3.append(QtiPrimaryCardUtils.getCurrentPrimarySlotFromDB(this.mContext));
            sb3.append(" ,Cards not changed, IGNORE!!");
            logd(sb3.toString());
        }
    }

    public void setPrimaryCardOnSlot(int slotId) {
        if (!QtiSubscriptionController.getInstance().isRadioInValidState()) {
            StringBuilder sb = new StringBuilder();
            sb.append("setPrimaryCardOnSlot[");
            sb.append(slotId);
            sb.append("]: Radio is in Invalid State, EXIT!!!");
            loge(sb.toString());
            sendSetPrimaryCardResult(2);
            return;
        }
        setPrimaryCardOnSlot(slotId, null);
    }

    private synchronized void setPrimaryCardOnSlot(int slotId, Message msg) {
        setPrimaryCardOnSlot(slotId, msg, VDBG);
    }

    private synchronized void setPrimaryCardOnSlot(int primarySlotId, Message msg, boolean isRetryRequest) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        int subId = SubscriptionController.getInstance().getSubIdUsingPhoneId(primarySlotId);
        StringBuilder sb = new StringBuilder();
        sb.append("setPrimaryCardOnSlot: for slotId:");
        sb.append(primarySlotId);
        sb.append(", Start.");
        logd(sb.toString());
        if ((this.mPrimaryCardState == PrimaryCardState.IDLE || isRetryRequest) && this.mPriorityHandler.isConfigLoadDone() && SubscriptionManager.isValidSlotIndex(primarySlotId)) {
            if (subscriptionManager.isActiveSubscriptionId(subId)) {
                if (msg == null) {
                    for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
                        this.mCurrentIccIds[i] = this.mCardInfoMgr.getCardInfo(i).getIccId();
                    }
                }
                this.mPrimaryCardState = PrimaryCardState.IN_PROGRESS;
                this.mPrefNwModes = this.mPriorityHandler.getNwModesFromConfig(primarySlotId);
                this.mPrefPrimarySlot = primarySlotId;
                this.mCmdMessage = msg;
                int isFwkRequest = this.mCmdMessage != null ? 1000 : 1001;
                this.mPcTriggeredFlexMapDone = VDBG;
                for (int i2 = 0; i2 < QtiPrimaryCardUtils.PHONE_COUNT; i2++) {
                    saveNwModesToDB(this.mPrefNwModes[i2], i2);
                }
                for (int index = 0; index < QtiPrimaryCardUtils.PHONE_COUNT; index++) {
                    if (index != primarySlotId) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("set NwMode[");
                        sb2.append(this.mPrefNwModes[index]);
                        sb2.append("]  on Secondary card:");
                        sb2.append(index);
                        logd(sb2.toString());
                        this.mPhone[index].setPreferredNetworkType(this.mPrefNwModes[index], obtainMessage(3, index, isFwkRequest));
                    }
                }
                return;
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Primary Card State is not IDLE, mPrimaryCardState:");
        sb3.append(this.mPrimaryCardState);
        sb3.append(" subId: ");
        sb3.append(subId);
        sb3.append(", or configs not yet loaded EXIT!");
        loge(sb3.toString());
        sendSetPrimaryCardResult(2);
    }

    private void saveNwModesToDB(int nwMode, int slotId) {
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        int[] subId = QtiSubscriptionController.getInstance().getSubId(slotId);
        String str = "preferred_network_mode";
        if (subId != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("saveNwModesToDB: subId[");
            sb.append(slotId);
            sb.append("] = ");
            sb.append(subId[0]);
            sb.append(", new Nw mode = ");
            sb.append(nwMode);
            logi(sb.toString());
            if (subscriptionManager.isActiveSubscriptionId(subId[0])) {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(subId[0]);
                Global.putInt(contentResolver, sb2.toString(), nwMode);
            }
        } else {
            loge("saveNwModesToDB: subId is null, do not save nwMode in subId based DB");
        }
        QtiPhoneUtils.putIntAtIndex(this.mContext.getContentResolver(), str, slotId, nwMode);
    }

    private boolean isCardsInfoChanged() {
        boolean cardChanged = VDBG;
        for (int index = 0; index < QtiPrimaryCardUtils.PHONE_COUNT; index++) {
            if (isCardsInfoChanged(index)) {
                cardChanged = DBG;
            }
        }
        return cardChanged;
    }

    /* access modifiers changed from: protected */
    public boolean isCardsInfoChanged(int phoneId) {
        String[] strArr = this.mCurrentIccIds;
        String iccId = this.mCardInfoMgr.getCardInfo(phoneId).getIccId();
        strArr[phoneId] = iccId;
        String iccId2 = iccId;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        StringBuilder sb = new StringBuilder();
        sb.append(PRIMARYCARD_SUBSCRIPTION_KEY);
        sb.append(phoneId);
        return TextUtils.equals(iccId2, defaultSharedPreferences.getString(sb.toString(), null)) ^ DBG;
    }

    private void saveCardIccIdInfoInSp() {
        for (int i = 0; i < QtiPrimaryCardUtils.PHONE_COUNT; i++) {
            String iccId = this.mCurrentIccIds[i];
            Editor edit = PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
            StringBuilder sb = new StringBuilder();
            sb.append(PRIMARYCARD_SUBSCRIPTION_KEY);
            sb.append(i);
            edit.putString(sb.toString(), iccId).commit();
        }
    }

    /* access modifiers changed from: private */
    public void logd(String string) {
        Rlog.d(LOG_TAG, string);
    }

    private void logi(String string) {
        Rlog.i(LOG_TAG, string);
    }

    private void loge(String string) {
        Rlog.e(LOG_TAG, string);
    }
}
