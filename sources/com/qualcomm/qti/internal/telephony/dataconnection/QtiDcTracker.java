package com.qualcomm.qti.internal.telephony.dataconnection;

import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.uicc.IccRecords;
import java.util.HashSet;
import java.util.Iterator;

public final class QtiDcTracker extends DcTracker {
    private String LOG_TAG = "QtiDCT";
    private HashSet<String> mIccidSet = new HashSet<>();
    private int mTransportType = 1;

    public QtiDcTracker(Phone phone, int transportType) {
        super(phone, transportType);
        this.mTransportType = transportType;
        StringBuilder sb = new StringBuilder();
        sb.append(this.LOG_TAG);
        sb.append("-");
        sb.append(transportType == 1 ? "C" : "I");
        this.LOG_TAG = sb.toString();
        log(this.LOG_TAG + ".constructor");
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
        if (this.mTransportType == 1) {
            this.mAutoAttachOnCreationConfig = this.mPhone.getContext().getResources().getBoolean(17891366);
        }
        createAllApnList();
        setDataProfilesAsNeeded();
        if (isRecordsLoaded()) {
            setInitialAttachApn();
        }
        this.mPhone.notifyDataConnection();
        setupDataOnAllConnectableApns("simLoaded");
    }

    /* access modifiers changed from: protected */
    public boolean allowInitialAttachForOperator() {
        IccRecords r = (IccRecords) this.mIccRecords.get();
        String iccId = r != null ? r.getIccId() : "";
        if (iccId == null) {
            return true;
        }
        Iterator<String> itr = this.mIccidSet.iterator();
        while (itr.hasNext()) {
            if (iccId.contains(itr.next())) {
                return false;
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
        Rlog.d(str, "[" + this.mPhone.getPhoneId() + "]" + s);
    }

    /* access modifiers changed from: protected */
    public DataConnection createDataConnection() {
        log("createDataConnection Ex");
        int id = this.mUniqueIdGenerator.getAndIncrement();
        DataConnection conn = QtiDataConnection.makeDataConnection(this.mPhone, id, this, this.mDataServiceManager, this.mDcTesterFailBringUpAll, this.mDcc);
        this.mDataConnections.put(Integer.valueOf(id), conn);
        log("createDataConnection() X id=" + id + " dc=" + conn);
        return conn;
    }

    /* access modifiers changed from: protected */
    public boolean isPermanentFailure(int dcFailCause) {
        if (!this.mPhone.getContext().getResources().getBoolean(17891492) || dcFailCause != 55) {
            return QtiDcTracker.super.isPermanentFailure(dcFailCause);
        }
        log("isPermanentFailure: MULTI_CONN_TO_SAME_PDN_NOT_ALLOWED");
        return true;
    }
}
