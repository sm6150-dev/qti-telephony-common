package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import com.qualcomm.qcrilhook.QcRilHook;
import com.qualcomm.qcrilhook.QcRilHookCallback;
import com.qualcomm.qti.internal.telephony.DynamicSarService;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import org.codeaurora.telephony.utils.AsyncResult;

public class ModemSarController implements DynamicSarService.SarControllerClient {
    /* access modifiers changed from: private */
    public static int CHARGEState = 0;
    private static final String DEVICE_INFO_HW_CN = "CN";
    private static final String DEVICE_INFO_HW_GLOBAL = "GLOBAL";
    private static final String DEVICE_INFO_HW_INDIA = "INDIA";
    private static final String DEVICE_INFO_HW_LEVEL_P0 = "P0";
    private static final String DEVICE_INFO_HW_LEVEL_P1 = "P1";
    private static final String DEVICE_INFO_HW_LEVEL_P2 = "P2";
    private static final String DEVICE_INFO_SW_GLOBAL = "global";
    private static final String DEVICE_INFO_SW_INDIA = "in_global";
    private static final String DEVICE_TYPE_F10 = "davinci";
    private static final String DEVICE_TYPE_GRUS = "grus";
    private static final String DEVICE_TYPE_PHOENIXIN = "phoenixin";
    private static final String DEVICE_TYPE_TOCO = "toco";
    private static final String DEVICE_TYPE_TUCANA = "tucana";
    private static final int DSI_0 = 0;
    private static final int DSI_1 = 1;
    private static final int DSI_2 = 2;
    private static final int DSI_3 = 3;
    private static final int DSI_4 = 4;
    private static final int DSI_5 = 5;
    private static final int DSI_6 = 6;
    private static final int DSI_7 = 7;
    private static final int DSI_8 = 8;
    /* access modifiers changed from: private */
    public static int DSI_Current = 0;
    /* access modifiers changed from: private */
    public static HashMap<Integer, Integer> DSI_Hash = new HashMap<>();
    /* access modifiers changed from: private */
    public static int DSI_History = 0;
    private static final int EVENT_CHARGE = 5;
    private static final int EVENT_Hotspot = 3;
    private static final int EVENT_Receiver = 2;
    private static final int EVENT_SAR_SENSOR = 1;
    private static final int EVENT_WIFI = 4;
    /* access modifiers changed from: private */
    public static int HotspotState = 0;
    private static final int HotspotStateNum = 2;
    public static final String LOG_TAG = "ModemSarController";
    private static final String PROPERTY_CHARGE_SAR_CONTROL = "persist.radio.charge_sar_control";
    private static final String PROPERTY_DEVICE_INFO_HW = "ro.boot.hwc";
    private static final String PROPERTY_DEVICE_INFO_HW_LEVEL = "ro.boot.hwlevel";
    private static final String PROPERTY_DEVICE_INFO_SW = "ro.product.mod_device";
    private static final String PROPERTY_DEVICE_NAME = "ro.product.device";
    private static final String PROPERTY_DYNAMIC_SAR_MAIN_CONTROL = "persist.radio.dynamic_sar";
    private static final String PROPERTY_DYNAMIC_SAR_TEST_CONTROL = "persist.radio.sar_test";
    /* access modifiers changed from: private */
    public static int ReceiverState = 0;
    private static final int ReceiverStateNum = 2;
    /* access modifiers changed from: private */
    public static int SarSensorState = 0;
    /* access modifiers changed from: private */
    public static int WIFIState = 0;
    private static final int WIFIStateNum = 2;
    /* access modifiers changed from: private */
    public static boolean mCHARGESAREnabled = false;
    private static String mDeviceHW = null;
    /* access modifiers changed from: private */
    public static String mDeviceName = null;
    private static String mDeviceSW = null;
    private static boolean mDynmaicSarMainEnabled = false;
    private static boolean mDynmaicSarTestEnabled = false;
    /* access modifiers changed from: private */
    public static QcRilHook mQcRilHook;
    /* access modifiers changed from: private */
    public static boolean mQcRilHookReady;
    private static QcRilHookCallback mQcrilHookCb = new QcRilHookCallback() {
        public void onQcRilHookReady() {
            boolean unused = ModemSarController.mQcRilHookReady = true;
        }

        public synchronized void onQcRilHookDisconnected() {
            boolean unused = ModemSarController.mQcRilHookReady = false;
        }
    };
    /* access modifiers changed from: private */
    public static boolean mSarSensorEnabled = true;
    private static ModemSarController sIntance;
    CmdProcThread mCmdProc = null;
    private Context mContext;
    private DynamicSarService mService;

