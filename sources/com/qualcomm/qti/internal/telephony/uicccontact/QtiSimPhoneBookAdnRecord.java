package com.qualcomm.qti.internal.telephony.uicccontact;

import android.text.TextUtils;
import java.util.Arrays;

public class QtiSimPhoneBookAdnRecord {
    static final String LOG_TAG = "QtiSimPhoneBookAdnRecord";
    public int mAdNumCount = 0;
    public String[] mAdNumbers = null;
    public String mAlphaTag = null;
    public int mEmailCount = 0;
    public String[] mEmails = null;
    public String mNumber = null;
    public int mRecordIndex = 0;

    public int getRecordIndex() {
        return this.mRecordIndex;
    }

    public String getAlphaTag() {
        return this.mAlphaTag;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public int getNumEmails() {
        return this.mEmailCount;
    }

    public String[] getEmails() {
        return this.mEmails;
    }

    public int getNumAdNumbers() {
        return this.mAdNumCount;
    }

    public String[] getAdNumbers() {
        return this.mAdNumbers;
    }

    public static String ConvertToPhoneNumber(String input) {
        if (input == null) {
            return null;
        }
        return input.replace('e', ';').replace('T', ',').replace('?', 'N');
    }

    public static String ConvertToRecordNumber(String input) {
        if (input == null) {
            return null;
        }
        return input.replace(';', 'e').replace(',', 'T').replace('N', '?');
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.mAlphaTag) && TextUtils.isEmpty(this.mNumber) && this.mEmails == null && this.mAdNumbers == null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimPhoneBookAdnRecord{");
        sb.append("index =");
        sb.append(this.mRecordIndex);
        sb.append(", name = ");
        String str = this.mAlphaTag;
        if (str == null) {
            str = "null";
        }
        sb.append(str);
        sb.append(", number = ");
        String str2 = this.mNumber;
        if (str2 == null) {
            str2 = "null";
        }
        sb.append(str2);
        sb.append(", email count = ");
        sb.append(this.mEmailCount);
        sb.append(", email = ");
        sb.append(Arrays.toString(this.mEmails));
        sb.append(", ad number count = ");
        sb.append(this.mAdNumCount);
        sb.append(", ad number = ");
        sb.append(Arrays.toString(this.mAdNumbers));
        sb.append("}");
        return sb.toString();
    }
}
