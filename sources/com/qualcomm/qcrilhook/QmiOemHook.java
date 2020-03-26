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
import java.util.HashMap;
import org.codeaurora.telephony.utils.AsyncResult;
import org.codeaurora.telephony.utils.Registrant;
import org.codeaurora.telephony.utils.RegistrantList;

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
            boolean unused = QmiOemHook.mIsServiceConnected = QmiOemHook.enableVLog;
            AsyncResult ar = new AsyncResult((Object) null, Boolean.valueOf(QmiOemHook.mIsServiceConnected), (Throwable) null);
            Log.i(QmiOemHook.LOG_TAG, "onQcRilHookReadyCb notifying registrants");
            QmiOemHook.sReadyCbRegistrantList.notifyRegistrants(ar);
        }

        public synchronized void onQcRilHookDisconnected() {
            boolean unused = QmiOemHook.mIsServiceConnected = false;
            AsyncResult ar = new AsyncResult((Object) null, Boolean.valueOf(QmiOemHook.mIsServiceConnected), (Throwable) null);
            Log.i(QmiOemHook.LOG_TAG, "onQcRilHookReadyCb: service disconnected; notifying registrants.");
            QmiOemHook.sReadyCbRegistrantList.notifyRegistrants(ar);
        }
    };
    int mResponseResult = 0;
    public ByteBuffer respByteBuf;

    private QmiOemHook(Context context) {
        this.mQcRilOemHook = new QcRilHook(context, this.mQcrilHookCb);
        QcRilHook.register(this, 0, (Object) null);
    }

    private QmiOemHook(Context context, Looper looper) {
        super(looper);
        this.mQcRilOemHook = new QcRilHook(context, this.mQcrilHookCb);
        QcRilHook.register(this, 0, (Object) null);
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
            vLog("dispose mRefCount = " + mRefCount);
        }
    }

    private void vLog(String logString) {
        Log.v(LOG_TAG, logString);
    }

    public static void registerService(short serviceId, Handler h, int what) {
        String str = LOG_TAG;
        Log.v(str, "Registering Service Id = " + serviceId + " h = " + h + " what = " + what);
        synchronized (serviceRegistrantsMap) {
            serviceRegistrantsMap.put(Short.valueOf(serviceId), new Registrant(h, what, (Object) null));
        }
    }

    public static void registerOnReadyCb(Handler h, int what, Object obj) {
        String str = LOG_TAG;
        Log.v(str, "Registering Service for OnQcRilHookReadyCb =  h = " + h + " what = " + what);
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
            Log.v(str, "Thread=" + Thread.currentThread().getName() + " received " + msg);
            Log.v(LOG_TAG, "QMI_OEM_HOOK_UNSOL received");
            Message mesg = (Message) ((AsyncResult) msg.obj).result;
            int phoneId = mesg.arg1;
            String str2 = LOG_TAG;
            Log.d(str2, "QMI_OEM_HOOK_UNSOL received phoneId: " + phoneId);
            receive((byte[]) mesg.obj, (Message) null, QmiOemHookConstants.ResponseType.IS_UNSOL, phoneId);
        }
    }

    public static HashMap<Integer, Object> receive(byte[] payload, Message msg, QmiOemHookConstants.ResponseType responseType, int phoneId) {
        Message message = msg;
        QmiOemHookConstants.ResponseType responseType2 = responseType;
        Log.v(LOG_TAG, "receive responseData = " + EmbmsOemHook.bytesToHexString(payload) + " message=" + message + " responseType= " + responseType2);
        if (payload == null) {
            Log.v(LOG_TAG, "payload is null");
            return null;
        }
        ByteBuffer respByteBuf2 = ByteBuffer.wrap(payload);
        respByteBuf2.order(BaseQmiTypes.BaseQmiItemType.QMI_BYTE_ORDER);
        Log.v(LOG_TAG, "receive respByteBuf after ByteBuffer.wrap(payload) = " + EmbmsOemHook.bytesToHexString(respByteBuf2.array()));
        Log.v(LOG_TAG, "receive respByteBuf = " + respByteBuf2);
        int requestId = respByteBuf2.getInt();
        int responseSize = respByteBuf2.getInt();
        int successStatus = -1;
        if (!isValidQmiMessage(responseType2, requestId)) {
            Log.e(LOG_TAG, "requestId NOT in QMI OemHook range, No further processing");
            return null;
        } else if (responseSize > 0) {
            short serviceId = respByteBuf2.getShort();
            short messageId = respByteBuf2.getShort();
            int responseTlvSize = responseSize - 4;
            if (responseType2 != QmiOemHookConstants.ResponseType.IS_UNSOL) {
                successStatus = PrimitiveParser.toUnsigned(respByteBuf2.getShort());
                responseTlvSize -= 2;
            }
            Log.d(LOG_TAG, "receive requestId=" + requestId + " responseSize=" + responseSize + " responseTlvSize=" + responseTlvSize + " serviceId=" + serviceId + " messageId=" + messageId + " successStatus = " + successStatus + " phoneId: " + phoneId);
            HashMap<Integer, Object> hashMap = new HashMap<>();
            hashMap.put(1, Integer.valueOf(requestId));
            hashMap.put(2, Integer.valueOf(responseTlvSize));
            hashMap.put(7, Short.valueOf(serviceId));
            hashMap.put(8, Short.valueOf(messageId));
            hashMap.put(3, Integer.valueOf(successStatus));
            hashMap.put(4, message);
            hashMap.put(5, responseType2);
            hashMap.put(6, respByteBuf2);
            hashMap.put(9, Integer.valueOf(phoneId));
            if (responseType2 != QmiOemHookConstants.ResponseType.IS_UNSOL && responseType2 != QmiOemHookConstants.ResponseType.IS_ASYNC_RESPONSE) {
                return hashMap;
            }
            AsyncResult ar = new AsyncResult((Object) null, hashMap, (Throwable) null);
            Registrant r = serviceRegistrantsMap.get(Short.valueOf(serviceId));
            if (r != null) {
                Log.v(LOG_TAG, "Notifying registrant for responseType = " + responseType2);
                r.notifyRegistrant(ar);
                return null;
            }
            Log.e(LOG_TAG, "Did not find the registered serviceId = " + serviceId);
            return null;
        } else {
            int i = phoneId;
            return null;
        }
    }

    private static boolean isValidQmiMessage(QmiOemHookConstants.ResponseType responseType, int requestId) {
        if (responseType == QmiOemHookConstants.ResponseType.IS_UNSOL) {
            if (requestId == 525388) {
                return enableVLog;
            }
            return false;
        } else if (requestId == 524388) {
            return enableVLog;
        } else {
            return false;
        }
    }

    private byte[] createPayload(short serviceId, short messageId, short[] types, BaseQmiTypes.BaseQmiItemType[] qmiItems) {
        int tlvSize = 0;
        if (qmiItems == null || types == null || qmiItems[0] == null) {
            Log.v(LOG_TAG, "This message has no payload");
        } else {
            for (BaseQmiTypes.BaseQmiItemType size : qmiItems) {
                tlvSize += size.getSize() + 3;
            }
        }
        ByteBuffer buf = BaseQmiTypes.QmiBase.createByteBuffer(tlvSize + 12);
        buf.putInt(0);
        buf.putInt(0);
        buf.putShort(serviceId);
        buf.putShort(messageId);
        Log.v(LOG_TAG, "createPayload: serviceId= " + serviceId + " messageId= " + messageId);
        if (!(qmiItems == null || types == null || qmiItems[0] == null)) {
            for (int i = 0; i < qmiItems.length; i++) {
                vLog(qmiItems[i].toString());
                buf.put(qmiItems[i].toTlv(types[i]));
                Log.v(LOG_TAG, "Intermediate buf in QmiOemHook sendQmiMessage Sync or Async = " + EmbmsOemHook.bytesToHexString(qmiItems[i].toTlv(types[i])));
            }
        }
        Log.v(LOG_TAG, "Byte buf in QmiOemHook createPayload = " + buf);
        return buf.array();
    }

    public byte[] sendQmiMessage(int serviceHook, short[] types, BaseQmiTypes.BaseQmiItemType[] qmiItems) throws IOException {
        int msgSize = 0;
        for (BaseQmiTypes.BaseQmiItemType size : qmiItems) {
            msgSize += size.getSize() + 3;
        }
        ByteBuffer buf = BaseQmiTypes.QmiBase.createByteBuffer(4 + msgSize);
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

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short[] types, BaseQmiTypes.BaseQmiItemType[] qmiItems) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, types, qmiItems, 0);
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short[] types, BaseQmiTypes.BaseQmiItemType[] qmiItems, int phoneId) throws IOException {
        AsyncResult result = this.mQcRilOemHook.sendQcRilHookMsg((int) IQcRilHook.QCRILHOOK_QMI_OEMHOOK_REQUEST_ID, createPayload(serviceId, messageId, types, qmiItems), phoneId);
        if (result.exception == null) {
            return receive((byte[]) result.result, (Message) null, QmiOemHookConstants.ResponseType.IS_SYNC_RESPONSE, phoneId);
        }
        Log.w(LOG_TAG, String.format("sendQmiMessage() Failed : %s", new Object[]{result.exception.toString()}));
        result.exception.printStackTrace();
        throw new IOException();
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short[] types, BaseQmiTypes.BaseQmiItemType[] qmiItems, Message msg) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, types, qmiItems, msg, 0);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short[] types, BaseQmiTypes.BaseQmiItemType[] qmiItems, Message msg, int phoneId) throws IOException {
        String str = LOG_TAG;
        Log.w(str, "sendQmiMessageAsync phoneId: " + phoneId);
        this.mQcRilOemHook.sendQcRilHookMsgAsync(IQcRilHook.QCRILHOOK_QMI_OEMHOOK_REQUEST_ID, createPayload(serviceId, messageId, types, qmiItems), new OemHookCallback(msg), phoneId);
    }

    public byte[] sendQmiMessage(int serviceHook, short type, BaseQmiTypes.BaseQmiItemType qmiItem) throws IOException {
        return sendQmiMessage(serviceHook, new short[]{type}, new BaseQmiTypes.BaseQmiItemType[]{qmiItem});
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short type, BaseQmiTypes.BaseQmiItemType qmiItem) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, type, qmiItem, 0);
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId, short type, BaseQmiTypes.BaseQmiItemType qmiItem, int phoneId) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, new short[]{type}, new BaseQmiTypes.BaseQmiItemType[]{qmiItem}, phoneId);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short type, BaseQmiTypes.BaseQmiItemType qmiItem, Message msg) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, type, qmiItem, msg, 0);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, short type, BaseQmiTypes.BaseQmiItemType qmiItem, Message msg, int phoneId) throws IOException {
        String str = LOG_TAG;
        Log.w(str, "sendQmiMessageAsync phoneId: " + phoneId);
        sendQmiMessageAsync(serviceId, messageId, new short[]{type}, new BaseQmiTypes.BaseQmiItemType[]{qmiItem}, msg, phoneId);
    }

    public byte[] sendQmiMessage(int serviceHook) throws IOException {
        return sendQmiMessage(serviceHook, 0, (BaseQmiTypes.BaseQmiItemType) new QmiPrimitiveTypes.QmiNull());
    }

    public HashMap<Integer, Object> sendQmiMessageSync(short serviceId, short messageId) throws IOException {
        return sendQmiMessageSync(serviceId, messageId, (short[]) null, (BaseQmiTypes.BaseQmiItemType[]) null);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, Message msg) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, msg, 0);
    }

    public void sendQmiMessageAsync(short serviceId, short messageId, Message msg, int phoneId) throws IOException {
        sendQmiMessageAsync(serviceId, messageId, (short[]) null, (BaseQmiTypes.BaseQmiItemType[]) null, msg, phoneId);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        Log.v(LOG_TAG, "is destroyed");
    }
}
