package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.cdma.CdmaSubscriptionSourceManager;
import com.qualcomm.qti.internal.telephony.primarycard.SubsidyLockSettingsObserver;

public class QtiGsmCdmaPhone extends GsmCdmaPhone {
    private static final int EVENT_OEM_HOOK_SERVICE_READY = 46;
    private static final String LOG_TAG = "QtiGsmCdmaPhone";
    private static final int PROP_EVENT_START = 45;
    private static int READY = 1;
    private static final int mNumPhones = TelephonyManager.getDefault().getPhoneCount();
    CdmaSubscriptionSourceManager mCdmaNVSSM;
    int mCdmaNVSubscriptionSource;
    private boolean mIsPhoneReadyPending;
    private boolean mIsPhoneReadySent;
    private BaseRilInterface mQtiRilInterface;

    public QtiGsmCdmaPhone(Context context, CommandsInterface ci, PhoneNotifier notifier, int phoneId, int precisePhoneType, TelephonyComponentFactory telephonyComponentFactory) {
        this(context, ci, notifier, false, phoneId, precisePhoneType, telephonyComponentFactory);
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [android.os.Handler, com.qualcomm.qti.internal.telephony.QtiGsmCdmaPhone] */
    public QtiGsmCdmaPhone(Context context, CommandsInterface ci, PhoneNotifier notifier, boolean unitTestMode, int phoneId, int precisePhoneType, TelephonyComponentFactory telephonyComponentFactory) {
        super(context, ci, notifier, unitTestMode, phoneId, precisePhoneType, telephonyComponentFactory);
        this.mIsPhoneReadySent = false;
        this.mIsPhoneReadyPending = false;
        this.mCdmaNVSubscriptionSource = -1;
        Rlog.d(LOG_TAG, "Constructor");
        this.mQtiRilInterface = getQtiRilInterface();
        this.mQtiRilInterface.registerForServiceReadyEvent(this, 46, null);
        this.mCdmaNVSSM = CdmaSubscriptionSourceManager.getInstance(context, this.mCi, null, -1, null);
    }

    private void handleCdmaNVSubscriptionSource(int newSubscriptionSource) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(" mCdmaNVSubscriptionSource:  ");
        sb.append(this.mCdmaNVSubscriptionSource);
        sb.append(" newSubscriptionSource:  ");
        sb.append(newSubscriptionSource);
        Rlog.d(str, sb.toString());
        if (newSubscriptionSource != this.mCdmaNVSubscriptionSource) {
            this.mCdmaNVSubscriptionSource = newSubscriptionSource;
            handleNVChange(newSubscriptionSource);
        }
    }

    private void handleNVChange(int newSubscriptionSource) {
        boolean isNVReady = true;
        if (newSubscriptionSource != 1) {
            isNVReady = false;
        }
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(" handleNVChanged: isNVReady: ");
        sb.append(isNVReady);
        Rlog.e(str, sb.toString());
        QtiSubscriptionInfoUpdater.getInstance().updateNVRecord(isNVReady, this.mPhoneId);
    }

    public void setPreferredNetworkType(int networkType, Message response) {
        QtiRadioCapabilityController radioCapController = QtiRadioCapabilityController.getInstance();
        if (radioCapController != null) {
            radioCapController.setPreferredNetworkType(getPhoneId(), networkType, response);
        } else {
            Rlog.e(LOG_TAG, " Error: Received null QtiRadioCapabilityController instante ");
        }
    }

    private void updatePhoneReady(int phoneId) {
        if (!this.mIsPhoneReadySent && SystemProperties.getInt("persist.vendor.radio.poweron_opt", 0) == 1) {
            if (!this.mQtiRilInterface.isServiceReady()) {
                this.mIsPhoneReadyPending = true;
                return;
            }
            logd("Sending Phone Ready to RIL.");
            this.mQtiRilInterface.sendPhoneStatus(READY, phoneId);
            this.mIsPhoneReadySent = true;
            this.mIsPhoneReadyPending = false;
        }
    }

