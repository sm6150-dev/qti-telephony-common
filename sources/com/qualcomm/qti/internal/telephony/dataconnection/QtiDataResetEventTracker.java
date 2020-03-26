package com.qualcomm.qti.internal.telephony.dataconnection;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Pair;
import com.android.internal.telephony.Phone;

public class QtiDataResetEventTracker {
    private static final boolean DBG = true;
    private Context mContext = null;
    private QtiDataConnection mDc;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Pair<Integer, Integer> result;
            if (msg.what == 270377 && (result = (Pair) ((AsyncResult) msg.obj).result) != null) {
                if (QtiDataResetEventTracker.this.mPreviousRAT > 0 && ((Integer) result.second).intValue() > 0 && QtiDataResetEventTracker.this.mPreviousRAT != ((Integer) result.second).intValue()) {
                    QtiDataResetEventTracker qtiDataResetEventTracker = QtiDataResetEventTracker.this;
                    qtiDataResetEventTracker.log("RAT CHANGED, " + QtiDataResetEventTracker.this.mPreviousRAT + "->" + result.second);
                    QtiDataResetEventTracker.this.notifyResetEvent();
                }
                int unused = QtiDataResetEventTracker.this.mPreviousRAT = ((Integer) result.second).intValue();
            }
        }
    };
    private ResetEventListener mListener = null;
    private Phone mPhone = null;
    private PhoneStateListener mPhoneStateListener = null;
    /* access modifiers changed from: private */
    public GsmCellLocation mPreviousLocation = null;
    /* access modifiers changed from: private */
    public int mPreviousRAT = 0;
    private TelephonyManager mTelephonyManager = null;
    private int mTransportType;

    public interface ResetEventListener {
        void onResetEvent();
    }

    public QtiDataResetEventTracker(int transportType, Phone phone, QtiDataConnection dc, ResetEventListener listener) {
        this.mDc = dc;
        log("QtiDataResetEventTracker constructor: " + this);
        this.mPhone = phone;
        this.mContext = this.mPhone.getContext();
        this.mListener = listener;
        this.mTransportType = transportType;
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    }

    public void startResetEventTracker() {
        log("startResetEventTracker");
        stopResetEventTracker();
        this.mPhone.getServiceStateTracker().registerForDataRegStateOrRatChanged(this.mTransportType, this.mHandler, 270377, (Object) null);
        if (this.mPhone.getCellLocation() instanceof GsmCellLocation) {
            this.mPreviousLocation = (GsmCellLocation) this.mPhone.getCellLocation();
            log("DataConnection mPreviousLocation : " + this.mPreviousLocation);
        }
        int ddsSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (this.mPhoneStateListener == null) {
            this.mPhoneStateListener = new PhoneStateListener() {
                public void onCellLocationChanged(CellLocation location) {
                    QtiDataResetEventTracker qtiDataResetEventTracker = QtiDataResetEventTracker.this;
                    qtiDataResetEventTracker.log("DataConnection onCellLocationChanged : " + location);
                    if (location instanceof GsmCellLocation) {
                        GsmCellLocation currentLocation = (GsmCellLocation) location;
                        if (!(QtiDataResetEventTracker.this.mPreviousLocation == null || currentLocation == null || (QtiDataResetEventTracker.this.mPreviousLocation.getCid() == currentLocation.getCid() && QtiDataResetEventTracker.this.mPreviousLocation.getLac() == currentLocation.getLac()))) {
                            QtiDataResetEventTracker.this.log("DataConnection location updated");
                            QtiDataResetEventTracker.this.notifyResetEvent();
                        }
                        GsmCellLocation unused = QtiDataResetEventTracker.this.mPreviousLocation = currentLocation;
                    }
                }
            };
        }
        this.mTelephonyManager.createForSubscriptionId(ddsSubId).listen(this.mPhoneStateListener, 16);
    }

    public void stopResetEventTracker() {
        log("stopResetTimer");
        try {
            this.mPreviousRAT = 0;
            this.mPreviousLocation = null;
            if (this.mPhoneStateListener != null) {
                this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
            }
            this.mPhone.getServiceStateTracker().unregisterForDataRegStateOrRatChanged(this.mTransportType, this.mHandler);
        } catch (Exception e) {
            log("error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void dispose() {
        log("dispose");
        stopResetEventTracker();
        this.mTelephonyManager = null;
    }

    /* access modifiers changed from: private */
    public void notifyResetEvent() {
        stopResetEventTracker();
        ResetEventListener resetEventListener = this.mListener;
        if (resetEventListener != null) {
            resetEventListener.onResetEvent();
        }
    }

    /* access modifiers changed from: private */
    public void log(String log) {
        Rlog.d(this.mDc.getName() + "[DRET]", log);
    }
}
