package com.qualcomm.qti.internal.telephony;

import android.os.Message;
import android.os.SystemProperties;
import com.android.internal.telephony.IccCardConstants.State;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.IccPhoneBookInterfaceManager.Request;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.UiccController;
import com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache;
import java.util.List;

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

    public List<AdnRecord> getAdnRecordsInEf(int efid) {
        if (this.mPhone.getContext().checkCallingOrSelfPermission("android.permission.READ_CONTACTS") == 0) {
            int efid2 = updateEfForIccType(efid);
            StringBuilder sb = new StringBuilder();
            sb.append("getAdnRecordsInEF: efid=0x");
            sb.append(Integer.toHexString(efid2).toUpperCase());
            logd(sb.toString());
            checkThread();
            Request loadRequest = new Request();
            synchronized (loadRequest) {
                Message response = this.mBaseHandler.obtainMessage(2, loadRequest);
                if (!isSimPhoneBookEnabled() || !(efid2 == 20272 || efid2 == 28474)) {
                    if (this.mAdnCache != null) {
                        this.mAdnCache.requestLoadAllAdnLike(efid2, this.mAdnCache.extensionEfForEf(efid2), response);
                        waitForResult(loadRequest);
                    } else {
                        loge("Failure while trying to load from SIM due to uninitialised adncache");
                    }
                } else if (this.mSimPbAdnCache != null) {
                    this.mSimPbAdnCache.requestLoadAllAdnLike(response);
                    waitForResult(loadRequest);
                } else {
                    loge("Failure while trying to load from SIM due to uninit  sim pb adncache");
                }
            }
            return (List) loadRequest.mResult;
        }
        throw new SecurityException("Requires android.permission.READ_CONTACTS permission");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0118, code lost:
        return ((java.lang.Boolean) r2.mResult).booleanValue();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean updateAdnRecordsWithContentValuesInEfBySearch(int r26, android.content.ContentValues r27, java.lang.String r28) {
        /*
            r25 = this;
            r1 = r25
            r2 = r27
            com.android.internal.telephony.Phone r0 = r1.mPhone
            android.content.Context r0 = r0.getContext()
            java.lang.String r3 = "android.permission.WRITE_CONTACTS"
            int r0 = r0.checkCallingOrSelfPermission(r3)
            if (r0 != 0) goto L_0x012e
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
            int r15 = r25.updateEfForIccType(r26)
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
            r7 = r28
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            r1.logd(r0)
            r25.checkThread()
            com.android.internal.telephony.IccPhoneBookInterfaceManager$Request r0 = new com.android.internal.telephony.IccPhoneBookInterfaceManager$Request
            r0.<init>()
            r22 = r0
            r2 = r22
            monitor-enter(r2)
            android.os.Handler r0 = r1.mBaseHandler     // Catch:{ all -> 0x0123 }
            r7 = 3
            android.os.Message r0 = r0.obtainMessage(r7, r2)     // Catch:{ all -> 0x0123 }
            com.android.internal.telephony.uicc.AdnRecord r7 = new com.android.internal.telephony.uicc.AdnRecord     // Catch:{ all -> 0x0123 }
            r7.<init>(r3, r5, r12, r14)     // Catch:{ all -> 0x0123 }
            r22 = r3
            com.android.internal.telephony.uicc.AdnRecord r3 = new com.android.internal.telephony.uicc.AdnRecord     // Catch:{ all -> 0x011d }
            r3.<init>(r4, r6, r13, r11)     // Catch:{ all -> 0x011d }
            boolean r16 = r25.isSimPhoneBookEnabled()     // Catch:{ all -> 0x011d }
            if (r16 == 0) goto L_0x00ec
            r23 = r4
            r4 = 20272(0x4f30, float:2.8407E-41)
            if (r15 == r4) goto L_0x00d1
            r4 = 28474(0x6f3a, float:3.99E-41)
            if (r15 != r4) goto L_0x00ee
        L_0x00d1:
            com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache r4 = r1.mSimPbAdnCache     // Catch:{ all -> 0x00e8 }
            if (r4 == 0) goto L_0x00e0
            com.qualcomm.qti.internal.telephony.uicccontact.QtiSimPhoneBookAdnRecordCache r4 = r1.mSimPbAdnCache     // Catch:{ all -> 0x00e8 }
            r4.updateSimPbAdnBySearch(r7, r3, r0)     // Catch:{ all -> 0x00e8 }
            r1.waitForResult(r2)     // Catch:{ all -> 0x00e8 }
            r24 = r15
            goto L_0x010f
        L_0x00e0:
            java.lang.String r4 = "Failure while trying to update by search due to uninit sim pb adncache"
            r1.loge(r4)     // Catch:{ all -> 0x00e8 }
            r24 = r15
            goto L_0x010f
        L_0x00e8:
            r0 = move-exception
            r24 = r15
            goto L_0x012a
        L_0x00ec:
            r23 = r4
        L_0x00ee:
            com.android.internal.telephony.uicc.AdnRecordCache r4 = r1.mAdnCache     // Catch:{ all -> 0x0119 }
            if (r4 == 0) goto L_0x0108
            com.android.internal.telephony.uicc.AdnRecordCache r4 = r1.mAdnCache     // Catch:{ all -> 0x0119 }
            r24 = r15
            r15 = r4
            r16 = r24
            r17 = r7
            r18 = r3
            r19 = r28
            r20 = r0
            r15.updateAdnBySearch(r16, r17, r18, r19, r20)     // Catch:{ all -> 0x012c }
            r1.waitForResult(r2)     // Catch:{ all -> 0x012c }
            goto L_0x010f
        L_0x0108:
            r24 = r15
            java.lang.String r4 = "Failure while trying to update by search due to uninitialised adncache"
            r1.loge(r4)     // Catch:{ all -> 0x012c }
        L_0x010f:
            monitor-exit(r2)     // Catch:{ all -> 0x012c }
            java.lang.Object r0 = r2.mResult
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x0119:
            r0 = move-exception
            r24 = r15
            goto L_0x012a
        L_0x011d:
            r0 = move-exception
            r23 = r4
            r24 = r15
            goto L_0x012a
        L_0x0123:
            r0 = move-exception
            r22 = r3
            r23 = r4
            r24 = r15
        L_0x012a:
            monitor-exit(r2)     // Catch:{ all -> 0x012c }
            throw r0
        L_0x012c:
            r0 = move-exception
            goto L_0x012a
        L_0x012e:
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
