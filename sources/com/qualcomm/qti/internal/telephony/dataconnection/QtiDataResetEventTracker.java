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
            if (msg.what == 270377) {
                Pair<Integer, Integer> result = (Pair) ((AsyncResult) msg.obj).result;
                if (result != null) {
                    if (QtiDataResetEventTracker.this.mPreviousRAT > 0 && ((Integer) result.second).intValue() > 0 && QtiDataResetEventTracker.this.mPreviousRAT != ((Integer) result.second).intValue()) {
                        QtiDataResetEventTracker qtiDataResetEventTracker = QtiDataResetEventTracker.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("RAT CHANGED, ");
                        sb.append(QtiDataResetEventTracker.this.mPreviousRAT);
                        sb.append("->");
                        sb.append(result.second);
                        qtiDataResetEventTracker.log(sb.toString());
                        QtiDataResetEventTracker.this.notifyResetEvent();
                    }
                    QtiDataResetEventTracker.this.mPreviousRAT = ((Integer) result.second).intValue();
                }
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
        StringBuilder sb = new StringBuilder();
        sb.append("QtiDataResetEventTracker constructor: ");
        sb.append(this);
        log(sb.toString());
        this.mPhone = phone;
        this.mContext = this.mPhone.getContext();
        this.mListener = listener;
        this.mTransportType = transportType;
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    }

    public void startResetEventTracker() {
        log("startResetEventTracker");
        stopResetEventTracker();
        this.mPhone.getServiceStateTracker().registerForDataRegStateOrRatChanged(this.mTransportType, this.mHandler, 270377, null);
        if (this.mPhone.getCellLocation() instanceof GsmCellLocation) {
            this.mPreviousLocation = (GsmCellLocation) this.mPhone.getCellLocation();
            StringBuilder sb = new StringBuilder();
            sb.append("DataConnection mPreviousLocation : ");
            sb.append(this.mPreviousLocation);
            log(sb.toString());
        }
        int ddsSubId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (this.mPhoneStateListener == null) {
            this.mPhoneStateListener = new PhoneStateListener() {
                public void onCellLocationChanged(CellLocation location) {
                    QtiDataResetEventTracker qtiDataResetEventTracker = QtiDataResetEventTracker.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append("DataConnection onCellLocationChanged : ");
                    sb.append(location);
                    qtiDataResetEventTracker.log(sb.toString());
                    if (location instanceof GsmCellLocation) {
                        GsmCellLocation currentLocation = (GsmCellLocation) location;
                        if (!(QtiDataResetEventTracker.this.mPreviousLocation == null || currentLocation == null || (QtiDataResetEventTracker.this.mPreviousLocation.getCid() == currentLocation.getCid() && QtiDataResetEventTracker.this.mPreviousLocation.getLac() == currentLocation.getLac()))) {
                            QtiDataResetEventTracker.this.log("DataConnection location updated");
                            QtiDataResetEventTracker.this.notifyResetEvent();
                        }
                        QtiDataResetEventTracker.this.mPreviousLocation = currentLocation;
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
            StringBuilder sb = new StringBuilder();
            sb.append("error:");
            sb.append(e.getMessage());
            log(sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDc.getName());
        sb.append("[DRET]");
        Rlog.d(sb.toString(), log);
    }
}
