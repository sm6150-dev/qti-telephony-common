package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
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
    private static final int DUMMY_SUB_ID_BASE = 2147483643;
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
    public int mCurrentDdsSubId = DUMMY_SUB_ID_BASE;
    private boolean mIsCTClassA = this.mCarrierMode.equals(CARRIER_MODE_CT_CLASS_A);
    /* access modifiers changed from: private */
    public Handler mSubscriptionHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 101) {
                QtiSubscriptionController.this.logd("EVENT_CALL_ENDED");
                if (!QtiSubscriptionController.this.isActiveSubId(QtiSubscriptionController.this.mCurrentDdsSubId) || !QtiSubscriptionController.this.isSubProvisioned(QtiSubscriptionController.this.mCurrentDdsSubId)) {
                    QtiSubscriptionController.this.logd("Current dds sub is inactive");
                    QtiSubscriptionController.this.mCurrentDdsSubId = QtiSubscriptionController.mDefaultFallbackSubId;
                }
                QtiSubscriptionController qtiSubscriptionController = QtiSubscriptionController.this;
                StringBuilder sb = new StringBuilder();
                sb.append("Set DDS to : ");
                sb.append(QtiSubscriptionController.this.mCurrentDdsSubId);
                qtiSubscriptionController.logd(sb.toString());
                QtiSubscriptionController.this.setDefaultDataSubId(QtiSubscriptionController.this.mCurrentDdsSubId);
                QtiCallStateNotifier.getInstance().unregisterForCallEnd(QtiSubscriptionController.this.mSubscriptionHandler);
            }
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
                sNumPhones = TelephonyManager.getDefault().getPhoneCount();
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
        mDefaultPhoneId = 0;
        mDefaultFallbackSubId = DUMMY_SUB_ID_BASE;
        this.mTelecomManager = TelecomManager.from(this.mContext);
        this.mTelephonyManager = TelephonyManager.from(this.mContext);
    }

    private void clearVoiceSubId() {
        List<SubscriptionInfo> records = getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
        StringBuilder sb = new StringBuilder();
        sb.append("[clearVoiceSubId] records: ");
        sb.append(records);
        logdl(sb.toString());
        if (shouldDefaultBeCleared(records, getDefaultVoiceSubId())) {
            logdl("[clearVoiceSubId] clear voice sub id");
            setDefaultVoiceSubId(DUMMY_SUB_ID_BASE);
        }
    }

    public int getSlotIndex(int subId) {
        if (subId == Integer.MAX_VALUE) {
            subId = getDefaultSubId();
        }
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            logd("[getSlotIndex]- subId invalid");
            return -1;
        } else if (subId >= DUMMY_SUB_ID_BASE) {
            return getPhoneIdFromDummySubId(subId);
        } else {
            return QtiSubscriptionController.super.getSlotIndex(subId);
        }
    }

    public int getPhoneId(int subId) {
        if (subId == Integer.MAX_VALUE) {
            subId = getDefaultSubId();
            StringBuilder sb = new StringBuilder();
            sb.append("[getPhoneId] asked for default subId=");
            sb.append(subId);
            logdl(sb.toString());
        }
        if (!SubscriptionManager.isValidSubscriptionId(subId)) {
            logdl("[getPhoneId]- invalid subId return=-1");
            return -1;
        } else if (subId >= DUMMY_SUB_ID_BASE) {
            return getPhoneIdFromDummySubId(subId);
        } else {
            return QtiSubscriptionController.super.getPhoneId(subId);
        }
    }

    private int getPhoneIdFromDummySubId(int subId) {
        return subId - DUMMY_SUB_ID_BASE;
    }

    /* access modifiers changed from: protected */
    public int[] getDummySubIds(int slotIdx) {
        int numSubs = getActiveSubInfoCountMax();
        if (numSubs <= 0) {
            return null;
        }
        int[] dummyValues = new int[numSubs];
        for (int i = 0; i < numSubs; i++) {
            dummyValues[i] = DUMMY_SUB_ID_BASE + slotIdx;
        }
        return dummyValues;
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

    public int addSubInfoRecord(String iccId, int slotId) {
        int retVal = QtiSubscriptionController.super.addSubInfoRecord(iccId, slotId);
        int[] subId = getSubId(slotId);
        if (subId != null && subId.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("addSubInfoRecord: broadcast intent subId[");
            sb.append(slotId);
            sb.append("] = ");
            sb.append(subId[0]);
            logd(sb.toString());
            this.mAddSubscriptionRecordRegistrants.notifyRegistrants(new AsyncResult(null, Integer.valueOf(slotId), null));
            Intent intent = new Intent(ACTION_SUBSCRIPTION_RECORD_ADDED);
            SubscriptionManager.putPhoneIdAndSubIdExtra(intent, slotId, subId[0]);
            this.mContext.sendBroadcast(intent);
        }
        return retVal;
    }

    public void setDefaultDataSubId(int subId) {
        enforceModifyPhoneState("setDefaultDataSubId");
        String flexMapSupportType = SystemProperties.get("persist.vendor.radio.flexmap_type", "nw_mode");
        if (!QtiPhoneSwitcher.isLplusLSupported || !QtiCallStateNotifier.getInstance().isCallInProgress() || QtiDdsSwitchController.isTempDdsSwitchRequired()) {
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

    public void clearDefaultsForInactiveSubIds() {
        enforceModifyPhoneState("clearDefaultsForInactiveSubIds");
        long identity = Binder.clearCallingIdentity();
        try {
            List<SubscriptionInfo> records = getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
            StringBuilder sb = new StringBuilder();
            sb.append("[clearDefaultsForInactiveSubIds] records: ");
            sb.append(records);
            logdl(sb.toString());
            if (shouldDefaultBeCleared(records, getDefaultDataSubId())) {
                logd("[clearDefaultsForInactiveSubIds] clearing default data sub id");
                setDefaultDataSubId(-1);
            }
            if (shouldDefaultBeCleared(records, getDefaultSmsSubId())) {
                logdl("[clearDefaultsForInactiveSubIds] clearing default sms sub id");
                setDefaultSmsSubId(-1);
            }
            if (shouldDefaultBeCleared(records, getDefaultVoiceSubId())) {
                logdl("[clearDefaultsForInactiveSubIds] clearing default voice sub id");
                setDefaultVoiceSubId(DUMMY_SUB_ID_BASE);
            }
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
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
            if (sCi != null && !sCi[i].getRadioState().isAvailable()) {
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
        boolean isApmSimNotPwrDown = SystemProperties.getInt(APM_SIM_NOT_PWDN_PROPERTY, 0) == 1;
        int isAPMOn = Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0);
        if (isAPMOn == 1 && !isApmSimNotPwrDown) {
            StringBuilder sb = new StringBuilder();
            sb.append("isRadioInValidState, isApmSimNotPwrDown = ");
            sb.append(isApmSimNotPwrDown);
            sb.append(", isAPMOn:");
            sb.append(isAPMOn);
            logd(sb.toString());
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
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0133, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void updateUserPreferences() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            r1 = 0
            boolean r2 = r8.isRadioInValidState()     // Catch:{ all -> 0x0144 }
            if (r2 != 0) goto L_0x0010
            java.lang.String r2 = "Radio is in Invalid state, Ignore Updating User Preference!!!"
            r8.logd(r2)     // Catch:{ all -> 0x0144 }
            monitor-exit(r8)
            return
        L_0x0010:
            android.content.Context r2 = r8.mContext     // Catch:{ all -> 0x0144 }
            java.lang.String r2 = r2.getOpPackageName()     // Catch:{ all -> 0x0144 }
            java.util.List r2 = r8.getActiveSubscriptionInfoList(r2)     // Catch:{ all -> 0x0144 }
            if (r2 == 0) goto L_0x0134
            int r3 = r2.size()     // Catch:{ all -> 0x0144 }
            r4 = 1
            if (r3 >= r4) goto L_0x0025
            goto L_0x0134
        L_0x0025:
            java.lang.String r3 = "persist.vendor.radio.aosp_usr_pref_sel"
            r5 = 0
            boolean r3 = android.os.SystemProperties.getBoolean(r3, r5)     // Catch:{ all -> 0x0144 }
            if (r3 == 0) goto L_0x0035
            java.lang.String r3 = "updateUserPreferences: AOSP user preference option enabled "
            r8.logi(r3)     // Catch:{ all -> 0x0144 }
            monitor-exit(r8)
            return
        L_0x0035:
            java.util.Iterator r3 = r2.iterator()     // Catch:{ all -> 0x0144 }
        L_0x0039:
            boolean r6 = r3.hasNext()     // Catch:{ all -> 0x0144 }
            if (r6 == 0) goto L_0x0055
            java.lang.Object r6 = r3.next()     // Catch:{ all -> 0x0144 }
            android.telephony.SubscriptionInfo r6 = (android.telephony.SubscriptionInfo) r6     // Catch:{ all -> 0x0144 }
            int r7 = r6.getSimSlotIndex()     // Catch:{ all -> 0x0144 }
            int r7 = r8.getUiccProvisionStatus(r7)     // Catch:{ all -> 0x0144 }
            if (r7 != r4) goto L_0x0054
            int r1 = r1 + 1
            if (r0 != 0) goto L_0x0054
            r0 = r6
        L_0x0054:
            goto L_0x0039
        L_0x0055:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0144 }
            r3.<init>()     // Catch:{ all -> 0x0144 }
            java.lang.String r6 = "updateUserPreferences:: active sub count = "
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            r3.append(r1)     // Catch:{ all -> 0x0144 }
            java.lang.String r6 = " dds = "
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            int r6 = r8.getDefaultDataSubId()     // Catch:{ all -> 0x0144 }
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            java.lang.String r6 = " voice = "
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            int r6 = r8.getDefaultVoiceSubId()     // Catch:{ all -> 0x0144 }
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            java.lang.String r6 = " sms = "
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            int r6 = r8.getDefaultSmsSubId()     // Catch:{ all -> 0x0144 }
            r3.append(r6)     // Catch:{ all -> 0x0144 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0144 }
            r8.logd(r3)     // Catch:{ all -> 0x0144 }
            if (r1 != r4) goto L_0x0092
            r8.setSMSPromptEnabled(r5)     // Catch:{ all -> 0x0144 }
        L_0x0092:
            if (r0 == 0) goto L_0x0132
            int r3 = r8.getActiveSubInfoCountMax()     // Catch:{ all -> 0x0144 }
            if (r3 != r4) goto L_0x009c
            goto L_0x0132
        L_0x009c:
            int r3 = r0.getSubscriptionId()     // Catch:{ all -> 0x0144 }
            r8.handleDataPreference(r3)     // Catch:{ all -> 0x0144 }
            int r3 = r8.getDefaultSmsSubId()     // Catch:{ all -> 0x0144 }
            boolean r3 = r8.isSubProvisioned(r3)     // Catch:{ all -> 0x0144 }
            if (r3 != 0) goto L_0x00b4
            int r3 = r0.getSubscriptionId()     // Catch:{ all -> 0x0144 }
            r8.setDefaultSmsSubId(r3)     // Catch:{ all -> 0x0144 }
        L_0x00b4:
            int r3 = r8.getDefaultVoiceSubId()     // Catch:{ all -> 0x0144 }
            boolean r3 = r8.isSubProvisioned(r3)     // Catch:{ all -> 0x0144 }
            if (r3 != 0) goto L_0x00c5
            int r3 = r0.getSubscriptionId()     // Catch:{ all -> 0x0144 }
            r8.setDefaultVoiceSubId(r3)     // Catch:{ all -> 0x0144 }
        L_0x00c5:
            boolean r3 = r8.isNonSimAccountFound()     // Catch:{ all -> 0x0144 }
            if (r3 != 0) goto L_0x00ee
            if (r1 != r4) goto L_0x00ee
            int r3 = r0.getSubscriptionId()     // Catch:{ all -> 0x0144 }
            android.telecom.PhoneAccountHandle r4 = r8.subscriptionIdToPhoneAccountHandle(r3)     // Catch:{ all -> 0x0144 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0144 }
            r5.<init>()     // Catch:{ all -> 0x0144 }
            java.lang.String r6 = "set default phoneaccount to  "
            r5.append(r6)     // Catch:{ all -> 0x0144 }
            r5.append(r3)     // Catch:{ all -> 0x0144 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0144 }
            r8.logi(r5)     // Catch:{ all -> 0x0144 }
            android.telecom.TelecomManager r5 = r8.mTelecomManager     // Catch:{ all -> 0x0144 }
            r5.setUserSelectedOutgoingPhoneAccount(r4)     // Catch:{ all -> 0x0144 }
        L_0x00ee:
            int r3 = mDefaultFallbackSubId     // Catch:{ all -> 0x0144 }
            boolean r3 = r8.isSubProvisioned(r3)     // Catch:{ all -> 0x0144 }
            if (r3 != 0) goto L_0x00fd
            int r3 = r0.getSubscriptionId()     // Catch:{ all -> 0x0144 }
            r8.setDefaultFallbackSubId(r3)     // Catch:{ all -> 0x0144 }
        L_0x00fd:
            r8.notifySubscriptionInfoChanged()     // Catch:{ all -> 0x0144 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0144 }
            r3.<init>()     // Catch:{ all -> 0x0144 }
            java.lang.String r4 = "updateUserPreferences: after currentDds = "
            r3.append(r4)     // Catch:{ all -> 0x0144 }
            int r4 = r8.getDefaultDataSubId()     // Catch:{ all -> 0x0144 }
            r3.append(r4)     // Catch:{ all -> 0x0144 }
            java.lang.String r4 = " voice = "
            r3.append(r4)     // Catch:{ all -> 0x0144 }
            int r4 = r8.getDefaultVoiceSubId()     // Catch:{ all -> 0x0144 }
            r3.append(r4)     // Catch:{ all -> 0x0144 }
            java.lang.String r4 = " sms = "
            r3.append(r4)     // Catch:{ all -> 0x0144 }
            int r4 = r8.getDefaultSmsSubId()     // Catch:{ all -> 0x0144 }
            r3.append(r4)     // Catch:{ all -> 0x0144 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0144 }
            r8.logd(r3)     // Catch:{ all -> 0x0144 }
            monitor-exit(r8)
            return
        L_0x0132:
            monitor-exit(r8)
            return
        L_0x0134:
            java.lang.String r3 = "updateUserPreferences: Subscription list is empty"
            r8.logi(r3)     // Catch:{ all -> 0x0144 }
            r8.clearVoiceSubId()     // Catch:{ all -> 0x0144 }
            r3 = 2147483643(0x7ffffffb, float:NaN)
            r8.setDefaultFallbackSubId(r3)     // Catch:{ all -> 0x0144 }
            monitor-exit(r8)
            return
        L_0x0144:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiSubscriptionController.updateUserPreferences():void");
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
        if (!SubscriptionManager.isValidSlotIndex(slotId) || subId >= DUMMY_SUB_ID_BASE) {
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
        boolean z = false;
        int value = 0;
        try {
            value = Global.getInt(this.mContext.getContentResolver(), "multi_sim_sms_prompt");
        } catch (SettingNotFoundException e) {
            loge("Settings Exception Reading Dual Sim SMS Prompt Values");
        }
        if (value != 0) {
            z = true;
        }
        return z;
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
