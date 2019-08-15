package com.qualcomm.qti.internal.telephony;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.Call.State;
import com.android.internal.telephony.GsmCdmaCall;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCall;

public class QtiCallStateNotifier {
    protected static QtiCallStateNotifier mInstance = null;
    private final int EVENT_PRECISE_CS_CALL_STATE_CHANGED;
    private final int EVENT_PRECISE_IMS_CALL_STATE_CHANGED;
    private final String LOG_TAG = "QtiCallStateNotifier";
    private GsmCdmaCall[] mBgCsCalls;
    private ImsPhoneCall[] mBgImsCalls;
    /* access modifiers changed from: private */
    public RegistrantList mCallEndRegistrants;
    private CallStateHandler mCallStateHandler;
    private GsmCdmaCall[] mFgCsCalls;
    private ImsPhoneCall[] mFgImsCalls;
    private ImsPhone[] mImsPhones;
    /* access modifiers changed from: private */
    public boolean mIsCallInActiveState;
    /* access modifiers changed from: private */
    public boolean mIsCallInProgress;
    private final int mNumPhones;
    private Phone[] mPhones;
    private GsmCdmaCall[] mRiCsCalls;
    private ImsPhoneCall[] mRiImsCalls;

    private class CallStateHandler extends Handler {
        private CallStateHandler() {
        }

        public void handleMessage(Message msg) {
            int phoneId = ((Integer) ((AsyncResult) msg.obj).userObj).intValue();
            switch (msg.what) {
                case 101:
                case 102:
                    if (!QtiCallStateNotifier.this.mIsCallInActiveState && QtiCallStateNotifier.this.isCallActive(phoneId)) {
                        QtiCallStateNotifier qtiCallStateNotifier = QtiCallStateNotifier.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("processCallStateChanged: call active on phone");
                        sb.append(phoneId);
                        qtiCallStateNotifier.log(sb.toString());
                        QtiCallStateNotifier.this.mIsCallInActiveState = true;
                        return;
                    } else if (QtiCallStateNotifier.this.isCallIdle(phoneId)) {
                        QtiCallStateNotifier qtiCallStateNotifier2 = QtiCallStateNotifier.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("processCallStateChanged: call disconnected on phone");
                        sb2.append(phoneId);
                        qtiCallStateNotifier2.log(sb2.toString());
                        QtiCallStateNotifier.this.mIsCallInActiveState = false;
                        QtiCallStateNotifier.this.mIsCallInProgress = false;
                        QtiCallStateNotifier.this.mCallEndRegistrants.notifyRegistrants();
                        return;
                    } else if (!QtiCallStateNotifier.this.mIsCallInProgress) {
                        QtiCallStateNotifier qtiCallStateNotifier3 = QtiCallStateNotifier.this;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("processCallStateChanged: call started on phone");
                        sb3.append(phoneId);
                        qtiCallStateNotifier3.log(sb3.toString());
                        QtiCallStateNotifier.this.mIsCallInProgress = true;
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public static void init(Phone[] phones) {
        synchronized (QtiCallStateNotifier.class) {
            if (mInstance == null) {
                mInstance = new QtiCallStateNotifier(phones);
            }
        }
    }

    public static QtiCallStateNotifier getInstance() {
        QtiCallStateNotifier qtiCallStateNotifier;
        synchronized (QtiCallStateNotifier.class) {
            if (mInstance != null) {
                qtiCallStateNotifier = mInstance;
            } else {
                throw new RuntimeException("QtiCallStateNotifier is not initialized!");
            }
        }
        return qtiCallStateNotifier;
    }

    private QtiCallStateNotifier(Phone[] phones) {
        this.mIsCallInActiveState = false;
        this.mIsCallInProgress = false;
        this.mCallEndRegistrants = new RegistrantList();
        this.EVENT_PRECISE_CS_CALL_STATE_CHANGED = 101;
        this.EVENT_PRECISE_IMS_CALL_STATE_CHANGED = 102;
        this.mNumPhones = TelephonyManager.getDefault().getPhoneCount();
        this.mPhones = phones;
        this.mImsPhones = new ImsPhone[this.mNumPhones];
        this.mFgCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mBgCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mRiCsCalls = new GsmCdmaCall[this.mNumPhones];
        this.mFgImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mBgImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mRiImsCalls = new ImsPhoneCall[this.mNumPhones];
        this.mCallStateHandler = new CallStateHandler();
        for (int i = 0; i < this.mNumPhones; i++) {
            if (this.mPhones[i] != null) {
                this.mPhones[i].registerForPreciseCallStateChanged(this.mCallStateHandler, 101, Integer.valueOf(i));
                this.mFgCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getForegroundCall();
                this.mBgCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getBackgroundCall();
                this.mRiCsCalls[i] = (GsmCdmaCall) this.mPhones[i].getRingingCall();
            }
            this.mImsPhones[i] = (ImsPhone) this.mPhones[i].getImsPhone();
            if (this.mImsPhones[i] != null) {
                this.mImsPhones[i].registerForPreciseCallStateChanged(this.mCallStateHandler, 102, Integer.valueOf(i));
                this.mFgImsCalls[i] = this.mImsPhones[i].getForegroundCall();
                this.mBgImsCalls[i] = this.mImsPhones[i].getBackgroundCall();
                this.mRiImsCalls[i] = this.mImsPhones[i].getRingingCall();
            }
        }
    }

    public boolean isCallActive() {
        return this.mIsCallInActiveState;
    }

    public boolean isCallInProgress() {
        return this.mIsCallInProgress;
    }

    public void registerForCallEnd(Handler h, int what, Object o) {
        this.mCallEndRegistrants.addUnique(h, what, o);
    }

    public void unregisterForCallEnd(Handler h) {
        this.mCallEndRegistrants.remove(h);
    }

    /* access modifiers changed from: private */
    public boolean isCallActive(int phoneId) {
        return this.mFgCsCalls[phoneId].getState() == State.ACTIVE || this.mBgCsCalls[phoneId].getState() == State.ACTIVE || this.mRiCsCalls[phoneId].getState() == State.ACTIVE || this.mFgImsCalls[phoneId].getState() == State.ACTIVE || this.mBgImsCalls[phoneId].getState() == State.ACTIVE || this.mRiImsCalls[phoneId].getState() == State.ACTIVE;
    }

    /* access modifiers changed from: private */
    public boolean isCallIdle(int phoneId) {
        return this.mFgCsCalls[phoneId].isIdle() && this.mBgCsCalls[phoneId].isIdle() && this.mRiCsCalls[phoneId].isIdle() && this.mFgImsCalls[phoneId].isIdle() && this.mBgImsCalls[phoneId].isIdle() && this.mRiImsCalls[phoneId].isIdle();
    }

    /* access modifiers changed from: protected */
    public void log(String l) {
        Rlog.d("QtiCallStateNotifier", l);
    }
}
