package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.net.wifi.MiuiWifiManager;
import miui.os.Build;

public class WifiManagerCompatible {
    public static void setSARLimit(Context context, int set) {
        MiuiWifiManager.getInstance(context).setSARLimit(set);
    }

    public static boolean isNeeded() {
        return Build.IS_GLOBAL_BUILD;
    }
}
