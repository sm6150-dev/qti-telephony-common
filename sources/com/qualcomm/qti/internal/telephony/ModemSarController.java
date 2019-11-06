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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import org.codeaurora.telephony.utils.AsyncResult;

public class ModemSarController implements SarControllerClient {
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
            ModemSarController.mQcRilHookReady = true;
        }

        public synchronized void onQcRilHookDisconnected() {
            ModemSarController.mQcRilHookReady = false;
        }
    };
    /* access modifiers changed from: private */
    public static boolean mSarSensorEnabled = true;
    private static ModemSarController sIntance;
    CmdProcThread mCmdProc = null;
    private Context mContext;
    private DynamicSarService mService;

    private static class CmdProcThread extends Thread {
        private Handler mHandler;

        private static class CmdHandler extends Handler {
            private static final int DYNAMIC_SAR_REQ_NUM = 524489;
            private static final int INT_SIZE = 4;
            private static final String OEM_IDENTIFIER = "QOEMHOOK";
            private byte[] mResBuf;

            private CmdHandler() {
                this.mResBuf = new byte[16];
            }

            private void cmdMsgSend(int reqNum, int para1) {
                String str;
                while (true) {
                    boolean access$000 = ModemSarController.mQcRilHookReady;
                    str = ModemSarController.LOG_TAG;
                    if (access$000) {
                        break;
                    }
                    Log.i(str, "Error: QcrilHook is not ready!");
                    SystemClock.sleep(5000);
                }
                byte[] requestData = new byte[8];
                ModemSarController.mQcRilHook;
                ByteBuffer reqBuffer = QcRilHook.createBufferWithNativeByteOrder(requestData);
                reqBuffer.putInt(para1);
                reqBuffer.putInt(0);
                AsyncResult ar = ModemSarController.mQcRilHook.sendQcRilHookMsg(reqNum, requestData);
                if (ar.exception != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Exception ");
                    sb.append(ar.exception);
                    Log.e(str, sb.toString());
                } else if (ar.result != null) {
                    ByteBuffer byteBuf = ByteBuffer.wrap((byte[]) ar.result);
                    byteBuf.order(ByteOrder.nativeOrder());
                    this.mResBuf[0] = (byte) byteBuf.getInt();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Response is: ");
                    sb2.append(byteBuf.toString());
                    Log.d(str, sb2.toString());
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("mResBuf[0] = ");
                    sb3.append(String.valueOf(this.mResBuf[0]));
                    Log.d(str, sb3.toString());
                } else {
                    Log.e(str, "mQcRilHook.sendQcRilHookMsg: Null Response");
                }
            }

            /* JADX WARNING: Removed duplicated region for block: B:10:0x00fa  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x011d  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            private void DSI_Handle() {
                /*
                    r5 = this;
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "SarSensorState is = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.SarSensorState
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "ReceiverState is = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.ReceiverState
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "HotspotState is = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.HotspotState
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "WIFIState is = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.WIFIState
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "CHARGEState is = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.CHARGEState
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "DSI_History = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_History
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    int r0 = com.qualcomm.qti.internal.telephony.ModemSarController.CHARGEState
                    r1 = 1
                    if (r0 != r1) goto L_0x00af
                    boolean r0 = com.qualcomm.qti.internal.telephony.ModemSarController.mCHARGESAREnabled
                    if (r0 == 0) goto L_0x00af
                    java.lang.String r0 = com.qualcomm.qti.internal.telephony.ModemSarController.mDeviceName
                    java.lang.String r1 = "grus"
                    boolean r0 = r1.equals(r0)
                    if (r0 == 0) goto L_0x00af
                    r0 = 8
                    com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current = r0
                    goto L_0x00d8
                L_0x00af:
                    java.util.HashMap r0 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Hash
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.SarSensorState
                    int r2 = com.qualcomm.qti.internal.telephony.ModemSarController.ReceiverState
                    int r3 = com.qualcomm.qti.internal.telephony.ModemSarController.HotspotState
                    int r4 = com.qualcomm.qti.internal.telephony.ModemSarController.WIFIState
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Hash_Key_Convert(r1, r2, r3, r4)
                    java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                    java.lang.Object r0 = r0.get(r1)
                    java.lang.Integer r0 = (java.lang.Integer) r0
                    int r0 = r0.intValue()
                    com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current = r0
                L_0x00d8:
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "DSI_Current is = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    int r0 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_History
                    if (r0 == r1) goto L_0x011d
                    java.lang.StringBuilder r0 = new java.lang.StringBuilder
                    r0.<init>()
                    java.lang.String r1 = "cmdMsgSend: = "
                    r0.append(r1)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current
                    r0.append(r1)
                    java.lang.String r0 = r0.toString()
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                    r0 = 524489(0x800c9, float:7.34966E-40)
                    int r1 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current
                    r5.cmdMsgSend(r0, r1)
                    goto L_0x0122
                L_0x011d:
                    java.lang.String r0 = "DSI value not change, will not send dsi to modem"
                    com.qualcomm.qti.internal.telephony.ModemSarController.log(r0)
                L_0x0122:
                    int r0 = com.qualcomm.qti.internal.telephony.ModemSarController.DSI_Current
                    com.qualcomm.qti.internal.telephony.ModemSarController.DSI_History = r0
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.ModemSarController.CmdProcThread.CmdHandler.DSI_Handle():void");
            }

            public void handleMessage(Message msg) {
                StringBuilder sb = new StringBuilder();
                sb.append("handlerMessage, msg = ");
                sb.append(msg.what);
                ModemSarController.log(sb.toString());
                int i = msg.what;
                String str = "msg.arg1 = ";
                if (i == 1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(msg.arg1);
                    ModemSarController.log(sb2.toString());
                    if (ModemSarController.mSarSensorEnabled) {
                        ModemSarController.SarSensorState = msg.arg1;
                        if (!(ModemSarController.SarSensorState == 0 || 1 == ModemSarController.SarSensorState || 2 == ModemSarController.SarSensorState)) {
                            ModemSarController.log("sar sensor value is invalid, will force set it to 0");
                            ModemSarController.SarSensorState = 0;
                        }
                        DSI_Handle();
                        return;
                    }
                    ModemSarController.log("sar sensor is not started, invalid event, will not send dsi");
                } else if (i == 2) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(msg.arg1);
                    ModemSarController.log(sb3.toString());
                    ModemSarController.ReceiverState = msg.arg1;
                    if (!(ModemSarController.ReceiverState == 0 || 1 == ModemSarController.ReceiverState)) {
                        ModemSarController.log("ReceiverState is invalid, will force set it to 0");
                        ModemSarController.ReceiverState = 0;
                    }
                    DSI_Handle();
                } else if (i == 3) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str);
                    sb4.append(msg.arg1);
                    ModemSarController.log(sb4.toString());
                    ModemSarController.HotspotState = msg.arg1;
                    if (!(ModemSarController.HotspotState == 0 || 1 == ModemSarController.HotspotState)) {
                        ModemSarController.log("HotspotState is invalid, will force set it to 0");
                        ModemSarController.HotspotState = 0;
                    }
                    DSI_Handle();
                } else if (i == 4) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(str);
                    sb5.append(msg.arg1);
                    ModemSarController.log(sb5.toString());
                    ModemSarController.WIFIState = msg.arg1;
                    if (!(ModemSarController.WIFIState == 0 || 1 == ModemSarController.WIFIState)) {
                        ModemSarController.log("WIFIState is invalid, will force set it to 0");
                        ModemSarController.WIFIState = 0;
                    }
                    DSI_Handle();
                } else if (i == 5) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str);
                    sb6.append(msg.arg1);
                    ModemSarController.log(sb6.toString());
                    if (ModemSarController.mCHARGESAREnabled) {
                        ModemSarController.CHARGEState = msg.arg1;
                        if (!(ModemSarController.CHARGEState == 0 || 1 == ModemSarController.CHARGEState)) {
                            ModemSarController.log("CHARGE SAR value is invalid, will force set it to 0");
                            ModemSarController.CHARGEState = 0;
                        }
                        DSI_Handle();
                        return;
                    }
                    ModemSarController.log("CHARGE SAR is not started, invalid event, will not send dsi");
                }
            }
        }

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
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append("Device name = ");
        sb.append(mDeviceName);
        sb.append(", DeviceHW = ");
        sb.append(mDeviceHW);
        sb.append(", DeviceSW = ");
        sb.append(mDeviceSW);
        sb.append(", mDynmaicSarTestEnabled = ");
        sb.append(mDynmaicSarTestEnabled);
        sb.append(", mDynmaicSarMainEnabled = ");
        sb.append(mDynmaicSarMainEnabled);
        sb.append(", mSarSensorEnabled = ");
        sb.append(mSarSensorEnabled);
        sb.append(", mCHARGESAREnabled = ");
        sb.append(mCHARGESAREnabled);
        log(sb.toString());
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
        boolean sarFlag = true;
        String str = DEVICE_INFO_HW_LEVEL_P2;
        log("onInitSarParameterF10");
        if (mDeviceSW == null) {
            log("mDeviceSW is null");
            return false;
        } else if (mDeviceHW == null) {
            log("mDeviceHW is null");
            return false;
        } else {
            String hwLevel = SystemProperties.get(PROPERTY_DEVICE_INFO_HW_LEVEL);
            StringBuilder sb = new StringBuilder();
            sb.append("hwLevel: ");
            sb.append(hwLevel);
            log(sb.toString());
            if (DEVICE_INFO_HW_LEVEL_P0.equals(hwLevel) || DEVICE_INFO_HW_LEVEL_P1.equals(hwLevel)) {
                log("RFNV about sar is not valid in P0 and P1, pls use test switch to open sar after you are sure RFNV of sar is good");
                sarFlag = false;
            } else if (mDeviceSW.contains(DEVICE_INFO_SW_GLOBAL) && mDeviceHW.contains(DEVICE_INFO_HW_GLOBAL)) {
                DSI_Hash_Init_Common_Cfg();
            } else if (!mDeviceSW.contains(DEVICE_INFO_SW_INDIA) || !mDeviceHW.contains(DEVICE_INFO_HW_INDIA)) {
                log("sw and hw info is mismatch or not correct, pls check your sw and hw info");
                sarFlag = false;
            } else {
                DSI_Hash_Init_Common_Cfg();
                mSarSensorEnabled = false;
                log("India version not supprot sar sensor, will not listen it");
            }
            return sarFlag;
        }
    }

    private static boolean onInitSarParameterG7B() {
        boolean sarFlag = true;
        log("onInitSarParameterG7B");
        String str = mDeviceSW;
        if (str == null) {
            log("mDeviceSW is null");
            return false;
        } else if (mDeviceHW == null) {
            log("mDeviceHW is null");
            return false;
        } else {
            if (!str.contains(DEVICE_INFO_SW_INDIA) || !mDeviceHW.contains(DEVICE_INFO_HW_INDIA)) {
                log("sw and hw info is mismatch or not correct, pls check your sw and hw info");
                sarFlag = false;
            } else {
                DSI_Hash_Init_Common_Cfg();
                mSarSensorEnabled = false;
                log("India version not supprot sar sensor, will not listen it");
            }
            return sarFlag;
        }
    }

    public void onStateChanged(int type, int value) {
        StringBuilder sb = new StringBuilder();
        sb.append("onStateChanged: type = ");
        sb.append(type);
        sb.append(", value = ");
        sb.append(value);
        log(sb.toString());
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
        HashMap<Integer, Integer> hashMap = DSI_Hash;
        Integer valueOf = Integer.valueOf(0);
        hashMap.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 0, 0)), valueOf);
        HashMap<Integer, Integer> hashMap2 = DSI_Hash;
        Integer valueOf2 = Integer.valueOf(1);
        hashMap2.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 0, 1)), valueOf);
        HashMap<Integer, Integer> hashMap3 = DSI_Hash;
        Integer valueOf3 = Integer.valueOf(2);
        hashMap3.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 0, 0)), valueOf2);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 0, 0)), valueOf2);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 0, 0)), valueOf2);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 0, 1)), valueOf3);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 0, 1)), valueOf3);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 0, 1)), valueOf3);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 0, 0)), Integer.valueOf(3));
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 0, 1)), Integer.valueOf(4));
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 0, 0)), Integer.valueOf(5));
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 0, 1)), Integer.valueOf(6));
        HashMap<Integer, Integer> hashMap4 = DSI_Hash;
        Integer valueOf4 = Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 1, 0));
        Integer valueOf5 = Integer.valueOf(7);
        hashMap4.put(valueOf4, valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 0, 1, 1)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 1, 0)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(2, 1, 1, 1)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 1, 0)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 0, 1, 1)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 1, 0)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(1, 1, 1, 1)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 1, 0)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 0, 1, 1)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 1, 0)), valueOf5);
        DSI_Hash.put(Integer.valueOf(DSI_Hash_Key_Convert(0, 1, 1, 1)), valueOf5);
    }

    private void sendMsgToHandler(int message, int arg1, int arg2) {
        StringBuilder sb = new StringBuilder();
        sb.append("sendMsgToHandler, Message = ");
        sb.append(message);
        sb.append(", arg1 = ");
        sb.append(arg1);
        sb.append(", arg2 = ");
        sb.append(arg2);
        log(sb.toString());
        Handler handler = this.mCmdProc.getCmdHandler();
        handler.sendMessage(handler.obtainMessage(message, arg1, arg2));
    }

    /* access modifiers changed from: private */
    public static final void log(String s) {
        Log.i(LOG_TAG, s);
    }
}
