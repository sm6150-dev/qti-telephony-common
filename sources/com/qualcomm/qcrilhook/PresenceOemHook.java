package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes.BaseQmiItemType;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class PresenceOemHook {
    public static final String[] IMS_ENABLER_RESPONSE = {"UNKNOWN", "UNINITIALIZED", "INITIALIZED", "AIRPLANE", "REGISTERED"};
    private static String LOG_TAG = "PresenceOemHook";
    public static final int OEM_HOOK_UNSOL_IND = 1;
    private static final short PRESENCE_SERVICE_ID = 3;
    public static final short QCRILHOOK_PRESENCE_IMS_ENABLER_STATE_REQ = 36;
    public static final short QCRILHOOK_PRESENCE_IMS_GET_EVENT_REPORT_REQ = 46;
    public static final short QCRILHOOK_PRESENCE_IMS_GET_NOTIFY_FMT_REQ = 44;
    public static final short QCRILHOOK_PRESENCE_IMS_SEND_PUBLISH_REQ = 37;
    public static final short QCRILHOOK_PRESENCE_IMS_SEND_PUBLISH_XML_REQ = 38;
    public static final short QCRILHOOK_PRESENCE_IMS_SEND_SUBSCRIBE_REQ = 40;
    public static final short QCRILHOOK_PRESENCE_IMS_SEND_SUBSCRIBE_XML_REQ = 41;
    public static final short QCRILHOOK_PRESENCE_IMS_SEND_UNPUBLISH_REQ = 39;
    public static final short QCRILHOOK_PRESENCE_IMS_SEND_UNSUBSCRIBE_REQ = 42;
    public static final short QCRILHOOK_PRESENCE_IMS_SET_EVENT_REPORT_REQ = 45;
    public static final short QCRILHOOK_PRESENCE_IMS_SET_NOTIFY_FMT_REQ = 43;
    public static final short QCRILHOOK_PRESENCE_IMS_UNSOL_ENABLER_STATE = 35;
    public static final short QCRILHOOK_PRESENCE_IMS_UNSOL_NOTIFY_UPDATE = 34;
    public static final short QCRILHOOK_PRESENCE_IMS_UNSOL_NOTIFY_XML_UPDATE = 33;
    public static final short QCRILHOOK_PRESENCE_IMS_UNSOL_PUBLISH_TRIGGER = 32;
    private static PresenceOemHook mInstance;
    private static int mRefCount = 0;
    Context mContext;
    private QmiOemHook mQmiOemHook;

    public static class PresenceSolResponse {
        public Object data;
        public int result;
    }

    public static class PresenceUnsolIndication {
        public Object obj;
        public int oemHookMesgId;
    }

    public enum SubscriptionType {
        NONE,
        SIMPLE,
        POLLING
    }

    private PresenceOemHook(Context context, Looper listenerLooper) {
        this.mContext = context;
        this.mQmiOemHook = QmiOemHook.getInstance(context, listenerLooper);
    }

    public static PresenceOemHook getInstance(Context context, Handler listenerHandler) {
        if (mInstance == null) {
            mInstance = new PresenceOemHook(context, listenerHandler.getLooper());
            QmiOemHook.registerService(PRESENCE_SERVICE_ID, listenerHandler, 1);
            Log.v(LOG_TAG, "Registered PresenceOemHook with QmiOemHook");
        } else {
            mInstance.mContext = context;
        }
        mRefCount++;
        return mInstance;
    }

    public synchronized void dispose() {
        mRefCount--;
        if (mRefCount == 0) {
            Log.v(LOG_TAG, "dispose(): Unregistering service");
            QmiOemHook.unregisterService(3);
            this.mQmiOemHook.dispose();
            mInstance = null;
        } else {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("dispose mRefCount = ");
            sb.append(mRefCount);
            Log.v(str, sb.toString());
        }
    }

    public Object imsp_get_enabler_state_req() {
        NoTlvPayloadRequest req = new NoTlvPayloadRequest();
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 36, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_publish_req(int publish_status, String contact_uri, String description, String ver, String service_id, int is_audio_supported, int audio_capability, int is_video_supported, int video_capability) {
        PublishStructRequest publishStructRequest = new PublishStructRequest(publish_status, contact_uri, description, ver, service_id, is_audio_supported, audio_capability, is_video_supported, video_capability);
        PublishStructRequest req = publishStructRequest;
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 37, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_publish_xml_req(String xml) {
        PublishXMLRequest req = new PublishXMLRequest(xml);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 38, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_unpublish_req() {
        new UnPublishRequest();
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 39, (short[]) null, (BaseQmiItemType[]) null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_subscribe_req(SubscriptionType subscriptionType, ArrayList<String> contactList) {
        SubscribeStructRequest req = new SubscribeStructRequest(subscriptionType, contactList);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 40, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_subscribe_xml_req(String xml) {
        SubscribeXMLRequest req = new SubscribeXMLRequest(xml);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 41, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_unsubscribe_req(String peerURI) {
        UnSubscribeRequest req = new UnSubscribeRequest(peerURI);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 42, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_set_notify_fmt_req(int flag) {
        SetFmt req = new SetFmt((short) flag);
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 43, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_get_notify_fmt_req() {
        new NoTlvPayloadRequest();
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 44, (short[]) null, (BaseQmiItemType[]) null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_set_event_report_req(int mask) {
        SetEventReport req = new SetEventReport(mask);
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 45, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_get_event_report_req() {
        new NoTlvPayloadRequest();
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 46, (short[]) null, (BaseQmiItemType[]) null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
                String xml = LOG_TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Response: QCRILHOOK_PRESENCE_IMS_UNSOL_PUBLISH_TRIGGER=");
                sb3.append(successStatus);
                Log.v(xml, sb3.toString());
                int val = PresenceMsgParser.parsePublishTrigger(respByteBuf);
                PresenceUnsolIndication ind = new PresenceUnsolIndication();
                ind.oemHookMesgId = 32;
                ind.obj = Integer.valueOf(val);
                String str3 = LOG_TAG;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Response: QCRILHOOK_PRESENCE_IMS_UNSOL_PUBLISH_TRIGGER result=");
                sb4.append(successStatus);
                sb4.append(" publish_trigger=");
                sb4.append(val);
                Log.v(str3, sb4.toString());
                returnObject = ind;
                break;
            case 33:
                String str4 = LOG_TAG;
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Ind: QCRILHOOK_PRESENCE_IMS_UNSOL_NOTIFY_XML_UPDATE=");
                sb5.append(successStatus);
                Log.v(str4, sb5.toString());
                String xml2 = PresenceMsgParser.parseNotifyUpdateXML(respByteBuf);
                PresenceUnsolIndication presenceUnSolInd = new PresenceUnsolIndication();
                presenceUnSolInd.oemHookMesgId = 33;
                presenceUnSolInd.obj = xml2;
                returnObject = presenceUnSolInd;
                break;
            case 34:
                String str5 = LOG_TAG;
                StringBuilder sb6 = new StringBuilder();
                sb6.append("Ind: QCRILHOOK_PRESENCE_IMS_UNSOL_NOTIFY_UPDATE=");
                sb6.append(successStatus);
                Log.v(str5, sb6.toString());
                PresenceUnsolIndication presenceUnSolInd2 = new PresenceUnsolIndication();
                presenceUnSolInd2.oemHookMesgId = 34;
                presenceUnSolInd2.obj = PresenceMsgParser.parseNotifyUpdate(respByteBuf, responseSize, successStatus);
                returnObject = presenceUnSolInd2;
                break;
            case 35:
                String str6 = LOG_TAG;
                StringBuilder sb7 = new StringBuilder();
                sb7.append("Response: QCRILHOOK_PRESENCE_IMS_UNSOL_ENABLER_STATE=");
                sb7.append(successStatus);
                Log.v(str6, sb7.toString());
                int val2 = PresenceMsgParser.parseEnablerStateInd(respByteBuf);
                PresenceUnsolIndication ind2 = new PresenceUnsolIndication();
                ind2.oemHookMesgId = 35;
                ind2.obj = Integer.valueOf(val2);
                String str7 = LOG_TAG;
                StringBuilder sb8 = new StringBuilder();
                sb8.append("Response: QCRILHOOK_PRESENCE_IMS_UNSOL_ENABLER_STATE result=");
                sb8.append(successStatus);
                sb8.append(" enabler_state=");
                sb8.append(val2);
                Log.v(str7, sb8.toString());
                returnObject = ind2;
                break;
            case 36:
                PresenceSolResponse presenceSolResp = new PresenceSolResponse();
                if (successStatus == 0) {
                    int enablerState = PresenceMsgParser.parseEnablerState(respByteBuf);
                    String str8 = LOG_TAG;
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append("Enabler state = ");
                    sb9.append(enablerState);
                    Log.v(str8, sb9.toString());
                    presenceSolResp.result = successStatus;
                    presenceSolResp.data = Integer.valueOf(enablerState);
                    returnObject = presenceSolResp;
                    String state = IMS_ENABLER_RESPONSE[enablerState];
                    String str9 = LOG_TAG;
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append("Response: QCRILHOOK_PRESENCE_IMS_ENABLER_STATE_REQ=");
                    sb10.append(state);
                    Log.v(str9, sb10.toString());
                    break;
                } else {
                    String state2 = LOG_TAG;
                    StringBuilder sb11 = new StringBuilder();
                    sb11.append("OemHookError: QCRILHOOK_PRESENCE_IMS_ENABLER_STATE_REQ=");
                    sb11.append(successStatus);
                    Log.v(state2, sb11.toString());
                    presenceSolResp.result = successStatus;
                    presenceSolResp.data = Integer.valueOf(0);
                    return presenceSolResp;
                }
            case 37:
                String str10 = LOG_TAG;
                StringBuilder sb12 = new StringBuilder();
                sb12.append("Response: QCRILHOOK_PRESENCE_IMS_SEND_PUBLISH_REQ=");
                sb12.append(successStatus);
                Log.v(str10, sb12.toString());
                break;
            case 38:
                String str11 = LOG_TAG;
                StringBuilder sb13 = new StringBuilder();
                sb13.append("Response: QCRILHOOK_PRESENCE_IMS_SEND_PUBLISH_XML_REQ=");
                sb13.append(successStatus);
                Log.v(str11, sb13.toString());
                break;
            case 39:
                String str12 = LOG_TAG;
                StringBuilder sb14 = new StringBuilder();
                sb14.append("Response: QCRILHOOK_PRESENCE_IMS_SEND_UNPUBLISH_REQ=");
                sb14.append(successStatus);
                Log.v(str12, sb14.toString());
                break;
            case 40:
                String str13 = LOG_TAG;
                StringBuilder sb15 = new StringBuilder();
                sb15.append("Response: QCRILHOOK_PRESENCE_IMS_SEND_SUBSCRIBE_REQ=");
                sb15.append(successStatus);
                Log.v(str13, sb15.toString());
                break;
            case 41:
                String str14 = LOG_TAG;
                StringBuilder sb16 = new StringBuilder();
                sb16.append("Response: QCRILHOOK_PRESENCE_IMS_SEND_SUBSCRIBE_XML_REQ=");
                sb16.append(successStatus);
                Log.v(str14, sb16.toString());
                break;
            case 42:
                String str15 = LOG_TAG;
                StringBuilder sb17 = new StringBuilder();
                sb17.append("Response: QCRILHOOK_PRESENCE_IMS_SEND_UNSUBSCRIBE_REQ=");
                sb17.append(successStatus);
                Log.v(str15, sb17.toString());
                break;
            case 43:
                String str16 = LOG_TAG;
                StringBuilder sb18 = new StringBuilder();
                sb18.append("Response: QCRILHOOK_PRESENCE_IMS_SET_NOTIFY_FMT_REQ=");
                sb18.append(successStatus);
                Log.v(str16, sb18.toString());
                PresenceSolResponse presenceSolResp2 = new PresenceSolResponse();
                presenceSolResp2.result = successStatus;
                presenceSolResp2.data = Integer.valueOf(-1);
                returnObject = presenceSolResp2;
                break;
            case 44:
                String str17 = LOG_TAG;
                StringBuilder sb19 = new StringBuilder();
                sb19.append("Response: QCRILHOOK_PRESENCE_IMS_GET_NOTIFY_FMT_REQ=");
                sb19.append(successStatus);
                Log.v(str17, sb19.toString());
                PresenceSolResponse presenceSolResp3 = new PresenceSolResponse();
                if (successStatus == 0) {
                    int val3 = PresenceMsgParser.parseGetNotifyReq(respByteBuf);
                    presenceSolResp3.result = successStatus;
                    presenceSolResp3.data = Integer.valueOf(val3);
                    String str18 = LOG_TAG;
                    StringBuilder sb20 = new StringBuilder();
                    sb20.append("Response: QCRILHOOK_PRESENCE_IMS_GET_NOTIFY_FMT_REQ update_with_struct_info=");
                    sb20.append(val3);
                    Log.v(str18, sb20.toString());
                } else {
                    presenceSolResp3.result = successStatus;
                    presenceSolResp3.data = Integer.valueOf(-1);
                }
                returnObject = presenceSolResp3;
                break;
            case 45:
                String str19 = LOG_TAG;
                StringBuilder sb21 = new StringBuilder();
                sb21.append("Response: QCRILHOOK_PRESENCE_IMS_SET_EVENT_REPORT_REQ=");
                sb21.append(successStatus);
                Log.v(str19, sb21.toString());
                PresenceSolResponse presenceSolResp4 = new PresenceSolResponse();
                presenceSolResp4.result = successStatus;
                presenceSolResp4.data = Integer.valueOf(-1);
                returnObject = presenceSolResp4;
                break;
            case 46:
                String str20 = LOG_TAG;
                StringBuilder sb22 = new StringBuilder();
                sb22.append("Response: QCRILHOOK_PRESENCE_IMS_GET_EVENT_REPORT_REQ=");
                sb22.append(successStatus);
                Log.v(str20, sb22.toString());
                PresenceSolResponse presenceSolResp5 = new PresenceSolResponse();
                if (successStatus == 0) {
                    int val4 = PresenceMsgParser.parseGetEventReport(respByteBuf);
                    presenceSolResp5.result = successStatus;
                    presenceSolResp5.data = Integer.valueOf(val4);
                    String str21 = LOG_TAG;
                    StringBuilder sb23 = new StringBuilder();
                    sb23.append("Response: QCRILHOOK_PRESENCE_IMS_GET_EVENT_REPORT_REQ event_report_bit_masks=");
                    sb23.append(val4);
                    Log.v(str21, sb23.toString());
                } else {
                    presenceSolResp5.result = successStatus;
                    presenceSolResp5.data = Integer.valueOf(-1);
                }
                returnObject = presenceSolResp5;
                break;
        }
        return returnObject;
    }

    public static Object handleMessage(Message msg) {
        if (msg.what != 1) {
            String str = LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Recieved msg.what=");
            sb.append(msg.what);
            Log.d(str, sb.toString());
            return null;
        }
        HashMap<Integer, Object> map = (HashMap) ((AsyncResult) msg.obj).result;
        if (map != null) {
            return receive(map);
        }
        Log.e(LOG_TAG, "Hashmap async userobj is NULL");
        return null;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        Log.v(LOG_TAG, "finalize() hit");
    }
}
