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
import org.codeaurora.internal.NrConfigType;
import org.codeaurora.internal.NrIconType;
import org.codeaurora.internal.Status;
import org.codeaurora.internal.Token;
import vendor.qti.hardware.radio.qtiradio.V1_0.QtiRadioResponseInfo;
import vendor.qti.hardware.radio.qtiradio.V2_0.DcParam;
import vendor.qti.hardware.radio.qtiradio.V2_0.SignalStrength;
import vendor.qti.hardware.radio.qtiradio.V2_1.UpperLayerIndInfo;
import vendor.qti.hardware.radio.qtiradio.V2_2.IQtiRadioIndication.Stub;
import vendor.qti.hardware.radio.qtiradio.V2_2.IQtiRadioResponse;

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

    public class QtiRadioIndication extends Stub {
        static final String TAG = "QtiRadioIndication";
        int mSlotId;

        public QtiRadioIndication(int slotId) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(slotId);
            sb.append("]Constructor: ");
            Log.d(str, sb.toString());
            this.mSlotId = slotId;
        }

        public void on5gStatusChange(int enableStatus) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("on5gStatusChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mCallback != null) {
                boolean enabled = enableStatus == 1;
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("on5gStatusChange: enabled = ");
                sb2.append(enabled);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), enabled);
            }
        }

        public void onNrDcParamChange(DcParam dcParam) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrDcParamChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            org.codeaurora.internal.DcParam aidlDcParam = QtiRadioHidlClient.this.convertHidl2Aidl(dcParam);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onNrDcParamChange: ");
            sb2.append(aidlDcParam);
            Log.d(str2, sb2.toString());
            QtiRadioHidlClient.this.mCallback.onNrDcParam(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), aidlDcParam);
        }

        public void onNrBearerAllocationChange_2_1(int bearerStatus) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrBearerAllocationChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            BearerAllocationStatus bStatus = QtiRadioHidlClient.this.convertHidlBearerStatus2Aidl(bearerStatus);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onNrBearerAllocationChange: bStatus = ");
            sb2.append(bStatus);
            Log.d(str2, sb2.toString());
            QtiRadioHidlClient.this.mCallback.onAnyNrBearerAllocation(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), bStatus);
        }

        public void onNrBearerAllocationChange(int bearerStatus) {
            onNrBearerAllocationChange_2_1(bearerStatus);
        }

        public void onSignalStrengthChange(SignalStrength signalStrength) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onSignalStrengthChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            org.codeaurora.internal.SignalStrength aidlSignalStrength = QtiRadioHidlClient.this.convertHidl2Aidl(signalStrength);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onSignalStrengthChange: ");
            sb2.append(signalStrength);
            Log.d(str2, sb2.toString());
            QtiRadioHidlClient.this.mCallback.onSignalStrength(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), aidlSignalStrength);
        }

        public void onUpperLayerIndInfoChange(UpperLayerIndInfo uliInfo) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onUpperLayerIndInfoChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            org.codeaurora.internal.UpperLayerIndInfo upperLayerInfo = QtiRadioHidlClient.this.convertHidl2Aidl(uliInfo);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onUpperLayerIndInfoChange:  upperLayerInfo = ");
            sb2.append(upperLayerInfo);
            Log.d(str2, sb2.toString());
            QtiRadioHidlClient.this.mCallback.onUpperLayerIndInfo(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), upperLayerInfo);
        }

        public void on5gConfigInfoChange(int configType) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("on5gConfigInfoChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            NrConfigType nrConfigType = QtiRadioHidlClient.this.convertHidlConfigType2Aidl(configType);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("on5gConfigInfoChange:  5gConfigType = ");
            sb2.append(nrConfigType);
            Log.d(str2, sb2.toString());
            QtiRadioHidlClient.this.mCallback.on5gConfigInfo(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), nrConfigType);
        }

        public void onNrIconTypeChange(int iconType) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrIconTypeChange: slotId = ");
            sb.append(this.mSlotId);
            Log.d(str, sb.toString());
            NrIconType nrIconType = QtiRadioHidlClient.this.convertHidlNrIconType2Aidl(iconType);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onNrIconTypeChange:  NrIconType = ");
            sb2.append(nrIconType);
            Log.d(str2, sb2.toString());
            QtiRadioHidlClient.this.mCallback.onNrIconType(this.mSlotId, QtiRadioHidlClient.this.UNSOL, new Status(1), nrIconType);
        }

        public void qtiRadioIndication(int value) {
            Log.d(TAG, "qtiRadioIndication: NOP!!");
        }
    }

    public class QtiRadioResponse extends IQtiRadioResponse.Stub {
        static final String TAG = "QtiRadioResponse";
        int mSlotId;

        public QtiRadioResponse(int slotId) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(slotId);
            sb.append("] Constructor: ");
            Log.d(str, sb.toString());
            this.mSlotId = slotId;
        }

        public void onEnable5gResponse(int serial, int errorCode, int status) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onEnable5gResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" status = ");
            sb.append(status);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                boolean enabled = QtiRadioHidlClient.this.isEnableOrDisableSucess(errorCode);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onEnable5gResponse: enabled = ");
                sb2.append(enabled);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), enabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onEnable5gResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onDisable5gResponse(int serial, int errorCode, int status) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onDisable5gResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" status = ");
            sb.append(status);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                boolean enabled = QtiRadioHidlClient.this.isEnableOrDisableSucess(errorCode);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onDisable5gResponse: enabled = ");
                sb2.append(!enabled);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), !enabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onDisable5gResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onEnable5gOnlyResponse(int serial, int errorCode, int status) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onEnable5gOnlyResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" status = ");
            sb.append(status);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                boolean enabled = QtiRadioHidlClient.this.isEnableOrDisableSucess(errorCode);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onEnable5gOnlyResponse: enabled = ");
                sb2.append(enabled);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), enabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onEnable5gOnlyResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void on5gStatusResponse(int serial, int errorCode, int enabled) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("on5gStatusResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" enabled = ");
            sb.append(enabled);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                boolean isEnabled = true;
                if (enabled != 1) {
                    isEnabled = false;
                }
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("on5gStatusResponse: enabled = ");
                sb2.append(isEnabled);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.on5gStatus(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), isEnabled);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("on5gStatusResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onNrDcParamResponse(int serial, int errorCode, DcParam dcParam) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrDcParamResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" dcParam = ");
            sb.append(dcParam);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                org.codeaurora.internal.DcParam aidlDcParam = QtiRadioHidlClient.this.convertHidl2Aidl(dcParam);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onNrDcParamResponse:  ");
                sb2.append(aidlDcParam);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.onNrDcParam(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), aidlDcParam);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onNrDcParamResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onNrBearerAllocationResponse_2_1(int serial, int errorCode, int bearerStatus) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrBearerAllocationResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" bearerStatus = ");
            sb.append(bearerStatus);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                BearerAllocationStatus bStatus = QtiRadioHidlClient.this.convertHidlBearerStatus2Aidl(bearerStatus);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onNrBearerAllocationResponse:  allocated = ");
                sb2.append(bStatus);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.onAnyNrBearerAllocation(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), bStatus);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onNrBearerAllocationResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onNrBearerAllocationResponse(int serial, int errorCode, int bearerStatus) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrBearerAllocationResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" bearerStatus = ");
            sb.append(bearerStatus);
            Log.d(str, sb.toString());
            onNrBearerAllocationResponse_2_1(serial, errorCode, bearerStatus);
        }

        public void onUpperLayerIndInfoResponse(int serial, int errorCode, UpperLayerIndInfo uliInfo) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onUpperLayerIndInfoResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" UpperLayerIndInfo = ");
            sb.append(uliInfo);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                org.codeaurora.internal.UpperLayerIndInfo upperLayerInfo = QtiRadioHidlClient.this.convertHidl2Aidl(uliInfo);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onUpperLayerIndInfoResponse:  upperLayerInfo = ");
                sb2.append(upperLayerInfo);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.onUpperLayerIndInfo(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), upperLayerInfo);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onUpperLayerIndInfoResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void on5gConfigInfoResponse(int serial, int errorCode, int configType) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("on5gConfigInfoResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" ConfigType = ");
            sb.append(configType);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                NrConfigType nrConfigType = QtiRadioHidlClient.this.convertHidlConfigType2Aidl(configType);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("on5gConfigInfoResponse:  NrConfigType = ");
                sb2.append(nrConfigType);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.on5gConfigInfo(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), nrConfigType);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("on5gConfigInfoResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onNrIconTypeResponse(int serial, int errorCode, int iconType) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrIconTypeResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" iconType = ");
            sb.append(iconType);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                NrIconType nrIconType = QtiRadioHidlClient.this.convertHidlNrIconType2Aidl(iconType);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onNrIconTypeResponse:  NrIconType = ");
                sb2.append(nrIconType);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.onNrIconType(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), nrIconType);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onNrIconTypeResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void onSignalStrengthResponse(int serial, int errorCode, SignalStrength signalStrength) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onSignalStrengthResponse: serial = ");
            sb.append(serial);
            sb.append(" errorCode = ");
            sb.append(errorCode);
            sb.append(" signalStrength = ");
            sb.append(signalStrength);
            Log.d(str, sb.toString());
            if (QtiRadioHidlClient.this.mInflightRequests.containsKey(Integer.valueOf(serial))) {
                Token token = (Token) QtiRadioHidlClient.this.mInflightRequests.get(Integer.valueOf(serial));
                org.codeaurora.internal.SignalStrength aidlSignalStrength = QtiRadioHidlClient.this.convertHidl2Aidl(signalStrength);
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("onSignalStrengthResponse:  ");
                sb2.append(aidlSignalStrength);
                Log.d(str2, sb2.toString());
                QtiRadioHidlClient.this.mCallback.onSignalStrength(this.mSlotId, token, QtiRadioHidlClient.this.convertHidl2Aidl(errorCode), aidlSignalStrength);
                QtiRadioHidlClient.this.mInflightRequests.remove(Integer.valueOf(serial));
                return;
            }
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("onSignalStrengthResponse: No previous request found for serial = ");
            sb3.append(serial);
            Log.d(str3, sb3.toString());
        }

        public void getAtrResponse(QtiRadioResponseInfo qtiResponseInfo, String atr) {
            Log.d(TAG, "getAtrResponse: NOP!!");
        }

        public void sendCdmaSmsResponse(QtiRadioResponseInfo qtiResponseInfo, SendSmsResult sms) {
            Log.d(TAG, "sendCdmaSmsResponse: NOP!!");
        }
    }

    private int getPhoneCount() {
        return TelephonyManager.getDefault().getPhoneCount();
    }

    /* access modifiers changed from: private */
    public org.codeaurora.internal.DcParam convertHidl2Aidl(DcParam dcParam) {
        return new org.codeaurora.internal.DcParam(dcParam.endc, dcParam.dcnr);
    }

    /* access modifiers changed from: private */
    public org.codeaurora.internal.UpperLayerIndInfo convertHidl2Aidl(UpperLayerIndInfo ulInfo) {
        return new org.codeaurora.internal.UpperLayerIndInfo(ulInfo.plmnInfoList, ulInfo.upplerLayerInd);
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
    public org.codeaurora.internal.SignalStrength convertHidl2Aidl(SignalStrength signalStrength) {
        return new org.codeaurora.internal.SignalStrength(signalStrength.rsrp, signalStrength.snr);
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

    private QtiTelephonyComponentFactory getQtiTelephonyComponentFactory() {
        return QtiTelephonyComponentFactory.getInstance();
    }

    /* access modifiers changed from: private */
    public void setCallbacks(int slotId) {
        QtiTelephonyComponentFactory factory = getQtiTelephonyComponentFactory();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("ril[");
        sb.append(slotId);
        sb.append("]: ");
        sb.append(factory.getRil(slotId));
        Log.d(str, sb.toString());
        factory.getRil(slotId).setCallbacks(new QtiRadioResponse(slotId), new QtiRadioIndication(slotId));
    }

    private void register() {
        Log.d(TAG, "Register");
        int phones = getPhoneCount();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("Phone count = ");
        sb.append(phones);
        Log.d(str, sb.toString());
        IntStream.range(0, phones).forEach(new IntConsumer() {
            public final void accept(int i) {
                QtiRadioHidlClient.this.setCallbacks(i);
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
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("enable5g: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).enable5g(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("enable5g: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token disable5g(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("disable5g: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).disable5g(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("disable5g: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token enable5gOnly(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("enable5gOnly: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).enable5gOnly(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("enable5gOnly: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token query5gStatus(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("query5gStatus: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).query5gStatus(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("query5gStatus: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrDcParam(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrDcParam: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrDcParam(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("queryNrDcParam: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrBearerAllocation(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrBearerAllocation: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrBearerAllocation(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("queryNrBearerAllocation: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrSignalStrength(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrSignalStrength: slotId = ");
        sb.append(slotId);
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrSignalStrength(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("queryNrSignalStrength: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryUpperLayerIndInfo(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryUpperLayerIndInfo: slotId = ");
        sb.append(slotId);
        sb.append(" token ");
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryUpperLayerIndInfo(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("queryUpperLayerIndInfo: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token query5gConfigInfo(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("query5gConfigInfo: slotId = ");
        sb.append(slotId);
        sb.append(" token ");
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).query5gConfigInfo(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("query5gConfigInfo: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public Token queryNrIconType(int slotId) throws RemoteException {
        Token token = getNextToken();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrIconType: slotId = ");
        sb.append(slotId);
        sb.append(" token ");
        sb.append(token);
        Log.d(str, sb.toString());
        int serial = token.get();
        this.mInflightRequests.put(Integer.valueOf(serial), token);
        try {
            getQtiTelephonyComponentFactory().getRil(slotId).queryNrIconType(serial);
            return token;
        } catch (RemoteException | NullPointerException e) {
            this.mInflightRequests.remove(Integer.valueOf(serial), token);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("queryNrIconType: slotId = ");
            sb2.append(slotId);
            sb2.append(" Exception = ");
            sb2.append(e);
            Log.d(TAG, sb2.toString());
            e.printStackTrace();
            throw e;
        }
    }

    public void registerCallback(IHidlConnectionCallback callback) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("registerCallback: callback = ");
        sb.append(callback);
        Log.d(str, sb.toString());
        this.mCallback = callback;
    }

    public void unRegisterCallback(IHidlConnectionCallback callback) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("unRegisterCallback: callback = ");
        sb.append(callback);
        Log.d(str, sb.toString());
        if (this.mCallback == callback) {
            this.mCallback = null;
        }
    }
}
