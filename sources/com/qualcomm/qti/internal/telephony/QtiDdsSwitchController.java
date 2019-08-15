package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.telephony.Rlog;
import com.android.internal.telephony.Call.State;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCall;

public class QtiDdsSwitchController {
    public static boolean mTempDdsSwitchRequired = false;
    private final int EVENT_PRECISE_CS_CALL_STATE_CHANGED;
    private final int EVENT_PRECISE_IMS_CALL_STATE_CHANGED;
    private final String LOG_TAG = "QtiDdsSwitchController";
    private final String PROPERTY_TEMP_DDSSWITCH;
    private boolean isLplusLSupported;
    private boolean isPropertyEnabled;
    private final GsmCdmaCall[] mBgCsCalls;
    private final ImsPhoneCall[] mBgImsCalls;
    private final Context mContext;
    private final Handler mDdsSwitchHandler;
    private final GsmCdmaCall[] mFgCsCalls;
    private final ImsPhoneCall[] mFgImsCalls;
    private final ImsPhone[] mImsPhones;
    private boolean mIsCallActive;
    /* access modifiers changed from: private */
    public boolean mNotifyCallState;
    private final int mNumPhones;
    /* access modifiers changed from: private */
    public final Phone[] mPhones;
    private final GsmCdmaCall[] mRiCsCalls;
    private final ImsPhoneCall[] mRiImsCalls;
    /* access modifiers changed from: private */
    public final SubscriptionController mSubscriptionController;
    /* access modifiers changed from: private */
    public int mUserDdsSubId;

