package com.qualcomm.qcrilhook;

import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PresenceMsgParser {
    /* access modifiers changed from: private */
    public static String LOG_TAG = "PresenceMsgParser";

    public static class ContactInfo {
        public ListHeaderInfo listHeaderInfo;
        public String mAudioCapabilities;
        public String mContactUri;
        public String mDescription;
        public boolean mIsAudioSupported;
        public boolean mIsVideoSupported;
        public boolean mIsVolteContact;
        public int mPublishStatus;
        public String mResourceCid;
        public String mResourceId;
        public String mResourceReason;
        public String mResourceState;
        public String mResourceUri;
        public String mServiceId;
        public String mTimeStamp;
        public String mVersion;
        public String mVideoCapabilities;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ContactInfo [listHeaderInfo=");
            sb.append(this.listHeaderInfo);
            sb.append(", mResourceUri=");
            sb.append(this.mResourceUri);
            sb.append(", mResourceId=");
            sb.append(this.mResourceId);
            sb.append(", mResourceState=");
            sb.append(this.mResourceState);
            sb.append(", mResourceReason=");
            sb.append(this.mResourceReason);
            sb.append(", mResourceCid=");
            sb.append(this.mResourceCid);
            sb.append(", mDescription=");
            sb.append(this.mDescription);
            sb.append(", mVersion=");
            sb.append(this.mVersion);
            sb.append(", mServiceId=");
            sb.append(this.mServiceId);
            sb.append(", mContactUri=");
            sb.append(this.mContactUri);
            sb.append(", mIsVolteContact=");
            sb.append(this.mIsVolteContact);
            sb.append(", mPublishStatus=");
            sb.append(this.mPublishStatus);
            sb.append(", mIsAudioSupported=");
            sb.append(this.mIsAudioSupported);
            sb.append(", mIsVideoSupported=");
            sb.append(this.mIsVideoSupported);
            sb.append(", mAudioCapabilities=");
            sb.append(this.mAudioCapabilities);
            sb.append(", mVideoCapabilities=");
            sb.append(this.mVideoCapabilities);
            sb.append(", mTimeStamp=");
            sb.append(this.mTimeStamp);
            sb.append("]");
            return sb.toString();
        }
    }

    public static class ListHeaderInfo {
        public String mListContactUri;
        public String mListFullState;
        public String mListName;
        public String mListVersion;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ListHeaderInfo [mListContactUri=");
            sb.append(this.mListContactUri);
            sb.append(", mListName=");
            sb.append(this.mListName);
            sb.append(", mListVersion=");
            sb.append(this.mListVersion);
            sb.append(", mListFullState=");
            sb.append(this.mListFullState);
            sb.append("]");
            return sb.toString();
        }
    }

    enum MediaCapabilities {
        FULL_DUPLEX,
        HALF_RECEIVE_ONLY,
        HALF_SEND_ONLY
    }

    static class PresenceRichNotifyParser {
        private ContactInfo c;
        private ListHeaderInfo listHeaderInfo;
        private ArrayList<ContactInfo> parsedContactList;
        private ByteBuffer respByteBuf;
        private int totalBytes;

        public PresenceRichNotifyParser(ByteBuffer respByteBuf2, int n) {
            this.respByteBuf = respByteBuf2;
            this.totalBytes = n;
        }

        private String parseString(int n) {
            int STRING_LENGTH = n;
            if (this.respByteBuf.remaining() < STRING_LENGTH) {
                new Exception().printStackTrace();
                return "";
            }
            byte[] data = new byte[STRING_LENGTH];
            for (int i = 0; i < STRING_LENGTH; i++) {
                data[i] = this.respByteBuf.get();
            }
            return new String(data);
        }

        private int parseInteger() {
            if (this.respByteBuf.remaining() < 4) {
                new Exception().printStackTrace();
                return 0;
            }
            byte[] data = new byte[4];
            for (int i = 0; i < 4; i++) {
                data[i] = this.respByteBuf.get();
            }
            return PrimitiveParser.toUnsigned(data[0]);
        }

        private int parseShort() {
            if (this.respByteBuf.remaining() < 2) {
                new Exception().printStackTrace();
                return 0;
            }
            byte[] data = new byte[2];
            for (int i = 0; i < 2; i++) {
                data[i] = this.respByteBuf.get();
            }
            return PrimitiveParser.toUnsigned(data[0]);
        }

        private int parseByte() {
            if (this.respByteBuf.remaining() < 1) {
                new Exception().printStackTrace();
                return 0;
            }
            return PrimitiveParser.toUnsigned(new byte[]{this.respByteBuf.get()}[0]);
        }

        private void parseListContactUri() {
            String s = parseString(parseByte());
            this.listHeaderInfo.mListContactUri = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ListContactUri = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseListName() {
            String s = parseString(parseByte());
            this.listHeaderInfo.mListName = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ListName = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseListVersion() {
            int listVersion = parseInteger();
            ListHeaderInfo listHeaderInfo2 = this.listHeaderInfo;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(listVersion);
            listHeaderInfo2.mListVersion = sb.toString();
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Parsing ListVersion = ");
            sb2.append(listVersion);
            Log.d(access$000, sb2.toString());
        }

        private void parseListFullState() {
            int b = parseByte();
            ListHeaderInfo listHeaderInfo2 = this.listHeaderInfo;
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(b);
            listHeaderInfo2.mListFullState = sb.toString();
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Parsing ListFullState = ");
            sb2.append(b);
            Log.d(access$000, sb2.toString());
        }

        private void parseListInfo() {
            parseListContactUri();
            parseListName();
            parseListVersion();
            parseListFullState();
        }

        private void parseResourceUri() {
            String s = parseString(parseByte());
            this.c.mResourceUri = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ResourceUri = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseIsVolteContact() {
            int val = parseByte();
            ContactInfo contactInfo = this.c;
            boolean z = true;
            if (val != 1) {
                z = false;
            }
            contactInfo.mIsVolteContact = z;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing IsVolteContact = ");
            sb.append(this.c.mIsVolteContact);
            Log.d(access$000, sb.toString());
        }

        private void parsePublishStatus() {
            int val = parseInteger();
            this.c.mPublishStatus = val;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing PublishStatus = ");
            sb.append(val);
            Log.d(access$000, sb.toString());
        }

        private void parseResourceId() {
            String s = parseString(parseByte());
            this.c.mResourceId = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ResourceId = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseResourceState() {
            String s = parseString(parseByte());
            this.c.mResourceState = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ResourceState = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseResourceReason() {
            String s = parseString(parseByte());
            this.c.mResourceReason = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ResourceReason = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseResourceCid() {
            String s = parseString(parseShort());
            this.c.mResourceCid = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ResourceCid = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseResouceInstance() {
            parseResourceId();
            parseResourceState();
            parseResourceReason();
            parseResourceCid();
        }

        private void parseContactUri() {
            String s = parseString(parseByte());
            this.c.mContactUri = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing Contact Uri = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseDescription() {
            String s = parseString(parseByte());
            this.c.mDescription = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing Description = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseVersion() {
            String s = parseString(parseByte());
            this.c.mVersion = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing Version = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseServiceid() {
            String s = parseString(parseByte());
            this.c.mServiceId = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing ServiceId = ");
            sb.append(s);
            Log.d(access$000, sb.toString());
        }

        private void parseServiceDescriptions() {
            parseDescription();
            parseVersion();
            parseServiceid();
        }

        private void parseIsAudioSupported() {
            int val = parseByte();
            ContactInfo contactInfo = this.c;
            boolean z = true;
            if (val != 1) {
                z = false;
            }
            contactInfo.mIsAudioSupported = z;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing isAudioSupported=");
            sb.append(this.c.mIsAudioSupported);
            Log.d(access$000, sb.toString());
        }

        private void parseAudioCapability() {
            int val = parseInteger();
            this.c.mAudioCapabilities = MediaCapabilities.values()[val].toString();
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing AudioCapabilities=");
            sb.append(this.c.mAudioCapabilities);
            Log.d(access$000, sb.toString());
        }

        private void parseVideoCapability() {
            int val = parseInteger();
            this.c.mVideoCapabilities = MediaCapabilities.values()[val].toString();
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing VideoCapabilities=");
            sb.append(this.c.mVideoCapabilities);
            Log.d(access$000, sb.toString());
        }

        private void parseIsVideoSupported() {
            int val = parseByte();
            ContactInfo contactInfo = this.c;
            boolean z = true;
            if (val != 1) {
                z = false;
            }
            contactInfo.mIsVideoSupported = z;
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing isVideoSupported=");
            sb.append(this.c.mIsVideoSupported);
            Log.d(access$000, sb.toString());
        }

        private void parseServiceCapabilities() {
            parseIsAudioSupported();
            parseAudioCapability();
            parseIsVideoSupported();
            parseVideoCapability();
        }

        private void parsePresenceInfo() {
            parseContactUri();
            parseServiceDescriptions();
            parseServiceCapabilities();
        }

        private void parseTimeStamp() {
            this.c.mTimeStamp = parseString(parseByte());
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing timeStamp=");
            sb.append(this.c.mTimeStamp);
            Log.d(access$000, sb.toString());
        }

        private void parsePresenceUserInfoWithTs() {
            parsePresenceInfo();
            parseTimeStamp();
        }

        public int parseUserListInfoLen() {
            return parseByte();
        }

        private void parseUserListInfo() {
            int numOfContacts = parseUserListInfoLen();
            String access$000 = PresenceMsgParser.LOG_TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Parsing numOfContacts = ");
            sb.append(numOfContacts);
            Log.d(access$000, sb.toString());
            for (int i = 0; i < numOfContacts; i++) {
                this.c = new ContactInfo();
                this.c.listHeaderInfo = this.listHeaderInfo;
                parseResourceUri();
                parseIsVolteContact();
                parsePublishStatus();
                parseResouceInstance();
                parsePresenceUserInfoWithTs();
                this.parsedContactList.add(this.c);
            }
        }

        /* access modifiers changed from: private */
        public ArrayList<ContactInfo> parseRichInfo() {
            this.parsedContactList = new ArrayList<>();
            this.listHeaderInfo = new ListHeaderInfo();
            parseListInfo();
            parseUserListInfo();
            return this.parsedContactList;
        }
    }

    static ArrayList<ContactInfo> parseNotifyUpdate(ByteBuffer respByteBuf, int responseSize, int successStatus) {
        String str = LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("notifyUpdate(), Thread=");
        sb.append(Thread.currentThread().getName());
        Log.d(str, sb.toString());
        while (responseSize > 0) {
            short type = PrimitiveParser.toUnsigned(respByteBuf.get());
            int length = PrimitiveParser.toUnsigned(respByteBuf.getShort());
            if (type != 1) {
                if (type == 16) {
                    byte[] data = new byte[length];
                    for (int i = 0; i < length; i++) {
                        data[i] = respByteBuf.get();
                    }
                    int callId = PrimitiveParser.toUnsigned(data[0]);
                    String str2 = LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("callId = ");
                    sb2.append(callId);
                    Log.v(str2, sb2.toString());
                }
                responseSize -= 3 + length;
            } else {
                Log.v(LOG_TAG, "NOTIFY_DETAIL_TYPE");
                ArrayList<ContactInfo> parsedContactList = new PresenceRichNotifyParser(respByteBuf, length).parseRichInfo();
                String str3 = LOG_TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("parsed contact info ");
                sb3.append(parsedContactList);
                Log.d(str3, sb3.toString());
                return parsedContactList;
            }
        }
        return null;
    }

    static int parseEnablerState(ByteBuffer respByteBuf) {
        byte unsigned = (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
        short unsigned2 = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
        return (int) PrimitiveParser.toUnsigned(respByteBuf.getInt());
    }

    static String parseNotifyUpdateXML(ByteBuffer respByteBuf) {
        byte unsigned = (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
        short len = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            data[i] = respByteBuf.get();
        }
        return new String(data);
    }

    static int parseGetNotifyReq(ByteBuffer respByteBuf) {
        byte unsigned = (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
        short unsigned2 = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
        return (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
    }

    static int parseGetEventReport(ByteBuffer respByteBuf) {
        byte unsigned = (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
        short unsigned2 = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
        return (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
    }

    static int parsePublishTrigger(ByteBuffer respByteBuf) {
        byte unsigned = (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
        short unsigned2 = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
        return (int) PrimitiveParser.toUnsigned(respByteBuf.getInt());
    }

    static int parseEnablerStateInd(ByteBuffer respByteBuf) {
        byte unsigned = (byte) PrimitiveParser.toUnsigned(respByteBuf.get());
        short unsigned2 = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
        return (int) PrimitiveParser.toUnsigned(respByteBuf.getInt());
    }
}
