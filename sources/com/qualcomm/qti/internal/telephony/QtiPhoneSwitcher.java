package com.qualcomm.qti.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.hardware.radio.V1_0.DataCallFailCause;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.StringNetworkSpecifier;
import android.os.AsyncResult;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CallManager;
import com.android.internal.telephony.PhoneConstants.State;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.PhoneSwitcher.PhoneState;
import com.android.internal.telephony.dataconnection.DcRequest;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class QtiPhoneSwitcher extends PhoneSwitcher {
    public static boolean isLplusLSupported = false;
    private final int EVENT_ALLOW_DATA_FALSE_RESPONSE;
    private final int EVENT_ALLOW_DATA_TRUE_RESPONSE;
    private final int EVENT_RADIO_AVAILABLE;
    private final int MAX_CONNECT_FAILURE_COUNT = 5;
    private final int NONUSER_INITIATED_SWITCH;
    private final String PROPERTY_TEMP_DDSSWITCH;
    private final int USER_INITIATED_SWITCH;
    private int[] mAllowDataFailure;
    private CallManager mCm;
    private int mDefaultDataPhoneId;
    private boolean mManualDdsSwitch;
    private List<Integer> mNewActivePhones;
    private QtiDdsSwitchController mQtiDdsSwitchController;
    private QtiRilInterface mQtiRilInterface;
    private final int[] mRetryArray = {5, 10, 20, 40, 60};
    private boolean mSendDdsSwitchDoneIntent;
    private BroadcastReceiver mSimStateIntentReceiver;
    /* access modifiers changed from: private */
    public String[] mSimStates;
    private boolean mWaitForDetachResponse;

    /* JADX WARNING: type inference failed for: r0v0, types: [com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher, android.os.Handler] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher, android.os.Handler]
      assigns: [?[OBJECT, ARRAY]]
      uses: [com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher, android.os.Handler]
      mth insns count: 57
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public QtiPhoneSwitcher(int r12, int r13, android.content.Context r14, com.android.internal.telephony.SubscriptionController r15, android.os.Looper r16, com.android.internal.telephony.ITelephonyRegistry r17, com.android.internal.telephony.CommandsInterface[] r18, com.android.internal.telephony.Phone[] r19) {
        /*
            r11 = this;
            r0 = r11
            r8 = r13
            r11.<init>(r12, r13, r14, r15, r16, r17, r18, r19)
            r1 = 5
            r0.MAX_CONNECT_FAILURE_COUNT = r1
            int[] r1 = new int[r1]
            r1 = {5, 10, 20, 40, 60} // fill-array
            r0.mRetryArray = r1
            r1 = 0
            r0.mManualDdsSwitch = r1
            r0.mSendDdsSwitchDoneIntent = r1
            r2 = -1
            r0.mDefaultDataPhoneId = r2
            r0.mWaitForDetachResponse = r1
            r2 = 112(0x70, float:1.57E-43)
            r0.EVENT_ALLOW_DATA_FALSE_RESPONSE = r2
            r2 = 113(0x71, float:1.58E-43)
            r0.EVENT_ALLOW_DATA_TRUE_RESPONSE = r2
            r2 = 114(0x72, float:1.6E-43)
            r0.EVENT_RADIO_AVAILABLE = r2
            r0.USER_INITIATED_SWITCH = r1
            r3 = 1
            r0.NONUSER_INITIATED_SWITCH = r3
            java.lang.String r3 = "persist.vendor.radio.enable_temp_dds"
            r0.PROPERTY_TEMP_DDSSWITCH = r3
            com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher$1 r3 = new com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher$1
            r3.<init>()
            r0.mSimStateIntentReceiver = r3
            int[] r3 = new int[r8]
            r0.mAllowDataFailure = r3
            java.lang.String[] r3 = new java.lang.String[r8]
            r0.mSimStates = r3
            com.android.internal.telephony.CallManager r3 = com.android.internal.telephony.CallManager.getInstance()
            r0.mCm = r3
            com.android.internal.telephony.CallManager r3 = r0.mCm
            r4 = 0
            r5 = 107(0x6b, float:1.5E-43)
            r3.registerForDisconnect(r0, r5, r4)
            com.qualcomm.qti.internal.telephony.QtiRilInterface r3 = com.qualcomm.qti.internal.telephony.QtiRilInterface.getInstance(r14)
            r0.mQtiRilInterface = r3
        L_0x0052:
            com.android.internal.telephony.CommandsInterface[] r3 = r0.mCommandsInterfaces
            int r3 = r3.length
            if (r1 >= r3) goto L_0x0065
            com.android.internal.telephony.CommandsInterface[] r3 = r0.mCommandsInterfaces
            r3 = r3[r1]
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            r3.registerForAvailable(r0, r2, r5)
            int r1 = r1 + 1
            goto L_0x0052
        L_0x0065:
            com.qualcomm.qti.internal.telephony.QtiRilInterface r1 = r0.mQtiRilInterface
            r2 = 108(0x6c, float:1.51E-43)
            r1.registerForUnsol(r0, r2, r4)
            android.content.IntentFilter r1 = new android.content.IntentFilter
            r1.<init>()
            r9 = r1
            java.lang.String r1 = "android.intent.action.SIM_STATE_CHANGED"
            r9.addAction(r1)
            android.content.Context r1 = r0.mContext
            android.content.BroadcastReceiver r2 = r0.mSimStateIntentReceiver
            r1.registerReceiver(r2, r9)
            com.qualcomm.qti.internal.telephony.QtiDdsSwitchController r10 = new com.qualcomm.qti.internal.telephony.QtiDdsSwitchController
            boolean r7 = isLplusLSupported
            r1 = r10
            r2 = r8
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r19
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r0.mQtiDdsSwitchController = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher.<init>(int, int, android.content.Context, com.android.internal.telephony.SubscriptionController, android.os.Looper, com.android.internal.telephony.ITelephonyRegistry, com.android.internal.telephony.CommandsInterface[], com.android.internal.telephony.Phone[]):void");
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

    /* JADX WARNING: type inference failed for: r6v0, types: [com.qualcomm.qti.internal.telephony.QtiPhoneSwitcher, android.os.Handler, com.android.internal.telephony.PhoneSwitcher] */
    public void handleMessage(Message msg) {
        int ddsSubId = this.mSubscriptionController.getDefaultDataSubId();
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(ddsSubId);
        StringBuilder sb = new StringBuilder();
        sb.append("handle event - ");
        sb.append(msg.what);
        log(sb.toString());
        int i = 0;
        switch (msg.what) {
            case 101:
                boolean isLplTempSwitch = SystemProperties.getBoolean("persist.vendor.radio.enable_temp_dds", false);
                if (!isAnyVoiceCallActiveOnDevice() || isLplTempSwitch) {
                    onEvaluate(false, "defaultChanged");
                    break;
                } else {
                    log("Voice call active. Waiting for call end");
                    return;
                }
            case 102:
                broadcastNetworkSpecifier();
                onEvaluate(false, "subChanged");
                break;
            case 107:
                log("EVENT_VOICE_CALL_ENDED");
                if (!isAnyVoiceCallActiveOnDevice()) {
                    while (true) {
                        int i2 = i;
                        if (i2 < this.mNumPhones) {
                            if (getConnectFailureCount(i2) > 0 && isPhoneIdValidForRetry(i2)) {
                                resendDataAllowed(i2);
                                break;
                            } else {
                                i = i2 + 1;
                            }
                        } else {
                            break;
                        }
                    }
                }
                break;
            case 108:
                AsyncResult ar = (AsyncResult) msg.obj;
                if (ar.result == null) {
                    log("Error: empty result, EVENT_UNSOL_MAX_DATA_ALLOWED_CHANGED");
                    break;
                } else {
                    handleUnsolMaxDataAllowedChange((Message) ar.result);
                    break;
                }
            case DataCallFailCause.APN_TYPE_CONFLICT /*112*/:
                log("EVENT_ALLOW_DATA_FALSE_RESPONSE");
                this.mWaitForDetachResponse = false;
                informDdsToRil(ddsSubId);
                for (Integer intValue : this.mNewActivePhones) {
                    activate(intValue.intValue());
                }
                if (this.mNewActivePhones.contains(Integer.valueOf(ddsPhoneId))) {
                    this.mManualDdsSwitch = false;
                    break;
                }
                break;
            case DataCallFailCause.INVALID_PCSCF_ADDR /*113*/:
                onAllowDataResponse(msg.arg1, (AsyncResult) msg.obj);
                break;
            case DataCallFailCause.INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN /*114*/:
                if (!this.mQtiRilInterface.isServiceReady()) {
                    log("Oem hook service is not ready");
                    break;
                } else {
                    queryMaxDataAllowed();
                    isLplusLSupported = this.mQtiRilInterface.getLpluslSupportStatus();
                    this.mQtiDdsSwitchController.updateLplusLStatus(isLplusLSupported);
                    while (true) {
                        int i3 = i;
                        if (i3 >= this.mCommandsInterfaces.length) {
                            break;
                        } else {
                            this.mCommandsInterfaces[i3].unregisterForAvailable(this);
                            i = i3 + 1;
                        }
                    }
                }
            default:
                QtiPhoneSwitcher.super.handleMessage(msg);
                break;
        }
    }

    private void broadcastNetworkSpecifier() {
        ArrayList<Integer> subIdList = new ArrayList<>();
        for (int i = 0; i < this.mNumPhones; i++) {
            int[] subId = this.mSubscriptionController.getSubId(i);
            if (subId != null && subId.length > 0 && this.mSubscriptionController.isActiveSubId(subId[0]) && isUiccProvisioned(i)) {
                subIdList.add(Integer.valueOf(subId[0]));
            }
        }
        if (subIdList.size() > 0) {
            Intent intent = new Intent("org.codeaurora.intent.action.ACTION_NETWORK_SPECIFIER_SET");
            intent.putIntegerArrayListExtra("SubIdList", subIdList);
            log("Broadcast network specifier set intent");
            this.mContext.sendBroadcast(intent);
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
        StringBuilder sb = new StringBuilder();
        sb.append("SIM READY for phoneId: ");
        sb.append(phoneId);
        log(sb.toString());
        return true;
    }

    /* access modifiers changed from: protected */
    public void onEvaluate(boolean requestsChanged, String reason) {
        StringBuilder sb = new StringBuilder(reason);
        boolean diffDetected = requestsChanged;
        int dataSubId = this.mSubscriptionController.getDefaultDataSubId();
        int ddsPhoneId = this.mSubscriptionController.getPhoneId(dataSubId);
        if ((this.mSubscriptionController.isActiveSubId(dataSubId) && dataSubId != this.mDefaultDataSubscription) || !(ddsPhoneId == -1 || ddsPhoneId == this.mDefaultDataPhoneId)) {
            sb.append(" default ");
            sb.append(this.mDefaultDataSubscription);
            sb.append("->");
            sb.append(dataSubId);
            this.mManualDdsSwitch = true;
            this.mSendDdsSwitchDoneIntent = true;
            this.mDefaultDataSubscription = dataSubId;
            this.mDefaultDataPhoneId = ddsPhoneId;
            diffDetected = true;
        }
        boolean diffDetected2 = diffDetected;
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
                diffDetected2 = true;
            }
        }
        if (isEmergency() != 0) {
            log("onEvalaute aborted due to Emergency");
            return;
        }
        if (diffDetected2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("evaluating due to ");
            sb2.append(sb.toString());
            log(sb2.toString());
            List<Integer> newActivePhones = new ArrayList<>();
            for (DcRequest dcRequest : this.mPrioritizedDcRequests) {
                int phoneIdForRequest = phoneIdForRequest(dcRequest.networkRequest, dcRequest.apnId);
                if (phoneIdForRequest != -1 && !newActivePhones.contains(Integer.valueOf(phoneIdForRequest))) {
                    newActivePhones.add(Integer.valueOf(phoneIdForRequest));
                    if (newActivePhones.size() >= this.mMaxActivePhones) {
                        break;
                    }
                }
            }
            this.mNewActivePhones = newActivePhones;
            for (int phoneId = 0; phoneId < this.mNumPhones; phoneId++) {
                if (!newActivePhones.contains(Integer.valueOf(phoneId))) {
                    deactivate(phoneId);
                }
            }
            if (this.mWaitForDetachResponse == 0) {
                informDdsToRil(dataSubId);
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
        } else if ("defaultChanged".equals(reason) && this.mSendDdsSwitchDoneIntent) {
            this.mSendDdsSwitchDoneIntent = false;
            Intent intent = new Intent("org.codeaurora.intent.action.ACTION_DDS_SWITCH_DONE");
            intent.putExtra("subscription", dataSubId);
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Dds already switch done on sub ");
            sb3.append(dataSubId);
            sb3.append(", send the broadcast right now!");
            log(sb3.toString());
            this.mContext.sendBroadcast(intent);
        }
    }

    /* access modifiers changed from: protected */
    public int phoneIdForRequest(NetworkRequest netRequest, int apnid) {
        int subId;
        String specifier = null;
        NetworkSpecifier networkSpecifierObj = netRequest.networkCapabilities.getNetworkSpecifier();
        if (networkSpecifierObj != null && (networkSpecifierObj instanceof StringNetworkSpecifier)) {
            specifier = ((StringNetworkSpecifier) networkSpecifierObj).specifier;
        }
        if (TextUtils.isEmpty(specifier)) {
            subId = this.mDefaultDataSubscription;
        } else if (5 != apnid || !this.mManualDdsSwitch || this.mMaxActivePhones == this.mNumPhones) {
            try {
                subId = Integer.parseInt(specifier);
            } catch (NumberFormatException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("NumberFormatException on ");
                sb.append(specifier);
                Rlog.e("PhoneSwitcher", sb.toString());
                subId = -1;
            }
        } else {
            subId = this.mDefaultDataSubscription;
        }
        int phoneId = -1;
        if (subId == -1) {
            return -1;
        }
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
            state.active = false;
            StringBuilder sb = new StringBuilder();
            sb.append("deactivate ");
            sb.append(phoneId);
            log(sb.toString());
            state.lastRequested = System.currentTimeMillis();
            if (this.mSubscriptionController.isActiveSubId(this.mPhoneSubscriptions[phoneId])) {
                this.mCommandsInterfaces[phoneId].setDataAllowed(false, obtainMessage(DataCallFailCause.APN_TYPE_CONFLICT));
                this.mWaitForDetachResponse = true;
            }
            this.mActivePhoneRegistrants[phoneId].notifyRegistrants();
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
            this.mCommandsInterfaces[phoneId].setDataAllowed(true, obtainMessage(DataCallFailCause.INVALID_PCSCF_ADDR, phoneId, 0));
        }
    }

    /* access modifiers changed from: protected */
    public void onResendDataAllowed(Message msg) {
        int phoneId = msg.arg1;
        this.mCommandsInterfaces[phoneId].setDataAllowed(this.mPhoneStates[phoneId].active, obtainMessage(DataCallFailCause.INVALID_PCSCF_ADDR, phoneId, 0));
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
        if (SubscriptionManager.isValidPhoneId(ddsPhoneId) && phoneId != ddsPhoneId) {
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

    private void onAllowDataResponse(final int phoneId, AsyncResult ar) {
        if (ar.exception != null) {
            incConnectFailureCount(phoneId);
            StringBuilder sb = new StringBuilder();
            sb.append("Allow_data failed on phoneId = ");
            sb.append(phoneId);
            sb.append(", failureCount = ");
            sb.append(getConnectFailureCount(phoneId));
            log(sb.toString());
            if (isAnyVoiceCallActiveOnDevice()) {
                log("Wait for call end indication");
            } else if (!isSimReady(phoneId)) {
                log("Wait for SIM to get READY");
            } else {
                int allowDataFailureCount = getConnectFailureCount(phoneId);
                if (allowDataFailureCount > 5) {
                    handleConnectMaxFailure(phoneId);
                } else {
                    int retryDelay = this.mRetryArray[allowDataFailureCount - 1] * 1000;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Scheduling retry connect/allow_data after: ");
                    sb2.append(retryDelay);
                    log(sb2.toString());
                    postDelayed(new Runnable() {
                        public void run() {
                            QtiPhoneSwitcher.this.log("Running retry connect/allow_data");
                            if (QtiPhoneSwitcher.this.isPhoneIdValidForRetry(phoneId)) {
                                QtiPhoneSwitcher.this.resendDataAllowed(phoneId);
                                return;
                            }
                            QtiPhoneSwitcher.this.log("Abandon Retry");
                            QtiPhoneSwitcher.this.resetConnectFailureCount(phoneId);
                        }
                    }, (long) retryDelay);
                }
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Allow_data success on phoneId = ");
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
            this.mActivePhoneRegistrants[phoneId].notifyRegistrants();
            QtiDdsSwitchController qtiDdsSwitchController = this.mQtiDdsSwitchController;
            if (QtiDdsSwitchController.isTempDdsSwitchRequired()) {
                this.mQtiDdsSwitchController.resetTempDdsSwitchRequired();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isPhoneIdValidForRetry(int phoneId) {
        int phoneIdForRequest = -1;
        if (this.mPrioritizedDcRequests.size() > 0) {
            DcRequest dcRequest = (DcRequest) this.mPrioritizedDcRequests.get(0);
            phoneIdForRequest = phoneIdForRequest(dcRequest.networkRequest, dcRequest.apnId);
        }
        if (phoneIdForRequest == phoneId) {
            return true;
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
            QtiDdsSwitchController qtiDdsSwitchController = this.mQtiDdsSwitchController;
            if (QtiDdsSwitchController.isTempDdsSwitchRequired()) {
                this.mQtiRilInterface.qcRilSendDDSInfo(ddsPhoneId, 1, i);
            } else {
                this.mQtiRilInterface.qcRilSendDDSInfo(ddsPhoneId, 0, i);
            }
        }
    }
}