    public static void make(Context context) {
        if (sIntance == null) {
            sIntance = new ModemSarController(context);
        }
    }

    private ModemSarController(Context context) {
        this.mContext = context;
        mDeviceName = SystemProperties.get(PROPERTY_DEVICE_NAME);
        mDeviceHW = SystemProperties.get(PROPERTY_DEVICE_INFO_HW);
        mDeviceSW = SystemProperties.get(PROPERTY_DEVICE_INFO_SW);
        mDynmaicSarTestEnabled = SystemProperties.getBoolean(PROPERTY_DYNAMIC_SAR_TEST_CONTROL, false);
        mDynmaicSarMainEnabled = SystemProperties.getBoolean(PROPERTY_DYNAMIC_SAR_MAIN_CONTROL, false);
        mCHARGESAREnabled = SystemProperties.getBoolean(PROPERTY_CHARGE_SAR_CONTROL, false);
        log("Device name = " + mDeviceName + ", DeviceHW = " + mDeviceHW + ", DeviceSW = " + mDeviceSW + ", mDynmaicSarTestEnabled = " + mDynmaicSarTestEnabled + ", mDynmaicSarMainEnabled = " + mDynmaicSarMainEnabled + ", mSarSensorEnabled = " + mSarSensorEnabled + ", mCHARGESAREnabled = " + mCHARGESAREnabled);
        if (mDynmaicSarTestEnabled) {
            log("Warning, Sar Test switch is started.");
            DSI_Hash_Init_Common_Cfg();
        } else if (!mDynmaicSarMainEnabled) {
            log("Sar main switch is not started, ModemSarController do nothing");
            return;
        } else if (true != onInitSarProdcutParameter()) {
            log("wil not start dynamic sar service");
            return;
        }
        this.mService = DynamicSarService.getInstance(context);
        if (this.mService == null) {
            log("DynamicSarService start failed");
            return;
        }
        this.mCmdProc = new CmdProcThread();
        if (this.mCmdProc == null) {
            log("mCmdProc is null, CmdProcThread init failed");
            return;
        }
        mQcRilHook = new QcRilHook(context, mQcrilHookCb);
        if (mQcRilHook == null) {
            log("QcRilHook start failed");
            return;
        }
        this.mCmdProc.start();
        if (mSarSensorEnabled) {
            this.mService.registerStateChangeListener(3, this);
        }
        this.mService.registerStateChangeListener(5, this);
        this.mService.registerStateChangeListener(1, this);
        this.mService.registerStateChangeListener(0, this);
        if (mCHARGESAREnabled) {
            this.mService.registerStateChangeListener(6, this);
            this.mService.registerStateChangeListener(7, this);
        }
    }

    public static boolean onInitSarProdcutParameter() {
        String str = mDeviceName;
        if (str == null) {
            log("mDeviceName is null");
            return false;
        } else if (str.contains(DEVICE_TYPE_F10)) {
            return onInitSarParameterF10();
        } else {
            if (mDeviceName.contains(DEVICE_TYPE_GRUS)) {
                return onInitSarParameterF2();
            }
            if (mDeviceName.contains(DEVICE_TYPE_PHOENIXIN)) {
                return onInitSarParameterG7B();
            }
            if (mDeviceName.contains(DEVICE_TYPE_TUCANA)) {
                return onInitSarParameterF4();
            }
            if (mDeviceName.contains(DEVICE_TYPE_TOCO)) {
                return onInitSarParameterF4L();
            }
            log("not support this product");
            return false;
        }
    }

