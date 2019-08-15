package com.qualcomm.qti.internal.telephony;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.radio.V1_0.RadioAccessFamily;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.SubscriptionInfoUpdater;
import com.android.internal.telephony.uicc.IccCardApplicationStatus.AppType;
import com.android.internal.telephony.uicc.IccCardStatus.CardState;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import java.util.List;

public class QtiSubscriptionInfoUpdater extends SubscriptionInfoUpdater {
    private static final String ACTION_ALL_ICC_QUERY_DONE = "org.codeaurora.intent.action.ALL_ICC_QUERY_DONE";
    private static final String CARRIER_MODE_CT_CLASS_A = "ct_class_a";
    private static final String CT_IIN = "898603,898611,8985231,8985302,8985307";
    private static final int EVENT_ADD_SUBINFO_RECORD = 100;
    private static final int EVENT_UPDATE_NV_RECORD = 101;
    private static final String ICCID_STRING_FOR_NO_SIM = "";
    private static final String ICCID_STRING_FOR_NV = "DUMMY_NV_ID";
    private static final String LOG_TAG = "QtiSubscriptionInfoUpdater";
    private static final String ROAMING_SETTINGS_CONFIG = "persist.vendor.radio.roamingsettings";
    private static final int mNumPhones = TelephonyManager.getDefault().getPhoneCount();
    private static Context sContext = null;
    private static QtiSubscriptionInfoUpdater sInstance = null;
    private boolean isNVSubAvailable = false;
    private String mCarrierMode = SystemProperties.get("persist.vendor.radio.carrier_mode", "default");
    private boolean mIsCTClassA = this.mCarrierMode.equals(CARRIER_MODE_CT_CLASS_A);
    private boolean[] mIsRecordUpdateRequired = new boolean[mNumPhones];
    private boolean needEnableRoamingSettings = false;

    static QtiSubscriptionInfoUpdater init(Looper looper, Context context, Phone[] phone, CommandsInterface[] ci) {
        QtiSubscriptionInfoUpdater qtiSubscriptionInfoUpdater;
        synchronized (QtiSubscriptionInfoUpdater.class) {
            if (sInstance == null) {
                sInstance = new QtiSubscriptionInfoUpdater(looper, context, phone, ci);
            } else {
                String str = LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("init() called multiple times!  sInstance = ");
                sb.append(sInstance);
                Log.wtf(str, sb.toString());
            }
            qtiSubscriptionInfoUpdater = sInstance;
        }
        return qtiSubscriptionInfoUpdater;
    }

    public static QtiSubscriptionInfoUpdater getInstance() {
        if (sInstance == null) {
            Log.wtf(LOG_TAG, "getInstance null");
        }
        return sInstance;
    }

    private QtiSubscriptionInfoUpdater(Looper looper, Context context, Phone[] phone, CommandsInterface[] ci) {
        super(looper, context, phone, ci);
        sContext = context;
        for (int index = 0; index < mNumPhones; index++) {
            this.mIsRecordUpdateRequired[index] = false;
        }
    }

