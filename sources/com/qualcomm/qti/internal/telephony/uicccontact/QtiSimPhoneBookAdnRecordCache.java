package com.qualcomm.qti.internal.telephony.uicccontact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.IccRefreshResponse;
import com.qualcomm.qti.internal.telephony.QtiRilInterface;
import java.util.ArrayList;
import java.util.Iterator;

public class QtiSimPhoneBookAdnRecordCache extends Handler {
    private static final boolean DBG = true;
    static final int EVENT_INIT_ADN_DONE = 1;
    static final int EVENT_LOAD_ADN_RECORD_DONE = 3;
    static final int EVENT_LOAD_ALL_ADN_LIKE_DONE = 4;
    static final int EVENT_QUERY_ADN_RECORD_DONE = 2;
    static final int EVENT_SIM_REFRESH = 6;
    static final int EVENT_UPDATE_ADN_RECORD_DONE = 5;
    private static final String LOG_TAG = "QtiSimPhoneBookAdnRecordCache";
    SparseArray<int[]> extRecList = new SparseArray<>();
    private int mAddNumCount = 0;
    private int mAdnCount = 0;
    ArrayList<Message> mAdnLoadingWaiters = new ArrayList<>();
    Message mAdnUpdatingWaiter = null;
    protected final CommandsInterface mCi;
    protected Context mContext;
    private int mEmailCount = 0;
    private Object mLock = new Object();
    private int mMaxAnrLen = 0;
    private int mMaxEmailLen = 0;
    private int mMaxNameLen = 0;
    private int mMaxNumberLen = 0;
    protected int mPhoneId;
    private QtiRilInterface mQtiRilInterface;
    private int mRecCount = 0;
    private boolean mRefreshAdnCache = false;
    private ArrayList<AdnRecord> mSimPbRecords;
    private int mValidAddNumCount = 0;
    private int mValidAdnCount = 0;
    private int mValidEmailCount = 0;
    private final BroadcastReceiver sReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SIM_STATE_CHANGED")) {
                int phoneId = intent.getIntExtra("phone", -1);
                String simStatus = intent.getStringExtra("ss");
                if ("ABSENT".equals(simStatus) && QtiSimPhoneBookAdnRecordCache.this.mPhoneId == phoneId) {
                    QtiSimPhoneBookAdnRecordCache qtiSimPhoneBookAdnRecordCache = QtiSimPhoneBookAdnRecordCache.this;
                    qtiSimPhoneBookAdnRecordCache.log("ACTION_SIM_STATE_CHANGED intent received simStatus: " + simStatus + "phoneId: " + phoneId);
                    QtiSimPhoneBookAdnRecordCache.this.invalidateAdnCache();
                }
            }
        }
    };

    public QtiSimPhoneBookAdnRecordCache(Context context, int phoneId, CommandsInterface ci) {
        this.mCi = ci;
        this.mSimPbRecords = new ArrayList<>();
        this.mPhoneId = phoneId;
        this.mContext = context;
        this.mQtiRilInterface = QtiRilInterface.getInstance(context);
        this.mQtiRilInterface.registerForAdnInitDone(this, 1, (Object) null);
        this.mCi.registerForIccRefresh(this, 6, (Object) null);
        context.registerReceiver(this.sReceiver, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"));
    }

    public void reset() {
        this.mAdnLoadingWaiters.clear();
        clearUpdatingWriter();
        this.mSimPbRecords.clear();
        this.mRecCount = 0;
        this.mRefreshAdnCache = false;
    }

    private void clearUpdatingWriter() {
        sendErrorResponse(this.mAdnUpdatingWaiter, "QtiSimPhoneBookAdnRecordCache reset");
        this.mAdnUpdatingWaiter = null;
    }

    private void sendErrorResponse(Message response, String errString) {
        if (response != null) {
            AsyncResult.forMessage(response).exception = new RuntimeException(errString);
            response.sendToTarget();
        }
    }

    private void notifyAndClearWaiters() {
        ArrayList<Message> arrayList = this.mAdnLoadingWaiters;
        if (arrayList != null) {
            int s = arrayList.size();
            for (int i = 0; i < s; i++) {
                Message response = this.mAdnLoadingWaiters.get(i);
                if (response != null) {
                    AsyncResult.forMessage(response).result = this.mSimPbRecords;
                    response.sendToTarget();
                }
            }
            this.mAdnLoadingWaiters.clear();
        }
    }

    public void queryAdnRecord() {
        this.mRecCount = 0;
        this.mAdnCount = 0;
        this.mValidAdnCount = 0;
        this.mEmailCount = 0;
        this.mAddNumCount = 0;
        this.mRefreshAdnCache = false;
        log("start to queryAdnRecord");
        this.mQtiRilInterface.registerForAdnRecordsInfo(this, 3, (Object) null);
        this.mQtiRilInterface.getAdnRecord(obtainMessage(2), this.mPhoneId);
        try {
            this.mLock.wait();
        } catch (InterruptedException e) {
            Rlog.e(LOG_TAG, "Interrupted Exception in queryAdnRecord");
        }
        this.mQtiRilInterface.unregisterForAdnRecordsInfo(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0036, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void requestLoadAllAdnLike(android.os.Message r3) {
        /*
            r2 = this;
            com.qualcomm.qti.internal.telephony.QtiRilInterface r0 = r2.mQtiRilInterface
            boolean r0 = r0.isServiceReady()
            if (r0 != 0) goto L_0x0013
            java.lang.String r0 = "Oem hook service is not ready yet "
            r2.log(r0)
            java.lang.String r0 = "Oem hook service is not ready yet"
            r2.sendErrorResponse(r3, r0)
            return
        L_0x0013:
            java.util.ArrayList<android.os.Message> r0 = r2.mAdnLoadingWaiters
            if (r0 == 0) goto L_0x001a
            r0.add(r3)
        L_0x001a:
            java.lang.Object r0 = r2.mLock
            monitor-enter(r0)
            java.util.ArrayList<com.android.internal.telephony.uicc.AdnRecord> r1 = r2.mSimPbRecords     // Catch:{ all -> 0x003c }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x003c }
            if (r1 != 0) goto L_0x0037
            java.lang.String r1 = "ADN cache has already filled in"
            r2.log(r1)     // Catch:{ all -> 0x003c }
            boolean r1 = r2.mRefreshAdnCache     // Catch:{ all -> 0x003c }
            if (r1 == 0) goto L_0x0032
            r2.refreshAdnCache()     // Catch:{ all -> 0x003c }
            goto L_0x0035
        L_0x0032:
            r2.notifyAndClearWaiters()     // Catch:{ all -> 0x003c }
        L_0x0035:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return
        L_0x0037:
            r2.queryAdnRecord()     // Catch:{ all -> 0x003c }
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return
        L_0x003c:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache.requestLoadAllAdnLike(android.os.Message):void");
    }

    public void updateSimPbAdnBySearch(AdnRecord oldAdn, AdnRecord newAdn, Message response) {
        ArrayList<AdnRecord> oldAdnList = this.mSimPbRecords;
        if (!this.mQtiRilInterface.isServiceReady()) {
            log("Oem hook service is not ready yet ");
            sendErrorResponse(response, "Oem hook service is not ready yet");
            return;
        }
        synchronized (this.mLock) {
            if (!this.mSimPbRecords.isEmpty()) {
                log("ADN cache has already filled in");
                if (this.mRefreshAdnCache) {
                    refreshAdnCache();
                }
            } else {
                queryAdnRecord();
            }
        }
        if (oldAdnList == null) {
            sendErrorResponse(response, "Sim PhoneBook Adn list not exist");
            return;
        }
        int index = -1;
        int count = 1;
        if (!oldAdn.isEmpty() || newAdn.isEmpty()) {
            Iterator<AdnRecord> it = oldAdnList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (oldAdn.isEqual(it.next())) {
                    index = count;
                    break;
                } else {
                    count++;
                }
            }
        } else {
            index = 0;
        }
        if (index == -1) {
            sendErrorResponse(response, "Sim PhoneBook Adn record don't exist for " + oldAdn);
        } else if (index == 0 && this.mValidAdnCount == this.mAdnCount) {
            sendErrorResponse(response, "Sim PhoneBook Adn record is full");
        } else {
            int recordIndex = index == 0 ? 0 : oldAdnList.get(index - 1).getRecordNumber();
            QtiSimPhoneBookAdnRecord updateAdn = new QtiSimPhoneBookAdnRecord();
            updateAdn.mRecordIndex = recordIndex;
            updateAdn.mAlphaTag = newAdn.getAlphaTag();
            updateAdn.mNumber = newAdn.getNumber();
            if (newAdn.getEmails() != null) {
                updateAdn.mEmails = newAdn.getEmails();
                updateAdn.mEmailCount = updateAdn.mEmails.length;
            }
            if (newAdn.getAdditionalNumbers() != null) {
                updateAdn.mAdNumbers = newAdn.getAdditionalNumbers();
                updateAdn.mAdNumCount = updateAdn.mAdNumbers.length;
            }
            if (this.mAdnUpdatingWaiter != null) {
                sendErrorResponse(response, "Have pending update for Sim PhoneBook Adn");
                return;
            }
            this.mAdnUpdatingWaiter = response;
            this.mQtiRilInterface.updateAdnRecord(updateAdn, obtainMessage(5, index, 0, newAdn), this.mPhoneId);
        }
    }

    public void handleMessage(Message msg) {
        QtiSimPhoneBookAdnRecord[] AdnRecordsGroup;
        AsyncResult ar = (AsyncResult) msg.obj;
        switch (msg.what) {
            case 1:
                AsyncResult ar2 = (AsyncResult) msg.obj;
                log("Initialized ADN done");
                if (ar2.exception == null) {
                    invalidateAdnCache();
                    return;
                }
                log("Init ADN done Exception: " + ar2.exception);
                return;
            case 2:
                log("Querying ADN record done");
                if (ar.exception != null) {
                    synchronized (this.mLock) {
                        this.mLock.notify();
                    }
                    Iterator<Message> it = this.mAdnLoadingWaiters.iterator();
                    while (it.hasNext()) {
                        sendErrorResponse(it.next(), "Query adn record failed" + ar.exception);
                    }
                    this.mAdnLoadingWaiters.clear();
                    return;
                }
                this.mAdnCount = ((int[]) ar.result)[0];
                this.mValidAdnCount = ((int[]) ar.result)[1];
                this.mEmailCount = ((int[]) ar.result)[2];
                this.mValidEmailCount = ((int[]) ar.result)[3];
                this.mAddNumCount = ((int[]) ar.result)[4];
                this.mValidAddNumCount = ((int[]) ar.result)[5];
                this.mMaxNameLen = ((int[]) ar.result)[6];
                this.mMaxNumberLen = ((int[]) ar.result)[7];
                this.mMaxEmailLen = ((int[]) ar.result)[8];
                this.mMaxAnrLen = ((int[]) ar.result)[9];
                log("Max ADN count is: " + this.mAdnCount + ", Valid ADN count is: " + this.mValidAdnCount + ", Email count is: " + this.mEmailCount + ", Valid email count is: " + this.mValidEmailCount + ", Add number count is: " + this.mAddNumCount + ", Valid add number count is: " + this.mValidAddNumCount + ", Max name length is: " + this.mMaxNameLen + ", Max number length is: " + this.mMaxNumberLen + ", Max email length is: " + this.mMaxEmailLen + ", Valid anr length is: " + this.mMaxAnrLen);
                int i = this.mValidAdnCount;
                if (i == 0 || this.mRecCount == i) {
                    sendMessage(obtainMessage(4));
                    return;
                }
                return;
            case 3:
                log("Loading ADN record done");
                if (ar.exception == null && (AdnRecordsGroup = (QtiSimPhoneBookAdnRecord[]) ar.result) != null) {
                    for (int i2 = 0; i2 < AdnRecordsGroup.length; i2++) {
                        if (AdnRecordsGroup[i2] != null) {
                            this.mSimPbRecords.add(new AdnRecord(0, AdnRecordsGroup[i2].getRecordIndex(), AdnRecordsGroup[i2].getAlphaTag(), AdnRecordsGroup[i2].getNumber(), AdnRecordsGroup[i2].getEmails(), AdnRecordsGroup[i2].getAdNumbers()));
                            this.mRecCount++;
                        }
                    }
                    if (this.mRecCount == this.mValidAdnCount) {
                        sendMessage(obtainMessage(4));
                        return;
                    }
                    return;
                }
                return;
            case 4:
                log("Loading all ADN records done");
                synchronized (this.mLock) {
                    this.mLock.notify();
                }
                notifyAndClearWaiters();
                return;
            case 5:
                log("Update ADN record done");
                Exception e = null;
                if (ar.exception == null) {
                    int index = msg.arg1;
                    AdnRecord adn = (AdnRecord) ar.userObj;
                    int recordIndex = ((int[]) ar.result)[0];
                    if (index == 0) {
                        log("Record number for added ADN is " + recordIndex);
                        adn.setRecordNumber(recordIndex);
                        this.mSimPbRecords.add(adn);
                        this.mValidAdnCount = this.mValidAdnCount + 1;
                    } else if (adn.isEmpty()) {
                        int adnRecordIndex = this.mSimPbRecords.get(index - 1).getRecordNumber();
                        log("Record number for deleted ADN is " + adnRecordIndex);
                        if (recordIndex == adnRecordIndex) {
                            this.mSimPbRecords.remove(index - 1);
                            this.mValidAdnCount--;
                        } else {
                            e = new RuntimeException("The index for deleted ADN record did not match");
                        }
                    } else {
                        int adnRecordIndex2 = this.mSimPbRecords.get(index - 1).getRecordNumber();
                        log("Record number for changed ADN is " + adnRecordIndex2);
                        if (recordIndex == adnRecordIndex2) {
                            adn.setRecordNumber(recordIndex);
                            this.mSimPbRecords.set(index - 1, adn);
                        } else {
                            e = new RuntimeException("The index for changed ADN record did not match");
                        }
                    }
                } else {
                    e = new RuntimeException("Update adn record failed", ar.exception);
                }
                Message message = this.mAdnUpdatingWaiter;
                if (message != null) {
                    AsyncResult.forMessage(message, (Object) null, e);
                    this.mAdnUpdatingWaiter.sendToTarget();
                    this.mAdnUpdatingWaiter = null;
                    return;
                }
                return;
            case 6:
                AsyncResult ar3 = (AsyncResult) msg.obj;
                log("SIM REFRESH occurred");
                if (ar3.exception == null) {
                    IccRefreshResponse refreshRsp = (IccRefreshResponse) ar3.result;
                    if (refreshRsp == null) {
                        log("IccRefreshResponse received is null");
                        return;
                    } else if (refreshRsp.refreshResult == 0 || refreshRsp.refreshResult == 1) {
                        invalidateAdnCache();
                        return;
                    } else {
                        return;
                    }
                } else {
                    log("SIM refresh Exception: " + ar3.exception);
                    return;
                }
            default:
                return;
        }
    }

    public int getAdnCount() {
        return this.mAdnCount;
    }

    public int getUsedAdnCount() {
        return this.mValidAdnCount;
    }

    public int getEmailCount() {
        return this.mEmailCount;
    }

    public int getUsedEmailCount() {
        return this.mValidEmailCount;
    }

    public int getAnrCount() {
        return this.mAddNumCount;
    }

    public int getUsedAnrCount() {
        return this.mValidAddNumCount;
    }

    public int getMaxNameLen() {
        return this.mMaxNameLen;
    }

    public int getMaxNumberLen() {
        return this.mMaxNumberLen;
    }

    public int getMaxEmailLen() {
        return this.mMaxEmailLen;
    }

    public int getMaxAnrLen() {
        return this.mMaxAnrLen;
    }

    /* access modifiers changed from: private */
    public void log(String msg) {
        Rlog.d(LOG_TAG, msg);
    }

    public void invalidateAdnCache() {
        log("invalidateAdnCache");
        this.mRefreshAdnCache = DBG;
    }

    private void refreshAdnCache() {
        log("refreshAdnCache");
        this.mSimPbRecords.clear();
        queryAdnRecord();
    }

    @VisibleForTesting
    public ArrayList<AdnRecord> getSimPbRecords() {
        return this.mSimPbRecords;
    }

    @VisibleForTesting
    public boolean getRefreshAdnCache() {
        return this.mRefreshAdnCache;
    }
}
