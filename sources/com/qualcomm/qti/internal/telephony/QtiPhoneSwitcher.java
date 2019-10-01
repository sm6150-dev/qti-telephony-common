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
import android.provider.Settings.Global;
import android.telephony.SubscriptionManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CallManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants.State;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.PhoneSwitcher.PhoneState;
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
                StringBuilder sb = new StringBuilder();
                sb.append("mSimStateIntentReceiver: phoneId = ");
                sb.append(phoneId);
                sb.append(" value = ");
                sb.append(value);
                qtiPhoneSwitcher.log(sb.toString());
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
        this.mCm.registerForDisconnect(this, DataCallFailCause.IFACE_MISMATCH, null);
        this.mQtiRilInterface = QtiRilInterface.getInstance(context);
        this.mQtiRilInterface.registerForUnsol(this, DataCallFailCause.COMPANION_IFACE_IN_USE, null);
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
            QtiPhoneSwitcher qtiPhoneSwitcher = new QtiPhoneSwitcher(maxActivePhones, numPhones, context, subscriptionController, looper, tr, cis, phones);
            sPhoneSwitcher = qtiPhoneSwitcher;
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
                StringBuilder sb = new StringBuilder();
                sb.append("Response size is Invalid ");
                sb.append(response_size);
                log(sb.toString());
                return;
            }
            this.mMaxActivePhones = payload.get();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" Unsol Max Data Changed to: ");
            sb2.append(this.mMaxActivePhones);
            log(sb2.toString());
        }
    }

    public void handleMessage(Message msg) {
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(this.mSubscriptionController.getDefaultDataSubId());
        StringBuilder sb = new StringBuilder();
        sb.append("handle event - ");
        sb.append(msg.what);
        log(sb.toString());
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
        if (!"READY".equals(this.mSimStates[phoneId])) {
            if (!"LOADED".equals(this.mSimStates[phoneId])) {
                if (!"IMSI".equals(this.mSimStates[phoneId])) {
                    return false;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SIM READY for phoneId: ");
        sb.append(phoneId);
        log(sb.toString());
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
        String str = "->";
        if (primaryDataSubId != this.mPrimaryDataSubId) {
            sb.append(" mPrimaryDataSubId ");
            sb.append(this.mPrimaryDataSubId);
            sb.append(str);
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
                sb.append(str);
                sb.append(sub);
                this.mPhoneSubscriptions[i] = sub;
                diffDetected = true;
            }
        }
        int oldPreferredDataPhoneId = this.mPreferredDataPhoneId;
        updatePreferredDataPhoneId();
        if (oldPreferredDataPhoneId != this.mPreferredDataPhoneId) {
            sb.append(" preferred phoneId ");
            sb.append(oldPreferredDataPhoneId);
            sb.append(str);
            sb.append(this.mPreferredDataPhoneId);
            mPreferredDataPhoneIdUpdated = true;
            diffDetected = true;
        }
        if (isEmergency()) {
            log("onEvalaute aborted due to Emergency");
            return false;
        }
        if (diffDetected) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("evaluating due to ");
            sb2.append(sb.toString());
            log(sb2.toString());
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
        int phoneId = -1;
        int i = 0;
        while (true) {
            if (i >= this.mNumPhones) {
                break;
            } else if (this.mPhoneSubscriptions[i] == subId) {
                phoneId = i;
                break;
            } else {
                i++;
            }
        }
        return phoneId;
    }

    private boolean isUiccProvisioned(int phoneId) {
        boolean status = QtiUiccCardProvisioner.getInstance().getCurrentUiccCardProvisioningStatus(phoneId) > 0;
        StringBuilder sb = new StringBuilder();
        sb.append("isUiccProvisioned = ");
        sb.append(status);
        log(sb.toString());
        return status;
    }

    /* access modifiers changed from: protected */
    public void deactivate(int phoneId) {
        PhoneState state = this.mPhoneStates[phoneId];
        if (state.active) {
            SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
            state.active = false;
            StringBuilder sb = new StringBuilder();
            sb.append("deactivate ");
            sb.append(phoneId);
            log(sb.toString());
            state.lastRequested = System.currentTimeMillis();
            if ((this.mHalCommandToUse == 1 || this.mHalCommandToUse == 0) && this.mSubscriptionController.isActiveSubId(this.mPhoneSubscriptions[phoneId])) {
                this.mCommandsInterfaces[phoneId].setDataAllowed(false, obtainMessage(201));
                this.mWaitForDetachResponse = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void activate(int phoneId) {
        PhoneState state = this.mPhoneStates[phoneId];
        if (!state.active || this.mManualDdsSwitch || getConnectFailureCount(phoneId) != 0) {
            state.active = true;
            StringBuilder sb = new StringBuilder();
            sb.append("activate ");
            sb.append(phoneId);
            log(sb.toString());
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
            StringBuilder sb = new StringBuilder();
            sb.append("sendRilCommands: skip dds switch due to invalid phoneid=");
            sb.append(phoneId);
            log(sb.toString());
            return;
        }
        if (this.mHalCommandToUse == 1 || this.mHalCommandToUse == 0) {
            if (this.mNumPhones > 1) {
                this.mCommandsInterfaces[phoneId].setDataAllowed(isPhoneActive(phoneId), obtainMessage(202, phoneId, 0));
            }
        } else if (phoneId == this.mPreferredDataPhoneId) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("sendRilCommands: setPreferredDataModem - phoneId: ");
            sb2.append(phoneId);
            log(sb2.toString());
            this.mRadioConfig.setPreferredDataModem(phoneId, obtainMessage(203, phoneId, 0));
        }
    }

    /* access modifiers changed from: protected */
    public boolean isCallActive(Phone phone) {
        boolean z = true;
        boolean isLplTempSwitch = SystemProperties.getBoolean("persist.vendor.radio.enable_temp_dds", true) && Global.getInt(this.mContext.getContentResolver(), "vice_slot_volte_data_enabled", 0) != 0;
        StringBuilder sb = new StringBuilder();
        sb.append("isLplTempSwitch = ");
        sb.append(isLplTempSwitch);
        log(sb.toString());
        if (!isLplTempSwitch || phone == null) {
            return false;
        }
        int phoneId = phone.getPhoneId();
        if (!this.mFgCsCalls[phoneId].getState().isAlive() && !this.mBgCsCalls[phoneId].getState().isAlive() && !this.mRiCsCalls[phoneId].getState().isAlive() && !this.mFgImsCalls[phoneId].getState().isAlive() && !this.mBgImsCalls[phoneId].getState().isAlive() && !this.mRiImsCalls[phoneId].getState().isAlive()) {
            z = false;
        }
        return z;
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
            StringBuilder sb = new StringBuilder();
            sb.append("ALLOW_DATA retries exhausted on phoneId = ");
            sb.append(phoneId);
            log(sb.toString());
            enforceDds(ddsPhoneId);
        }
    }

    private void enforceDds(int phoneId) {
        int[] subId = this.mSubscriptionController.getSubId(phoneId);
        StringBuilder sb = new StringBuilder();
        sb.append("enforceDds: subId = ");
        sb.append(subId[0]);
        log(sb.toString());
        this.mSubscriptionController.setDefaultDataSubId(subId[0]);
    }

    private boolean isAnyVoiceCallActiveOnDevice() {
        boolean ret = this.mCm.getState() != State.IDLE;
        StringBuilder sb = new StringBuilder();
        sb.append("isAnyVoiceCallActiveOnDevice: ");
        sb.append(ret);
        log(sb.toString());
        return ret;
    }

    private void onDdsSwitchResponse(final int phoneId, AsyncResult ar) {
        if (ar.exception != null) {
            incConnectFailureCount(phoneId);
            StringBuilder sb = new StringBuilder();
            sb.append("Dds switch failed on phoneId = ");
            sb.append(phoneId);
            sb.append(", failureCount = ");
            sb.append(getConnectFailureCount(phoneId));
            log(sb.toString());
            if (isAnyVoiceCallActiveOnDevice()) {
                log("Wait for call end indication");
            } else if (!isSimReady(phoneId)) {
                log("Wait for SIM to get READY");
            } else {
                int ddsSwitchFailureCount = getConnectFailureCount(phoneId);
                if (ddsSwitchFailureCount > 5) {
                    handleConnectMaxFailure(phoneId);
                } else {
                    int retryDelay = this.mRetryArray[ddsSwitchFailureCount - 1] * 1000;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Scheduling DDS switch retry after: ");
                    sb2.append(retryDelay);
                    log(sb2.toString());
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
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("DDS switch success on phoneId = ");
            sb3.append(phoneId);
            log(sb3.toString());
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
            StringBuilder sb = new StringBuilder();
            sb.append("InformDdsToRil rild= ");
            sb.append(i);
            sb.append(", DDS=");
            sb.append(ddsPhoneId);
            log(sb.toString());
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
