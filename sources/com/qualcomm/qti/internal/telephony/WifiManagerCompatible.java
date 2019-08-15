package com.qualcomm.qti.internal.telephony;

import android.net.wifi.MiuiWifiManager;
import miui.os.Build;

public class WifiManagerCompatible {
    public static void setSARLimit(int set) {
        MiuiWifiManager.setSARLimit(set);
    }

    public static boolean isNeeded() {
        return Build.IS_GLOBAL_BUILD;
    }
}
