package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.os.AsyncResult;
import android.os.IHwBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.SmsResponse;
import java.util.concurrent.atomic.AtomicLong;
import vendor.qti.hardware.radio.qtiradio.V1_0.IQtiRadio;
import vendor.qti.hardware.radio.qtiradio.V1_0.QtiRadioResponseInfo;
import vendor.qti.hardware.radio.qtiradio.V2_0.DcParam;
import vendor.qti.hardware.radio.qtiradio.V2_0.SignalStrength;
import vendor.qti.hardware.radio.qtiradio.V2_1.UpperLayerIndInfo;
import vendor.qti.hardware.radio.qtiradio.V2_2.IQtiRadioIndication;
import vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadioResponse;

public final class QtiRIL extends RIL {
    static final String[] QTI_HIDL_SERVICE_NAME = {"slot1", "slot2", "slot3"};
    static final String TAG = "QTIRILJ";
    IQtiRadioIndication.Stub mClientRadioIndicationCb;
    IQtiRadioResponse.Stub mClientRadioResponseCb;
    final QtiRadioProxyDeathRecipient mDeathRecipient;
    int mQtiPhoneId;
    private IQtiRadio mQtiRadio;
    QtiRadioIndication mQtiRadioIndication;
    final AtomicLong mQtiRadioProxyCookie;
    QtiRadioResponse mQtiRadioResponse;
    private final QtiRadioServiceNotification mServiceNotification;

    final class QtiRadioProxyDeathRecipient implements IHwBinder.DeathRecipient {
        QtiRadioProxyDeathRecipient() {
        }

        public void serviceDied(long cookie) {
            Rlog.d(QtiRIL.TAG, "serviceDied");
            QtiRIL.this.resetServiceAndRequestList();
        }
    }

    /* access modifiers changed from: private */
    public void resetServiceAndRequestList() {
        resetProxyAndRequestList();
        this.mQtiRadio = null;
        this.mQtiRadioResponse = null;
        this.mQtiRadioIndication = null;
        this.mQtiRadioProxyCookie.incrementAndGet();
    }

    /* access modifiers changed from: private */
    public boolean isQtiRadioServiceConnected() {
        return this.mQtiRadio != null;
    }

    final class QtiRadioServiceNotification extends IServiceNotification.Stub {
        QtiRadioServiceNotification() {
        }

        public void onRegistration(String fqName, String name, boolean preexisting) {
            Rlog.d(QtiRIL.TAG, "QtiRadio interface service started " + fqName + " " + name + " preexisting =" + preexisting);
            if (!QtiRIL.this.isQtiRadioServiceConnected()) {
                QtiRIL.this.initQtiRadio();
            }
        }
    }

