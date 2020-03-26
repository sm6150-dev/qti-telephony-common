package com.qualcomm.qti.internal.telephony.primarycard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.qualcomm.qti.internal.telephony.QtiSubscriptionController;
import com.qualcomm.qti.internal.telephony.QtiUiccCardProvisioner;

public class QtiCardInfoManager extends Handler {
    private static final boolean DBG = true;
    private static final int EVENT_ICC_CHANGED = 3;
    private static final int EVENT_MANUAL_PROVISION_STATE_CHANGED = 1;
    private static final int EVENT_READ_EF_HPLMNWACT_DONE = 2;
    private static final int EVENT_SUBINFO_RECORD_ADDED = 4;
    private static final int HPLMN_SEL_DATA_LEN = 5;
    private static final String LOG_TAG = "QtiPcCardInfoManager";
    static int PHONE_COUNT = 0;
    private static final int UPDATE_CARDTYPE_COMPLETED = 2;
    private static final int UPDATE_CARDTYPE_INIT = 0;
    private static final int UPDATE_CARDTYPE_IN_PROGRESS = 1;
    private static final int UPDATE_CARDTYPE_NOT_NEEDED = 3;
    private static final boolean VDBG = false;
    /* access modifiers changed from: private */
    public static Context mContext;
    private static QtiCardInfoManager sInstance;
    private RegistrantList mAllCardsInfoAvailableRegistrants = new RegistrantList();
    /* access modifiers changed from: private */
    public CardInfo[] mCardInfos;
    private QtiUiccCardProvisioner mQtiCardProvisioner;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                int slotId = intent.getIntExtra("slot", 0);
                String stateExtra = intent.getStringExtra("ss");
                QtiCardInfoManager qtiCardInfoManager = QtiCardInfoManager.this;
                qtiCardInfoManager.logd(" SIM_STATE_CHANGED intent received state is " + stateExtra + " slotId + " + slotId);
                if (SubscriptionManager.isValidSlotIndex(slotId)) {
                    if (!"LOADED".equals(stateExtra)) {
                        boolean unused = QtiCardInfoManager.this.mCardInfos[slotId].mMCCMNCLoaded = QtiCardInfoManager.VDBG;
                    } else if (!QtiCardInfoManager.this.mCardInfos[slotId].mMCCMNCLoaded) {
                        boolean unused2 = QtiCardInfoManager.this.mCardInfos[slotId].mMCCMNCLoaded = QtiCardInfoManager.DBG;
                        QtiCardInfoManager.this.updateCardInfo(slotId);
                    }
                }
            }
        }
    };

    public enum CardType {
        UNKNOWN,
        CARDTYPE_2G,
        CARDTYPE_3G,
        CARDTYPE_4G
    }

    public static class CardInfo {
        /* access modifiers changed from: private */
        public CardType mCardType;
        /* access modifiers changed from: private */
        public String mIccId;
        /* access modifiers changed from: private */
        public boolean mMCCMNCLoaded;
        /* access modifiers changed from: private */
        public String mMccMnc;
        /* access modifiers changed from: private */
        public int mProvisionState;
        /* access modifiers changed from: private */
        public int mUpdateCardTypeState;

        /* access modifiers changed from: private */
        public void reset() {
            this.mCardType = CardType.UNKNOWN;
            this.mIccId = null;
            this.mMccMnc = null;
            this.mUpdateCardTypeState = 0;
            this.mProvisionState = -1;
        }

        public String getIccId() {
            if (!QtiPrimaryCardUtils.setPrimaryCardOnDeAct() || this.mProvisionState != 0) {
                return this.mIccId;
            }
            return null;
        }

        public String getMccMnc() {
            if (!QtiPrimaryCardUtils.setPrimaryCardOnDeAct() || this.mProvisionState != 0) {
                return this.mMccMnc;
            }
            return null;
        }

        public int getProvisionState() {
            return this.mProvisionState;
        }

        public boolean isMccMncLoaded() {
            return this.mMCCMNCLoaded;
        }

        public boolean isCardTypeSame(String cardType) {
            if (!QtiPrimaryCardUtils.setPrimaryCardOnDeAct() || this.mProvisionState != 0) {
                return CardType.valueOf(cardType).equals(this.mCardType);
            }
            return QtiCardInfoManager.VDBG;
        }

        public boolean isCardInfoAvailable(int slotId) {
            int i = this.mUpdateCardTypeState;
            boolean isAvailable = (i == 0 || i == 1) ? QtiCardInfoManager.VDBG : true;
            if (SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled() && !SubsidyLockSettingsObserver.isPermanentlyUnlocked(QtiCardInfoManager.mContext)) {
                UiccCard uiccCard = UiccController.getInstance().getUiccCard(slotId);
                if (!(this.mProvisionState != 1 || uiccCard == null || uiccCard.getApplication(1) == null)) {
                    UiccCardApplication app = uiccCard.getApplication(1);
                    if (app != null && !app.isPersoLocked()) {
                        isAvailable &= this.mMCCMNCLoaded;
                    }
                    if (!this.mMCCMNCLoaded) {
                        this.mMccMnc = null;
                    }
                }
            }
            return isAvailable;
        }
    }

    static QtiCardInfoManager init(Context context, CommandsInterface[] ci) {
        synchronized (QtiCardInfoManager.class) {
            if (sInstance == null) {
                sInstance = new QtiCardInfoManager(context, ci);
            }
        }
        return sInstance;
    }

    public static QtiCardInfoManager getInstance() {
        QtiCardInfoManager qtiCardInfoManager;
        synchronized (QtiCardInfoManager.class) {
            if (sInstance != null) {
                qtiCardInfoManager = sInstance;
            } else {
                throw new RuntimeException("QtiCardInfoManager was not initialized!");
            }
        }
        return qtiCardInfoManager;
    }

    private QtiCardInfoManager(Context context, CommandsInterface[] ci) {
        mContext = context;
        PHONE_COUNT = ((TelephonyManager) mContext.getSystemService("phone")).getPhoneCount();
        this.mCardInfos = new CardInfo[PHONE_COUNT];
        for (int index = 0; index < PHONE_COUNT; index++) {
            this.mCardInfos[index] = new CardInfo();
            ci[index].registerForAvailable(this, 1, new Integer(index));
        }
        this.mQtiCardProvisioner = QtiUiccCardProvisioner.getInstance();
        this.mQtiCardProvisioner.registerForManualProvisionChanged(this, 1, (Object) null);
        UiccController.getInstance().registerForIccChanged(this, 3, (Object) null);
        QtiSubscriptionController.getInstance().registerForAddSubscriptionRecord(this, 4, (Object) null);
        if (SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled()) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.SIM_STATE_CHANGED");
            context.registerReceiver(this.receiver, filter);
        }
    }

    public void registerAllCardsInfoAvailable(Handler handler, int what, Object obj) {
        Registrant r = new Registrant(handler, what, obj);
        synchronized (this.mAllCardsInfoAvailableRegistrants) {
            this.mAllCardsInfoAvailableRegistrants.add(r);
            int index = 0;
            while (index < PHONE_COUNT) {
                if (this.mCardInfos[index].isCardInfoAvailable(index)) {
                    index++;
                } else {
                    return;
                }
            }
            r.notifyRegistrant();
        }
    }

    public void unregisterAllCardsInfoAvailable(Handler handler) {
        synchronized (this.mAllCardsInfoAvailableRegistrants) {
            this.mAllCardsInfoAvailableRegistrants.remove(handler);
        }
    }

    public void handleMessage(Message msg) {
        int i = msg.what;
        if (i == 1) {
            logd("on EVENT_MANUAL_PROVISION_STATE_CHANGED");
            onManualProvisionStateChanged((AsyncResult) msg.obj);
        } else if (i == 2) {
            logd("on EVENT_READ_EF_HPLMNWACT_DONE");
            onEfLoaded((AsyncResult) msg.obj);
        } else if (i == 3) {
            logd("on EVENT_ICC_CHANGED");
            onIccChanged((AsyncResult) msg.obj);
        } else if (i == 4) {
            logd("on EVENT_SUBINFO_RECORD_ADDED");
            onSubscriptionInfoChanged((AsyncResult) msg.obj);
        }
    }

    private void onSubscriptionInfoChanged(AsyncResult subInfoChange) {
        if (subInfoChange != null && subInfoChange.result != null) {
            this.mCardInfos[((Integer) subInfoChange.result).intValue()].reset();
            updateCardInfo(((Integer) subInfoChange.result).intValue());
        }
    }

    private void onIccChanged(AsyncResult iccChangedResult) {
        if (iccChangedResult != null && iccChangedResult.result != null) {
            updateCardInfo(((Integer) iccChangedResult.result).intValue());
        }
    }

    private void onManualProvisionStateChanged(AsyncResult manualProvisionResult) {
        if (manualProvisionResult == null || manualProvisionResult.result == null) {
            for (int index = 0; index < PHONE_COUNT; index++) {
                updateCardInfo(index);
            }
            return;
        }
        updateCardInfo(((Integer) manualProvisionResult.result).intValue());
    }

    /* access modifiers changed from: private */
    public void updateCardInfo(int slotId) {
        SubscriptionInfo sir;
        int currProvState = this.mQtiCardProvisioner.getCurrentUiccCardProvisioningStatus(slotId);
        String currIccId = this.mQtiCardProvisioner.getUiccIccId(slotId);
        String currMccMnc = null;
        logd("updateCardInfo[" + slotId + "]: Start!");
        if (!QtiSubscriptionController.getInstance().isRadioInValidState()) {
            loge("updateCardInfo[" + slotId + "]: Radio is in Invalid State, IGNORE!!!");
            return;
        }
        if (1 == currProvState) {
            UiccCard uiccCard = UiccController.getInstance().getUiccCard(slotId);
            if (uiccCard == null || uiccCard.getApplication(1) == null) {
                loge("updateCardInfo[" + slotId + "]: card not READY!! ");
                return;
            }
            SubscriptionManager subscriptionManager = (SubscriptionManager) mContext.getSystemService("telephony_subscription_service");
            if (!subscriptionManager.isActiveSubscriptionId(SubscriptionController.getInstance().getSubIdUsingPhoneId(slotId))) {
                loge("updateCardInfo[" + slotId + "]: subId not added yet!! ");
                return;
            } else if (SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled() && (sir = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slotId)) != null) {
                currMccMnc = "" + sir.getMcc() + sir.getMnc();
            }
        }
        if (currProvState == -1) {
            this.mCardInfos[slotId].reset();
            logd("updateCardInfo[" + slotId + "]: ProvStatus is Invalid, reset cardInfo!");
        } else if (isUpdateCardInfoRequired(slotId, currIccId, currProvState, currMccMnc)) {
            if (currProvState == -2) {
                this.mCardInfos[slotId].reset();
                int unused = this.mCardInfos[slotId].mProvisionState = currProvState;
                int unused2 = this.mCardInfos[slotId].mUpdateCardTypeState = 3;
                logd("updateCardInfo[" + slotId + "]: CardAbsent!!!");
                notifyAllCardsInfoAvailableIfNeeded();
            } else {
                this.mCardInfos[slotId].reset();
                logd("updateCardInfo[" + slotId + "]: Query current state is required!");
                String unused3 = this.mCardInfos[slotId].mIccId = currIccId;
                String unused4 = this.mCardInfos[slotId].mMccMnc = currMccMnc;
                int unused5 = this.mCardInfos[slotId].mProvisionState = currProvState;
                if (updateUiccCardType(slotId)) {
                    int unused6 = this.mCardInfos[slotId].mUpdateCardTypeState = 2;
                    notifyAllCardsInfoAvailableIfNeeded();
                }
            }
        }
        logi("updateCardInfo[" + slotId + "]: Exit! - UpdateCardTypeState: " + this.mCardInfos[slotId].mUpdateCardTypeState + ", mCardType: " + this.mCardInfos[slotId].mCardType);
    }

    private boolean isSubsidyRestricted() {
        return (!SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled() || SubsidyLockSettingsObserver.isPermanentlyUnlocked(mContext)) ? VDBG : DBG;
    }

    private boolean isUpdateCardInfoRequired(int slotId, String currIccId, int currProvState, String currMccmnc) {
        if (!TextUtils.equals(currIccId, this.mCardInfos[slotId].mIccId)) {
            return DBG;
        }
        if ((isSubsidyRestricted() && currMccmnc != null && !TextUtils.equals(currMccmnc, this.mCardInfos[slotId].mMccMnc)) || currProvState != this.mCardInfos[slotId].mProvisionState || this.mCardInfos[slotId].mUpdateCardTypeState == 0) {
            return DBG;
        }
        if (this.mCardInfos[slotId].mCardType != CardType.UNKNOWN || this.mCardInfos[slotId].mUpdateCardTypeState == 3) {
            return VDBG;
        }
        return DBG;
    }

    private boolean updateUiccCardType(int slotId) {
        try {
            UiccCardApplication app = UiccController.getInstance().getUiccCard(slotId).getApplication(1);
            if (app.getType() != IccCardApplicationStatus.AppType.APPTYPE_USIM) {
                CardType unused = this.mCardInfos[slotId].mCardType = CardType.CARDTYPE_2G;
            } else {
                boolean read4gEf = QtiPrimaryCardUtils.read4gFlag();
                CardType unused2 = this.mCardInfos[slotId].mCardType = CardType.CARDTYPE_3G;
                if (read4gEf) {
                    IccFileHandler iccFh = app.getIccFileHandler();
                    if (this.mCardInfos[slotId].mProvisionState == 1) {
                        int unused3 = this.mCardInfos[slotId].mUpdateCardTypeState = 1;
                        iccFh.loadEFTransparent(28514, obtainMessage(2, Integer.valueOf(slotId)));
                        return VDBG;
                    }
                }
            }
        } catch (Exception e) {
            loge("For slot " + slotId + " Exception while updateUiccCardType " + e.getMessage());
        }
        return DBG;
    }

    private void onEfLoaded(AsyncResult ar) {
        int slotId = ((Integer) ar.userObj).intValue();
        logd("onEfLoaded: Started");
        if (ar.exception != null) {
            logd("EF_HPLMNWACT read with exception = " + ar.exception);
        } else {
            byte[] data = (byte[]) ar.result;
            logd("result=" + IccUtils.bytesToHexString(data));
            int numRec = data.length / 5;
            logd("number of Records=" + numRec);
            int i = 0;
            while (true) {
                if (i >= numRec) {
                    break;
                } else if ((data[(i * 5) + 3] & 64) != 0) {
                    CardType unused = this.mCardInfos[slotId].mCardType = CardType.CARDTYPE_4G;
                    break;
                } else {
                    i++;
                }
            }
        }
        int unused2 = this.mCardInfos[slotId].mUpdateCardTypeState = 2;
        notifyAllCardsInfoAvailableIfNeeded();
        logd("onEfLoaded(" + slotId + ") : mCardType = " + this.mCardInfos[slotId].mCardType);
    }

    private void notifyAllCardsInfoAvailableIfNeeded() {
        for (int index = 0; index < PHONE_COUNT; index++) {
            if (!this.mCardInfos[index].isCardInfoAvailable(index)) {
                logd(" card info not available " + index);
                return;
            }
        }
        this.mAllCardsInfoAvailableRegistrants.notifyRegistrants();
    }

    public CardInfo getCardInfo(int slotId) {
        return this.mCardInfos[slotId];
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