    private static boolean onInitSarParameterF2() {
        log("onInitSarParameterF2");
        if (mDeviceHW.equals(DEVICE_INFO_HW_CN) || !mDeviceSW.contains(DEVICE_INFO_SW_GLOBAL)) {
            log("not support this version");
            return false;
        }
        DSI_Hash_Init_Common_Cfg();
        return true;
    }

    private static boolean onInitSarParameterF10() {
        log("onInitSarParameterF10");
        if (mDeviceSW == null) {
            log("mDeviceSW is null");
            return false;
        } else if (mDeviceHW == null) {
            log("mDeviceHW is null");
            return false;
        } else {
            String hwLevel = SystemProperties.get(PROPERTY_DEVICE_INFO_HW_LEVEL);
            log("hwLevel: " + hwLevel);
            if (DEVICE_INFO_HW_LEVEL_P0.equals(hwLevel) || DEVICE_INFO_HW_LEVEL_P1.equals(hwLevel)) {
                log("RFNV about sar is not valid in P0 and P1, pls use test switch to open sar after you are sure RFNV of sar is good");
                return false;
            } else if (mDeviceSW.contains(DEVICE_INFO_SW_GLOBAL) && mDeviceHW.contains(DEVICE_INFO_HW_GLOBAL)) {
                DSI_Hash_Init_Common_Cfg();
                return true;
            } else if (!mDeviceSW.contains(DEVICE_INFO_SW_INDIA) || !mDeviceHW.contains(DEVICE_INFO_HW_INDIA)) {
                log("sw and hw info is mismatch or not correct, pls check your sw and hw info");
                return false;
            } else {
                DSI_Hash_Init_Common_Cfg();
                mSarSensorEnabled = false;
                log("India version not supprot sar sensor, will not listen it");
                return true;
            }
        }
    }

    private static boolean onInitSarParameterG7B() {
        log("onInitSarParameterG7B");
        String str = mDeviceSW;
        if (str == null) {
            log("mDeviceSW is null");
            return false;
        } else if (mDeviceHW == null) {
            log("mDeviceHW is null");
            return false;
        } else if (!str.contains(DEVICE_INFO_SW_INDIA) || !mDeviceHW.contains(DEVICE_INFO_HW_INDIA)) {
            log("sw and hw info is mismatch or not correct, pls check your sw and hw info");
            return false;
        } else {
            DSI_Hash_Init_Common_Cfg();
            mSarSensorEnabled = false;
            log("India version not supprot sar sensor, will not listen it");
            return true;
        }
    }

    private static boolean onInitSarParameterF4() {
        log("onInitSarParameterF4");
        if (!mDeviceSW.contains(DEVICE_INFO_SW_GLOBAL) || !mDeviceHW.contains(DEVICE_INFO_HW_GLOBAL)) {
            log("not support this version");
            return false;
        }
        DSI_Hash_Init_Common_Cfg();
        return true;
    }

    private static boolean onInitSarParameterF4L() {
        log("onInitSarParameterF4L");
        if (mDeviceSW.contains(DEVICE_INFO_SW_GLOBAL) && mDeviceHW.contains(DEVICE_INFO_HW_GLOBAL)) {
            DSI_Hash_Init_Common_Cfg();
            log("Global version disable sar sensor");
            return true;
        } else if (!mDeviceSW.contains(DEVICE_INFO_SW_INDIA) || !mDeviceHW.contains(DEVICE_INFO_HW_INDIA)) {
            log("not support this version");
            return false;
        } else {
            DSI_Hash_Init_Common_Cfg();
            mSarSensorEnabled = false;
            log("India version not supprot sar sensor, will not listen it");
            return true;
        }
    }

    public void onStateChanged(int type, int value) {
        log("onStateChanged: type = " + type + ", value = " + value);
        if (type == 0) {
            sendMsgToHandler(4, value, 0);
        } else if (type == 1) {
            sendMsgToHandler(3, value, 0);
        } else if (type == 3) {
            sendMsgToHandler(1, value, 0);
        } else if (type == 5) {
            sendMsgToHandler(2, value, 0);
        } else if (type == 6) {
            sendMsgToHandler(5, value, 0);
        } else if (type == 7) {
            sendMsgToHandler(5, value, 0);
        }
    }

