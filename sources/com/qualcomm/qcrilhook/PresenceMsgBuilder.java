package com.qualcomm.qcrilhook;

import com.qualcomm.qcrilhook.BaseQmiTypes;
import com.qualcomm.qcrilhook.PresenceOemHook;
import com.qualcomm.qcrilhook.QmiPrimitiveTypes;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

public class PresenceMsgBuilder {

    static class NoTlvPayloadRequest extends BaseQmiTypes.BaseQmiStructType {
        public static final short IMS_ENABLER_REQ_TYPE = 1;
        QmiPrimitiveTypes.QmiNull noParam = null;

        public BaseQmiTypes.BaseQmiItemType[] getItems() {
            return new BaseQmiTypes.BaseQmiItemType[]{this.noParam};
        }

        public short[] getTypes() {
            return new short[]{1};
        }
    }

    static class Subscribe {
        Subscribe() {
        }

        static class Imsp_user_uri_struct extends BaseQmiTypes.BaseQmiItemType {
            ArrayList<QmiPrimitiveTypes.QmiString> imsp_user_uri = new ArrayList<>();
            ArrayList<QmiPrimitiveTypes.QmiByte> imsp_user_uri_len = new ArrayList<>();
            int mCompleteLen = 0;
            ArrayList<String> mContactList;
            int mNum;

            public Imsp_user_uri_struct(ArrayList<String> contactList) {
                this.mNum = contactList.size();
                this.mContactList = contactList;
                Iterator<String> it = contactList.iterator();
                while (it.hasNext()) {
                    String s = it.next();
                    int len = s.length();
                    this.mCompleteLen += len;
                    this.imsp_user_uri_len.add(new QmiPrimitiveTypes.QmiByte(len));
                    this.imsp_user_uri.add(new QmiPrimitiveTypes.QmiString(s));
                }
            }

            public byte[] toByteArray() {
                ByteBuffer tempBuf = createByteBuffer(getSize());
                for (int i = 0; i < this.mNum; i++) {
                    tempBuf.put(this.imsp_user_uri_len.get(i).toByteArray());
                    tempBuf.put(this.imsp_user_uri.get(i).toByteArray());
                }
                return tempBuf.array();
            }

            public int getSize() {
                return this.mCompleteLen + this.mNum;
            }

            public byte[] toTlv(short type) throws InvalidParameterException {
                ByteBuffer buf = createByteBuffer(getSize());
                buf.put(toByteArray());
                return buf.array();
            }

            public String toString() {
                String temp = "";
                for (int i = 0; i < this.mNum; i++) {
                    temp = temp + String.format("[Contact[%d]_%s]", new Object[]{Integer.valueOf(i), this.imsp_user_uri.get(i).toString()});
                }
                return temp;
            }
        }

        static class Imsp_user_info_struct extends BaseQmiTypes.BaseQmiItemType {
            Imsp_user_uri_struct imsp_user_uri;
            QmiPrimitiveTypes.QmiByte subscribe_user_list_len;

            public Imsp_user_info_struct(ArrayList<String> contactList) {
                this.subscribe_user_list_len = new QmiPrimitiveTypes.QmiByte(contactList.size());
                this.imsp_user_uri = new Imsp_user_uri_struct(contactList);
            }

            public byte[] toByteArray() {
                ByteBuffer tempBuf = createByteBuffer(getSize());
                tempBuf.put(this.subscribe_user_list_len.toByteArray());
                tempBuf.put(this.imsp_user_uri.toByteArray());
                return tempBuf.array();
            }

            public int getSize() {
                return this.subscribe_user_list_len.getSize() + this.imsp_user_uri.getSize();
            }

            public byte[] toTlv(short type) throws InvalidParameterException {
                ByteBuffer buf = createByteBuffer(getSize() + 3);
                try {
                    buf.put(PrimitiveParser.parseByte(type));
                    buf.putShort(PrimitiveParser.parseShort(getSize()));
                    buf.put(toByteArray());
                    return buf.array();
                } catch (NumberFormatException e) {
                    throw new InvalidParameterException(e.toString());
                }
            }

