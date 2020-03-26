package com.qualcomm.qti.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
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
        String v = Settings.Global.getString(cr, name);
        if (index == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("putIntAtIndex index == MAX_VALUE index=" + index);
        } else if (index >= 0) {
            if (v != null) {
                valArray = v.split(",");
            }
            for (int i = 0; i < index; i++) {
                String str = "";
                if (valArray != null && i < valArray.length) {
                    str = valArray[i];
                }
                data = data + str + ",";
            }
            String data2 = data + value;
            if (valArray != null) {
                for (int i2 = index + 1; i2 < valArray.length; i2++) {
                    data2 = data2 + "," + valArray[i2];
                }
            }
            return Settings.Global.putString(cr, name, data2);
        } else {
            throw new IllegalArgumentException("putIntAtIndex index < 0 index=" + index);
        }
    }

    public static int getIntAtIndex(ContentResolver cr, String name, int index) throws Settings.SettingNotFoundException {
        String v = Settings.Global.getString(cr, name);
        if (v != null) {
            String[] valArray = v.split(",");
            if (index >= 0 && index < valArray.length && valArray[index] != null) {
                try {
                    return Integer.parseInt(valArray[index]);
                } catch (NumberFormatException e) {
                }
            }
        }
        throw new Settings.SettingNotFoundException(name);
    }
}