    /* access modifiers changed from: protected */
    public void phoneObjectUpdater(int newVoiceTech) {
        QtiGsmCdmaPhone.super.phoneObjectUpdater(newVoiceTech);
        updatePhoneReady(this.mPhoneId);
    }

    public void radioCapabilityUpdated(RadioCapability rc) {
        this.mRadioCapability.set(rc);
        QtiRadioCapabilityController radioCapController = QtiRadioCapabilityController.getInstance();
        if (radioCapController != null) {
            radioCapController.radioCapabilityUpdated(getPhoneId(), rc);
        }
    }

    public boolean getCallForwardingIndicator() {
        if (!isCurrentSubValid()) {
            return false;
        }
        return QtiGsmCdmaPhone.super.getCallForwardingIndicator();
    }

    private boolean isCurrentSubValid() {
        int provisionStatus;
        SubscriptionManager subscriptionManager = SubscriptionManager.from(this.mContext);
        try {
            provisionStatus = QtiUiccCardProvisioner.getInstance().getCurrentUiccCardProvisioningStatus(this.mPhoneId);
        } catch (NullPointerException e) {
            provisionStatus = 0;
        }
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("ProvisionStatus: ");
        sb.append(provisionStatus);
        sb.append(" phone id:");
        sb.append(this.mPhoneId);
        Rlog.d(str, sb.toString());
        return subscriptionManager.isActiveSubId(getSubId()) && provisionStatus == 1;
    }

    public boolean setLocalCallHold(boolean enable) {
        if (this.mQtiRilInterface.isServiceReady()) {
            return this.mQtiRilInterface.setLocalCallHold(this.mPhoneId, enable);
        }
        Rlog.e(LOG_TAG, "mQtiRilInterface is not ready yet");
        return false;
    }

    public void fetchIMEI() {
        Rlog.d(LOG_TAG, "fetching device id");
        this.mCi.getDeviceIdentity(obtainMessage(21));
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.android.internal.telephony.GsmCdmaPhone, android.os.Handler, com.qualcomm.qti.internal.telephony.QtiGsmCdmaPhone] */
    public void dispose() {
        this.mQtiRilInterface.unRegisterForServiceReadyEvent(this);
        this.mQtiRilInterface = null;
        QtiGsmCdmaPhone.super.dispose();
    }

