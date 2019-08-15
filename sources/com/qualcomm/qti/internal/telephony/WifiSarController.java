package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

public class WifiSarController implements SarControllerClient {
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
    private final String PATERN3_DEVCIE_LIST = "raphael,davinci,davinciin";
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
        } else if ("raphael,davinci,davinciin".indexOf(device) != -1) {
            mPaternIndex = 3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("device: ");
        sb.append(device);
        sb.append(", SAR patern: ");
        sb.append(mPaternIndex);
        Log.d("WifiSarController", sb.toString());
        this.mContext = context;
        this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        if (mPaternIndex != 0) {
            this.mService = DynamicSarService.getInstance(context);
        }
        if (this.mService != null) {
            if (mPaternIndex == 1) {
                this.mService.registerStateChangeListener(0, this);
                this.mService.registerStateChangeListener(1, this);
                this.mService.registerStateChangeListener(3, this);
                this.mService.registerStateChangeListener(2, this);
                this.mService.registerStateChangeListener(5, this);
            } else if (mPaternIndex == 2) {
                this.mService.registerStateChangeListener(0, this);
                this.mService.registerStateChangeListener(1, this);
                this.mService.registerStateChangeListener(5, this);
            } else if (mPaternIndex == 3) {
                this.mService.registerStateChangeListener(0, this);
                this.mService.registerStateChangeListener(1, this);
                this.mService.registerStateChangeListener(2, this);
                this.mService.registerStateChangeListener(5, this);
                this.mService.registerStateChangeListener(8, this);
            }
        }
        Log.d("WifiSarController", "WifiSarController init done");
    }

    public void onStateChanged(int type, int value) {
        StringBuilder sb = new StringBuilder();
        sb.append("onStateChanged: type = ");
        sb.append(type);
        sb.append(", value = ");
        sb.append(value);
        Log.d("WifiSarController", sb.toString());
        if (type == 5) {
            mAudioReceiverState = value;
        } else if (type != 8) {
            switch (type) {
                case 0:
                    mWifiState = value;
                    break;
                case 1:
                    mHotspotState = value;
                    break;
                case 2:
                    mModemState = value;
                    break;
                case 3:
                    mSarSensorState = value;
                    break;
            }
        } else {
            mMccState = value;
        }
        updateWifiSarSet();
    }

    private void updateWifiSarSet() {
        int sarSet = 100;
        if (mPaternIndex == 1) {
            sarSet = calculateSarSetPatern1();
        } else if (mPaternIndex == 2) {
            sarSet = calculateSarSetPatern2();
        } else if (mPaternIndex == 3) {
            sarSet = calculateSarSetPatern3();
        }
        if (mSarSet != sarSet) {
            StringBuilder sb = new StringBuilder();
            sb.append("setSARLimit: ");
            sb.append(sarSet);
            Log.d("WifiSarController", sb.toString());
            WifiManagerCompatible.setSARLimit(sarSet);
            mSarSet = sarSet;
        }
    }

    private int calculateSarSetPatern1() {
        int sarSet = 100;
        if (mWifiState == 0 && mHotspotState == 0) {
            return 100;
        }
        if (mAudioReceiverState == 1) {
            sarSet = mModemState == 1 ? 1 : 0;
        } else if (mHotspotState == 1) {
            if (mSarSensorState != 0) {
                sarSet = 4;
            }
        } else if (mModemState == 1) {
            if (mSarSensorState == 2) {
                sarSet = 3;
            } else if (mSarSensorState == 1) {
                sarSet = 2;
            }
        } else if (mSarSensorState != 0) {
            sarSet = 0;
        }
        return sarSet;
    }

    private int calculateSarSetPatern2() {
        int sarSet = 100;
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
            sarSet = 0;
        }
        return sarSet;
    }

    private int calculateSarSetPatern3() {
        int sarSet = 100;
        if (mWifiState == 0 && mHotspotState == 0) {
            return 100;
        }
        int i = 1;
        if (mHotspotState == 1) {
            sarSet = 8;
        } else if (mAudioReceiverState == 1) {
            if (mMccState == 1) {
                if (mModemState != 1) {
                    i = 0;
                }
                sarSet = i;
            } else {
                sarSet = mModemState == 1 ? 3 : 2;
            }
        } else if (mHotspotState == 0) {
            if (mMccState == 1) {
                sarSet = mModemState == 1 ? 5 : 4;
            } else {
                sarSet = mModemState == 1 ? 7 : 6;
            }
        }
        return sarSet;
    }
}
