package com.qualcomm.qti.internal.telephony;

import android.os.Message;
import android.os.SystemProperties;
import com.android.internal.telephony.IccCardConstants.State;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.UiccController;
import com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class QtiIccPhoneBookInterfaceManager extends IccPhoneBookInterfaceManager {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "QtiIccPhoneBookInterfaceManager";
    private int mPhoneId;
    private QtiSimPhoneBookAdnRecordCache mSimPbAdnCache;

    public QtiIccPhoneBookInterfaceManager(Phone phone) {
        super(phone);
        this.mPhoneId = phone.getPhoneId();
        if (isSimPhoneBookEnabled() && this.mSimPbAdnCache == null) {
            this.mSimPbAdnCache = new QtiSimPhoneBookAdnRecordCache(phone.getContext(), phone.getPhoneId(), phone.mCi);
        }
    }

    private boolean isSimPhoneBookEnabled() {
        if (!SystemProperties.getBoolean("persist.vendor.radio.sim_contacts_from_iccio", false)) {
            return DBG;
        }
        return false;
    }

    public void dispose() {
        if (this.mRecords != null) {
            this.mRecords.clear();
        }
    }

    public List<AdnRecord> getAdnRecordsInEf(int efid) {
        if (this.mPhone.getContext().checkCallingOrSelfPermission("android.permission.READ_CONTACTS") == 0) {
            int efid2 = updateEfForIccType(efid);
            StringBuilder sb = new StringBuilder();
            sb.append("getAdnRecordsInEF: efid=0x");
            sb.append(Integer.toHexString(efid2).toUpperCase());
            logd(sb.toString());
            synchronized (this.mLock) {
                checkThread();
                AtomicBoolean status = new AtomicBoolean(false);
                Message response = this.mBaseHandler.obtainMessage(2, status);
                if (!isSimPhoneBookEnabled() || !(efid2 == 20272 || efid2 == 28474)) {
                    if (this.mAdnCache != null) {
                        this.mAdnCache.requestLoadAllAdnLike(efid2, this.mAdnCache.extensionEfForEf(efid2), response);
                        waitForResult(status);
                    } else {
                        loge("Failure while trying to load from SIM due to uninitialised adncache");
                    }
                } else if (this.mSimPbAdnCache != null) {
                    this.mSimPbAdnCache.requestLoadAllAdnLike(response);
                    waitForResult(status);
                } else {
                    loge("Failure while trying to load from SIM due to uninit  sim pb adncache");
                }
            }
            return this.mRecords;
        }
        throw new SecurityException("Requires android.permission.READ_CONTACTS permission");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0116, code lost:
        return r1.mSuccess;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean updateAdnRecordsWithContentValuesInEfBySearch(int r28, android.content.ContentValues r29, java.lang.String r30) {
        /*
            r27 = this;
            r1 = r27
            r2 = r29
            com.android.internal.telephony.Phone r0 = r1.mPhone
            android.content.Context r0 = r0.getContext()
            java.lang.String r3 = "android.permission.WRITE_CONTACTS"
            int r0 = r0.checkCallingOrSelfPermission(r3)
            if (r0 != 0) goto L_0x0142
            java.lang.String r0 = "tag"
            java.lang.String r3 = r2.getAsString(r0)
            java.lang.String r0 = "newTag"
            java.lang.String r4 = r2.getAsString(r0)
            java.lang.String r0 = "number"
            java.lang.String r5 = r2.getAsString(r0)
            java.lang.String r0 = "newNumber"
            java.lang.String r6 = r2.getAsString(r0)
            java.lang.String r0 = "emails"
            java.lang.String r7 = r2.getAsString(r0)
            java.lang.String r0 = "newEmails"
            java.lang.String r8 = r2.getAsString(r0)
            java.lang.String r0 = "anrs"
            java.lang.String r9 = r2.getAsString(r0)
            java.lang.String r0 = "newAnrs"
            java.lang.String r10 = r2.getAsString(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            r11 = 0
            if (r0 == 0) goto L_0x004b
            r0 = r11
            goto L_0x004f
        L_0x004b:
            java.lang.String[] r0 = r1.getStringArray(r7)
        L_0x004f:
            r12 = r0
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0058
            r0 = r11
            goto L_0x005c
        L_0x0058:
            java.lang.String[] r0 = r1.getStringArray(r8)
        L_0x005c:
            r13 = r0
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L_0x0065
            r0 = r11
            goto L_0x0069
        L_0x0065:
            java.lang.String[] r0 = r1.getAnrStringArray(r9)
        L_0x0069:
            r14 = r0
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L_0x0071
            goto L_0x0075
        L_0x0071:
            java.lang.String[] r11 = r1.getAnrStringArray(r10)
        L_0x0075:
            int r15 = r27.updateEfForIccType(r28)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r21 = r7
            java.lang.String r7 = "updateAdnRecordsWithContentValuesInEfBySearch: efid="
            r0.append(r7)
            r0.append(r15)
            java.lang.String r7 = ", values = "
            r0.append(r7)
            r0.append(r2)
            java.lang.String r7 = ", pin2="
            r0.append(r7)
            r7 = r30
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            r1.logd(r0)
            java.lang.Object r2 = r1.mLock
            monitor-enter(r2)
            r27.checkThread()     // Catch:{ all -> 0x0133 }
            r0 = 0
            r1.mSuccess = r0     // Catch:{ all -> 0x0133 }
            java.util.concurrent.atomic.AtomicBoolean r0 = new java.util.concurrent.atomic.AtomicBoolean     // Catch:{ all -> 0x0133 }
            r23 = r8
            r8 = 0
            r0.<init>(r8)     // Catch:{ all -> 0x0129 }
            com.android.internal.telephony.IccPhoneBookInterfaceManager$IccPbHandler r8 = r1.mBaseHandler     // Catch:{ all -> 0x0129 }
            r24 = r9
            r9 = 3
            android.os.Message r8 = r8.obtainMessage(r9, r0)     // Catch:{ all -> 0x0121 }
            com.android.internal.telephony.uicc.AdnRecord r9 = new com.android.internal.telephony.uicc.AdnRecord     // Catch:{ all -> 0x0121 }
            r9.<init>(r3, r5, r12, r14)     // Catch:{ all -> 0x0121 }
            r25 = r3
            com.android.internal.telephony.uicc.AdnRecord r3 = new com.android.internal.telephony.uicc.AdnRecord     // Catch:{ all -> 0x011b }
            r3.<init>(r4, r6, r13, r11)     // Catch:{ all -> 0x011b }
            boolean r16 = r27.isSimPhoneBookEnabled()     // Catch:{ all -> 0x011b }
            if (r16 == 0) goto L_0x00f0
            r26 = r4
            r4 = 20272(0x4f30, float:2.8407E-41)
            if (r15 == r4) goto L_0x00d7
            r4 = 28474(0x6f3a, float:3.99E-41)
            if (r15 != r4) goto L_0x00f2
        L_0x00d7:
            com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache r4 = r1.mSimPbAdnCache     // Catch:{ all -> 0x00ec }
            if (r4 == 0) goto L_0x00e4
            com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache r4 = r1.mSimPbAdnCache     // Catch:{ all -> 0x00ec }
            r4.updateSimPbAdnBySearch(r9, r3, r8)     // Catch:{ all -> 0x00ec }
            r1.waitForResult(r0)     // Catch:{ all -> 0x00ec }
            goto L_0x00e9
        L_0x00e4:
            java.lang.String r4 = "Failure while trying to update by search due to uninit sim pb adncache"
            r1.loge(r4)     // Catch:{ all -> 0x00ec }
        L_0x00e9:
            r22 = r15
            goto L_0x0113
        L_0x00ec:
            r0 = move-exception
            r22 = r15
            goto L_0x013e
        L_0x00f0:
            r26 = r4
        L_0x00f2:
            com.android.internal.telephony.uicc.AdnRecordCache r4 = r1.mAdnCache     // Catch:{ all -> 0x0117 }
            if (r4 == 0) goto L_0x010c
            com.android.internal.telephony.uicc.AdnRecordCache r4 = r1.mAdnCache     // Catch:{ all -> 0x0117 }
            r22 = r15
            r15 = r4
            r16 = r22
            r17 = r9
            r18 = r3
            r19 = r7
            r20 = r8
            r15.updateAdnBySearch(r16, r17, r18, r19, r20)     // Catch:{ all -> 0x0140 }
            r1.waitForResult(r0)     // Catch:{ all -> 0x0140 }
            goto L_0x0113
        L_0x010c:
            r22 = r15
            java.lang.String r4 = "Failure while trying to update by search due to uninitialised adncache"
            r1.loge(r4)     // Catch:{ all -> 0x0140 }
        L_0x0113:
            monitor-exit(r2)     // Catch:{ all -> 0x0140 }
            boolean r0 = r1.mSuccess
            return r0
        L_0x0117:
            r0 = move-exception
            r22 = r15
            goto L_0x013e
        L_0x011b:
            r0 = move-exception
            r26 = r4
            r22 = r15
            goto L_0x013e
        L_0x0121:
            r0 = move-exception
            r25 = r3
            r26 = r4
            r22 = r15
            goto L_0x013e
        L_0x0129:
            r0 = move-exception
            r25 = r3
            r26 = r4
            r24 = r9
            r22 = r15
            goto L_0x013e
        L_0x0133:
            r0 = move-exception
            r25 = r3
            r26 = r4
            r23 = r8
            r24 = r9
            r22 = r15
        L_0x013e:
            monitor-exit(r2)     // Catch:{ all -> 0x0140 }
            throw r0
        L_0x0140:
            r0 = move-exception
            goto L_0x013e
        L_0x0142:
            r7 = r30
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.String r2 = "Requires android.permission.WRITE_CONTACTS permission"
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiIccPhoneBookInterfaceManager.updateAdnRecordsWithContentValuesInEfBySearch(int, android.content.ContentValues, java.lang.String):boolean");
    }

    public int[] getAdnRecordsCapacity() {
        int[] capacity = new int[10];
        if (isSimPhoneBookEnabled()) {
            if (this.mSimPbAdnCache != null) {
                State cardstate = UiccController.getInstance().getUiccProfileForPhone(this.mPhoneId).getState();
                if (cardstate == State.READY || cardstate == State.LOADED) {
                    capacity[0] = this.mSimPbAdnCache.getAdnCount();
                    capacity[1] = this.mSimPbAdnCache.getUsedAdnCount();
                    capacity[2] = this.mSimPbAdnCache.getEmailCount();
                    capacity[3] = this.mSimPbAdnCache.getUsedEmailCount();
                    capacity[4] = this.mSimPbAdnCache.getAnrCount();
                    capacity[5] = this.mSimPbAdnCache.getUsedAnrCount();
                    capacity[6] = this.mSimPbAdnCache.getMaxNameLen();
                    capacity[7] = this.mSimPbAdnCache.getMaxNumberLen();
                    capacity[8] = this.mSimPbAdnCache.getMaxEmailLen();
                    capacity[9] = this.mSimPbAdnCache.getMaxAnrLen();
                } else {
                    logd("sim state is not ready when getAdnRecordsCapacity.");
                }
            } else {
                loge("mAdnCache is NULL when getAdnRecordsCapacity.");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("getAdnRecordsCapacity on slot ");
        sb.append(this.mPhoneId);
        sb.append(": max adn=");
        sb.append(capacity[0]);
        sb.append(", used adn=");
        sb.append(capacity[1]);
        sb.append(", max email=");
        sb.append(capacity[2]);
        sb.append(", used email=");
        sb.append(capacity[3]);
        sb.append(", max anr=");
        sb.append(capacity[4]);
        sb.append(", used anr=");
        sb.append(capacity[5]);
        sb.append(", max name length =");
        sb.append(capacity[6]);
        sb.append(", max number length =");
        sb.append(capacity[7]);
        sb.append(", max email length =");
        sb.append(capacity[8]);
        sb.append(", max anr length =");
        sb.append(capacity[9]);
        logd(sb.toString());
        return capacity;
    }
}
