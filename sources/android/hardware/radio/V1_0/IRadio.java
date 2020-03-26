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

public interface IRadio extends IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadio";

    void acceptCall(int i) throws RemoteException;

    void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException;

    void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException;

    void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException;

    IHwBinder asBinder();

    void cancelPendingUssd(int i) throws RemoteException;

    void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException;

    void conference(int i) throws RemoteException;

    void deactivateDataCall(int i, int i2, boolean z) throws RemoteException;

    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteSmsOnRuim(int i, int i2) throws RemoteException;

    void deleteSmsOnSim(int i, int i2) throws RemoteException;

    void dial(int i, Dial dial) throws RemoteException;

    void exitEmergencyCallbackMode(int i) throws RemoteException;

    void explicitCallTransfer(int i) throws RemoteException;

    void getAllowedCarriers(int i) throws RemoteException;

    void getAvailableBandModes(int i) throws RemoteException;

    void getAvailableNetworks(int i) throws RemoteException;

    void getBasebandVersion(int i) throws RemoteException;

    void getCDMASubscription(int i) throws RemoteException;

    void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void getCallWaiting(int i, int i2) throws RemoteException;

    void getCdmaBroadcastConfig(int i) throws RemoteException;

    void getCdmaRoamingPreference(int i) throws RemoteException;

    void getCdmaSubscriptionSource(int i) throws RemoteException;

    void getCellInfoList(int i) throws RemoteException;

    void getClip(int i) throws RemoteException;

    void getClir(int i) throws RemoteException;

    void getCurrentCalls(int i) throws RemoteException;

    void getDataCallList(int i) throws RemoteException;

    void getDataRegistrationState(int i) throws RemoteException;

    DebugInfo getDebugInfo() throws RemoteException;

    void getDeviceIdentity(int i) throws RemoteException;

    void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException;

    void getGsmBroadcastConfig(int i) throws RemoteException;

    void getHardwareConfig(int i) throws RemoteException;

    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccCardStatus(int i) throws RemoteException;

    void getImsRegistrationState(int i) throws RemoteException;

    void getImsiForApp(int i, String str) throws RemoteException;

    void getLastCallFailCause(int i) throws RemoteException;

    void getModemActivityInfo(int i) throws RemoteException;

    void getMute(int i) throws RemoteException;

    void getNeighboringCids(int i) throws RemoteException;

    void getNetworkSelectionMode(int i) throws RemoteException;

    void getOperator(int i) throws RemoteException;

    void getPreferredNetworkType(int i) throws RemoteException;

    void getPreferredVoicePrivacy(int i) throws RemoteException;

    void getRadioCapability(int i) throws RemoteException;

    void getSignalStrength(int i) throws RemoteException;

    void getSmscAddress(int i) throws RemoteException;

    void getTTYMode(int i) throws RemoteException;

    void getVoiceRadioTechnology(int i) throws RemoteException;

    void getVoiceRegistrationState(int i) throws RemoteException;

    void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException;

    void hangup(int i, int i2) throws RemoteException;

    void hangupForegroundResumeBackground(int i) throws RemoteException;

    void hangupWaitingOrBackground(int i) throws RemoteException;

    void iccCloseLogicalChannel(int i, int i2) throws RemoteException;

    void iccIOForApp(int i, IccIo iccIo) throws RemoteException;

    void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException;

    void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException;

    void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException;

    ArrayList<String> interfaceChain() throws RemoteException;

    String interfaceDescriptor() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void notifySyspropsChanged() throws RemoteException;

    void nvReadItem(int i, int i2) throws RemoteException;

    void nvResetConfig(int i, int i2) throws RemoteException;

    void nvWriteCdmaPrl(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException;

    void ping() throws RemoteException;

    void pullLceData(int i) throws RemoteException;

    void rejectCall(int i) throws RemoteException;

    void reportSmsMemoryStatus(int i, boolean z) throws RemoteException;

    void reportStkServiceIsRunning(int i) throws RemoteException;

    void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException;

    void requestIsimAuthentication(int i, String str) throws RemoteException;

    void requestShutdown(int i) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException;

    void sendCDMAFeatureCode(int i, String str) throws RemoteException;

    void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void sendDeviceState(int i, int i2, boolean z) throws RemoteException;

    void sendDtmf(int i, String str) throws RemoteException;

    void sendEnvelope(int i, String str) throws RemoteException;

    void sendEnvelopeWithStatus(int i, String str) throws RemoteException;

    void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException;

    void sendSMSExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendTerminalResponseToSim(int i, String str) throws RemoteException;

    void sendUssd(int i, String str) throws RemoteException;

    void separateConnection(int i, int i2) throws RemoteException;

    void setAllowedCarriers(int i, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException;

    void setBandMode(int i, int i2) throws RemoteException;

    void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException;

    void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void setCallWaiting(int i, boolean z, int i2) throws RemoteException;

    void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException;

    void setCdmaBroadcastConfig(int i, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void setCdmaRoamingPreference(int i, int i2) throws RemoteException;

    void setCdmaSubscriptionSource(int i, int i2) throws RemoteException;

    void setCellInfoListRate(int i, int i2) throws RemoteException;

    void setClir(int i, int i2) throws RemoteException;

    void setDataAllowed(int i, boolean z) throws RemoteException;

    void setDataProfile(int i, ArrayList<DataProfileInfo> arrayList, boolean z) throws RemoteException;

    void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException;

    void setGsmBroadcastActivation(int i, boolean z) throws RemoteException;

    void setGsmBroadcastConfig(int i, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void setHALInstrumentation() throws RemoteException;

    void setIndicationFilter(int i, int i2) throws RemoteException;

    void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo, boolean z, boolean z2) throws RemoteException;

    void setLocationUpdates(int i, boolean z) throws RemoteException;

    void setMute(int i, boolean z) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    void setNetworkSelectionModeManual(int i, String str) throws RemoteException;

    void setPreferredNetworkType(int i, int i2) throws RemoteException;

    void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException;

    void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException;

    void setRadioPower(int i, boolean z) throws RemoteException;

    void setResponseFunctions(IRadioResponse iRadioResponse, IRadioIndication iRadioIndication) throws RemoteException;

    void setSimCardPower(int i, boolean z) throws RemoteException;

    void setSmscAddress(int i, String str) throws RemoteException;

    void setSuppServiceNotifications(int i, boolean z) throws RemoteException;

    void setTTYMode(int i, int i2) throws RemoteException;

    void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException;

    void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, boolean z2, boolean z3) throws RemoteException;

    void startDtmf(int i, String str) throws RemoteException;

    void startLceService(int i, int i2, boolean z) throws RemoteException;

    void stopDtmf(int i) throws RemoteException;

    void stopLceService(int i) throws RemoteException;

    void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPinForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyNetworkDepersonalization(int i, String str) throws RemoteException;

    void switchWaitingOrHoldingAndActive(int i) throws RemoteException;

    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException;

    void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException;

    static IRadio asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IRadio)) {
            return (IRadio) iface;
        }
        IRadio proxy = new Proxy(binder);
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

    static IRadio castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static IRadio getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static IRadio getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    static IRadio getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    static IRadio getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadio {
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
                return "[class or subclass of android.hardware.radio@1.0::IRadio]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        public void setResponseFunctions(IRadioResponse radioResponse, IRadioIndication radioIndication) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            IHwBinder iHwBinder = null;
            _hidl_request.writeStrongBinder(radioResponse == null ? null : radioResponse.asBinder());
            if (radioIndication != null) {
                iHwBinder = radioIndication.asBinder();
            }
            _hidl_request.writeStrongBinder(iHwBinder);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getIccCardStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPinForApp(int serial, String pin, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPukForApp(int serial, String puk, String pin, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(puk);
            _hidl_request.writeString(pin);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPin2ForApp(int serial, String pin2, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin2);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyIccPuk2ForApp(int serial, String puk2, String pin2, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(puk2);
            _hidl_request.writeString(pin2);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPinForApp(int serial, String oldPin, String newPin, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPin);
            _hidl_request.writeString(newPin);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void changeIccPin2ForApp(int serial, String oldPin2, String newPin2, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPin2);
            _hidl_request.writeString(newPin2);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void supplyNetworkDepersonalization(int serial, String netPin) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(netPin);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCurrentCalls(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void dial(int serial, Dial dialInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dialInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsiForApp(int serial, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangup(int serial, int gsmIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(gsmIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupWaitingOrBackground(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void hangupForegroundResumeBackground(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void switchWaitingOrHoldingAndActive(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void conference(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void rejectCall(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getLastCallFailCause(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSignalStrength(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getOperator(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioPower(int serial, boolean on) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(on);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDtmf(int serial, String s) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(s);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSms(int serial, GsmSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendSMSExpectMore(int serial, GsmSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setupDataCall(int serial, int radioTechnology, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean roamingAllowed, boolean isRoaming) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(radioTechnology);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(roamingAllowed);
            _hidl_request.writeBool(isRoaming);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccIOForApp(int serial, IccIo iccIo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            iccIo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendUssd(int serial, String ussd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(ussd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void cancelPendingUssd(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClir(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setClir(int serial, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallForwardStatus(int serial, CallForwardInfo callInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallForward(int serial, CallForwardInfo callInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            callInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCallWaiting(int serial, int serviceClass) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(serviceClass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCallWaiting(int serial, boolean enable, int serviceClass) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            _hidl_request.writeInt32(serviceClass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingGsmSms(int serial, boolean success, int cause) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeInt32(cause);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acceptCall(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deactivateDataCall(int serial, int cid, boolean reasonRadioShutDown) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cid);
            _hidl_request.writeBool(reasonRadioShutDown);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getFacilityLockForApp(int serial, String facility, String password, int serviceClass, String appId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(password);
            _hidl_request.writeInt32(serviceClass);
            _hidl_request.writeString(appId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setFacilityLockForApp(int serial, String facility, boolean lockState, String password, int serviceClass, String appId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeBool(lockState);
            _hidl_request.writeString(password);
            _hidl_request.writeInt32(serviceClass);
            _hidl_request.writeString(appId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBarringPassword(int serial, String facility, String oldPassword, String newPassword) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(facility);
            _hidl_request.writeString(oldPassword);
            _hidl_request.writeString(newPassword);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNetworkSelectionMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeAutomatic(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setNetworkSelectionModeManual(int serial, String operatorNumeric) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(operatorNumeric);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableNetworks(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startDtmf(int serial, String s) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(s);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(48, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopDtmf(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(49, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getBasebandVersion(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(50, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void separateConnection(int serial, int gsmIndex) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(gsmIndex);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(51, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setMute(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(52, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getMute(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(53, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getClip(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(54, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDataCallList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(55, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSuppServiceNotifications(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(56, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToSim(int serial, SmsWriteArgs smsWriteArgs) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsWriteArgs.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(57, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnSim(int serial, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(58, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setBandMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(59, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getAvailableBandModes(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(60, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelope(int serial, String command) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(command);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(61, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendTerminalResponseToSim(int serial, String commandResponse) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(commandResponse);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(62, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void handleStkCallSetupRequestFromSim(int serial, boolean accept) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(accept);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(63, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void explicitCallTransfer(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(64, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredNetworkType(int serial, int nwType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(nwType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(65, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredNetworkType(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(66, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getNeighboringCids(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(67, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setLocationUpdates(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(68, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaSubscriptionSource(int serial, int cdmaSub) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(cdmaSub);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(69, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaRoamingPreference(int serial, int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(70, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaRoamingPreference(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(71, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setTTYMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(72, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getTTYMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(73, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setPreferredVoicePrivacy(int serial, boolean enable) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enable);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(74, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getPreferredVoicePrivacy(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(75, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCDMAFeatureCode(int serial, String featureCode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(featureCode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(76, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendBurstDtmf(int serial, String dtmf, int on, int off) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(dtmf);
            _hidl_request.writeInt32(on);
            _hidl_request.writeInt32(off);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(77, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendCdmaSms(int serial, CdmaSmsMessage sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(78, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeLastIncomingCdmaSms(int serial, CdmaSmsAck smsAck) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            smsAck.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(79, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getGsmBroadcastConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(80, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastConfig(int serial, ArrayList<GsmBroadcastSmsConfigInfo> configInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(81, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setGsmBroadcastActivation(int serial, boolean activate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(activate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(82, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaBroadcastConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(83, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastConfig(int serial, ArrayList<CdmaBroadcastSmsConfigInfo> configInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(_hidl_request, configInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(84, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCdmaBroadcastActivation(int serial, boolean activate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(activate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(85, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCDMASubscription(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(86, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void writeSmsToRuim(int serial, CdmaSmsWriteArgs cdmaSms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            cdmaSms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(87, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void deleteSmsOnRuim(int serial, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(88, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getDeviceIdentity(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(89, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void exitEmergencyCallbackMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(90, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getSmscAddress(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(91, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSmscAddress(int serial, String smsc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(smsc);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(92, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportSmsMemoryStatus(int serial, boolean available) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(available);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(93, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void reportStkServiceIsRunning(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(94, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCdmaSubscriptionSource(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(95, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIsimAuthentication(int serial, String challenge) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(challenge);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(96, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void acknowledgeIncomingGsmSmsWithPdu(int serial, boolean success, String ackPdu) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(success);
            _hidl_request.writeString(ackPdu);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(97, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendEnvelopeWithStatus(int serial, String contents) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(contents);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(98, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getVoiceRadioTechnology(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(99, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getCellInfoList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(100, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setCellInfoListRate(int serial, int rate) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(rate);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(101, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setInitialAttachApn(int serial, DataProfileInfo dataProfileInfo, boolean modemCognitive, boolean isRoaming) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dataProfileInfo.writeToParcel(_hidl_request);
            _hidl_request.writeBool(modemCognitive);
            _hidl_request.writeBool(isRoaming);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(102, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getImsRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(SubsidyLockSettingsObserver.SUBSIDY_UNLOCKED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendImsSms(int serial, ImsSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(104, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduBasicChannel(int serial, SimApdu message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(105, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccOpenLogicalChannel(int serial, String aid, int p2) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(aid);
            _hidl_request.writeInt32(p2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(106, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccCloseLogicalChannel(int serial, int channelId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(channelId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(107, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void iccTransmitApduLogicalChannel(int serial, SimApdu message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(108, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvReadItem(int serial, int itemId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(itemId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(109, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteItem(int serial, NvWriteItem item) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            item.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(110, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvWriteCdmaPrl(int serial, ArrayList<Byte> prl) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt8Vector(prl);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(111, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void nvResetConfig(int serial, int resetType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(resetType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.APN_TYPE_CONFLICT, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setUiccSubscription(int serial, SelectUiccSub uiccSub) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            uiccSub.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INVALID_PCSCF_ADDR, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataAllowed(int serial, boolean allow) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(allow);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.INTERNAL_CALL_PREEMPT_BY_HIGH_PRIO_APN, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getHardwareConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.EMM_ACCESS_BARRED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestIccSimAuthentication(int serial, int authContext, String authData, String aid) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(authContext);
            _hidl_request.writeString(authData);
            _hidl_request.writeString(aid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.EMERGENCY_IFACE_ONLY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setDataProfile(int serial, ArrayList<DataProfileInfo> profiles, boolean isRoaming) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            DataProfileInfo.writeVectorToParcel(_hidl_request, profiles);
            _hidl_request.writeBool(isRoaming);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.IFACE_MISMATCH, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void requestShutdown(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.COMPANION_IFACE_IN_USE, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getRadioCapability(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.IP_ADDRESS_MISMATCH, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setRadioCapability(int serial, RadioCapability rc) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            rc.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.IFACE_AND_POL_FAMILY_MISMATCH, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void startLceService(int serial, int reportInterval, boolean pullMode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(reportInterval);
            _hidl_request.writeBool(pullMode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.EMM_ACCESS_BARRED_INFINITE_RETRY, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void stopLceService(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(DataCallFailCause.AUTH_FAILURE_ON_EMERGENCY_CALL, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void pullLceData(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(123, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void getModemActivityInfo(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(124, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setAllowedCarriers(int serial, boolean allAllowed, CarrierRestrictions carriers) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
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

        public void getAllowedCarriers(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(126, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void sendDeviceState(int serial, int deviceStateType, boolean state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(deviceStateType);
            _hidl_request.writeBool(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(LastCallFailCause.INTERWORKING_UNSPECIFIED, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setIndicationFilter(int serial, int indicationFilter) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(indicationFilter);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(128, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void setSimCardPower(int serial, boolean powerUp) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(powerUp);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(129, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        public void responseAcknowledgement() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IRadio.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(130, _hidl_request, _hidl_reply, 1);
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

    public static abstract class Stub extends HwBinder implements IRadio {
        public IHwBinder asBinder() {
            return this;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(new String[]{IRadio.kInterfaceName, IBase.kInterfaceName}));
        }

        public void debug(NativeHandle fd, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[][]{new byte[]{-101, 90, -92, -103, -20, 59, 66, 38, -15, 95, 72, -11, -19, 8, -119, 110, 47, -64, 103, 111, -105, -116, -98, 25, -100, 29, -94, 29, -86, -16, 2, -90}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}}));
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
            if (IRadio.kInterfaceName.equals(descriptor)) {
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
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v53, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v55, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v56, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v57, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v58, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v59, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v60, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v61, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v62, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v63, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v64, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v65, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v66, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v67, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v68, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v69, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v70, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v71, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v73, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v74, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v75, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v76, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v77, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v78, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v79, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v80, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v82, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v83, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v85, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v86, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v87, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v88, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v89, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v90, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v91, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v92, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v93, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v94, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v95, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v96, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v97, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v98, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v99, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v100, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v101, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v102, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v103, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v104, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v105, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v106, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v107, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v108, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v109, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v110, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v111, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v112, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v113, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v114, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v115, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v116, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v117, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v118, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v119, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v120, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v121, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v122, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v123, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v124, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v125, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v126, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v127, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v128, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v129, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v130, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v131, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v132, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v133, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v134, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v135, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v136, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v137, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v138, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v139, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v140, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v141, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v142, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v143, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v144, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v145, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v146, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v147, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v148, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v149, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v150, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v151, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v152, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v153, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v154, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v155, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v156, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v157, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v158, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v159, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v160, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v161, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v162, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v163, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v164, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v165, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v166, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v167, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v168, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v169, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v170, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v171, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v172, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v173, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v174, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v175, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v176, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v177, resolved type: boolean} */
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
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v261, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v263, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v264, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v265, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v266, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v267, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v268, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v269, resolved type: boolean} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v270, resolved type: boolean} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onTransact(int r18, android.os.HwParcel r19, android.os.HwParcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r7 = r17
                r8 = r19
                r9 = r20
                java.lang.String r0 = "android.hardware.radio@1.0::IRadio"
                r1 = -2147483648(0xffffffff80000000, float:-0.0)
                r2 = 0
                r3 = 1
                switch(r18) {
                    case 1: goto L_0x1147;
                    case 2: goto L_0x112e;
                    case 3: goto L_0x110d;
                    case 4: goto L_0x10e6;
                    case 5: goto L_0x10c3;
                    case 6: goto L_0x109c;
                    case 7: goto L_0x1075;
                    case 8: goto L_0x104e;
                    case 9: goto L_0x102f;
                    case 10: goto L_0x1014;
                    case 11: goto L_0x0ff1;
                    case 12: goto L_0x0fd2;
                    case 13: goto L_0x0fb3;
                    case 14: goto L_0x0f98;
                    case 15: goto L_0x0f7d;
                    case 16: goto L_0x0f62;
                    case 17: goto L_0x0f47;
                    case 18: goto L_0x0f2c;
                    case 19: goto L_0x0f11;
                    case 20: goto L_0x0ef6;
                    case 21: goto L_0x0edb;
                    case 22: goto L_0x0ec0;
                    case 23: goto L_0x0ea5;
                    case 24: goto L_0x0e86;
                    case 25: goto L_0x0e67;
                    case 26: goto L_0x0e44;
                    case 27: goto L_0x0e21;
                    case 28: goto L_0x0de3;
                    case 29: goto L_0x0dc0;
                    case 30: goto L_0x0da1;
                    case 31: goto L_0x0d86;
                    case 32: goto L_0x0d6b;
                    case 33: goto L_0x0d4c;
                    case 34: goto L_0x0d29;
                    case 35: goto L_0x0d06;
                    case 36: goto L_0x0ce7;
                    case 37: goto L_0x0cc4;
                    case 38: goto L_0x0ca1;
                    case 39: goto L_0x0c86;
                    case 40: goto L_0x0c63;
                    case 41: goto L_0x0c30;
                    case 42: goto L_0x0bf7;
                    case 43: goto L_0x0bd0;
                    case 44: goto L_0x0bb5;
                    case 45: goto L_0x0b9a;
                    case 46: goto L_0x0b7b;
                    case 47: goto L_0x0b60;
                    case 48: goto L_0x0b41;
                    case 49: goto L_0x0b26;
                    case 50: goto L_0x0b0b;
                    case 51: goto L_0x0aec;
                    case 52: goto L_0x0acd;
                    case 53: goto L_0x0ab2;
                    case 54: goto L_0x0a97;
                    case 55: goto L_0x0a7c;
                    case 56: goto L_0x0a5d;
                    case 57: goto L_0x0a3a;
                    case 58: goto L_0x0a1b;
                    case 59: goto L_0x09fc;
                    case 60: goto L_0x09e1;
                    case 61: goto L_0x09c2;
                    case 62: goto L_0x09a3;
                    case 63: goto L_0x0984;
                    case 64: goto L_0x0969;
                    case 65: goto L_0x094a;
                    case 66: goto L_0x092f;
                    case 67: goto L_0x0914;
                    case 68: goto L_0x08f5;
                    case 69: goto L_0x08d6;
                    case 70: goto L_0x08b7;
                    case 71: goto L_0x089c;
                    case 72: goto L_0x087d;
                    case 73: goto L_0x0862;
                    case 74: goto L_0x0843;
                    case 75: goto L_0x0828;
                    case 76: goto L_0x0809;
                    case 77: goto L_0x07e2;
                    case 78: goto L_0x07bf;
                    case 79: goto L_0x079c;
                    case 80: goto L_0x0781;
                    case 81: goto L_0x0762;
                    case 82: goto L_0x0743;
                    case 83: goto L_0x0728;
                    case 84: goto L_0x0709;
                    case 85: goto L_0x06ea;
                    case 86: goto L_0x06cf;
                    case 87: goto L_0x06ac;
                    case 88: goto L_0x068d;
                    case 89: goto L_0x0672;
                    case 90: goto L_0x0657;
                    case 91: goto L_0x063c;
                    case 92: goto L_0x061d;
                    case 93: goto L_0x05fe;
                    case 94: goto L_0x05e3;
                    case 95: goto L_0x05c8;
                    case 96: goto L_0x05a9;
                    case 97: goto L_0x0586;
                    case 98: goto L_0x0567;
                    case 99: goto L_0x054c;
                    case 100: goto L_0x0531;
                    case 101: goto L_0x0512;
                    case 102: goto L_0x04e7;
                    case 103: goto L_0x04cc;
                    case 104: goto L_0x04a9;
                    case 105: goto L_0x0486;
                    case 106: goto L_0x0463;
                    case 107: goto L_0x0444;
                    case 108: goto L_0x0421;
                    case 109: goto L_0x0402;
                    case 110: goto L_0x03df;
                    case 111: goto L_0x03c0;
                    case 112: goto L_0x03a1;
                    case 113: goto L_0x037e;
                    case 114: goto L_0x035f;
                    case 115: goto L_0x0344;
                    case 116: goto L_0x031d;
                    case 117: goto L_0x02fa;
                    case 118: goto L_0x02df;
                    case 119: goto L_0x02c4;
                    case 120: goto L_0x02a1;
                    case 121: goto L_0x027e;
                    case 122: goto L_0x0263;
                    case 123: goto L_0x0248;
                    case 124: goto L_0x022d;
                    case 125: goto L_0x0206;
                    case 126: goto L_0x01eb;
                    case 127: goto L_0x01c8;
                    case 128: goto L_0x01a9;
                    case 129: goto L_0x018a;
                    case 130: goto L_0x0173;
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
                goto L_0x1173
            L_0x0016:
                r0 = r21 & 1
                if (r0 == 0) goto L_0x001b
                r2 = r3
            L_0x001b:
                r0 = r2
                if (r0 == 0) goto L_0x1173
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0026:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x002b
                r2 = r3
            L_0x002b:
                if (r2 == r3) goto L_0x0035
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0035:
                r8.enforceInterface(r0)
                r17.notifySyspropsChanged()
                goto L_0x1173
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
                goto L_0x1173
            L_0x004d:
                r8.enforceInterface(r0)
                android.hidl.base.V1_0.DebugInfo r0 = r17.getDebugInfo()
                r9.writeStatus(r2)
                r0.writeToParcel(r9)
                r20.send()
                goto L_0x1173
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
                goto L_0x1173
            L_0x006f:
                r8.enforceInterface(r0)
                r17.ping()
                r9.writeStatus(r2)
                r20.send()
                goto L_0x1173
            L_0x007d:
                r0 = r21 & 1
                if (r0 == 0) goto L_0x0082
                r2 = r3
            L_0x0082:
                r0 = r2
                if (r0 == 0) goto L_0x1173
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x008d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0092
                r2 = r3
            L_0x0092:
                if (r2 == r3) goto L_0x009c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x009c:
                r8.enforceInterface(r0)
                r17.setHALInstrumentation()
                goto L_0x1173
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
                goto L_0x1173
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
                goto L_0x1173
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
                goto L_0x1173
            L_0x0119:
                r8.enforceInterface(r0)
                java.lang.String r0 = r17.interfaceDescriptor()
                r9.writeStatus(r2)
                r9.writeString(r0)
                r20.send()
                goto L_0x1173
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
                goto L_0x1173
            L_0x013b:
                r8.enforceInterface(r0)
                android.os.NativeHandle r0 = r19.readNativeHandle()
                java.util.ArrayList r1 = r19.readStringVector()
                r7.debug(r0, r1)
                r9.writeStatus(r2)
                r20.send()
                goto L_0x1173
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
                goto L_0x1173
            L_0x0161:
                r8.enforceInterface(r0)
                java.util.ArrayList r0 = r17.interfaceChain()
                r9.writeStatus(r2)
                r9.writeStringVector(r0)
                r20.send()
                goto L_0x1173
            L_0x0173:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0178
                r2 = r3
            L_0x0178:
                if (r2 == r3) goto L_0x0182
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0182:
                r8.enforceInterface(r0)
                r17.responseAcknowledgement()
                goto L_0x1173
            L_0x018a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x018f
                r2 = r3
            L_0x018f:
                if (r2 == r3) goto L_0x0199
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0199:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setSimCardPower(r0, r1)
                goto L_0x1173
            L_0x01a9:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x01ae
                r2 = r3
            L_0x01ae:
                if (r2 == r3) goto L_0x01b8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x01b8:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setIndicationFilter(r0, r1)
                goto L_0x1173
            L_0x01c8:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x01cd
                r2 = r3
            L_0x01cd:
                if (r2 == r3) goto L_0x01d7
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x01d7:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                boolean r3 = r19.readBool()
                r7.sendDeviceState(r0, r1, r3)
                goto L_0x1173
            L_0x01eb:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x01f0
                r2 = r3
            L_0x01f0:
                if (r2 == r3) goto L_0x01fa
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x01fa:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getAllowedCarriers(r0)
                goto L_0x1173
            L_0x0206:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x020b
                r2 = r3
            L_0x020b:
                if (r2 == r3) goto L_0x0215
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0215:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                android.hardware.radio.V1_0.CarrierRestrictions r3 = new android.hardware.radio.V1_0.CarrierRestrictions
                r3.<init>()
                r3.readFromParcel(r8)
                r7.setAllowedCarriers(r0, r1, r3)
                goto L_0x1173
            L_0x022d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0232
                r2 = r3
            L_0x0232:
                if (r2 == r3) goto L_0x023c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x023c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getModemActivityInfo(r0)
                goto L_0x1173
            L_0x0248:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x024d
                r2 = r3
            L_0x024d:
                if (r2 == r3) goto L_0x0257
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0257:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.pullLceData(r0)
                goto L_0x1173
            L_0x0263:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0268
                r2 = r3
            L_0x0268:
                if (r2 == r3) goto L_0x0272
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0272:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.stopLceService(r0)
                goto L_0x1173
            L_0x027e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0283
                r2 = r3
            L_0x0283:
                if (r2 == r3) goto L_0x028d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x028d:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                boolean r3 = r19.readBool()
                r7.startLceService(r0, r1, r3)
                goto L_0x1173
            L_0x02a1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x02a6
                r2 = r3
            L_0x02a6:
                if (r2 == r3) goto L_0x02b0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x02b0:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.RadioCapability r1 = new android.hardware.radio.V1_0.RadioCapability
                r1.<init>()
                r1.readFromParcel(r8)
                r7.setRadioCapability(r0, r1)
                goto L_0x1173
            L_0x02c4:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x02c9
                r2 = r3
            L_0x02c9:
                if (r2 == r3) goto L_0x02d3
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x02d3:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getRadioCapability(r0)
                goto L_0x1173
            L_0x02df:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x02e4
                r2 = r3
            L_0x02e4:
                if (r2 == r3) goto L_0x02ee
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x02ee:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.requestShutdown(r0)
                goto L_0x1173
            L_0x02fa:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x02ff
                r2 = r3
            L_0x02ff:
                if (r2 == r3) goto L_0x0309
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0309:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.util.ArrayList r1 = android.hardware.radio.V1_0.DataProfileInfo.readVectorFromParcel(r19)
                boolean r3 = r19.readBool()
                r7.setDataProfile(r0, r1, r3)
                goto L_0x1173
            L_0x031d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0322
                r2 = r3
            L_0x0322:
                if (r2 == r3) goto L_0x032c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x032c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.requestIccSimAuthentication(r0, r1, r3, r4)
                goto L_0x1173
            L_0x0344:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0349
                r2 = r3
            L_0x0349:
                if (r2 == r3) goto L_0x0353
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0353:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getHardwareConfig(r0)
                goto L_0x1173
            L_0x035f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0364
                r2 = r3
            L_0x0364:
                if (r2 == r3) goto L_0x036e
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x036e:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setDataAllowed(r0, r1)
                goto L_0x1173
            L_0x037e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0383
                r2 = r3
            L_0x0383:
                if (r2 == r3) goto L_0x038d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x038d:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.SelectUiccSub r1 = new android.hardware.radio.V1_0.SelectUiccSub
                r1.<init>()
                r1.readFromParcel(r8)
                r7.setUiccSubscription(r0, r1)
                goto L_0x1173
            L_0x03a1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x03a6
                r2 = r3
            L_0x03a6:
                if (r2 == r3) goto L_0x03b0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x03b0:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.nvResetConfig(r0, r1)
                goto L_0x1173
            L_0x03c0:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x03c5
                r2 = r3
            L_0x03c5:
                if (r2 == r3) goto L_0x03cf
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x03cf:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.util.ArrayList r1 = r19.readInt8Vector()
                r7.nvWriteCdmaPrl(r0, r1)
                goto L_0x1173
            L_0x03df:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x03e4
                r2 = r3
            L_0x03e4:
                if (r2 == r3) goto L_0x03ee
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x03ee:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.NvWriteItem r1 = new android.hardware.radio.V1_0.NvWriteItem
                r1.<init>()
                r1.readFromParcel(r8)
                r7.nvWriteItem(r0, r1)
                goto L_0x1173
            L_0x0402:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0407
                r2 = r3
            L_0x0407:
                if (r2 == r3) goto L_0x0411
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0411:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.nvReadItem(r0, r1)
                goto L_0x1173
            L_0x0421:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0426
                r2 = r3
            L_0x0426:
                if (r2 == r3) goto L_0x0430
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0430:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.SimApdu r1 = new android.hardware.radio.V1_0.SimApdu
                r1.<init>()
                r1.readFromParcel(r8)
                r7.iccTransmitApduLogicalChannel(r0, r1)
                goto L_0x1173
            L_0x0444:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0449
                r2 = r3
            L_0x0449:
                if (r2 == r3) goto L_0x0453
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0453:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.iccCloseLogicalChannel(r0, r1)
                goto L_0x1173
            L_0x0463:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0468
                r2 = r3
            L_0x0468:
                if (r2 == r3) goto L_0x0472
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0472:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                int r3 = r19.readInt32()
                r7.iccOpenLogicalChannel(r0, r1, r3)
                goto L_0x1173
            L_0x0486:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x048b
                r2 = r3
            L_0x048b:
                if (r2 == r3) goto L_0x0495
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0495:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.SimApdu r1 = new android.hardware.radio.V1_0.SimApdu
                r1.<init>()
                r1.readFromParcel(r8)
                r7.iccTransmitApduBasicChannel(r0, r1)
                goto L_0x1173
            L_0x04a9:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x04ae
                r2 = r3
            L_0x04ae:
                if (r2 == r3) goto L_0x04b8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x04b8:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.ImsSmsMessage r1 = new android.hardware.radio.V1_0.ImsSmsMessage
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendImsSms(r0, r1)
                goto L_0x1173
            L_0x04cc:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x04d1
                r2 = r3
            L_0x04d1:
                if (r2 == r3) goto L_0x04db
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x04db:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getImsRegistrationState(r0)
                goto L_0x1173
            L_0x04e7:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x04ec
                r2 = r3
            L_0x04ec:
                if (r2 == r3) goto L_0x04f6
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x04f6:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.DataProfileInfo r1 = new android.hardware.radio.V1_0.DataProfileInfo
                r1.<init>()
                r1.readFromParcel(r8)
                boolean r3 = r19.readBool()
                boolean r4 = r19.readBool()
                r7.setInitialAttachApn(r0, r1, r3, r4)
                goto L_0x1173
            L_0x0512:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0517
                r2 = r3
            L_0x0517:
                if (r2 == r3) goto L_0x0521
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0521:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setCellInfoListRate(r0, r1)
                goto L_0x1173
            L_0x0531:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0536
                r2 = r3
            L_0x0536:
                if (r2 == r3) goto L_0x0540
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0540:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getCellInfoList(r0)
                goto L_0x1173
            L_0x054c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0551
                r2 = r3
            L_0x0551:
                if (r2 == r3) goto L_0x055b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x055b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getVoiceRadioTechnology(r0)
                goto L_0x1173
            L_0x0567:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x056c
                r2 = r3
            L_0x056c:
                if (r2 == r3) goto L_0x0576
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0576:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.sendEnvelopeWithStatus(r0, r1)
                goto L_0x1173
            L_0x0586:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x058b
                r2 = r3
            L_0x058b:
                if (r2 == r3) goto L_0x0595
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0595:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                java.lang.String r3 = r19.readString()
                r7.acknowledgeIncomingGsmSmsWithPdu(r0, r1, r3)
                goto L_0x1173
            L_0x05a9:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x05ae
                r2 = r3
            L_0x05ae:
                if (r2 == r3) goto L_0x05b8
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x05b8:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.requestIsimAuthentication(r0, r1)
                goto L_0x1173
            L_0x05c8:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x05cd
                r2 = r3
            L_0x05cd:
                if (r2 == r3) goto L_0x05d7
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x05d7:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getCdmaSubscriptionSource(r0)
                goto L_0x1173
            L_0x05e3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x05e8
                r2 = r3
            L_0x05e8:
                if (r2 == r3) goto L_0x05f2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x05f2:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.reportStkServiceIsRunning(r0)
                goto L_0x1173
            L_0x05fe:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0603
                r2 = r3
            L_0x0603:
                if (r2 == r3) goto L_0x060d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x060d:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.reportSmsMemoryStatus(r0, r1)
                goto L_0x1173
            L_0x061d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0622
                r2 = r3
            L_0x0622:
                if (r2 == r3) goto L_0x062c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x062c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.setSmscAddress(r0, r1)
                goto L_0x1173
            L_0x063c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0641
                r2 = r3
            L_0x0641:
                if (r2 == r3) goto L_0x064b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x064b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getSmscAddress(r0)
                goto L_0x1173
            L_0x0657:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x065c
                r2 = r3
            L_0x065c:
                if (r2 == r3) goto L_0x0666
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0666:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.exitEmergencyCallbackMode(r0)
                goto L_0x1173
            L_0x0672:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0677
                r2 = r3
            L_0x0677:
                if (r2 == r3) goto L_0x0681
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0681:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getDeviceIdentity(r0)
                goto L_0x1173
            L_0x068d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0692
                r2 = r3
            L_0x0692:
                if (r2 == r3) goto L_0x069c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x069c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.deleteSmsOnRuim(r0, r1)
                goto L_0x1173
            L_0x06ac:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x06b1
                r2 = r3
            L_0x06b1:
                if (r2 == r3) goto L_0x06bb
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x06bb:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.CdmaSmsWriteArgs r1 = new android.hardware.radio.V1_0.CdmaSmsWriteArgs
                r1.<init>()
                r1.readFromParcel(r8)
                r7.writeSmsToRuim(r0, r1)
                goto L_0x1173
            L_0x06cf:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x06d4
                r2 = r3
            L_0x06d4:
                if (r2 == r3) goto L_0x06de
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x06de:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getCDMASubscription(r0)
                goto L_0x1173
            L_0x06ea:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x06ef
                r2 = r3
            L_0x06ef:
                if (r2 == r3) goto L_0x06f9
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x06f9:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setCdmaBroadcastActivation(r0, r1)
                goto L_0x1173
            L_0x0709:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x070e
                r2 = r3
            L_0x070e:
                if (r2 == r3) goto L_0x0718
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0718:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.util.ArrayList r1 = android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.readVectorFromParcel(r19)
                r7.setCdmaBroadcastConfig(r0, r1)
                goto L_0x1173
            L_0x0728:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x072d
                r2 = r3
            L_0x072d:
                if (r2 == r3) goto L_0x0737
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0737:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getCdmaBroadcastConfig(r0)
                goto L_0x1173
            L_0x0743:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0748
                r2 = r3
            L_0x0748:
                if (r2 == r3) goto L_0x0752
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0752:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setGsmBroadcastActivation(r0, r1)
                goto L_0x1173
            L_0x0762:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0767
                r2 = r3
            L_0x0767:
                if (r2 == r3) goto L_0x0771
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0771:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.util.ArrayList r1 = android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.readVectorFromParcel(r19)
                r7.setGsmBroadcastConfig(r0, r1)
                goto L_0x1173
            L_0x0781:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0786
                r2 = r3
            L_0x0786:
                if (r2 == r3) goto L_0x0790
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0790:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getGsmBroadcastConfig(r0)
                goto L_0x1173
            L_0x079c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x07a1
                r2 = r3
            L_0x07a1:
                if (r2 == r3) goto L_0x07ab
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x07ab:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.CdmaSmsAck r1 = new android.hardware.radio.V1_0.CdmaSmsAck
                r1.<init>()
                r1.readFromParcel(r8)
                r7.acknowledgeLastIncomingCdmaSms(r0, r1)
                goto L_0x1173
            L_0x07bf:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x07c4
                r2 = r3
            L_0x07c4:
                if (r2 == r3) goto L_0x07ce
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x07ce:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.CdmaSmsMessage r1 = new android.hardware.radio.V1_0.CdmaSmsMessage
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendCdmaSms(r0, r1)
                goto L_0x1173
            L_0x07e2:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x07e7
                r2 = r3
            L_0x07e7:
                if (r2 == r3) goto L_0x07f1
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x07f1:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                int r3 = r19.readInt32()
                int r4 = r19.readInt32()
                r7.sendBurstDtmf(r0, r1, r3, r4)
                goto L_0x1173
            L_0x0809:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x080e
                r2 = r3
            L_0x080e:
                if (r2 == r3) goto L_0x0818
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0818:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.sendCDMAFeatureCode(r0, r1)
                goto L_0x1173
            L_0x0828:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x082d
                r2 = r3
            L_0x082d:
                if (r2 == r3) goto L_0x0837
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0837:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getPreferredVoicePrivacy(r0)
                goto L_0x1173
            L_0x0843:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0848
                r2 = r3
            L_0x0848:
                if (r2 == r3) goto L_0x0852
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0852:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setPreferredVoicePrivacy(r0, r1)
                goto L_0x1173
            L_0x0862:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0867
                r2 = r3
            L_0x0867:
                if (r2 == r3) goto L_0x0871
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0871:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getTTYMode(r0)
                goto L_0x1173
            L_0x087d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0882
                r2 = r3
            L_0x0882:
                if (r2 == r3) goto L_0x088c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x088c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setTTYMode(r0, r1)
                goto L_0x1173
            L_0x089c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08a1
                r2 = r3
            L_0x08a1:
                if (r2 == r3) goto L_0x08ab
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x08ab:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getCdmaRoamingPreference(r0)
                goto L_0x1173
            L_0x08b7:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08bc
                r2 = r3
            L_0x08bc:
                if (r2 == r3) goto L_0x08c6
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x08c6:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setCdmaRoamingPreference(r0, r1)
                goto L_0x1173
            L_0x08d6:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08db
                r2 = r3
            L_0x08db:
                if (r2 == r3) goto L_0x08e5
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x08e5:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setCdmaSubscriptionSource(r0, r1)
                goto L_0x1173
            L_0x08f5:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x08fa
                r2 = r3
            L_0x08fa:
                if (r2 == r3) goto L_0x0904
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0904:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setLocationUpdates(r0, r1)
                goto L_0x1173
            L_0x0914:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0919
                r2 = r3
            L_0x0919:
                if (r2 == r3) goto L_0x0923
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0923:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getNeighboringCids(r0)
                goto L_0x1173
            L_0x092f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0934
                r2 = r3
            L_0x0934:
                if (r2 == r3) goto L_0x093e
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x093e:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getPreferredNetworkType(r0)
                goto L_0x1173
            L_0x094a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x094f
                r2 = r3
            L_0x094f:
                if (r2 == r3) goto L_0x0959
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0959:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setPreferredNetworkType(r0, r1)
                goto L_0x1173
            L_0x0969:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x096e
                r2 = r3
            L_0x096e:
                if (r2 == r3) goto L_0x0978
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0978:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.explicitCallTransfer(r0)
                goto L_0x1173
            L_0x0984:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0989
                r2 = r3
            L_0x0989:
                if (r2 == r3) goto L_0x0993
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0993:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.handleStkCallSetupRequestFromSim(r0, r1)
                goto L_0x1173
            L_0x09a3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x09a8
                r2 = r3
            L_0x09a8:
                if (r2 == r3) goto L_0x09b2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x09b2:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.sendTerminalResponseToSim(r0, r1)
                goto L_0x1173
            L_0x09c2:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x09c7
                r2 = r3
            L_0x09c7:
                if (r2 == r3) goto L_0x09d1
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x09d1:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.sendEnvelope(r0, r1)
                goto L_0x1173
            L_0x09e1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x09e6
                r2 = r3
            L_0x09e6:
                if (r2 == r3) goto L_0x09f0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x09f0:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getAvailableBandModes(r0)
                goto L_0x1173
            L_0x09fc:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a01
                r2 = r3
            L_0x0a01:
                if (r2 == r3) goto L_0x0a0b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0a0b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setBandMode(r0, r1)
                goto L_0x1173
            L_0x0a1b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a20
                r2 = r3
            L_0x0a20:
                if (r2 == r3) goto L_0x0a2a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0a2a:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.deleteSmsOnSim(r0, r1)
                goto L_0x1173
            L_0x0a3a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a3f
                r2 = r3
            L_0x0a3f:
                if (r2 == r3) goto L_0x0a49
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0a49:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.SmsWriteArgs r1 = new android.hardware.radio.V1_0.SmsWriteArgs
                r1.<init>()
                r1.readFromParcel(r8)
                r7.writeSmsToSim(r0, r1)
                goto L_0x1173
            L_0x0a5d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a62
                r2 = r3
            L_0x0a62:
                if (r2 == r3) goto L_0x0a6c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0a6c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setSuppServiceNotifications(r0, r1)
                goto L_0x1173
            L_0x0a7c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a81
                r2 = r3
            L_0x0a81:
                if (r2 == r3) goto L_0x0a8b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0a8b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getDataCallList(r0)
                goto L_0x1173
            L_0x0a97:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0a9c
                r2 = r3
            L_0x0a9c:
                if (r2 == r3) goto L_0x0aa6
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0aa6:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getClip(r0)
                goto L_0x1173
            L_0x0ab2:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ab7
                r2 = r3
            L_0x0ab7:
                if (r2 == r3) goto L_0x0ac1
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0ac1:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getMute(r0)
                goto L_0x1173
            L_0x0acd:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ad2
                r2 = r3
            L_0x0ad2:
                if (r2 == r3) goto L_0x0adc
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0adc:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setMute(r0, r1)
                goto L_0x1173
            L_0x0aec:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0af1
                r2 = r3
            L_0x0af1:
                if (r2 == r3) goto L_0x0afb
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0afb:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.separateConnection(r0, r1)
                goto L_0x1173
            L_0x0b0b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b10
                r2 = r3
            L_0x0b10:
                if (r2 == r3) goto L_0x0b1a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0b1a:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getBasebandVersion(r0)
                goto L_0x1173
            L_0x0b26:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b2b
                r2 = r3
            L_0x0b2b:
                if (r2 == r3) goto L_0x0b35
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0b35:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.stopDtmf(r0)
                goto L_0x1173
            L_0x0b41:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b46
                r2 = r3
            L_0x0b46:
                if (r2 == r3) goto L_0x0b50
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0b50:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.startDtmf(r0, r1)
                goto L_0x1173
            L_0x0b60:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b65
                r2 = r3
            L_0x0b65:
                if (r2 == r3) goto L_0x0b6f
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0b6f:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getAvailableNetworks(r0)
                goto L_0x1173
            L_0x0b7b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b80
                r2 = r3
            L_0x0b80:
                if (r2 == r3) goto L_0x0b8a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0b8a:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.setNetworkSelectionModeManual(r0, r1)
                goto L_0x1173
            L_0x0b9a:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0b9f
                r2 = r3
            L_0x0b9f:
                if (r2 == r3) goto L_0x0ba9
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0ba9:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.setNetworkSelectionModeAutomatic(r0)
                goto L_0x1173
            L_0x0bb5:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0bba
                r2 = r3
            L_0x0bba:
                if (r2 == r3) goto L_0x0bc4
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0bc4:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getNetworkSelectionMode(r0)
                goto L_0x1173
            L_0x0bd0:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0bd5
                r2 = r3
            L_0x0bd5:
                if (r2 == r3) goto L_0x0bdf
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0bdf:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.setBarringPassword(r0, r1, r3, r4)
                goto L_0x1173
            L_0x0bf7:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0bfc
                r2 = r3
            L_0x0bfc:
                r10 = r2
                if (r10 == r3) goto L_0x0c07
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0c07:
                r8.enforceInterface(r0)
                int r11 = r19.readInt32()
                java.lang.String r12 = r19.readString()
                boolean r13 = r19.readBool()
                java.lang.String r14 = r19.readString()
                int r15 = r19.readInt32()
                java.lang.String r16 = r19.readString()
                r0 = r17
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r6 = r16
                r0.setFacilityLockForApp(r1, r2, r3, r4, r5, r6)
                goto L_0x1173
            L_0x0c30:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c35
                r2 = r3
            L_0x0c35:
                r6 = r2
                if (r6 == r3) goto L_0x0c40
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0c40:
                r8.enforceInterface(r0)
                int r10 = r19.readInt32()
                java.lang.String r11 = r19.readString()
                java.lang.String r12 = r19.readString()
                int r13 = r19.readInt32()
                java.lang.String r14 = r19.readString()
                r0 = r17
                r1 = r10
                r2 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.getFacilityLockForApp(r1, r2, r3, r4, r5)
                goto L_0x1173
            L_0x0c63:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c68
                r2 = r3
            L_0x0c68:
                if (r2 == r3) goto L_0x0c72
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0c72:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                boolean r3 = r19.readBool()
                r7.deactivateDataCall(r0, r1, r3)
                goto L_0x1173
            L_0x0c86:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0c8b
                r2 = r3
            L_0x0c8b:
                if (r2 == r3) goto L_0x0c95
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0c95:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.acceptCall(r0)
                goto L_0x1173
            L_0x0ca1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ca6
                r2 = r3
            L_0x0ca6:
                if (r2 == r3) goto L_0x0cb0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0cb0:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                int r3 = r19.readInt32()
                r7.acknowledgeLastIncomingGsmSms(r0, r1, r3)
                goto L_0x1173
            L_0x0cc4:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0cc9
                r2 = r3
            L_0x0cc9:
                if (r2 == r3) goto L_0x0cd3
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0cd3:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                int r3 = r19.readInt32()
                r7.setCallWaiting(r0, r1, r3)
                goto L_0x1173
            L_0x0ce7:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0cec
                r2 = r3
            L_0x0cec:
                if (r2 == r3) goto L_0x0cf6
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0cf6:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.getCallWaiting(r0, r1)
                goto L_0x1173
            L_0x0d06:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d0b
                r2 = r3
            L_0x0d0b:
                if (r2 == r3) goto L_0x0d15
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0d15:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.CallForwardInfo r1 = new android.hardware.radio.V1_0.CallForwardInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.setCallForward(r0, r1)
                goto L_0x1173
            L_0x0d29:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d2e
                r2 = r3
            L_0x0d2e:
                if (r2 == r3) goto L_0x0d38
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0d38:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.CallForwardInfo r1 = new android.hardware.radio.V1_0.CallForwardInfo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.getCallForwardStatus(r0, r1)
                goto L_0x1173
            L_0x0d4c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d51
                r2 = r3
            L_0x0d51:
                if (r2 == r3) goto L_0x0d5b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0d5b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.setClir(r0, r1)
                goto L_0x1173
            L_0x0d6b:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d70
                r2 = r3
            L_0x0d70:
                if (r2 == r3) goto L_0x0d7a
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0d7a:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getClir(r0)
                goto L_0x1173
            L_0x0d86:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0d8b
                r2 = r3
            L_0x0d8b:
                if (r2 == r3) goto L_0x0d95
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0d95:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.cancelPendingUssd(r0)
                goto L_0x1173
            L_0x0da1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0da6
                r2 = r3
            L_0x0da6:
                if (r2 == r3) goto L_0x0db0
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0db0:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.sendUssd(r0, r1)
                goto L_0x1173
            L_0x0dc0:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0dc5
                r2 = r3
            L_0x0dc5:
                if (r2 == r3) goto L_0x0dcf
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0dcf:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.IccIo r1 = new android.hardware.radio.V1_0.IccIo
                r1.<init>()
                r1.readFromParcel(r8)
                r7.iccIOForApp(r0, r1)
                goto L_0x1173
            L_0x0de3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0de8
                r2 = r3
            L_0x0de8:
                r10 = r2
                if (r10 == r3) goto L_0x0df3
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0df3:
                r8.enforceInterface(r0)
                int r11 = r19.readInt32()
                int r12 = r19.readInt32()
                android.hardware.radio.V1_0.DataProfileInfo r0 = new android.hardware.radio.V1_0.DataProfileInfo
                r0.<init>()
                r13 = r0
                r13.readFromParcel(r8)
                boolean r14 = r19.readBool()
                boolean r15 = r19.readBool()
                boolean r16 = r19.readBool()
                r0 = r17
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r5 = r15
                r6 = r16
                r0.setupDataCall(r1, r2, r3, r4, r5, r6)
                goto L_0x1173
            L_0x0e21:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e26
                r2 = r3
            L_0x0e26:
                if (r2 == r3) goto L_0x0e30
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0e30:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.GsmSmsMessage r1 = new android.hardware.radio.V1_0.GsmSmsMessage
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendSMSExpectMore(r0, r1)
                goto L_0x1173
            L_0x0e44:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e49
                r2 = r3
            L_0x0e49:
                if (r2 == r3) goto L_0x0e53
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0e53:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.GsmSmsMessage r1 = new android.hardware.radio.V1_0.GsmSmsMessage
                r1.<init>()
                r1.readFromParcel(r8)
                r7.sendSms(r0, r1)
                goto L_0x1173
            L_0x0e67:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e6c
                r2 = r3
            L_0x0e6c:
                if (r2 == r3) goto L_0x0e76
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0e76:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.sendDtmf(r0, r1)
                goto L_0x1173
            L_0x0e86:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0e8b
                r2 = r3
            L_0x0e8b:
                if (r2 == r3) goto L_0x0e95
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0e95:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                boolean r1 = r19.readBool()
                r7.setRadioPower(r0, r1)
                goto L_0x1173
            L_0x0ea5:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0eaa
                r2 = r3
            L_0x0eaa:
                if (r2 == r3) goto L_0x0eb4
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0eb4:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getOperator(r0)
                goto L_0x1173
            L_0x0ec0:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ec5
                r2 = r3
            L_0x0ec5:
                if (r2 == r3) goto L_0x0ecf
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0ecf:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getDataRegistrationState(r0)
                goto L_0x1173
            L_0x0edb:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ee0
                r2 = r3
            L_0x0ee0:
                if (r2 == r3) goto L_0x0eea
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0eea:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getVoiceRegistrationState(r0)
                goto L_0x1173
            L_0x0ef6:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0efb
                r2 = r3
            L_0x0efb:
                if (r2 == r3) goto L_0x0f05
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0f05:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getSignalStrength(r0)
                goto L_0x1173
            L_0x0f11:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f16
                r2 = r3
            L_0x0f16:
                if (r2 == r3) goto L_0x0f20
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0f20:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getLastCallFailCause(r0)
                goto L_0x1173
            L_0x0f2c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f31
                r2 = r3
            L_0x0f31:
                if (r2 == r3) goto L_0x0f3b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0f3b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.rejectCall(r0)
                goto L_0x1173
            L_0x0f47:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f4c
                r2 = r3
            L_0x0f4c:
                if (r2 == r3) goto L_0x0f56
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0f56:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.conference(r0)
                goto L_0x1173
            L_0x0f62:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f67
                r2 = r3
            L_0x0f67:
                if (r2 == r3) goto L_0x0f71
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0f71:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.switchWaitingOrHoldingAndActive(r0)
                goto L_0x1173
            L_0x0f7d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f82
                r2 = r3
            L_0x0f82:
                if (r2 == r3) goto L_0x0f8c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0f8c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.hangupForegroundResumeBackground(r0)
                goto L_0x1173
            L_0x0f98:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0f9d
                r2 = r3
            L_0x0f9d:
                if (r2 == r3) goto L_0x0fa7
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0fa7:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.hangupWaitingOrBackground(r0)
                goto L_0x1173
            L_0x0fb3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0fb8
                r2 = r3
            L_0x0fb8:
                if (r2 == r3) goto L_0x0fc2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0fc2:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                int r1 = r19.readInt32()
                r7.hangup(r0, r1)
                goto L_0x1173
            L_0x0fd2:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0fd7
                r2 = r3
            L_0x0fd7:
                if (r2 == r3) goto L_0x0fe1
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x0fe1:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.getImsiForApp(r0, r1)
                goto L_0x1173
            L_0x0ff1:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x0ff6
                r2 = r3
            L_0x0ff6:
                if (r2 == r3) goto L_0x1000
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x1000:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                android.hardware.radio.V1_0.Dial r1 = new android.hardware.radio.V1_0.Dial
                r1.<init>()
                r1.readFromParcel(r8)
                r7.dial(r0, r1)
                goto L_0x1173
            L_0x1014:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1019
                r2 = r3
            L_0x1019:
                if (r2 == r3) goto L_0x1023
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x1023:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getCurrentCalls(r0)
                goto L_0x1173
            L_0x102f:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1034
                r2 = r3
            L_0x1034:
                if (r2 == r3) goto L_0x103e
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x103e:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                r7.supplyNetworkDepersonalization(r0, r1)
                goto L_0x1173
            L_0x104e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1053
                r2 = r3
            L_0x1053:
                if (r2 == r3) goto L_0x105d
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x105d:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.changeIccPin2ForApp(r0, r1, r3, r4)
                goto L_0x1173
            L_0x1075:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x107a
                r2 = r3
            L_0x107a:
                if (r2 == r3) goto L_0x1084
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x1084:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.changeIccPinForApp(r0, r1, r3, r4)
                goto L_0x1173
            L_0x109c:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x10a1
                r2 = r3
            L_0x10a1:
                if (r2 == r3) goto L_0x10ab
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x10ab:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.supplyIccPuk2ForApp(r0, r1, r3, r4)
                goto L_0x1173
            L_0x10c3:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x10c8
                r2 = r3
            L_0x10c8:
                if (r2 == r3) goto L_0x10d2
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x10d2:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                r7.supplyIccPin2ForApp(r0, r1, r3)
                goto L_0x1173
            L_0x10e6:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x10eb
                r2 = r3
            L_0x10eb:
                if (r2 == r3) goto L_0x10f5
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x10f5:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                java.lang.String r4 = r19.readString()
                r7.supplyIccPukForApp(r0, r1, r3, r4)
                goto L_0x1173
            L_0x110d:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1112
                r2 = r3
            L_0x1112:
                if (r2 == r3) goto L_0x111b
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x111b:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                java.lang.String r1 = r19.readString()
                java.lang.String r3 = r19.readString()
                r7.supplyIccPinForApp(r0, r1, r3)
                goto L_0x1173
            L_0x112e:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x1133
                r2 = r3
            L_0x1133:
                if (r2 == r3) goto L_0x113c
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x113c:
                r8.enforceInterface(r0)
                int r0 = r19.readInt32()
                r7.getIccCardStatus(r0)
                goto L_0x1173
            L_0x1147:
                r4 = r21 & 1
                if (r4 == 0) goto L_0x114c
                goto L_0x114d
            L_0x114c:
                r3 = r2
            L_0x114d:
                if (r3 == 0) goto L_0x1156
                r9.writeStatus(r1)
                r20.send()
                goto L_0x1173
            L_0x1156:
                r8.enforceInterface(r0)
                android.os.IHwBinder r0 = r19.readStrongBinder()
                android.hardware.radio.V1_0.IRadioResponse r0 = android.hardware.radio.V1_0.IRadioResponse.asInterface(r0)
                android.os.IHwBinder r1 = r19.readStrongBinder()
                android.hardware.radio.V1_0.IRadioIndication r1 = android.hardware.radio.V1_0.IRadioIndication.asInterface(r1)
                r7.setResponseFunctions(r0, r1)
                r9.writeStatus(r2)
                r20.send()
            L_0x1173:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.V1_0.IRadio.Stub.onTransact(int, android.os.HwParcel, android.os.HwParcel, int):void");
        }
    }
}
