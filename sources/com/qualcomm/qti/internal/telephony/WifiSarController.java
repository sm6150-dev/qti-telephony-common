package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import com.qualcomm.qti.internal.telephony.DynamicSarService;

public class WifiSarController implements DynamicSarService.SarControllerClient {
    private static int mAudioReceiverState;
    private static int mHotspotState;
    private static int mMccState = 3;
    private static int mModemState;
    private static int mPaternIndex = 0;
    private static int mSarSensorState;
    private static int mSarSet = 0;
    private static int mWifiState;
    private final int DSI0 = 0;
    private final int DSI1 = 1;
    private final int DSI2 = 2;
    private final int DSI3 = 3;
    private final int DSI4 = 4;
    private final int DSI5 = 5;
    private final int DSI6 = 6;
    private final int DSI7 = 7;
    private final int DSI8 = 8;
    private final int DSI_SAR_DISABLE = 100;
    private final String PATERN1_DEVCIE_LIST = "grus";
    private final String PATERN2_DEVCIE_LIST = "andromeda";
    private final String TAG = "WifiSarController";
    private Context mContext;
    private DynamicSarService mService = null;
    private WifiManager mWifiManager;

    public static boolean isNeeded() {
        return WifiManagerCompatible.isNeeded();
    }

    public WifiSarController(Context context) {
        Log.d("WifiSarController", "WifiSarController init...");
        String device = Build.DEVICE.toLowerCase();
        if ("grus".indexOf(device) != -1) {
            mPaternIndex = 1;
        } else if ("andromeda".indexOf(device) != -1) {
            mPaternIndex = 2;
        } else {
            mPaternIndex = 3;
        }
        Log.d("WifiSarController", "device: " + device + ", SAR patern: " + mPaternIndex);
        this.mContext = context;
        this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        if (mPaternIndex != 0) {
            this.mService = DynamicSarService.getInstance(context);
        }
        DynamicSarService dynamicSarService = this.mService;
        if (dynamicSarService != null) {
            int i = mPaternIndex;
            if (i == 1) {
                dynamicSarService.registerStateChangeListener(0, this);
                this.mService.registerStateChangeListener(1, this);
                this.mService.registerStateChangeListener(3, this);
                this.mService.registerStateChangeListener(2, this);
                this.mService.registerStateChangeListener(5, this);
            } else if (i == 2) {
                dynamicSarService.registerStateChangeListener(0, this);
                this.mService.registerStateChangeListener(1, this);
                this.mService.registerStateChangeListener(5, this);
            } else if (i == 3) {
                dynamicSarService.registerStateChangeListener(0, this);
                this.mService.registerStateChangeListener(1, this);
                this.mService.registerStateChangeListener(2, this);
                this.mService.registerStateChangeListener(5, this);
                this.mService.registerStateChangeListener(8, this);
            }
        }
        Log.d("WifiSarController", "WifiSarController init done");
    }

    public void onStateChanged(int type, int value) {
        Log.d("WifiSarController", "onStateChanged: type = " + type + ", value = " + value);
        if (type == 0) {
            mWifiState = value;
        } else if (type == 1) {
            mHotspotState = value;
        } else if (type == 2) {
            mModemState = value;
        } else if (type == 3) {
            mSarSensorState = value;
        } else if (type == 5) {
            mAudioReceiverState = value;
        } else if (type == 8) {
            mMccState = value;
        }
        updateWifiSarSet();
    }

    private void updateWifiSarSet() {
        int sarSet = 100;
        int i = mPaternIndex;
        if (i == 1) {
            sarSet = calculateSarSetPatern1();
        } else if (i == 2) {
            sarSet = calculateSarSetPatern2();
        } else if (i == 3) {
            sarSet = calculateSarSetPatern3();
        }
        if (mSarSet != sarSet) {
            Log.d("WifiSarController", "setSARLimit: " + sarSet);
            WifiManagerCompatible.setSARLimit(this.mContext, sarSet);
            mSarSet = sarSet;
        }
    }

    private int calculateSarSetPatern1() {
        if (mWifiState == 0 && mHotspotState == 0) {
            return 100;
        }
        if (mAudioReceiverState == 1) {
            if (mModemState == 1) {
                return 1;
            }
            return 0;
        } else if (mHotspotState == 1) {
            if (mSarSensorState != 0) {
                return 4;
            }
            return 100;
        } else if (mModemState == 1) {
            int i = mSarSensorState;
            if (i == 2) {
                return 3;
            }
            if (i == 1) {
                return 2;
            }
            return 100;
        } else if (mSarSensorState != 0) {
            return 0;
        } else {
            return 100;
        }
    }

    private int calculateSarSetPatern2() {
        if (mWifiState == 0 && mHotspotState == 0) {
            return 100;
        }
        if (mWifiState == 1 && mHotspotState == 1) {
            int staFreq = this.mWifiManager.getConnectionInfo().getFrequency();
            int sapBand = this.mWifiManager.getWifiApConfiguration().apBand;
            if ((staFreq < 5000 && sapBand == 1) || (staFreq > 5000 && sapBand == 0)) {
                return 1;
            }
        }
        if (mAudioReceiverState == 1) {
            return 0;
        }
        return 100;
    }

    private int calculateSarSetPatern3() {
        if (mWifiState == 0 && mHotspotState == 0) {
            return 100;
        }
        int i = mHotspotState;
        int sarSet = 1;
        if (i == 1) {
            return 8;
        }
        if (mAudioReceiverState == 1) {
            if (mMccState == 1) {
                if (mModemState != 1) {
                    sarSet = 0;
                }
                return sarSet;
            }
            return mModemState == 1 ? 3 : 2;
        } else if (i != 0) {
            return 100;
        } else {
            if (mMccState == 1) {
                return mModemState == 1 ? 5 : 4;
            }
            return mModemState == 1 ? 7 : 6;
        }
    }
}