            public String toString() {
                return String.format("[subscribe_user_list_len_%s], [imsp_user_uri=%s]", new Object[]{this.subscribe_user_list_len.toString(), this.imsp_user_uri.toString()});
            }
        }

        static class SubscribeStructRequest extends BaseQmiTypes.BaseQmiStructType {
            public static final short IMSP_SUBSCRIPTION_TYPE = 1;
            public static final short IMSP_USER_INFO = 2;
            Imsp_user_info_struct mUserInfo;
            QmiPrimitiveTypes.QmiInteger subscriptionType;

            public SubscribeStructRequest(PresenceOemHook.SubscriptionType subscriptionType2, ArrayList<String> contactList) {
                this.subscriptionType = new QmiPrimitiveTypes.QmiInteger((long) subscriptionType2.ordinal());
                this.mUserInfo = new Imsp_user_info_struct(contactList);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.subscriptionType, this.mUserInfo};
            }

            public short[] getTypes() {
                return new short[]{1, 2};
            }
        }

        static class SubscribeXMLRequest extends BaseQmiTypes.BaseQmiStructType {
            public static final short SUBSCRIBE_XML_TYPE = 1;
            QmiPrimitiveTypes.QmiString subscribeXml;

            public SubscribeXMLRequest() {
            }

            public SubscribeXMLRequest(String xml) {
                this.subscribeXml = new QmiPrimitiveTypes.QmiString(xml);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.subscribeXml};
            }

