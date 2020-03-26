package com.qualcomm.qti.internal.nrNetworkService.hidl;

import android.hardware.radio.V1_0.SendSmsResult;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.qualcomm.qti.internal.telephony.QtiTelephonyComponentFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.codeaurora.internal.BearerAllocationStatus;
import org.codeaurora.internal.DcParam;
import org.codeaurora.internal.NrConfigType;
import org.codeaurora.internal.NrIconType;
import org.codeaurora.internal.SignalStrength;
import org.codeaurora.internal.Status;
import org.codeaurora.internal.Token;
import org.codeaurora.internal.UpperLayerIndInfo;
import vendor.qti.hardware.radio.qtiradio.V1_0.QtiRadioResponseInfo;
import vendor.qti.hardware.radio.qtiradio.V2_2.IQtiRadioIndication;
import vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadioResponse;

public class QtiRadioHidlClient implements IHidlConnectionInterface {
    private static final String TAG = "QtiRadioHidlClient";
    private final int MAX_SLOTS = 2;
    /* access modifiers changed from: private */
    public final Token UNSOL = new Token(-1);
    /* access modifiers changed from: private */
    public IHidlConnectionCallback mCallback;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Integer, Token> mInflightRequests = new ConcurrentHashMap<>();
    private int mSerial = -1;

    private int getPhoneCount() {
        return TelephonyManager.getDefault().getPhoneCount();
    }

    /* access modifiers changed from: private */
    public DcParam convertHidl2Aidl(vendor.qti.hardware.radio.qtiradio.V2_0.DcParam dcParam) {
        return new DcParam(dcParam.endc, dcParam.dcnr);
    }

    /* access modifiers changed from: private */
    public UpperLayerIndInfo convertHidl2Aidl(vendor.qti.hardware.radio.qtiradio.V2_1.UpperLayerIndInfo ulInfo) {
        return new UpperLayerIndInfo(ulInfo.plmnInfoList, ulInfo.upplerLayerInd);
    }

    /* access modifiers changed from: private */
    public NrConfigType convertHidlConfigType2Aidl(int configType) {
        return new NrConfigType(configType);
    }

    /* access modifiers changed from: private */
    public NrIconType convertHidlNrIconType2Aidl(int iconType) {
        return new NrIconType(iconType);
    }

    /* access modifiers changed from: private */
    public SignalStrength convertHidl2Aidl(vendor.qti.hardware.radio.qtiradio.V2_0.SignalStrength signalStrength) {
        return new SignalStrength(signalStrength.rsrp, signalStrength.snr);
    }

    /* access modifiers changed from: private */
    public Status convertHidl2Aidl(int rilErrorCode) {
        return new Status(rilErrorCode == 0 ? 1 : 0);
    }

    /* access modifiers changed from: private */
    public BearerAllocationStatus convertHidlBearerStatus2Aidl(int bearerStatus) {
        return new BearerAllocationStatus(bearerStatus);
    }

    /* access modifiers changed from: private */
    public boolean isEnableOrDisableSucess(int errorCode) {
        return errorCode == 0;
    }

    public class QtiRadioResponse extends IQtiRadioResponse.Stub {
        static final String TAG = "QtiRadioResponse";
        int mSlotId;

        public QtiRadioResponse(int slotId) {
            Log.d(TAG, "[" + slotId + "] Constructor: ");
            this.mSlotId = slotId;
        }

        public void onEnable5gResponse(int serial, int errorCode, int status) {
            Log.d(TAG, "onEnable5gResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                boolean enabled = QtiRadioHidlClient.this.isEnableOrDisableSucess(errorCode);
                Log.d(TAG, "onEnable5gResponse: enabled = " + enabled);
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), enabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onEnable5gResponse: No previous request found for serial = " + serial);
        }

        public void onDisable5gResponse(int serial, int errorCode, int status) {
            Log.d(TAG, "onDisable5gResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                boolean enabled = QtiRadioHidlClient.this.isEnableOrDisableSucess(errorCode);
                StringBuilder sb = new StringBuilder();
                sb.append("onDisable5gResponse: enabled = ");
                sb.append(!enabled);
                Log.d(TAG, sb.toString());
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), !enabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onDisable5gResponse: No previous request found for serial = " + serial);
        }

        public void onEnable5gOnlyResponse(int serial, int errorCode, int status) {
            Log.d(TAG, "onEnable5gOnlyResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                boolean enabled = QtiRadioHidlClient.this.isEnableOrDisableSucess(errorCode);
                Log.d(TAG, "onEnable5gOnlyResponse: enabled = " + enabled);
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), enabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onEnable5gOnlyResponse: No previous request found for serial = " + serial);
        }

