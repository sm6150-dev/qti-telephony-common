package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import org.codeaurora.telephony.utils.AsyncResult;
import org.codeaurora.telephony.utils.Registrant;
import org.codeaurora.telephony.utils.RegistrantList;

public class EmbmsOemHook extends Handler {
    private static final int DEFAULT_PHONE = 0;
    private static final short EMBMSHOOK_MSG_ID_ACTDEACT = 17;
    private static final short EMBMSHOOK_MSG_ID_ACTIVATE = 2;
    private static final short EMBMSHOOK_MSG_ID_CONTENT_DESCRIPTION = 29;
    private static final short EMBMSHOOK_MSG_ID_DEACTIVATE = 3;
    private static final short EMBMSHOOK_MSG_ID_DELIVER_LOG_PACKET = 22;
    private static final short EMBMSHOOK_MSG_ID_DISABLE = 1;
    private static final short EMBMSHOOK_MSG_ID_ENABLE = 0;
    private static final short EMBMSHOOK_MSG_ID_GET_ACTIVE = 5;
    private static final short EMBMSHOOK_MSG_ID_GET_ACTIVE_LOG_PACKET_IDS = 21;
    private static final short EMBMSHOOK_MSG_ID_GET_AVAILABLE = 4;
    private static final short EMBMSHOOK_MSG_ID_GET_COVERAGE = 8;
    private static final short EMBMSHOOK_MSG_ID_GET_E911_STATE = 27;
    private static final short EMBMSHOOK_MSG_ID_GET_EMBMS_STATUS = 33;
    private static final short EMBMSHOOK_MSG_ID_GET_INTERESTED_TMGI_LIST_RESP = 35;
    private static final short EMBMSHOOK_MSG_ID_GET_PLMN_LIST = 31;
    private static final short EMBMSHOOK_MSG_ID_GET_SIB16_COVERAGE = 24;
    private static final short EMBMSHOOK_MSG_ID_GET_SIG_STRENGTH = 9;
    private static final short EMBMSHOOK_MSG_ID_GET_TIME = 26;
    private static final short EMBMSHOOK_MSG_ID_SET_TIME = 23;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_ACTIVE_TMGI_LIST = 12;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_AVAILABLE_TMGI_LIST = 15;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_CELL_ID = 18;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL = 30;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_COVERAGE_STATE = 13;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_E911_STATE = 28;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_EMBMS_STATUS = 32;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_GET_INTERESTED_TMGI_LIST = 34;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_OOS_STATE = 16;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_RADIO_STATE = 19;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_SAI_LIST = 20;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_SIB16 = 25;
    private static final short EMBMSHOOK_MSG_ID_UNSOL_STATE_CHANGE = 11;
    private static final short EMBMS_SERVICE_ID = 2;
    private static final int FAILURE = -1;
    /* access modifiers changed from: private */
    public static String LOG_TAG = "EmbmsOemHook";
    private static final int OEM_HOOK_RESPONSE = 1;
    private static final short ONE_BYTE = 1;
    private static final int QCRILHOOK_READY_CALLBACK = 2;
    private static final short SIZE_OF_EACH_PLMN_IN_BYTES = 6;
    private static final int SIZE_OF_TMGI = 6;
    private static final int SUCCESS = 0;
    private static final byte TLV_TYPE_ACTDEACTIVATE_REQ_ACT_TMGI = 3;
    private static final byte TLV_TYPE_ACTDEACTIVATE_REQ_DEACT_TMGI = 4;
    private static final byte TLV_TYPE_ACTDEACTIVATE_REQ_EARFCN_LIST = 6;
    private static final byte TLV_TYPE_ACTDEACTIVATE_REQ_PRIORITY = 5;
    private static final byte TLV_TYPE_ACTDEACTIVATE_REQ_SAI_LIST = 16;
    private static final byte TLV_TYPE_ACTDEACTIVATE_RESP_ACTTMGI = 17;
    private static final byte TLV_TYPE_ACTDEACTIVATE_RESP_ACT_CODE = 2;
    private static final byte TLV_TYPE_ACTDEACTIVATE_RESP_DEACTTMGI = 18;
    private static final byte TLV_TYPE_ACTDEACTIVATE_RESP_DEACT_CODE = 3;
    private static final byte TLV_TYPE_ACTIVATE_REQ_EARFCN_LIST = 5;
    private static final byte TLV_TYPE_ACTIVATE_REQ_PRIORITY = 4;
    private static final byte TLV_TYPE_ACTIVATE_REQ_SAI_LIST = 16;
    private static final byte TLV_TYPE_ACTIVATE_REQ_TMGI = 3;
    private static final byte TLV_TYPE_ACTIVATE_RESP_TMGI = 17;
    private static final short TLV_TYPE_ACTIVELOGPACKETID_REQ_PACKET_ID_LIST = 2;
    private static final short TLV_TYPE_ACTIVELOGPACKETID_RESP_PACKET_ID_LIST = 2;
    private static final byte TLV_TYPE_COMMON_REQ_CALL_ID = 2;
    private static final byte TLV_TYPE_COMMON_REQ_TRACE_ID = 1;
    private static final byte TLV_TYPE_COMMON_RESP_CALL_ID = 16;
    private static final byte TLV_TYPE_COMMON_RESP_CODE = 2;
    private static final byte TLV_TYPE_COMMON_RESP_TRACE_ID = 1;
    private static final byte TLV_TYPE_CONTENT_DESCRIPTION_REQ_PARAMETER_ARRAY = 16;
    private static final byte TLV_TYPE_CONTENT_DESCRIPTION_REQ_TMGI = 3;
    private static final byte TLV_TYPE_DEACTIVATE_REQ_TMGI = 3;
    private static final byte TLV_TYPE_DEACTIVATE_RESP_TMGI = 17;
    private static final short TLV_TYPE_DELIVERLOGPACKET_REQ_LOG_PACKET = 3;
    private static final short TLV_TYPE_DELIVERLOGPACKET_REQ_PACKET_ID = 2;
    private static final byte TLV_TYPE_ENABLE_RESP_IFNAME = 17;
    private static final byte TLV_TYPE_ENABLE_RESP_IF_INDEX = 18;
    private static final byte TLV_TYPE_GET_ACTIVE_RESP_TMGI_ARRAY = 16;
    private static final byte TLV_TYPE_GET_AVAILABLE_RESP_TMGI_ARRAY = 16;
    private static final byte TLV_TYPE_GET_COVERAGE_STATE_RESP_STATE = 16;
    private static final short TLV_TYPE_GET_E911_RESP_STATE = 16;
    private static final short TLV_TYPE_GET_EMBMS_STATUS_RESP = 2;
    private static final byte TLV_TYPE_GET_INTERESTED_TMGI_LIST_RESP_TMGI = 3;
    private static final byte TLV_TYPE_GET_PLMN_LIST_RESP_PLMN_LIST = 2;
    private static final byte TLV_TYPE_GET_SIG_STRENGTH_RESP_ACTIVE_TMGI_LIST = 20;
    private static final byte TLV_TYPE_GET_SIG_STRENGTH_RESP_EXCESS_SNR = 18;
    private static final byte TLV_TYPE_GET_SIG_STRENGTH_RESP_MBSFN_AREA_ID = 16;
    private static final byte TLV_TYPE_GET_SIG_STRENGTH_RESP_NUMBER_OF_TMGI_PER_MBSFN = 19;
    private static final byte TLV_TYPE_GET_SIG_STRENGTH_RESP_SNR = 17;
    private static final byte TLV_TYPE_GET_TIME_RESP_DAY_LIGHT_SAVING = 16;
    private static final byte TLV_TYPE_GET_TIME_RESP_LEAP_SECONDS = 17;
    private static final byte TLV_TYPE_GET_TIME_RESP_LOCAL_TIME_OFFSET = 18;
    private static final byte TLV_TYPE_GET_TIME_RESP_TIME_MSECONDS = 3;
    private static final byte TLV_TYPE_SET_TIME_REQ_SNTP_SUCCESS = 1;
    private static final byte TLV_TYPE_SET_TIME_REQ_TIME_MSECONDS = 16;
    private static final byte TLV_TYPE_SET_TIME_REQ_TIME_STAMP = 17;
    private static final short TLV_TYPE_UNSOL_ACTIVE_IND_TMGI_ARRAY = 2;
    private static final short TLV_TYPE_UNSOL_AVAILABLE_IND_TMGI_ARRAY_OR_RESPONSE_CODE = 2;
    private static final short TLV_TYPE_UNSOL_CELL_ID_IND_CID = 4;
    private static final short TLV_TYPE_UNSOL_CELL_ID_IND_MCC = 2;
    private static final short TLV_TYPE_UNSOL_CELL_ID_IND_MNC = 3;
    private static final short TLV_TYPE_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL_CONTENT_CONTROL = 16;
    private static final short TLV_TYPE_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL_STATUS_CONTROL = 17;
    private static final short TLV_TYPE_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL_TMGI = 2;
    private static final short TLV_TYPE_UNSOL_COVERAGE_IND_STATE_OR_RESPONSE_CODE = 2;
    private static final short TLV_TYPE_UNSOL_E911_STATE_OR_RESPONSE_CODE = 2;
    private static final short TLV_TYPE_UNSOL_EMBMS_STATUS = 1;
    private static final short TLV_TYPE_UNSOL_OOS_IND_STATE = 2;
    private static final short TLV_TYPE_UNSOL_OOS_IND_TMGI_ARRAY = 3;
    private static final short TLV_TYPE_UNSOL_RADIO_STATE = 2;
    private static final short TLV_TYPE_UNSOL_SAI_IND_AVAILABLE_SAI_LIST = 4;
    private static final short TLV_TYPE_UNSOL_SAI_IND_CAMPED_SAI_LIST = 2;
    private static final short TLV_TYPE_UNSOL_SAI_IND_SAI_PER_GROUP_LIST = 3;
    private static final short TLV_TYPE_UNSOL_SIB16 = 1;
    private static final short TLV_TYPE_UNSOL_STATE_IND_IF_INDEX = 3;
    private static final short TLV_TYPE_UNSOL_STATE_IND_IP_ADDRESS = 2;
    private static final short TLV_TYPE_UNSOL_STATE_IND_STATE = 1;
    private static final short TWO_BYTES = 2;
    private static final int UNSOL_BASE_QCRILHOOK = 4096;
    public static final int UNSOL_TYPE_ACTIVE_TMGI_LIST = 2;
    public static final int UNSOL_TYPE_AVAILABLE_TMGI_LIST = 4;
    public static final int UNSOL_TYPE_BROADCAST_COVERAGE = 3;
    public static final int UNSOL_TYPE_CELL_ID = 6;
    public static final int UNSOL_TYPE_CONTENT_DESC_PER_OBJ_CONTROL = 11;
    public static final int UNSOL_TYPE_E911_STATE = 10;
    public static final int UNSOL_TYPE_EMBMSOEMHOOK_READY_CALLBACK = 4097;
    public static final int UNSOL_TYPE_EMBMS_STATUS = 12;
    public static final int UNSOL_TYPE_GET_INTERESTED_TMGI_LIST = 13;
    public static final int UNSOL_TYPE_OOS_STATE = 5;
    public static final int UNSOL_TYPE_RADIO_STATE = 7;
    public static final int UNSOL_TYPE_SAI_LIST = 8;
    public static final int UNSOL_TYPE_SIB16_COVERAGE = 9;
    public static final int UNSOL_TYPE_STATE_CHANGE = 1;
    private static int mRefCount = 0;
    private static EmbmsOemHook sInstance;
    private QmiOemHook mQmiOemHook;
    private RegistrantList mRegistrants = new RegistrantList();

