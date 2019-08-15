package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiItemType;
import com.qualcomm.qcrilhook.BaseQmiTypes.QmiBase;
import com.qualcomm.qcrilhook.QmiOemHookConstants.ResponseType;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiNull;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class QmiOemHook extends Handler {
    private static final int DEFAULT_PHONE = 0;
    /* access modifiers changed from: private */
    public static String LOG_TAG = "QMI_OEMHOOK";
    private static final int QMI_OEM_HOOK_UNSOL = 0;
    private static final int RESERVED_SIZE = 8;
    private static final boolean enableVLog = true;
    private static QmiOemHook mInstance;
    /* access modifiers changed from: private */
    public static boolean mIsServiceConnected = false;
    private static int mRefCount = 0;
    /* access modifiers changed from: private */
    public static RegistrantList sReadyCbRegistrantList = new RegistrantList();
    public static HashMap<Short, Registrant> serviceRegistrantsMap = new HashMap<>();
    private Context mContext;
    private QcRilHook mQcRilOemHook;
    private QcRilHookCallback mQcrilHookCb = new QcRilHookCallback() {
        public void onQcRilHookReady() {
            QmiOemHook.mIsServiceConnected = QmiOemHook.enableVLog;
            AsyncResult ar = new AsyncResult(null, Boolean.valueOf(QmiOemHook.mIsServiceConnected), null);
            Log.i(QmiOemHook.LOG_TAG, "onQcRilHookReadyCb notifying registrants");
            QmiOemHook.sReadyCbRegistrantList.notifyRegistrants(ar);
        }

        public synchronized void onQcRilHookDisconnected() {
            QmiOemHook.mIsServiceConnected = false;
            AsyncResult ar = new AsyncResult(null, Boolean.valueOf(QmiOemHook.mIsServiceConnected), null);
            Log.i(QmiOemHook.LOG_TAG, "onQcRilHookReadyCb: service disconnected; notifying registrants.");
            QmiOemHook.sReadyCbRegistrantList.notifyRegistrants(ar);
        }
    };
    int mResponseResult = 0;
    public ByteBuffer respByteBuf;

    private QmiOemHook(Context context) {
        this.mQcRilOemHook = new QcRilHook(context, this.mQcrilHookCb);
        QcRilHook.register(this, 0, null);
    }

    private QmiOemHook(Context context, Looper looper) {
        super(looper);
        this.mQcRilOemHook = new QcRilHook(context, this.mQcrilHookCb);
        QcRilHook.register(this, 0, null);
    }

    public static synchronized QmiOemHook getInstance(Context context) {
        QmiOemHook qmiOemHook;
        synchronized (QmiOemHook.class) {
            if (mInstance == null) {
                mInstance = new QmiOemHook(context);
            }
            mRefCount++;
            qmiOemHook = mInstance;
        }
        return qmiOemHook;
    }

    public static synchronized QmiOemHook getInstance(Context context, Looper looper) {
        QmiOemHook qmiOemHook;
        synchronized (QmiOemHook.class) {
            if (mInstance == null) {
                mInstance = new QmiOemHook(context, looper);
            }
            mRefCount++;
            qmiOemHook = mInstance;
        }
        return qmiOemHook;
    }

    public synchronized void dispose() {
        mRefCount--;
        if (mRefCount == 0) {
            vLog("dispose(): Unregistering QcRilHook and calling QcRilHook dispose");
            QcRilHook.unregister(this);
            mIsServiceConnected = false;
            this.mQcRilOemHook.dispose();
            mInstance = null;
            sReadyCbRegistrantList.removeCleared();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("dispose mRefCount = ");
            sb.append(mRefCount);
            vLog(sb.toString());
        }
    }

    private void vLog(String logString) {
        Log.v(LOG_TAG, logString);
    }

    public static void registerService(short serviceId, Handler h, int what) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Registering Service Id = ");
        sb.append(serviceId);
        sb.append(" h = ");
        sb.append(h);
        sb.append(" what = ");
        sb.append(what);
        Log.v(str, sb.toString());
        synchronized (serviceRegistrantsMap) {
            serviceRegistrantsMap.put(Short.valueOf(serviceId), new Registrant(h, what, null));
        }
    }

    public static void registerOnReadyCb(Handler h, int what, Object obj) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Registering Service for OnQcRilHookReadyCb =  h = ");
        sb.append(h);
        sb.append(" what = ");
        sb.append(what);
        Log.v(str, sb.toString());
        synchronized (sReadyCbRegistrantList) {
            sReadyCbRegistrantList.add(new Registrant(h, what, obj));
        }
    }

    public static void unregisterService(int serviceId) {
        synchronized (serviceRegistrantsMap) {
            serviceRegistrantsMap.remove(Short.valueOf((short) serviceId));
        }
    }

    public static void unregisterOnReadyCb(Handler h) {
        synchronized (sReadyCbRegistrantList) {
            sReadyCbRegistrantList.remove(h);
        }
    }

    public void handleMessage(Message msg) {
        if (msg.what == 0) {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Thread=");
            sb.append(Thread.currentThread().getName());
            sb.append(" received ");
            sb.append(msg);
            Log.v(str, sb.toString());
            Log.v(LOG_TAG, "QMI_OEM_HOOK_UNSOL received");
            Message mesg = (Message) ((AsyncResult) msg.obj).result;
            byte[] response = (byte[]) mesg.obj;
            int phoneId = mesg.arg1;
            String str2 = LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("QMI_OEM_HOOK_UNSOL received phoneId: ");
            sb2.append(phoneId);
            Log.d(str2, sb2.toString());
            receive(response, null, ResponseType.IS_UNSOL, phoneId);
        }
    }

    public static HashMap<Integer, Object> receive(byte[] payload, Message msg, ResponseType responseType, int phoneId) {
        Message message = msg;
        ResponseType responseType2 = responseType;
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("receive responseData = ");
        sb.append(EmbmsOemHook.bytesToHexString(payload));
        sb.append(" message=");
        sb.append(message);
        sb.append(" responseType= ");
        sb.append(responseType2);
        Log.v(str, sb.toString());
        if (payload == null) {
            Log.v(LOG_TAG, "payload is null");
            return null;
        }
        ByteBuffer respByteBuf2 = ByteBuffer.wrap(payload);
        respByteBuf2.order(BaseQmiItemType.QMI_BYTE_ORDER);
        String str2 = LOG_TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("receive respByteBuf after ByteBuffer.wrap(payload) = ");
        sb2.append(EmbmsOemHook.bytesToHexString(respByteBuf2.array()));
        Log.v(str2, sb2.toString());
        String str3 = LOG_TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("receive respByteBuf = ");
        sb3.append(respByteBuf2);
        Log.v(str3, sb3.toString());
        int requestId = respByteBuf2.getInt();
        int responseSize = respByteBuf2.getInt();
        int successStatus = -1;
        if (!isValidQmiMessage(responseType2, requestId)) {
            Log.e(LOG_TAG, "requestId NOT in QMI OemHook range, No further processing");
            return null;
        }
        if (responseSize > 0) {
            short serviceId = respByteBuf2.getShort();
            short messageId = respByteBuf2.getShort();
            int responseTlvSize = responseSize - 4;
            if (responseType2 != ResponseType.IS_UNSOL) {
                successStatus = PrimitiveParser.toUnsigned(respByteBuf2.getShort());
                responseTlvSize -= 2;
            }
            String str4 = LOG_TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("receive requestId=");
            sb4.append(requestId);
            sb4.append(" responseSize=");
            sb4.append(responseSize);
            sb4.append(" responseTlvSize=");
            sb4.append(responseTlvSize);
            sb4.append(" serviceId=");
            sb4.append(serviceId);
            sb4.append(" messageId=");
            sb4.append(messageId);
            sb4.append(" successStatus = ");
            sb4.append(successStatus);
            sb4.append(" phoneId: ");
            sb4.append(phoneId);
            Log.d(str4, sb4.toString());
            HashMap<Integer, Object> hashMap = new HashMap<>();
            hashMap.put(Integer.valueOf(1), Integer.valueOf(requestId));
            hashMap.put(Integer.valueOf(2), Integer.valueOf(responseTlvSize));
            hashMap.put(Integer.valueOf(7), Short.valueOf(serviceId));
            hashMap.put(Integer.valueOf(8), Short.valueOf(messageId));
            hashMap.put(Integer.valueOf(3), Integer.valueOf(successStatus));
            hashMap.put(Integer.valueOf(4), message);
            hashMap.put(Integer.valueOf(5), responseType2);
            hashMap.put(Integer.valueOf(6), respByteBuf2);
            hashMap.put(Integer.valueOf(9), Integer.valueOf(phoneId));
            if (responseType2 != ResponseType.IS_UNSOL && responseType2 != ResponseType.IS_ASYNC_RESPONSE) {
                return hashMap;
            }
            AsyncResult ar = new AsyncResult(null, hashMap, null);
            Registrant r = (Registrant) serviceRegistrantsMap.get(Short.valueOf(serviceId));
            if (r != null) {
                String str5 = LOG_TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Notifying registrant for responseType = ");
                sb5.append(responseType2);
                Log.v(str5, sb5.toString());
                r.notifyRegistrant(ar);
                return null;
            }
            String str6 = LOG_TAG;
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Did not find the registered serviceId = ");
            sb6.append(serviceId);
            Log.e(str6, sb6.toString());
        } else {
            int i = phoneId;
        }
        return null;
    }

    private static boolean isValidQmiMessage(ResponseType responseType, int requestId) {
        boolean z = false;
        if (responseType == ResponseType.IS_UNSOL) {
            if (requestId == 525388) {
                z = true;
            }
            return z;
        }
        if (requestId == 524388) {
            z = true;
        }
        return z;
    }

    private byte[] createPayload(short serviceId, short messageId, short[] types, BaseQmiItemType[] qmiItems) {
        int i = 0;
        if (qmiItems == null || types == null || qmiItems[0] == null) {
            Log.v(LOG_TAG, "This message has no payload");
        } else {
            int tlvSize = 0;
            for (BaseQmiItemType size : qmiItems) {
                tlvSize += 3 + size.getSize();
            }
            i = tlvSize;
        }
        ByteBuffer buf = QmiBase.createByteBuffer(12 + i);
        buf.putInt(0);
        buf.putInt(0);
        buf.putShort(serviceId);
        buf.putShort(messageId);
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("createPayload: serviceId= ");
        sb.append(serviceId);
        sb.append(" messageId= ");
        sb.append(messageId);
        Log.v(str, sb.toString());
        if (!(qmiItems == null || types == null || qmiItems[0] == null)) {
            for (int i2 = 0; i2 < qmiItems.length; i2++) {
                vLog(qmiItems[i2].toString());
                buf.put(qmiItems[i2].toTlv(types[i2]));
                String str2 = LOG_TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Intermediate buf in QmiOemHook sendQmiMessage Sync or Async = ");
                sb2.append(EmbmsOemHook.bytesToHexString(qmiItems[i2].toTlv(types[i2])));
                Log.v(str2, sb2.toString());
            }
        }
        String str3 = LOG_TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Byte buf in QmiOemHook createPayload = ");
        sb3.append(buf);
        Log.v(str3, sb3.toString());
        return buf.array();
    }

    public byte[] sendQmiMessage(int serviceHook, short[] types, BaseQmiItemType[] qmiItems) throws IOException {
        int msgSize = 0;
        for (BaseQmiItemType size : qmiItems) {
            msgSize += 3 + size.getSize();
        }
        ByteBuffer buf = QmiBase.createByteBuffer(4 + msgSize);
        buf.putInt(0);
        buf.putShort(PrimitiveParser.parseShort(msgSize));
        for (int i = 0; i < qmiItems.length; i++) {
            vLog(qmiItems[i].toString());
            buf.put(qmiItems[i].toTlv(types[i]));
        }
        AsyncResult result = this.mQcRilOemHook.sendQcRilHookMsg(serviceHook, buf.array());
        if (result.exception == null) {
            return (byte[]) result.result;
        }
        Log.w(LOG_TAG, String.format("sendQmiMessage() Failed : %s", new Object[]{result.exception.toString()}));
        result.exception.printStackTrace();
        throw new IOException();
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short[] types, BaseQmiItemType[] qmiItems) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, types, qmiItems, 0);
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short[] types, BaseQmiItemType[] qmiItems, int phoneId) throws IOException {
        AsyncResult result = this.mQcRilOemHook.sendQcRilHookMsg((int) IQcRilHook.QCRILHOOK_QMI_OEMHOOK_REQUEST_ID, createPayload(serviceId, messageId, types, qmiItems), phoneId);
        if (result.exception == null) {
            return receive((byte[]) result.result, null, ResponseType.IS_SYNC_RESPONSE, phoneId);
        }
        Log.w(LOG_TAG, String.format("sendQmiMessage() Failed : %s", new Object[]{result.exception.toString()}));
        result.exception.printStackTrace();
        throw new IOException();
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short[] types, BaseQmiItemType[] qmiItems, Message msg) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, types, qmiItems, msg, 0);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short[] types, BaseQmiItemType[] qmiItems, Message msg, int phoneId) throws IOException {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("sendQmiMessageAsync phoneId: ");
        sb.append(phoneId);
        Log.w(str, sb.toString());
        this.mQcRilOemHook.sendQcRilHookMsgAsync(IQcRilHook.QCRILHOOK_QMI_OEMHOOK_REQUEST_ID, createPayload(serviceId, messageId, types, qmiItems), new OemHookCallback(msg), phoneId);
    }

    public byte[] sendQmiMessage(int serviceHook, short type, BaseQmiItemType qmiItem) throws IOException {
        return sendQmiMessage(serviceHook, new short[]{type}, new BaseQmiItemType[]{qmiItem});
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short type, BaseQmiItemType qmiItem) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, type, qmiItem, 0);
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short type, BaseQmiItemType qmiItem, int phoneId) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, new short[]{type}, new BaseQmiItemType[]{qmiItem}, phoneId);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short type, BaseQmiItemType qmiItem, Message msg) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, type, qmiItem, msg, 0);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short type, BaseQmiItemType qmiItem, Message msg, int phoneId) throws IOException {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("sendQmiMessageAsync phoneId: ");
        sb.append(phoneId);
        Log.w(str, sb.toString());
        sendQmiMessageAsync(serviceId, messageId, new short[]{type}, new BaseQmiItemType[]{qmiItem}, msg, phoneId);
    }

    public byte[] sendQmiMessage(int serviceHook) throws IOException {
        return sendQmiMessage(serviceHook, 0, (BaseQmiItemType) new QmiNull());
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, (short[]) null, (BaseQmiItemType[]) null);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, Message msg) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, msg, 0);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, Message msg, int phoneId) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, (short[]) null, (BaseQmiItemType[]) null, msg, phoneId);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        Log.v(LOG_TAG, "is destroyed");
    }
}