    private void registerForQtiRadioServiceNotification() {
        try {
            if (!IServiceManager.getService().registerForNotifications(IQtiRadio.kInterfaceName, QTI_HIDL_SERVICE_NAME[this.mQtiPhoneId], this.mServiceNotification)) {
                Rlog.e(TAG, "Failed to register for service start notifications");
            }
        } catch (RemoteException ex) {
            Rlog.e(TAG, "Failed to register for service start notifications. Exception " + ex);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void initQtiRadio() {
        try {
            this.mQtiRadio = IQtiRadio.getService(QTI_HIDL_SERVICE_NAME[this.mQtiPhoneId]);
            if (this.mQtiRadio == null) {
                Rlog.e(TAG, "initQtiRadio: mQtiRadio is null. Return");
                return;
            }
            Rlog.d(TAG, "initQtiRadio: mQtiRadio" + this.mQtiRadio);
            this.mQtiRadio.linkToDeath(this.mDeathRecipient, this.mQtiRadioProxyCookie.incrementAndGet());
            this.mQtiRadioResponse = new QtiRadioResponse(this);
            this.mQtiRadioIndication = new QtiRadioIndication(this.mQtiPhoneId);
            this.mQtiRadio.setCallback(this.mQtiRadioResponse, this.mQtiRadioIndication);
        } catch (Exception ex) {
            Rlog.e(TAG, "initQtiRadio: Exception: " + ex);
            resetServiceAndRequestList();
        }
        return;
    }

    public QtiRIL(Context context, int preferredNetworkType, int cdmaSubscription) {
        this(context, preferredNetworkType, cdmaSubscription, (Integer) null);
    }

    public QtiRIL(Context context, int preferredNetworkType, int cdmaSubscription, Integer instanceId) {
        super(context, preferredNetworkType, cdmaSubscription, instanceId);
        this.mQtiPhoneId = 0;
        this.mQtiRadioProxyCookie = new AtomicLong(0);
        this.mServiceNotification = new QtiRadioServiceNotification();
        this.mQtiPhoneId = instanceId.intValue();
        Rlog.d(TAG, "QtiRIL");
        this.mDeathRecipient = new QtiRadioProxyDeathRecipient();
        registerForQtiRadioServiceNotification();
    }

    public IQtiRadio getQtiRadioProxy(Message result) {
        if (!this.mIsMobileNetworkSupported) {
            Rlog.d(TAG, "getQtiRadioProxy: Not calling getService(): wifi-only");
            if (result != null) {
                AsyncResult.forMessage(result, (Object) null, CommandException.fromRilErrno(1));
                result.sendToTarget();
            }
            return null;
        }
        if (this.mQtiRadio == null) {
            Rlog.d(TAG, "getQtiRadioProxy: mRadioProxy == null");
            if (result != null) {
                AsyncResult.forMessage(result, (Object) null, CommandException.fromRilErrno(1));
                result.sendToTarget();
            }
        }
        return this.mQtiRadio;
    }

    public void enable5g(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.enable5g(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void disable5g(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.disable5g(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void enable5gOnly(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.enable5gOnly(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void query5gStatus(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.query5gStatus(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void queryNrDcParam(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.queryNrDcParam(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void queryNrBearerAllocation(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.queryNrBearerAllocation(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void queryNrSignalStrength(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_0 != null) {
            radioProxy2_0.queryNrSignalStrength(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void queryUpperLayerIndInfo(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_1.IQtiRadio radioProxy2_1 = vendor.qti.hardware.radio.qtiradio.V2_1.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_1 != null) {
            radioProxy2_1.queryUpperLayerIndInfo(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void queryNrIconType(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_2.IQtiRadio radioProxy2_2 = vendor.qti.hardware.radio.qtiradio.V2_2.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_2 != null) {
            radioProxy2_2.queryNrIconType(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void query5gConfigInfo(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_1.IQtiRadio radioProxy2_1 = vendor.qti.hardware.radio.qtiradio.V2_1.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_1 != null) {
            radioProxy2_1.query5gConfigInfo(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public int getPropertyValueInt(String property, int def) throws RemoteException {
        int i = def;
        vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio radioProxy2_3 = vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_3 != null) {
            try {
                return radioProxy2_3.getPropertyValueInt(property, def);
            } catch (RemoteException e) {
                throw new RemoteException("API Error");
            }
        } else {
            Rlog.e(TAG, "getPropertyValueInt HAL API not available");
            return SystemProperties.getInt(property, def);
        }
    }

    public boolean getPropertyValueBool(String property, boolean def) throws RemoteException {
        boolean z = def;
        vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio radioProxy2_3 = vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_3 != null) {
            try {
                return radioProxy2_3.getPropertyValueBool(property, def);
            } catch (RemoteException e) {
                throw new RemoteException("API Error");
            }
        } else {
            Rlog.e(TAG, "getPropertyValueBool HAL API not available");
            return SystemProperties.getBoolean(property, def);
        }
    }

    public String getPropertyValueString(String property, String def) throws RemoteException {
        String str = def;
        vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio radioProxy2_3 = vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_3 != null) {
            try {
                return radioProxy2_3.getPropertyValueString(property, def);
            } catch (RemoteException e) {
                throw new RemoteException("API Error");
            }
        } else {
            Rlog.e(TAG, "getPropertyValueString HAL API not available");
            return SystemProperties.get(property, def);
        }
    }

    public void enableEndc(int serial, boolean enable) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio radioProxy2_3 = vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_3 != null) {
            radioProxy2_3.enableEndc(serial, enable);
            return;
        }
        throw new RemoteException("API not available!");
    }

    public void queryEndcStatus(int serial) throws RemoteException {
        vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio radioProxy2_3 = vendor.qti.hardware.radio.qtiradio.V2_3.IQtiRadio.castFrom(this.mQtiRadio);
        if (radioProxy2_3 != null) {
            radioProxy2_3.queryEndcStatus(serial);
            return;
        }
        throw new RemoteException("API not available!");
    }

    private String convertNullToEmptyString(String string) {
        return string != null ? string : "";
    }

    public void sendCdmaSms(byte[] pdu, Message result) {
        sendCdmaSms(pdu, result, false);
    }

    public void sendCdmaSms(byte[] pdu, Message result, boolean expectMore) {
        if (!SystemProperties.get("persist.radio.feature").equals("CDMA_SMS")) {
            Rlog.d(TAG, "Feature not enabled, fall back to default sendCdmaSms");
            QtiRIL.super.sendCdmaSms(pdu, result);
            return;
        }
        vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio radioProxy2_0 = vendor.qti.hardware.radio.qtiradio.V2_0.IQtiRadio.castFrom(getQtiRadioProxy((Message) null));
        if (radioProxy2_0 != null) {
            int serial = obtainRequestSerial(87, result, this.mRILDefaultWorkSource);
            Rlog.d(TAG, "[" + serial + "] >  RIL_REQUEST_CDMA_SEND_SMS expectMore=" + expectMore);
            CdmaSmsMessage msg = new CdmaSmsMessage();
            constructCdmaSendSmsRilRequest(msg, pdu);
            try {
                radioProxy2_0.sendCdmaSms(serial, msg, expectMore);
                this.mMetrics.writeRilSendSms(this.mQtiPhoneId, serial, 2, 2);
            } catch (RemoteException | RuntimeException e) {
                resetServiceAndRequestList();
            }
        } else {
            Rlog.d(TAG, "fall back to default sendCdmaSms");
            QtiRIL.super.sendCdmaSms(pdu, result);
        }
    }

    /* access modifiers changed from: package-private */
    public Message qtiGetMessageFromRequest(Object request) {
        return getMessageFromRequest(request);
    }

    /* access modifiers changed from: package-private */
    public Object qtiProcessResponse(RadioResponseInfo responseInfo) {
        return processResponse(responseInfo);
    }

    /* access modifiers changed from: package-private */
    public void qtiProcessResponseDone(Object ret, RadioResponseInfo responseInfo, Object str) {
        processResponseDone(ret, responseInfo, str);
    }

    public void setCallbacks(IQtiRadioResponse.Stub qtiRadioResponse, IQtiRadioIndication.Stub qtiRadioIndication) {
        this.mClientRadioResponseCb = qtiRadioResponse;
        this.mClientRadioIndicationCb = qtiRadioIndication;
    }

    public class QtiRadioResponse extends IQtiRadioResponse.Stub {
        static final String QTI_RILJ_LOG_TAG = "QtiRadioResponse";
        QtiRIL mRil;

        public QtiRadioResponse(QtiRIL ril) {
            this.mRil = ril;
        }

        /* access modifiers changed from: package-private */
        public void sendMessageResponse(Message msg, Object ret) {
            if (msg != null) {
                AsyncResult.forMessage(msg, ret, (Throwable) null);
                msg.sendToTarget();
            }
        }

        /* access modifiers changed from: package-private */
        public RadioResponseInfo toRadioResponseInfo(QtiRadioResponseInfo qtiResponseInfo) {
            RadioResponseInfo responseInfo = new RadioResponseInfo();
            responseInfo.type = qtiResponseInfo.type;
            responseInfo.serial = qtiResponseInfo.serial;
            responseInfo.error = qtiResponseInfo.error;
            return responseInfo;
        }

        private void responseString(RadioResponseInfo responseInfo, String str) {
            Object request = this.mRil.qtiProcessResponse(responseInfo);
            Message result = this.mRil.qtiGetMessageFromRequest(request);
            if (result != null) {
                if (responseInfo.error == 0) {
                    sendMessageResponse(result, str);
                }
                this.mRil.qtiProcessResponseDone(request, responseInfo, str);
            }
        }

        public void getAtrResponse(QtiRadioResponseInfo qtiResponseInfo, String atr) {
            Rlog.d(QTI_RILJ_LOG_TAG, "getAtrResponse");
            responseString(toRadioResponseInfo(qtiResponseInfo), atr);
        }

        public void onEnable5gResponse(int serial, int errorCode, int status) {
            Rlog.d(QtiRIL.TAG, "onEnable5gResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onEnable5gResponse(serial, errorCode, status);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onDisable5gResponse(int serial, int errorCode, int status) {
            Rlog.d(QtiRIL.TAG, "onDisable5gResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onDisable5gResponse(serial, errorCode, status);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onEnable5gOnlyResponse(int serial, int errorCode, int status) {
            Rlog.d(QtiRIL.TAG, "onEnable5gOnlyResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onEnable5gOnlyResponse(serial, errorCode, status);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void on5gStatusResponse(int serial, int errorCode, int enabled) {
            Rlog.d(QtiRIL.TAG, "on5gStatusResponse: serial = " + serial + " errorCode = " + errorCode + " enabled = " + enabled);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.on5gStatusResponse(serial, errorCode, enabled);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrDcParamResponse(int serial, int errorCode, DcParam dcParam) {
            Rlog.d(QtiRIL.TAG, "onNrDcParamResponse: serial = " + serial + " errorCode = " + errorCode + " dcParam = " + dcParam);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onNrDcParamResponse(serial, errorCode, dcParam);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrBearerAllocationResponse_2_1(int serial, int errorCode, int bearerStatus) {
            Rlog.d(QtiRIL.TAG, "onNrBearerAllocationResponse_2_1: serial = " + serial + " errorCode = " + errorCode + " bearerStatus = " + bearerStatus);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onNrBearerAllocationResponse_2_1(serial, errorCode, bearerStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrBearerAllocationResponse(int serial, int errorCode, int bearerStatus) {
            Rlog.d(QtiRIL.TAG, "onNrBearerAllocationResponse: serial = " + serial + " errorCode = " + errorCode + " bearerStatus = " + bearerStatus);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onNrBearerAllocationResponse(serial, errorCode, bearerStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onUpperLayerIndInfoResponse(int serial, int errorCode, UpperLayerIndInfo uliInfo) {
            Rlog.d(QtiRIL.TAG, "UpperLayerIndInfoResponse: serial = " + serial + " errorCode = " + errorCode + " UpperLayerIndInfo = " + uliInfo);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onUpperLayerIndInfoResponse(serial, errorCode, uliInfo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void on5gConfigInfoResponse(int serial, int errorCode, int confType) {
            Rlog.d(QtiRIL.TAG, "on5gConfigInfoResponse: serial = " + serial + " errorCode = " + errorCode + " ConfigType = " + confType);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.on5gConfigInfoResponse(serial, errorCode, confType);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onSignalStrengthResponse(int serial, int errorCode, SignalStrength signalStrength) {
            Rlog.d(QtiRIL.TAG, "onSignalStrengthResponse: serial = " + serial + " errorCode = " + errorCode + " signalStrength = " + signalStrength);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onSignalStrengthResponse(serial, errorCode, signalStrength);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrIconTypeResponse(int serial, int errorCode, int iconType) {
            Rlog.d(QtiRIL.TAG, "onNrIconTypeResponse: serial = " + serial + " errorCode = " + errorCode + " iconType = " + iconType);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onNrIconTypeResponse(serial, errorCode, iconType);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onEnableEndcResponse(int serial, int errorCode, int status) {
            Rlog.d(QtiRIL.TAG, "onEnableEndcResponse: serial = " + serial + " errorCode = " + errorCode + " status = " + status);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onEnableEndcResponse(serial, errorCode, status);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onEndcStatusResponse(int serial, int errorCode, int endcStatus) {
            Rlog.d(QtiRIL.TAG, "onEndcStatusResponse: serial = " + serial + " errorCode = " + errorCode + " endcStatus = " + endcStatus);
            if (QtiRIL.this.mClientRadioResponseCb != null) {
                try {
                    QtiRIL.this.mClientRadioResponseCb.onEndcStatusResponse(serial, errorCode, endcStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        private void responseSms(RadioResponseInfo responseInfo, SendSmsResult sms) {
            Object request = this.mRil.qtiProcessResponse(responseInfo);
            if (request != null) {
                Message result = this.mRil.qtiGetMessageFromRequest(request);
                SmsResponse ret = new SmsResponse(sms.messageRef, sms.ackPDU, sms.errorCode);
                if (responseInfo.error == 0) {
                    sendMessageResponse(result, ret);
                }
                this.mRil.qtiProcessResponseDone(request, responseInfo, ret);
            }
        }

        public void sendCdmaSmsResponse(QtiRadioResponseInfo qtiResponseInfo, SendSmsResult sms) {
            Rlog.d(QtiRIL.TAG, "sendCdmaSmsResponse");
            RadioResponseInfo responseInfo = toRadioResponseInfo(qtiResponseInfo);
            Rlog.d(QTI_RILJ_LOG_TAG, "[" + qtiResponseInfo.serial + "] < RIL_REQUEST_CDMA_SEND_SMS ");
            responseSms(responseInfo, sms);
        }
    }

    public class QtiRadioIndication extends IQtiRadioIndication.Stub {
        static final String TAG = "QtiRadioIndication";
        int mSlotId;

        public QtiRadioIndication(int slotId) {
            Rlog.d(TAG, "[" + slotId + "]Constructor: ");
            this.mSlotId = slotId;
        }

        public void on5gStatusChange(int enableStatus) {
            Rlog.d(TAG, "on5gStatusChange: slotId = " + this.mSlotId);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.on5gStatusChange(enableStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrDcParamChange(DcParam dcParam) {
            Rlog.d(TAG, "onNrDcParamChange: slotId = " + this.mSlotId);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.onNrDcParamChange(dcParam);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrBearerAllocationChange_2_1(int bearerStatus) {
            Rlog.d(TAG, "onNrBearerAllocationChange_2_1: slotId = " + this.mSlotId);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.onNrBearerAllocationChange_2_1(bearerStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrBearerAllocationChange(int bearerStatus) {
            Rlog.d(TAG, "onNrBearerAllocationChange: slotId = " + this.mSlotId);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.onNrBearerAllocationChange(bearerStatus);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onUpperLayerIndInfoChange(UpperLayerIndInfo uliInfo) {
            Rlog.d(TAG, "onUpperLayerIndInfoChange: UpperLayerIndInfo = " + uliInfo);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.onUpperLayerIndInfoChange(uliInfo);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void on5gConfigInfoChange(int confType) {
            Rlog.d(TAG, "on5gConfigInfoChange: ConfigType = " + confType);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.on5gConfigInfoChange(confType);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onNrIconTypeChange(int iconType) {
            Rlog.d(TAG, "onNrIconTypeChange: iconType = " + iconType);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.onNrIconTypeChange(iconType);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onSignalStrengthChange(SignalStrength signalStrength) {
            Rlog.d(TAG, "onSignalStrengthChange: slotId = " + this.mSlotId);
            if (QtiRIL.this.mClientRadioIndicationCb != null) {
                try {
                    QtiRIL.this.mClientRadioIndicationCb.onSignalStrengthChange(signalStrength);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public void qtiRadioIndication(int value) {
            Rlog.d(TAG, "qtiRadioIndication: NOP!!");
        }
    }
}
