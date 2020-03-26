package com.qualcomm.qti.internal.telephony;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Message;
import android.telephony.NetworkRegistrationInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.ServiceStateTracker;

public class QtiServiceStateTracker extends ServiceStateTracker {
    private static final String ACTION_MANAGED_ROAMING_IND = "codeaurora.intent.action.ACTION_MANAGED_ROAMING_IND";
    private static final boolean DBG = true;
    private static final String LOG_TAG = "QtiServiceStateTracker";
    private static final boolean VDBG = false;
    private final String ACTION_RAC_CHANGED = "qualcomm.intent.action.ACTION_RAC_CHANGED";
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle bundle;
            if (intent.getAction().equals("qualcomm.intent.action.ACTION_RAC_CHANGED") && (bundle = intent.getExtras()) != null) {
                int unused = QtiServiceStateTracker.this.mRac = bundle.getInt("rac");
                int unused2 = QtiServiceStateTracker.this.mRat = bundle.getInt("rat");
            }
        }
    };
    /* access modifiers changed from: private */
    public int mRac;
    private final String mRacChange = "rac";
    /* access modifiers changed from: private */
    public int mRat;
    private final String mRatInfo = "rat";
    private int mTac = -1;

    public QtiServiceStateTracker(GsmCdmaPhone phone, CommandsInterface ci) {
        super(phone, ci);
        IntentFilter filter = new IntentFilter();
        filter.addAction("qualcomm.intent.action.ACTION_RAC_CHANGED");
        phone.getContext().registerReceiver(this.mIntentReceiver, filter);
    }

    /* access modifiers changed from: protected */
    public void handlePollStateResultMessage(int what, AsyncResult ar) {
        if (what != 4) {
            QtiServiceStateTracker.super.handlePollStateResultMessage(what, ar);
            return;
        }
        QtiServiceStateTracker.super.handlePollStateResultMessage(what, ar);
        if (this.mPhone.isPhoneTypeGsm()) {
            NetworkRegistrationInfo regStates = (NetworkRegistrationInfo) ar.result;
            if (regStates.getRegistrationState() == 3 && regStates.getRejectCause() == 10) {
                log(" Posting Managed roaming intent sub = " + this.mPhone.getSubId());
                try {
                    Intent intent = new Intent(ACTION_MANAGED_ROAMING_IND);
                    intent.setComponent(new ComponentName("com.qualcomm.qti.networksetting", "com.qualcomm.qti.networksetting.ManagedRoaming"));
                    intent.addFlags(268435456);
                    intent.putExtra("subscription", this.mPhone.getSubId());
                    this.mPhone.getContext().startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    loge("unable to start activity: " + e);
                }
            }
        }
    }

    public void handleMessage(Message msg) {
        if (msg.what != 1) {
            QtiServiceStateTracker.super.handleMessage(msg);
        } else if (this.mPhone.mCi.getRadioState() == 0) {
            setPowerStateToDesired();
            log("Trigger as manual polling");
            pollState();
        } else {
            QtiServiceStateTracker.super.handleMessage(msg);
        }
    }

    @VisibleForTesting
    public int[] getPollingContext() {
        return this.mPollingContext;
    }
}