    private class DdsSwitchHandler extends Handler {
        public DdsSwitchHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            int phoneId = ((Integer) ((AsyncResult) msg.obj).userObj).intValue();
            switch (msg.what) {
                case 101:
                case 102:
                    if (!QtiDdsSwitchController.this.mNotifyCallState) {
                        QtiDdsSwitchController.this.mUserDdsSubId = QtiDdsSwitchController.this.mSubscriptionController.getDefaultDataSubId();
                        int voiceRat = QtiDdsSwitchController.this.mPhones[phoneId].getServiceState().getRilVoiceRadioTechnology();
                        QtiDdsSwitchController.this.mNotifyCallState = (!QtiDdsSwitchController.this.isFeatureEnabled() || !QtiDdsSwitchController.this.isCallOnNonDds(phoneId) || voiceRat == 0 || voiceRat == 6 || voiceRat == 4 || voiceRat == 5) ? false : true;
                        QtiDdsSwitchController qtiDdsSwitchController = QtiDdsSwitchController.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("mUserDdsSubId - ");
                        sb.append(QtiDdsSwitchController.this.mUserDdsSubId);
                        sb.append(", voiceRat - ");
                        sb.append(voiceRat);
                        qtiDdsSwitchController.log(sb.toString());
                    }
                    if (QtiDdsSwitchController.this.mNotifyCallState) {
                        QtiDdsSwitchController.this.log("EVENT_PRECISE_CALL_STATE_CHANGED");
                        QtiDdsSwitchController.this.processCallStateChanged(phoneId);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public QtiDdsSwitchController(int numPhones, Context context, SubscriptionController subscriptionController, Looper looper, Phone[] phones, boolean status) {
        this.mIsCallActive = false;
        this.mNotifyCallState = false;
        this.mUserDdsSubId = -1;
        this.PROPERTY_TEMP_DDSSWITCH = "persist.vendor.radio.enable_temp_dds";
        this.isPropertyEnabled = SystemProperties.getBoolean("persist.vendor.radio.enable_temp_dds", false);
        this.isLplusLSupported = false;
        this.EVENT_PRECISE_CS_CALL_STATE_CHANGED = 101;
        this.EVENT_PRECISE_IMS_CALL_STATE_CHANGED = 102;
        this.mNumPhones = numPhones;
        this.mContext = context;
        this.mSubscriptionController = subscriptionController;
        this.mDdsSwitchHandler = new DdsSwitchHandler(looper);
        this.mPhones = phones;
        this.mImsPhones = new ImsPhone[this.mNumPhones];
        this.mFgCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mBgCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mRiCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mFgImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mBgImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mRiImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.isLplusLSupported = status;
        for (int i = 0; i < this.mNumPhones; i++) {
            this.mImsPhones[i] = (ImsPhone) this.mPhones[i].getImsPhone();
            if (!(this.mPhones[i] == null || this.mImsPhones[i] == null)) {
                this.mFgCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getForegroundCall();
                this.mBgCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getBackgroundCall();
                this.mRiCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getRingingCall();
                this.mFgImsCalls[i] = this.mImsPhones[i].getForegroundCall();
                this.mBgImsCalls[i] = this.mImsPhones[i].getBackgroundCall();
                this.mRiImsCalls[i] = this.mImsPhones[i].getRingingCall();
            }
        }
    }

    private void onCallStarted(int phoneId) {
        this.mUserDdsSubId = this.mSubscriptionController.getDefaultDataSubId();
        int callSubId = this.mSubscriptionController.getSubId(phoneId)[0];
        mTempDdsSwitchRequired = true;
        StringBuilder sb = new StringBuilder();
        sb.append("Trigger temporary DDS switch to sub: ");
        sb.append(callSubId);
        log(sb.toString());
        this.mSubscriptionController.setDefaultDataSubId(callSubId);
    }

    private void onCallEnded(int phoneId) {
        mTempDdsSwitchRequired = false;
        if (this.mSubscriptionController.isActiveSubId(this.mUserDdsSubId)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Set DDS to actual sub: ");
            sb.append(this.mUserDdsSubId);
            log(sb.toString());
            this.mSubscriptionController.setDefaultDataSubId(this.mUserDdsSubId);
            return;
        }
        log("User dds sub is invalid, skip dds reset");
    }

    public static boolean isTempDdsSwitchRequired() {
        return mTempDdsSwitchRequired;
    }

    public void resetTempDdsSwitchRequired() {
        mTempDdsSwitchRequired = false;
    }

    public void updateLplusLStatus(boolean status) {
        this.isLplusLSupported = status;
        StringBuilder sb = new StringBuilder();
        sb.append("updateLplusLStatus - status: ");
        sb.append(status);
        log(sb.toString());
        int i = 0;
        if (this.isLplusLSupported) {
            while (true) {
                int i2 = i;
                if (i2 < this.mNumPhones) {
                    if (this.mPhones[i2] != null) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Register for call state change on phone: ");
                        sb2.append(i2);
                        log(sb2.toString());
                        this.mPhones[i2].registerForPreciseCallStateChanged(this.mDdsSwitchHandler, 101, Integer.valueOf(i2));
                    }
                    if (this.mImsPhones[i2] != null) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Register for call state change on ims phone: ");
                        sb3.append(i2);
                        log(sb3.toString());
                        this.mImsPhones[i2].registerForPreciseCallStateChanged(this.mDdsSwitchHandler, 102, Integer.valueOf(i2));
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } else {
            while (true) {
                int i3 = i;
                if (i3 < this.mNumPhones) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Unregister for call state change on phone: ");
                    sb4.append(i3);
                    log(sb4.toString());
                    if (this.mPhones[i3] != null) {
                        this.mPhones[i3].unregisterForPreciseCallStateChanged(this.mDdsSwitchHandler);
                    }
                    if (this.mImsPhones[i3] != null) {
                        this.mImsPhones[i3].unregisterForPreciseCallStateChanged(this.mDdsSwitchHandler);
                    }
                    i = i3 + 1;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isFeatureEnabled() {
        this.isPropertyEnabled = SystemProperties.getBoolean("persist.vendor.radio.enable_temp_dds", false);
        StringBuilder sb = new StringBuilder();
        sb.append("isPropertyEnabled: ");
        sb.append(this.isPropertyEnabled);
        log(sb.toString());
        return this.isPropertyEnabled;
    }

    private boolean isCallActive(int phoneId) {
        return this.mFgCsCalls[phoneId].getState() == State.ACTIVE || this.mBgCsCalls[phoneId].getState() == State.ACTIVE || this.mRiCsCalls[phoneId].getState() == State.ACTIVE || this.mFgImsCalls[phoneId].getState() == State.ACTIVE || this.mBgImsCalls[phoneId].getState() == State.ACTIVE || this.mRiImsCalls[phoneId].getState() == State.ACTIVE;
    }

    private boolean isCallIdle(int phoneId) {
        return this.mFgCsCalls[phoneId].isIdle() && this.mBgCsCalls[phoneId].isIdle() && this.mRiCsCalls[phoneId].isIdle() && this.mFgImsCalls[phoneId].isIdle() && this.mBgImsCalls[phoneId].isIdle() && this.mRiImsCalls[phoneId].isIdle();
    }

    /* access modifiers changed from: private */
    public boolean isCallOnNonDds(int phoneId) {
        return phoneId != this.mSubscriptionController.getPhoneId(this.mUserDdsSubId);
    }

    /* access modifiers changed from: private */
    public void processCallStateChanged(int phoneId) {
        if (!this.mIsCallActive && isCallActive(phoneId)) {
            log("notifyCallStateChanged: call active on non dds");
            this.mIsCallActive = true;
            onCallStarted(phoneId);
        } else if (isCallIdle(phoneId)) {
            this.mNotifyCallState = false;
            if (this.mIsCallActive) {
                log("notifyCallStateChanged: call disconnected on non dds");
                this.mIsCallActive = false;
                onCallEnded(phoneId);
            }
        } else {
            log("ignore call state change");
        }
    }

    /* access modifiers changed from: protected */
    public void log(String l) {
        Rlog.d("QtiDdsSwitchController", l);
    }
}
