package com.qualcomm.qti.internal.telephony.dataconnection;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.hardware.radio.V1_0.RadioAccessFamily;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.ApnContext;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DataServiceManager;
import com.android.internal.telephony.dataconnection.DcController;
import com.android.internal.telephony.dataconnection.DcTesterFailBringUpAll;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.qualcomm.qti.internal.telephony.dataconnection.QtiDataResetEventTracker;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class QtiDataConnection extends DataConnection {
    protected static final int MAX_PDP_REJECT_COUNT = 3;
    protected static AlertDialog mDataRejectDialog = null;
    protected static int mPdpRejectCause = RadioAccessFamily.GSM;
    protected static int mPdpRejectCount = 0;
    protected QtiDcRetryAlarmController mDcRetryAlarmController = new QtiDcRetryAlarmController(this.mPhone, this);
    protected QtiDataResetEventTracker mQtiDataResetEventTracker = null;
    protected QtiDataResetEventTracker.ResetEventListener mResetEventListener = new QtiDataResetEventTracker.ResetEventListener() {
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
        DataConnection dc = new QtiDataConnection(phone, "QtiDC-" + mInstanceNumber.incrementAndGet(), id, dct, dataServiceManager, failBringUpAll, dcc);
        dc.start();
        return dc;
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
    public void notifyDisconnectCompleted(DataConnection.DisconnectParams dp, boolean sendAll) {
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
        log("displayPopup : " + pdpRejectCause);
        String title = this.mPhone.getContext().getResources().getString(17039826);
        String message = null;
        if (pdpRejectCause == 29) {
            message = this.mPhone.getContext().getResources().getString(17041301);
        } else if (pdpRejectCause == 33) {
            message = this.mPhone.getContext().getResources().getString(17041103);
        } else if (pdpRejectCause == 55) {
            message = this.mPhone.getContext().getResources().getString(17040520);
        }
        AlertDialog alertDialog = mDataRejectDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mPhone.getContext());
            builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
            mDataRejectDialog = builder.create();
        }
        mDataRejectDialog.setMessage(message);
        mDataRejectDialog.setCanceledOnTouchOutside(false);
        mDataRejectDialog.setTitle(title);
        mDataRejectDialog.getWindow().setType(2003);
        mDataRejectDialog.show();
    }

    private boolean handleDataReject(DataConnection.SetupResult result, DataConnection.ConnectionParams cp) {
        ApnContext apnContext = cp.mApnContext;
        int delay = this.mPhone.getContext().getResources().getInteger(17694964);
        log("handleDataReject: delay from config: " + delay);
        if (mPdpRejectCause != result.mFailCause) {
            log("handleDataReject: mPdpRejectCause: " + mPdpRejectCause + ", result.mFailCause: " + result.mFailCause + ", reset mPdpRejectCount");
            mPdpRejectCount = 0;
            mPdpRejectCause = RadioAccessFamily.GSM;
        }
        if (!isPdpRejectCause(result.mFailCause)) {
            return false;
        }
        mPdpRejectCount++;
        log("handleDataReject: new mPdpRejectCount = " + mPdpRejectCount);
        mPdpRejectCause = result.mFailCause;
        if (3 <= mPdpRejectCount) {
            log("reached max retry count, reset mPdpRejectCount");
            mPdpRejectCount = 0;
            displayPopup(mPdpRejectCause);
            delay = this.mPhone.getContext().getResources().getInteger(17694965);
            log("handleDataReject: next attempt in " + (delay / 1000) + "s");
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
                    apnContext.setReconnectIntent((PendingIntent) null);
                } else {
                    log("handleDataReject: intent = null");
                }
            } else {
                log("handleDataReject: apnContext = null");
            }
            log("handleDataReject: startRetryAlarm with delay " + delay + ", mTag: " + this.mTag);
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
    public boolean isPdpRejectCauseFailureHandled(DataConnection.SetupResult result, DataConnection.ConnectionParams cp) {
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
        log("isPdpRejectCauseFailureHandled: config is enabledmPdpRejectCount: " + mPdpRejectCount);
        return handleDataReject(result, cp);
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.print("QtiDataConnection ");
        pw.println(" mDcRetryAlarmController=" + this.mDcRetryAlarmController);
        QtiDataConnection.super.dump(fd, pw, args);
    }
}
