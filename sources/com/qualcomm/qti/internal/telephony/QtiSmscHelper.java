package com.qualcomm.qti.internal.telephony;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class QtiSmscHelper extends Handler {
    private static final int EVENT_GET_SMSC = 2;
    private static final int EVENT_SET_SMSC = 1;
    private static final String LOG_TAG = "QtiSmscHelper";
    private static final int PHONE_COUNT = TelephonyManager.getDefault().getPhoneCount();
    private Object mGetLock = new Object();
    private final Phone[] mPhones = PhoneFactory.getPhones();
    private Object mSetLock = new Object();
    private final AtomicReferenceArray mSmscArray = new AtomicReferenceArray(new String[PHONE_COUNT]);
    private volatile boolean mSuccess = false;

    public QtiSmscHelper() {
        super(Looper.getMainLooper());
    }

    public void handleMessage(Message msg) {
        boolean z;
        AsyncResult ar = (AsyncResult) msg.obj;
        switch (msg.what) {
            case 1:
                synchronized (this.mSetLock) {
                    if (ar != null) {
                        try {
                            if (ar.exception == null) {
                                z = true;
                                this.mSuccess = z;
                                this.mSetLock.notifyAll();
                            }
                        } finally {
                        }
                    }
                    z = false;
                    this.mSuccess = z;
                    this.mSetLock.notifyAll();
                }
                return;
            case 2:
                synchronized (this.mGetLock) {
                    if (ar != null) {
                        try {
                            if (ar.exception == null) {
                                String str = LOG_TAG;
                                StringBuilder sb = new StringBuilder();
                                sb.append("smsc = ");
                                sb.append(ar.result);
                                sb.append(" on phone = ");
                                sb.append(msg.arg1);
                                Rlog.d(str, sb.toString());
                                this.mSmscArray.set(msg.arg1, ar.result);
                            }
                        } finally {
                        }
                    }
                    this.mGetLock.notifyAll();
                }
                return;
            default:
                return;
        }
    }

    public boolean setSmscAddress(int slotId, String smsc) {
        if (!isValidPhoneId(slotId)) {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid phone id = ");
            sb.append(slotId);
            Rlog.d(str, sb.toString());
            return false;
        }
        synchronized (this.mSetLock) {
            if (TextUtils.equals((String) this.mSmscArray.get(slotId), smsc)) {
                String str2 = LOG_TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("the same smsc is there on phone = ");
                sb2.append(slotId);
                Rlog.d(str2, sb2.toString());
                return true;
            }
            this.mPhones[slotId].setSmscAddress(smsc, obtainMessage(1, slotId, -1));
            try {
                this.mSetLock.wait();
            } catch (InterruptedException e) {
            }
            if (this.mSuccess) {
                this.mSmscArray.set(slotId, smsc);
            }
            boolean z = this.mSuccess;
            return z;
        }
    }

    public String getSmscAddress(int slotId) {
        String str;
        if (!isValidPhoneId(slotId)) {
            String str2 = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid phone id = ");
            sb.append(slotId);
            Rlog.d(str2, sb.toString());
            return null;
        }
        synchronized (this.mGetLock) {
            this.mPhones[slotId].getSmscAddress(obtainMessage(2, slotId, -1));
            try {
                this.mGetLock.wait();
            } catch (InterruptedException e) {
            }
            str = (String) this.mSmscArray.get(slotId);
        }
        return str;
    }

    private boolean isValidPhoneId(int slotId) {
        return slotId >= 0 && slotId < PHONE_COUNT;
    }
}