    private EmbmsOemHook(Context context) {
        Log.v(LOG_TAG, "EmbmsOemHook ()");
        this.mQmiOemHook = QmiOemHook.getInstance(context);
        QmiOemHook.registerService(2, this, 1);
        QmiOemHook.registerOnReadyCb(this, 2, (Object) null);
    }

    public static synchronized EmbmsOemHook getInstance(Context context) {
        EmbmsOemHook embmsOemHook;
        synchronized (EmbmsOemHook.class) {
            if (sInstance == null) {
                sInstance = new EmbmsOemHook(context);
                Log.d(LOG_TAG, "Singleton Instance of Embms created.");
            }
            mRefCount++;
            embmsOemHook = sInstance;
        }
        return embmsOemHook;
    }

    public synchronized void dispose() {
        int i = mRefCount - 1;
        mRefCount = i;
        if (i == 0) {
            Log.d(LOG_TAG, "dispose(): Unregistering receiver");
            QmiOemHook.unregisterService(2);
            QmiOemHook.unregisterOnReadyCb(this);
            this.mQmiOemHook.dispose();
            this.mQmiOemHook = null;
            sInstance = null;
            this.mRegistrants.removeCleared();
        } else {
            Log.v(LOG_TAG, "dispose mRefCount = " + mRefCount);
        }
    }

    public void handleMessage(Message msg) {
        String str = LOG_TAG;
        Log.i(str, "received message : " + msg.what);
        AsyncResult ar = (AsyncResult) msg.obj;
        int i = msg.what;
        if (i == 1) {
            HashMap<Integer, Object> map = (HashMap) ar.result;
            if (map == null) {
                Log.e(LOG_TAG, "Hashmap async userobj is NULL");
            } else {
                handleResponse(map);
            }
        } else if (i != 2) {
            String str2 = LOG_TAG;
            Log.e(str2, "Unexpected message received from QmiOemHook what = " + msg.what);
        } else {
            notifyUnsol(4097, ar.result, 0);
        }
    }