    public void handleMessage(Message msg) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(" handleMessage: EVENT:  ");
        sb.append(msg.what);
        Rlog.d(str, sb.toString());
        switch (msg.what) {
            case 100:
                handleAddSubInfoRecordEvent(msg.arg1, (String) msg.obj);
                return;
            case 101:
                handleUpdateNVRecord(msg.arg1);
                return;
            default:
                QtiSubscriptionInfoUpdater.super.handleMessage(msg);
                return;
        }
    }

    public void updateNVRecord(boolean isNVReady, int slotId) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("updateNVRecord, isNVReady: ");
        sb.append(isNVReady);
        sb.append(" slotId: ");
        sb.append(slotId);
        Rlog.d(str, sb.toString());
        this.isNVSubAvailable = isNVReady;
        sendMessage(obtainMessage(101, slotId, -1, null));
    }

    public void handleUpdateNVRecord(int slotId) {
        if (this.isNVSubAvailable) {
            this.mIsRecordUpdateRequired[slotId] = true;
            handleAddSubInfoRecordEvent(slotId, ICCID_STRING_FOR_NV);
            return;
        }
        List<SubscriptionInfo> subInfo = SubscriptionController.getInstance().getSubInfoUsingSlotIndexPrivileged(slotId, false);
        if (subInfo != null && ((SubscriptionInfo) subInfo.get(0)).getIccId().equals(ICCID_STRING_FOR_NV)) {
            handleSimAbsent(slotId);
        }
    }

    /* access modifiers changed from: 0000 */
    public void addSubInfoRecord(int slotId, String iccId) {
        if (iccId != null && slotId >= 0 && slotId < mNumPhones) {
            sendMessage(obtainMessage(100, slotId, -1, iccId));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0085, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void handleAddSubInfoRecordEvent(int r5, java.lang.String r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String[] r0 = mIccId     // Catch:{ all -> 0x0086 }
            r0 = r0[r5]     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x0015
            java.lang.String[] r0 = mIccId     // Catch:{ all -> 0x0086 }
            r0 = r0[r5]     // Catch:{ all -> 0x0086 }
            java.lang.String r1 = ""
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x0086 }
            if (r0 != 0) goto L_0x0015
            monitor-exit(r4)
            return
        L_0x0015:
            java.lang.String[] r0 = mIccId     // Catch:{ all -> 0x0086 }
            r0 = r0[r5]     // Catch:{ all -> 0x0086 }
            r1 = 1
            if (r0 == 0) goto L_0x0024
            java.lang.String[] r0 = mIccId     // Catch:{ all -> 0x0086 }
            r0 = r0[r5]     // Catch:{ all -> 0x0086 }
            java.lang.String r2 = ""
            if (r0 != r2) goto L_0x0028
        L_0x0024:
            boolean[] r0 = r4.mIsRecordUpdateRequired     // Catch:{ all -> 0x0086 }
            r0[r5] = r1     // Catch:{ all -> 0x0086 }
        L_0x0028:
            java.lang.String[] r0 = mIccId     // Catch:{ all -> 0x0086 }
            r0[r5] = r6     // Catch:{ all -> 0x0086 }
            java.lang.String r0 = "QtiSubscriptionInfoUpdater"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0086 }
            r2.<init>()     // Catch:{ all -> 0x0086 }
            java.lang.String r3 = " slotId = "
            r2.append(r3)     // Catch:{ all -> 0x0086 }
            r2.append(r5)     // Catch:{ all -> 0x0086 }
            java.lang.String r3 = " needEnableRoamingSettings = "
            r2.append(r3)     // Catch:{ all -> 0x0086 }
            boolean r3 = r4.needEnableRoamingSettings     // Catch:{ all -> 0x0086 }
            r2.append(r3)     // Catch:{ all -> 0x0086 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0086 }
            android.telephony.Rlog.d(r0, r2)     // Catch:{ all -> 0x0086 }
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x0086 }
            r2 = 0
            if (r0 != 0) goto L_0x0069
            boolean r0 = r4.isCtCard(r6)     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x0069
            java.lang.String r0 = "persist.vendor.radio.roamingsettings"
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r2)     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x0069
            android.content.Context r0 = sContext     // Catch:{ all -> 0x0086 }
            r4.setRoamingSettingsState(r0, r1)     // Catch:{ all -> 0x0086 }
            r4.needEnableRoamingSettings = r1     // Catch:{ all -> 0x0086 }
            goto L_0x0072
        L_0x0069:
            boolean r0 = r4.needEnableRoamingSettings     // Catch:{ all -> 0x0086 }
            if (r0 != 0) goto L_0x0072
            android.content.Context r0 = sContext     // Catch:{ all -> 0x0086 }
            r4.setRoamingSettingsState(r0, r2)     // Catch:{ all -> 0x0086 }
        L_0x0072:
            boolean r0 = r4.mIsCTClassA     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x007b
            if (r5 != 0) goto L_0x007b
            r4.checkUiccCard(r6)     // Catch:{ all -> 0x0086 }
        L_0x007b:
            boolean r0 = r4.isAllIccIdQueryDone()     // Catch:{ all -> 0x0086 }
            if (r0 == 0) goto L_0x0084
            r4.updateSubscriptionInfoByIccId()     // Catch:{ all -> 0x0086 }
        L_0x0084:
            monitor-exit(r4)
            return
        L_0x0086:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiSubscriptionInfoUpdater.handleAddSubInfoRecordEvent(int, java.lang.String):void");
    }

    private void sendBroadCastToApp() {
        Intent intent = new Intent();
        intent.setClassName("com.qualcomm.qti.networksetting", "com.qualcomm.qti.networksetting.SimAlertNotification");
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Sending broadcast to NetworkSetting");
        sb.append(intent);
        Rlog.d(str, sb.toString());
        sContext.sendBroadcast(intent);
    }

    private void checkUiccCard(String iccId) {
        if (isCtCard(iccId)) {
            boolean hasUiccApp = false;
            UiccCard uiccCard = UiccController.getInstance().getUiccCard(0);
            if (uiccCard != null && uiccCard.getCardState() == CardState.CARDSTATE_PRESENT) {
                if (uiccCard.isApplicationOnIcc(AppType.APPTYPE_USIM) && (uiccCard.isApplicationOnIcc(AppType.APPTYPE_CSIM) || uiccCard.isApplicationOnIcc(AppType.APPTYPE_RUIM))) {
                    hasUiccApp = true;
                }
                if (!hasUiccApp) {
                    Rlog.d(LOG_TAG, "This is a 3G CT card.");
                    sendBroadCastToApp();
                    return;
                }
                return;
            }
            return;
        }
        Rlog.d(LOG_TAG, "This is a non-CT card.");
        sendBroadCastToApp();
    }

    private boolean isCtCard(String iccId) {
        for (String iin : CT_IIN.split(",")) {
            if (iccId.startsWith(iin)) {
                return true;
            }
        }
        return false;
    }

    private void setRoamingSettingsState(Context context, boolean install) {
        int state;
        if (context == null) {
            Rlog.d(LOG_TAG, "setRoamingSettingsState, context null");
            return;
        }
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            Rlog.d(LOG_TAG, "setRoamingSettingsState, PackageManager null");
            return;
        }
        String packageName = "com.qualcomm.qti.roamingsettings";
        ComponentName cn = new ComponentName(packageName, "com.qualcomm.qti.roamingsettings.RoamingSettingsActivity");
        if (install) {
            state = 1;
        } else {
            state = 2;
        }
        for (PackageInfo pi : pm.getInstalledPackages(RadioAccessFamily.EHRPD)) {
            if (!TextUtils.isEmpty(pi.packageName) && packageName.equals(pi.packageName)) {
                String str = LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("setRoamingSettings state = ");
                sb.append(state);
                Rlog.d(str, sb.toString());
                pm.setComponentEnabledSetting(cn, state, 0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void handleSimLoaded(int slotId) {
        if (mIccId[slotId] == null || mIccId[slotId] == ICCID_STRING_FOR_NO_SIM) {
            this.mIsRecordUpdateRequired[slotId] = true;
        }
        QtiSubscriptionInfoUpdater.super.handleSimLoaded(slotId);
    }

    /* access modifiers changed from: protected */
    public void handleSimLocked(int slotId, String reason) {
        if (mIccId[slotId] == null || mIccId[slotId] == ICCID_STRING_FOR_NO_SIM) {
            this.mIsRecordUpdateRequired[slotId] = true;
        }
        QtiSubscriptionInfoUpdater.super.handleSimLocked(slotId, reason);
    }

    /* access modifiers changed from: protected */
    public void handleSimAbsent(int slotId) {
        if (!this.isNVSubAvailable) {
            if (mIccId[slotId] == null || mIccId[slotId] != ICCID_STRING_FOR_NO_SIM) {
                this.mIsRecordUpdateRequired[slotId] = true;
            }
            QtiSubscriptionInfoUpdater.super.handleSimAbsent(slotId);
        }
    }

    /* access modifiers changed from: protected */
    public void handleSimError(int slotId) {
        if (!this.isNVSubAvailable) {
            if (mIccId[slotId] == null || mIccId[slotId] != ICCID_STRING_FOR_NO_SIM) {
                this.mIsRecordUpdateRequired[slotId] = true;
            }
            QtiSubscriptionInfoUpdater.super.handleSimError(slotId);
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void updateSubscriptionInfoByIccId() {
        boolean isUpdateRequired = false;
        int index = 0;
        while (true) {
            if (index >= mNumPhones) {
                break;
            } else if (this.mIsRecordUpdateRequired[index]) {
                isUpdateRequired = true;
                break;
            } else {
                index++;
            }
        }
        if (isUpdateRequired) {
            QtiSubscriptionInfoUpdater.super.updateSubscriptionInfoByIccId();
            Rlog.d(LOG_TAG, "SIM state changed, Updating user preference ");
            if (QtiUiccCardProvisioner.getInstance().isAllCardProvisionInfoReceived()) {
                QtiSubscriptionController.getInstance().updateUserPreferences();
            }
            for (int index2 = 0; index2 < mNumPhones; index2++) {
                this.mIsRecordUpdateRequired[index2] = false;
            }
        } else {
            Rlog.d(LOG_TAG, "Ignoring subscription update event");
        }
    }
}
