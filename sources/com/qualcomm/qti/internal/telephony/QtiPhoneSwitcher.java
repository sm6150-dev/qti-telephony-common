package com.qualcomm.qti.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.radio.V1_0.DataCallFailCause;
import android.net.NetworkRequest;
import android.os.AsyncResult;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.dataconnection.DcRequest;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCall;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class QtiPhoneSwitcher extends PhoneSwitcher {
    public static boolean isLplusLSupported = false;
    private final int EVENT_ALLOW_DATA_FALSE_RESPONSE = 201;
    private final int EVENT_ALLOW_DATA_TRUE_RESPONSE = 202;
    private final int EVENT_DDS_SWITCH_RESPONSE = 203;
    private final int MAX_CONNECT_FAILURE_COUNT = 5;
    private final int NONUSER_INITIATED_SWITCH = 1;
    private final String PROPERTY_TEMP_DDSSWITCH = "persist.vendor.radio.enable_temp_dds";
    private final int USER_INITIATED_SWITCH = 0;
    private int[] mAllowDataFailure;
    private final GsmCdmaCall[] mBgCsCalls;
    private final ImsPhoneCall[] mBgImsCalls;
    private CallManager mCm;
    private int mDefaultDataPhoneId = -1;
    private final GsmCdmaCall[] mFgCsCalls;
    private final ImsPhoneCall[] mFgImsCalls;
    private final ImsPhone[] mImsPhones;
    private boolean mManualDdsSwitch = false;
    private List<Integer> mNewActivePhones;
    private QtiRilInterface mQtiRilInterface;
    private final int[] mRetryArray = {5, 10, 20, 40, 60};
    private final GsmCdmaCall[] mRiCsCalls;
    private final ImsPhoneCall[] mRiImsCalls;
    private boolean mSendDdsSwitchDoneIntent = false;
    private BroadcastReceiver mSimStateIntentReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SIM_STATE_CHANGED")) {
                String value = intent.getStringExtra("ss");
                int phoneId = intent.getIntExtra("phone", -1);
                QtiPhoneSwitcher qtiPhoneSwitcher = QtiPhoneSwitcher.this;
                qtiPhoneSwitcher.log("mSimStateIntentReceiver: phoneId = " + phoneId + " value = " + value);
                if (phoneId != -1) {
                    QtiPhoneSwitcher.this.mSimStates[phoneId] = value;
                }
                if (QtiPhoneSwitcher.this.isSimReady(phoneId) && QtiPhoneSwitcher.this.getConnectFailureCount(phoneId) > 0) {
                    QtiPhoneSwitcher.this.onRadioCapChanged(phoneId);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public String[] mSimStates;
    private boolean mWaitForDetachResponse = false;

    /* JADX WARNING: type inference failed for: r4v0, types: [com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher, android.os.Handler] */
    public QtiPhoneSwitcher(int maxActivePhones, int numPhones, Context context, SubscriptionController subscriptionController, Looper looper, ITelephonyRegistry tr, CommandsInterface[] cis, Phone[] phones) {
        super(maxActivePhones, numPhones, context, subscriptionController, looper, tr, cis, phones);
        this.mAllowDataFailure = new int[numPhones];
        this.mSimStates = new String[numPhones];
        this.mCm = CallManager.getInstance();
        this.mCm.registerForDisconnect(this, DataCallFailCause.IFACE_MISMATCH, (Object) null);
        this.mQtiRilInterface = QtiRilInterface.getInstance(context);
        this.mQtiRilInterface.registerForUnsol(this, DataCallFailCause.COMPANION_IFACE_IN_USE, (Object) null);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mContext.registerReceiver(this.mSimStateIntentReceiver, filter);
        this.mImsPhones = new ImsPhone[this.mNumPhones];
        this.mFgCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mBgCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mRiCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mFgImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mBgImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mRiImsCalls = new ImsPhoneCall[this.mNumPhones];
        for (int i = 0; i < this.mNumPhones; i++) {
            if (this.mPhones[i] != null) {
                this.mFgCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getForegroundCall();
                this.mBgCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getBackgroundCall();
                this.mRiCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getRingingCall();
            }
            this.mImsPhones[i] = (ImsPhone) this.mPhones[i].getImsPhone();
            ImsPhone[] imsPhoneArr = this.mImsPhones;
            if (imsPhoneArr[i] != null) {
                this.mFgImsCalls[i] = imsPhoneArr[i].getForegroundCall();
                this.mBgImsCalls[i] = this.mImsPhones[i].getBackgroundCall();
                this.mRiImsCalls[i] = this.mImsPhones[i].getRingingCall();
            }
        }
    }

    public static QtiPhoneSwitcher make(int maxActivePhones, int numPhones, Context context, SubscriptionController subscriptionController, Looper looper, ITelephonyRegistry tr, CommandsInterface[] cis, Phone[] phones) {
        if (sPhoneSwitcher == null) {
            sPhoneSwitcher = new QtiPhoneSwitcher(maxActivePhones, numPhones, context, subscriptionController, looper, tr, cis, phones);
        }
        return sPhoneSwitcher;
    }

    private void queryMaxDataAllowed() {
        this.mMaxActivePhones = this.mQtiRilInterface.getMaxDataAllowed();
    }

    private void handleUnsolMaxDataAllowedChange(Message msg) {
        if (msg == null || msg.obj == null) {
            log("Null data received in handleUnsolMaxDataAllowedChange");
            return;
        }
        ByteBuffer payload = ByteBuffer.wrap((byte[]) msg.obj);
        payload.order(ByteOrder.nativeOrder());
        if (payload.getInt() == 525342) {
            int response_size = payload.getInt();
            if (response_size < 0) {
                log("Response size is Invalid " + response_size);
                return;
            }
            this.mMaxActivePhones = payload.get();
            log(" Unsol Max Data Changed to: " + this.mMaxActivePhones);
        }
    }

    public void handleMessage(Message msg) {
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(this.mSubscriptionController.getDefaultDataSubId());
        log("handle event - " + msg.what);
        int i = msg.what;
        if (i == 102) {
            if (this.mHalCommandToUse == 0) {
                log("EVENT_SUBSCRIPTION_CHANGED: update HAL command");
                this.mHalCommandToUse = this.mRadioConfig.isSetPreferredDataCommandSupported() ? 2 : 1;
            }
            onEvaluate(false, "subChanged");
        } else if (i == 108) {
            if (this.mQtiRilInterface.isServiceReady()) {
                queryMaxDataAllowed();
                isLplusLSupported = this.mQtiRilInterface.getLpluslSupportStatus();
            } else {
                log("Oem hook service is not ready");
            }
            QtiPhoneSwitcher.super.handleMessage(msg);
        } else if (i == 117) {
            log("EVENT_VOICE_CALL_ENDED");
            if (!isAnyVoiceCallActiveOnDevice()) {
                int i2 = 0;
                while (i2 < this.mNumPhones) {
                    if (getConnectFailureCount(i2) <= 0 || !isPhoneIdValidForRetry(i2)) {
                        i2++;
                    } else {
                        onRadioCapChanged(i2);
                        return;
                    }
                }
            }
        } else if (i != 118) {
            switch (i) {
                case 201:
                    log("EVENT_ALLOW_DATA_FALSE_RESPONSE");
                    this.mWaitForDetachResponse = false;
                    for (Integer intValue : this.mNewActivePhones) {
                        activate(intValue.intValue());
                    }
                    if (this.mNewActivePhones.contains(Integer.valueOf(ddsPhoneId))) {
                        this.mManualDdsSwitch = false;
                        return;
                    }
                    return;
                case 202:
                    log("EVENT_ALLOW_DATA_TRUE_RESPONSE");
                    onDdsSwitchResponse(msg.arg1, (AsyncResult) msg.obj);
                    return;
                case 203:
                    log("EVENT_DDS_SWITCH_RESPONSE");
                    onDdsSwitchResponse(msg.arg1, (AsyncResult) msg.obj);
                    return;
                default:
                    QtiPhoneSwitcher.super.handleMessage(msg);
                    return;
            }
        } else {
            org.codeaurora.telephony.utils.AsyncResult asyncresult = (org.codeaurora.telephony.utils.AsyncResult) msg.obj;
            if (asyncresult.result != null) {
                handleUnsolMaxDataAllowedChange((Message) asyncresult.result);
            } else {
                log("Error: empty result, EVENT_UNSOL_MAX_DATA_ALLOWED_CHANGED");
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isSimReady(int phoneId) {
        if (phoneId == -1) {
            return false;
        }
        if (!"READY".equals(this.mSimStates[phoneId]) && !"LOADED".equals(this.mSimStates[phoneId]) && !"IMSI".equals(this.mSimStates[phoneId])) {
            return false;
        }
        log("SIM READY for phoneId: " + phoneId);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onEvaluate(boolean requestsChanged, String reason) {
        StringBuilder sb = new StringBuilder(reason);
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        boolean mPreferredDataPhoneIdUpdated = false;
        boolean diffDetected = requestsChanged;
        int primaryDataSubId = this.mSubscriptionController.getDefaultDataSubId();
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(primaryDataSubId);
        if (primaryDataSubId != this.mPrimaryDataSubId) {
            sb.append(" mPrimaryDataSubId ");
            sb.append(this.mPrimaryDataSubId);
            sb.append("->");
            sb.append(primaryDataSubId);
            this.mManualDdsSwitch = true;
            this.mSendDdsSwitchDoneIntent = true;
            this.mPrimaryDataSubId = primaryDataSubId;
        }
        for (int i = 0; i < this.mNumPhones; i++) {
            int sub = this.mSubscriptionController.getSubIdUsingPhoneId(i);
            if (sub != this.mPhoneSubscriptions[i]) {
                sb.append(" phone[");
                sb.append(i);
                sb.append("] ");
                sb.append(this.mPhoneSubscriptions[i]);
                sb.append("->");
                sb.append(sub);
                this.mPhoneSubscriptions[i] = sub;
                diffDetected = true;
            }
        }
        int i2 = this.mPreferredDataPhoneId;
        updatePreferredDataPhoneId();
        if (i2 != this.mPreferredDataPhoneId) {
            sb.append(" preferred phoneId ");
            sb.append(i2);
            sb.append("->");
            sb.append(this.mPreferredDataPhoneId);
            mPreferredDataPhoneIdUpdated = true;
            diffDetected = true;
        }
        if (isEmergency()) {
            log("onEvalaute aborted due to Emergency");
            return false;
        }
        if (diffDetected) {
            log("evaluating due to " + sb.toString());
            if (this.mHalCommandToUse == 2) {
                for (int phoneId = 0; phoneId < this.mNumPhones; phoneId++) {
                    activate(phoneId);
                }
                if (mPreferredDataPhoneIdUpdated) {
                    sendRilCommands(this.mPreferredDataPhoneId);
                }
            } else {
                List<Integer> newActivePhones = new ArrayList<>();
                for (DcRequest dcRequest : this.mPrioritizedDcRequests) {
                    int phoneIdForRequest = phoneIdForRequest(dcRequest.networkRequest, dcRequest.apnType);
                    if (phoneIdForRequest != -1 && !newActivePhones.contains(Integer.valueOf(phoneIdForRequest))) {
                        newActivePhones.add(Integer.valueOf(phoneIdForRequest));
                        if (newActivePhones.size() >= this.mMaxActivePhones) {
                            break;
                        }
                    }
                }
                this.mNewActivePhones = newActivePhones;
                for (int phoneId2 = 0; phoneId2 < this.mNumPhones; phoneId2++) {
                    if (!newActivePhones.contains(Integer.valueOf(phoneId2))) {
                        deactivate(phoneId2);
                    }
                }
                if (this.mWaitForDetachResponse == 0) {
                    boolean activateDdsPhone = this.mNewActivePhones.contains(Integer.valueOf(ddsPhoneId));
                    if (!activateDdsPhone || !this.mManualDdsSwitch) {
                        for (Integer intValue : newActivePhones) {
                            activate(intValue.intValue());
                        }
                    } else {
                        activate(ddsPhoneId);
                    }
                    if (activateDdsPhone) {
                        this.mManualDdsSwitch = false;
                    }
                }
            }
        }
        return diffDetected;
    }

    /* access modifiers changed from: protected */
    public int phoneIdForRequest(NetworkRequest netRequest, int apnType) {
        int subId = getSubIdFromNetworkRequest(netRequest);
        if (subId == Integer.MAX_VALUE) {
            return this.mPreferredDataPhoneId;
        }
        if (subId == -1) {
            return -1;
        }
        int preferredDataSubId = SubscriptionManager.isValidPhoneId(this.mPreferredDataPhoneId) ? this.mPhoneSubscriptions[this.mPreferredDataPhoneId] : -1;
        if (netRequest.networkCapabilities.hasCapability(12) && subId != preferredDataSubId && subId != this.mValidator.getSubIdInValidation()) {
            return -1;
        }
        if (64 == apnType && this.mManualDdsSwitch && this.mMaxActivePhones != this.mNumPhones) {
            subId = this.mPrimaryDataSubId;
        }
        for (int i = 0; i < this.mNumPhones; i++) {
            if (this.mPhoneSubscriptions[i] == subId) {
                return i;
            }
        }
        return -1;
    }

    private boolean isUiccProvisioned(int phoneId) {
        boolean status = QtiUiccCardProvisioner.getInstance().getCurrentUiccCardProvisioningStatus(phoneId) > 0;
        log("isUiccProvisioned = " + status);
        return status;
    }

    /* access modifiers changed from: protected */
    public void deactivate(int phoneId) {
        PhoneSwitcher.PhoneState state = this.mPhoneStates[phoneId];
        if (state.active) {
            SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
            state.active = false;
            log("deactivate " + phoneId);
            state.lastRequested = System.currentTimeMillis();
            if ((this.mHalCommandToUse == 1 || this.mHalCommandToUse == 0) && this.mSubscriptionController.isActiveSubId(this.mPhoneSubscriptions[phoneId])) {
                this.mCommandsInterfaces[phoneId].setDataAllowed(false, obtainMessage(201));
                this.mWaitForDetachResponse = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void activate(int phoneId) {
        PhoneSwitcher.PhoneState state = this.mPhoneStates[phoneId];
        if (!state.active || this.mManualDdsSwitch || getConnectFailureCount(phoneId) != 0) {
            state.active = true;
            log("activate " + phoneId);
            state.lastRequested = System.currentTimeMillis();
            if ((this.mHalCommandToUse == 1 || this.mHalCommandToUse == 0) && this.mNumPhones > 1) {
                informDdsToRil(this.mPrimaryDataSubId);
                this.mCommandsInterfaces[phoneId].setDataAllowed(true, obtainMessage(202, phoneId, 0));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void sendRilCommands(int phoneId) {
        if (!SubscriptionManager.isValidPhoneId(phoneId) || phoneId >= this.mNumPhones) {
            log("sendRilCommands: skip dds switch due to invalid phoneid=" + phoneId);
        } else if (this.mHalCommandToUse == 1 || this.mHalCommandToUse == 0) {
            if (this.mNumPhones > 1) {
                this.mCommandsInterfaces[phoneId].setDataAllowed(isPhoneActive(phoneId), obtainMessage(202, phoneId, 0));
            }
        } else if (phoneId == this.mPreferredDataPhoneId) {
            log("sendRilCommands: setPreferredDataModem - phoneId: " + phoneId);
            this.mRadioConfig.setPreferredDataModem(phoneId, obtainMessage(203, phoneId, 0));
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCallActive(Phone phone) {
        boolean isLplTempSwitch = SystemProperties.getBoolean("persist.vendor.radio.enable_temp_dds", true) && Settings.Global.getInt(this.mContext.getContentResolver(), "vice_slot_volte_data_enabled", 0) != 0;
        log("isLplTempSwitch = " + isLplTempSwitch);
        if (!isLplTempSwitch || phone == null) {
            return false;
        }
        int phoneId = phone.getPhoneId();
        if (this.mFgCsCalls[phoneId].getState() != Call.State.IDLE && this.mFgCsCalls[phoneId].getState() != Call.State.DISCONNECTED) {
            return true;
        }
        if (this.mBgCsCalls[phoneId].getState() != Call.State.IDLE && this.mBgCsCalls[phoneId].getState() != Call.State.DISCONNECTED) {
            return true;
        }
        if (this.mRiCsCalls[phoneId].getState() != Call.State.IDLE && this.mRiCsCalls[phoneId].getState() != Call.State.DISCONNECTED) {
            return true;
        }
        if (this.mFgImsCalls[phoneId].getState() != Call.State.IDLE && this.mFgImsCalls[phoneId].getState() != Call.State.DISCONNECTED) {
            return true;
        }
        if (this.mBgImsCalls[phoneId].getState() != Call.State.IDLE && this.mBgImsCalls[phoneId].getState() != Call.State.DISCONNECTED) {
            return true;
        }
        if (this.mRiImsCalls[phoneId].getState() == Call.State.IDLE || this.mRiImsCalls[phoneId].getState() == Call.State.DISCONNECTED) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void resetConnectFailureCount(int phoneId) {
        this.mAllowDataFailure[phoneId] = 0;
    }

    private void incConnectFailureCount(int phoneId) {
        int[] iArr = this.mAllowDataFailure;
        iArr[phoneId] = iArr[phoneId] + 1;
    }

    @VisibleForTesting
    public int getConnectFailureCount(int phoneId) {
        return this.mAllowDataFailure[phoneId];
    }

    private void handleConnectMaxFailure(int phoneId) {
        resetConnectFailureCount(phoneId);
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(this.mSubscriptionController.getDefaultDataSubId());
        if (QtiPhoneUtils.getInstance().isValidPhoneId(ddsPhoneId) && phoneId != ddsPhoneId) {
            log("ALLOW_DATA retries exhausted on phoneId = " + phoneId);
            enforceDds(ddsPhoneId);
        }
    }

    private void enforceDds(int phoneId) {
        int[] subId = this.mSubscriptionController.getSubId(phoneId);
        log("enforceDds: subId = " + subId[0]);
        this.mSubscriptionController.setDefaultDataSubId(subId[0]);
    }

    private boolean isAnyVoiceCallActiveOnDevice() {
        boolean ret = this.mCm.getState() != PhoneConstants.State.IDLE;
        log("isAnyVoiceCallActiveOnDevice: " + ret);
        return ret;
    }

    private void onDdsSwitchResponse(final int phoneId, AsyncResult ar) {
        if (ar.exception != null) {
            incConnectFailureCount(phoneId);
            log("Dds switch failed on phoneId = " + phoneId + ", failureCount = " + getConnectFailureCount(phoneId));
            if (isAnyVoiceCallActiveOnDevice()) {
                log("Wait for call end indication");
            } else if (!isSimReady(phoneId)) {
                log("Wait for SIM to get READY");
            } else {
                int ddsSwitchFailureCount = getConnectFailureCount(phoneId);
                if (ddsSwitchFailureCount > 5) {
                    handleConnectMaxFailure(phoneId);
                    return;
                }
                int retryDelay = this.mRetryArray[ddsSwitchFailureCount - 1] * 1000;
                log("Scheduling DDS switch retry after: " + retryDelay);
                postDelayed(new Runnable() {
                    public void run() {
                        QtiPhoneSwitcher.this.log("Running DDS switch retry");
                        if (QtiPhoneSwitcher.this.isPhoneIdValidForRetry(phoneId)) {
                            QtiPhoneSwitcher.this.onRadioCapChanged(phoneId);
                            return;
                        }
                        QtiPhoneSwitcher.this.log("Abandon DDS switch retry");
                        QtiPhoneSwitcher.this.resetConnectFailureCount(phoneId);
                    }
                }, (long) retryDelay);
            }
        } else {
            log("DDS switch success on phoneId = " + phoneId);
            if (this.mSendDdsSwitchDoneIntent) {
                this.mSendDdsSwitchDoneIntent = false;
                Intent intent = new Intent("org.codeaurora.intent.action.ACTION_DDS_SWITCH_DONE");
                intent.putExtra("subscription", this.mSubscriptionController.getDefaultDataSubId());
                intent.addFlags(16777216);
                log("Broadcast dds switch done intent");
                this.mContext.sendBroadcast(intent);
            }
            resetConnectFailureCount(phoneId);
            this.mActivePhoneRegistrants.notifyRegistrants();
            notifyPreferredDataSubIdChanged();
        }
    }

    /* access modifiers changed from: private */
    public boolean isPhoneIdValidForRetry(int phoneId) {
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(this.mSubscriptionController.getDefaultDataSubId());
        if (ddsPhoneId != -1 && ddsPhoneId == phoneId) {
            return true;
        }
        if (this.mPrioritizedDcRequests.size() <= 0) {
            return false;
        }
        for (int i = 0; i < this.mMaxActivePhones; i++) {
            DcRequest dcRequest = (DcRequest) this.mPrioritizedDcRequests.get(i);
            if (dcRequest != null && phoneIdForRequest(dcRequest.networkRequest, dcRequest.apnType) == phoneId) {
                return true;
            }
        }
        return false;
    }

    private void informDdsToRil(int ddsSubId) {
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(ddsSubId);
        if (!this.mQtiRilInterface.isServiceReady()) {
            log("Oem hook service is not ready yet");
            return;
        }
        for (int i = 0; i < this.mNumPhones; i++) {
            log("InformDdsToRil rild= " + i + ", DDS=" + ddsPhoneId);
            if (isCallInProgress()) {
                this.mQtiRilInterface.qcRilSendDDSInfo(ddsPhoneId, 1, i);
            } else {
                this.mQtiRilInterface.qcRilSendDDSInfo(ddsPhoneId, 0, i);
            }
        }
    }

    private boolean isCallInProgress() {
        return SubscriptionManager.isValidPhoneId(this.mPhoneIdInVoiceCall);
    }
}
