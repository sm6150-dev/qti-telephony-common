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
    private final int EVENT_PRECISE_CS_CALL_STATE_CHANGED = 101;
    private final int EVENT_PRECISE_IMS_CALL_STATE_CHANGED = 102;
    private final String LOG_TAG = "QtiCallStateNotifier";
    private GsmCdmaCall[] mBgCsCalls;
    private ImsPhoneCall[] mBgImsCalls;
    /* access modifiers changed from: private */
    public RegistrantList mCallEndRegistrants = new RegistrantList();
    private CallStateHandler mCallStateHandler;
    private GsmCdmaCall[] mFgCsCalls;
    private ImsPhoneCall[] mFgImsCalls;
    private ImsPhone[] mImsPhones;
    /* access modifiers changed from: private */
    public boolean mIsCallInActiveState = false;
    /* access modifiers changed from: private */
    public boolean mIsCallInProgress = false;
    private final int mNumPhones = TelephonyManager.getDefault().getPhoneCount();
    private Phone[] mPhones;
    private GsmCdmaCall[] mRiCsCalls;
    private ImsPhoneCall[] mRiImsCalls;

    private class CallStateHandler extends Handler {
        private CallStateHandler() {
        }

        public void handleMessage(Message msg) {
            int phoneId = ((Integer) ((AsyncResult) msg.obj).userObj).intValue();
            int i = msg.what;
            if (i != 101 && i != 102) {
                return;
            }
            if (!QtiCallStateNotifier.this.mIsCallInActiveState && QtiCallStateNotifier.this.isCallActive(phoneId)) {
                QtiCallStateNotifier qtiCallStateNotifier = QtiCallStateNotifier.this;
                StringBuilder sb = new StringBuilder();
                sb.append("processCallStateChanged: call active on phone");
                sb.append(phoneId);
                qtiCallStateNotifier.log(sb.toString());
                QtiCallStateNotifier.this.mIsCallInActiveState = true;
            } else if (QtiCallStateNotifier.this.isCallIdle(phoneId)) {
                QtiCallStateNotifier qtiCallStateNotifier2 = QtiCallStateNotifier.this;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("processCallStateChanged: call disconnected on phone");
                sb2.append(phoneId);
                qtiCallStateNotifier2.log(sb2.toString());
                QtiCallStateNotifier.this.mIsCallInActiveState = false;
                QtiCallStateNotifier.this.mIsCallInProgress = false;
                QtiCallStateNotifier.this.mCallEndRegistrants.notifyRegistrants();
            } else if (!QtiCallStateNotifier.this.mIsCallInProgress) {
                QtiCallStateNotifier qtiCallStateNotifier3 = QtiCallStateNotifier.this;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("processCallStateChanged: call started on phone");
                sb3.append(phoneId);
                qtiCallStateNotifier3.log(sb3.toString());
                QtiCallStateNotifier.this.mIsCallInProgress = true;
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
        this.mPhones = phones;
        int i = this.mNumPhones;
        this.mImsPhones = new ImsPhone[i];
        this.mFgCsCalls = new GsmCdmaCall[i];
        this.mBgCsCalls = new GsmCdmaCall[i];
        this.mRiCsCalls = new GsmCdmaCall[i];
        this.mFgImsCalls = new ImsPhoneCall[i];
        this.mBgImsCalls = new ImsPhoneCall[i];
        this.mRiImsCalls = new ImsPhoneCall[i];
        this.mCallStateHandler = new CallStateHandler();
        for (int i2 = 0; i2 < this.mNumPhones; i2++) {
            Phone[] phoneArr = this.mPhones;
            if (phoneArr[i2] != null) {
                phoneArr[i2].registerForPreciseCallStateChanged(this.mCallStateHandler, 101, Integer.valueOf(i2));
                this.mFgCsCalls[i2] = (GsmCdmaCall) this.mPhones[i2].getForegroundCall();
                this.mBgCsCalls[i2] = (GsmCdmaCall) this.mPhones[i2].getBackgroundCall();
                this.mRiCsCalls[i2] = (GsmCdmaCall) this.mPhones[i2].getRingingCall();
            }
            this.mImsPhones[i2] = (ImsPhone) this.mPhones[i2].getImsPhone();
            ImsPhone[] imsPhoneArr = this.mImsPhones;
            if (imsPhoneArr[i2] != null) {
                imsPhoneArr[i2].registerForPreciseCallStateChanged(this.mCallStateHandler, 102, Integer.valueOf(i2));
                this.mFgImsCalls[i2] = this.mImsPhones[i2].getForegroundCall();
                this.mBgImsCalls[i2] = this.mImsPhones[i2].getBackgroundCall();
                this.mRiImsCalls[i2] = this.mImsPhones[i2].getRingingCall();
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
        GsmCdmaCall[] gsmCdmaCallArr = this.mFgCsCalls;
        if (gsmCdmaCallArr[phoneId] == null || gsmCdmaCallArr[phoneId].getState() != State.ACTIVE) {
            GsmCdmaCall[] gsmCdmaCallArr2 = this.mBgCsCalls;
            if (gsmCdmaCallArr2[phoneId] == null || gsmCdmaCallArr2[phoneId].getState() != State.ACTIVE) {
                GsmCdmaCall[] gsmCdmaCallArr3 = this.mRiCsCalls;
                if (gsmCdmaCallArr3[phoneId] == null || gsmCdmaCallArr3[phoneId].getState() != State.ACTIVE) {
                    ImsPhoneCall[] imsPhoneCallArr = this.mFgImsCalls;
                    if (imsPhoneCallArr[phoneId] == null || imsPhoneCallArr[phoneId].getState() != State.ACTIVE) {
                        ImsPhoneCall[] imsPhoneCallArr2 = this.mBgImsCalls;
                        if (imsPhoneCallArr2[phoneId] == null || imsPhoneCallArr2[phoneId].getState() != State.ACTIVE) {
                            ImsPhoneCall[] imsPhoneCallArr3 = this.mRiImsCalls;
                            if (imsPhoneCallArr3[phoneId] == null || imsPhoneCallArr3[phoneId].getState() != State.ACTIVE) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isCallIdle(int phoneId) {
        GsmCdmaCall[] gsmCdmaCallArr = this.mFgCsCalls;
        if (gsmCdmaCallArr[phoneId] == null || gsmCdmaCallArr[phoneId].isIdle()) {
            GsmCdmaCall[] gsmCdmaCallArr2 = this.mBgCsCalls;
            if (gsmCdmaCallArr2[phoneId] == null || gsmCdmaCallArr2[phoneId].isIdle()) {
                GsmCdmaCall[] gsmCdmaCallArr3 = this.mRiCsCalls;
                if (gsmCdmaCallArr3[phoneId] == null || gsmCdmaCallArr3[phoneId].isIdle()) {
                    ImsPhoneCall[] imsPhoneCallArr = this.mFgImsCalls;
                    if (imsPhoneCallArr[phoneId] == null || imsPhoneCallArr[phoneId].isIdle()) {
                        ImsPhoneCall[] imsPhoneCallArr2 = this.mBgImsCalls;
                        if (imsPhoneCallArr2[phoneId] == null || imsPhoneCallArr2[phoneId].isIdle()) {
                            ImsPhoneCall[] imsPhoneCallArr3 = this.mRiImsCalls;
                            if (imsPhoneCallArr3[phoneId] == null || imsPhoneCallArr3[phoneId].isIdle()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void log(String l) {
        Rlog.d("QtiCallStateNotifier", l);
    }
}