    private void handleResponse(HashMap<Integer, Object> map) {
        HashMap<Integer, Object> hashMap = map;
        short msgId = ((Short) hashMap.get(8)).shortValue();
        int responseSize = ((Integer) hashMap.get(2)).intValue();
        int successStatus = ((Integer) hashMap.get(3)).intValue();
        Message msg = (Message) hashMap.get(4);
        int phoneId = ((Integer) hashMap.get(9)).intValue();
        if (msg != null) {
            msg.arg1 = phoneId;
        }
        ByteBuffer respByteBuf = (ByteBuffer) hashMap.get(6);
        String str = LOG_TAG;
        Log.d(str, " responseSize=" + responseSize + " successStatus=" + successStatus + "phoneId: " + phoneId);
        switch (msgId) {
            case 0:
                msg.obj = new EnableResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 1:
                msg.obj = new DisableResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 2:
            case 3:
                msg.obj = new TmgiResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 4:
            case 15:
                if (msgId != 4 || successStatus == 0) {
                    notifyUnsol(4, new TmgiListIndication(respByteBuf, msgId), phoneId);
                    return;
                }
                String str2 = LOG_TAG;
                Log.e(str2, "Error received in EMBMSHOOK_MSG_ID_GET_AVAILABLE: " + successStatus);
                return;
            case 5:
            case 12:
                if (msgId != 5 || successStatus == 0) {
                    notifyUnsol(2, new TmgiListIndication(respByteBuf, msgId), phoneId);
                    return;
                }
                String str3 = LOG_TAG;
                Log.e(str3, "Error received in EMBMSHOOK_MSG_ID_GET_ACTIVE: " + successStatus);
                return;
            case 8:
            case 13:
                if (msgId != 8 || successStatus == 0) {
                    notifyUnsol(3, new CoverageState(respByteBuf, msgId), phoneId);
                    return;
                }
                String str4 = LOG_TAG;
                Log.e(str4, "Error received in EMBMSHOOK_MSG_ID_GET_COVERAGE: " + successStatus);
                return;
            case 9:
                msg.obj = new SigStrengthResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 11:
                notifyUnsol(1, new StateChangeInfo(respByteBuf), phoneId);
                return;
            case 16:
                notifyUnsol(5, new OosState(respByteBuf), phoneId);
                return;
            case 17:
                msg.obj = new ActDeactResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 18:
                notifyUnsol(6, new CellIdIndication(respByteBuf), phoneId);
                return;
            case 19:
                notifyUnsol(7, new RadioStateIndication(respByteBuf), phoneId);
                return;
            case 20:
                notifyUnsol(8, new SaiIndication(respByteBuf), phoneId);
                return;
            case 21:
                msg.obj = new ActiveLogPacketIDsResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 22:
                String str5 = LOG_TAG;
                Log.v(str5, " deliverLogPacket response successStatus=" + successStatus);
                return;
            case 23:
                msg.arg1 = successStatus;
                msg.sendToTarget();
                return;
            case 24:
            case 25:
                if (msgId != 24 || successStatus == 0) {
                    notifyUnsol(9, new Sib16Coverage(respByteBuf), phoneId);
                    return;
                }
                String str6 = LOG_TAG;
                Log.e(str6, "Error received in EMBMSHOOK_MSG_ID_GET_SIB16_COVERAGE: " + successStatus);
                return;
            case 26:
                msg.obj = new TimeResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 27:
            case 28:
                notifyUnsol(10, new E911StateIndication(respByteBuf, msgId), phoneId);
                return;
            case 29:
                String str7 = LOG_TAG;
                Log.v(str7, " contentDescription response successStatus=" + successStatus);
                return;
            case 30:
                notifyUnsol(11, new ContentDescPerObjectControlIndication(respByteBuf), phoneId);
                return;
            case 31:
                msg.obj = new GetPLMNListResponse(successStatus, respByteBuf);
                msg.sendToTarget();
                return;
            case 32:
            case 33:
                notifyUnsol(12, new EmbmsStatus(respByteBuf, msgId), phoneId);
                return;
            case 34:
                notifyUnsol(13, new RequestIndication(respByteBuf), phoneId);
                return;
            case 35:
                String str8 = LOG_TAG;
                Log.v(str8, " getInterestedTmgiListResponse ack successStatus=" + successStatus);
                return;
            default:
                String str9 = LOG_TAG;
                Log.e(str9, "received unexpected msgId " + msgId);
                return;
        }
    }

    private void notifyUnsol(int type, Object payload, int phoneId) {
        AsyncResult ar = new AsyncResult((Object) null, new UnsolObject(type, payload, phoneId), (Throwable) null);
        String str = LOG_TAG;
        Log.i(str, "Notifying registrants type = " + type);
        this.mRegistrants.notifyRegistrants(ar);
    }

    public void registerForNotifications(Handler h, int what, Object obj) {
        Registrant r = new Registrant(h, what, obj);
        synchronized (this.mRegistrants) {
            Log.i(LOG_TAG, "Adding a registrant");
            this.mRegistrants.add(r);
        }
    }

    public void unregisterForNotifications(Handler h) {
        synchronized (this.mRegistrants) {
            Log.i(LOG_TAG, "Removing a registrant");
            this.mRegistrants.remove(h);
        }
    }

    public int enable(int traceId, Message msg, int phoneId) {
        try {
            String str = LOG_TAG;
            Log.i(str, "enable called on PhoneId: " + phoneId);
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_ENABLE, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during enable !!!!!!");
            return -1;
        }
    }