            public short[] getTypes() {
                return new short[]{1};
            }
        }
    }

    static class UnSubscribe {
        UnSubscribe() {
        }

        static class UnSubscribeRequest extends BaseQmiTypes.BaseQmiStructType {
            public static final short PEER_URI_TYPE = 1;
            QmiPrimitiveTypes.QmiString peerURI;

            public UnSubscribeRequest(String peerURI2) {
                this.peerURI = new QmiPrimitiveTypes.QmiString(peerURI2);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.peerURI};
            }

            public short[] getTypes() {
                return new short[]{1};
            }
        }
    }

    static class Publish {
        Publish() {
        }

        static class Imsp_presence_service_description_struct extends BaseQmiTypes.BaseQmiItemType {
            QmiPrimitiveTypes.QmiString mDescription;
            QmiPrimitiveTypes.QmiString mService_id;
            QmiPrimitiveTypes.QmiString mVer;

            public Imsp_presence_service_description_struct(String description, String ver, String service_id) {
                this.mDescription = new QmiPrimitiveTypes.QmiString(description);
                this.mVer = new QmiPrimitiveTypes.QmiString(ver);
                this.mService_id = new QmiPrimitiveTypes.QmiString(service_id);
            }

            public byte[] toByteArray() {
                ByteBuffer tempBuf = createByteBuffer(getSize());
                tempBuf.put((byte) this.mDescription.getSize());
                tempBuf.put(this.mDescription.toByteArray());
                tempBuf.put((byte) this.mVer.getSize());
                tempBuf.put(this.mVer.toByteArray());
                tempBuf.put((byte) this.mService_id.getSize());
                tempBuf.put(this.mService_id.toByteArray());
                return tempBuf.array();
            }

            public int getSize() {
                return this.mDescription.getSize() + 1 + this.mVer.getSize() + 1 + this.mService_id.getSize() + 1;
            }

            public byte[] toTlv(short type) throws InvalidParameterException {
                ByteBuffer buf = createByteBuffer(getSize() + 2);
                buf.putShort(PrimitiveParser.parseShort(getSize()));
                buf.put(toByteArray());
                return buf.array();
            }

            public String toString() {
                return String.format("[mDescription_%s],[mVer_%s], [mService_id_%s]", new Object[]{this.mDescription.toString(), this.mVer.toString(), this.mService_id.toString()});
            }
        }

        static class Imsp_presence_service_capabilities_struct extends BaseQmiTypes.BaseQmiItemType {
            QmiPrimitiveTypes.QmiInteger mAudio_capability;
            QmiPrimitiveTypes.QmiByte mIs_audio_supported;
            QmiPrimitiveTypes.QmiByte mIs_video_supported;
            QmiPrimitiveTypes.QmiInteger mVideo_capability;

            public Imsp_presence_service_capabilities_struct(int is_audio_supported, int audio_capability, int is_video_supported, int video_capability) {
                this.mIs_audio_supported = new QmiPrimitiveTypes.QmiByte(is_audio_supported);
                this.mAudio_capability = new QmiPrimitiveTypes.QmiInteger((long) audio_capability);
                this.mIs_video_supported = new QmiPrimitiveTypes.QmiByte(is_video_supported);
                this.mVideo_capability = new QmiPrimitiveTypes.QmiInteger((long) video_capability);
            }

            public byte[] toByteArray() {
                ByteBuffer tempBuf = createByteBuffer(getSize());
                tempBuf.put(this.mIs_audio_supported.toByteArray());
                tempBuf.put(this.mAudio_capability.toByteArray());
                tempBuf.put(this.mIs_video_supported.toByteArray());
                tempBuf.put(this.mVideo_capability.toByteArray());
                return tempBuf.array();
            }

            public int getSize() {
                return this.mIs_audio_supported.getSize() + this.mAudio_capability.getSize() + this.mIs_video_supported.getSize() + this.mVideo_capability.getSize();
            }

            public byte[] toTlv(short type) throws InvalidParameterException {
                ByteBuffer buf = createByteBuffer(getSize() + 2);
                buf.putShort(PrimitiveParser.parseShort(getSize()));
                buf.put(toByteArray());
                return buf.array();
            }

            public String toString() {
                return String.format("[mIs_audio_supported_%s], [mAudio_capability_%s], [mIs_video_supported_%s], [mVideo_capability_%s]", new Object[]{this.mIs_audio_supported.toString(), this.mAudio_capability.toString(), this.mIs_video_supported.toString(), this.mVideo_capability.toString()});
            }
        }

        static class Imsp_presence_info_struct extends BaseQmiTypes.BaseQmiItemType {
            QmiPrimitiveTypes.QmiString mContact_uri;
            Imsp_presence_service_capabilities_struct mService_capabilities;
            Imsp_presence_service_description_struct mService_descriptions;

            public Imsp_presence_info_struct(String contact_uri, String description, String ver, String service_id, int is_audio_supported, int audio_capability, int is_video_supported, int video_capability) {
                this.mContact_uri = new QmiPrimitiveTypes.QmiString(contact_uri);
                this.mService_descriptions = new Imsp_presence_service_description_struct(description, ver, service_id);
                this.mService_capabilities = new Imsp_presence_service_capabilities_struct(is_audio_supported, audio_capability, is_video_supported, video_capability);
            }

            public byte[] toByteArray() {
                ByteBuffer tempBuf = createByteBuffer(getSize());
                tempBuf.put((byte) this.mContact_uri.getSize());
                tempBuf.put(this.mContact_uri.toByteArray());
                tempBuf.put(this.mService_descriptions.toByteArray());
                tempBuf.put(this.mService_capabilities.toByteArray());
                return tempBuf.array();
            }

            public int getSize() {
                return this.mContact_uri.getSize() + 1 + this.mService_descriptions.getSize() + this.mService_capabilities.getSize();
            }

            public String toString() {
                return String.format("[mContact_uri_%s], [mService_descriptions=%s], [mService_capabilities=%s] ", new Object[]{this.mContact_uri.toString(), this.mService_descriptions.toString(), this.mService_capabilities.toString()});
            }
        }

        static class PublishStructRequest extends BaseQmiTypes.BaseQmiStructType {
            public static final short PUBLISH_PRESENCE_INFO_TYPE = 16;
            public static final short PUBLISH_STATUS_TYPE = 1;
            Imsp_presence_info_struct mPresence_info;
            QmiPrimitiveTypes.QmiInteger mPublish_status;

            public PublishStructRequest() {
            }

            public PublishStructRequest(int publish_status, String contact_uri, String description, String ver, String service_id, int is_audio_supported, int audio_capability, int is_video_supported, int video_capability) {
                this.mPublish_status = new QmiPrimitiveTypes.QmiInteger((long) publish_status);
                this.mPresence_info = new Imsp_presence_info_struct(contact_uri, description, ver, service_id, is_audio_supported, audio_capability, is_video_supported, video_capability);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.mPublish_status, this.mPresence_info};
            }

            public short[] getTypes() {
                return new short[]{1, 16};
            }
        }

        static class PublishXMLRequest extends BaseQmiTypes.BaseQmiStructType {
            public static final short PUBLISH_XML_TYPE = 1;
            QmiPrimitiveTypes.QmiString publishXml;

            public PublishXMLRequest() {
            }

            public PublishXMLRequest(String xml) {
                this.publishXml = new QmiPrimitiveTypes.QmiString(xml);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.publishXml};
            }

            public short[] getTypes() {
                return new short[]{1};
            }
        }
    }

    static class UnPublish {
        UnPublish() {
        }

        static class UnPublishRequest extends BaseQmiTypes.BaseQmiStructType {
            public static final short UNPUBLISH_REQ_TYPE = 1;
            QmiPrimitiveTypes.QmiNull noParam = new QmiPrimitiveTypes.QmiNull();

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.noParam};
            }

            public short[] getTypes() {
                return new short[]{1};
            }
        }
    }

    static class NotifyFmt {
        NotifyFmt() {
        }

        static class SetFmt extends BaseQmiTypes.BaseQmiStructType {
            public static final short UPDATE_WITH_STRUCT_INFO_TYPE = 1;
            QmiPrimitiveTypes.QmiByte mUpdate_with_struct_info;

            public SetFmt() {
            }

            public SetFmt(short flag) {
                this.mUpdate_with_struct_info = new QmiPrimitiveTypes.QmiByte(flag);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.mUpdate_with_struct_info};
            }

            public short[] getTypes() {
                return new short[]{1};
            }
        }
    }

    static class EventReport {
        EventReport() {
        }

        static class EventReportMaskStruct extends BaseQmiTypes.BaseQmiItemType {
            QmiPrimitiveTypes.QmiByte mMask;

            public EventReportMaskStruct(int mask) {
                this.mMask = new QmiPrimitiveTypes.QmiByte(mask);
            }

            public byte[] toByteArray() {
                ByteBuffer tempBuf = createByteBuffer(getSize());
                tempBuf.put(this.mMask.toByteArray());
                return tempBuf.array();
            }

            public int getSize() {
                return 8;
            }

            public byte[] toTlv(short type) throws InvalidParameterException {
                ByteBuffer buf = createByteBuffer(getSize() + 3);
                try {
                    buf.put(PrimitiveParser.parseByte(type));
                    buf.putShort(PrimitiveParser.parseShort(getSize()));
                    buf.put(toByteArray());
                    return buf.array();
                } catch (NumberFormatException e) {
                    throw new InvalidParameterException(e.toString());
                }
            }

            public String toString() {
                return String.format("[mMask_%s]", new Object[]{this.mMask});
            }
        }

        static class SetEventReport extends BaseQmiTypes.BaseQmiStructType {
            public static final short EVENT_REPORT_MASK_TYPE = 1;
            EventReportMaskStruct mask;

            public SetEventReport() {
            }

            public SetEventReport(int mask2) {
                this.mask = new EventReportMaskStruct(mask2);
            }

            public BaseQmiTypes.BaseQmiItemType[] getItems() {
                return new BaseQmiTypes.BaseQmiItemType[]{this.mask};
            }

            public short[] getTypes() {
                return new short[]{1};
            }
        }
    }
}
