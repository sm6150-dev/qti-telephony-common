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
import com.android.internal.telephony.uicc.IccCardApplicationStatus.AppType;
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
                StringBuilder sb = new StringBuilder();
                sb.append(" SIM_STATE_CHANGED intent received state is ");
                sb.append(stateExtra);
                sb.append(" slotId + ");
                sb.append(slotId);
                qtiCardInfoManager.logd(sb.toString());
                if (SubscriptionManager.isValidSlotIndex(slotId)) {
                    if (!"LOADED".equals(stateExtra)) {
                        QtiCardInfoManager.this.mCardInfos[slotId].mMCCMNCLoaded = QtiCardInfoManager.VDBG;
                    } else if (!QtiCardInfoManager.this.mCardInfos[slotId].mMCCMNCLoaded) {
                        QtiCardInfoManager.this.mCardInfos[slotId].mMCCMNCLoaded = QtiCardInfoManager.DBG;
                        QtiCardInfoManager.this.updateCardInfo(slotId);
                    }
                }
            }
        }
    };

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

    public enum CardType {
        UNKNOWN,
        CARDTYPE_2G,
        CARDTYPE_3G,
        CARDTYPE_4G
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
        this.mQtiCardProvisioner.registerForManualProvisionChanged(this, 1, null);
        UiccController.getInstance().registerForIccChanged(this, 3, null);
        QtiSubscriptionController.getInstance().registerForAddSubscriptionRecord(this, 4, null);
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
        int currProvState = this.mQtiCardProvisioner.getCurrentUiccCardProvisioningStatus(slotId);
        String currIccId = this.mQtiCardProvisioner.getUiccIccId(slotId);
        String currMccMnc = null;
        StringBuilder sb = new StringBuilder();
        String str = "updateCardInfo[";
        sb.append(str);
        sb.append(slotId);
        sb.append("]: Start!");
        logd(sb.toString());
        if (!QtiSubscriptionController.getInstance().isRadioInValidState()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(slotId);
            sb2.append("]: Radio is in Invalid State, IGNORE!!!");
            loge(sb2.toString());
            return;
        }
        if (1 == currProvState) {
            UiccCard uiccCard = UiccController.getInstance().getUiccCard(slotId);
            if (uiccCard == null || uiccCard.getApplication(1) == null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(slotId);
                sb3.append("]: card not READY!! ");
                loge(sb3.toString());
                return;
            }
            SubscriptionManager subscriptionManager = (SubscriptionManager) mContext.getSystemService("telephony_subscription_service");
            if (!subscriptionManager.isActiveSubscriptionId(SubscriptionController.getInstance().getSubIdUsingPhoneId(slotId))) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(slotId);
                sb4.append("]: subId not added yet!! ");
                loge(sb4.toString());
                return;
            } else if (SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled()) {
                SubscriptionInfo sir = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(slotId);
                if (sir != null) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("");
                    sb5.append(sir.getMcc());
                    sb5.append(sir.getMnc());
                    currMccMnc = sb5.toString();
                }
            }
        }
        if (currProvState == -1) {
            this.mCardInfos[slotId].reset();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            sb6.append(slotId);
            sb6.append("]: ProvStatus is Invalid, reset cardInfo!");
            logd(sb6.toString());
        } else if (isUpdateCardInfoRequired(slotId, currIccId, currProvState, currMccMnc)) {
            if (currProvState == -2) {
                this.mCardInfos[slotId].reset();
                this.mCardInfos[slotId].mProvisionState = currProvState;
                this.mCardInfos[slotId].mUpdateCardTypeState = 3;
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str);
                sb7.append(slotId);
                sb7.append("]: CardAbsent!!!");
                logd(sb7.toString());
                notifyAllCardsInfoAvailableIfNeeded();
            } else {
                this.mCardInfos[slotId].reset();
                StringBuilder sb8 = new StringBuilder();
                sb8.append(str);
                sb8.append(slotId);
                sb8.append("]: Query current state is required!");
                logd(sb8.toString());
                this.mCardInfos[slotId].mIccId = currIccId;
                this.mCardInfos[slotId].mMccMnc = currMccMnc;
                this.mCardInfos[slotId].mProvisionState = currProvState;
                if (updateUiccCardType(slotId)) {
                    this.mCardInfos[slotId].mUpdateCardTypeState = 2;
                    notifyAllCardsInfoAvailableIfNeeded();
                }
            }
        }
        StringBuilder sb9 = new StringBuilder();
        sb9.append(str);
        sb9.append(slotId);
        sb9.append("]: Exit! - UpdateCardTypeState: ");
        sb9.append(this.mCardInfos[slotId].mUpdateCardTypeState);
        sb9.append(", mCardType: ");
        sb9.append(this.mCardInfos[slotId].mCardType);
        logi(sb9.toString());
    }

    private boolean isSubsidyRestricted() {
        return (!SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled() || SubsidyLockSettingsObserver.isPermanentlyUnlocked(mContext)) ? VDBG : DBG;
    }

    private boolean isUpdateCardInfoRequired(int slotId, String currIccId, int currProvState, String currMccmnc) {
        if (!TextUtils.equals(currIccId, this.mCardInfos[slotId].mIccId) || ((isSubsidyRestricted() && currMccmnc != null && !TextUtils.equals(currMccmnc, this.mCardInfos[slotId].mMccMnc)) || currProvState != this.mCardInfos[slotId].mProvisionState || this.mCardInfos[slotId].mUpdateCardTypeState == 0 || (this.mCardInfos[slotId].mCardType == CardType.UNKNOWN && this.mCardInfos[slotId].mUpdateCardTypeState != 3))) {
            return DBG;
        }
        return VDBG;
    }

    private boolean updateUiccCardType(int slotId) {
        try {
            UiccCardApplication app = UiccController.getInstance().getUiccCard(slotId).getApplication(1);
            if (app.getType() != AppType.APPTYPE_USIM) {
                this.mCardInfos[slotId].mCardType = CardType.CARDTYPE_2G;
            } else {
                boolean read4gEf = QtiPrimaryCardUtils.read4gFlag();
                this.mCardInfos[slotId].mCardType = CardType.CARDTYPE_3G;
                if (read4gEf) {
                    IccFileHandler iccFh = app.getIccFileHandler();
                    if (this.mCardInfos[slotId].mProvisionState == 1) {
                        this.mCardInfos[slotId].mUpdateCardTypeState = 1;
                        iccFh.loadEFTransparent(28514, obtainMessage(2, Integer.valueOf(slotId)));
                        return VDBG;
                    }
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("For slot ");
            sb.append(slotId);
            sb.append(" Exception while updateUiccCardType ");
            sb.append(e.getMessage());
            loge(sb.toString());
        }
        return DBG;
    }

    private void onEfLoaded(AsyncResult ar) {
        int slotId = ((Integer) ar.userObj).intValue();
        logd("onEfLoaded: Started");
        if (ar.exception != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("EF_HPLMNWACT read with exception = ");
            sb.append(ar.exception);
            logd(sb.toString());
        } else {
            byte[] data = (byte[]) ar.result;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("result=");
            sb2.append(IccUtils.bytesToHexString(data));
            logd(sb2.toString());
            int numRec = data.length / 5;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("number of Records=");
            sb3.append(numRec);
            logd(sb3.toString());
            int i = 0;
            while (true) {
                if (i >= numRec) {
                    break;
                } else if ((data[(i * 5) + 3] & 64) != 0) {
                    this.mCardInfos[slotId].mCardType = CardType.CARDTYPE_4G;
                    break;
                } else {
                    i++;
                }
            }
        }
        this.mCardInfos[slotId].mUpdateCardTypeState = 2;
        notifyAllCardsInfoAvailableIfNeeded();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("onEfLoaded(");
        sb4.append(slotId);
        sb4.append(") : mCardType = ");
        sb4.append(this.mCardInfos[slotId].mCardType);
        logd(sb4.toString());
    }

    private void notifyAllCardsInfoAvailableIfNeeded() {
        for (int index = 0; index < PHONE_COUNT; index++) {
            if (!this.mCardInfos[index].isCardInfoAvailable(index)) {
                StringBuilder sb = new StringBuilder();
                sb.append(" card info not available ");
                sb.append(index);
                logd(sb.toString());
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
