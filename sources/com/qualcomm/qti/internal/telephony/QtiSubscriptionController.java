package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
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
                int unused = r0.mCurrentDdsSubId = r1
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
                Log.wtf(LOG_TAG, "init() called multiple times!  sInstance = " + sInstance);
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
        logd("addSubInfoRecord: broadcast intent subId[" + slotIndex + "]");
        return addSubInfo(iccId, (String) null, slotIndex, 0);
    }

    public int addSubInfo(String uniqueId, String displayName, int slotIndex, int subscriptionType) {
        int retVal = QtiSubscriptionController.super.addSubInfo(uniqueId, displayName, slotIndex, subscriptionType);
        int[] subId = getSubId(slotIndex);
        if (subId != null && subId.length > 0) {
            logd("addSubInfoRecord: broadcast intent subId[" + slotIndex + "] = " + subId[0]);
            this.mAddSubscriptionRecordRegistrants.notifyRegistrants(new AsyncResult((Object) null, Integer.valueOf(slotIndex), (Throwable) null));
            Intent intent = new Intent(ACTION_SUBSCRIPTION_RECORD_ADDED);
            SubscriptionManager.putPhoneIdAndSubIdExtra(intent, slotIndex, subId[0]);
            this.mContext.sendBroadcast(intent);
        }
        return retVal;
    }

    public void setDefaultDataSubId(int subId) {
        enforceModifyPhoneState("setDefaultDataSubId");
        String flexMapSupportType = SystemProperties.get("persist.vendor.radio.flexmap_type", "nw_mode");
        if (QtiPhoneSwitcher.isLplusLSupported && QtiCallStateNotifier.getInstance().isCallInProgress()) {
            logd("Active call, cannot set Dds to : " + subId);
            this.mCurrentDdsSubId = subId;
            QtiCallStateNotifier.getInstance().registerForCallEnd(this.mSubscriptionHandler, 101, (Object) null);
        } else if (!SubscriptionManager.isValidSubscriptionId(subId) || !flexMapSupportType.equals("dds")) {
            updateAllDataConnectionTrackers();
            Settings.Global.putInt(this.mContext.getContentResolver(), "multi_sim_data_call", subId);
            broadcastDefaultDataSubIdChanged(subId);
        } else {
            QtiRadioCapabilityController radioCapController = QtiRadioCapabilityController.getInstance();
            if (radioCapController.isBothPhonesMappedToSameStack()) {
                radioCapController.initNormalMappingRequest();
                logd(" setDefaultDataSubId init normal mapping: " + subId);
            }
            QtiSubscriptionController.super.setDefaultDataSubId(subId);
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldDefaultBeCleared(List<SubscriptionInfo> records, int subId) {
        logdl("[shouldDefaultBeCleared: subId] " + subId);
        if (records == null) {
            logdl("[shouldDefaultBeCleared] return true no records subId=" + subId);
            return true;
        } else if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            logdl("[shouldDefaultBeCleared] return false only one subId, subId=" + subId);
            return false;
        } else {
            for (SubscriptionInfo record : records) {
                int id = record.getSubscriptionId();
                logdl("[shouldDefaultBeCleared] Record.id: " + id);
                if (id == subId) {
                    logdl("[shouldDefaultBeCleared] return false subId is active, subId=" + subId);
                    return false;
                }
            }
            if (getUiccProvisionStatus(getSlotIndex(subId)) == 1) {
                logdl("[shouldDefaultBeCleared] return false subId is provisioned, subId=" + subId);
                return false;
            }
            logdl("[shouldDefaultBeCleared] return true not active subId=" + subId);
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
            loge("Exception: " + ex);
        }
        boolean isApmSimNotPwrDown = simNotPwrDown == 1;
        int isAPMOn = Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0);
        if (isAPMOn == 1 && !isApmSimNotPwrDown) {
            logd("isRadioInValidState, isApmSimNotPwrDown = " + isApmSimNotPwrDown + ", isAPMOn:" + isAPMOn);
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

    /* access modifiers changed from: package-private */
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
            logd("updateUserPreferences:: active sub count = " + activeCount + " dds = " + getDefaultDataSubId() + " voice = " + getDefaultVoiceSubId() + " sms = " + getDefaultSmsSubId());
            if (activeCount == 1) {
                setSMSPromptEnabled(false);
            }
            if (mNextActivatedSub != null && getActiveSubInfoCountMax() != 1) {
                handleDataPreference(mNextActivatedSub.getSubscriptionId());
                if (!isSubProvisioned(getDefaultSmsSubId())) {
                    setDefaultSmsSubId(mNextActivatedSub.getSubscriptionId());
                }
                logd("updateUserPreferences: isMiuiRom = " + isMiuiRom());
                if (!isMiuiRom()) {
                    if (!isSubProvisioned(getDefaultVoiceSubId())) {
                        setDefaultVoiceSubId(mNextActivatedSub.getSubscriptionId());
                    }
                    if (!isNonSimAccountFound() && activeCount == 1) {
                        int subId = mNextActivatedSub.getSubscriptionId();
                        PhoneAccountHandle phoneAccountHandle = subscriptionIdToPhoneAccountHandle(subId);
                        logi("set default phoneaccount to  " + subId);
                        this.mTelecomManager.setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
                    }
                }
                if (!isSubProvisioned(mDefaultFallbackSubId)) {
                    setDefaultFallbackSubId(mNextActivatedSub.getSubscriptionId(), 0);
                }
                notifySubscriptionInfoChanged();
                logd("updateUserPreferences: after currentDds = " + getDefaultDataSubId() + " voice = " + getDefaultVoiceSubId() + " sms = " + getDefaultSmsSubId());
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
            logd("havePrefSub = " + userPrefSubValid + " user pref subId = " + userPrefDataSubId + " current dds " + currentDataSubId + " next active subId " + nextActiveSubId);
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
        return !TextUtils.isEmpty(SystemProperties.get("ro.miui.ui.version.name", "")) || !TextUtils.isEmpty(SystemProperties.get("ro.miui.ui.version.code", ""));
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
            loge(" Invalid slotId " + slotId + " or subId = " + subId);
            return false;
        }
        if (getUiccProvisionStatus(slotId) != 1) {
            isSubIdUsable = false;
        }
        loge("isSubProvisioned, state = " + isSubIdUsable + " subId = " + subId);
        return isSubIdUsable;
    }

    public boolean isSMSPromptEnabled() {
        int value = 0;
        try {
            value = Settings.Global.getInt(this.mContext.getContentResolver(), "multi_sim_sms_prompt");
        } catch (Settings.SettingNotFoundException e) {
            loge("Settings Exception Reading Dual Sim SMS Prompt Values");
        }
        boolean prompt = value != 0;
        if (VDBG) {
            logd("SMS Prompt option:" + prompt);
        }
        return prompt;
    }

    public void setSMSPromptEnabled(boolean enabled) {
        enforceModifyPhoneState("setSMSPromptEnabled");
        Settings.Global.putInt(this.mContext.getContentResolver(), "multi_sim_sms_prompt", (int) enabled);
        logi("setSMSPromptOption to " + enabled);
    }

    private boolean isNonSimAccountFound() {
        Iterator<PhoneAccountHandle> phoneAccounts = this.mTelecomManager.getCallCapablePhoneAccounts().listIterator();
        while (phoneAccounts.hasNext()) {
            if (this.mTelephonyManager.getSubIdForPhoneAccount(this.mTelecomManager.getPhoneAccount(phoneAccounts.next())) == -1) {
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
            PhoneAccountHandle phoneAccountHandle = phoneAccounts.next();
            if (subId == this.mTelephonyManager.getSubIdForPhoneAccount(this.mTelecomManager.getPhoneAccount(phoneAccountHandle))) {
                return phoneAccountHandle;
            }
        }
        return null;
    }

    private int getUserPrefDataSubIdFromDB() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), SETTING_USER_PREF_DATA_SUB, -1);
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
