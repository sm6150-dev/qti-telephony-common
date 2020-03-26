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
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
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
                Log.wtf(LOG_TAG, "init() called multiple times!  sInstance = " + sInstance);
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
        Rlog.d(LOG_TAG, " handleMessage: EVENT:  " + msg.what);
        if (msg.what != 100) {
            QtiSubscriptionInfoUpdater.super.handleMessage(msg);
        } else {
            handleAddSubInfoRecordEvent(msg.arg1, (String) msg.obj);
        }
    }

    /* access modifiers changed from: package-private */
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
            Rlog.d(LOG_TAG, " slotId = " + slotId + " needEnableRoamingSettings = " + this.needEnableRoamingSettings);
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
        Rlog.d(LOG_TAG, "Sending broadcast to NetworkSetting" + intent);
        sContext.sendBroadcast(intent);
    }

    private void checkUiccCard(String iccId) {
        if (isCtCard(iccId)) {
            boolean hasUiccApp = false;
            UiccCard uiccCard = UiccController.getInstance().getUiccCard(0);
            if (uiccCard != null && uiccCard.getCardState() == IccCardStatus.CardState.CARDSTATE_PRESENT) {
                if (uiccCard.isApplicationOnIcc(IccCardApplicationStatus.AppType.APPTYPE_USIM) && (uiccCard.isApplicationOnIcc(IccCardApplicationStatus.AppType.APPTYPE_CSIM) || uiccCard.isApplicationOnIcc(IccCardApplicationStatus.AppType.APPTYPE_RUIM))) {
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
        ComponentName cn = new ComponentName("com.qualcomm.qti.roamingsettings", "com.qualcomm.qti.roamingsettings.RoamingSettingsActivity");
        if (install) {
            state = 1;
        } else {
            state = 2;
        }
        for (PackageInfo pi : pm.getInstalledPackages(RadioAccessFamily.EHRPD)) {
            if (!TextUtils.isEmpty(pi.packageName) && "com.qualcomm.qti.roamingsettings".equals(pi.packageName)) {
                Rlog.d(LOG_TAG, "setRoamingSettings state = " + state);
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
            Rlog.d(LOG_TAG, "SIM state changed, Updating user preference " + slotIndex);
            if (QtiUiccCardProvisioner.getInstance().isAllCardProvisionInfoReceived() && isAllIccIdQueryDone()) {
                QtiSubscriptionController.getInstance().updateUserPreferences();
            }
            this.mIsRecordUpdateRequired[slotIndex] = false;
        } else {
            Rlog.d(LOG_TAG, "Ignoring subscription update event " + slotIndex);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void updateUserPreferences() {
        Rlog.d(LOG_TAG, " calling updateUserPreferences ");
        if (isAllIccIdQueryDone()) {
            QtiSubscriptionController.getInstance().updateUserPreferences();
        }
    }
}
