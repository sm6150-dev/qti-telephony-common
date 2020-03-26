package com.qualcomm.qcrilhook;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.qualcomm.qcrilhook.BaseQmiTypes;
import com.qualcomm.qcrilhook.PresenceMsgBuilder;
import com.qualcomm.qcrilhook.QmiOemHookConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import org.codeaurora.telephony.utils.AsyncResult;

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
        PresenceOemHook presenceOemHook = mInstance;
        if (presenceOemHook == null) {
            mInstance = new PresenceOemHook(context, listenerHandler.getLooper());
            QmiOemHook.registerService(PRESENCE_SERVICE_ID, listenerHandler, 1);
            Log.v(LOG_TAG, "Registered PresenceOemHook with QmiOemHook");
        } else {
            presenceOemHook.mContext = context;
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
            Log.v(LOG_TAG, "dispose mRefCount = " + mRefCount);
        }
    }

    public Object imsp_get_enabler_state_req() {
        PresenceMsgBuilder.NoTlvPayloadRequest req = new PresenceMsgBuilder.NoTlvPayloadRequest();
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 36, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_publish_req(int publish_status, String contact_uri, String description, String ver, String service_id, int is_audio_supported, int audio_capability, int is_video_supported, int video_capability) {
        PresenceMsgBuilder.Publish.PublishStructRequest req = new PresenceMsgBuilder.Publish.PublishStructRequest(publish_status, contact_uri, description, ver, service_id, is_audio_supported, audio_capability, is_video_supported, video_capability);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 37, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_publish_xml_req(String xml) {
        PresenceMsgBuilder.Publish.PublishXMLRequest req = new PresenceMsgBuilder.Publish.PublishXMLRequest(xml);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 38, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_unpublish_req() {
        new PresenceMsgBuilder.UnPublish.UnPublishRequest();
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 39, (short[]) null, (BaseQmiTypes.BaseQmiItemType[]) null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_subscribe_req(SubscriptionType subscriptionType, ArrayList<String> contactList) {
        PresenceMsgBuilder.Subscribe.SubscribeStructRequest req = new PresenceMsgBuilder.Subscribe.SubscribeStructRequest(subscriptionType, contactList);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 40, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_subscribe_xml_req(String xml) {
        PresenceMsgBuilder.Subscribe.SubscribeXMLRequest req = new PresenceMsgBuilder.Subscribe.SubscribeXMLRequest(xml);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 41, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer imsp_send_unsubscribe_req(String peerURI) {
        PresenceMsgBuilder.UnSubscribe.UnSubscribeRequest req = new PresenceMsgBuilder.UnSubscribe.UnSubscribeRequest(peerURI);
        try {
            return (Integer) receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 42, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_set_notify_fmt_req(int flag) {
        PresenceMsgBuilder.NotifyFmt.SetFmt req = new PresenceMsgBuilder.NotifyFmt.SetFmt((short) flag);
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 43, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_get_notify_fmt_req() {
        new PresenceMsgBuilder.NoTlvPayloadRequest();
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 44, (short[]) null, (BaseQmiTypes.BaseQmiItemType[]) null));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_set_event_report_req(int mask) {
        PresenceMsgBuilder.EventReport.SetEventReport req = new PresenceMsgBuilder.EventReport.SetEventReport(mask);
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 45, req.getTypes(), req.getItems()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object imsp_get_event_report_req() {
        new PresenceMsgBuilder.NoTlvPayloadRequest();
        try {
            return receive(this.mQmiOemHook.sendQmiMessageSync((short) PRESENCE_SERVICE_ID, 46, (short[]) null, (BaseQmiTypes.BaseQmiItemType[]) null));
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
                String xml = LOG_TAG;
                Log.v(xml, "Response: QCRILHOOK_PRESENCE_IMS_UNSOL_PUBLISH_TRIGGER=" + successStatus);
                int val = PresenceMsgParser.parsePublishTrigger(respByteBuf);
                PresenceUnsolIndication ind = new PresenceUnsolIndication();
                ind.oemHookMesgId = 32;
                ind.obj = Integer.valueOf(val);
                String str3 = LOG_TAG;
                Log.v(str3, "Response: QCRILHOOK_PRESENCE_IMS_UNSOL_PUBLISH_TRIGGER result=" + successStatus + " publish_trigger=" + val);
                return ind;
            case 33:
                String str4 = LOG_TAG;
                Log.v(str4, "Ind: QCRILHOOK_PRESENCE_IMS_UNSOL_NOTIFY_XML_UPDATE=" + successStatus);
                String xml2 = PresenceMsgParser.parseNotifyUpdateXML(respByteBuf);
                PresenceUnsolIndication presenceUnSolInd = new PresenceUnsolIndication();
                presenceUnSolInd.oemHookMesgId = 33;
                presenceUnSolInd.obj = xml2;
                return presenceUnSolInd;
            case 34:
                String str5 = LOG_TAG;
                Log.v(str5, "Ind: QCRILHOOK_PRESENCE_IMS_UNSOL_NOTIFY_UPDATE=" + successStatus);
                PresenceUnsolIndication presenceUnSolInd2 = new PresenceUnsolIndication();
                presenceUnSolInd2.oemHookMesgId = 34;
                presenceUnSolInd2.obj = PresenceMsgParser.parseNotifyUpdate(respByteBuf, responseSize, successStatus);
                return presenceUnSolInd2;
            case 35:
                String str6 = LOG_TAG;
                Log.v(str6, "Response: QCRILHOOK_PRESENCE_IMS_UNSOL_ENABLER_STATE=" + successStatus);
                int val2 = PresenceMsgParser.parseEnablerStateInd(respByteBuf);
                PresenceUnsolIndication ind2 = new PresenceUnsolIndication();
                ind2.oemHookMesgId = 35;
                ind2.obj = Integer.valueOf(val2);
                String str7 = LOG_TAG;
                Log.v(str7, "Response: QCRILHOOK_PRESENCE_IMS_UNSOL_ENABLER_STATE result=" + successStatus + " enabler_state=" + val2);
                return ind2;
            case 36:
                PresenceSolResponse presenceSolResp = new PresenceSolResponse();
                if (successStatus == 0) {
                    int enablerState = PresenceMsgParser.parseEnablerState(respByteBuf);
                    String str8 = LOG_TAG;
                    Log.v(str8, "Enabler state = " + enablerState);
                    presenceSolResp.result = successStatus;
                    presenceSolResp.data = Integer.valueOf(enablerState);
                    Object returnObject2 = presenceSolResp;
                    String state = IMS_ENABLER_RESPONSE[enablerState];
                    String str9 = LOG_TAG;
                    Log.v(str9, "Response: QCRILHOOK_PRESENCE_IMS_ENABLER_STATE_REQ=" + state);
                    return returnObject2;
                }
                String state2 = LOG_TAG;
                Log.v(state2, "OemHookError: QCRILHOOK_PRESENCE_IMS_ENABLER_STATE_REQ=" + successStatus);
                presenceSolResp.result = successStatus;
                presenceSolResp.data = 0;
                return presenceSolResp;
            case 37:
                String str10 = LOG_TAG;
                Log.v(str10, "Response: QCRILHOOK_PRESENCE_IMS_SEND_PUBLISH_REQ=" + successStatus);
                return returnObject;
            case 38:
                String str11 = LOG_TAG;
                Log.v(str11, "Response: QCRILHOOK_PRESENCE_IMS_SEND_PUBLISH_XML_REQ=" + successStatus);
                return returnObject;
            case 39:
                String str12 = LOG_TAG;
                Log.v(str12, "Response: QCRILHOOK_PRESENCE_IMS_SEND_UNPUBLISH_REQ=" + successStatus);
                return returnObject;
            case 40:
                String str13 = LOG_TAG;
                Log.v(str13, "Response: QCRILHOOK_PRESENCE_IMS_SEND_SUBSCRIBE_REQ=" + successStatus);
                return returnObject;
            case 41:
                String str14 = LOG_TAG;
                Log.v(str14, "Response: QCRILHOOK_PRESENCE_IMS_SEND_SUBSCRIBE_XML_REQ=" + successStatus);
                return returnObject;
            case 42:
                String str15 = LOG_TAG;
                Log.v(str15, "Response: QCRILHOOK_PRESENCE_IMS_SEND_UNSUBSCRIBE_REQ=" + successStatus);
                return returnObject;
            case 43:
                String str16 = LOG_TAG;
                Log.v(str16, "Response: QCRILHOOK_PRESENCE_IMS_SET_NOTIFY_FMT_REQ=" + successStatus);
                PresenceSolResponse presenceSolResp2 = new PresenceSolResponse();
                presenceSolResp2.result = successStatus;
                presenceSolResp2.data = -1;
                return presenceSolResp2;
            case 44:
                String str17 = LOG_TAG;
                Log.v(str17, "Response: QCRILHOOK_PRESENCE_IMS_GET_NOTIFY_FMT_REQ=" + successStatus);
                PresenceSolResponse presenceSolResp3 = new PresenceSolResponse();
                if (successStatus == 0) {
                    int val3 = PresenceMsgParser.parseGetNotifyReq(respByteBuf);
                    presenceSolResp3.result = successStatus;
                    presenceSolResp3.data = Integer.valueOf(val3);
                    String str18 = LOG_TAG;
                    Log.v(str18, "Response: QCRILHOOK_PRESENCE_IMS_GET_NOTIFY_FMT_REQ update_with_struct_info=" + val3);
                } else {
                    presenceSolResp3.result = successStatus;
                    presenceSolResp3.data = -1;
                }
                return presenceSolResp3;
            case 45:
                String str19 = LOG_TAG;
                Log.v(str19, "Response: QCRILHOOK_PRESENCE_IMS_SET_EVENT_REPORT_REQ=" + successStatus);
                PresenceSolResponse presenceSolResp4 = new PresenceSolResponse();
                presenceSolResp4.result = successStatus;
                presenceSolResp4.data = -1;
                return presenceSolResp4;
            case 46:
                String str20 = LOG_TAG;
                Log.v(str20, "Response: QCRILHOOK_PRESENCE_IMS_GET_EVENT_REPORT_REQ=" + successStatus);
                PresenceSolResponse presenceSolResp5 = new PresenceSolResponse();
                if (successStatus == 0) {
                    int val4 = PresenceMsgParser.parseGetEventReport(respByteBuf);
                    presenceSolResp5.result = successStatus;
                    presenceSolResp5.data = Integer.valueOf(val4);
                    String str21 = LOG_TAG;
                    Log.v(str21, "Response: QCRILHOOK_PRESENCE_IMS_GET_EVENT_REPORT_REQ event_report_bit_masks=" + val4);
                } else {
                    presenceSolResp5.result = successStatus;
                    presenceSolResp5.data = -1;
                }
                return presenceSolResp5;
            default:
                return returnObject;
        }
    }

    public static Object handleMessage(Message msg) {
        if (msg.what != 1) {
            String str = LOG_TAG;
            Log.d(str, "Recieved msg.what=" + msg.what);
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
