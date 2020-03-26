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
                qtiDcRetryAlarmController.log("onReceive: ignore empty action='" + action + "'");
            } else if (!TextUtils.equals(action, QtiDcRetryAlarmController.this.mActionRetry)) {
                QtiDcRetryAlarmController qtiDcRetryAlarmController2 = QtiDcRetryAlarmController.this;
                qtiDcRetryAlarmController2.log("onReceive: unknown action=" + action);
            } else if (!intent.hasExtra(QtiDcRetryAlarmController.INTENT_RETRY_ALARM_WHAT)) {
                throw new RuntimeException(QtiDcRetryAlarmController.this.mActionRetry + " has no INTENT_RETRY_ALRAM_WHAT");
            } else if (intent.hasExtra(QtiDcRetryAlarmController.INTENT_RETRY_ALARM_TAG)) {
                int what = intent.getIntExtra(QtiDcRetryAlarmController.INTENT_RETRY_ALARM_WHAT, Integer.MAX_VALUE);
                int tag = intent.getIntExtra(QtiDcRetryAlarmController.INTENT_RETRY_ALARM_TAG, Integer.MAX_VALUE);
                QtiDcRetryAlarmController qtiDcRetryAlarmController3 = QtiDcRetryAlarmController.this;
                qtiDcRetryAlarmController3.log("onReceive: action=" + action + " sendMessage(what:" + what + ", tag:" + tag + ")");
                QtiDcRetryAlarmController.this.mDc.sendMessage(QtiDcRetryAlarmController.this.mDc.obtainMessage(what, tag, 0));
            } else {
                throw new RuntimeException(QtiDcRetryAlarmController.this.mActionRetry + " has no INTENT_RETRY_ALRAM_TAG");
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
        this.mActionRetry = this.mDc.getClass().getCanonicalName() + "." + this.mDc.getName() + ".action_retry";
        IntentFilter filter = new IntentFilter();
        filter.addAction(this.mActionRetry);
        log("QtiDcRetryAlarmController: register for intent action=" + this.mActionRetry);
        this.mPhone.getContext().registerReceiver(this.mIntentReceiver, filter, (String) null, this.mDc.getHandler());
    }

    /* access modifiers changed from: package-private */
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
        log("startRetryAlarm: next attempt in " + (delay / 1000) + "s what=" + what + " tag=" + tag);
        this.mRetryIntent = PendingIntent.getBroadcast(this.mPhone.getContext(), 0, intent, 134217728);
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + ((long) delay), this.mRetryIntent);
    }

    public void cancel() {
        if (this.mRetryIntent != null) {
            log("cancel event: " + this.mRetryIntent);
            this.mAlarmManager.cancel(this.mRetryIntent);
            this.mRetryIntent = null;
        }
    }

    public String toString() {
        return this.mLogTag + " [dcRac] " + " mPhone=" + this.mPhone + " mDc=" + this.mDc + " mAlaramManager=" + this.mAlarmManager + " mActionRetry=" + this.mActionRetry;
    }

    /* access modifiers changed from: private */
    public void log(String s) {
        String str = this.mLogTag;
        Rlog.d(str, "[dcRac] " + s);
    }
}
