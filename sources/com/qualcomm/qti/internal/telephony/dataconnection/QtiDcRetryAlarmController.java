package com.qualcomm.qti.internal.telephony.dataconnection;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.DataConnection;

public class QtiDcRetryAlarmController {
    private static final boolean DBG = true;
    private static final String INTENT_RETRY_ALARM_TAG = "tag";
    private static final String INTENT_RETRY_ALARM_WHAT = "what";
    /* access modifiers changed from: private */
    public String mActionRetry;
    private AlarmManager mAlarmManager;
    /* access modifiers changed from: private */
    public DataConnection mDc;
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                QtiDcRetryAlarmController qtiDcRetryAlarmController = QtiDcRetryAlarmController.this;
                StringBuilder sb = new StringBuilder();
                sb.append("onReceive: ignore empty action='");
                sb.append(action);
                sb.append("'");
                qtiDcRetryAlarmController.log(sb.toString());
                return;
            }
            if (TextUtils.equals(action, QtiDcRetryAlarmController.this.mActionRetry)) {
                String str = QtiDcRetryAlarmController.INTENT_RETRY_ALARM_WHAT;
                if (intent.hasExtra(str)) {
                    String str2 = QtiDcRetryAlarmController.INTENT_RETRY_ALARM_TAG;
                    if (intent.hasExtra(str2)) {
                        int what = intent.getIntExtra(str, Integer.MAX_VALUE);
                        int tag = intent.getIntExtra(str2, Integer.MAX_VALUE);
                        QtiDcRetryAlarmController qtiDcRetryAlarmController2 = QtiDcRetryAlarmController.this;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("onReceive: action=");
                        sb2.append(action);
                        sb2.append(" sendMessage(what:");
                        sb2.append(what);
                        sb2.append(", tag:");
                        sb2.append(tag);
                        sb2.append(")");
                        qtiDcRetryAlarmController2.log(sb2.toString());
                        QtiDcRetryAlarmController.this.mDc.sendMessage(QtiDcRetryAlarmController.this.mDc.obtainMessage(what, tag, 0));
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(QtiDcRetryAlarmController.this.mActionRetry);
                        sb3.append(" has no INTENT_RETRY_ALRAM_TAG");
                        throw new RuntimeException(sb3.toString());
                    }
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(QtiDcRetryAlarmController.this.mActionRetry);
                    sb4.append(" has no INTENT_RETRY_ALRAM_WHAT");
                    throw new RuntimeException(sb4.toString());
                }
            } else {
                QtiDcRetryAlarmController qtiDcRetryAlarmController3 = QtiDcRetryAlarmController.this;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("onReceive: unknown action=");
                sb5.append(action);
                qtiDcRetryAlarmController3.log(sb5.toString());
            }
        }
    };
    private String mLogTag = "QtiDcRac";
    private Phone mPhone;
    private PendingIntent mRetryIntent = null;

    QtiDcRetryAlarmController(Phone phone, QtiDataConnection dc) {
        this.mLogTag = dc.getName();
        this.mPhone = phone;
        this.mDc = dc;
        this.mAlarmManager = (AlarmManager) this.mPhone.getContext().getSystemService("alarm");
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDc.getClass().getCanonicalName());
        sb.append(".");
        sb.append(this.mDc.getName());
        sb.append(".action_retry");
        this.mActionRetry = sb.toString();
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.mActionRetry);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("QtiDcRetryAlarmController: register for intent action=");
        sb2.append(this.mActionRetry);
        log(sb2.toString());
        this.mPhone.getContext().registerReceiver(this.mIntentReceiver, filter, null, this.mDc.getHandler());
    }

    /* access modifiers changed from: 0000 */
    public void dispose() {
        log("dispose");
        this.mPhone.getContext().unregisterReceiver(this.mIntentReceiver);
        this.mPhone = null;
        this.mDc = null;
        this.mAlarmManager = null;
        this.mActionRetry = null;
    }

    public void startRetryAlarm(int what, int tag, int delay) {
        Intent intent = new Intent(this.mActionRetry);
        intent.putExtra(INTENT_RETRY_ALARM_WHAT, what);
        intent.putExtra(INTENT_RETRY_ALARM_TAG, tag);
        intent.addFlags(268435456);
        StringBuilder sb = new StringBuilder();
        sb.append("startRetryAlarm: next attempt in ");
        sb.append(delay / 1000);
        sb.append("s what=");
        sb.append(what);
        sb.append(" tag=");
        sb.append(tag);
        log(sb.toString());
        this.mRetryIntent = PendingIntent.getBroadcast(this.mPhone.getContext(), 0, intent, 134217728);
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + ((long) delay), this.mRetryIntent);
    }

    public void cancel() {
        if (this.mRetryIntent != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("cancel event: ");
            sb.append(this.mRetryIntent);
            log(sb.toString());
            this.mAlarmManager.cancel(this.mRetryIntent);
            this.mRetryIntent = null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mLogTag);
        sb.append(" [dcRac] ");
        sb.append(" mPhone=");
        sb.append(this.mPhone);
        sb.append(" mDc=");
        sb.append(this.mDc);
        sb.append(" mAlaramManager=");
        sb.append(this.mAlarmManager);
        sb.append(" mActionRetry=");
        sb.append(this.mActionRetry);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public void log(String s) {
        String str = this.mLogTag;
        StringBuilder sb = new StringBuilder();
        sb.append("[dcRac] ");
        sb.append(s);
        Rlog.d(str, sb.toString());
    }
}
