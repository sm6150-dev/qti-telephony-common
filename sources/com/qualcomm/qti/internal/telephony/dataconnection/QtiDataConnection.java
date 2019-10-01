package com.qualcomm.qti.internal.telephony.dataconnection;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.hardware.radio.V1_0.RadioAccessFamily;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.ApnContext;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DataConnection.ConnectionParams;
import com.android.internal.telephony.dataconnection.DataConnection.DisconnectParams;
import com.android.internal.telephony.dataconnection.DataConnection.SetupResult;
import com.android.internal.telephony.dataconnection.DataServiceManager;
import com.android.internal.telephony.dataconnection.DcController;
import com.android.internal.telephony.dataconnection.DcTesterFailBringUpAll;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.qualcomm.qti.internal.telephony.dataconnection.QtiDataResetEventTracker.ResetEventListener;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class QtiDataConnection extends DataConnection {
    protected static final int MAX_PDP_REJECT_COUNT = 3;
    protected static AlertDialog mDataRejectDialog = null;
    protected static int mPdpRejectCause = RadioAccessFamily.GSM;
    protected static int mPdpRejectCount = 0;
    protected QtiDcRetryAlarmController mDcRetryAlarmController = new QtiDcRetryAlarmController(this.mPhone, this);
    protected QtiDataResetEventTracker mQtiDataResetEventTracker = null;
    protected ResetEventListener mResetEventListener = new ResetEventListener() {
        public void onResetEvent() {
            QtiDataConnection.this.log("DataConnection onResetEvent, reset mPdpRejectCount");
            QtiDataConnection.mPdpRejectCount = 0;
            QtiDataConnection.mPdpRejectCause = RadioAccessFamily.GSM;
            QtiDataConnection.this.mDcRetryAlarmController.cancel();
            QtiDataConnection.this.mQtiDataResetEventTracker.stopResetEventTracker();
            QtiDataConnection qtiDataConnection = QtiDataConnection.this;
            qtiDataConnection.sendMessage(qtiDataConnection.obtainMessage(262171, qtiDataConnection.mTag, 0));
        }
    };

    public QtiDataConnection(Phone phone, String name, int id, DcTracker dct, DataServiceManager dataServiceManager, DcTesterFailBringUpAll failBringUpAll, DcController dcc) {
        super(phone, name, id, dct, dataServiceManager, failBringUpAll, dcc);
    }

    public static DataConnection makeDataConnection(Phone phone, int id, DcTracker dct, DataServiceManager dataServiceManager, DcTesterFailBringUpAll failBringUpAll, DcController dcc) {
        StringBuilder sb = new StringBuilder();
        sb.append("QtiDC-");
        sb.append(mInstanceNumber.incrementAndGet());
        QtiDataConnection qtiDataConnection = new QtiDataConnection(phone, sb.toString(), id, dct, dataServiceManager, failBringUpAll, dcc);
        qtiDataConnection.start();
        return qtiDataConnection;
    }

    /* access modifiers changed from: protected */
    public void dispose() {
        QtiDataConnection.super.dispose();
        QtiDataResetEventTracker qtiDataResetEventTracker = this.mQtiDataResetEventTracker;
        if (qtiDataResetEventTracker != null) {
            qtiDataResetEventTracker.dispose();
            this.mQtiDataResetEventTracker = null;
        }
        QtiDcRetryAlarmController qtiDcRetryAlarmController = this.mDcRetryAlarmController;
        if (qtiDcRetryAlarmController != null) {
            qtiDcRetryAlarmController.dispose();
            this.mDcRetryAlarmController = null;
        }
    }

    /* access modifiers changed from: protected */
    public void notifyDisconnectCompleted(DisconnectParams dp, boolean sendAll) {
        QtiDataConnection.super.notifyDisconnectCompleted(dp, sendAll);
        log("notifyDisconnectCompleted, reset mPdpRejectCount");
        mPdpRejectCount = 0;
        this.mDcRetryAlarmController.cancel();
        QtiDataResetEventTracker qtiDataResetEventTracker = this.mQtiDataResetEventTracker;
        if (qtiDataResetEventTracker != null) {
            qtiDataResetEventTracker.stopResetEventTracker();
        }
    }

    private boolean isWCDMA(int radioTechnology) {
        return radioTechnology == 3 || radioTechnology == 9 || radioTechnology == 10 || radioTechnology == 11 || radioTechnology == 15;
    }

    private void displayPopup(int pdpRejectCause) {
        StringBuilder sb = new StringBuilder();
        sb.append("displayPopup : ");
        sb.append(pdpRejectCause);
        log(sb.toString());
        String title = this.mPhone.getContext().getResources().getString(17039825);
        String message = null;
        if (pdpRejectCause == 29) {
            message = this.mPhone.getContext().getResources().getString(17041296);
        } else if (pdpRejectCause == 33) {
            message = this.mPhone.getContext().getResources().getString(17041098);
        } else if (pdpRejectCause == 55) {
            message = this.mPhone.getContext().getResources().getString(17040515);
        }
        AlertDialog alertDialog = mDataRejectDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            Builder builder = new Builder(this.mPhone.getContext());
            builder.setPositiveButton(17039370, null);
            mDataRejectDialog = builder.create();
        }
        mDataRejectDialog.setMessage(message);
        mDataRejectDialog.setCanceledOnTouchOutside(false);
        mDataRejectDialog.setTitle(title);
        mDataRejectDialog.getWindow().setType(2003);
        mDataRejectDialog.show();
    }

    private boolean handleDataReject(SetupResult result, ConnectionParams cp) {
        ApnContext apnContext = cp.mApnContext;
        int delay = this.mPhone.getContext().getResources().getInteger(17694964);
        StringBuilder sb = new StringBuilder();
        sb.append("handleDataReject: delay from config: ");
        sb.append(delay);
        log(sb.toString());
        if (mPdpRejectCause != result.mFailCause) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("handleDataReject: mPdpRejectCause: ");
            sb2.append(mPdpRejectCause);
            sb2.append(", result.mFailCause: ");
            sb2.append(result.mFailCause);
            sb2.append(", reset mPdpRejectCount");
            log(sb2.toString());
            mPdpRejectCount = 0;
            mPdpRejectCause = RadioAccessFamily.GSM;
        }
        if (!isPdpRejectCause(result.mFailCause)) {
            return false;
        }
        mPdpRejectCount++;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("handleDataReject: new mPdpRejectCount = ");
        sb3.append(mPdpRejectCount);
        log(sb3.toString());
        mPdpRejectCause = result.mFailCause;
        if (3 <= mPdpRejectCount) {
            log("reached max retry count, reset mPdpRejectCount");
            mPdpRejectCount = 0;
            displayPopup(mPdpRejectCause);
            delay = this.mPhone.getContext().getResources().getInteger(17694965);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("handleDataReject: next attempt in ");
            sb4.append(delay / 1000);
            sb4.append("s");
            log(sb4.toString());
            if (this.mQtiDataResetEventTracker == null) {
                this.mQtiDataResetEventTracker = new QtiDataResetEventTracker(this.mTransportType, this.mPhone, this, this.mResetEventListener);
            }
            this.mQtiDataResetEventTracker.startResetEventTracker();
        }
        if (delay >= 0) {
            log("handleDataReject: DcActivatingState: **ERR_RilError retry**");
            if (apnContext != null) {
                PendingIntent intent = apnContext.getReconnectIntent();
                if (intent != null) {
                    ((AlarmManager) this.mPhone.getContext().getSystemService("alarm")).cancel(intent);
                    apnContext.setReconnectIntent(null);
                } else {
                    log("handleDataReject: intent = null");
                }
            } else {
                log("handleDataReject: apnContext = null");
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append("handleDataReject: startRetryAlarm with delay ");
            sb5.append(delay);
            sb5.append(", mTag: ");
            sb5.append(this.mTag);
            log(sb5.toString());
            this.mDcRetryAlarmController.startRetryAlarm(262171, this.mTag, delay);
            this.mInactiveState.setEnterNotificationParams(cp, result.mFailCause);
            transitionTo(this.mInactiveState);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void handlePdpRejectCauseSuccess() {
        if (mPdpRejectCount > 0 && mPdpRejectCause != 65536) {
            log("handlePdpRejectCauseSuccess: reset mPdpRejectCount");
            mPdpRejectCount = 0;
            mPdpRejectCause = RadioAccessFamily.GSM;
            QtiDataResetEventTracker qtiDataResetEventTracker = this.mQtiDataResetEventTracker;
            if (qtiDataResetEventTracker != null) {
                qtiDataResetEventTracker.stopResetEventTracker();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isPdpRejectCauseFailureHandled(SetupResult result, ConnectionParams cp) {
        if (!isWCDMA(this.mPhone.getServiceState().getRilDataRadioTechnology()) || !isPdpRejectConfigEnabled()) {
            log("isPdpRejectCauseFailureHandled: DataConnection not on wcdma");
            mPdpRejectCount = 0;
            mPdpRejectCause = RadioAccessFamily.GSM;
            QtiDataResetEventTracker qtiDataResetEventTracker = this.mQtiDataResetEventTracker;
            if (qtiDataResetEventTracker == null) {
                return false;
            }
            qtiDataResetEventTracker.stopResetEventTracker();
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("isPdpRejectCauseFailureHandled: config is enabledmPdpRejectCount: ");
        sb.append(mPdpRejectCount);
        log(sb.toString());
        return handleDataReject(result, cp);
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("QtiDataConnection ");
        StringBuilder sb = new StringBuilder();
        sb.append(" mDcRetryAlarmController=");
        sb.append(this.mDcRetryAlarmController);
        pw.println(sb.toString());
        QtiDataConnection.super.dump(fd, pw, args);
    }
}
