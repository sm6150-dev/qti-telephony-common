package com.qualcomm.qti.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.RadioAccessFamily;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants.State;
import com.android.internal.telephony.ProxyController;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import java.util.HashMap;

public class QtiRadioCapabilityController extends Handler {
    static final String ALLOW_FLEX_MAPPING_ON_INACTIVE_SUB_PROPERTY = "persist.radio.flex_map_inactive";
    private static final boolean DBG = true;
    private static final int EVENT_RADIO_CAPS_AVAILABLE = 2;
    private static final int EVENT_RADIO_NOT_AVAILABLE = 1;
    private static final int EVENT_UPDATE_BINDING_DONE = 3;
    private static final int FAILURE = 0;
    private static final String LOG_TAG = "QtiRadioCapabilityController";
    private static final int SUCCESS = 1;
    private static final boolean VDBG = false;
    private static int mNumPhones;
    private static QtiRadioCapabilityController sInstance;
    private static Object sSetNwModeLock = new Object();
    private boolean bothPhonesMappedToSameStack = VDBG;
    private CommandsInterface[] mCi;
    private Context mContext;
    private int[] mCurrentStackId;
    private boolean mIsSetPrefNwModeInProgress = VDBG;
    private boolean mNeedSetDds = VDBG;
    private Phone[] mPhone;
    private int[] mPrefNwMode;
    private int[] mPreferredStackId;
    private QtiSubscriptionController mQtiSubscriptionController = null;
    private int[] mRadioAccessFamily;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            StringBuilder sb = new StringBuilder();
            sb.append("mReceiver: action ");
            sb.append(action);
            Rlog.d(QtiRadioCapabilityController.LOG_TAG, sb.toString());
            if (action.equals("android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE")) {
                QtiRadioCapabilityController qtiRadioCapabilityController = QtiRadioCapabilityController.this;
                qtiRadioCapabilityController.sendMessage(qtiRadioCapabilityController.obtainMessage(3, 1, -1));
            } else if (action.equals("android.intent.action.ACTION_SET_RADIO_CAPABILITY_FAILED")) {
                QtiRadioCapabilityController qtiRadioCapabilityController2 = QtiRadioCapabilityController.this;
                qtiRadioCapabilityController2.sendMessage(qtiRadioCapabilityController2.obtainMessage(3, 0, -1));
            }
        }
    };
    private HashMap<Integer, Message> mStoredResponse = new HashMap<>();
    private SubscriptionManager mSubscriptionManager = null;

    public static QtiRadioCapabilityController make(Context context, Phone[] phone, CommandsInterface[] ci) {
        String str = LOG_TAG;
        Rlog.d(str, "getInstance");
        if (sInstance == null) {
            sInstance = new QtiRadioCapabilityController(context, phone, ci);
        } else {
            Log.wtf(str, "QtiRadioCapabilityController.make() should be called once");
        }
        return sInstance;
    }

    public static QtiRadioCapabilityController getInstance() {
        if (sInstance == null) {
            Log.e(LOG_TAG, "QtiRadioCapabilityController.getInstance called before make");
        }
        return sInstance;
    }

    private QtiRadioCapabilityController(Context context, Phone[] phone, CommandsInterface[] ci) {
        this.mCi = ci;
        this.mContext = context;
        this.mPhone = phone;
        mNumPhones = ((TelephonyManager) this.mContext.getSystemService("phone")).getPhoneCount();
        int i = mNumPhones;
        this.mPreferredStackId = new int[i];
        this.mCurrentStackId = new int[i];
        this.mPrefNwMode = new int[i];
        this.mRadioAccessFamily = new int[i];
        this.mSubscriptionManager = (SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        this.mQtiSubscriptionController = QtiSubscriptionController.getInstance();
        int i2 = 0;
        while (true) {
            CommandsInterface[] commandsInterfaceArr = this.mCi;
            if (i2 < commandsInterfaceArr.length) {
                commandsInterfaceArr[i2].registerForNotAvailable(this, 1, new Integer(i2));
                this.mStoredResponse.put(Integer.valueOf(i2), null);
                i2++;
            } else {
                IntentFilter filter = new IntentFilter("android.intent.action.ACTION_SET_RADIO_CAPABILITY_DONE");
                filter.addAction("android.intent.action.ACTION_SET_RADIO_CAPABILITY_FAILED");
                context.registerReceiver(this.mReceiver, filter);
                logd("Constructor - Exit");
                return;
            }
        }
    }

    public void handleMessage(Message msg) {
        int i = msg.what;
        if (i != 1) {
            if (i == 2) {
                handleRadioCapsAvailable();
            } else if (i == 3) {
                logv(" EVENT_UPDATE_BINDING_DONE ");
                handleUpdateBindingDone(msg.arg1);
            }
        } else if (msg.obj != null) {
            AsyncResult ar = (AsyncResult) msg.obj;
            if (ar.userObj != null) {
                Integer phoneId = (Integer) ar.userObj;
                StringBuilder sb = new StringBuilder();
                sb.append("EVENT_RADIO_NOT_AVAILABLE, phoneId = ");
                sb.append(phoneId);
                logd(sb.toString());
                processRadioNotAvailable(ar, phoneId.intValue());
                return;
            }
            loge("Invalid user obj");
        } else {
            loge("Invalid msg obj");
        }
    }

    private boolean areAllModemCapInfoReceived() {
        for (int i = 0; i < mNumPhones; i++) {
            if (this.mPhone[i].getRadioCapability() == null) {
                return VDBG;
            }
        }
        return DBG;
    }

    private boolean isFlexMappingAllowedOnInactiveSub() {
        return SystemProperties.getBoolean(ALLOW_FLEX_MAPPING_ON_INACTIVE_SUB_PROPERTY, VDBG);
    }

    private void handleUpdateBindingDone(int result) {
        boolean z = this.bothPhonesMappedToSameStack;
        boolean z2 = VDBG;
        if (z && result == 1) {
            this.bothPhonesMappedToSameStack = VDBG;
            if (SystemProperties.get("persist.vendor.radio.flexmap_type", "nw_mode").equals("dds")) {
                logd("handleUpdateBindingDone: set dds ");
                QtiSubscriptionController qtiSubscriptionController = this.mQtiSubscriptionController;
                qtiSubscriptionController.setDefaultDataSubId(qtiSubscriptionController.getDefaultDataSubId());
            } else {
                for (int i = 0; i < mNumPhones; i++) {
                    if (((Message) this.mStoredResponse.get(Integer.valueOf(i))) != null) {
                        logd("handleUpdateBindingDone: try initiate pending flex map req ");
                        if (updateStackBindingIfRequired(DBG)) {
                            return;
                        }
                    }
                }
            }
        }
        if (result == 1) {
            updateNewNwModeToDB();
            for (int i2 = 0; i2 < mNumPhones; i2++) {
                this.mPhone[i2].fetchIMEI();
            }
        }
        for (int i3 = 0; i3 < mNumPhones; i3++) {
            sendSubscriptionSettings(i3);
        }
        setDdsIfRequired(DBG);
        setNWModeInProgressFlag(VDBG);
        if (result == 1) {
            z2 = true;
        }
        notifyRadioCapsUpdated(z2);
        for (int i4 = 0; i4 < mNumPhones; i4++) {
            int errorCode = 0;
            Message resp = (Message) this.mStoredResponse.get(Integer.valueOf(i4));
            if (resp != null) {
                if (result != 1) {
                    errorCode = 2;
                }
                sendResponseToTarget(resp, errorCode);
                this.mStoredResponse.put(Integer.valueOf(i4), null);
            }
        }
    }

    private void handleRadioCapsAvailable() {
        logd("handleRadioCapsAvailable... ");
        if (updateStackBindingIfRequired(VDBG)) {
            setNWModeInProgressFlag(DBG);
        } else {
            notifyRadioCapsUpdated(VDBG);
        }
    }

    private void processRadioNotAvailable(AsyncResult ar, int phoneId) {
        StringBuilder sb = new StringBuilder();
        sb.append("processRadioNotAvailable on phoneId = ");
        sb.append(phoneId);
        logd(sb.toString());
        this.mNeedSetDds = DBG;
    }

    private void syncCurrentStackInfo() {
        int i = 0;
        while (i < mNumPhones) {
            this.mCurrentStackId[i] = Integer.valueOf(this.mPhone[i].getModemUuId()).intValue();
            this.mRadioAccessFamily[this.mCurrentStackId[i]] = this.mPhone[i].getRadioAccessFamily();
            int[] iArr = this.mPreferredStackId;
            int[] iArr2 = this.mCurrentStackId;
            iArr[i] = iArr2[i] >= 0 ? iArr2[i] : i;
            StringBuilder sb = new StringBuilder();
            sb.append("syncCurrentStackInfo, current stackId[");
            sb.append(i);
            sb.append("] = ");
            sb.append(this.mCurrentStackId[i]);
            sb.append(" raf = ");
            sb.append(this.mRadioAccessFamily[this.mCurrentStackId[i]]);
            logv(sb.toString());
            i++;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0103, code lost:
        return r1;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:52:0x0104=Splitter:B:52:0x0104, B:60:0x0128=Splitter:B:60:0x0128, B:56:0x010a=Splitter:B:56:0x010a, B:29:0x006f=Splitter:B:29:0x006f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean updateStackBindingIfRequired(boolean r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            r0 = 0
            r1 = 0
            boolean r2 = r10.isAnyCallsInProgress()     // Catch:{ all -> 0x0148 }
            boolean r3 = r10.isAnyPhoneInEcmState()     // Catch:{ all -> 0x0148 }
            java.lang.String r4 = "persist.vendor.radio.flexmap_type"
            java.lang.String r5 = "nw_mode"
            java.lang.String r4 = android.os.SystemProperties.get(r4, r5)     // Catch:{ all -> 0x0148 }
            java.lang.String r5 = "updateStackBindingIfRequired"
            r10.logd(r5)     // Catch:{ all -> 0x0148 }
            int r5 = mNumPhones     // Catch:{ all -> 0x0148 }
            r6 = 1
            if (r5 == r6) goto L_0x0128
            java.lang.String r5 = "nw_mode"
            boolean r5 = r4.equals(r5)     // Catch:{ all -> 0x0148 }
            if (r5 != 0) goto L_0x0027
            goto L_0x0128
        L_0x0027:
            if (r2 != 0) goto L_0x010a
            if (r3 != 0) goto L_0x010a
            boolean r5 = r10.areAllModemCapInfoReceived()     // Catch:{ all -> 0x0148 }
            if (r5 != 0) goto L_0x0033
            goto L_0x010a
        L_0x0033:
            r5 = 0
            if (r11 != 0) goto L_0x006f
            r6 = r5
        L_0x0037:
            int r7 = mNumPhones     // Catch:{ all -> 0x0148 }
            if (r6 >= r7) goto L_0x006f
            com.qualcomm.qti.internal.telephony.QtiSubscriptionController r7 = r10.mQtiSubscriptionController     // Catch:{ all -> 0x0148 }
            int[] r7 = r7.getSubId(r6)     // Catch:{ all -> 0x0148 }
            boolean r8 = r10.isCardAbsent(r6)     // Catch:{ all -> 0x0148 }
            if (r8 != 0) goto L_0x006c
            if (r7 == 0) goto L_0x0056
            int r8 = r7.length     // Catch:{ all -> 0x0148 }
            if (r8 <= 0) goto L_0x0056
            android.telephony.SubscriptionManager r8 = r10.mSubscriptionManager     // Catch:{ all -> 0x0148 }
            r9 = r7[r5]     // Catch:{ all -> 0x0148 }
            boolean r8 = r8.isActiveSubscriptionId(r9)     // Catch:{ all -> 0x0148 }
            if (r8 != 0) goto L_0x006c
        L_0x0056:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            r5.<init>()     // Catch:{ all -> 0x0148 }
            java.lang.String r8 = "Error: subId not generated yet "
            r5.append(r8)     // Catch:{ all -> 0x0148 }
            r5.append(r6)     // Catch:{ all -> 0x0148 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0148 }
            r10.loge(r5)     // Catch:{ all -> 0x0148 }
            monitor-exit(r10)
            return r1
        L_0x006c:
            int r6 = r6 + 1
            goto L_0x0037
        L_0x006f:
            boolean r6 = r10.isBothPhonesMappedToSameStack()     // Catch:{ all -> 0x0148 }
            if (r6 != 0) goto L_0x0104
            boolean r6 = r10.is7Plus7DeviceFlexMapped()     // Catch:{ all -> 0x0148 }
            if (r6 == 0) goto L_0x007d
            goto L_0x0104
        L_0x007d:
            r10.updatePreferredStackIds(r11)     // Catch:{ all -> 0x0148 }
            r6 = r5
        L_0x0081:
            int r7 = mNumPhones     // Catch:{ all -> 0x0148 }
            if (r6 >= r7) goto L_0x00c8
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            r7.<init>()     // Catch:{ all -> 0x0148 }
            java.lang.String r8 = " pref stack["
            r7.append(r8)     // Catch:{ all -> 0x0148 }
            r7.append(r6)     // Catch:{ all -> 0x0148 }
            java.lang.String r8 = "] = "
            r7.append(r8)     // Catch:{ all -> 0x0148 }
            int[] r8 = r10.mPreferredStackId     // Catch:{ all -> 0x0148 }
            r8 = r8[r6]     // Catch:{ all -> 0x0148 }
            r7.append(r8)     // Catch:{ all -> 0x0148 }
            java.lang.String r8 = " current stack["
            r7.append(r8)     // Catch:{ all -> 0x0148 }
            r7.append(r6)     // Catch:{ all -> 0x0148 }
            java.lang.String r8 = "] = "
            r7.append(r8)     // Catch:{ all -> 0x0148 }
            int[] r8 = r10.mCurrentStackId     // Catch:{ all -> 0x0148 }
            r8 = r8[r6]     // Catch:{ all -> 0x0148 }
            r7.append(r8)     // Catch:{ all -> 0x0148 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0148 }
            r10.logv(r7)     // Catch:{ all -> 0x0148 }
            int[] r7 = r10.mPreferredStackId     // Catch:{ all -> 0x0148 }
            r7 = r7[r6]     // Catch:{ all -> 0x0148 }
            int[] r8 = r10.mCurrentStackId     // Catch:{ all -> 0x0148 }
            r8 = r8[r6]     // Catch:{ all -> 0x0148 }
            if (r7 == r8) goto L_0x00c5
            r0 = 1
            goto L_0x00c8
        L_0x00c5:
            int r6 = r6 + 1
            goto L_0x0081
        L_0x00c8:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            r6.<init>()     // Catch:{ all -> 0x0148 }
            java.lang.String r7 = " updateStackBindingIfRequired, required =  "
            r6.append(r7)     // Catch:{ all -> 0x0148 }
            r6.append(r0)     // Catch:{ all -> 0x0148 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0148 }
            r10.logd(r6)     // Catch:{ all -> 0x0148 }
            if (r0 == 0) goto L_0x0102
            int r6 = mNumPhones     // Catch:{ all -> 0x0148 }
            android.telephony.RadioAccessFamily[] r6 = new android.telephony.RadioAccessFamily[r6]     // Catch:{ all -> 0x0148 }
        L_0x00e3:
            int r7 = mNumPhones     // Catch:{ all -> 0x0148 }
            if (r5 >= r7) goto L_0x00f9
            android.telephony.RadioAccessFamily r7 = new android.telephony.RadioAccessFamily     // Catch:{ all -> 0x0148 }
            int[] r8 = r10.mRadioAccessFamily     // Catch:{ all -> 0x0148 }
            int[] r9 = r10.mPreferredStackId     // Catch:{ all -> 0x0148 }
            r9 = r9[r5]     // Catch:{ all -> 0x0148 }
            r8 = r8[r9]     // Catch:{ all -> 0x0148 }
            r7.<init>(r5, r8)     // Catch:{ all -> 0x0148 }
            r6[r5] = r7     // Catch:{ all -> 0x0148 }
            int r5 = r5 + 1
            goto L_0x00e3
        L_0x00f9:
            com.android.internal.telephony.ProxyController r5 = com.android.internal.telephony.ProxyController.getInstance()     // Catch:{ all -> 0x0148 }
            boolean r5 = r5.setRadioCapability(r6)     // Catch:{ all -> 0x0148 }
            r1 = r5
        L_0x0102:
            monitor-exit(r10)
            return r1
        L_0x0104:
            boolean r5 = r10.initNormalMappingRequest()     // Catch:{ all -> 0x0148 }
            monitor-exit(r10)
            return r5
        L_0x010a:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            r5.<init>()     // Catch:{ all -> 0x0148 }
            java.lang.String r6 = "Error: Call state = "
            r5.append(r6)     // Catch:{ all -> 0x0148 }
            r5.append(r2)     // Catch:{ all -> 0x0148 }
            java.lang.String r6 = ", ecm state = "
            r5.append(r6)     // Catch:{ all -> 0x0148 }
            r5.append(r3)     // Catch:{ all -> 0x0148 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0148 }
            r10.loge(r5)     // Catch:{ all -> 0x0148 }
            monitor-exit(r10)
            return r1
        L_0x0128:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            r5.<init>()     // Catch:{ all -> 0x0148 }
            java.lang.String r6 = "No need to update Stack Bindingm prop = "
            r5.append(r6)     // Catch:{ all -> 0x0148 }
            r5.append(r4)     // Catch:{ all -> 0x0148 }
            java.lang.String r6 = " ph count = "
            r5.append(r6)     // Catch:{ all -> 0x0148 }
            int r6 = mNumPhones     // Catch:{ all -> 0x0148 }
            r5.append(r6)     // Catch:{ all -> 0x0148 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0148 }
            r10.loge(r5)     // Catch:{ all -> 0x0148 }
            monitor-exit(r10)
            return r1
        L_0x0148:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiRadioCapabilityController.updateStackBindingIfRequired(boolean):boolean");
    }

    private void updatePreferredStackIds(boolean isNwModeRequest) {
        if (!areAllModemCapInfoReceived()) {
            StringBuilder sb = new StringBuilder();
            sb.append("updatePreferredStackIds: Modem Caps not Available, request =");
            sb.append(isNwModeRequest);
            loge(sb.toString());
            return;
        }
        if (!isNwModeRequest) {
            syncPreferredNwModeFromDB();
        }
        syncCurrentStackInfo();
        for (int curPhoneId = 0; curPhoneId < mNumPhones; curPhoneId++) {
            String str = "] on phoneId[";
            String str2 = "]";
            if (isNwModeSupportedOnStack(this.mPrefNwMode[curPhoneId], this.mCurrentStackId[curPhoneId])) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("updatePreferredStackIds: current stack[");
                sb2.append(this.mCurrentStackId[curPhoneId]);
                sb2.append("]supports NwMode[");
                sb2.append(this.mPrefNwMode[curPhoneId]);
                sb2.append(str);
                sb2.append(curPhoneId);
                sb2.append(str2);
                logd(sb2.toString());
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("updatePreferredStackIds:  current stack[");
                sb3.append(this.mCurrentStackId[curPhoneId]);
                String str3 = "],  NwMode[";
                sb3.append(str3);
                sb3.append(this.mPrefNwMode[curPhoneId]);
                sb3.append(str);
                sb3.append(curPhoneId);
                sb3.append(str2);
                logd(sb3.toString());
                for (int otherPhoneId = 0; otherPhoneId < mNumPhones; otherPhoneId++) {
                    if (otherPhoneId != curPhoneId) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("updatePreferredStackIds:  other stack[");
                        sb4.append(this.mCurrentStackId[otherPhoneId]);
                        sb4.append(str3);
                        sb4.append(this.mPrefNwMode[curPhoneId]);
                        sb4.append(str);
                        sb4.append(curPhoneId);
                        sb4.append(str2);
                        logd(sb4.toString());
                        if (isNwModeSupportedOnStack(this.mPrefNwMode[curPhoneId], this.mCurrentStackId[otherPhoneId]) && ((isCardAbsent(otherPhoneId) && !isCardAbsent(curPhoneId)) || isNwModeSupportedOnStack(this.mPrefNwMode[otherPhoneId], this.mCurrentStackId[curPhoneId]))) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("updatePreferredStackIds: Cross Binding is possible between phoneId[");
                            sb5.append(curPhoneId);
                            sb5.append("] and phoneId[");
                            sb5.append(otherPhoneId);
                            sb5.append(str2);
                            logd(sb5.toString());
                            int[] iArr = this.mPreferredStackId;
                            int[] iArr2 = this.mCurrentStackId;
                            iArr[curPhoneId] = iArr2[otherPhoneId];
                            iArr[otherPhoneId] = iArr2[curPhoneId];
                        }
                    }
                }
            }
        }
    }

    private boolean isNwModeSupportedOnStack(int nwMode, int stackId) {
        int[] numRafSupported = new int[mNumPhones];
        int maxNumRafSupported = 0;
        boolean isSupported = VDBG;
        for (int i = 0; i < mNumPhones; i++) {
            numRafSupported[i] = getNumOfRafSupportedForNwMode(nwMode, this.mRadioAccessFamily[i]);
            if (maxNumRafSupported < numRafSupported[i]) {
                maxNumRafSupported = numRafSupported[i];
            }
        }
        if (numRafSupported[stackId] == maxNumRafSupported) {
            isSupported = DBG;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("nwMode:");
        sb.append(nwMode);
        sb.append(", on stack:");
        sb.append(stackId);
        sb.append(" is ");
        sb.append(isSupported ? "Supported" : "Not Supported");
        logd(sb.toString());
        return isSupported;
    }

    private void syncPreferredNwModeFromDB() {
        for (int i = 0; i < mNumPhones; i++) {
            this.mPrefNwMode[i] = getNetworkModeFromDB(i);
        }
    }

    private int getNetworkModeFromDB(int phoneId) {
        int networkMode;
        String str = "preferred_network_mode";
        int[] subId = this.mQtiSubscriptionController.getSubId(phoneId);
        try {
            networkMode = QtiPhoneUtils.getIntAtIndex(this.mContext.getContentResolver(), str, phoneId);
        } catch (SettingNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getNwMode: ");
            sb.append(phoneId);
            sb.append(" ,Could not find PREFERRED_NETWORK_MODE!!!");
            loge(sb.toString());
            networkMode = Phone.PREFERRED_NT_MODE;
        }
        String str2 = "] = ";
        if (subId == null || subId.length <= 0 || !this.mSubscriptionManager.isActiveSubscriptionId(subId[0])) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" get slotId based N/W mode, val[");
            sb2.append(phoneId);
            sb2.append(str2);
            sb2.append(networkMode);
            logi(sb2.toString());
            return networkMode;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(subId[0]);
        int networkMode2 = Global.getInt(contentResolver, sb3.toString(), networkMode);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(" get sub based N/W mode, val[");
        sb4.append(phoneId);
        sb4.append(str2);
        sb4.append(networkMode2);
        logi(sb4.toString());
        return networkMode2;
    }

    private void updateNewNwModeToDB() {
        for (int i = 0; i < mNumPhones; i++) {
            int nwModeFromDB = getNetworkModeFromDB(i);
            if (this.mPrefNwMode[i] != nwModeFromDB) {
                int[] subId = this.mQtiSubscriptionController.getSubId(i);
                StringBuilder sb = new StringBuilder();
                sb.append("updateNewNwModeToDB: subId[");
                sb.append(i);
                sb.append("] = ");
                sb.append(subId);
                sb.append(" new Nw mode = ");
                sb.append(this.mPrefNwMode[i]);
                sb.append(" old n/w mode = ");
                sb.append(nwModeFromDB);
                logi(sb.toString());
                String str = "preferred_network_mode";
                if (this.mSubscriptionManager.isActiveSubscriptionId(subId[0])) {
                    ContentResolver contentResolver = this.mContext.getContentResolver();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(subId[0]);
                    Global.putInt(contentResolver, sb2.toString(), this.mPrefNwMode[i]);
                }
                QtiPhoneUtils.putIntAtIndex(this.mContext.getContentResolver(), str, i, this.mPrefNwMode[i]);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00af, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setPreferredNetworkType(int r6, int r7, android.os.Message r8) {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.isSetNWModeInProgress()     // Catch:{ all -> 0x00d2 }
            if (r0 != 0) goto L_0x00b0
            boolean r0 = r5.isUiccProvisionInProgress()     // Catch:{ all -> 0x00d2 }
            if (r0 == 0) goto L_0x000f
            goto L_0x00b0
        L_0x000f:
            com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = r5.mQtiSubscriptionController     // Catch:{ all -> 0x00d2 }
            int[] r0 = r0.getSubId(r6)     // Catch:{ all -> 0x00d2 }
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L_0x0025
            int r3 = r0.length     // Catch:{ all -> 0x00d2 }
            if (r3 <= 0) goto L_0x0025
            android.telephony.SubscriptionManager r3 = r5.mSubscriptionManager     // Catch:{ all -> 0x00d2 }
            r4 = r0[r2]     // Catch:{ all -> 0x00d2 }
            boolean r3 = r3.isActiveSubscriptionId(r4)     // Catch:{ all -> 0x00d2 }
            r1 = r3
        L_0x0025:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r3.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.String r4 = "setPreferredNetworkType: nwMode["
            r3.append(r4)     // Catch:{ all -> 0x00d2 }
            r3.append(r6)     // Catch:{ all -> 0x00d2 }
            java.lang.String r4 = "] = "
            r3.append(r4)     // Catch:{ all -> 0x00d2 }
            r3.append(r7)     // Catch:{ all -> 0x00d2 }
            java.lang.String r4 = " isActive = "
            r3.append(r4)     // Catch:{ all -> 0x00d2 }
            r3.append(r1)     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00d2 }
            r5.logd(r3)     // Catch:{ all -> 0x00d2 }
            r3 = 1
            r5.setNWModeInProgressFlag(r3)     // Catch:{ all -> 0x00d2 }
            r5.syncPreferredNwModeFromDB()     // Catch:{ all -> 0x00d2 }
            int[] r4 = r5.mPrefNwMode     // Catch:{ all -> 0x00d2 }
            r4[r6] = r7     // Catch:{ all -> 0x00d2 }
            boolean r4 = r5.isFlexMappingAllowedOnInactiveSub()     // Catch:{ all -> 0x00d2 }
            if (r4 != 0) goto L_0x005c
            if (r1 == 0) goto L_0x0088
        L_0x005c:
            boolean r3 = r5.updateStackBindingIfRequired(r3)     // Catch:{ all -> 0x00d2 }
            if (r3 == 0) goto L_0x0088
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r2.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = "setPreferredNetworkType: store msg, nwMode["
            r2.append(r3)     // Catch:{ all -> 0x00d2 }
            r2.append(r6)     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = "] = "
            r2.append(r3)     // Catch:{ all -> 0x00d2 }
            r2.append(r7)     // Catch:{ all -> 0x00d2 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00d2 }
            r5.logv(r2)     // Catch:{ all -> 0x00d2 }
            java.util.HashMap<java.lang.Integer, android.os.Message> r2 = r5.mStoredResponse     // Catch:{ all -> 0x00d2 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x00d2 }
            r2.put(r3, r8)     // Catch:{ all -> 0x00d2 }
            goto L_0x00ae
        L_0x0088:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r3.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.String r4 = "setPreferredNetworkType: sending nwMode["
            r3.append(r4)     // Catch:{ all -> 0x00d2 }
            r3.append(r6)     // Catch:{ all -> 0x00d2 }
            java.lang.String r4 = "] = "
            r3.append(r4)     // Catch:{ all -> 0x00d2 }
            r3.append(r7)     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00d2 }
            r5.logv(r3)     // Catch:{ all -> 0x00d2 }
            com.android.internal.telephony.CommandsInterface[] r3 = r5.mCi     // Catch:{ all -> 0x00d2 }
            r3 = r3[r6]     // Catch:{ all -> 0x00d2 }
            r3.setPreferredNetworkType(r7, r8)     // Catch:{ all -> 0x00d2 }
            r5.setNWModeInProgressFlag(r2)     // Catch:{ all -> 0x00d2 }
        L_0x00ae:
            monitor-exit(r5)
            return
        L_0x00b0:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d2 }
            r0.<init>()     // Catch:{ all -> 0x00d2 }
            java.lang.String r1 = "setPreferredNetworkType: In Progress, nwmode["
            r0.append(r1)     // Catch:{ all -> 0x00d2 }
            r0.append(r6)     // Catch:{ all -> 0x00d2 }
            java.lang.String r1 = "] = "
            r0.append(r1)     // Catch:{ all -> 0x00d2 }
            r0.append(r7)     // Catch:{ all -> 0x00d2 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00d2 }
            r5.loge(r0)     // Catch:{ all -> 0x00d2 }
            r0 = 2
            r5.sendResponseToTarget(r8, r0)     // Catch:{ all -> 0x00d2 }
            monitor-exit(r5)
            return
        L_0x00d2:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiRadioCapabilityController.setPreferredNetworkType(int, int, android.os.Message):void");
    }

    private int getNumOfRafSupportedForNwMode(int nwMode, int radioAccessFamily) {
        if (radioAccessFamily == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(" Modem Capabilites are null. Return!!, N/W mode ");
            sb.append(nwMode);
            loge(sb.toString());
            return 0;
        }
        int nwModeRaf = RadioAccessFamily.getRafFromNetworkType(nwMode);
        int supportedRafMaskForNwMode = radioAccessFamily & nwModeRaf;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("getNumOfRATsSupportedForNwMode: nwMode[");
        sb2.append(nwMode);
        sb2.append(" nwModeRaf = ");
        sb2.append(nwModeRaf);
        sb2.append("] raf = ");
        sb2.append(radioAccessFamily);
        sb2.append(" supportedRafMaskForNwMode:");
        sb2.append(supportedRafMaskForNwMode);
        logv(sb2.toString());
        return Integer.bitCount(supportedRafMaskForNwMode);
    }

    private void sendSubscriptionSettings(int phoneId) {
        Phone phone = this.mPhone[phoneId];
        this.mCi[phoneId].setPreferredNetworkType(getNetworkModeFromDB(phoneId), null);
        int[] subId = this.mQtiSubscriptionController.getSubId(phoneId);
        if (subId != null && subId.length > 0 && this.mSubscriptionManager.isActiveSubscriptionId(subId[0])) {
            DataEnabledSettings dataEnabledSettings = phone.getDataEnabledSettings();
            dataEnabledSettings.setUserDataEnabled(dataEnabledSettings.isUserDataEnabled());
        }
    }

    private void notifyRadioCapsUpdated(boolean isCrossMapDone) {
        StringBuilder sb = new StringBuilder();
        sb.append("notifyRadioCapsUpdated: radio caps updated ");
        sb.append(isCrossMapDone);
        logd(sb.toString());
        if (isCrossMapDone) {
            for (int i = 0; i < mNumPhones; i++) {
                this.mCurrentStackId[i] = this.mPreferredStackId[i];
            }
        }
        this.mContext.sendStickyBroadcastAsUser(new Intent("org.codeaurora.intent.action.ACTION_RADIO_CAPABILITY_UPDATED"), UserHandle.ALL);
    }

    private void sendResponseToTarget(Message response, int responseCode) {
        if (response != null) {
            AsyncResult.forMessage(response, null, CommandException.fromRilErrno(responseCode));
            response.sendToTarget();
        }
    }

    private boolean isAnyCallsInProgress() {
        for (int i = 0; i < mNumPhones; i++) {
            if (this.mPhone[i].getState() != State.IDLE) {
                return DBG;
            }
        }
        return VDBG;
    }

    private boolean isAnyPhoneInEcmState() {
        for (int i = 0; i < mNumPhones; i++) {
            if (this.mPhone[i].isInEcm()) {
                return DBG;
            }
        }
        return VDBG;
    }

    private boolean isUiccProvisionInProgress() {
        QtiUiccCardProvisioner uiccProvisioner = QtiUiccCardProvisioner.getInstance();
        if (uiccProvisioner == null) {
            return VDBG;
        }
        boolean retVal = uiccProvisioner.isAnyProvisionRequestInProgress();
        StringBuilder sb = new StringBuilder();
        sb.append("isUiccProvisionInProgress: retVal =  ");
        sb.append(retVal);
        logd(sb.toString());
        return retVal;
    }

    private boolean isCardAbsent(int phoneId) {
        int provisionStatus = -1;
        QtiUiccCardProvisioner uiccProvisioner = QtiUiccCardProvisioner.getInstance();
        if (uiccProvisioner != null) {
            provisionStatus = uiccProvisioner.getCurrentUiccCardProvisioningStatus(phoneId);
            StringBuilder sb = new StringBuilder();
            sb.append("provisionStatus[");
            sb.append(phoneId);
            sb.append("] : ");
            sb.append(provisionStatus);
            logd(sb.toString());
        }
        return provisionStatus == -2 ? DBG : VDBG;
    }

    private void setNWModeInProgressFlag(boolean newStatus) {
        synchronized (sSetNwModeLock) {
            this.mIsSetPrefNwModeInProgress = newStatus;
        }
    }

    public boolean isSetNWModeInProgress() {
        boolean retVal;
        synchronized (sSetNwModeLock) {
            retVal = this.mIsSetPrefNwModeInProgress;
        }
        return retVal;
    }

    public void radioCapabilityUpdated(int phoneId, RadioCapability rc) {
        if (!QtiPhoneUtils.getInstance().isValidPhoneId(phoneId) || isSetNWModeInProgress()) {
            StringBuilder sb = new StringBuilder();
            sb.append("radioCapabilityUpdated: Invalid phoneId=");
            sb.append(phoneId);
            sb.append(" or SetNWModeInProgress");
            loge(sb.toString());
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" radioCapabilityUpdated phoneId[");
        sb2.append(phoneId);
        sb2.append("] rc = ");
        sb2.append(rc);
        logd(sb2.toString());
        if (areAllModemCapInfoReceived()) {
            sendMessage(obtainMessage(2));
        }
    }

    public void setDdsIfRequired(boolean forceSetDds) {
        int ddsSubId = this.mQtiSubscriptionController.getDefaultDataSubId();
        int ddsPhoneId = this.mQtiSubscriptionController.getPhoneId(ddsSubId);
        StringBuilder sb = new StringBuilder();
        sb.append("setDdsIfRequired: ddsSub = ");
        sb.append(ddsSubId);
        sb.append(" ddsPhone = ");
        sb.append(ddsPhoneId);
        sb.append(" force = ");
        sb.append(forceSetDds);
        sb.append(" needSetDds = ");
        sb.append(this.mNeedSetDds);
        logd(sb.toString());
        if (!QtiPhoneUtils.getInstance().isValidPhoneId(ddsPhoneId)) {
            return;
        }
        if (forceSetDds || this.mNeedSetDds) {
            this.mCi[ddsPhoneId].setDataAllowed(DBG, null);
            if (this.mNeedSetDds) {
                this.mNeedSetDds = VDBG;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean is7Plus7DeviceFlexMapped() {
        if (mNumPhones <= 1 || !areAllModemCapInfoReceived() || Integer.valueOf(this.mPhone[0].getModemUuId()).intValue() != 1 || this.mPhone[0].getRadioAccessFamily() != this.mPhone[1].getRadioAccessFamily() || this.mPhone[0].getRadioAccessFamily() == 0) {
            return VDBG;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Device is flex mapped: ");
        sb.append(this.mPhone[0].getModemUuId());
        sb.append(" raf = ");
        sb.append(this.mPhone[0].getRadioAccessFamily());
        logi(sb.toString());
        return DBG;
    }

    /* access modifiers changed from: 0000 */
    public boolean isBothPhonesMappedToSameStack() {
        if (mNumPhones <= 1 || !areAllModemCapInfoReceived() || Integer.valueOf(this.mPhone[0].getModemUuId()) != Integer.valueOf(this.mPhone[1].getModemUuId())) {
            return VDBG;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Error: both Phones mapped same stackId: ");
        sb.append(this.mPhone[0].getModemUuId());
        sb.append(" raf = ");
        sb.append(this.mPhone[0].getRadioAccessFamily());
        loge(sb.toString());
        this.bothPhonesMappedToSameStack = DBG;
        return DBG;
    }

    /* access modifiers changed from: 0000 */
    public boolean initNormalMappingRequest() {
        int i;
        RadioCapability[] oldRadioCapability = new RadioCapability[mNumPhones];
        int maxRaf = RadioAccessFamily.getRafFromNetworkType(22);
        int minRaf = RadioAccessFamily.getRafFromNetworkType(1);
        logd(" initNormalMappingRequest  ");
        setNWModeInProgressFlag(DBG);
        int i2 = 0;
        while (true) {
            i = mNumPhones;
            if (i2 >= i) {
                break;
            }
            oldRadioCapability[i2] = this.mPhone[i2].getRadioCapability();
            RadioCapability rc = new RadioCapability(i2, 0, 0, i2 == 0 ? minRaf : maxRaf, i2 == 0 ? "1" : "0", 1);
            this.mPhone[i2].radioCapabilityUpdated(rc);
            i2++;
        }
        RadioAccessFamily[] rafs = new RadioAccessFamily[i];
        int i3 = 0;
        while (i3 < mNumPhones) {
            rafs[i3] = new RadioAccessFamily(i3, i3 == 0 ? maxRaf : minRaf);
            i3++;
        }
        if (ProxyController.getInstance().setRadioCapability(rafs)) {
            return DBG;
        }
        for (int i4 = 0; i4 < mNumPhones; i4++) {
            this.mPhone[i4].radioCapabilityUpdated(oldRadioCapability[i4]);
        }
        logd(" initNormalMappingRequest:  Fail, request in progress ");
        setNWModeInProgressFlag(VDBG);
        return VDBG;
    }

    private void logd(String string) {
        Rlog.d(LOG_TAG, string);
    }

    private void logi(String string) {
        Rlog.i(LOG_TAG, string);
    }

    private void loge(String string) {
        Rlog.e(LOG_TAG, string);
    }

    private void logv(String string) {
    }
}
