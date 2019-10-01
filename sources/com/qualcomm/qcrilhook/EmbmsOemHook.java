package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiItemType;
import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiStructType;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiArray;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiByte;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiInteger;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiLong;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes.QmiString;
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

    public class ActDeactRequest extends BaseQmiStructType {
        public QmiArray<QmiByte> actTmgi;
        public QmiByte callId;
        public QmiArray<QmiByte> deActTmgi;
        public QmiArray<QmiInteger> earfcnList;
        public QmiInteger priority;
        public QmiArray<QmiInteger> saiList;
        public QmiInteger traceId;

        public ActDeactRequest(int trace, byte callId2, byte[] actTmgi2, byte[] deActTmgi2, int priority2, int[] saiList2, int[] earfcnList2) {
            this.traceId = new QmiInteger((long) trace);
            this.callId = new QmiByte(callId2);
            this.priority = new QmiInteger((long) priority2);
            this.actTmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, actTmgi2);
            this.deActTmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, deActTmgi2);
            this.saiList = EmbmsOemHook.this.intArrayToQmiArray(1, saiList2);
            this.earfcnList = EmbmsOemHook.this.intArrayToQmiArray(1, earfcnList2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.callId, this.actTmgi, this.deActTmgi, this.priority, this.saiList, this.earfcnList};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3, 4, EmbmsOemHook.EMBMSHOOK_MSG_ID_GET_ACTIVE, 16, EmbmsOemHook.SIZE_OF_EACH_PLMN_IN_BYTES};
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("traceId = ");
                    sb.append(this.traceId);
                    Log.i(access$000, sb.toString());
                } else if (type == 2) {
                    this.actCode = buf.getShort();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Act code = ");
                    sb2.append(this.actCode);
                    Log.i(access$0002, sb2.toString());
                } else if (type != 3) {
                    switch (type) {
                        case 16:
                            byte id = buf.get();
                            String access$0003 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("callid = ");
                            sb3.append(id);
                            Log.i(access$0003, sb3.toString());
                            break;
                        case 17:
                            byte tmgiLength = buf.get();
                            byte[] tmgi = new byte[tmgiLength];
                            for (int i = 0; i < tmgiLength; i++) {
                                tmgi[i] = buf.get();
                            }
                            this.actTmgi = tmgi;
                            String access$0004 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Act tmgi = ");
                            sb4.append(EmbmsOemHook.bytesToHexString(this.actTmgi));
                            Log.i(access$0004, sb4.toString());
                            break;
                        case 18:
                            byte tmgiLength2 = buf.get();
                            byte[] tmgi2 = new byte[tmgiLength2];
                            for (int i2 = 0; i2 < tmgiLength2; i2++) {
                                tmgi2[i2] = buf.get();
                            }
                            this.deactTmgi = tmgi2;
                            String access$0005 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("Deact tmgi = ");
                            sb5.append(EmbmsOemHook.bytesToHexString(this.deactTmgi));
                            Log.i(access$0005, sb5.toString());
                            break;
                        default:
                            String access$0006 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append("TmgiResponse: Unexpected Type ");
                            sb6.append(type);
                            Log.e(access$0006, sb6.toString());
                            break;
                    }
                } else {
                    this.deactCode = buf.getShort();
                    String access$0007 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("Deact code = ");
                    sb7.append(this.deactCode);
                    Log.i(access$0007, sb7.toString());
                }
            }
        }
    }

    public class ActiveLogPacketIDsRequest extends BaseQmiStructType {
        public QmiArray<QmiInteger> supportedLogPacketIdList;
        public QmiInteger traceId;

        public ActiveLogPacketIDsRequest(int trace, int[] supportedLogPacketIdList2) {
            this.traceId = new QmiInteger((long) trace);
            this.supportedLogPacketIdList = EmbmsOemHook.this.intArrayToQmiArray(2, supportedLogPacketIdList2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.supportedLogPacketIdList};
        }

        public short[] getTypes() {
            return new short[]{1, 2};
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type != 2) {
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("ActiveLogPacketIDsResponse: Unexpected Type ");
                        sb2.append(type);
                        Log.e(access$0002, sb2.toString());
                    } else {
                        short logPacketIdLength = buf.getShort();
                        int[] activeLogPacketIdListArray = new int[logPacketIdLength];
                        for (int i = 0; i < logPacketIdLength; i++) {
                            activeLogPacketIdListArray[i] = buf.getInt();
                        }
                        this.activePacketIdList = activeLogPacketIdListArray;
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Active log packet Id's = ");
                        sb3.append(Arrays.toString(this.activePacketIdList));
                        Log.i(access$0003, sb3.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in ActiveLogPacketIDsResponse");
                }
            }
        }
    }

    public class BasicRequest extends BaseQmiStructType {
        public QmiInteger traceId;

        public BasicRequest(int trace) {
            this.traceId = new QmiInteger((long) trace);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId};
        }

        public short[] getTypes() {
            return new short[]{1};
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type == 2) {
                        byte[] temp = new byte[length];
                        for (int i = 0; i < length; i++) {
                            temp[i] = buf.get();
                        }
                        this.mcc = new QmiString(temp).toStringValue();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("MCC = ");
                        sb2.append(this.mcc);
                        Log.i(access$0002, sb2.toString());
                    } else if (type == 3) {
                        byte[] temp2 = new byte[length];
                        for (int i2 = 0; i2 < length; i2++) {
                            temp2[i2] = buf.get();
                        }
                        this.mnc = new QmiString(temp2).toStringValue();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("MNC = ");
                        sb3.append(this.mnc);
                        Log.i(access$0003, sb3.toString());
                    } else if (type != 4) {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("CellIdIndication: Unexpected Type ");
                        sb4.append(type);
                        Log.e(access$0004, sb4.toString());
                    } else {
                        this.id = String.format("%7s", new Object[]{Integer.toHexString(buf.getInt())}).replace(' ', '0');
                        String access$0005 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("CellId = ");
                        sb5.append(this.id);
                        Log.i(access$0005, sb5.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for CellIdIndication");
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type == 2) {
                        byte tmgiLength = buf.get();
                        byte[] tmgi2 = new byte[tmgiLength];
                        for (int i = 0; i < tmgiLength; i++) {
                            tmgi2[i] = buf.get();
                        }
                        this.tmgi = tmgi2;
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("tmgi = ");
                        sb2.append(EmbmsOemHook.bytesToHexString(this.tmgi));
                        Log.i(access$0002, sb2.toString());
                    } else if (type == 16) {
                        this.perObjectContentControl = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("perObjectContentControl = ");
                        sb3.append(this.perObjectContentControl);
                        Log.i(access$0003, sb3.toString());
                    } else if (type != 17) {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("ContentDescPerObjectControl: Unexpected Type ");
                        sb4.append(type);
                        Log.e(access$0004, sb4.toString());
                    } else {
                        this.perObjectStatusControl = buf.getInt();
                        String access$0005 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("perObjectStatusControl = ");
                        sb5.append(this.perObjectStatusControl);
                        Log.i(access$0005, sb5.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing forContentDescPerObjectControl Notification");
                }
            }
        }
    }

    public class ContentDescriptionReq extends BaseQmiStructType {
        public QmiByte callId;
        public QmiArray<QmiInteger> parameterArray;
        public QmiArray<QmiByte> tmgi;
        public QmiInteger traceId;

        public ContentDescriptionReq(int trace, byte callId2, byte[] tmgi2, int[] parameterArray2) {
            this.traceId = new QmiInteger((long) trace);
            this.callId = new QmiByte(callId2);
            this.tmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, tmgi2);
            this.parameterArray = EmbmsOemHook.this.intArrayToQmiArray(1, parameterArray2, 2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.callId, this.tmgi, this.parameterArray};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3, 16};
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
                                StringBuilder sb = new StringBuilder();
                                sb.append("CoverageState: Unexpected Type ");
                                sb.append(type);
                                Log.e(access$000, sb.toString());
                            }
                        } else if (msgId == 8) {
                            this.code = buf.getInt();
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("response code = ");
                            sb2.append(this.code);
                            Log.i(access$0002, sb2.toString());
                        }
                        this.state = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Coverage State = ");
                        sb3.append(this.state);
                        Log.i(access$0003, sb3.toString());
                    } else {
                        this.traceId = buf.getInt();
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("traceId = ");
                        sb4.append(this.traceId);
                        Log.i(access$0004, sb4.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in CoverageState");
                }
            }
        }
    }

    public class DeliverLogPacketRequest extends BaseQmiStructType {
        public QmiArray<QmiByte> logPacket;
        public QmiInteger logPacketId;
        public QmiInteger traceId;

        public DeliverLogPacketRequest(int trace, int logPacketId2, byte[] logPacket2) {
            this.traceId = new QmiInteger((long) trace);
            this.logPacketId = new QmiInteger((long) logPacketId2);
            this.logPacket = EmbmsOemHook.this.byteArrayToQmiArray(2, logPacket2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.logPacketId, this.logPacket};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3};
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("traceId = ");
                    sb.append(this.traceId);
                    Log.i(access$000, sb.toString());
                } else if (type == 2) {
                    this.code = buf.getInt();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("code = ");
                    sb2.append(this.code);
                    Log.i(access$0002, sb2.toString());
                } else if (type != 16) {
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("DisableResponse: Unexpected Type ");
                    sb3.append(type);
                    Log.e(access$0003, sb3.toString());
                } else {
                    this.callId = buf.get();
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("callid = ");
                    sb4.append(this.callId);
                    Log.i(access$0004, sb4.toString());
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
                                StringBuilder sb = new StringBuilder();
                                sb.append("E911 State: Unexpected Type ");
                                sb.append(type);
                                Log.e(access$000, sb.toString());
                            }
                        } else if (msgId == 27) {
                            this.code = buf.getInt();
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("response code = ");
                            sb2.append(this.code);
                            Log.i(access$0002, sb2.toString());
                        }
                        this.state = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("E911 State = ");
                        sb3.append(this.state);
                        Log.i(access$0003, sb3.toString());
                    } else {
                        this.traceId = buf.getInt();
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("traceId = ");
                        sb4.append(this.traceId);
                        Log.i(access$0004, sb4.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for E911 Notification");
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type == 2 || type == 1000) {
                        byte status = buf.get();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Unsol embmsStatus received = ");
                        sb2.append(status);
                        Log.i(access$0002, sb2.toString());
                        if (status == 1) {
                            this.embmsStatus = true;
                        }
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Unsol embmsStatus = ");
                        sb3.append(this.embmsStatus);
                        Log.i(access$0003, sb3.toString());
                    } else {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("embmsStatus: Unexpected Type ");
                        sb4.append(type);
                        Log.e(access$0004, sb4.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for embmsStatus");
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("traceId = ");
                    sb.append(this.traceId);
                    Log.i(access$000, sb.toString());
                } else if (type != 2) {
                    switch (type) {
                        case 16:
                            this.callId = buf.get();
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("callid = ");
                            sb2.append(this.callId);
                            Log.i(access$0002, sb2.toString());
                            break;
                        case 17:
                            byte[] name = new byte[length];
                            for (int i = 0; i < length; i++) {
                                name[i] = buf.get();
                            }
                            this.interfaceName = new QmiString(name).toStringValue();
                            String access$0003 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("ifName = ");
                            sb3.append(this.interfaceName);
                            Log.i(access$0003, sb3.toString());
                            break;
                        case 18:
                            this.ifIndex = buf.getInt();
                            String access$0004 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("ifIndex = ");
                            sb4.append(this.ifIndex);
                            Log.i(access$0004, sb4.toString());
                            break;
                        default:
                            String access$0005 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("EnableResponse: Unexpected Type ");
                            sb5.append(type);
                            Log.e(access$0005, sb5.toString());
                            break;
                    }
                } else {
                    this.code = buf.getInt();
                    String access$0006 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("code = ");
                    sb6.append(this.code);
                    Log.i(access$0006, sb6.toString());
                }
            }
        }
    }

    public class GenericRequest extends BaseQmiStructType {
        public QmiByte callId;
        public QmiInteger traceId;

        public GenericRequest(int trace, byte callId2) {
            this.traceId = new QmiInteger((long) trace);
            this.callId = new QmiByte(callId2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.callId};
        }

        public short[] getTypes() {
            return new short[]{1, 2};
        }
    }

    public class GetInterestedTmgiResponse extends BaseQmiStructType {
        public QmiByte callId;
        public QmiArray<QmiByte> tmgiList;
        public QmiInteger traceId;

        public GetInterestedTmgiResponse(int traceId2, byte callId2, byte[] tmgiList2) {
            this.traceId = new QmiInteger((long) traceId2);
            this.callId = new QmiByte(callId2);
            this.tmgiList = EmbmsOemHook.this.tmgiListArrayToQmiArray(1, tmgiList2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.callId, this.tmgiList};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3};
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type != 2) {
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("GetPLMNListResponse: Unexpected Type ");
                        sb2.append(type);
                        Log.e(access$0002, sb2.toString());
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
                                int index3 = index + 1;
                                this.plmnList[index] = 32;
                                index = index3;
                            }
                        }
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("plmnList = ");
                        sb3.append(EmbmsOemHook.bytesToHexString(this.plmnList));
                        Log.i(access$0003, sb3.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in GetPLMNListResponse");
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("traceId = ");
                    sb.append(this.traceId);
                    Log.i(access$000, sb.toString());
                } else if (type == 2) {
                    this.state = buf.getInt();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("OOs State = ");
                    sb2.append(this.state);
                    Log.i(access$0002, sb2.toString());
                } else if (type != 3) {
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("OosState: Unexpected Type ");
                    sb3.append(type);
                    Log.e(access$0003, sb3.toString());
                } else {
                    this.list = EmbmsOemHook.this.parseTmgi(buf);
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("tmgiArray = ");
                    sb4.append(EmbmsOemHook.bytesToHexString(this.list));
                    Log.i(access$0004, sb4.toString());
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type != 2) {
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("RadioStateIndication: Unexpected Type ");
                        sb2.append(type);
                        Log.e(access$0002, sb2.toString());
                    } else {
                        this.state = buf.getInt();
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("radio = ");
                        sb3.append(this.state);
                        Log.i(access$0003, sb3.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for RadioStateIndication");
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("RequestIndication: Unexpected Type ");
                        sb.append(type);
                        Log.e(access$000, sb.toString());
                    } else {
                        this.traceId = buf.getInt();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("traceId = ");
                        sb2.append(this.traceId);
                        Log.i(access$0002, sb2.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for RequestIndication");
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type == 2) {
                        byte listLength = buf.get();
                        int[] list = new int[listLength];
                        for (int i = 0; i < listLength; i++) {
                            list[i] = buf.getInt();
                        }
                        this.campedSaiList = list;
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Camped list = ");
                        sb2.append(Arrays.toString(this.campedSaiList));
                        Log.i(access$0002, sb2.toString());
                    } else if (type == 3) {
                        byte listLength2 = buf.get();
                        int[] list2 = new int[listLength2];
                        for (int i2 = 0; i2 < listLength2; i2++) {
                            list2[i2] = buf.getInt();
                        }
                        this.numSaiPerGroupList = list2;
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Number of SAI per group list = ");
                        sb3.append(Arrays.toString(this.numSaiPerGroupList));
                        Log.i(access$0003, sb3.toString());
                    } else if (type != 4) {
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("SaiIndication: Unexpected Type ");
                        sb4.append(type);
                        Log.e(access$0004, sb4.toString());
                    } else {
                        short availableLength = buf.getShort();
                        int[] list3 = new int[availableLength];
                        for (int i3 = 0; i3 < availableLength; i3++) {
                            list3[i3] = buf.getInt();
                        }
                        this.availableSaiList = list3;
                        String access$0005 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("Available SAI list = ");
                        sb5.append(Arrays.toString(this.availableSaiList));
                        Log.i(access$0005, sb5.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for SaiIndication");
                }
            }
        }
    }

    public class SetTimeRequest extends BaseQmiStructType {
        public QmiByte sntpSuccess;
        public QmiLong timeMseconds;
        public QmiLong timeStamp;

        public SetTimeRequest(byte sntpSuccess2, long timeMseconds2, long timeStamp2) {
            this.sntpSuccess = new QmiByte(sntpSuccess2);
            this.timeMseconds = new QmiLong(timeMseconds2);
            this.timeStamp = new QmiLong(timeStamp2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.sntpSuccess, this.timeMseconds, this.timeStamp};
        }

        public short[] getTypes() {
            return new short[]{1, 16, 17};
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("Sib16Coverage: Unexpected Type ");
                        sb.append(type);
                        Log.e(access$000, sb.toString());
                    } else {
                        if (buf.get() == 1) {
                            this.inCoverage = true;
                        }
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Unsol SIB16 coverage status = ");
                        sb2.append(this.inCoverage);
                        Log.i(access$0002, sb2.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Unexpected buffer format when parsing for Sib16Coverage");
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type != 2) {
                        switch (type) {
                            case 16:
                                byte mbsfnLength = buf.get();
                                int[] mbsfnArray = new int[mbsfnLength];
                                for (int i = 0; i < mbsfnLength; i++) {
                                    mbsfnArray[i] = buf.getInt();
                                }
                                this.mbsfnAreaId = mbsfnArray;
                                String access$0002 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("MBSFN_Area_ID = ");
                                sb2.append(Arrays.toString(this.mbsfnAreaId));
                                Log.i(access$0002, sb2.toString());
                                break;
                            case 17:
                                byte snrLength = buf.get();
                                float[] snrArray = new float[snrLength];
                                for (int i2 = 0; i2 < snrLength; i2++) {
                                    snrArray[i2] = buf.getFloat();
                                }
                                this.snr = snrArray;
                                String access$0003 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("SNR = ");
                                sb3.append(Arrays.toString(this.snr));
                                Log.i(access$0003, sb3.toString());
                                break;
                            case 18:
                                byte tmgiPerMbsfnLength = buf.get();
                                float[] esnrArray = new float[tmgiPerMbsfnLength];
                                for (int i3 = 0; i3 < tmgiPerMbsfnLength; i3++) {
                                    esnrArray[i3] = buf.getFloat();
                                }
                                this.esnr = esnrArray;
                                String access$0004 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("EXCESS SNR = ");
                                sb4.append(Arrays.toString(this.esnr));
                                Log.i(access$0004, sb4.toString());
                                break;
                            case 19:
                                byte tmgiPerMbsfnLength2 = buf.get();
                                int[] tmgiPerMbsfnArray = new int[tmgiPerMbsfnLength2];
                                for (int i4 = 0; i4 < tmgiPerMbsfnLength2; i4++) {
                                    tmgiPerMbsfnArray[i4] = buf.getInt();
                                }
                                this.tmgiPerMbsfn = tmgiPerMbsfnArray;
                                String access$0005 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("NUMBER OF TMGI PER MBSFN = ");
                                sb5.append(Arrays.toString(this.tmgiPerMbsfn));
                                Log.i(access$0005, sb5.toString());
                                break;
                            case 20:
                                this.tmgilist = EmbmsOemHook.this.parseActiveTmgi(buf);
                                String access$0006 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append("tmgiArray = ");
                                sb6.append(EmbmsOemHook.bytesToHexString(this.tmgilist));
                                Log.i(access$0006, sb6.toString());
                                break;
                            default:
                                String access$0007 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append("SigStrengthResponse: Unexpected Type ");
                                sb7.append(type);
                                Log.e(access$0007, sb7.toString());
                                break;
                        }
                    } else {
                        this.code = buf.getInt();
                        String access$0008 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("code = ");
                        sb8.append(this.code);
                        Log.i(access$0008, sb8.toString());
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("State = ");
                    sb.append(this.state);
                    Log.i(access$000, sb.toString());
                } else if (type == 2) {
                    byte[] address = new byte[length];
                    for (int i = 0; i < length; i++) {
                        address[i] = buf.get();
                    }
                    this.ipAddress = new QmiString(address).toString();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("ip Address = ");
                    sb2.append(this.ipAddress);
                    Log.i(access$0002, sb2.toString());
                } else if (type != 3) {
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("StateChangeInfo: Unexpected Type ");
                    sb3.append(type);
                    Log.e(access$0003, sb3.toString());
                } else {
                    this.ifIndex = buf.getInt();
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("index = ");
                    sb4.append(this.ifIndex);
                    Log.i(access$0004, sb4.toString());
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
                        StringBuilder sb = new StringBuilder();
                        sb.append("traceId = ");
                        sb.append(this.traceId);
                        Log.i(access$000, sb.toString());
                    } else if (type == 2) {
                        this.code = buf.getInt();
                        String access$0002 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("code = ");
                        sb2.append(this.code);
                        Log.i(access$0002, sb2.toString());
                    } else if (type != 3) {
                        switch (type) {
                            case 16:
                                this.additionalInfo = true;
                                if (buf.get() == 1) {
                                    this.dayLightSaving = true;
                                }
                                String access$0003 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("dayLightSaving = ");
                                sb3.append(this.dayLightSaving);
                                Log.i(access$0003, sb3.toString());
                                break;
                            case 17:
                                this.additionalInfo = true;
                                this.leapSeconds = buf.get();
                                String access$0004 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("leapSeconds = ");
                                sb4.append(this.leapSeconds);
                                Log.i(access$0004, sb4.toString());
                                break;
                            case 18:
                                this.additionalInfo = true;
                                this.localTimeOffset = (long) buf.get();
                                String access$0005 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("localTimeOffset = ");
                                sb5.append(this.localTimeOffset);
                                Log.i(access$0005, sb5.toString());
                                break;
                            default:
                                String access$0006 = EmbmsOemHook.LOG_TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append("TimeResponse: Unexpected Type ");
                                sb6.append(type);
                                Log.e(access$0006, sb6.toString());
                                break;
                        }
                    } else {
                        this.timeMseconds = buf.getLong();
                        String access$0007 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("timeMseconds = ");
                        sb7.append(this.timeMseconds);
                        Log.i(access$0007, sb7.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in TimeResponse");
                }
            }
            String access$0008 = EmbmsOemHook.LOG_TAG;
            StringBuilder sb8 = new StringBuilder();
            sb8.append("additionalInfo = ");
            sb8.append(this.additionalInfo);
            Log.i(access$0008, sb8.toString());
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
            StringBuilder sb = new StringBuilder();
            sb.append("TimeResponse: traceId = ");
            sb.append(this.traceId);
            sb.append(" code = ");
            sb.append(this.code);
            sb.append(" timeMseconds = ");
            sb.append(this.timeMseconds);
            sb.append("additionalInfo = ");
            sb.append(this.additionalInfo);
            sb.append(" localTimeOffset = ");
            sb.append(this.localTimeOffset);
            sb.append(" dayLightSaving = ");
            sb.append(this.dayLightSaving);
            sb.append(" leapSeconds = ");
            sb.append(this.leapSeconds);
            Log.i(access$000, sb.toString());
        }
    }

    public class TmgiActivateRequest extends BaseQmiStructType {
        public QmiByte callId;
        public QmiArray<QmiInteger> earfcnList;
        public QmiInteger priority;
        public QmiArray<QmiInteger> saiList;
        public QmiArray<QmiByte> tmgi;
        public QmiInteger traceId;

        public TmgiActivateRequest(int trace, byte callId2, byte[] tmgi2, int priority2, int[] saiList2, int[] earfcnList2) {
            this.traceId = new QmiInteger((long) trace);
            this.callId = new QmiByte(callId2);
            this.priority = new QmiInteger((long) priority2);
            this.tmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, tmgi2);
            this.saiList = EmbmsOemHook.this.intArrayToQmiArray(1, saiList2);
            this.earfcnList = EmbmsOemHook.this.intArrayToQmiArray(1, earfcnList2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.callId, this.tmgi, this.priority, this.saiList, this.earfcnList};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3, 4, 16, EmbmsOemHook.EMBMSHOOK_MSG_ID_GET_ACTIVE};
        }
    }

    public class TmgiDeActivateRequest extends BaseQmiStructType {
        public QmiByte callId;
        public QmiArray<QmiByte> tmgi;
        public QmiInteger traceId;

        public TmgiDeActivateRequest(int trace, byte[] tmgi2, byte callId2) {
            this.traceId = new QmiInteger((long) trace);
            this.tmgi = EmbmsOemHook.this.byteArrayToQmiArray(1, tmgi2);
            this.callId = new QmiByte(callId2);
        }

        public BaseQmiItemType[] getItems() {
            return new BaseQmiItemType[]{this.traceId, this.callId, this.tmgi};
        }

        public short[] getTypes() {
            return new short[]{1, 2, 3};
        }
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
                            StringBuilder sb = new StringBuilder();
                            sb.append("response code = ");
                            sb.append(this.code);
                            Log.i(access$000, sb.toString());
                        } else if (type != 16) {
                            String access$0002 = EmbmsOemHook.LOG_TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("TmgiListIndication: Unexpected Type ");
                            sb2.append(type);
                            Log.e(access$0002, sb2.toString());
                        }
                        this.list = EmbmsOemHook.this.parseTmgi(buf);
                        String access$0003 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("tmgiArray = ");
                        sb3.append(EmbmsOemHook.bytesToHexString(this.list));
                        Log.i(access$0003, sb3.toString());
                    } else {
                        this.traceId = buf.getInt();
                        String access$0004 = EmbmsOemHook.LOG_TAG;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("traceId = ");
                        sb4.append(this.traceId);
                        Log.i(access$0004, sb4.toString());
                    }
                } catch (BufferUnderflowException e) {
                    Log.e(EmbmsOemHook.LOG_TAG, "Invalid format of byte buffer received in TmgiListIndication");
                }
            }
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("traceId = ");
                    sb.append(this.traceId);
                    Log.i(access$000, sb.toString());
                } else if (type == 2) {
                    this.code = buf.getInt();
                    String access$0002 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("code = ");
                    sb2.append(this.code);
                    Log.i(access$0002, sb2.toString());
                } else if (type == 16) {
                    byte tmgiLength = buf.get();
                    String access$0003 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("callid = ");
                    sb3.append(tmgiLength);
                    Log.i(access$0003, sb3.toString());
                } else if (type != 17) {
                    String access$0004 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("TmgiResponse: Unexpected Type ");
                    sb4.append(type);
                    Log.e(access$0004, sb4.toString());
                } else {
                    byte tmgiLength2 = buf.get();
                    byte[] tmgi2 = new byte[tmgiLength2];
                    for (int i = 0; i < tmgiLength2; i++) {
                        tmgi2[i] = buf.get();
                    }
                    this.tmgi = tmgi2;
                    String access$0005 = EmbmsOemHook.LOG_TAG;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("tmgi = ");
                    sb5.append(EmbmsOemHook.bytesToHexString(this.tmgi));
                    Log.i(access$0005, sb5.toString());
                }
            }
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

    private EmbmsOemHook(Context context) {
        Log.v(LOG_TAG, "EmbmsOemHook ()");
        this.mQmiOemHook = QmiOemHook.getInstance(context);
        QmiOemHook.registerService(2, this, 1);
        QmiOemHook.registerOnReadyCb(this, 2, null);
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
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("dispose mRefCount = ");
            sb.append(mRefCount);
            Log.v(str, sb.toString());
        }
    }

    public void handleMessage(Message msg) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("received message : ");
        sb.append(msg.what);
        Log.i(str, sb.toString());
        AsyncResult ar = (AsyncResult) msg.obj;
        int i = msg.what;
        if (i == 1) {
            HashMap<Integer, Object> map = (HashMap) ar.result;
            if (map == null) {
                Log.e(LOG_TAG, "Hashmap async userobj is NULL");
                return;
            }
            handleResponse(map);
        } else if (i != 2) {
            String str2 = LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unexpected message received from QmiOemHook what = ");
            sb2.append(msg.what);
            Log.e(str2, sb2.toString());
        } else {
            notifyUnsol(4097, ar.result, 0);
        }
    }

    private void handleResponse(HashMap<Integer, Object> map) {
        HashMap<Integer, Object> hashMap = map;
        short msgId = ((Short) hashMap.get(Integer.valueOf(8))).shortValue();
        int responseSize = ((Integer) hashMap.get(Integer.valueOf(2))).intValue();
        int successStatus = ((Integer) hashMap.get(Integer.valueOf(3))).intValue();
        Message msg = (Message) hashMap.get(Integer.valueOf(4));
        int phoneId = ((Integer) hashMap.get(Integer.valueOf(9))).intValue();
        if (msg != null) {
            msg.arg1 = phoneId;
        }
        ByteBuffer respByteBuf = (ByteBuffer) hashMap.get(Integer.valueOf(6));
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(" responseSize=");
        sb.append(responseSize);
        sb.append(" successStatus=");
        sb.append(successStatus);
        sb.append("phoneId: ");
        sb.append(phoneId);
        Log.d(str, sb.toString());
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
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Error received in EMBMSHOOK_MSG_ID_GET_AVAILABLE: ");
                sb2.append(successStatus);
                Log.e(str2, sb2.toString());
                return;
            case 5:
            case 12:
                if (msgId != 5 || successStatus == 0) {
                    notifyUnsol(2, new TmgiListIndication(respByteBuf, msgId), phoneId);
                    return;
                }
                String str3 = LOG_TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error received in EMBMSHOOK_MSG_ID_GET_ACTIVE: ");
                sb3.append(successStatus);
                Log.e(str3, sb3.toString());
                return;
            case 8:
            case 13:
                if (msgId != 8 || successStatus == 0) {
                    notifyUnsol(3, new CoverageState(respByteBuf, msgId), phoneId);
                    return;
                }
                String str4 = LOG_TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Error received in EMBMSHOOK_MSG_ID_GET_COVERAGE: ");
                sb4.append(successStatus);
                Log.e(str4, sb4.toString());
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
                StringBuilder sb5 = new StringBuilder();
                sb5.append(" deliverLogPacket response successStatus=");
                sb5.append(successStatus);
                Log.v(str5, sb5.toString());
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
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Error received in EMBMSHOOK_MSG_ID_GET_SIB16_COVERAGE: ");
                sb6.append(successStatus);
                Log.e(str6, sb6.toString());
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
                StringBuilder sb7 = new StringBuilder();
                sb7.append(" contentDescription response successStatus=");
                sb7.append(successStatus);
                Log.v(str7, sb7.toString());
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
                StringBuilder sb8 = new StringBuilder();
                sb8.append(" getInterestedTmgiListResponse ack successStatus=");
                sb8.append(successStatus);
                Log.v(str8, sb8.toString());
                return;
            default:
                String str9 = LOG_TAG;
                StringBuilder sb9 = new StringBuilder();
                sb9.append("received unexpected msgId ");
                sb9.append(msgId);
                Log.e(str9, sb9.toString());
                return;
        }
    }

    private void notifyUnsol(int type, Object payload, int phoneId) {
        AsyncResult ar = new AsyncResult(null, new UnsolObject(type, payload, phoneId), null);
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Notifying registrants type = ");
        sb.append(type);
        Log.i(str, sb.toString());
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
            StringBuilder sb = new StringBuilder();
            sb.append("enable called on PhoneId: ");
            sb.append(phoneId);
            Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("activateTmgi called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
        TmgiActivateRequest tmgiActivateRequest = new TmgiActivateRequest(traceId, callId, tmgi, priority, saiList, earfcnList);
        TmgiActivateRequest req = tmgiActivateRequest;
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
        StringBuilder sb = new StringBuilder();
        sb.append("deactivateTmgi called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("actDeactTmgi called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
        ActDeactRequest actDeactRequest = new ActDeactRequest(traceId, callId, actTmgi, deActTmgi, priority, saiList, earfcnList);
        ActDeactRequest req = actDeactRequest;
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
        StringBuilder sb = new StringBuilder();
        sb.append("getAvailableTMGIList called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getActiveTMGIList called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getCoverageState called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getSignalStrength called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("disable called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getActiveLogPacketIDs called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("deliverLogPacket called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
            String str = "0123456789abcdef";
            ret.append(str.charAt((bytes[i] >> 4) & 15));
            ret.append(str.charAt(bytes[i] & 15));
        }
        return ret.toString();
    }

    public int getTime(int traceId, Message msg, int phoneId) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getTime called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getSib16CoverageStatus called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getEmbmsStatus called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setTime called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
        if (sntpSuccess) {
            success = 1;
        } else {
            success = 0;
        }
        String str2 = LOG_TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("setTime success = ");
        sb2.append(success);
        sb2.append(" timeMseconds = ");
        sb2.append(timeMseconds);
        sb2.append(" timeStamp = ");
        sb2.append(timeStamp);
        Log.i(str2, sb2.toString());
        SetTimeRequest setTimeRequest = new SetTimeRequest(success, timeMseconds, timeStamp);
        SetTimeRequest req = setTimeRequest;
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
        StringBuilder sb = new StringBuilder();
        sb.append("getE911State called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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
        String str;
        int i = numberOfParameter;
        int[] parameterCode2 = parameterCode;
        int[] parameterValue2 = parameterValue;
        try {
            String str2 = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("contentDescription called on PhoneId: ");
            try {
                sb.append(phoneId);
                Log.i(str2, sb.toString());
                str = " parameterValue = ";
                if (parameterCode2 == null || parameterValue2 == null) {
                    String str3 = LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("contentDescription: either parameterCode or parameterValue is nullparameterCode = ");
                    sb2.append(parameterCode2);
                    sb2.append(str);
                    sb2.append(parameterValue2);
                    Log.i(str3, sb2.toString());
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
                        String str4 = LOG_TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("contentDescription: parameterArray: ");
                        sb3.append(Arrays.toString(parameterArray));
                        Log.i(str4, sb3.toString());
                        ContentDescriptionReq contentDescriptionReq = new ContentDescriptionReq(traceId, callId, tmgi, parameterArray);
                        ContentDescriptionReq req = contentDescriptionReq;
                        this.mQmiOemHook.sendQmiMessageAsync(2, (short) EMBMSHOOK_MSG_ID_CONTENT_DESCRIPTION, req.getTypes(), req.getItems(), msg, phoneId);
                        return 0;
                    }
                }
                String str5 = LOG_TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("contentDescription: Invalid input, numberOfParameter = ");
                sb4.append(i);
                sb4.append(" parameterCode = ");
                sb4.append(parameterCode2);
                sb4.append(str);
                sb4.append(parameterValue2);
                Log.e(str5, sb4.toString());
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
        StringBuilder sb = new StringBuilder();
        sb.append("getPLMNListRequest called on PhoneId: ");
        sb.append(phoneId);
        Log.i(str, sb.toString());
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

    /* access modifiers changed from: private */
    public byte[] parseTmgi(ByteBuffer buf) {
        int index = 0;
        byte totalTmgis = buf.get();
        byte[] tmgi = new byte[(totalTmgis * TLV_TYPE_ACTDEACTIVATE_REQ_EARFCN_LIST)];
        for (int i = 0; i < totalTmgis; i++) {
            byte tmgiLength = buf.get();
            int j = 0;
            while (j < tmgiLength) {
                int index2 = index + 1;
                tmgi[index] = buf.get();
                j++;
                index = index2;
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
                int index2 = index + 1;
                tmgi[index] = buf.get();
                j++;
                index = index2;
            }
        }
        return tmgi;
    }

    /* access modifiers changed from: private */
    public QmiArray<QmiByte> byteArrayToQmiArray(short vSize, byte[] arr) {
        QmiByte[] qmiByteArray = new QmiByte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            qmiByteArray[i] = new QmiByte(arr[i]);
        }
        return new QmiArray<>((T[]) qmiByteArray, QmiByte.class, vSize);
    }

    /* access modifiers changed from: private */
    public QmiArray<QmiByte> tmgiListArrayToQmiArray(short vSize, byte[] tmgiList) {
        int length = tmgiList == null ? 0 : tmgiList.length;
        int numOfTmgi = length / 6;
        QmiByte[] qmiByteArray = new QmiByte[(length + (numOfTmgi * 1))];
        int index = 0;
        int i = 0;
        while (i < numOfTmgi) {
            int index2 = index + 1;
            qmiByteArray[index] = new QmiByte(6);
            int j = i * 6;
            while (j < (i + 1) * 6) {
                int index3 = index2 + 1;
                qmiByteArray[index2] = new QmiByte(tmgiList[j]);
                j++;
                index2 = index3;
            }
            i++;
            index = index2;
        }
        return new QmiArray<>(qmiByteArray, QmiByte.class, vSize, 7);
    }

    /* access modifiers changed from: private */
    public QmiArray<QmiInteger> intArrayToQmiArray(short vSize, int[] arr) {
        int length = arr == null ? 0 : arr.length;
        QmiInteger[] qmiIntArray = new QmiInteger[length];
        for (int i = 0; i < length; i++) {
            qmiIntArray[i] = new QmiInteger((long) arr[i]);
        }
        return new QmiArray<>((T[]) qmiIntArray, QmiInteger.class, vSize);
    }

    /* access modifiers changed from: private */
    public QmiArray<QmiInteger> intArrayToQmiArray(short vSize, int[] arr, short numOfElements) {
        int length = arr == null ? 0 : arr.length;
        QmiInteger[] qmiIntArray = new QmiInteger[length];
        for (int i = 0; i < length; i++) {
            qmiIntArray[i] = new QmiInteger((long) arr[i]);
        }
        return new QmiArray<>(qmiIntArray, QmiInteger.class, vSize, numOfElements);
    }
}
