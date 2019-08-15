package com.qualcomm.qti.internal.telephony.dataconnection;

import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.uicc.IccRecords;
import java.util.HashSet;
import java.util.Iterator;

public final class QtiDcTracker extends DcTracker {
    private String LOG_TAG = "QtiDCT";
    private HashSet<String> mIccidSet = new HashSet<>();

    public QtiDcTracker(Phone phone, int transportType) {
        super(phone, transportType);
        StringBuilder sb = new StringBuilder();
        sb.append(this.LOG_TAG);
        sb.append(".constructor");
        log(sb.toString());
        fillIccIdSet();
    }

    private boolean isRecordsLoaded() {
        IccRecords r = (IccRecords) this.mIccRecords.get();
        if (r != null) {
            return r.getRecordsLoaded();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onRecordsLoadedOrSubIdChanged() {
        log("onRecordsLoaded: createAllApnList");
        this.mAutoAttachOnCreationConfig = this.mPhone.getContext().getResources().getBoolean(17956894);
        createAllApnList();
        if (isRecordsLoaded()) {
            setInitialAttachApn();
        }
        if (this.mPhone.mCi.getRadioState().isOn()) {
            log("onRecordsLoaded: notifying data availability");
            notifyOffApnsOfAvailability("simLoaded");
        }
        setupDataOnConnectableApns("simLoaded");
    }

    /* access modifiers changed from: protected */
    public boolean getAttachedStatus() {
        int dataSub = SubscriptionManager.getDefaultDataSubscriptionId();
        if (!SubscriptionManager.isUsableSubIdValue(dataSub) || dataSub == this.mPhone.getSubId()) {
            return QtiDcTracker.super.getAttachedStatus();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean allowInitialAttachForOperator() {
        IccRecords r = (IccRecords) this.mIccRecords.get();
        String iccId = r != null ? r.getIccId() : "";
        if (iccId != null) {
            Iterator<String> itr = this.mIccidSet.iterator();
            while (itr.hasNext()) {
                if (iccId.contains((CharSequence) itr.next())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void fillIccIdSet() {
        this.mIccidSet.add("8991840");
        this.mIccidSet.add("8991854");
        this.mIccidSet.add("8991855");
        this.mIccidSet.add("8991856");
        this.mIccidSet.add("8991857");
        this.mIccidSet.add("8991858");
        this.mIccidSet.add("8991859");
        this.mIccidSet.add("899186");
        this.mIccidSet.add("8991870");
        this.mIccidSet.add("8991871");
        this.mIccidSet.add("8991872");
        this.mIccidSet.add("8991873");
        this.mIccidSet.add("8991874");
    }

    /* access modifiers changed from: protected */
    public void log(String s) {
        String str = this.LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.mPhone.getPhoneId());
        sb.append("]");
        sb.append(s);
        Rlog.d(str, sb.toString());
    }
}