        public void on5gStatusResponse(int serial, int errorCode, int enabled) {
            Log.d(TAG, "on5gStatusResponse: serial = " + serial + " errorCode = " + errorCode + " enabled = " + enabled);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                boolean isEnabled = true;
                if (enabled != 1) {
                    isEnabled = false;
                }
                Log.d(TAG, "on5gStatusResponse: enabled = " + isEnabled);
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), isEnabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "on5gStatusResponse: No previous request found for serial = " + serial);
        }

        public void onNrDcParamResponse(int serial, int errorCode, vendor.qti.hardware.radio.qtiradio.V2_0.DcParam dcParam) {
            Log.d(TAG, "onNrDcParamResponse: serial = " + serial + " errorCode = " + errorCode + " dcParam = " + dcParam);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                DcParam aidlDcParam = QtiRadioHidlClient.this.convertHidl2Aidl(dcParam);
                Log.d(TAG, "onNrDcParamResponse:  " + aidlDcParam);
                QtiRadioHidlClient.this.mCallback.onNrDcParam(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), aidlDcParam);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onNrDcParamResponse: No previous request found for serial = " + serial);
        }

        public void onNrBearerAllocationResponse_2_1(int serial, int errorCode, int bearerStatus) {
            Log.d(TAG, "onNrBearerAllocationResponse: serial = " + serial + " errorCode = " + errorCode + " bearerStatus = " + bearerStatus);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                BearerAllocationStatus bStatus = QtiRadioHidlClient.this.convertHidlBearerStatus2Aidl(bearerStatus);
                Log.d(TAG, "onNrBearerAllocationResponse:  allocated = " + bStatus);
                QtiRadioHidlClient.this.mCallback.onAnyNrBearerAllocation(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), bStatus);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onNrBearerAllocationResponse: No previous request found for serial = " + serial);
        }

        public void onNrBearerAllocationResponse(int serial, int errorCode, int bearerStatus) {
            Log.d(TAG, "onNrBearerAllocationResponse: serial = " + serial + " errorCode = " + errorCode + " bearerStatus = " + bearerStatus);
            onNrBearerAllocationResponse_2_1(serial, errorCode, bearerStatus);
        }

        public void onUpperLayerIndInfoResponse(int serial, int errorCode, vendor.qti.hardware.radio.qtiradio.V2_1.UpperLayerIndInfo uliInfo) {
            Log.d(TAG, "onUpperLayerIndInfoResponse: serial = " + serial + " errorCode = " + errorCode + " UpperLayerIndInfo = " + uliInfo);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                UpperLayerIndInfo upperLayerInfo = QtiRadioHidlClient.this.convertHidl2Aidl(uliInfo);
                Log.d(TAG, "onUpperLayerIndInfoResponse:  upperLayerInfo = " + upperLayerInfo);
                QtiRadioHidlClient.this.mCallback.onUpperLayerIndInfo(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), upperLayerInfo);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onUpperLayerIndInfoResponse: No previous request found for serial = " + serial);
        }

        public void on5gConfigInfoResponse(int serial, int errorCode, int configType) {
            Log.d(TAG, "on5gConfigInfoResponse: serial = " + serial + " errorCode = " + errorCode + " ConfigType = " + configType);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                NrConfigType nrConfigType = QtiRadioHidlClient.this.convertHidlConfigType2Aidl(configType);
                Log.d(TAG, "on5gConfigInfoResponse:  NrConfigType = " + nrConfigType);
                QtiRadioHidlClient.this.mCallback.on5gConfigInfo(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), nrConfigType);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "on5gConfigInfoResponse: No previous request found for serial = " + serial);
        }

        public void onNrIconTypeResponse(int serial, int errorCode, int iconType) {
            Log.d(TAG, "onNrIconTypeResponse: serial = " + serial + " errorCode = " + errorCode + " iconType = " + iconType);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                NrIconType nrIconType = QtiRadioHidlClient.this.convertHidlNrIconType2Aidl(iconType);
                Log.d(TAG, "onNrIconTypeResponse:  NrIconType = " + nrIconType);
                QtiRadioHidlClient.this.mCallback.onNrIconType(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), nrIconType);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onNrIconTypeResponse: No previous request found for serial = " + serial);
        }

        public void onSignalStrengthResponse(int serial, int errorCode, vendor.qti.hardware.radio.qtiradio.V2_0.SignalStrength signalStrength) {
            Log.d(TAG, "onSignalStrengthResponse: serial = " + serial + " errorCode = " + errorCode + " signalStrength = " + signalStrength);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                SignalStrength aidlSignalStrength = QtiRadioHidlClient.this.convertHidl2Aidl(signalStrength);
                Log.d(TAG, "onSignalStrengthResponse:  " + aidlSignalStrength);
                QtiRadioHidlClient.this.mCallback.onSignalStrength(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), aidlSignalStrength);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onSignalStrengthResponse: No previous request found for serial = " + serial);
        }

        public void onEnableEndcResponse(int serial, int errorCode, int status) {
            Log.d(TAG, "onEnableEndcResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Log.d(TAG, "onEnableEndcResponse: status = " + status);
                QtiRadioHidlClient.this.mCallback.onEnableEndc(this.mSlotId, (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial)), QtiRadioHidlClient.this.convertHidl2Aidl(errorCode));
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onEnableEndcResponse: No previous request found for serial = " + serial);
        }

        public void onEndcStatusResponse(int serial, int errorCode, int enabled) {
            Log.d(TAG, "onEndcStatusResponse: serial = " + serial + " errorCode = " + errorCode + " enabled = " + enabled);
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                boolean isEnabled = true;
                if (enabled != 1) {
                    isEnabled = false;
                }
                Log.d(TAG, "onEndcStatusResponse: enabled = " + isEnabled);
                QtiRadioHidlClient.this.mCallback.onEndcStatus(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), isEnabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            Log.d(TAG, "onEndcStatusResponse: No previous request found for serial = " + serial);
        }

        public void getAtrResponse(QtiRadioResponseInfo qtiResponseInfo, String atr) {
            Log.d(TAG, "getAtrResponse: NOP!!");
        }

        public void sendCdmaSmsResponse(QtiRadioResponseInfo qtiResponseInfo, SendSmsResult sms) {
            Log.d(TAG, "sendCdmaSmsResponse: NOP!!");
        }
    }

    public class QtiRadioIndication extends IQtiRadioIndication.Stub {
        static final String TAG = "QtiRadioIndication";
        int mSlotId;

        public QtiRadioIndication(int slotId) {
            Log.d(TAG, "[" + slotId + "]Constructor: ");
            this.mSlotId = slotId;
        }

        public void on5gStatusChange(int enableStatus) {
            Log.d(TAG, "on5gStatusChange: slotId = " + this.mSlotId);
            if (QtiRadioHidlClient.this.mCallback != null) {
                boolean enabled = enableStatus == 1;
                Log.d(TAG, "on5gStatusChange: enabled = " + enabled);
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), enabled);
            }
        }

        public void onNrDcParamChange(vendor.qti.hardware.radio.qtiradio.V2_0.DcParam dcParam) {
            Log.d(TAG, "onNrDcParamChange: slotId = " + this.mSlotId);
            DcParam aidlDcParam = QtiRadioHidlClient.this.convertHidl2Aidl(dcParam);
            Log.d(TAG, "onNrDcParamChange: " + aidlDcParam);
            QtiRadioHidlClient.this.mCallback.onNrDcParam(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), aidlDcParam);
        }

        public void onNrBearerAllocationChange_2_1(int bearerStatus) {
            Log.d(TAG, "onNrBearerAllocationChange: slotId = " + this.mSlotId);
            BearerAllocationStatus bStatus = QtiRadioHidlClient.this.convertHidlBearerStatus2Aidl(bearerStatus);
            Log.d(TAG, "onNrBearerAllocationChange: bStatus = " + bStatus);
            QtiRadioHidlClient.this.mCallback.onAnyNrBearerAllocation(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), bStatus);
        }

        public void onNrBearerAllocationChange(int bearerStatus) {
            onNrBearerAllocationChange_2_1(bearerStatus);
        }

        public void onSignalStrengthChange(vendor.qti.hardware.radio.qtiradio.V2_0.SignalStrength signalStrength) {
            Log.d(TAG, "onSignalStrengthChange: slotId = " + this.mSlotId);
            SignalStrength aidlSignalStrength = QtiRadioHidlClient.this.convertHidl2Aidl(signalStrength);
            Log.d(TAG, "onSignalStrengthChange: " + signalStrength);
            QtiRadioHidlClient.this.mCallback.onSignalStrength(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), aidlSignalStrength);
        }

        public void onUpperLayerIndInfoChange(vendor.qti.hardware.radio.qtiradio.V2_1.UpperLayerIndInfo uliInfo) {
            Log.d(TAG, "onUpperLayerIndInfoChange: slotId = " + this.mSlotId);
            UpperLayerIndInfo upperLayerInfo = QtiRadioHidlClient.this.convertHidl2Aidl(uliInfo);
            Log.d(TAG, "onUpperLayerIndInfoChange:  upperLayerInfo = " + upperLayerInfo);
            QtiRadioHidlClient.this.mCallback.onUpperLayerIndInfo(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), upperLayerInfo);
        }

        public void on5gConfigInfoChange(int configType) {
            Log.d(TAG, "on5gConfigInfoChange: slotId = " + this.mSlotId);
            NrConfigType nrConfigType = QtiRadioHidlClient.this.convertHidlConfigType2Aidl(configType);
            Log.d(TAG, "on5gConfigInfoChange:  5gConfigType = " + nrConfigType);
            QtiRadioHidlClient.this.mCallback.on5gConfigInfo(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), nrConfigType);
        }

        public void onNrIconTypeChange(int iconType) {
            Log.d(TAG, "onNrIconTypeChange: slotId = " + this.mSlotId);
            NrIconType nrIconType = QtiRadioHidlClient.this.convertHidlNrIconType2Aidl(iconType);
            Log.d(TAG, "onNrIconTypeChange:  NrIconType = " + nrIconType);
            QtiRadioHidlClient.this.mCallback.onNrIconType(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), nrIconType);
        }

        public void qtiRadioIndication(int value) {
            Log.d(TAG, "qtiRadioIndication: NOP!!");
        }
    }

    private QtiTelephonyComponentFactory getQtiTelephonyComponentFactory() {
        return QtiTelephonyComponentFactory.getInstance();
    }

    /* access modifiers changed from: private */
    /* renamed from: setCallbacks */
    public void lambda$register$0$QtiRadioHidlClient(int slotId) {
        QtiTelephonyComponentFactory factory = getQtiTelephonyComponentFactory();
        Log.d(TAG, "ril[" + slotId + "]: " + factory.getRil(slotId));
        factory.getRil(slotId).setCallbacks(new QtiRadioResponse(slotId), new QtiRadioIndication(slotId));
    }

    private void register() {
        Log.d(TAG, "Register");
        int phones = getPhoneCount();
        Log.d(TAG, "Phone count = " + phones);
        IntStream.range(0, phones).forEach(new IntConsumer() {
            public final void accept(int i) {
                QtiRadioHidlClient.this.lambda$register$0$QtiRadioHidlClient(i);
            }
        });
    }

    public QtiRadioHidlClient() {
        Log.d(TAG, "constructor");
        register();
    }

    private Token getNextToken() {
        int i = this.mSerial + 1;
        this.mSerial = i;
        return new Token(i);
    }

    public Token enable5g(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "enable5g: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).enable5g(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "enable5g: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token disable5g(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "disable5g: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).disable5g(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "disable5g: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token enable5gOnly(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "enable5gOnly: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).enable5gOnly(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "enable5gOnly: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token query5gStatus(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "query5gStatus: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).query5gStatus(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "query5gStatus: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrDcParam(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "queryNrDcParam: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrDcParam(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "queryNrDcParam: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrBearerAllocation(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "queryNrBearerAllocation: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrBearerAllocation(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "queryNrBearerAllocation: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrSignalStrength(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "queryNrSignalStrength: slotId = " + slotId + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrSignalStrength(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "queryNrSignalStrength: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryUpperLayerIndInfo(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "queryUpperLayerIndInfo: slotId = " + slotId + " token " + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryUpperLayerIndInfo(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "queryUpperLayerIndInfo: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token query5gConfigInfo(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "query5gConfigInfo: slotId = " + slotId + " token " + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).query5gConfigInfo(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "query5gConfigInfo: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrIconType(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "queryNrIconType: slotId = " + slotId + " token " + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrIconType(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "queryNrIconType: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token enableEndc(int slotId, boolean enable) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "enableEndc: slotId = " + slotId + " token " + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).enableEndc(serial, enable);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "enableEndc: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryEndcStatus(int slotId) throws RemoteException {
        Token token = getNextToken();
        Log.d(TAG, "queryEndcStatus: slotId = " + slotId + " token " + token);
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryEndcStatus(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            Log.d(TAG, "queryEndcStatus: slotId = " + slotId + " Exception = " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public void registerCallback(IHidlConnectionCallback callback) {
        Log.d(TAG, "registerCallback: callback = " + callback);
        this.mCallback = callback;
    }

    public void unRegisterCallback(IHidlConnectionCallback callback) {
        Log.d(TAG, "unRegisterCallback: callback = " + callback);
        if (this.mCallback == callback) {
            this.mCallback = null;
        }
    }
}