    public void handleMessage(Message msg) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("handleMessage: Event: ");
        sb.append(msg.what);
        Rlog.d(str, sb.toString());
        int i = msg.what;
        if (i == 1) {
            this.mIsPhoneReadySent = false;
            updatePhoneReady(this.mPhoneId);
            QtiGsmCdmaPhone.super.handleMessage(msg);
        } else if (i != 3) {
            if (i != 5) {
                if (i == 23) {
                    Rlog.d(LOG_TAG, "Event EVENT_NV_READY Received");
                    prepareEri();
                    Rlog.d(LOG_TAG, "notifyMessageWaitingChanged");
                    this.mNotifier.notifyMessageWaitingChanged(this);
                    updateVoiceMail();
                    return;
                } else if (i != 27) {
                    if (i == 41) {
                        this.mIsPhoneReadySent = false;
                        QtiGsmCdmaPhone.super.handleMessage(msg);
                        return;
                    } else if (i != 46) {
                        QtiGsmCdmaPhone.super.handleMessage(msg);
                        return;
                    } else {
                        AsyncResult ar = (AsyncResult) msg.obj;
                        if (ar == null || ar.result == null) {
                            Rlog.e(LOG_TAG, "Error: empty result, EVENT_OEM_HOOK_SERVICE_READY");
                            return;
                        } else if (((Boolean) ar.result).booleanValue()) {
                            if (this.mIsPhoneReadyPending) {
                                updatePhoneReady(this.mPhoneId);
                            }
                            Rlog.d(LOG_TAG, "EVENT_OEM_HOOK_SERVICE_READY received");
                            return;
                        } else {
                            return;
                        }
                    }
                }
            }
            if (mNumPhones == 1) {
                handleCdmaNVSubscriptionSource(this.mCdmaNVSSM.getCdmaSubscriptionSource());
            }
            QtiGsmCdmaPhone.super.handleMessage(msg);
        } else {
            if (isPhoneTypeGsm()) {
                String str2 = LOG_TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("notify call forward indication, phone id:");
                sb2.append(this.mPhoneId);
                Rlog.d(str2, sb2.toString());
                notifyCallForwardingIndicator();
            }
            QtiGsmCdmaPhone.super.handleMessage(msg);
        }
    }

    public void startDtmf(char c) {
        if (PhoneNumberUtils.is12Key(c) || c == 'D') {
            if (isPhoneTypeCdma() && c == 'D') {
                c = '#';
            }
            this.mCi.startDtmf(c, null);
            return;
        }
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("startDtmf called with invalid character '");
        sb.append(c);
        sb.append("'");
        Rlog.e(str, sb.toString());
    }

    public void sendBurstDtmf(String dtmfString, int on, int off, Message onComplete) {
        Character c = Character.valueOf(dtmfString.charAt(0));
        if (dtmfString.length() == 1 && c.charValue() == 'D') {
            dtmfString = c.toString();
        }
        QtiGsmCdmaPhone.super.sendBurstDtmf(dtmfString, on, off, onComplete);
    }

    public void sendSubscriptionSettings(boolean restoreNetworkSelection) {
        ExtTelephonyServiceImpl serviceImpl = ExtTelephonyServiceImpl.getInstance();
        if (SubsidyLockSettingsObserver.isSubsidyLockFeatureEnabled() && !SubsidyLockSettingsObserver.isSubsidyUnlocked(this.mContext)) {
            if ((serviceImpl != null) && serviceImpl.isPrimaryCarrierSlotId(getPhoneId())) {
                setPreferredNetworkType(PhoneFactory.calculatePreferredNetworkType(this.mContext, getSubId()), null);
                logd(" settings network selection mode to AUTO ");
                setNetworkSelectionModeAutomatic(null);
                return;
            }
        }
        QtiGsmCdmaPhone.super.sendSubscriptionSettings(restoreNetworkSelection);
    }

    private BaseRilInterface getQtiRilInterface() {
        if (!getUnitTestMode()) {
            return QtiRilInterface.getInstance(this.mContext);
        }
        logd("getQtiRilInterface, unitTestMode = true");
        return SimulatedQtiRilInterface.getInstance(this.mContext);
    }

    public boolean isEmergencyNumber(String address) {
        return QtiEmergencyCallHelper.isEmergencyNumber(address);
    }

    public ServiceState getServiceState() {
        if (this.mSST == null || this.mSST.mSS.getState() != 0) {
            if (this.mImsPhone != null && (this.mImsPhone.isVolteEnabled() || this.mImsPhone.isVideoEnabled() || this.mImsPhone.isWifiCallingEnabled())) {
                return ServiceState.mergeServiceStates(this.mSST == null ? new ServiceState() : this.mSST.mSS, this.mImsPhone.getServiceState());
            }
        }
        if (this.mSST != null) {
            return this.mSST.mSS;
        }
        return new ServiceState();
    }

    public String getFullIccSerialNumber() {
        String iccId = QtiGsmCdmaPhone.super.getFullIccSerialNumber();
        if (TextUtils.isEmpty(iccId)) {
            return QtiUiccCardProvisioner.getInstance().getUiccFullIccId(this.mPhoneId);
        }
        return iccId;
    }

    private void logd(String msg) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.mPhoneId);
        sb.append(" ] ");
        sb.append(msg);
        Rlog.d(str, sb.toString());
    }

    private void loge(String msg) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.mPhoneId);
        sb.append(" ] ");
        sb.append(msg);
        Rlog.e(str, sb.toString());
    }
}
