package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.SubscriptionController;
import java.util.Iterator;
import java.util.List;

public class QtiSubscriptionController extends SubscriptionController {
    private static final String ACTION_SUBSCRIPTION_RECORD_ADDED = "org.codeaurora.intent.action.SUBSCRIPTION_INFO_RECORD_ADDED";
    private static final String APM_SIM_NOT_PWDN_PROPERTY = "persist.vendor.radio.apm_sim_not_pwdn";
    private static final String CARRIER_MODE_CT_CLASS_A = "ct_class_a";
    private static final int DEFAULT_PHONE_INDEX = 0;
    private static final int EVENT_CALL_ENDED = 101;
    static final String LOG_TAG = "QtiSubscriptionController";
    private static final int NOT_PROVISIONED = 0;
    private static final int PROVISIONED = 1;
    private static final String SETTING_USER_PREF_DATA_SUB = "user_preferred_data_sub";
    private static CommandsInterface[] sCi = null;
    private static int sNumPhones;
    private RegistrantList mAddSubscriptionRecordRegistrants = new RegistrantList();
    private String mCarrierMode = SystemProperties.get("persist.vendor.radio.carrier_mode", "default");
    /* access modifiers changed from: private */
    public int mCurrentDdsSubId = Integer.MAX_VALUE;
    private boolean mIsCTClassA = this.mCarrierMode.equals(CARRIER_MODE_CT_CLASS_A);
    /* access modifiers changed from: private */
    public Handler mSubscriptionHandler = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0024, code lost:
            if (com.qualcomm.qti.internal.telephony.QtiSubscriptionController.access$200(r0, com.qualcomm.qti.internal.telephony.QtiSubscriptionController.access$100(r0)) == false) goto L_0x0026;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r4) {
            /*
                r3 = this;
                int r0 = r4.what
                r1 = 101(0x65, float:1.42E-43)
                if (r0 == r1) goto L_0x0007
                goto L_0x0068
            L_0x0007:
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                java.lang.String r1 = "EVENT_CALL_ENDED"
                r0.logd(r1)
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                int r1 = r0.mCurrentDdsSubId
                boolean r0 = r0.isActiveSubId(r1)
                if (r0 == 0) goto L_0x0026
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                int r1 = r0.mCurrentDdsSubId
                boolean r0 = r0.isSubProvisioned(r1)
                if (r0 != 0) goto L_0x0036
            L_0x0026:
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                java.lang.String r1 = "Current dds sub is inactive"
                r0.logd(r1)
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                int r1 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.mDefaultFallbackSubId
                r0.mCurrentDdsSubId = r1
            L_0x0036:
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Set DDS to : "
                r1.append(r2)
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r2 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                int r2 = r2.mCurrentDdsSubId
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.logd(r1)
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                int r1 = r0.mCurrentDdsSubId
                r0.setDefaultDataSubId(r1)
                com.qualcomm.qti.internal.telephony.QtiCallStateNotifier r0 = com.qualcomm.qti.internal.telephony.QtiCallStateNotifier.getInstance()
                com.qualcomm.qti.internal.telephony.QtiSubscriptionController r1 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.this
                android.os.Handler r1 = r1.mSubscriptionHandler
                r0.unregisterForCallEnd(r1)
            L_0x0068:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiSubscriptionController.AnonymousClass1.handleMessage(android.os.Message):void");
        }
    };
    private TelecomManager mTelecomManager;
    private TelephonyManager mTelephonyManager;

    public static QtiSubscriptionController init(Context c, CommandsInterface[] ci) {
        QtiSubscriptionController qtiSubscriptionController;
        synchronized (QtiSubscriptionController.class) {
            if (sInstance == null) {
                sInstance = new QtiSubscriptionController(c);
                sCi = ci;
            } else {
                String str = LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("init() called multiple times!  sInstance = ");
                sb.append(sInstance);
                Log.wtf(str, sb.toString());
            }
            qtiSubscriptionController = sInstance;
        }
        return qtiSubscriptionController;
    }

    public static QtiSubscriptionController getInstance() {
        if (sInstance == null) {
            Log.wtf(LOG_TAG, "getInstance null");
        }
        return sInstance;
    }

    private QtiSubscriptionController(Context c) {
        super(c);
        logd(" init by Context");
        this.mTelecomManager = (TelecomManager) this.mContext.getSystemService("telecom");
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        sNumPhones = TelephonyManager.getDefault().getPhoneCount();
    }

    public void registerForAddSubscriptionRecord(Handler handler, int what, Object obj) {
        Registrant r = new Registrant(handler, what, obj);
        synchronized (this.mAddSubscriptionRecordRegistrants) {
            this.mAddSubscriptionRecordRegistrants.add(r);
            if (getActiveSubscriptionInfoList(this.mContext.getOpPackageName()) != null) {
                r.notifyRegistrant();
            }
        }
    }

    public void unregisterForAddSubscriptionRecord(Handler handler) {
        synchronized (this.mAddSubscriptionRecordRegistrants) {
            this.mAddSubscriptionRecordRegistrants.remove(handler);
        }
    }

    public int addSubInfoRecord(String iccId, int slotIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append("addSubInfoRecord: broadcast intent subId[");
        sb.append(slotIndex);
        sb.append("]");
        logd(sb.toString());
        return addSubInfo(iccId, null, slotIndex, 0);
    }

    public int addSubInfo(String uniqueId, String displayName, int slotIndex, int subscriptionType) {
        int retVal = QtiSubscriptionController.super.addSubInfo(uniqueId, displayName, slotIndex, subscriptionType);
        int[] subId = getSubId(slotIndex);
        if (subId != null && subId.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("addSubInfoRecord: broadcast intent subId[");
            sb.append(slotIndex);
            sb.append("] = ");
            sb.append(subId[0]);
            logd(sb.toString());
            this.mAddSubscriptionRecordRegistrants.notifyRegistrants(new AsyncResult(null, Integer.valueOf(slotIndex), null));
            Intent intent = new Intent(ACTION_SUBSCRIPTION_RECORD_ADDED);
            SubscriptionManager.putPhoneIdAndSubIdExtra(intent, slotIndex, subId[0]);
            this.mContext.sendBroadcast(intent);
        }
        return retVal;
    }

    public void setDefaultDataSubId(int subId) {
        enforceModifyPhoneState("setDefaultDataSubId");
        String flexMapSupportType = SystemProperties.get("persist.vendor.radio.flexmap_type", "nw_mode");
        if (!QtiPhoneSwitcher.isLplusLSupported || !QtiCallStateNotifier.getInstance().isCallInProgress()) {
            if (!SubscriptionManager.isValidSubscriptionId(subId) || !flexMapSupportType.equals("dds")) {
                updateAllDataConnectionTrackers();
                Global.putInt(this.mContext.getContentResolver(), "multi_sim_data_call", subId);
                broadcastDefaultDataSubIdChanged(subId);
            } else {
                QtiRadioCapabilityController radioCapController = QtiRadioCapabilityController.getInstance();
                if (radioCapController.isBothPhonesMappedToSameStack()) {
                    radioCapController.initNormalMappingRequest();
                    StringBuilder sb = new StringBuilder();
                    sb.append(" setDefaultDataSubId init normal mapping: ");
                    sb.append(subId);
                    logd(sb.toString());
                }
                QtiSubscriptionController.super.setDefaultDataSubId(subId);
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Active call, cannot set Dds to : ");
        sb2.append(subId);
        logd(sb2.toString());
        this.mCurrentDdsSubId = subId;
        QtiCallStateNotifier.getInstance().registerForCallEnd(this.mSubscriptionHandler, 101, null);
    }

    /* access modifiers changed from: protected */
    public boolean shouldDefaultBeCleared(List<SubscriptionInfo> records, int subId) {
        StringBuilder sb = new StringBuilder();
        sb.append("[shouldDefaultBeCleared: subId] ");
        sb.append(subId);
        logdl(sb.toString());
        if (records == null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("[shouldDefaultBeCleared] return true no records subId=");
            sb2.append(subId);
            logdl(sb2.toString());
            return true;
        } else if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("[shouldDefaultBeCleared] return false only one subId, subId=");
            sb3.append(subId);
            logdl(sb3.toString());
            return false;
        } else {
            for (SubscriptionInfo record : records) {
                int id = record.getSubscriptionId();
                StringBuilder sb4 = new StringBuilder();
                sb4.append("[shouldDefaultBeCleared] Record.id: ");
                sb4.append(id);
                logdl(sb4.toString());
                if (id == subId) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("[shouldDefaultBeCleared] return false subId is active, subId=");
                    sb5.append(subId);
                    logdl(sb5.toString());
                    return false;
                }
            }
            if (getUiccProvisionStatus(getSlotIndex(subId)) == 1) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append("[shouldDefaultBeCleared] return false subId is provisioned, subId=");
                sb6.append(subId);
                logdl(sb6.toString());
                return false;
            }
            StringBuilder sb7 = new StringBuilder();
            sb7.append("[shouldDefaultBeCleared] return true not active subId=");
            sb7.append(subId);
            logdl(sb7.toString());
            return true;
        }
    }

    private boolean isRadioAvailableOnAllSubs() {
        for (int i = 0; i < sNumPhones; i++) {
            CommandsInterface[] commandsInterfaceArr = sCi;
            if (commandsInterfaceArr != null && commandsInterfaceArr[i].getRadioState() == 2) {
                return false;
            }
        }
        return true;
    }

    private boolean isShuttingDown() {
        for (int i = 0; i < sNumPhones; i++) {
            if (sPhones[i] != null && sPhones[i].isShuttingDown()) {
                return true;
            }
        }
        return false;
    }

    public boolean isRadioInValidState() {
        int simNotPwrDown = 0;
        try {
            simNotPwrDown = QtiTelephonyComponentFactory.getInstance().getRil(0).getPropertyValueInt(APM_SIM_NOT_PWDN_PROPERTY, 0);
        } catch (RemoteException | NullPointerException ex) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception: ");
            sb.append(ex);
            loge(sb.toString());
        }
        boolean isApmSimNotPwrDown = simNotPwrDown == 1;
        int isAPMOn = Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0);
        if (isAPMOn == 1 && !isApmSimNotPwrDown) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("isRadioInValidState, isApmSimNotPwrDown = ");
            sb2.append(isApmSimNotPwrDown);
            sb2.append(", isAPMOn:");
            sb2.append(isAPMOn);
            logd(sb2.toString());
            return false;
        } else if (!isRadioAvailableOnAllSubs()) {
            logd(" isRadioInValidState, radio not available");
            return false;
        } else if (!isShuttingDown()) {
            return true;
        } else {
            logd(" isRadioInValidState: device shutdown in progress ");
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateUserPreferences() {
        SubscriptionInfo mNextActivatedSub = null;
        int activeCount = 0;
        if (!isRadioInValidState()) {
            logd("Radio is in Invalid state, Ignore Updating User Preference!!!");
            return;
        }
        List<SubscriptionInfo> sil = getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
        if (sil == null || sil.size() < 1) {
            logi("updateUserPreferences: Subscription list is empty");
        } else if (SystemProperties.getBoolean("persist.vendor.radio.aosp_usr_pref_sel", false)) {
            logi("updateUserPreferences: AOSP user preference option enabled ");
        } else {
            for (SubscriptionInfo subInfo : sil) {
                if (getUiccProvisionStatus(subInfo.getSimSlotIndex()) == 1) {
                    activeCount++;
                    if (mNextActivatedSub == null) {
                        mNextActivatedSub = subInfo;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("updateUserPreferences:: active sub count = ");
            sb.append(activeCount);
            sb.append(" dds = ");
            sb.append(getDefaultDataSubId());
            String str = " voice = ";
            sb.append(str);
            sb.append(getDefaultVoiceSubId());
            String str2 = " sms = ";
            sb.append(str2);
            sb.append(getDefaultSmsSubId());
            logd(sb.toString());
            if (activeCount == 1) {
                setSMSPromptEnabled(false);
            }
            if (mNextActivatedSub != null && getActiveSubInfoCountMax() != 1) {
                handleDataPreference(mNextActivatedSub.getSubscriptionId());
                if (!isSubProvisioned(getDefaultSmsSubId())) {
                    setDefaultSmsSubId(mNextActivatedSub.getSubscriptionId());
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("updateUserPreferences: isMiuiRom = ");
                sb2.append(isMiuiRom());
                logd(sb2.toString());
                if (!isMiuiRom()) {
                    if (!isSubProvisioned(getDefaultVoiceSubId())) {
                        setDefaultVoiceSubId(mNextActivatedSub.getSubscriptionId());
                    }
                    if (!isNonSimAccountFound() && activeCount == 1) {
                        int subId = mNextActivatedSub.getSubscriptionId();
                        PhoneAccountHandle phoneAccountHandle = subscriptionIdToPhoneAccountHandle(subId);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("set default phoneaccount to  ");
                        sb3.append(subId);
                        logi(sb3.toString());
                        this.mTelecomManager.setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
                    }
                }
                if (!isSubProvisioned(mDefaultFallbackSubId)) {
                    setDefaultFallbackSubId(mNextActivatedSub.getSubscriptionId(), 0);
                }
                notifySubscriptionInfoChanged();
                StringBuilder sb4 = new StringBuilder();
                sb4.append("updateUserPreferences: after currentDds = ");
                sb4.append(getDefaultDataSubId());
                sb4.append(str);
                sb4.append(getDefaultVoiceSubId());
                sb4.append(str2);
                sb4.append(getDefaultSmsSubId());
                logd(sb4.toString());
            }
        }
    }

    private void handleDataPreference(int nextActiveSubId) {
        int userPrefDataSubId = getUserPrefDataSubIdFromDB();
        int currentDataSubId = getDefaultDataSubId();
        List<SubscriptionInfo> subInfoList = getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
        if (subInfoList != null) {
            boolean userPrefSubValid = false;
            for (SubscriptionInfo subInfo : subInfoList) {
                if (subInfo.getSubscriptionId() == userPrefDataSubId) {
                    userPrefSubValid = true;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("havePrefSub = ");
            sb.append(userPrefSubValid);
            sb.append(" user pref subId = ");
            sb.append(userPrefDataSubId);
            sb.append(" current dds ");
            sb.append(currentDataSubId);
            sb.append(" next active subId ");
            sb.append(nextActiveSubId);
            logd(sb.toString());
            if (!isMiuiRom()) {
                if (this.mIsCTClassA && isSubProvisioned(getSubId(0)[0])) {
                    logd("set dds to slot0 for ct classA mode");
                    setDefaultDataSubId(getSubId(0)[0]);
                } else if (userPrefSubValid && isSubProvisioned(userPrefDataSubId) && currentDataSubId != userPrefDataSubId) {
                    setDefaultDataSubId(userPrefDataSubId);
                } else if (!isSubProvisioned(currentDataSubId)) {
                    setDefaultDataSubId(nextActiveSubId);
                }
            }
            QtiRadioCapabilityController.getInstance().setDdsIfRequired(false);
        }
    }

    private boolean isMiuiRom() {
        String str = "";
        return !TextUtils.isEmpty(SystemProperties.get("ro.miui.ui.version.name", str)) || !TextUtils.isEmpty(SystemProperties.get("ro.miui.ui.version.code", str));
    }

    private int getUiccProvisionStatus(int slotId) {
        QtiUiccCardProvisioner uiccCardProvisioner = QtiUiccCardProvisioner.getInstance();
        if (uiccCardProvisioner != null) {
            return uiccCardProvisioner.getCurrentUiccCardProvisioningStatus(slotId);
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public boolean isSubProvisioned(int subId) {
        boolean isSubIdUsable = SubscriptionManager.isUsableSubIdValue(subId);
        if (!isSubIdUsable) {
            return isSubIdUsable;
        }
        int slotId = getSlotIndex(subId);
        if (!SubscriptionManager.isValidSlotIndex(slotId)) {
            StringBuilder sb = new StringBuilder();
            sb.append(" Invalid slotId ");
            sb.append(slotId);
            sb.append(" or subId = ");
            sb.append(subId);
            loge(sb.toString());
            return false;
        }
        if (getUiccProvisionStatus(slotId) != 1) {
            isSubIdUsable = false;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("isSubProvisioned, state = ");
        sb2.append(isSubIdUsable);
        sb2.append(" subId = ");
        sb2.append(subId);
        loge(sb2.toString());
        return isSubIdUsable;
    }

    public boolean isSMSPromptEnabled() {
        int value = 0;
        try {
            value = Global.getInt(this.mContext.getContentResolver(), "multi_sim_sms_prompt");
        } catch (SettingNotFoundException e) {
            loge("Settings Exception Reading Dual Sim SMS Prompt Values");
        }
        boolean prompt = value != 0;
        if (VDBG) {
            StringBuilder sb = new StringBuilder();
            sb.append("SMS Prompt option:");
            sb.append(prompt);
            logd(sb.toString());
        }
        return prompt;
    }

    public void setSMSPromptEnabled(boolean enabled) {
        enforceModifyPhoneState("setSMSPromptEnabled");
        Global.putInt(this.mContext.getContentResolver(), "multi_sim_sms_prompt", enabled ? 1 : 0);
        StringBuilder sb = new StringBuilder();
        sb.append("setSMSPromptOption to ");
        sb.append(enabled);
        logi(sb.toString());
    }

    private boolean isNonSimAccountFound() {
        Iterator<PhoneAccountHandle> phoneAccounts = this.mTelecomManager.getCallCapablePhoneAccounts().listIterator();
        while (phoneAccounts.hasNext()) {
            if (this.mTelephonyManager.getSubIdForPhoneAccount(this.mTelecomManager.getPhoneAccount((PhoneAccountHandle) phoneAccounts.next())) == -1) {
                logi("Other than SIM account found. ");
                return true;
            }
        }
        logi("Other than SIM account not found ");
        return false;
    }

    private PhoneAccountHandle subscriptionIdToPhoneAccountHandle(int subId) {
        Iterator<PhoneAccountHandle> phoneAccounts = this.mTelecomManager.getCallCapablePhoneAccounts().listIterator();
        while (phoneAccounts.hasNext()) {
            PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) phoneAccounts.next();
            if (subId == this.mTelephonyManager.getSubIdForPhoneAccount(this.mTelecomManager.getPhoneAccount(phoneAccountHandle))) {
                return phoneAccountHandle;
            }
        }
        return null;
    }

    private int getUserPrefDataSubIdFromDB() {
        return Global.getInt(this.mContext.getContentResolver(), SETTING_USER_PREF_DATA_SUB, -1);
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
