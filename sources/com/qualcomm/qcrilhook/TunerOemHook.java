package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes;
import com.qualcomm.qcrilhook.QmiOemHookConstants;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
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
        TunerOemHook tunerOemHook = mInstance;
        if (tunerOemHook == null) {
            mInstance = new TunerOemHook(context, listenerHandler.getLooper());
        } else {
            tunerOemHook.mContext = context;
        }
        mRefCount++;
        return mInstance;
    }

    public synchronized void registerOnReadyCb(Handler h, int what, Object obj) {
        QmiOemHook.registerOnReadyCb(h, what, (Object) null);
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
            Log.v(LOG_TAG, "dispose mRefCount = " + mRefCount);
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
        int intValue = ((Integer) map.get(1)).intValue();
        int responseSize = ((Integer) map.get(2)).intValue();
        int successStatus = ((Integer) map.get(3)).intValue();
        short messageId = ((Short) map.get(8)).shortValue();
        QmiOemHookConstants.ResponseType responseType = (QmiOemHookConstants.ResponseType) map.get(5);
        Message message = (Message) map.get(4);
        ByteBuffer respByteBuf = (ByteBuffer) map.get(6);
        String str = LOG_TAG;
        Log.v(str, "receive respByteBuf = " + respByteBuf);
        String str2 = LOG_TAG;
        Log.v(str2, " responseSize=" + responseSize + " successStatus=" + successStatus + " messageId= " + messageId);
        Object returnObject = Integer.valueOf(successStatus);
        switch (messageId) {
            case 32:
                String str3 = LOG_TAG;
                Log.v(str3, "Response: QCRILHOOK_TUNER_RFRPE_SET_RFM_SCENARIO_REQ=" + successStatus);
                return returnObject;
            case 33:
                String str4 = LOG_TAG;
                Log.v(str4, "Response: QCRILHOOK_TUNER_RFRPE_GET_RFM_SCENARIO_REQ=" + successStatus);
                return returnObject;
            case 34:
                String str5 = LOG_TAG;
                Log.v(str5, "Response: QCRILHOOK_TUNER_RFRPE_GET_PROVISIONED_TABLE_REVISION_REQ=" + successStatus);
                return Integer.valueOf(new ProvisionTable(respByteBuf).prv_tbl_rev);
            default:
                Log.v(LOG_TAG, "Invalid request");
                return returnObject;
        }
    }

    public static class ProvisionTable {
        public int[] prv_tbl_oem = null;
        public int prv_tbl_rev = -1;

        public ProvisionTable(ByteBuffer buf) {
            String access$000 = TunerOemHook.LOG_TAG;
            Log.d(access$000, "ProvsionTableInfo: " + buf.toString());
            while (buf.hasRemaining() && buf.remaining() >= 3) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int length = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 16) {
                    byte[] data = new byte[length];
                    for (int i = 0; i < length; i++) {
                        data[i] = buf.get();
                    }
                    ByteBuffer wrapped = ByteBuffer.wrap(data);
                    wrapped.order(ByteOrder.LITTLE_ENDIAN);
                    this.prv_tbl_rev = wrapped.getInt();
                    String access$0002 = TunerOemHook.LOG_TAG;
                    Log.i(access$0002, "Provision Table Rev = " + this.prv_tbl_rev);
                } else if (type != 17) {
                    Log.i(TunerOemHook.LOG_TAG, "Invalid TLV type");
                } else {
                    int prv_tbl_oem_len = buf.get();
                    this.prv_tbl_oem = new int[prv_tbl_oem_len];
                    for (int i2 = 0; i2 < prv_tbl_oem_len; i2++) {
                        this.prv_tbl_oem[i2] = buf.getShort();
                    }
                    String access$0003 = TunerOemHook.LOG_TAG;
                    Log.i(access$0003, "Provsions Table OEM = " + Arrays.toString(this.prv_tbl_oem));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> intArrayToQmiArray(int[] arr) {
        QmiPrimitiveTypes.QmiInteger[] qmiIntArray = new QmiPrimitiveTypes.QmiInteger[arr.length];
        for (int i = 0; i < arr.length; i++) {
            qmiIntArray[i] = new QmiPrimitiveTypes.QmiInteger((long) arr[i]);
        }
        return new QmiPrimitiveTypes.QmiArray<>((T[]) qmiIntArray, (short) arr.length, QmiPrimitiveTypes.QmiInteger.class);
    }

    public class ScenarioRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> list;

        public ScenarioRequest(int[] list2) {
            this.list = TunerOemHook.this.intArrayToQmiArray(list2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.list};
        }

        public short[] getTypes() {
            return new short[]{1};
        }
    }
}
