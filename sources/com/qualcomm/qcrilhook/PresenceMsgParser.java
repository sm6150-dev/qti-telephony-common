package com.qualcomm.qcrilhook;

import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PresenceMsgParser {
    /* access modifiers changed from: private */
    public static String LOG_TAG = "PresenceMsgParser";

    enum MediaCapabilities {
        FULL_DUPLEX,
        HALF_RECEIVE_ONLY,
        HALF_SEND_ONLY
    }

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
            return "ContactInfo [listHeaderInfo=" + this.listHeaderInfo + ", mResourceUri=" + this.mResourceUri + ", mResourceId=" + this.mResourceId + ", mResourceState=" + this.mResourceState + ", mResourceReason=" + this.mResourceReason + ", mResourceCid=" + this.mResourceCid + ", mDescription=" + this.mDescription + ", mVersion=" + this.mVersion + ", mServiceId=" + this.mServiceId + ", mContactUri=" + this.mContactUri + ", mIsVolteContact=" + this.mIsVolteContact + ", mPublishStatus=" + this.mPublishStatus + ", mIsAudioSupported=" + this.mIsAudioSupported + ", mIsVideoSupported=" + this.mIsVideoSupported + ", mAudioCapabilities=" + this.mAudioCapabilities + ", mVideoCapabilities=" + this.mVideoCapabilities + ", mTimeStamp=" + this.mTimeStamp + "]";
        }
    }

    public static class ListHeaderInfo {
        public String mListContactUri;
        public String mListFullState;
        public String mListName;
        public String mListVersion;

        public String toString() {
            return "ListHeaderInfo [mListContactUri=" + this.mListContactUri + ", mListName=" + this.mListName + ", mListVersion=" + this.mListVersion + ", mListFullState=" + this.mListFullState + "]";
        }
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
            Log.d(access$000, "Parsing ListContactUri = " + s);
        }

        private void parseListName() {
            String s = parseString(parseByte());
            this.listHeaderInfo.mListName = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ListName = " + s);
        }

        private void parseListVersion() {
            int listVersion = parseInteger();
            ListHeaderInfo listHeaderInfo2 = this.listHeaderInfo;
            listHeaderInfo2.mListVersion = "" + listVersion;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ListVersion = " + listVersion);
        }

        private void parseListFullState() {
            int b = parseByte();
            ListHeaderInfo listHeaderInfo2 = this.listHeaderInfo;
            listHeaderInfo2.mListFullState = "" + b;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ListFullState = " + b);
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
            Log.d(access$000, "Parsing ResourceUri = " + s);
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
            Log.d(access$000, "Parsing IsVolteContact = " + this.c.mIsVolteContact);
        }

        private void parsePublishStatus() {
            int val = parseInteger();
            this.c.mPublishStatus = val;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing PublishStatus = " + val);
        }

        private void parseResourceId() {
            String s = parseString(parseByte());
            this.c.mResourceId = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ResourceId = " + s);
        }

        private void parseResourceState() {
            String s = parseString(parseByte());
            this.c.mResourceState = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ResourceState = " + s);
        }

        private void parseResourceReason() {
            String s = parseString(parseByte());
            this.c.mResourceReason = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ResourceReason = " + s);
        }

        private void parseResourceCid() {
            String s = parseString(parseShort());
            this.c.mResourceCid = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ResourceCid = " + s);
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
            Log.d(access$000, "Parsing Contact Uri = " + s);
        }

        private void parseDescription() {
            String s = parseString(parseByte());
            this.c.mDescription = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing Description = " + s);
        }

        private void parseVersion() {
            String s = parseString(parseByte());
            this.c.mVersion = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing Version = " + s);
        }

        private void parseServiceid() {
            String s = parseString(parseByte());
            this.c.mServiceId = s;
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing ServiceId = " + s);
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
            Log.d(access$000, "Parsing isAudioSupported=" + this.c.mIsAudioSupported);
        }

        private void parseAudioCapability() {
            int val = parseInteger();
            this.c.mAudioCapabilities = MediaCapabilities.values()[val].toString();
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing AudioCapabilities=" + this.c.mAudioCapabilities);
        }

        private void parseVideoCapability() {
            int val = parseInteger();
            this.c.mVideoCapabilities = MediaCapabilities.values()[val].toString();
            String access$000 = PresenceMsgParser.LOG_TAG;
            Log.d(access$000, "Parsing VideoCapabilities=" + this.c.mVideoCapabilities);
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
            Log.d(access$000, "Parsing isVideoSupported=" + this.c.mIsVideoSupported);
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
            Log.d(access$000, "Parsing timeStamp=" + this.c.mTimeStamp);
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
            Log.d(access$000, "Parsing numOfContacts = " + numOfContacts);
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
        Log.d(str, "notifyUpdate(), Thread=" + Thread.currentThread().getName());
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
                    Log.v(str2, "callId = " + callId);
                }
                responseSize -= length + 3;
            } else {
                Log.v(LOG_TAG, "NOTIFY_DETAIL_TYPE");
                ArrayList<ContactInfo> parsedContactList = new PresenceRichNotifyParser(respByteBuf, length).parseRichInfo();
                String str3 = LOG_TAG;
                Log.d(str3, "parsed contact info " + parsedContactList);
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
        int len = (short) PrimitiveParser.toUnsigned(respByteBuf.getShort());
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
