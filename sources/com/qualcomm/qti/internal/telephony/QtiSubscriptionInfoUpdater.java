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
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SubscriptionInfoUpdater;
import com.android.internal.telephony.uicc.IccCardApplicationStatus.AppType;
import com.android.internal.telephony.uicc.IccCardStatus.CardState;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;

public class QtiSubscriptionInfoUpdater extends SubscriptionInfoUpdater {
    private static final String ACTION_ALL_ICC_QUERY_DONE = "org.codeaurora.intent.action.ALL_ICC_QUERY_DONE";
    private static final String CARRIER_MODE_CT_CLASS_A = "ct_class_a";
    private static final String CT_IIN = "898603,898611,8985231,8985302,8985307";
    private static final int EVENT_ADD_SUBINFO_RECORD = 100;
    private static final String ICCID_STRING_FOR_NO_SIM = "";
    private static final String LOG_TAG = "QtiSubscriptionInfoUpdater";
    private static final String ROAMING_SETTINGS_CONFIG = "persist.vendor.radio.roamingsettings";
    private static int mNumPhones;
    private static Context sContext = null;
    private static QtiSubscriptionInfoUpdater sInstance = null;
    private String mCarrierMode = SystemProperties.get("persist.vendor.radio.carrier_mode", "default");
    private boolean mIsCTClassA = this.mCarrierMode.equals(CARRIER_MODE_CT_CLASS_A);
    private boolean[] mIsRecordUpdateRequired;
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
        mNumPhones = ((TelephonyManager) context.getSystemService("phone")).getPhoneCount();
        this.mIsRecordUpdateRequired = new boolean[mNumPhones];
        for (int index = 0; index < mNumPhones; index++) {
            this.mIsRecordUpdateRequired[index] = false;
        }
    }

    public void handleMessage(Message msg) {
        StringBuilder sb = new StringBuilder();
        sb.append(" handleMessage: EVENT:  ");
        sb.append(msg.what);
        Rlog.d(LOG_TAG, sb.toString());
        if (msg.what != 100) {
            QtiSubscriptionInfoUpdater.super.handleMessage(msg);
        } else {
            handleAddSubInfoRecordEvent(msg.arg1, (String) msg.obj);
        }
    }

    /* access modifiers changed from: 0000 */
    public void addSubInfoRecord(int slotId, String iccId) {
        if (iccId != null && slotId >= 0 && slotId < mNumPhones) {
            sendMessage(obtainMessage(100, slotId, -1, iccId));
        }
    }

    private synchronized void handleAddSubInfoRecordEvent(int slotId, String iccId) {
        if (mIccId[slotId] == null || mIccId[slotId].equals(ICCID_STRING_FOR_NO_SIM)) {
            if (mIccId[slotId] == null || mIccId[slotId] == ICCID_STRING_FOR_NO_SIM) {
                this.mIsRecordUpdateRequired[slotId] = true;
            }
            mIccId[slotId] = iccId;
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append(" slotId = ");
            sb.append(slotId);
            sb.append(" needEnableRoamingSettings = ");
            sb.append(this.needEnableRoamingSettings);
            Rlog.d(str, sb.toString());
            if (!TextUtils.isEmpty(iccId) && isCtCard(iccId) && SystemProperties.getBoolean(ROAMING_SETTINGS_CONFIG, false)) {
                setRoamingSettingsState(sContext, true);
                this.needEnableRoamingSettings = true;
            } else if (!this.needEnableRoamingSettings) {
                setRoamingSettingsState(sContext, false);
            }
            if (this.mIsCTClassA && slotId == 0) {
                checkUiccCard(iccId);
            }
            updateSubscriptionInfoByIccId(slotId, true);
        }
    }

    private void sendBroadCastToApp() {
        Intent intent = new Intent();
        intent.setClassName("com.qualcomm.qti.networksetting", "com.qualcomm.qti.networksetting.SimAlertNotification");
        StringBuilder sb = new StringBuilder();
        sb.append("Sending broadcast to NetworkSetting");
        sb.append(intent);
        Rlog.d(LOG_TAG, sb.toString());
        sContext.sendBroadcast(intent);
    }

    private void checkUiccCard(String iccId) {
        boolean isCtCard = isCtCard(iccId);
        String str = LOG_TAG;
        if (isCtCard) {
            boolean hasUiccApp = false;
            UiccCard uiccCard = UiccController.getInstance().getUiccCard(0);
            if (uiccCard != null && uiccCard.getCardState() == CardState.CARDSTATE_PRESENT) {
                if (uiccCard.isApplicationOnIcc(AppType.APPTYPE_USIM) && (uiccCard.isApplicationOnIcc(AppType.APPTYPE_CSIM) || uiccCard.isApplicationOnIcc(AppType.APPTYPE_RUIM))) {
                    hasUiccApp = true;
                }
                if (!hasUiccApp) {
                    Rlog.d(str, "This is a 3G CT card.");
                    sendBroadCastToApp();
                    return;
                }
                return;
            }
            return;
        }
        Rlog.d(str, "This is a non-CT card.");
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
        String str = LOG_TAG;
        if (context == null) {
            Rlog.d(str, "setRoamingSettingsState, context null");
            return;
        }
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            Rlog.d(str, "setRoamingSettingsState, PackageManager null");
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
    public void handleSimAbsent(int slotId, int absentAndInactive) {
        if (mIccId[slotId] == null || mIccId[slotId] != ICCID_STRING_FOR_NO_SIM) {
            this.mIsRecordUpdateRequired[slotId] = true;
        }
        QtiSubscriptionInfoUpdater.super.handleSimAbsent(slotId, absentAndInactive);
    }

    /* access modifiers changed from: protected */
    public void handleSimError(int slotId) {
        if (mIccId[slotId] == null || mIccId[slotId] != ICCID_STRING_FOR_NO_SIM) {
            this.mIsRecordUpdateRequired[slotId] = true;
        }
        QtiSubscriptionInfoUpdater.super.handleSimError(slotId);
    }

    /* access modifiers changed from: protected */
    public synchronized void updateSubscriptionInfoByIccId(int slotIndex, boolean updateEmbeddedSubs) {
        if (this.mIsRecordUpdateRequired[slotIndex]) {
            QtiSubscriptionInfoUpdater.super.updateSubscriptionInfoByIccId(slotIndex, updateEmbeddedSubs);
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("SIM state changed, Updating user preference ");
            sb.append(slotIndex);
            Rlog.d(str, sb.toString());
            if (QtiUiccCardProvisioner.getInstance().isAllCardProvisionInfoReceived() && isAllIccIdQueryDone()) {
                QtiSubscriptionController.getInstance().updateUserPreferences();
            }
            this.mIsRecordUpdateRequired[slotIndex] = false;
        } else {
            String str2 = LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Ignoring subscription update event ");
            sb2.append(slotIndex);
            Rlog.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updateUserPreferences() {
        Rlog.d(LOG_TAG, " calling updateUserPreferences ");
        if (isAllIccIdQueryDone()) {
            QtiSubscriptionController.getInstance().updateUserPreferences();
        }
    }
}
