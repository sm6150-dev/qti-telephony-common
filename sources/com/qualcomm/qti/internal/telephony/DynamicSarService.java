package com.qualcomm.qti.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicSarService {
    public static final int AUDIO_RECEIVER_STATE_OFF = 0;
    public static final int AUDIO_RECEIVER_STATE_ON = 1;
    private static final String[] CEList = {"es", "it", "fr", "gb", "nl", "pt", "ch", "be", "lu", "de", "pl", "ua", "cz", "bg", "ro", "sk", "si", "hu", "gr", "dk", "fi", "no", "se", "lt", "ee", "lv", "rs", "at", "hr", "sa", "eg", "ir", "qa", "ma", "tr", "il", "ae", "ng", "th", "ph", "kh", "pk", "my", "au", "sg", "hk", "tw", "bd", "np", "lk", "jp"};
    public static final int CHARGE_CONNECTED_STATE = 1;
    public static final int CHARGE_DISCONNECTED_STATE = 0;
    private static final String[] FCCList = {"co", "uy", "py", "cl", "bo", "pe", "kr", "in"};
    public static final int HALL_STATE_CLOSE = 0;
    public static final int HALL_STATE_OPEN = 2;
    public static final int HOTSPOT_STATE_OFF = 0;
    public static final int HOTSPOT_STATE_ON = 1;
    public static final int MCC_CE = 1;
    public static final int MCC_DEFAULT = 3;
    public static final int MCC_FCC = 2;
    public static final int MODEM_STATE_ACTIVE = 1;
    public static final int MODEM_STATE_INACTIVE = 0;
    private static final String PROPERTY_OPERATOR_ISO_COUNTRY = "gsm.operator.iso-country";
    private static final String PROPERTY_RECEIVER_STATUS = "vendor.audio.voice.receiver.status";
    public static final int SAR_DISTANCE_LONG = 0;
    public static final int SAR_DISTANCE_MID = 1;
    public static final int SAR_DISTANCE_SHORT = 2;
    private static final int SENSOR_TYPE_HALLSENSOR = 0;
    private static final int SENSOR_TYPE_SARSENSOR = 33171028;
    public static final int TYPE_AUDIO_RECEIVER_STATE = 5;
    public static final int TYPE_CHARGE_CONNECTED_STATE = 6;
    public static final int TYPE_CHARGE_DISCONNECTED_STATE = 7;
    public static final int TYPE_HALL_SENSOR_STATE = 4;
    public static final int TYPE_HOTSPOT_STATE = 1;
    public static final int TYPE_MCC_STATE = 8;
    public static final int TYPE_MODEM_STATE = 2;
    public static final int TYPE_SAR_SENSOR_STATE = 3;
    public static final int TYPE_WIFI_STATE = 0;
    public static final int WIFI_STATE_CONNECTED = 1;
    public static final int WIFI_STATE_DISCONNECTED = 0;
    private static final List<String> mCEList = Arrays.asList(CEList);
    private static final List<String> mFCCList = Arrays.asList(FCCList);
    private static DynamicSarService mInstance;
    private static IntentFilter mfilter = new IntentFilter();
    private final String TAG = "DynamicSarService";
    private ArrayList<SarControllerClient> mAudioReceiverListeners = new ArrayList<>();
    private boolean mCallStateActive;
    /* access modifiers changed from: private */
    public ArrayList<SarControllerClient> mChargeCONNECTEDListeners = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<SarControllerClient> mChargeDISCONNECTEDListeners = new ArrayList<>();
    private Context mContext;
    private int mCurrentAudioReceiverState;
    private int mCurrentHallSendorState;
    /* access modifiers changed from: private */
    public int mCurrentHotspotState;
    /* access modifiers changed from: private */
    public int mCurrentMccState;
    private int mCurrentModemState;
    /* access modifiers changed from: private */
    public int mCurrentSarSensorState;
    /* access modifiers changed from: private */
    public int mCurrentWifiState;
    private boolean mDataStateActive;
    private ArrayList<SarControllerClient> mHallSensorListeners = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<SarControllerClient> mHotspotListeners = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<SarControllerClient> mMccListeners = new ArrayList<>();
    private ArrayList<SarControllerClient> mModemListeners = new ArrayList<>();
    private final PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        public void onCallStateChanged(int state, String incomingNumber) {
            StringBuilder sb = new StringBuilder();
            sb.append("PhoneStateListener.onCallStateChanged: state=");
            sb.append(state);
            Log.d("DynamicSarService", sb.toString());
            DynamicSarService.this.refreshSensorListener(32, state);
        }

        public void onDataActivity(int direction) {
            StringBuilder sb = new StringBuilder();
            sb.append("PhoneStateListener.onDataActivity: direction=");
            sb.append(direction);
            Log.d("DynamicSarService", sb.toString());
            DynamicSarService.this.refreshSensorListener(128, direction);
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int wifiState;
            String action = intent.getAction();
            int hotspotState = 0;
            if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                if (((NetworkInfo) intent.getParcelableExtra("networkInfo")).isConnected()) {
                    wifiState = 1;
                } else {
                    wifiState = 0;
                }
                if (wifiState != DynamicSarService.this.mCurrentWifiState) {
                    DynamicSarService.this.callChangeListeners(DynamicSarService.this.mWifiListeners, 0, wifiState);
                    DynamicSarService.this.mCurrentWifiState = wifiState;
                }
            } else if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(action)) {
                if (intent.getIntExtra("wifi_state", 0) == 13) {
                    hotspotState = 1;
                }
                if (hotspotState != DynamicSarService.this.mCurrentHotspotState) {
                    DynamicSarService.this.callChangeListeners(DynamicSarService.this.mHotspotListeners, 1, hotspotState);
                    DynamicSarService.this.mCurrentHotspotState = hotspotState;
                }
            } else if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)) {
                DynamicSarService.this.callChangeListeners(DynamicSarService.this.mChargeCONNECTEDListeners, 6, 1);
            } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
                DynamicSarService.this.callChangeListeners(DynamicSarService.this.mChargeDISCONNECTEDListeners, 7, 0);
            } else if ("android.net.wifi.COUNTRY_CODE_CHANGED".equals(action)) {
                int mccState = DynamicSarService.this.getMccState();
                if (mccState != DynamicSarService.this.mCurrentMccState) {
                    DynamicSarService.this.callChangeListeners(DynamicSarService.this.mMccListeners, 8, mccState);
                    DynamicSarService.this.mCurrentMccState = mccState;
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<SarControllerClient> mSarSensorListeners = new ArrayList<>();
    private Sensor mSensor;
    private final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public final void onSensorChanged(SensorEvent event) {
            int type = event.sensor.getType();
            if (type == 0) {
                return;
            }
            if (type != DynamicSarService.SENSOR_TYPE_SARSENSOR) {
                StringBuilder sb = new StringBuilder();
                sb.append("onSensorChanged unknown event for sensor: ");
                sb.append(event.sensor.getType());
                Log.d("DynamicSarService", sb.toString());
                return;
            }
            int sarState = (int) event.values[0];
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onSensorChanged distance = ");
            sb2.append(sarState);
            Log.d("DynamicSarService", sb2.toString());
            if (sarState != DynamicSarService.this.mCurrentSarSensorState) {
                DynamicSarService.this.callChangeListeners(DynamicSarService.this.mSarSensorListeners, 3, sarState);
                DynamicSarService.this.mCurrentSarSensorState = sarState;
            }
        }
    };
    private SensorManager mSensorManager;
    private TelephonyManager mTelephonyManager;
    /* access modifiers changed from: private */
    public ArrayList<SarControllerClient> mWifiListeners = new ArrayList<>();
    private WifiManager mWifiManager;

    interface SarControllerClient {
        void onStateChanged(int i, int i2);
    }

    private DynamicSarService(Context context) {
        Log.d("DynamicSarService", "DynamicSarService init...");
        this.mContext = context;
        this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        Log.d("DynamicSarService", "DynamicSarService init done");
    }

    public static DynamicSarService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DynamicSarService(context);
        }
        return mInstance;
    }

    /* access modifiers changed from: private */
    public int getMccState() {
        int mMccState = 0;
        String mNetworkCountryCode = SystemProperties.get(PROPERTY_OPERATOR_ISO_COUNTRY, "");
        if (mNetworkCountryCode.length() < 2) {
            Log.d("DynamicSarService", "Can't get network countryCode, use MCC_DEFAULT");
            return 3;
        }
        String[] mCountryCode = mNetworkCountryCode.split(",");
        int i = 0;
        while (true) {
            if (i >= mCountryCode.length) {
                break;
            }
            mCountryCode[i] = mCountryCode[i].toLowerCase();
            if (mCountryCode[i] != null && !mCountryCode[i].isEmpty()) {
                if (!mFCCList.contains(mCountryCode[i])) {
                    if (!mCEList.contains(mCountryCode[i])) {
                        mMccState = 3;
                        break;
                    }
                    mMccState = 1;
                } else {
                    mMccState = 2;
                    break;
                }
            } else {
                mMccState = 3;
            }
            i++;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("mNetworkCountryCode: ");
        sb.append(mNetworkCountryCode);
        sb.append(", mMccState: ");
        sb.append(mMccState);
        Log.d("DynamicSarService", sb.toString());
        return mMccState;
    }

    private final void startPollAudioReceiverStatus() {
        new Thread() {
            public void run() {
                DynamicSarService.this.pollReceiver();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public final void pollReceiver() {
        int state = 0;
        while (true) {
            String AudioReceiverState = SystemProperties.get(PROPERTY_RECEIVER_STATUS);
            if (AudioReceiverState.equals("on")) {
                state = 1;
            } else if (AudioReceiverState.equals("off")) {
                state = 0;
            }
            if (state != this.mCurrentAudioReceiverState) {
                StringBuilder sb = new StringBuilder();
                sb.append("AudioReceiverState: ");
                sb.append(state);
                Log.d("DynamicSarService", sb.toString());
                callChangeListeners(this.mAudioReceiverListeners, 5, state);
                this.mCurrentAudioReceiverState = state;
            }
            SystemClock.sleep(1000);
        }
    }

    private void startPhoneListener() {
        this.mTelephonyManager.listen(this.mPhoneStateListener, 160);
    }

    /* access modifiers changed from: private */
    public void refreshSensorListener(int messageType, int state) {
        StringBuilder sb = new StringBuilder();
        sb.append("modem state change,messageType: ");
        sb.append(messageType);
        sb.append(" state: ");
        sb.append(state);
        Log.d("DynamicSarService", sb.toString());
        int modemState = 0;
        if (messageType == 128) {
            if (state == 1 || state == 2 || state == 3) {
                this.mDataStateActive = true;
            } else if (state == 0 || state == 4) {
                this.mDataStateActive = false;
            }
        } else if (messageType == 32) {
            if (state == 1 || state == 2) {
                this.mCallStateActive = true;
            } else if (state == 0) {
                this.mCallStateActive = false;
            }
        }
        if (this.mDataStateActive || this.mCallStateActive) {
            modemState = 1;
        }
        if (modemState != this.mCurrentModemState) {
            callChangeListeners(this.mModemListeners, 2, modemState);
            this.mCurrentModemState = modemState;
        }
    }

    private void GetInitialChargeStatus() {
        int status = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("status", -1);
        boolean isCharging = status == 2 || status == 5;
        if (isCharging) {
            callChangeListeners(this.mChargeCONNECTEDListeners, 6, 1);
        } else if (!isCharging) {
            callChangeListeners(this.mChargeDISCONNECTEDListeners, 7, 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerStateChangeListener(int type, SarControllerClient listener) {
        switch (type) {
            case 0:
                mfilter.addAction("android.net.wifi.STATE_CHANGE");
                this.mWifiListeners.add(listener);
                break;
            case 1:
                mfilter.addAction("android.net.wifi.WIFI_AP_STATE_CHANGED");
                this.mHotspotListeners.add(listener);
                break;
            case 2:
                this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
                startPhoneListener();
                this.mModemListeners.add(listener);
                break;
            case 3:
                this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
                this.mSensor = this.mSensorManager.getDefaultSensor(SENSOR_TYPE_SARSENSOR, true);
                this.mSensorManager.registerListener(this.mSensorEventListener, this.mSensor, 3);
                this.mSarSensorListeners.add(listener);
                break;
            case 4:
                this.mHallSensorListeners.add(listener);
                break;
            case 5:
                startPollAudioReceiverStatus();
                this.mAudioReceiverListeners.add(listener);
                break;
            case 6:
                mfilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
                this.mChargeCONNECTEDListeners.add(listener);
                GetInitialChargeStatus();
                break;
            case 7:
                mfilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                this.mChargeDISCONNECTEDListeners.add(listener);
                GetInitialChargeStatus();
                break;
            case 8:
                mfilter.addAction("android.net.wifi.COUNTRY_CODE_CHANGED");
                this.mMccListeners.add(listener);
                break;
        }
        this.mContext.registerReceiver(this.mReceiver, mfilter);
    }

    /* access modifiers changed from: 0000 */
    public void callChangeListeners(ArrayList<SarControllerClient> listeners, int type, int value) {
        if (listeners.size() != 0) {
            for (int i = 0; i < listeners.size(); i++) {
                ((SarControllerClient) listeners.get(i)).onStateChanged(type, value);
            }
        }
    }
}
