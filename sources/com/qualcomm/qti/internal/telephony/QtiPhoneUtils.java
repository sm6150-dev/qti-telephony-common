package com.qualcomm.qti.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.TelephonyManager;

public class QtiPhoneUtils {
    private static final String LOG_TAG = "QtiPhoneUtils";
    private static Context mContext;
    private static QtiPhoneUtils sInstance;

    static QtiPhoneUtils init(Context context) {
        synchronized (QtiPhoneUtils.class) {
            if (sInstance == null) {
                sInstance = new QtiPhoneUtils(context);
            }
        }
        return sInstance;
    }

    public static QtiPhoneUtils getInstance() {
        QtiPhoneUtils qtiPhoneUtils;
        synchronized (QtiPhoneUtils.class) {
            if (sInstance != null) {
                qtiPhoneUtils = sInstance;
            } else {
                throw new RuntimeException("QtiPhoneUtils was not initialized!");
            }
        }
        return qtiPhoneUtils;
    }

    private QtiPhoneUtils(Context context) {
        mContext = context;
    }

    public int getPhoneCount() {
        return ((TelephonyManager) mContext.getSystemService("phone")).getPhoneCount();
    }

    public boolean isValidPhoneId(int phoneId) {
        return phoneId >= 0 && phoneId < getPhoneCount();
    }

    public static boolean putIntAtIndex(ContentResolver cr, String name, int index, int value) {
        String data = "";
        String[] valArray = null;
        String v = Global.getString(cr, name);
        if (index == Integer.MAX_VALUE) {
            StringBuilder sb = new StringBuilder();
            sb.append("putIntAtIndex index == MAX_VALUE index=");
            sb.append(index);
            throw new IllegalArgumentException(sb.toString());
        } else if (index >= 0) {
            String str = ",";
            if (v != null) {
                valArray = v.split(str);
            }
            for (int i = 0; i < index; i++) {
                String str2 = "";
                if (valArray != null && i < valArray.length) {
                    str2 = valArray[i];
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(data);
                sb2.append(str2);
                sb2.append(str);
                data = sb2.toString();
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(data);
            sb3.append(value);
            String data2 = sb3.toString();
            if (valArray != null) {
                for (int i2 = index + 1; i2 < valArray.length; i2++) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(data2);
                    sb4.append(str);
                    sb4.append(valArray[i2]);
                    data2 = sb4.toString();
                }
            }
            return Global.putString(cr, name, data2);
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("putIntAtIndex index < 0 index=");
            sb5.append(index);
            throw new IllegalArgumentException(sb5.toString());
        }
    }

    public static int getIntAtIndex(ContentResolver cr, String name, int index) throws SettingNotFoundException {
        String v = Global.getString(cr, name);
        if (v != null) {
            String[] valArray = v.split(",");
            if (index >= 0 && index < valArray.length && valArray[index] != null) {
                try {
                    return Integer.parseInt(valArray[index]);
                } catch (NumberFormatException e) {
                }
            }
        }
        throw new SettingNotFoundException(name);
    }
}
