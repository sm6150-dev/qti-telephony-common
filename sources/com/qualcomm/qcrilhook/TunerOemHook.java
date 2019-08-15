package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiItemType;
import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiStructType;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiArray;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiInteger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class TunerOemHook {
    /* access modifiers changed from: private */
    public static String LOG_TAG = "TunerOemHook";
    public static final short QCRILHOOK_TUNER_RFRPE_GET_PROVISIONED_TABLE_REVISION_REQ = 34;
    public static final short QCRILHOOK_TUNER_RFRPE_GET_RFM_SCENARIO_REQ = 33;
    public static final short QCRILHOOK_TUNER_RFRPE_SET_RFM_SCENARIO_REQ = 32;
    private static final byte TLV_TYPE_COMMON_REQ_SCENARIO_ID = 1;
    private static final byte TLV_TYPE_GET_PROVISION_TABLE_OPTIONAL_TAG1 = 16;
    private static final byte TLV_TYPE_GET_PROVISION_TABLE_OPTIONAL_TAG2 = 17;
    private static final short TUNER_SERVICE_ID = 4;
    private static TunerOemHook mInstance;
    private static int mRefCount = 0;
    Context mContext;
    private QmiOemHook mQmiOemHook;

    public static class ProvisionTable {
        public int[] prv_tbl_oem = null;
        public int prv_tbl_rev = -1;

        /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r3v1, types: [byte, int] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public ProvisionTable(java.nio.ByteBuffer r8) {
            /*
                r7 = this;
                r7.<init>()
                r0 = 0
                r7.prv_tbl_oem = r0
                r0 = -1
                r7.prv_tbl_rev = r0
                java.lang.String r0 = com.qualcomm.qcrilhook.TunerOemHook.LOG_TAG
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "ProvsionTableInfo: "
                r1.append(r2)
                java.lang.String r2 = r8.toString()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                android.util.Log.d(r0, r1)
            L_0x0025:
                boolean r0 = r8.hasRemaining()
                if (r0 == 0) goto L_0x00bf
                int r0 = r8.remaining()
                r1 = 3
                if (r0 < r1) goto L_0x00bf
                byte r0 = r8.get()
                short r0 = com.qualcomm.qcrilhook.PrimitiveParser.toUnsigned(r0)
                short r1 = r8.getShort()
                int r1 = com.qualcomm.qcrilhook.PrimitiveParser.toUnsigned(r1)
                r2 = 0
                switch(r0) {
                    case 16: goto L_0x0085;
                    case 17: goto L_0x0050;
                    default: goto L_0x0046;
                }
            L_0x0046:
                java.lang.String r2 = com.qualcomm.qcrilhook.TunerOemHook.LOG_TAG
                java.lang.String r3 = "Invalid TLV type"
                android.util.Log.i(r2, r3)
                goto L_0x00bd
            L_0x0050:
                byte r3 = r8.get()
                int[] r4 = new int[r3]
                r7.prv_tbl_oem = r4
            L_0x0059:
                if (r2 >= r3) goto L_0x0066
                int[] r4 = r7.prv_tbl_oem
                short r5 = r8.getShort()
                r4[r2] = r5
                int r2 = r2 + 1
                goto L_0x0059
            L_0x0066:
                java.lang.String r2 = com.qualcomm.qcrilhook.TunerOemHook.LOG_TAG
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "Provsions Table OEM = "
                r4.append(r5)
                int[] r5 = r7.prv_tbl_oem
                java.lang.String r5 = java.util.Arrays.toString(r5)
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                android.util.Log.i(r2, r4)
                goto L_0x00bd
            L_0x0085:
                byte[] r3 = new byte[r1]
            L_0x0088:
                if (r2 >= r1) goto L_0x0093
                byte r4 = r8.get()
                r3[r2] = r4
                int r2 = r2 + 1
                goto L_0x0088
            L_0x0093:
                java.nio.ByteBuffer r2 = java.nio.ByteBuffer.wrap(r3)
                java.nio.ByteOrder r4 = java.nio.ByteOrder.LITTLE_ENDIAN
                r2.order(r4)
                int r4 = r2.getInt()
                r7.prv_tbl_rev = r4
                java.lang.String r4 = com.qualcomm.qcrilhook.TunerOemHook.LOG_TAG
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Provision Table Rev = "
                r5.append(r6)
                int r6 = r7.prv_tbl_rev
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                android.util.Log.i(r4, r5)
            L_0x00bd:
                goto L_0x0025
            L_0x00bf:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qcrilhook.TunerOemHook.ProvisionTable.<init>(java.nio.ByteBuffer):void");
        }
    }

    public class ScenarioRequest extends BaseQmiStructType {
        public QmiArray<QmiInteger> list;

        public ScenarioRequest(int[] list2) {
            this.list = TunerOemHook.this.intArrayToQmiArray(list2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.list};
        }

        public short[] getTypes() {
            return new short[]{1};
        }
    }

    public static class TunerSolResponse {
        public Object data;
        public int result;
    }

    public static class TunerUnsolIndication {
        public Object obj;
        public int oemHookMesgId;
    }

    private TunerOemHook(Context context, Looper listenerLooper) {
        this.mContext = context;
        this.mQmiOemHook = QmiOemHook.getInstance(context, listenerLooper);
    }

    public static TunerOemHook getInstance(Context context, Handler listenerHandler) {
        if (mInstance == null) {
            mInstance = new TunerOemHook(context, listenerHandler.getLooper());
        } else {
            mInstance.mContext = context;
        }
        mRefCount++;
        return mInstance;
    }

    public synchronized void registerOnReadyCb(Handler h, int what, Object obj) {
        QmiOemHook.registerOnReadyCb(h, what, null);
    }

    public synchronized void unregisterOnReadyCb(Handler h) {
        QmiOemHook.unregisterOnReadyCb(h);
    }

    public synchronized void dispose() {
        mRefCount--;
        if (mRefCount == 0) {
            Log.v(LOG_TAG, "dispose(): Unregistering service");
            this.mQmiOemHook.dispose();
            this.mQmiOemHook = null;
            mInstance = null;
        } else {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("dispose mRefCount = ");
            sb.append(mRefCount);
            Log.v(str, sb.toString());
        }
    }

    public Integer tuner_send_proximity_updates(int[] proximityValues) {
        ScenarioRequest req = new ScenarioRequest(proximityValues);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) TUNER_SERVICE_ID, 32, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int tuner_get_provisioned_table_revision() {
        try {
            return ((Integer) receive(this.mQmiOemHook.sendQmiMessageSync(TUNER_SERVICE_ID, 34))).intValue();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Object receive(HashMap<Integer, Object> map) {
        if (map == null) {
            Log.v(LOG_TAG, "received null result");
            return new Integer(-1);
        }
        int intValue = ((Integer) map.get(Integer.valueOf(1))).intValue();
        int responseSize = ((Integer) map.get(Integer.valueOf(2))).intValue();
        int successStatus = ((Integer) map.get(Integer.valueOf(3))).intValue();
        short messageId = ((Short) map.get(Integer.valueOf(8))).shortValue();
        map.get(Integer.valueOf(5));
        map.get(Integer.valueOf(4));
        ByteBuffer respByteBuf = (ByteBuffer) map.get(Integer.valueOf(6));
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("receive respByteBuf = ");
        sb.append(respByteBuf);
        Log.v(str, sb.toString());
        String str2 = LOG_TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(" responseSize=");
        sb2.append(responseSize);
        sb2.append(" successStatus=");
        sb2.append(successStatus);
        sb2.append(" messageId= ");
        sb2.append(messageId);
        Log.v(str2, sb2.toString());
        Object returnObject = Integer.valueOf(successStatus);
        switch (messageId) {
            case 32:
                String str3 = LOG_TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Response: QCRILHOOK_TUNER_RFRPE_SET_RFM_SCENARIO_REQ=");
                sb3.append(successStatus);
                Log.v(str3, sb3.toString());
                break;
            case 33:
                String str4 = LOG_TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Response: QCRILHOOK_TUNER_RFRPE_GET_RFM_SCENARIO_REQ=");
                sb4.append(successStatus);
                Log.v(str4, sb4.toString());
                break;
            case 34:
                String str5 = LOG_TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Response: QCRILHOOK_TUNER_RFRPE_GET_PROVISIONED_TABLE_REVISION_REQ=");
                sb5.append(successStatus);
                Log.v(str5, sb5.toString());
                returnObject = Integer.valueOf(new ProvisionTable(respByteBuf).prv_tbl_rev);
                break;
            default:
                Log.v(LOG_TAG, "Invalid request");
                break;
        }
        return returnObject;
    }

    /* access modifiers changed from: private */
    public QmiArray<QmiInteger> intArrayToQmiArray(int[] arr) {
        QmiInteger[] qmiIntArray = new QmiInteger[arr.length];
        for (int i = 0; i < arr.length; i++) {
            qmiIntArray[i] = new QmiInteger((long) arr[i]);
        }
        return new QmiArray<>((T[]) qmiIntArray, (short) arr.length, QmiInteger.class);
    }
}