    public int activateTmgi(int traceId, byte callId, byte[] tmgi, int priority, int[] saiList, int[] earfcnList, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "activateTmgi called on PhoneId: " + phoneId);
        TmgiActivateRequest req = new TmgiActivateRequest(traceId, callId, tmgi, priority, saiList, earfcnList);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, 2, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during activate !!!!!!");
            return -1;
        }
    }

    public int deactivateTmgi(int traceId, byte callId, byte[] tmgi, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "deactivateTmgi called on PhoneId: " + phoneId);
        TmgiDeActivateRequest req = new TmgiDeActivateRequest(traceId, tmgi, callId);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, 3, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during deactivate !!!!!!");
            return -1;
        }
    }

    public int actDeactTmgi(int traceId, byte callId, byte[] actTmgi, byte[] deActTmgi, int priority, int[] saiList, int[] earfcnList, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "actDeactTmgi called on PhoneId: " + phoneId);
        ActDeactRequest req = new ActDeactRequest(traceId, callId, actTmgi, deActTmgi, priority, saiList, earfcnList);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, 17, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during activate-deactivate !!!!!!");
            return -1;
        }
    }

    public int getAvailableTMGIList(int traceId, byte callId, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getAvailableTMGIList called on PhoneId: " + phoneId);
        GenericRequest req = new GenericRequest(traceId, callId);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, 4, req.getTypes(), req.getItems(), (Message) null, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getAvailableTMGIList !!!!!!");
            return -1;
        }
    }

    public int getActiveTMGIList(int traceId, byte callId, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getActiveTMGIList called on PhoneId: " + phoneId);
        GenericRequest req = new GenericRequest(traceId, callId);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_ACTIVE, req.getTypes(), req.getItems(), (Message) null, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getActiveTMGIList !!!!!!");
            return -1;
        }
    }

    public int getCoverageState(int traceId, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getCoverageState called on PhoneId: " + phoneId);
        try {
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_COVERAGE, req.getTypes(), req.getItems(), (Message) null, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getActiveTMGIList !!!!!!");
            return -1;
        }
    }

    public int getSignalStrength(int traceId, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getSignalStrength called on PhoneId: " + phoneId);
        try {
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_SIG_STRENGTH, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during enable !!!!!!");
            return -1;
        }
    }

    public int disable(int traceId, byte callId, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "disable called on PhoneId: " + phoneId);
        GenericRequest req = new GenericRequest(traceId, callId);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, 1, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during disable !!!!!!");
            return -1;
        }
    }

    public int getActiveLogPacketIDs(int traceId, int[] supportedLogPacketIdList, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getActiveLogPacketIDs called on PhoneId: " + phoneId);
        ActiveLogPacketIDsRequest req = new ActiveLogPacketIDsRequest(traceId, supportedLogPacketIdList);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_ACTIVE_LOG_PACKET_IDS, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during activate log packet ID's !!!!!!");
            return -1;
        }
    }

    public int deliverLogPacket(int traceId, int logPacketId, byte[] logPacket, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "deliverLogPacket called on PhoneId: " + phoneId);
        DeliverLogPacketRequest req = new DeliverLogPacketRequest(traceId, logPacketId, logPacket);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_DELIVER_LOG_PACKET, req.getTypes(), req.getItems(), (Message) null, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during deliver logPacket !!!!!!");
            return -1;
        }
    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append("0123456789abcdef".charAt((bytes[i] >> 4) & 15));
            ret.append("0123456789abcdef".charAt(bytes[i] & 15));
        }
        return ret.toString();
    }

    public int getTime(int traceId, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getTime called on PhoneId: " + phoneId);
        try {
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_TIME, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getTime !!!!!!");
            return -1;
        }
    }

    public int getSib16CoverageStatus(Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getSib16CoverageStatus called on PhoneId: " + phoneId);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, EMBMSHOOK_MSG_ID_GET_SIB16_COVERAGE, msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getSIB16 !!!!!!");
            return -1;
        }
    }

    public int getEmbmsStatus(int traceId, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getEmbmsStatus called on PhoneId: " + phoneId);
        try {
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, 33, req.getTypes(), req.getItems(), (Message) null, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getEmbmsStatus !!!!!!");
            return -1;
        }
    }

    public int setTime(boolean sntpSuccess, long timeMseconds, long timeStamp, Message msg, int phoneId) {
        byte success;
        Log.i(LOG_TAG, "setTime called on PhoneId: " + phoneId);
        if (sntpSuccess) {
            success = 1;
        } else {
            success = 0;
        }
        Log.i(LOG_TAG, "setTime success = " + success + " timeMseconds = " + timeMseconds + " timeStamp = " + timeStamp);
        SetTimeRequest req = new SetTimeRequest(success, timeMseconds, timeStamp);
        try {
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_SET_TIME, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occured during setTime !!!!!!");
            return -1;
        }
    }

    public int getE911State(int traceId, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getE911State called on PhoneId: " + phoneId);
        try {
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_E911_STATE, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getE911State !!!!!!");
            return -1;
        }
    }

    public int contentDescription(int traceId, byte callId, byte[] tmgi, int numberOfParameter, int[] parameterCode, int[] parameterValue, Message msg, int phoneId) {
        int i = numberOfParameter;
        int[] parameterCode2 = parameterCode;
        int[] parameterValue2 = parameterValue;
        try {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("contentDescription called on PhoneId: ");
            try {
                sb.append(phoneId);
                Log.i(str, sb.toString());
                if (parameterCode2 == null || parameterValue2 == null) {
                    Log.i(LOG_TAG, "contentDescription: either parameterCode or parameterValue is nullparameterCode = " + parameterCode2 + " parameterValue = " + parameterValue2);
                    parameterCode2 = new int[0];
                    try {
                        parameterValue2 = new int[0];
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "IOException occurred during contentDescription !!!!!!");
                        return -1;
                    }
                }
            } catch (IOException e2) {
                Log.e(LOG_TAG, "IOException occurred during contentDescription !!!!!!");
                return -1;
            }
            try {
                if (i == parameterCode2.length && i == parameterValue2.length) {
                    if (parameterCode2.length == parameterValue2.length) {
                        int parameterArraySize = i * 2;
                        int[] parameterArray = new int[parameterArraySize];
                        int pointer = 0;
                        for (int i2 = 0; i2 < parameterArraySize; i2 += 2) {
                            parameterArray[i2] = parameterCode2[pointer];
                            parameterArray[i2 + 1] = parameterValue2[pointer];
                            pointer++;
                        }
                        Log.i(LOG_TAG, "contentDescription: parameterArray: " + Arrays.toString(parameterArray));
                        ContentDescriptionReq req = new ContentDescriptionReq(traceId, callId, tmgi, parameterArray);
                        this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_CONTENT_DESCRIPTION, req.getTypes(), req.getItems(), msg, phoneId);
                        return 0;
                    }
                }
                Log.e(LOG_TAG, "contentDescription: Invalid input, numberOfParameter = " + i + " parameterCode = " + parameterCode2 + " parameterValue = " + parameterValue2);
                return -1;
            } catch (IOException e3) {
                Log.e(LOG_TAG, "IOException occurred during contentDescription !!!!!!");
                return -1;
            }
        } catch (IOException e4) {
            int i3 = phoneId;
            Log.e(LOG_TAG, "IOException occurred during contentDescription !!!!!!");
            return -1;
        }
    }

    public int getPLMNListRequest(int traceId, Message msg, int phoneId) {
        String str = LOG_TAG;
        Log.i(str, "getPLMNListRequest called on PhoneId: " + phoneId);
        try {
            BasicRequest req = new BasicRequest(traceId);
            this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_GET_PLMN_LIST, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getPLMNListRequest !!!!!!");
            return -1;
        }
    }

    public int getInterestedTMGIListResponse(int traceId, byte callId, byte[] tmgiList, int phoneId, Message msg) {
        try {
            GetInterestedTmgiResponse req = new GetInterestedTmgiResponse(traceId, callId, tmgiList);
            this.mQmiOemHook.sendQmiMessageAsync(2, 35, req.getTypes(), req.getItems(), msg, phoneId);
            return 0;
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred during getInterestedTMGIListResponse !!!!!!");
            return -1;
        }
    }

    public class UnsolObject {
        public Object obj;
        public int phoneId;
        public int unsolId;

        public UnsolObject(int i, Object o, int phone) {
            this.unsolId = i;
            this.obj = o;
            this.phoneId = phone;
        }
    }

    public class StateChangeInfo {
        public int ifIndex;
        public String ipAddress;
        public int state;

        public StateChangeInfo(int state2, String address, int index) {
            this.state = state2;
            this.ipAddress = address;
            this.ifIndex = index;
        }

        public StateChangeInfo(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int length = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 1) {
                    this.state = buf.getInt();
                    String access$000 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$000, "State = " + this.state);
                } else if (type == 2) {
                    byte[] address = new byte[length];
                    for (int i = 0; i < length; i++) {
                        address[i] = buf.get();
                    }
                    this.ipAddress = new QmiPrimitiveTypes.QmiString(address).toString();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0002, "ip Address = " + this.ipAddress);
                } else if (type != 3) {
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    Log.e(access$0003, "StateChangeInfo: Unexpected Type " + type);
                } else {
                    this.ifIndex = buf.getInt();
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0004, "index = " + this.ifIndex);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public byte[] parseTmgi(ByteBuffer buf) {
        int index = 0;
        byte totalTmgis = buf.get();
        byte[] tmgi = new byte[(totalTmgis * TLV_TYPE_ACTDEACTIVATE_REQ_EARFCN_LIST)];
        for (int i = 0; i < totalTmgis; i++) {
            byte tmgiLength = buf.get();
            int j = 0;
            while (j < tmgiLength) {
                tmgi[index] = buf.get();
                j++;
                index++;
            }
        }
        return tmgi;
    }

    /* access modifiers changed from: private */
    public byte[] parseActiveTmgi(ByteBuffer buf) {
        int index = 0;
        short totalTmgis = buf.getShort();
        byte[] tmgi = new byte[(totalTmgis * SIZE_OF_EACH_PLMN_IN_BYTES)];
        for (int i = 0; i < totalTmgis; i++) {
            byte tmgiLength = buf.get();
            int j = 0;
            while (j < tmgiLength) {
                tmgi[index] = buf.get();
                j++;
                index++;
            }
        }
        return tmgi;
    }

    public class TmgiListIndication {
        public int code = 0;
        public byte[] list = new byte[0];
        public byte[] sessions = null;
        public int traceId = 0;

        public TmgiListIndication(ByteBuffer buf, short msgId) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type != 1) {
                        if (type == 2) {
                            if (msgId != 4) {
                                if (msgId == 5) {
                                }
                            }
                            this.code = buf.getInt();
                            String access$000 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$000, "response code = " + this.code);
                        } else if (type != 16) {
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            Log.e(access$0002, "TmgiListIndication: Unexpected Type " + type);
                        }
                        this.list = EmbmsOemHook.this.parseTmgi(buf);
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "tmgiArray = " + EmbmsOemHook.bytesToHexString(this.list));
                    } else {
                        this.traceId = buf.getInt();
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0004, "traceId = " + this.traceId);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in TmgiListIndication");
                }
            }
        }
    }

    public class OosState {
        public byte[] list = null;
        public int state;
        public int traceId = 0;

        public OosState(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 1) {
                    this.traceId = buf.getInt();
                    String access$000 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$000, "traceId = " + this.traceId);
                } else if (type == 2) {
                    this.state = buf.getInt();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0002, "OOs State = " + this.state);
                } else if (type != 3) {
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    Log.e(access$0003, "OosState: Unexpected Type " + type);
                } else {
                    this.list = EmbmsOemHook.this.parseTmgi(buf);
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0004, "tmgiArray = " + EmbmsOemHook.bytesToHexString(this.list));
                }
            }
        }
    }

    public class CellIdIndication {
        public String id = null;
        public String mcc = null;
        public String mnc = null;
        public int traceId = 0;

        public CellIdIndication(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int length = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type == 2) {
                        byte[] temp = new byte[length];
                        for (int i = 0; i < length; i++) {
                            temp[i] = buf.get();
                        }
                        this.mcc = new QmiPrimitiveTypes.QmiString(temp).toStringValue();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "MCC = " + this.mcc);
                    } else if (type == 3) {
                        byte[] temp2 = new byte[length];
                        for (int i2 = 0; i2 < length; i2++) {
                            temp2[i2] = buf.get();
                        }
                        this.mnc = new QmiPrimitiveTypes.QmiString(temp2).toStringValue();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "MNC = " + this.mnc);
                    } else if (type != 4) {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0004, "CellIdIndication: Unexpected Type " + type);
                    } else {
                        this.id = String.format("%7s", new Object[]{Integer.toHexString(buf.getInt())}).replace(' ', '0');
                        String access$0005 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0005, "CellId = " + this.id);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for CellIdIndication");
                }
            }
        }
    }

    public class RadioStateIndication {
        public int state = 0;
        public int traceId = 0;

        public RadioStateIndication(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type != 2) {
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0002, "RadioStateIndication: Unexpected Type " + type);
                    } else {
                        this.state = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "radio = " + this.state);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for RadioStateIndication");
                }
            }
        }
    }

    public class SaiIndication {
        public int[] availableSaiList = null;
        public int[] campedSaiList = null;
        public int[] numSaiPerGroupList = null;
        public int traceId = 0;

        public SaiIndication(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                try {
                    int type = buf.get();
                    short s = buf.getShort();
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type == 2) {
                        int listLength = buf.get();
                        int[] list = new int[listLength];
                        for (int i = 0; i < listLength; i++) {
                            list[i] = buf.getInt();
                        }
                        this.campedSaiList = list;
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "Camped list = " + Arrays.toString(this.campedSaiList));
                    } else if (type == 3) {
                        int listLength2 = buf.get();
                        int[] list2 = new int[listLength2];
                        for (int i2 = 0; i2 < listLength2; i2++) {
                            list2[i2] = buf.getInt();
                        }
                        this.numSaiPerGroupList = list2;
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "Number of SAI per group list = " + Arrays.toString(this.numSaiPerGroupList));
                    } else if (type != 4) {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0004, "SaiIndication: Unexpected Type " + type);
                    } else {
                        int availableLength = buf.getShort();
                        int[] list3 = new int[availableLength];
                        for (int i3 = 0; i3 < availableLength; i3++) {
                            list3[i3] = buf.getInt();
                        }
                        this.availableSaiList = list3;
                        String access$0005 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0005, "Available SAI list = " + Arrays.toString(this.availableSaiList));
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for SaiIndication");
                }
            }
        }
    }

    public class CoverageState {
        public int code = 0;
        public int state;
        public int status;
        public int traceId = 0;

        public CoverageState(ByteBuffer buf, short msgId) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type != 1) {
                        if (type != 2) {
                            if (type != 16) {
                                String access$000 = EmbmsOemHook.LOG_TAG;
                                Log.e(access$000, "CoverageState: Unexpected Type " + type);
                            }
                        } else if (msgId == 8) {
                            this.code = buf.getInt();
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0002, "response code = " + this.code);
                        }
                        this.state = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "Coverage State = " + this.state);
                    } else {
                        this.traceId = buf.getInt();
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0004, "traceId = " + this.traceId);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in CoverageState");
                }
            }
        }
    }

    public class Sib16Coverage {
        public boolean inCoverage = false;

        public Sib16Coverage(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type != 1) {
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$000, "Sib16Coverage: Unexpected Type " + type);
                    } else {
                        if (buf.get() == 1) {
                            this.inCoverage = true;
                        }
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "Unsol SIB16 coverage status = " + this.inCoverage);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for Sib16Coverage");
                }
            }
        }
    }

    public class E911StateIndication {
        public int code;
        public int state;
        public int traceId = 0;

        public E911StateIndication(ByteBuffer buf, short msgId) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type != 1) {
                        if (type != 2) {
                            if (type != 16) {
                                String access$000 = EmbmsOemHook.LOG_TAG;
                                Log.e(access$000, "E911 State: Unexpected Type " + type);
                            }
                        } else if (msgId == 27) {
                            this.code = buf.getInt();
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0002, "response code = " + this.code);
                        }
                        this.state = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "E911 State = " + this.state);
                    } else {
                        this.traceId = buf.getInt();
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0004, "traceId = " + this.traceId);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for E911 Notification");
                }
            }
        }
    }

    public class ContentDescPerObjectControlIndication {
        public int perObjectContentControl;
        public int perObjectStatusControl;
        public byte[] tmgi = null;
        public int traceId = 0;

        public ContentDescPerObjectControlIndication(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type == 2) {
                        int tmgiLength = buf.get();
                        byte[] tmgi2 = new byte[tmgiLength];
                        for (int i = 0; i < tmgiLength; i++) {
                            tmgi2[i] = buf.get();
                        }
                        this.tmgi = tmgi2;
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "tmgi = " + EmbmsOemHook.bytesToHexString(this.tmgi));
                    } else if (type == 16) {
                        this.perObjectContentControl = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "perObjectContentControl = " + this.perObjectContentControl);
                    } else if (type != 17) {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0004, "ContentDescPerObjectControl: Unexpected Type " + type);
                    } else {
                        this.perObjectStatusControl = buf.getInt();
                        String access$0005 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0005, "perObjectStatusControl = " + this.perObjectStatusControl);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing forContentDescPerObjectControl Notification");
                }
            }
        }
    }

    public class RequestIndication {
        public int traceId = 0;

        public RequestIndication(ByteBuffer buf) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type != 1) {
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$000, "RequestIndication: Unexpected Type " + type);
                    } else {
                        this.traceId = buf.getInt();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "traceId = " + this.traceId);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for RequestIndication");
                }
            }
        }
    }

    public class EnableResponse {
        public byte callId = 0;
        public int code = 0;
        public int ifIndex = 0;
        public String interfaceName = null;
        public int status;
        public int traceId;

        public EnableResponse(int error, ByteBuffer buf) {
            this.status = error;
            while (buf.hasRemaining()) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int length = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 1) {
                    this.traceId = buf.getInt();
                    String access$000 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$000, "traceId = " + this.traceId);
                } else if (type != 2) {
                    switch (type) {
                        case 16:
                            this.callId = buf.get();
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0002, "callid = " + this.callId);
                            break;
                        case 17:
                            byte[] name = new byte[length];
                            for (int i = 0; i < length; i++) {
                                name[i] = buf.get();
                            }
                            this.interfaceName = new QmiPrimitiveTypes.QmiString(name).toStringValue();
                            String access$0003 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0003, "ifName = " + this.interfaceName);
                            break;
                        case 18:
                            this.ifIndex = buf.getInt();
                            String access$0004 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0004, "ifIndex = " + this.ifIndex);
                            break;
                        default:
                            String access$0005 = EmbmsOemHook.LOG_TAG;
                            Log.e(access$0005, "EnableResponse: Unexpected Type " + type);
                            break;
                    }
                } else {
                    this.code = buf.getInt();
                    String access$0006 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0006, "code = " + this.code);
                }
            }
        }
    }

    public class DisableResponse {
        public byte callId = 0;
        public int code = 0;
        public int status;
        public int traceId;

        public DisableResponse(int error, ByteBuffer buf) {
            this.status = error;
            while (buf.hasRemaining()) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 1) {
                    this.traceId = buf.getInt();
                    String access$000 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$000, "traceId = " + this.traceId);
                } else if (type == 2) {
                    this.code = buf.getInt();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0002, "code = " + this.code);
                } else if (type != 16) {
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    Log.e(access$0003, "DisableResponse: Unexpected Type " + type);
                } else {
                    this.callId = buf.get();
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0004, "callid = " + this.callId);
                }
            }
        }
    }

    public class BasicRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiInteger traceId;

        public BasicRequest(int trace) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId};
        }

        public short[] getTypes() {
            return new short[]{1};
        }
    }

    public class GenericRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiByte callId;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public GenericRequest(int trace, byte callId2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.callId = new QmiPrimitiveTypes.QmiByte(callId2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.callId};
        }

        public short[] getTypes() {
            return new short[]{1, 2};
        }
    }

    /* access modifiers changed from: private */
    public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> byteArrayToQmiArray(short vSize, byte[] arr) {
        QmiPrimitiveTypes.QmiByte[] qmiByteArray = new QmiPrimitiveTypes.QmiByte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            qmiByteArray[i] = new QmiPrimitiveTypes.QmiByte(arr[i]);
        }
        return new QmiPrimitiveTypes.QmiArray<>((T[]) qmiByteArray, QmiPrimitiveTypes.QmiByte.class, vSize);
    }

    /* access modifiers changed from: private */
    public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> tmgiListArrayToQmiArray(short vSize, byte[] tmgiList) {
        int length = tmgiList == null ? 0 : tmgiList.length;
        int numOfTmgi = length / 6;
        QmiPrimitiveTypes.QmiByte[] qmiByteArray = new QmiPrimitiveTypes.QmiByte[(length + (numOfTmgi * 1))];
        int index = 0;
        int i = 0;
        while (i < numOfTmgi) {
            int index2 = index + 1;
            qmiByteArray[index] = new QmiPrimitiveTypes.QmiByte(6);
            int j = i * 6;
            while (j < (i + 1) * 6) {
                qmiByteArray[index2] = new QmiPrimitiveTypes.QmiByte(tmgiList[j]);
                j++;
                index2++;
            }
            i++;
            index = index2;
        }
        return new QmiPrimitiveTypes.QmiArray<>(qmiByteArray, QmiPrimitiveTypes.QmiByte.class, vSize, 7);
    }

    /* access modifiers changed from: private */
    public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> intArrayToQmiArray(short vSize, int[] arr) {
        int length = arr == null ? 0 : arr.length;
        QmiPrimitiveTypes.QmiInteger[] qmiIntArray = new QmiPrimitiveTypes.QmiInteger[length];
        for (int i = 0; i < length; i++) {
            qmiIntArray[i] = new QmiPrimitiveTypes.QmiInteger((long) arr[i]);
        }
        return new QmiPrimitiveTypes.QmiArray<>((T[]) qmiIntArray, QmiPrimitiveTypes.QmiInteger.class, vSize);
    }

    /* access modifiers changed from: private */
    public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> intArrayToQmiArray(short vSize, int[] arr, short numOfElements) {
        int length = arr == null ? 0 : arr.length;
        QmiPrimitiveTypes.QmiInteger[] qmiIntArray = new QmiPrimitiveTypes.QmiInteger[length];
        for (int i = 0; i < length; i++) {
            qmiIntArray[i] = new QmiPrimitiveTypes.QmiInteger((long) arr[i]);
        }
        return new QmiPrimitiveTypes.QmiArray<>(qmiIntArray, QmiPrimitiveTypes.QmiInteger.class, vSize, numOfElements);
    }

    public class TmgiActivateRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiByte callId;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> earfcnList;
        public QmiPrimitiveTypes.QmiInteger priority;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> saiList;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> tmgi;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public TmgiActivateRequest(int trace, byte callId2, byte[] tmgi2, int priority2, int[] saiList2, int[] earfcnList2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.callId = new QmiPrimitiveTypes.QmiByte(callId2);
            this.priority = new QmiPrimitiveTypes.QmiInteger((long) priority2);
            this.tmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, tmgi2);
            this.saiList = EmbmsOemHook.this.intArrayToQmiArray(1, saiList2);
            this.earfcnList = EmbmsOemHook.this.intArrayToQmiArray(1, earfcnList2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.callId, this.tmgi, this.priority, this.saiList, this.earfcnList};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3, 4, 16, EmbmsOemHook.EMBMSHOOK_MSG_ID_GET_ACTIVE};
        }
    }

    public class ActDeactRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> actTmgi;
        public QmiPrimitiveTypes.QmiByte callId;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> deActTmgi;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> earfcnList;
        public QmiPrimitiveTypes.QmiInteger priority;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> saiList;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public ActDeactRequest(int trace, byte callId2, byte[] actTmgi2, byte[] deActTmgi2, int priority2, int[] saiList2, int[] earfcnList2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.callId = new QmiPrimitiveTypes.QmiByte(callId2);
            this.priority = new QmiPrimitiveTypes.QmiInteger((long) priority2);
            this.actTmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, actTmgi2);
            this.deActTmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, deActTmgi2);
            this.saiList = EmbmsOemHook.this.intArrayToQmiArray(1, saiList2);
            this.earfcnList = EmbmsOemHook.this.intArrayToQmiArray(1, earfcnList2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.callId, this.actTmgi, this.deActTmgi, this.priority, this.saiList, this.earfcnList};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3, 4, EmbmsOemHook.EMBMSHOOK_MSG_ID_GET_ACTIVE, 16, EmbmsOemHook.SIZE_OF_EACH_PLMN_IN_BYTES};
        }
    }

    public class TmgiDeActivateRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiByte callId;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> tmgi;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public TmgiDeActivateRequest(int trace, byte[] tmgi2, byte callId2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.tmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, tmgi2);
            this.callId = new QmiPrimitiveTypes.QmiByte(callId2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.callId, this.tmgi};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3};
        }
    }

    public class SetTimeRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiByte sntpSuccess;
        public QmiPrimitiveTypes.QmiLong timeMseconds;
        public QmiPrimitiveTypes.QmiLong timeStamp;

        public SetTimeRequest(byte sntpSuccess2, long timeMseconds2, long timeStamp2) {
            this.sntpSuccess = new QmiPrimitiveTypes.QmiByte(sntpSuccess2);
            this.timeMseconds = new QmiPrimitiveTypes.QmiLong(timeMseconds2);
            this.timeStamp = new QmiPrimitiveTypes.QmiLong(timeStamp2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.sntpSuccess, this.timeMseconds, this.timeStamp};
        }

        public short[] getTypes() {
            return new short[]{1, 16, 17};
        }
    }

    public class ActiveLogPacketIDsRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> supportedLogPacketIdList;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public ActiveLogPacketIDsRequest(int trace, int[] supportedLogPacketIdList2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.supportedLogPacketIdList = EmbmsOemHook.this.intArrayToQmiArray(2, supportedLogPacketIdList2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.supportedLogPacketIdList};
        }

        public short[] getTypes() {
            return new short[]{1, 2};
        }
    }

    public class DeliverLogPacketRequest extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> logPacket;
        public QmiPrimitiveTypes.QmiInteger logPacketId;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public DeliverLogPacketRequest(int trace, int logPacketId2, byte[] logPacket2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.logPacketId = new QmiPrimitiveTypes.QmiInteger((long) logPacketId2);
            this.logPacket = EmbmsOemHook.this.byteArrayToQmiArray(2, logPacket2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.logPacketId, this.logPacket};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3};
        }
    }

    public class ContentDescriptionReq extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiByte callId;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiInteger> parameterArray;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> tmgi;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public ContentDescriptionReq(int trace, byte callId2, byte[] tmgi2, int[] parameterArray2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) trace);
            this.callId = new QmiPrimitiveTypes.QmiByte(callId2);
            this.tmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, tmgi2);
            this.parameterArray = EmbmsOemHook.this.intArrayToQmiArray(1, parameterArray2, 2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.callId, this.tmgi, this.parameterArray};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3, 16};
        }
    }

    public class GetInterestedTmgiResponse extends BaseQmiTypes.BaseQmiStructType {
        public QmiPrimitiveTypes.QmiByte callId;
        public QmiPrimitiveTypes.QmiArray<QmiPrimitiveTypes.QmiByte> tmgiList;
        public QmiPrimitiveTypes.QmiInteger traceId;

        public GetInterestedTmgiResponse(int traceId2, byte callId2, byte[] tmgiList2) {
            this.traceId = new QmiPrimitiveTypes.QmiInteger((long) traceId2);
            this.callId = new QmiPrimitiveTypes.QmiByte(callId2);
            this.tmgiList = EmbmsOemHook.this.tmgiListArrayToQmiArray(1, tmgiList2);
        }

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.traceId, this.callId, this.tmgiList};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3};
        }
    }

    public class TmgiResponse {
        public int code = 0;
        public int status;
        public byte[] tmgi = null;
        public int traceId = 0;

        public TmgiResponse(int status2, ByteBuffer buf) {
            this.status = status2;
            while (buf.hasRemaining()) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 1) {
                    this.traceId = buf.getInt();
                    String access$000 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$000, "traceId = " + this.traceId);
                } else if (type == 2) {
                    this.code = buf.getInt();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0002, "code = " + this.code);
                } else if (type == 16) {
                    byte id = buf.get();
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0003, "callid = " + id);
                } else if (type != 17) {
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    Log.e(access$0004, "TmgiResponse: Unexpected Type " + type);
                } else {
                    int tmgiLength = buf.get();
                    byte[] tmgi2 = new byte[tmgiLength];
                    for (int i = 0; i < tmgiLength; i++) {
                        tmgi2[i] = buf.get();
                    }
                    this.tmgi = tmgi2;
                    String access$0005 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0005, "tmgi = " + EmbmsOemHook.bytesToHexString(this.tmgi));
                }
            }
        }
    }

    public class SigStrengthResponse {
        public int code = 0;
        public float[] esnr = null;
        public int[] mbsfnAreaId = null;
        public float[] snr = null;
        public int status;
        public int[] tmgiPerMbsfn = null;
        public byte[] tmgilist = null;
        public int traceId = 0;

        public SigStrengthResponse(int status2, ByteBuffer buf) {
            this.status = status2;
            while (buf.hasRemaining()) {
                try {
                    int type = buf.get();
                    short s = buf.getShort();
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type != 2) {
                        switch (type) {
                            case 16:
                                int mbsfnLength = buf.get();
                                int[] mbsfnArray = new int[mbsfnLength];
                                for (int i = 0; i < mbsfnLength; i++) {
                                    mbsfnArray[i] = buf.getInt();
                                }
                                this.mbsfnAreaId = mbsfnArray;
                                String access$0002 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0002, "MBSFN_Area_ID = " + Arrays.toString(this.mbsfnAreaId));
                                break;
                            case 17:
                                int esnrLength = buf.get();
                                float[] snrArray = new float[esnrLength];
                                for (int i2 = 0; i2 < esnrLength; i2++) {
                                    snrArray[i2] = buf.getFloat();
                                }
                                this.snr = snrArray;
                                String access$0003 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0003, "SNR = " + Arrays.toString(this.snr));
                                break;
                            case 18:
                                int esnrLength2 = buf.get();
                                float[] esnrArray = new float[esnrLength2];
                                for (int i3 = 0; i3 < esnrLength2; i3++) {
                                    esnrArray[i3] = buf.getFloat();
                                }
                                this.esnr = esnrArray;
                                String access$0004 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0004, "EXCESS SNR = " + Arrays.toString(this.esnr));
                                break;
                            case 19:
                                int tmgiPerMbsfnLength = buf.get();
                                int[] tmgiPerMbsfnArray = new int[tmgiPerMbsfnLength];
                                for (int i4 = 0; i4 < tmgiPerMbsfnLength; i4++) {
                                    tmgiPerMbsfnArray[i4] = buf.getInt();
                                }
                                this.tmgiPerMbsfn = tmgiPerMbsfnArray;
                                String access$0005 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0005, "NUMBER OF TMGI PER MBSFN = " + Arrays.toString(this.tmgiPerMbsfn));
                                break;
                            case 20:
                                this.tmgilist = EmbmsOemHook.this.parseActiveTmgi(buf);
                                String access$0006 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0006, "tmgiArray = " + EmbmsOemHook.bytesToHexString(this.tmgilist));
                                break;
                            default:
                                String access$0007 = EmbmsOemHook.LOG_TAG;
                                Log.e(access$0007, "SigStrengthResponse: Unexpected Type " + type);
                                break;
                        }
                    } else {
                        this.code = buf.getInt();
                        String access$0008 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0008, "code = " + this.code);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in SigStrengthResponse");
                }
            }
            if (this.snr == null) {
                this.snr = new float[0];
            }
            if (this.esnr == null) {
                this.esnr = new float[0];
            }
            if (this.tmgiPerMbsfn == null) {
                this.tmgiPerMbsfn = new int[0];
            }
            if (this.mbsfnAreaId == null) {
                this.mbsfnAreaId = new int[0];
            }
            if (this.tmgilist == null) {
                this.tmgilist = new byte[0];
            }
        }
    }

    public class ActDeactResponse {
        public short actCode = EmbmsOemHook.EMBMSHOOK_MSG_ID_ENABLE;
        public byte[] actTmgi = null;
        public short deactCode = EmbmsOemHook.EMBMSHOOK_MSG_ID_ENABLE;
        public byte[] deactTmgi = null;
        public int status;
        public int traceId = 0;

        public ActDeactResponse(int status2, ByteBuffer buf) {
            this.status = status2;
            while (buf.hasRemaining()) {
                int type = PrimitiveParser.toUnsigned(buf.get());
                int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                if (type == 1) {
                    this.traceId = buf.getInt();
                    String access$000 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$000, "traceId = " + this.traceId);
                } else if (type == 2) {
                    this.actCode = buf.getShort();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0002, "Act code = " + this.actCode);
                } else if (type != 3) {
                    switch (type) {
                        case 16:
                            byte id = buf.get();
                            String access$0003 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0003, "callid = " + id);
                            break;
                        case 17:
                            int tmgiLength = buf.get();
                            byte[] tmgi = new byte[tmgiLength];
                            for (int i = 0; i < tmgiLength; i++) {
                                tmgi[i] = buf.get();
                            }
                            this.actTmgi = tmgi;
                            String access$0004 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0004, "Act tmgi = " + EmbmsOemHook.bytesToHexString(this.actTmgi));
                            break;
                        case 18:
                            int tmgiLength2 = buf.get();
                            byte[] tmgi2 = new byte[tmgiLength2];
                            for (int i2 = 0; i2 < tmgiLength2; i2++) {
                                tmgi2[i2] = buf.get();
                            }
                            this.deactTmgi = tmgi2;
                            String access$0005 = EmbmsOemHook.LOG_TAG;
                            Log.i(access$0005, "Deact tmgi = " + EmbmsOemHook.bytesToHexString(this.deactTmgi));
                            break;
                        default:
                            String access$0006 = EmbmsOemHook.LOG_TAG;
                            Log.e(access$0006, "TmgiResponse: Unexpected Type " + type);
                            break;
                    }
                } else {
                    this.deactCode = buf.getShort();
                    String access$0007 = EmbmsOemHook.LOG_TAG;
                    Log.i(access$0007, "Deact code = " + this.deactCode);
                }
            }
        }
    }

    public class TimeResponse {
        public boolean additionalInfo = false;
        public int code = 0;
        public boolean dayLightSaving = false;
        public byte leapSeconds = 0;
        public long localTimeOffset = 0;
        public int status;
        public long timeMseconds = 0;
        public int traceId = 0;

        public TimeResponse(int status2, ByteBuffer buf) {
            this.status = status2;
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type == 2) {
                        this.code = buf.getInt();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "code = " + this.code);
                    } else if (type != 3) {
                        switch (type) {
                            case 16:
                                this.additionalInfo = true;
                                if (buf.get() == 1) {
                                    this.dayLightSaving = true;
                                }
                                String access$0003 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0003, "dayLightSaving = " + this.dayLightSaving);
                                break;
                            case 17:
                                this.additionalInfo = true;
                                this.leapSeconds = buf.get();
                                String access$0004 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0004, "leapSeconds = " + this.leapSeconds);
                                break;
                            case 18:
                                this.additionalInfo = true;
                                this.localTimeOffset = (long) buf.get();
                                String access$0005 = EmbmsOemHook.LOG_TAG;
                                Log.i(access$0005, "localTimeOffset = " + this.localTimeOffset);
                                break;
                            default:
                                String access$0006 = EmbmsOemHook.LOG_TAG;
                                Log.e(access$0006, "TimeResponse: Unexpected Type " + type);
                                break;
                        }
                    } else {
                        this.timeMseconds = buf.getLong();
                        String access$0007 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0007, "timeMseconds = " + this.timeMseconds);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in TimeResponse");
                }
            }
            String access$0008 = EmbmsOemHook.LOG_TAG;
            Log.i(access$0008, "additionalInfo = " + this.additionalInfo);
        }

        public TimeResponse(int traceId2, int status2, long timeMseconds2, boolean additionalInfo2, long localTimeOffset2, boolean dayLightSaving2, byte leapSeconds2) {
            this.status = status2;
            this.traceId = traceId2;
            this.code = 0;
            this.timeMseconds = timeMseconds2;
            this.localTimeOffset = localTimeOffset2;
            this.additionalInfo = additionalInfo2;
            this.dayLightSaving = dayLightSaving2;
            this.leapSeconds = leapSeconds2;
            String access$000 = EmbmsOemHook.LOG_TAG;
            Log.i(access$000, "TimeResponse: traceId = " + this.traceId + " code = " + this.code + " timeMseconds = " + this.timeMseconds + "additionalInfo = " + this.additionalInfo + " localTimeOffset = " + this.localTimeOffset + " dayLightSaving = " + this.dayLightSaving + " leapSeconds = " + this.leapSeconds);
        }
    }

    public class ActiveLogPacketIDsResponse {
        public int[] activePacketIdList = null;
        public int status;
        public int traceId = 0;

        public ActiveLogPacketIDsResponse(int status2, ByteBuffer buf) {
            this.status = status2;
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type != 2) {
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0002, "ActiveLogPacketIDsResponse: Unexpected Type " + type);
                    } else {
                        int logPacketIdLength = buf.getShort();
                        int[] activeLogPacketIdListArray = new int[logPacketIdLength];
                        for (int i = 0; i < logPacketIdLength; i++) {
                            activeLogPacketIdListArray[i] = buf.getInt();
                        }
                        this.activePacketIdList = activeLogPacketIdListArray;
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "Active log packet Id's = " + Arrays.toString(this.activePacketIdList));
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in ActiveLogPacketIDsResponse");
                }
            }
        }
    }

    public class GetPLMNListResponse {
        public byte[] plmnList = null;
        public int status;
        public int traceId = 0;

        public GetPLMNListResponse(int status2, ByteBuffer buf) {
            this.status = status2;
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type != 2) {
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0002, "GetPLMNListResponse: Unexpected Type " + type);
                    } else {
                        byte numOfPlmn = buf.get();
                        this.plmnList = new byte[(numOfPlmn * EmbmsOemHook.TLV_TYPE_ACTDEACTIVATE_REQ_EARFCN_LIST)];
                        byte index = 0;
                        for (int i = 0; i < numOfPlmn; i++) {
                            byte mccLen = buf.get();
                            buf.get(this.plmnList, index, mccLen);
                            int index2 = index + mccLen;
                            byte mncLen = buf.get();
                            buf.get(this.plmnList, index2, mncLen);
                            index = index2 + mncLen;
                            if (mncLen == 2) {
                                this.plmnList[index] = 32;
                                index++;
                            }
                        }
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "plmnList = " + EmbmsOemHook.bytesToHexString(this.plmnList));
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in GetPLMNListResponse");
                }
            }
        }
    }

    public class EmbmsStatus {
        private static final int TYPE_EMBMS_STATUS = 1000;
        public boolean embmsStatus = false;
        public int traceId = 0;

        public EmbmsStatus(ByteBuffer buf, int msgId) {
            while (buf.hasRemaining()) {
                try {
                    int type = PrimitiveParser.toUnsigned(buf.get());
                    int unsigned = PrimitiveParser.toUnsigned(buf.getShort());
                    if (type == 1 && msgId == 32) {
                        type = 1000;
                    }
                    if (type == 1) {
                        this.traceId = buf.getInt();
                        String access$000 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$000, "traceId = " + this.traceId);
                    } else if (type == 2 || type == 1000) {
                        byte status = buf.get();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0002, "Unsol embmsStatus received = " + status);
                        if (status == 1) {
                            this.embmsStatus = true;
                        }
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        Log.i(access$0003, "Unsol embmsStatus = " + this.embmsStatus);
                    } else {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        Log.e(access$0004, "embmsStatus: Unexpected Type " + type);
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for embmsStatus");
                }
            }
        }
    }
}
