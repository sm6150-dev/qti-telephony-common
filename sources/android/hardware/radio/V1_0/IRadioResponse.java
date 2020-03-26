package android.hardware.radio.V1_0;

import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.qualcomm.qti.internal.telephony.primarycard.SubsidyLockSettingsObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface IRadioResponse extends IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadioResponse";

    void acceptCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeRequest(int i) throws RemoteException;

    IHwBinder asBinder();

    void cancelPendingUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void changeIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void changeIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void conferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void deactivateDataCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteSmsOnRuimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void deleteSmsOnSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void dialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void exitEmergencyCallbackModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void explicitCallTransferResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException;

    void getAvailableBandModesResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException;

    void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, ArrayList<OperatorInfo> arrayList) throws RemoteException;

    void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void getCDMASubscriptionResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException;

    void getCallForwardStatusResponse(RadioResponseInfo radioResponseInfo, ArrayList<CallForwardInfo> arrayList) throws RemoteException;

    void getCallWaitingResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException;

    void getCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void getCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getCellInfoListResponse(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) throws RemoteException;

    void getClipResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getClirResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) throws RemoteException;

    void getDataCallListResponse(RadioResponseInfo radioResponseInfo, ArrayList<SetupDataCallResult> arrayList) throws RemoteException;

    void getDataRegistrationStateResponse(RadioResponseInfo radioResponseInfo, DataRegStateResult dataRegStateResult) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException;

    void getFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<HardwareConfig> arrayList) throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIMSIForAppResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException;

    void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException;

    void getLastCallFailCauseResponse(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException;

    void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException;

    void getMuteResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getNeighboringCidsResponse(RadioResponseInfo radioResponseInfo, ArrayList<NeighboringCell> arrayList) throws RemoteException;

    void getNetworkSelectionModeResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getOperatorResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException;

    void getPreferredNetworkTypeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException;

    void getSignalStrengthResponse(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException;

    void getSmscAddressResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void getTTYModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getVoiceRadioTechnologyResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getVoiceRegistrationStateResponse(RadioResponseInfo radioResponseInfo, VoiceRegStateResult voiceRegStateResult) throws RemoteException;

    void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupForegroundResumeBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupWaitingOrBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void iccCloseLogicalChannelResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void iccIOForAppResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void iccOpenLogicalChannelResponse(RadioResponseInfo radioResponseInfo, int i, ArrayList<Byte> arrayList) throws RemoteException;

    void iccTransmitApduBasicChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void iccTransmitApduLogicalChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void ping() throws RemoteException;

    void pullLceDataResponse(RadioResponseInfo radioResponseInfo, LceDataInfo lceDataInfo) throws RemoteException;

    void rejectCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void reportSmsMemoryStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void reportStkServiceIsRunningResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void requestIccSimAuthenticationResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void requestIsimAuthenticationResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void requestShutdownResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendBurstDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendCDMAFeatureCodeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendEnvelopeResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void sendEnvelopeWithStatusResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendSMSExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendTerminalResponseToSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void separateConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void setBandModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setBarringPasswordResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallForwardResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallWaitingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCdmaBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCellInfoListRateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setClirResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDataProfileResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void setGsmBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIndicationFilterResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setInitialAttachApnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLocationUpdatesResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setMuteResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkSelectionModeManualResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPreferredNetworkTypeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException;

    void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSimCardPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSmscAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSuppServiceNotificationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setTTYModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setUiccSubscriptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setupDataCallResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException;

    void startDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void startLceServiceResponse(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) throws RemoteException;

    void stopDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void stopLceServiceResponse(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) throws RemoteException;

    void supplyIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyIccPuk2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyIccPukForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void writeSmsToRuimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    static IRadioResponse asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IRadioResponse)) {
            return (IRadioResponse) iface;
        }
        IRadioResponse proxy = new Proxy(binder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException e) {
        }
        return null;
    }

    static IRadioResponse castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IRadioResponse getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IRadioResponse getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    static IRadioResponse getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    static IRadioResponse getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadioResponse {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of android.hardware.radio@1.0::IRadioResponse]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void getIccCardStatusResponse(RadioResponseInfo info, CardStatus cardStatus) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            cardStatus.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPinForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPukForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPin2ForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPuk2ForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPinForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPin2ForAppResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyNetworkDepersonalizationResponse(RadioResponseInfo info, int remainingRetries) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(remainingRetries);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentCallsResponse(RadioResponseInfo info, ArrayList<Call> calls) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            Call.writeVectorToParcel(_hidl_request, calls);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dialResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIMSIForAppResponse(RadioResponseInfo info, String imsi) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(imsi);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupConnectionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupWaitingOrBackgroundResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void conferenceResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void rejectCallResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLastCallFailCauseResponse(RadioResponseInfo info, LastCallFailCauseInfo failCauseinfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            failCauseinfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrengthResponse(RadioResponseInfo info, SignalStrength sigStrength) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sigStrength.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationStateResponse(RadioResponseInfo info, VoiceRegStateResult voiceRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            voiceRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationStateResponse(RadioResponseInfo info, DataRegStateResult dataRegResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dataRegResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getOperatorResponse(RadioResponseInfo info, String longName, String shortName, String numeric) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(longName);
            _hidl_request.writeString(shortName);
            _hidl_request.writeString(numeric);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioPowerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSmsResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSMSExpectMoreResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCallResponse(RadioResponseInfo info, SetupDataCallResult dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            dcResponse.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccIOForAppResponse(RadioResponseInfo info, IccIoResult iccIo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            iccIo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendUssdResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelPendingUssdResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClirResponse(RadioResponseInfo info, int n, int m) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(n);
            _hidl_request.writeInt32(m);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setClirResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallForwardStatusResponse(RadioResponseInfo info, ArrayList<CallForwardInfo> callForwardInfos) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CallForwardInfo.writeVectorToParcel(_hidl_request, callForwardInfos);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallForwardResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallWaitingResponse(RadioResponseInfo info, boolean enable, int serviceClass) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(serviceClass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallWaitingResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acceptCallResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateDataCallResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getFacilityLockForAppResponse(RadioResponseInfo info, int response) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(response);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFacilityLockForAppResponse(RadioResponseInfo info, int retry) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(retry);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBarringPasswordResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNetworkSelectionModeResponse(RadioResponseInfo info, boolean manual) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(manual);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManualResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableNetworksResponse(RadioResponseInfo info, ArrayList<OperatorInfo> networkInfos) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            OperatorInfo.writeVectorToParcel(_hidl_request, networkInfos);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBasebandVersionResponse(RadioResponseInfo info, String version) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(version);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void separateConnectionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setMuteResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getMuteResponse(RadioResponseInfo info, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClipResponse(RadioResponseInfo info, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataCallListResponse(RadioResponseInfo info, ArrayList<SetupDataCallResult> dcResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            SetupDataCallResult.writeVectorToParcel(_hidl_request, dcResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(54, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSuppServiceNotificationsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(55, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToSimResponse(RadioResponseInfo info, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(56, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnSimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(57, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBandModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(58, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableBandModesResponse(RadioResponseInfo info, ArrayList<Integer> bandModes) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32Vector(bandModes);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(59, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelopeResponse(RadioResponseInfo info, String commandResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(commandResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(60, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendTerminalResponseToSimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(61, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(62, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void explicitCallTransferResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(63, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredNetworkTypeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(64, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredNetworkTypeResponse(RadioResponseInfo info, int nwType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(nwType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(65, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNeighboringCidsResponse(RadioResponseInfo info, ArrayList<NeighboringCell> cells) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            NeighboringCell.writeVectorToParcel(_hidl_request, cells);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(66, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLocationUpdatesResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(67, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaSubscriptionSourceResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(68, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaRoamingPreferenceResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(69, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaRoamingPreferenceResponse(RadioResponseInfo info, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(70, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTTYModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(71, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getTTYModeResponse(RadioResponseInfo info, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(72, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredVoicePrivacyResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(73, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredVoicePrivacyResponse(RadioResponseInfo info, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(74, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCDMAFeatureCodeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(75, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendBurstDtmfResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(76, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCdmaSmsResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(77, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(78, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastConfigResponse(RadioResponseInfo info, ArrayList<GsmBroadcastSmsConfigInfo> configs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(79, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastConfigResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(80, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastActivationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(81, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaBroadcastConfigResponse(RadioResponseInfo info, ArrayList<CdmaBroadcastSmsConfigInfo> configs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configs);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(82, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastConfigResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(83, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastActivationResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(84, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCDMASubscriptionResponse(RadioResponseInfo info, String mdn, String hSid, String hNid, String min, String prl) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(mdn);
            _hidl_request.writeString(hSid);
            _hidl_request.writeString(hNid);
            _hidl_request.writeString(min);
            _hidl_request.writeString(prl);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(85, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToRuimResponse(RadioResponseInfo info, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(86, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnRuimResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(87, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDeviceIdentityResponse(RadioResponseInfo info, String imei, String imeisv, String esn, String meid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(imei);
            _hidl_request.writeString(imeisv);
            _hidl_request.writeString(esn);
            _hidl_request.writeString(meid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(88, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void exitEmergencyCallbackModeResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(89, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmscAddressResponse(RadioResponseInfo info, String smsc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(smsc);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(90, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmscAddressResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(91, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportSmsMemoryStatusResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(92, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportStkServiceIsRunningResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(93, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaSubscriptionSourceResponse(RadioResponseInfo info, int source) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(source);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(94, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIsimAuthenticationResponse(RadioResponseInfo info, String response) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(response);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(95, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(96, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelopeWithStatusResponse(RadioResponseInfo info, IccIoResult iccIo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            iccIo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(97, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRadioTechnologyResponse(RadioResponseInfo info, int rat) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(rat);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(98, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoListResponse(RadioResponseInfo info, ArrayList<CellInfo> cellInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            CellInfo.writeVectorToParcel(_hidl_request, cellInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(99, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCellInfoListRateResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(100, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApnResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(101, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsRegistrationStateResponse(RadioResponseInfo info, boolean isRegistered, int ratFamily) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(isRegistered);
            _hidl_request.writeInt32(ratFamily);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(102, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendImsSmsResponse(RadioResponseInfo info, SendSmsResult sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(SubsidyLockSettingsObserver.SUBSIDY_UNLOCKED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduBasicChannelResponse(RadioResponseInfo info, IccIoResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(104, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccOpenLogicalChannelResponse(RadioResponseInfo info, int channelId, ArrayList<Byte> selectResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(channelId);
            _hidl_request.writeInt8Vector(selectResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(105, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccCloseLogicalChannelResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(106, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo info, IccIoResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(107, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvReadItemResponse(RadioResponseInfo info, String result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeString(result);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(108, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteItemResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(109, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteCdmaPrlResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(110, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvResetConfigResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(111, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setUiccSubscriptionResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.APN_TYPE_CONFLICT, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataAllowedResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_PCSCF_ADDR, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getHardwareConfigResponse(RadioResponseInfo info, ArrayList<HardwareConfig> config) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HardwareConfig.writeVectorToParcel(_hidl_request, config);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIccSimAuthenticationResponse(RadioResponseInfo info, IccIoResult result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.EMM_ACCESS_BARRED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfileResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.EMERGENCY_IFACE_ONLY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestShutdownResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.IFACE_MISMATCH, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRadioCapabilityResponse(RadioResponseInfo info, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.COMPANION_IFACE_IN_USE, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioCapabilityResponse(RadioResponseInfo info, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.IP_ADDRESS_MISMATCH, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startLceServiceResponse(RadioResponseInfo info, LceStatusInfo statusInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            statusInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.IFACE_AND_POL_FAMILY_MISMATCH, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopLceServiceResponse(RadioResponseInfo info, LceStatusInfo statusInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            statusInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.EMM_ACCESS_BARRED_INFINITE_RETRY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void pullLceDataResponse(RadioResponseInfo info, LceDataInfo lceInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            lceInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.AUTH_FAILURE_ON_EMERGENCY_CALL, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getModemActivityInfoResponse(RadioResponseInfo info, ActivityStatsInfo activityInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            activityInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(123, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setAllowedCarriersResponse(RadioResponseInfo info, int numAllowed) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeInt32(numAllowed);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(124, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAllowedCarriersResponse(RadioResponseInfo info, boolean allAllowed, CarrierRestrictions carriers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            _hidl_request.writeBool(allAllowed);
            carriers.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(125, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDeviceStateResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(126, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilterResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(LastCallFailCause.INTERWORKING_UNSPECIFIED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimCardPowerResponse(RadioResponseInfo info) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(128, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeRequest(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadioResponse.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(129, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readStringVector();
            } finally {
                _hidl_reply.release();
            }
        }

        public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            _hidl_request.writeNativeHandle(fd);
            _hidl_request.writeStringVector(options);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public String interfaceDescriptor() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readString();
            } finally {
                _hidl_reply.release();
            }
        }

        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
                HwBlob _hidl_blob = _hidl_reply.readBuffer(16);
                int _hidl_vec_size = _hidl_blob.getInt32(8);
                HwBlob childBlob = _hidl_reply.readEmbeddedBuffer((long) (_hidl_vec_size * 32), _hidl_blob.handle(), 0, true);
                _hidl_out_hashchain.clear();
                for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                    byte[] _hidl_vec_element = new byte[32];
                    childBlob.copyToInt8Array((long) (_hidl_index_0 * 32), _hidl_vec_element, 32);
                    _hidl_out_hashchain.add(_hidl_vec_element);
                }
                return _hidl_out_hashchain;
            } finally {
                _hidl_reply.release();
            }
        }

        public void setHALInstrumentation() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        public void ping() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                DebugInfo _hidl_out_info = new DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        public void notifySyspropsChanged() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadioResponse {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IRadioResponse.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IRadioResponse.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{29, 74, 87, 118, 97, 76, 8, -75, -41, -108, -91, -20, 90, -80, 70, -105, 38, 12, -67, 75, 52, 65, -43, -109, 92, -43, 62, -25, 29, 25, -38, 2}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
        }

        public final void setHALInstrumentation() {
        }

        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        public final void ping() {
        }

        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0;
            info.arch = 0;
            return info;
        }

        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        public IHwInterface queryLocalInterface(String descriptor) {
            if (IRadioResponse.kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws RemoteException {
            registerService(serviceName);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v169, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v171, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v172, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v173, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v174, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v175, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v176, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v178, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v179, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v180, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v181, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v182, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v183, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v184, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v185, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v186, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v187, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v188, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v189, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v190, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v191, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v192, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v193, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v194, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v195, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v196, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v197, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v198, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v199, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v200, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v201, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v202, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v203, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v204, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v205, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v206, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v207, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v208, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v209, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v210, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v211, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v212, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v213, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v214, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v215, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v216, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v217, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v218, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v219, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v220, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v221, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v222, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v223, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v224, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v225, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v226, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v227, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v228, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v229, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v230, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v231, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v232, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v233, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v234, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v235, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v236, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v237, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v238, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v239, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v240, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v241, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v242, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v243, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v244, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v245, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v246, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v247, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v248, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v249, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v250, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v251, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v252, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v253, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v254, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v255, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v256, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v257, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v258, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v259, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v260, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v262, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v263, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v264, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v265, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v266, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v267, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v268, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v269, resolved type: boolean} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onTransact(int r18, android.os.HwParcel r19, android.os.HwParcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r7 = r17
                r8 = r19
                r9 = r20
                java.lang.String r0 = "android.hardware.radio@1.0::IRadioResponse"
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = 0
                r3 = 1
                switch(r18) {
                    case 1: goto L_0x128f;
                    case 2: goto L_0x126e;
                    case 3: goto L_0x124d;
                    case 4: goto L_0x122a;
                    case 5: goto L_0x1207;
                    case 6: goto L_0x11e4;
                    case 7: goto L_0x11c1;
                    case 8: goto L_0x119e;
                    case 9: goto L_0x117b;
                    case 10: goto L_0x115c;
                    case 11: goto L_0x1139;
                    case 12: goto L_0x111a;
                    case 13: goto L_0x10fb;
                    case 14: goto L_0x10dc;
                    case 15: goto L_0x10bd;
                    case 16: goto L_0x109e;
                    case 17: goto L_0x107f;
                    case 18: goto L_0x1058;
                    case 19: goto L_0x1031;
                    case 20: goto L_0x100a;
                    case 21: goto L_0x0fe3;
                    case 22: goto L_0x0fb8;
                    case 23: goto L_0x0f99;
                    case 24: goto L_0x0f7a;
                    case 25: goto L_0x0f53;
                    case 26: goto L_0x0f2c;
                    case 27: goto L_0x0f05;
                    case 28: goto L_0x0ede;
                    case 29: goto L_0x0ebf;
                    case 30: goto L_0x0ea0;
                    case 31: goto L_0x0e79;
                    case 32: goto L_0x0e5a;
                    case 33: goto L_0x0e37;
                    case 34: goto L_0x0e18;
                    case 35: goto L_0x0df1;
                    case 36: goto L_0x0dd2;
                    case 37: goto L_0x0db3;
                    case 38: goto L_0x0d94;
                    case 39: goto L_0x0d75;
                    case 40: goto L_0x0d52;
                    case 41: goto L_0x0d2f;
                    case 42: goto L_0x0d10;
                    case 43: goto L_0x0ced;
                    case 44: goto L_0x0cce;
                    case 45: goto L_0x0caf;
                    case 46: goto L_0x0c8c;
                    case 47: goto L_0x0c6d;
                    case 48: goto L_0x0c4e;
                    case 49: goto L_0x0c2b;
                    case 50: goto L_0x0c0c;
                    case 51: goto L_0x0bed;
                    case 52: goto L_0x0bca;
                    case 53: goto L_0x0ba7;
                    case 54: goto L_0x0b84;
                    case 55: goto L_0x0b65;
                    case 56: goto L_0x0b42;
                    case 57: goto L_0x0b23;
                    case 58: goto L_0x0b04;
                    case 59: goto L_0x0ae1;
                    case 60: goto L_0x0abe;
                    case 61: goto L_0x0a9f;
                    case 62: goto L_0x0a80;
                    case 63: goto L_0x0a61;
                    case 64: goto L_0x0a42;
                    case 65: goto L_0x0a1f;
                    case 66: goto L_0x09fc;
                    case 67: goto L_0x09dd;
                    case 68: goto L_0x09be;
                    case 69: goto L_0x099f;
                    case 70: goto L_0x097c;
                    case 71: goto L_0x095d;
                    case 72: goto L_0x093a;
                    case 73: goto L_0x091b;
                    case 74: goto L_0x08f8;
                    case 75: goto L_0x08d9;
                    case 76: goto L_0x08ba;
                    case 77: goto L_0x0893;
                    case 78: goto L_0x0874;
                    case 79: goto L_0x0851;
                    case 80: goto L_0x0832;
                    case 81: goto L_0x0813;
                    case 82: goto L_0x07f0;
                    case 83: goto L_0x07d1;
                    case 84: goto L_0x07b2;
                    case 85: goto L_0x0774;
                    case 86: goto L_0x0751;
                    case 87: goto L_0x0732;
                    case 88: goto L_0x06fa;
                    case 89: goto L_0x06db;
                    case 90: goto L_0x06b8;
                    case 91: goto L_0x0699;
                    case 92: goto L_0x067a;
                    case 93: goto L_0x065b;
                    case 94: goto L_0x0638;
                    case 95: goto L_0x0615;
                    case 96: goto L_0x05f6;
                    case 97: goto L_0x05cf;
                    case 98: goto L_0x05ac;
                    case 99: goto L_0x0589;
                    case 100: goto L_0x056a;
                    case 101: goto L_0x054b;
                    case 102: goto L_0x0524;
                    case 103: goto L_0x04fd;
                    case 104: goto L_0x04d6;
                    case 105: goto L_0x04af;
                    case 106: goto L_0x0490;
                    case 107: goto L_0x0469;
                    case 108: goto L_0x0446;
                    case 109: goto L_0x0427;
                    case 110: goto L_0x0408;
                    case 111: goto L_0x03e9;
                    case 112: goto L_0x03ca;
                    case 113: goto L_0x03ab;
                    case 114: goto L_0x0388;
                    case 115: goto L_0x0361;
                    case 116: goto L_0x0342;
                    case 117: goto L_0x0323;
                    case 118: goto L_0x02fc;
                    case 119: goto L_0x02d5;
                    case 120: goto L_0x02ae;
                    case 121: goto L_0x0287;
                    case 122: goto L_0x0260;
                    case 123: goto L_0x0239;
                    case 124: goto L_0x0216;
                    case 125: goto L_0x01eb;
                    case 126: goto L_0x01cc;
                    case 127: goto L_0x01ad;
                    case 128: goto L_0x018e;
                    case 129: goto L_0x0173;
                    default: goto L_0x000f;
                }
            L_0x000f:
                java.lang.String r0 = "android.hidl.base@1.0::IBase"
                switch(r18) {
                    case 256067662: goto L_0x0151;
                    case 256131655: goto L_0x012b;
                    case 256136003: goto L_0x0109;
                    case 256398152: goto L_0x00a4;
                    case 256462420: goto L_0x008d;
                    case 256660548: goto L_0x007d;
                    case 256921159: goto L_0x005f;
                    case 257049926: goto L_0x003d;
                    case 257120595: goto L_0x0026;
                    case 257250372: goto L_0x0016;
                    default: goto L_0x0014;
                }
            L_0x0014:
                goto L_0x12b4
            L_0x0016:
                r0 = r21 & 1
                if (r0 == 0) goto L_0x001b
                r2 = r3
            L_0x001b:
                r0 = r2
                if (r0 == 0) goto L_0x12b4
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0026:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x002b
                r2 = r3
            L_0x002b:
                if (r2 == r3) goto L_0x0035
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0035:
                r8.enforceInterface(r0)
                r17.notifySyspropsChanged()
                goto L_0x12b4
            L_0x003d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0042
                goto L_0x0043
            L_0x0042:
                r3 = r2
            L_0x0043:
                if (r3 == 0) goto L_0x004d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x004d:
                r8.enforceInterface(r0)
                android.hidl.base.V1_0.DebugInfo r0 = r17.getDebugInfo()
                r9.writeStatus(r2)
                r0.writeToParcel(r9)
                r20.send()
                goto L_0x12b4
            L_0x005f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0064
                goto L_0x0065
            L_0x0064:
                r3 = r2
            L_0x0065:
                if (r3 == 0) goto L_0x006f
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x006f:
                r8.enforceInterface(r0)
                r17.ping()
                r9.writeStatus(r2)
                r20.send()
                goto L_0x12b4
            L_0x007d:
                r0 = r21 & 1
                if (r0 == 0) goto L_0x0082
                r2 = r3
            L_0x0082:
                r0 = r2
                if (r0 == 0) goto L_0x12b4
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x008d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0092
                r2 = r3
            L_0x0092:
                if (r2 == r3) goto L_0x009c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x009c:
                r8.enforceInterface(r0)
                r17.setHALInstrumentation()
                goto L_0x12b4
            L_0x00a4:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x00a9
                goto L_0x00aa
            L_0x00a9:
                r3 = r2
            L_0x00aa:
                if (r3 == 0) goto L_0x00b4
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x00b4:
                r8.enforceInterface(r0)
                java.util.ArrayList r0 = r17.getHashChain()
                r9.writeStatus(r2)
                android.os.HwBlob r1 = new android.os.HwBlob
                r4 = 16
                r1.<init>(r4)
                int r4 = r0.size()
                r5 = 8
                r1.putInt32(r5, r4)
                r5 = 12
                r1.putBool(r5, r2)
                android.os.HwBlob r2 = new android.os.HwBlob
                int r5 = r4 * 32
                r2.<init>(r5)
                r5 = 0
            L_0x00db:
                if (r5 >= r4) goto L_0x00fc
                int r6 = r5 * 32
                long r10 = (long) r6
                java.lang.Object r6 = r0.get(r5)
                byte[] r6 = (byte[]) r6
                if (r6 == 0) goto L_0x00f4
                int r12 = r6.length
                r13 = 32
                if (r12 != r13) goto L_0x00f4
                r2.putInt8Array(r10, r6)
                int r5 = r5 + 1
                goto L_0x00db
            L_0x00f4:
                java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
                java.lang.String r13 = "Array element is not of the expected length"
                r12.<init>(r13)
                throw r12
            L_0x00fc:
                r5 = 0
                r1.putBlob(r5, r2)
                r9.writeBuffer(r1)
                r20.send()
                goto L_0x12b4
            L_0x0109:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x010e
                goto L_0x010f
            L_0x010e:
                r3 = r2
            L_0x010f:
                if (r3 == 0) goto L_0x0119
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0119:
                r8.enforceInterface(r0)
                java.lang.String r0 = r17.interfaceDescriptor()
                r9.writeStatus(r2)
                r9.writeString(r0)
                r20.send()
                goto L_0x12b4
            L_0x012b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0130
                goto L_0x0131
            L_0x0130:
                r3 = r2
            L_0x0131:
                if (r3 == 0) goto L_0x013b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x013b:
                r8.enforceInterface(r0)
                android.os.NativeHandle r0 = r19.readNativeHandle()
                java.util.ArrayList r1 = r19.readStringVector()
                r7.debug(r0, r1)
                r9.writeStatus(r2)
                r20.send()
                goto L_0x12b4
            L_0x0151:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0156
                goto L_0x0157
            L_0x0156:
                r3 = r2
            L_0x0157:
                if (r3 == 0) goto L_0x0161
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0161:
                r8.enforceInterface(r0)
                java.util.ArrayList r0 = r17.interfaceChain()
                r9.writeStatus(r2)
                r9.writeStringVector(r0)
                r20.send()
                goto L_0x12b4
            L_0x0173:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0178
                r2 = r3
            L_0x0178:
                if (r2 == r3) goto L_0x0182
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0182:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.acknowledgeRequest(r0)
                goto L_0x12b4
            L_0x018e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0193
                r2 = r3
            L_0x0193:
                if (r2 == r3) goto L_0x019d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x019d:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setSimCardPowerResponse(r0)
                goto L_0x12b4
            L_0x01ad:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x01b2
                r2 = r3
            L_0x01b2:
                if (r2 == r3) goto L_0x01bc
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x01bc:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setIndicationFilterResponse(r0)
                goto L_0x12b4
            L_0x01cc:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x01d1
                r2 = r3
            L_0x01d1:
                if (r2 == r3) goto L_0x01db
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x01db:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.sendDeviceStateResponse(r0)
                goto L_0x12b4
            L_0x01eb:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x01f0
                r2 = r3
            L_0x01f0:
                if (r2 == r3) goto L_0x01fa
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x01fa:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                boolean r1 = r19.readBool()
                android.hardware.radio.V1_0.CarrierRestrictions r3 = new android.hardware.radio.V1_0.CarrierRestrictions
                r3.<init>()
                r3.readFromParcel(r8)
                r7.getAllowedCarriersResponse(r0, r1, r3)
                goto L_0x12b4
            L_0x0216:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x021b
                r2 = r3
            L_0x021b:
                if (r2 == r3) goto L_0x0225
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0225:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.setAllowedCarriersResponse(r0, r1)
                goto L_0x12b4
            L_0x0239:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x023e
                r2 = r3
            L_0x023e:
                if (r2 == r3) goto L_0x0248
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0248:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.ActivityStatsInfo r1 = new android.hardware.radio.V1_0.ActivityStatsInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getModemActivityInfoResponse(r0, r1)
                goto L_0x12b4
            L_0x0260:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0265
                r2 = r3
            L_0x0265:
                if (r2 == r3) goto L_0x026f
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x026f:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.LceDataInfo r1 = new android.hardware.radio.V1_0.LceDataInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.pullLceDataResponse(r0, r1)
                goto L_0x12b4
            L_0x0287:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x028c
                r2 = r3
            L_0x028c:
                if (r2 == r3) goto L_0x0296
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0296:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.LceStatusInfo r1 = new android.hardware.radio.V1_0.LceStatusInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.stopLceServiceResponse(r0, r1)
                goto L_0x12b4
            L_0x02ae:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x02b3
                r2 = r3
            L_0x02b3:
                if (r2 == r3) goto L_0x02bd
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x02bd:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.LceStatusInfo r1 = new android.hardware.radio.V1_0.LceStatusInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.startLceServiceResponse(r0, r1)
                goto L_0x12b4
            L_0x02d5:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x02da
                r2 = r3
            L_0x02da:
                if (r2 == r3) goto L_0x02e4
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x02e4:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.RadioCapability r1 = new android.hardware.radio.V1_0.RadioCapability
                r1.<init>()
                r1.readFromParcel(r8)
                r7.setRadioCapabilityResponse(r0, r1)
                goto L_0x12b4
            L_0x02fc:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0301
                r2 = r3
            L_0x0301:
                if (r2 == r3) goto L_0x030b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x030b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.RadioCapability r1 = new android.hardware.radio.V1_0.RadioCapability
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getRadioCapabilityResponse(r0, r1)
                goto L_0x12b4
            L_0x0323:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0328
                r2 = r3
            L_0x0328:
                if (r2 == r3) goto L_0x0332
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0332:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.requestShutdownResponse(r0)
                goto L_0x12b4
            L_0x0342:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0347
                r2 = r3
            L_0x0347:
                if (r2 == r3) goto L_0x0351
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0351:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setDataProfileResponse(r0)
                goto L_0x12b4
            L_0x0361:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0366
                r2 = r3
            L_0x0366:
                if (r2 == r3) goto L_0x0370
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0370:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.IccIoResult r1 = new android.hardware.radio.V1_0.IccIoResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.requestIccSimAuthenticationResponse(r0, r1)
                goto L_0x12b4
            L_0x0388:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x038d
                r2 = r3
            L_0x038d:
                if (r2 == r3) goto L_0x0397
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0397:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.HardwareConfig.readVectorFromParcel(r19)
                r7.getHardwareConfigResponse(r0, r1)
                goto L_0x12b4
            L_0x03ab:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x03b0
                r2 = r3
            L_0x03b0:
                if (r2 == r3) goto L_0x03ba
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x03ba:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setDataAllowedResponse(r0)
                goto L_0x12b4
            L_0x03ca:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x03cf
                r2 = r3
            L_0x03cf:
                if (r2 == r3) goto L_0x03d9
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x03d9:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setUiccSubscriptionResponse(r0)
                goto L_0x12b4
            L_0x03e9:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x03ee
                r2 = r3
            L_0x03ee:
                if (r2 == r3) goto L_0x03f8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x03f8:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.nvResetConfigResponse(r0)
                goto L_0x12b4
            L_0x0408:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x040d
                r2 = r3
            L_0x040d:
                if (r2 == r3) goto L_0x0417
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0417:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.nvWriteCdmaPrlResponse(r0)
                goto L_0x12b4
            L_0x0427:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x042c
                r2 = r3
            L_0x042c:
                if (r2 == r3) goto L_0x0436
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0436:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.nvWriteItemResponse(r0)
                goto L_0x12b4
            L_0x0446:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x044b
                r2 = r3
            L_0x044b:
                if (r2 == r3) goto L_0x0455
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0455:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                r7.nvReadItemResponse(r0, r1)
                goto L_0x12b4
            L_0x0469:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x046e
                r2 = r3
            L_0x046e:
                if (r2 == r3) goto L_0x0478
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0478:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.IccIoResult r1 = new android.hardware.radio.V1_0.IccIoResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.iccTransmitApduLogicalChannelResponse(r0, r1)
                goto L_0x12b4
            L_0x0490:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0495
                r2 = r3
            L_0x0495:
                if (r2 == r3) goto L_0x049f
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x049f:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.iccCloseLogicalChannelResponse(r0)
                goto L_0x12b4
            L_0x04af:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x04b4
                r2 = r3
            L_0x04b4:
                if (r2 == r3) goto L_0x04be
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x04be:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                java.util.ArrayList r3 = r19.readInt8Vector()
                r7.iccOpenLogicalChannelResponse(r0, r1, r3)
                goto L_0x12b4
            L_0x04d6:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x04db
                r2 = r3
            L_0x04db:
                if (r2 == r3) goto L_0x04e5
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x04e5:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.IccIoResult r1 = new android.hardware.radio.V1_0.IccIoResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.iccTransmitApduBasicChannelResponse(r0, r1)
                goto L_0x12b4
            L_0x04fd:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0502
                r2 = r3
            L_0x0502:
                if (r2 == r3) goto L_0x050c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x050c:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.SendSmsResult r1 = new android.hardware.radio.V1_0.SendSmsResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendImsSmsResponse(r0, r1)
                goto L_0x12b4
            L_0x0524:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0529
                r2 = r3
            L_0x0529:
                if (r2 == r3) goto L_0x0533
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0533:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                boolean r1 = r19.readBool()
                int r3 = r19.readInt32()
                r7.getImsRegistrationStateResponse(r0, r1, r3)
                goto L_0x12b4
            L_0x054b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0550
                r2 = r3
            L_0x0550:
                if (r2 == r3) goto L_0x055a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x055a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setInitialAttachApnResponse(r0)
                goto L_0x12b4
            L_0x056a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x056f
                r2 = r3
            L_0x056f:
                if (r2 == r3) goto L_0x0579
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0579:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCellInfoListRateResponse(r0)
                goto L_0x12b4
            L_0x0589:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x058e
                r2 = r3
            L_0x058e:
                if (r2 == r3) goto L_0x0598
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0598:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.CellInfo.readVectorFromParcel(r19)
                r7.getCellInfoListResponse(r0, r1)
                goto L_0x12b4
            L_0x05ac:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x05b1
                r2 = r3
            L_0x05b1:
                if (r2 == r3) goto L_0x05bb
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x05bb:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getVoiceRadioTechnologyResponse(r0, r1)
                goto L_0x12b4
            L_0x05cf:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x05d4
                r2 = r3
            L_0x05d4:
                if (r2 == r3) goto L_0x05de
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x05de:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.IccIoResult r1 = new android.hardware.radio.V1_0.IccIoResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendEnvelopeWithStatusResponse(r0, r1)
                goto L_0x12b4
            L_0x05f6:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x05fb
                r2 = r3
            L_0x05fb:
                if (r2 == r3) goto L_0x0605
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0605:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.acknowledgeIncomingGsmSmsWithPduResponse(r0)
                goto L_0x12b4
            L_0x0615:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x061a
                r2 = r3
            L_0x061a:
                if (r2 == r3) goto L_0x0624
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0624:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                r7.requestIsimAuthenticationResponse(r0, r1)
                goto L_0x12b4
            L_0x0638:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x063d
                r2 = r3
            L_0x063d:
                if (r2 == r3) goto L_0x0647
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0647:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getCdmaSubscriptionSourceResponse(r0, r1)
                goto L_0x12b4
            L_0x065b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0660
                r2 = r3
            L_0x0660:
                if (r2 == r3) goto L_0x066a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x066a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.reportStkServiceIsRunningResponse(r0)
                goto L_0x12b4
            L_0x067a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x067f
                r2 = r3
            L_0x067f:
                if (r2 == r3) goto L_0x0689
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0689:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.reportSmsMemoryStatusResponse(r0)
                goto L_0x12b4
            L_0x0699:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x069e
                r2 = r3
            L_0x069e:
                if (r2 == r3) goto L_0x06a8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x06a8:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setSmscAddressResponse(r0)
                goto L_0x12b4
            L_0x06b8:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x06bd
                r2 = r3
            L_0x06bd:
                if (r2 == r3) goto L_0x06c7
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x06c7:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                r7.getSmscAddressResponse(r0, r1)
                goto L_0x12b4
            L_0x06db:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x06e0
                r2 = r3
            L_0x06e0:
                if (r2 == r3) goto L_0x06ea
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x06ea:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.exitEmergencyCallbackModeResponse(r0)
                goto L_0x12b4
            L_0x06fa:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x06ff
                r2 = r3
            L_0x06ff:
                r6 = r2
                if (r6 == r3) goto L_0x070a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x070a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r10 = r0
                r10.readFromParcel(r8)
                java.lang.String r11 = r19.readString()
                java.lang.String r12 = r19.readString()
                java.lang.String r13 = r19.readString()
                java.lang.String r14 = r19.readString()
                r0 = r17
                r1 = r10
                r2 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.getDeviceIdentityResponse(r1, r2, r3, r4, r5)
                goto L_0x12b4
            L_0x0732:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0737
                r2 = r3
            L_0x0737:
                if (r2 == r3) goto L_0x0741
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0741:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.deleteSmsOnRuimResponse(r0)
                goto L_0x12b4
            L_0x0751:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0756
                r2 = r3
            L_0x0756:
                if (r2 == r3) goto L_0x0760
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0760:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.writeSmsToRuimResponse(r0, r1)
                goto L_0x12b4
            L_0x0774:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0779
                r2 = r3
            L_0x0779:
                r10 = r2
                if (r10 == r3) goto L_0x0784
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0784:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r11 = r0
                r11.readFromParcel(r8)
                java.lang.String r12 = r19.readString()
                java.lang.String r13 = r19.readString()
                java.lang.String r14 = r19.readString()
                java.lang.String r15 = r19.readString()
                java.lang.String r16 = r19.readString()
                r0 = r17
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r6 = r16
                r0.getCDMASubscriptionResponse(r1, r2, r3, r4, r5, r6)
                goto L_0x12b4
            L_0x07b2:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x07b7
                r2 = r3
            L_0x07b7:
                if (r2 == r3) goto L_0x07c1
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x07c1:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCdmaBroadcastActivationResponse(r0)
                goto L_0x12b4
            L_0x07d1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x07d6
                r2 = r3
            L_0x07d6:
                if (r2 == r3) goto L_0x07e0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x07e0:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCdmaBroadcastConfigResponse(r0)
                goto L_0x12b4
            L_0x07f0:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x07f5
                r2 = r3
            L_0x07f5:
                if (r2 == r3) goto L_0x07ff
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x07ff:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.readVectorFromParcel(r19)
                r7.getCdmaBroadcastConfigResponse(r0, r1)
                goto L_0x12b4
            L_0x0813:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0818
                r2 = r3
            L_0x0818:
                if (r2 == r3) goto L_0x0822
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0822:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setGsmBroadcastActivationResponse(r0)
                goto L_0x12b4
            L_0x0832:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0837
                r2 = r3
            L_0x0837:
                if (r2 == r3) goto L_0x0841
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0841:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setGsmBroadcastConfigResponse(r0)
                goto L_0x12b4
            L_0x0851:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0856
                r2 = r3
            L_0x0856:
                if (r2 == r3) goto L_0x0860
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0860:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.readVectorFromParcel(r19)
                r7.getGsmBroadcastConfigResponse(r0, r1)
                goto L_0x12b4
            L_0x0874:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0879
                r2 = r3
            L_0x0879:
                if (r2 == r3) goto L_0x0883
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0883:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.acknowledgeLastIncomingCdmaSmsResponse(r0)
                goto L_0x12b4
            L_0x0893:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0898
                r2 = r3
            L_0x0898:
                if (r2 == r3) goto L_0x08a2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x08a2:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.SendSmsResult r1 = new android.hardware.radio.V1_0.SendSmsResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendCdmaSmsResponse(r0, r1)
                goto L_0x12b4
            L_0x08ba:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08bf
                r2 = r3
            L_0x08bf:
                if (r2 == r3) goto L_0x08c9
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x08c9:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.sendBurstDtmfResponse(r0)
                goto L_0x12b4
            L_0x08d9:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08de
                r2 = r3
            L_0x08de:
                if (r2 == r3) goto L_0x08e8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x08e8:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.sendCDMAFeatureCodeResponse(r0)
                goto L_0x12b4
            L_0x08f8:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08fd
                r2 = r3
            L_0x08fd:
                if (r2 == r3) goto L_0x0907
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0907:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                boolean r1 = r19.readBool()
                r7.getPreferredVoicePrivacyResponse(r0, r1)
                goto L_0x12b4
            L_0x091b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0920
                r2 = r3
            L_0x0920:
                if (r2 == r3) goto L_0x092a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x092a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setPreferredVoicePrivacyResponse(r0)
                goto L_0x12b4
            L_0x093a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x093f
                r2 = r3
            L_0x093f:
                if (r2 == r3) goto L_0x0949
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0949:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getTTYModeResponse(r0, r1)
                goto L_0x12b4
            L_0x095d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0962
                r2 = r3
            L_0x0962:
                if (r2 == r3) goto L_0x096c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x096c:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setTTYModeResponse(r0)
                goto L_0x12b4
            L_0x097c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0981
                r2 = r3
            L_0x0981:
                if (r2 == r3) goto L_0x098b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x098b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getCdmaRoamingPreferenceResponse(r0, r1)
                goto L_0x12b4
            L_0x099f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x09a4
                r2 = r3
            L_0x09a4:
                if (r2 == r3) goto L_0x09ae
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x09ae:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCdmaRoamingPreferenceResponse(r0)
                goto L_0x12b4
            L_0x09be:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x09c3
                r2 = r3
            L_0x09c3:
                if (r2 == r3) goto L_0x09cd
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x09cd:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCdmaSubscriptionSourceResponse(r0)
                goto L_0x12b4
            L_0x09dd:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x09e2
                r2 = r3
            L_0x09e2:
                if (r2 == r3) goto L_0x09ec
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x09ec:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setLocationUpdatesResponse(r0)
                goto L_0x12b4
            L_0x09fc:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a01
                r2 = r3
            L_0x0a01:
                if (r2 == r3) goto L_0x0a0b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0a0b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.NeighboringCell.readVectorFromParcel(r19)
                r7.getNeighboringCidsResponse(r0, r1)
                goto L_0x12b4
            L_0x0a1f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a24
                r2 = r3
            L_0x0a24:
                if (r2 == r3) goto L_0x0a2e
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0a2e:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getPreferredNetworkTypeResponse(r0, r1)
                goto L_0x12b4
            L_0x0a42:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a47
                r2 = r3
            L_0x0a47:
                if (r2 == r3) goto L_0x0a51
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0a51:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setPreferredNetworkTypeResponse(r0)
                goto L_0x12b4
            L_0x0a61:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a66
                r2 = r3
            L_0x0a66:
                if (r2 == r3) goto L_0x0a70
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0a70:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.explicitCallTransferResponse(r0)
                goto L_0x12b4
            L_0x0a80:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a85
                r2 = r3
            L_0x0a85:
                if (r2 == r3) goto L_0x0a8f
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0a8f:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.handleStkCallSetupRequestFromSimResponse(r0)
                goto L_0x12b4
            L_0x0a9f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0aa4
                r2 = r3
            L_0x0aa4:
                if (r2 == r3) goto L_0x0aae
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0aae:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.sendTerminalResponseToSimResponse(r0)
                goto L_0x12b4
            L_0x0abe:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ac3
                r2 = r3
            L_0x0ac3:
                if (r2 == r3) goto L_0x0acd
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0acd:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                r7.sendEnvelopeResponse(r0, r1)
                goto L_0x12b4
            L_0x0ae1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ae6
                r2 = r3
            L_0x0ae6:
                if (r2 == r3) goto L_0x0af0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0af0:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = r19.readInt32Vector()
                r7.getAvailableBandModesResponse(r0, r1)
                goto L_0x12b4
            L_0x0b04:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b09
                r2 = r3
            L_0x0b09:
                if (r2 == r3) goto L_0x0b13
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0b13:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setBandModeResponse(r0)
                goto L_0x12b4
            L_0x0b23:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b28
                r2 = r3
            L_0x0b28:
                if (r2 == r3) goto L_0x0b32
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0b32:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.deleteSmsOnSimResponse(r0)
                goto L_0x12b4
            L_0x0b42:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b47
                r2 = r3
            L_0x0b47:
                if (r2 == r3) goto L_0x0b51
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0b51:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.writeSmsToSimResponse(r0, r1)
                goto L_0x12b4
            L_0x0b65:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b6a
                r2 = r3
            L_0x0b6a:
                if (r2 == r3) goto L_0x0b74
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0b74:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setSuppServiceNotificationsResponse(r0)
                goto L_0x12b4
            L_0x0b84:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b89
                r2 = r3
            L_0x0b89:
                if (r2 == r3) goto L_0x0b93
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0b93:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.SetupDataCallResult.readVectorFromParcel(r19)
                r7.getDataCallListResponse(r0, r1)
                goto L_0x12b4
            L_0x0ba7:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0bac
                r2 = r3
            L_0x0bac:
                if (r2 == r3) goto L_0x0bb6
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0bb6:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getClipResponse(r0, r1)
                goto L_0x12b4
            L_0x0bca:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0bcf
                r2 = r3
            L_0x0bcf:
                if (r2 == r3) goto L_0x0bd9
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0bd9:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                boolean r1 = r19.readBool()
                r7.getMuteResponse(r0, r1)
                goto L_0x12b4
            L_0x0bed:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0bf2
                r2 = r3
            L_0x0bf2:
                if (r2 == r3) goto L_0x0bfc
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0bfc:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setMuteResponse(r0)
                goto L_0x12b4
            L_0x0c0c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c11
                r2 = r3
            L_0x0c11:
                if (r2 == r3) goto L_0x0c1b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0c1b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.separateConnectionResponse(r0)
                goto L_0x12b4
            L_0x0c2b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c30
                r2 = r3
            L_0x0c30:
                if (r2 == r3) goto L_0x0c3a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0c3a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                r7.getBasebandVersionResponse(r0, r1)
                goto L_0x12b4
            L_0x0c4e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c53
                r2 = r3
            L_0x0c53:
                if (r2 == r3) goto L_0x0c5d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0c5d:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.stopDtmfResponse(r0)
                goto L_0x12b4
            L_0x0c6d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c72
                r2 = r3
            L_0x0c72:
                if (r2 == r3) goto L_0x0c7c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0c7c:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.startDtmfResponse(r0)
                goto L_0x12b4
            L_0x0c8c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c91
                r2 = r3
            L_0x0c91:
                if (r2 == r3) goto L_0x0c9b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0c9b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.OperatorInfo.readVectorFromParcel(r19)
                r7.getAvailableNetworksResponse(r0, r1)
                goto L_0x12b4
            L_0x0caf:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0cb4
                r2 = r3
            L_0x0cb4:
                if (r2 == r3) goto L_0x0cbe
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0cbe:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setNetworkSelectionModeManualResponse(r0)
                goto L_0x12b4
            L_0x0cce:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0cd3
                r2 = r3
            L_0x0cd3:
                if (r2 == r3) goto L_0x0cdd
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0cdd:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setNetworkSelectionModeAutomaticResponse(r0)
                goto L_0x12b4
            L_0x0ced:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0cf2
                r2 = r3
            L_0x0cf2:
                if (r2 == r3) goto L_0x0cfc
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0cfc:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                boolean r1 = r19.readBool()
                r7.getNetworkSelectionModeResponse(r0, r1)
                goto L_0x12b4
            L_0x0d10:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d15
                r2 = r3
            L_0x0d15:
                if (r2 == r3) goto L_0x0d1f
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0d1f:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setBarringPasswordResponse(r0)
                goto L_0x12b4
            L_0x0d2f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d34
                r2 = r3
            L_0x0d34:
                if (r2 == r3) goto L_0x0d3e
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0d3e:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.setFacilityLockForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x0d52:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d57
                r2 = r3
            L_0x0d57:
                if (r2 == r3) goto L_0x0d61
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0d61:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.getFacilityLockForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x0d75:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d7a
                r2 = r3
            L_0x0d7a:
                if (r2 == r3) goto L_0x0d84
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0d84:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.deactivateDataCallResponse(r0)
                goto L_0x12b4
            L_0x0d94:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d99
                r2 = r3
            L_0x0d99:
                if (r2 == r3) goto L_0x0da3
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0da3:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.acceptCallResponse(r0)
                goto L_0x12b4
            L_0x0db3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0db8
                r2 = r3
            L_0x0db8:
                if (r2 == r3) goto L_0x0dc2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0dc2:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.acknowledgeLastIncomingGsmSmsResponse(r0)
                goto L_0x12b4
            L_0x0dd2:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0dd7
                r2 = r3
            L_0x0dd7:
                if (r2 == r3) goto L_0x0de1
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0de1:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCallWaitingResponse(r0)
                goto L_0x12b4
            L_0x0df1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0df6
                r2 = r3
            L_0x0df6:
                if (r2 == r3) goto L_0x0e00
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0e00:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                boolean r1 = r19.readBool()
                int r3 = r19.readInt32()
                r7.getCallWaitingResponse(r0, r1, r3)
                goto L_0x12b4
            L_0x0e18:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e1d
                r2 = r3
            L_0x0e1d:
                if (r2 == r3) goto L_0x0e27
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0e27:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setCallForwardResponse(r0)
                goto L_0x12b4
            L_0x0e37:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e3c
                r2 = r3
            L_0x0e3c:
                if (r2 == r3) goto L_0x0e46
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0e46:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.CallForwardInfo.readVectorFromParcel(r19)
                r7.getCallForwardStatusResponse(r0, r1)
                goto L_0x12b4
            L_0x0e5a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e5f
                r2 = r3
            L_0x0e5f:
                if (r2 == r3) goto L_0x0e69
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0e69:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setClirResponse(r0)
                goto L_0x12b4
            L_0x0e79:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e7e
                r2 = r3
            L_0x0e7e:
                if (r2 == r3) goto L_0x0e88
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0e88:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                int r3 = r19.readInt32()
                r7.getClirResponse(r0, r1, r3)
                goto L_0x12b4
            L_0x0ea0:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ea5
                r2 = r3
            L_0x0ea5:
                if (r2 == r3) goto L_0x0eaf
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0eaf:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.cancelPendingUssdResponse(r0)
                goto L_0x12b4
            L_0x0ebf:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ec4
                r2 = r3
            L_0x0ec4:
                if (r2 == r3) goto L_0x0ece
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0ece:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.sendUssdResponse(r0)
                goto L_0x12b4
            L_0x0ede:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ee3
                r2 = r3
            L_0x0ee3:
                if (r2 == r3) goto L_0x0eed
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0eed:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.IccIoResult r1 = new android.hardware.radio.V1_0.IccIoResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.iccIOForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x0f05:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f0a
                r2 = r3
            L_0x0f0a:
                if (r2 == r3) goto L_0x0f14
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0f14:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.SetupDataCallResult r1 = new android.hardware.radio.V1_0.SetupDataCallResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.setupDataCallResponse(r0, r1)
                goto L_0x12b4
            L_0x0f2c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f31
                r2 = r3
            L_0x0f31:
                if (r2 == r3) goto L_0x0f3b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0f3b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.SendSmsResult r1 = new android.hardware.radio.V1_0.SendSmsResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendSMSExpectMoreResponse(r0, r1)
                goto L_0x12b4
            L_0x0f53:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f58
                r2 = r3
            L_0x0f58:
                if (r2 == r3) goto L_0x0f62
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0f62:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.SendSmsResult r1 = new android.hardware.radio.V1_0.SendSmsResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendSmsResponse(r0, r1)
                goto L_0x12b4
            L_0x0f7a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f7f
                r2 = r3
            L_0x0f7f:
                if (r2 == r3) goto L_0x0f89
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0f89:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.sendDtmfResponse(r0)
                goto L_0x12b4
            L_0x0f99:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f9e
                r2 = r3
            L_0x0f9e:
                if (r2 == r3) goto L_0x0fa8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0fa8:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.setRadioPowerResponse(r0)
                goto L_0x12b4
            L_0x0fb8:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0fbd
                r2 = r3
            L_0x0fbd:
                if (r2 == r3) goto L_0x0fc7
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0fc7:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.getOperatorResponse(r0, r1, r3, r4)
                goto L_0x12b4
            L_0x0fe3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0fe8
                r2 = r3
            L_0x0fe8:
                if (r2 == r3) goto L_0x0ff2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x0ff2:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.DataRegStateResult r1 = new android.hardware.radio.V1_0.DataRegStateResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getDataRegistrationStateResponse(r0, r1)
                goto L_0x12b4
            L_0x100a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x100f
                r2 = r3
            L_0x100f:
                if (r2 == r3) goto L_0x1019
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1019:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.VoiceRegStateResult r1 = new android.hardware.radio.V1_0.VoiceRegStateResult
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getVoiceRegistrationStateResponse(r0, r1)
                goto L_0x12b4
            L_0x1031:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1036
                r2 = r3
            L_0x1036:
                if (r2 == r3) goto L_0x1040
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1040:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.SignalStrength r1 = new android.hardware.radio.V1_0.SignalStrength
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getSignalStrengthResponse(r0, r1)
                goto L_0x12b4
            L_0x1058:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x105d
                r2 = r3
            L_0x105d:
                if (r2 == r3) goto L_0x1067
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1067:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.LastCallFailCauseInfo r1 = new android.hardware.radio.V1_0.LastCallFailCauseInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getLastCallFailCauseResponse(r0, r1)
                goto L_0x12b4
            L_0x107f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1084
                r2 = r3
            L_0x1084:
                if (r2 == r3) goto L_0x108e
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x108e:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.rejectCallResponse(r0)
                goto L_0x12b4
            L_0x109e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x10a3
                r2 = r3
            L_0x10a3:
                if (r2 == r3) goto L_0x10ad
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x10ad:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.conferenceResponse(r0)
                goto L_0x12b4
            L_0x10bd:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x10c2
                r2 = r3
            L_0x10c2:
                if (r2 == r3) goto L_0x10cc
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x10cc:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.switchWaitingOrHoldingAndActiveResponse(r0)
                goto L_0x12b4
            L_0x10dc:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x10e1
                r2 = r3
            L_0x10e1:
                if (r2 == r3) goto L_0x10eb
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x10eb:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.hangupForegroundResumeBackgroundResponse(r0)
                goto L_0x12b4
            L_0x10fb:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1100
                r2 = r3
            L_0x1100:
                if (r2 == r3) goto L_0x110a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x110a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.hangupWaitingOrBackgroundResponse(r0)
                goto L_0x12b4
            L_0x111a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x111f
                r2 = r3
            L_0x111f:
                if (r2 == r3) goto L_0x1129
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1129:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.hangupConnectionResponse(r0)
                goto L_0x12b4
            L_0x1139:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x113e
                r2 = r3
            L_0x113e:
                if (r2 == r3) goto L_0x1148
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1148:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.lang.String r1 = r19.readString()
                r7.getIMSIForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x115c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1161
                r2 = r3
            L_0x1161:
                if (r2 == r3) goto L_0x116b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x116b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                r7.dialResponse(r0)
                goto L_0x12b4
            L_0x117b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1180
                r2 = r3
            L_0x1180:
                if (r2 == r3) goto L_0x118a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x118a:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                java.util.ArrayList r1 = android.hardware.radio.V1_0.Call.readVectorFromParcel(r19)
                r7.getCurrentCallsResponse(r0, r1)
                goto L_0x12b4
            L_0x119e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x11a3
                r2 = r3
            L_0x11a3:
                if (r2 == r3) goto L_0x11ad
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x11ad:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.supplyNetworkDepersonalizationResponse(r0, r1)
                goto L_0x12b4
            L_0x11c1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x11c6
                r2 = r3
            L_0x11c6:
                if (r2 == r3) goto L_0x11d0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x11d0:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.changeIccPin2ForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x11e4:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x11e9
                r2 = r3
            L_0x11e9:
                if (r2 == r3) goto L_0x11f3
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x11f3:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.changeIccPinForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x1207:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x120c
                r2 = r3
            L_0x120c:
                if (r2 == r3) goto L_0x1216
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1216:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.supplyIccPuk2ForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x122a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x122f
                r2 = r3
            L_0x122f:
                if (r2 == r3) goto L_0x1239
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x1239:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.supplyIccPin2ForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x124d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1252
                r2 = r3
            L_0x1252:
                if (r2 == r3) goto L_0x125b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x125b:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.supplyIccPukForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x126e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1273
                r2 = r3
            L_0x1273:
                if (r2 == r3) goto L_0x127c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x127c:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                int r1 = r19.readInt32()
                r7.supplyIccPinForAppResponse(r0, r1)
                goto L_0x12b4
            L_0x128f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1294
                r2 = r3
            L_0x1294:
                if (r2 == r3) goto L_0x129d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x12b4
            L_0x129d:
                r8.enforceInterface(r0)
                android.hardware.radio.V1_0.RadioResponseInfo r0 = new android.hardware.radio.V1_0.RadioResponseInfo
                r0.<init>()
                r0.readFromParcel(r8)
                android.hardware.radio.V1_0.CardStatus r1 = new android.hardware.radio.V1_0.CardStatus
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getIccCardStatusResponse(r0, r1)
            L_0x12b4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.V1_0.IRadioResponse.Stub.onTransact(int, android.os.HwParcel, android.os.HwParcel, int):void");
        }
    }
}