    /* access modifiers changed from: private */
    public static int DSI_Hash_Key_Convert(int sarsensorstate, int receiverstate, int hotspotstate, int wifistate) {
        return (sarsensorstate * 8) + (receiverstate * 4) + (hotspotstate * 2) + wifistate;
    }

    private static void DSI_Hash_Init_Common_Cfg() {
        log("DSI_Hash_Init_Common_Cfg");
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 0, 0)), 0);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 0, 1)), 0);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 0, 0)), 1);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 0, 0)), 1);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 0, 0)), 1);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 0, 1)), 2);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 0, 1)), 2);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 0, 1)), 2);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 0, 0)), 3);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 0, 1)), 4);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 0, 0)), 5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 0, 1)), 6);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 1, 0)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 1, 1)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 1, 0)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 1, 1)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 1, 0)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 1, 1)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 1, 0)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 1, 1)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 1, 0)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 1, 1)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 1, 0)), 7);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 1, 1)), 7);
    }

    private void sendMsgToHandler(int message, int arg1, int arg2) {
        log("sendMsgToHandler, Message = " + message + ", arg1 = " + arg1 + ", arg2 = " + arg2);
        Handler handler = this.mCmdProc.getCmdHandler();
        handler.sendMessage(handler.obtainMessage(message, arg1, arg2));
    }

    /* access modifiers changed from: private */
    public static final void log(String s) {
        Log.i(LOG_TAG, s);
    }

    private static class CmdProcThread extends Thread {
        private Handler mHandler;

        private CmdProcThread() {
            this.mHandler = null;
        }

        public Handler getCmdHandler() {
            if (this.mHandler == null) {
                Log.e(ModemSarController.LOG_TAG, "getCmdHandler, handler is NULL!!!");
            }
            return this.mHandler;
        }

        public void run() {
            Looper.prepare();
            this.mHandler = new CmdHandler();
            ModemSarController.log("CmdProcThread, thread is running up!!!!");
            Looper.loop();
        }

        private static class CmdHandler extends Handler {
            private static final int DYNAMIC_SAR_REQ_NUM = 524489;
            private static final int INT_SIZE = 4;
            private static final String OEM_IDENTIFIER = "QOEMHOOK";
            private byte[] mResBuf;

            private CmdHandler() {
                this.mResBuf = new byte[16];
            }

            private void cmdMsgSend(int reqNum, int para1) {
                while (!ModemSarController.mQcRilHookReady) {
                    Log.i(ModemSarController.LOG_TAG, "Error: QcrilHook is not ready!");
                    SystemClock.sleep(5000);
                }
                byte[] requestData = new byte[8];
                QcRilHook unused = ModemSarController.mQcRilHook;
                ByteBuffer reqBuffer = QcRilHook.createBufferWithNativeByteOrder(requestData);
                reqBuffer.putInt(para1);
                reqBuffer.putInt(0);
                AsyncResult ar = ModemSarController.mQcRilHook.sendQcRilHookMsg(reqNum, requestData);
                if (ar.exception != null) {
                    Log.e(ModemSarController.LOG_TAG, "Exception " + ar.exception);
                } else if (ar.result != null) {
                    ByteBuffer byteBuf = ByteBuffer.wrap((byte[]) ar.result);
                    byteBuf.order(ByteOrder.nativeOrder());
                    this.mResBuf[0] = (byte) byteBuf.getInt();
                    Log.d(ModemSarController.LOG_TAG, "Response is: " + byteBuf.toString());
                    Log.d(ModemSarController.LOG_TAG, "mResBuf[0] = " + String.valueOf(this.mResBuf[0]));
                } else {
                    Log.e(ModemSarController.LOG_TAG, "mQcRilHook.sendQcRilHookMsg: Null Response");
                }
            }

            private void DSI_Handle() {
                ModemSarController.log("SarSensorState is = " + ModemSarController.SarSensorState);
                ModemSarController.log("ReceiverState is = " + ModemSarController.ReceiverState);
                ModemSarController.log("HotspotState is = " + ModemSarController.HotspotState);
                ModemSarController.log("WIFIState is = " + ModemSarController.WIFIState);
                ModemSarController.log("CHARGEState is = " + ModemSarController.CHARGEState);
                ModemSarController.log("DSI_History = " + ModemSarController.DSI_History);
                if (ModemSarController.CHARGEState != 1 || !ModemSarController.mCHARGESAREnabled || !ModemSarController.DEVICE_TYPE_GRUS.equals(ModemSarController.mDeviceName)) {
                    int unused = ModemSarController.DSI_Current = ((Integer) ModemSarController.DSI_Hash.get(Integer.valueOf(ModemSarController.DSI_Hash_Key_Convert(ModemSarController.SarSensorState, ModemSarController.ReceiverState, ModemSarController.HotspotState, ModemSarController.WIFIState)))).intValue();
                } else {
                    int unused2 = ModemSarController.DSI_Current = 8;
                }
                ModemSarController.log("DSI_Current is = " + ModemSarController.DSI_Current);
                if (ModemSarController.DSI_Current != ModemSarController.DSI_History) {
                    ModemSarController.log("cmdMsgSend: = " + ModemSarController.DSI_Current);
                    cmdMsgSend(DYNAMIC_SAR_REQ_NUM, ModemSarController.DSI_Current);
                } else {
                    ModemSarController.log("DSI value not change, will not send dsi to modem");
                }
                int unused3 = ModemSarController.DSI_History = ModemSarController.DSI_Current;
            }

            public void handleMessage(Message msg) {
                ModemSarController.log("handlerMessage, msg = " + msg.what);
                int i = msg.what;
                if (i == 1) {
                    ModemSarController.log("msg.arg1 = " + msg.arg1);
                    if (ModemSarController.mSarSensorEnabled) {
                        int unused = ModemSarController.SarSensorState = msg.arg1;
                        if (!(ModemSarController.SarSensorState == 0 || 1 == ModemSarController.SarSensorState || 2 == ModemSarController.SarSensorState)) {
                            ModemSarController.log("sar sensor value is invalid, will force set it to 0");
                            int unused2 = ModemSarController.SarSensorState = 2;
                        }
                        DSI_Handle();
                        return;
                    }
                    ModemSarController.log("sar sensor is not started, invalid event, will not send dsi");
                } else if (i == 2) {
                    ModemSarController.log("msg.arg1 = " + msg.arg1);
                    int unused3 = ModemSarController.ReceiverState = msg.arg1;
                    if (!(ModemSarController.ReceiverState == 0 || 1 == ModemSarController.ReceiverState)) {
                        ModemSarController.log("ReceiverState is invalid, will force set it to 0");
                        int unused4 = ModemSarController.ReceiverState = 0;
                    }
                    DSI_Handle();
                } else if (i == 3) {
                    ModemSarController.log("msg.arg1 = " + msg.arg1);
                    int unused5 = ModemSarController.HotspotState = msg.arg1;
                    if (!(ModemSarController.HotspotState == 0 || 1 == ModemSarController.HotspotState)) {
                        ModemSarController.log("HotspotState is invalid, will force set it to 0");
                        int unused6 = ModemSarController.HotspotState = 0;
                    }
                    DSI_Handle();
                } else if (i == 4) {
                    ModemSarController.log("msg.arg1 = " + msg.arg1);
                    int unused7 = ModemSarController.WIFIState = msg.arg1;
                    if (!(ModemSarController.WIFIState == 0 || 1 == ModemSarController.WIFIState)) {
                        ModemSarController.log("WIFIState is invalid, will force set it to 0");
                        int unused8 = ModemSarController.WIFIState = 0;
                    }
                    DSI_Handle();
                } else if (i == 5) {
                    ModemSarController.log("msg.arg1 = " + msg.arg1);
                    if (ModemSarController.mCHARGESAREnabled) {
                        int unused9 = ModemSarController.CHARGEState = msg.arg1;
                        if (!(ModemSarController.CHARGEState == 0 || 1 == ModemSarController.CHARGEState)) {
                            ModemSarController.log("CHARGE SAR value is invalid, will force set it to 0");
                            int unused10 = ModemSarController.CHARGEState = 0;
                        }
                        DSI_Handle();
                        return;
                    }
                    ModemSarController.log("CHARGE SAR is not started, invalid event, will not send dsi");
                }
            }
        }
    }
}
