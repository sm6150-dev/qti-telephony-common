package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings.Global;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyManager.MultiSimVariants;
import android.util.Log;
import com.android.internal.telephony.IccCard;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import com.qualcomm.qti.internal.nrNetworkService.MainServiceImpl;
import com.qualcomm.qti.internal.telephony.primarycard.QtiPrimaryCardController;
import com.qualcomm.qti.internal.telephony.primarycard.QtiPrimaryCardUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import org.codeaurora.internal.Client;
import org.codeaurora.internal.IDepersoResCallback;
import org.codeaurora.internal.IDsda;
import org.codeaurora.internal.IExtTelephony.Stub;
import org.codeaurora.internal.INetworkCallback;
import org.codeaurora.internal.Token;

public class ExtTelephonyServiceImpl extends Stub {
    private static final String CONFIG_CURRENT_PRIMARY_SUB = "config_current_primary_sub";
    private static final boolean DBG = true;
    private static final String LOG_TAG = "ExtTelephonyServiceImpl";
    private static final String TELEPHONY_SERVICE_NAME = "extphone";
    private static Context mContext;
    private static ExtTelephonyServiceImpl sInstance = null;
    private IDsda mDsda = null;
    private QtiSmscHelper mQtiSmscHelper;

    public static ExtTelephonyServiceImpl init(Context context) {
        ExtTelephonyServiceImpl extTelephonyServiceImpl;
        synchronized (ExtTelephonyServiceImpl.class) {
            mContext = context;
            if (sInstance == null) {
                sInstance = new ExtTelephonyServiceImpl();
            } else {
                String str = LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("init() called multiple times!  sInstance = ");
                sb.append(sInstance);
                Log.wtf(str, sb.toString());
            }
            extTelephonyServiceImpl = sInstance;
        }
        return extTelephonyServiceImpl;
    }

    public static ExtTelephonyServiceImpl getInstance() {
        if (sInstance == null) {
            Log.wtf(LOG_TAG, "getInstance null");
        }
        return sInstance;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [com.qualcomm.qti.internal.telephony.ExtTelephonyServiceImpl, android.os.IBinder] */
    private ExtTelephonyServiceImpl() {
        logd("init constructor ");
        if (ServiceManager.getService(TELEPHONY_SERVICE_NAME) == null) {
            ServiceManager.addService(TELEPHONY_SERVICE_NAME, this);
        }
        this.mQtiSmscHelper = new QtiSmscHelper();
    }

    public int getCurrentUiccCardProvisioningStatus(int slotId) {
        return QtiUiccCardProvisioner.getInstance().getCurrentUiccCardProvisioningStatus(slotId);
    }

    public int getUiccCardProvisioningUserPreference(int slotId) {
        return QtiUiccCardProvisioner.getInstance().getUiccCardProvisioningUserPreference(slotId);
    }

    public int activateUiccCard(int slotId) {
        return QtiUiccCardProvisioner.getInstance().activateUiccCard(slotId);
    }

    public int deactivateUiccCard(int slotId) {
        return QtiUiccCardProvisioner.getInstance().deactivateUiccCard(slotId);
    }

    public boolean isSMSPromptEnabled() {
        if (QtiSubscriptionController.getInstance() == null) {
            Log.wtf(LOG_TAG, "QtiSubscriptionController getInstance is null");
        }
        return QtiSubscriptionController.getInstance().isSMSPromptEnabled();
    }

    public void setSMSPromptEnabled(boolean enabled) {
        if (QtiSubscriptionController.getInstance() == null) {
            Log.wtf(LOG_TAG, "QtiSubscriptionController getInstance is null");
        }
        QtiSubscriptionController.getInstance().setSMSPromptEnabled(enabled);
    }

    public int getPhoneIdForECall() {
        return QtiEmergencyCallHelper.getPhoneIdForECall();
    }

    public int getPrimaryStackPhoneId() {
        return QtiEmergencyCallHelper.getPrimaryStackPhoneId();
    }

    public void setDsdaAdapter(IDsda a) {
        StringBuilder sb = new StringBuilder();
        sb.append("setDsdaAdapter:");
        sb.append(a);
        logd(sb.toString());
        this.mDsda = a;
    }

    public void switchToActiveSub(int sub) {
        StringBuilder sb = new StringBuilder();
        sb.append("switchToActiveSub:");
        sb.append(sub);
        sb.append(" mDsda:");
        sb.append(this.mDsda);
        logd(sb.toString());
        try {
            this.mDsda.switchToActiveSub(sub);
        } catch (RemoteException ex) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("switchToActiveSub:");
            sb2.append(ex);
            logd(sb2.toString());
        }
    }

    public int getActiveSubscription() {
        StringBuilder sb = new StringBuilder();
        sb.append("getActiveSubscription mDsda:");
        sb.append(this.mDsda);
        logd(sb.toString());
        try {
            return this.mDsda.getActiveSubscription();
        } catch (RemoteException ex) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("getActiveSubscription:");
            sb2.append(ex);
            logd(sb2.toString());
            return -1;
        }
    }

    public boolean isFdnEnabled() {
        IccCard card = PhoneFactory.getDefaultPhone().getIccCard();
        if (card != null) {
            return card.getIccFdnEnabled();
        }
        return false;
    }

    public int getUiccApplicationCount(int slotId) {
        UiccCard card = UiccController.getInstance().getUiccCard(slotId);
        if (card != null) {
            return card.getNumApplications();
        }
        return 0;
    }

    public void supplyIccDepersonalization(String netpin, String type, IDepersoResCallback callback, int phoneId) {
        logd("supplyIccDepersonalization");
        QtiDepersoSupplier.getInstance().supplyIccDepersonalization(netpin, type, callback, phoneId);
    }

    public int getUiccApplicationType(int slotId, int appIndex) {
        UiccCard card = UiccController.getInstance().getUiccCard(slotId);
        if (card != null) {
            return card.getApplicationIndex(appIndex).getType().ordinal();
        }
        return 0;
    }

    public int getUiccApplicationState(int slotId, int appIndex) {
        UiccCard card = UiccController.getInstance().getUiccCard(slotId);
        if (card != null) {
            return card.getApplicationIndex(appIndex).getState().ordinal();
        }
        return 0;
    }

    public void setPrimaryCardOnSlot(int slotId) {
        QtiPrimaryCardController.getInstance().setPrimaryCardOnSlot(slotId);
    }

    public int getCurrentPrimaryCardSlotId() {
        return QtiPrimaryCardUtils.getCurrentPrimarySlotFromDB(mContext);
    }

    public boolean isEmergencyNumber(String number) {
        return QtiEmergencyCallHelper.isEmergencyNumber(number);
    }

    public boolean isLocalEmergencyNumber(String number) {
        return QtiEmergencyCallHelper.isLocalEmergencyNumber(mContext, number);
    }

    public boolean isPotentialEmergencyNumber(String number) {
        return QtiEmergencyCallHelper.isPotentialEmergencyNumber(number);
    }

    public boolean isPotentialLocalEmergencyNumber(String number) {
        return QtiEmergencyCallHelper.isPotentialLocalEmergencyNumber(mContext, number);
    }

    public boolean isDeviceInSingleStandby() {
        return QtiEmergencyCallHelper.isDeviceInSingleStandby();
    }

    public boolean setLocalCallHold(int subscriptionId, boolean enable) {
        int phoneId = SubscriptionManager.getPhoneId(subscriptionId);
        Phone phone = PhoneFactory.getPhone(phoneId);
        StringBuilder sb = new StringBuilder();
        sb.append("setLocalCallHold:");
        sb.append(phoneId);
        sb.append(" enable:");
        sb.append(enable);
        logd(sb.toString());
        return ((QtiGsmCdmaPhone) phone).setLocalCallHold(enable);
    }

    public boolean isDsdaEnabled() {
        if (TelephonyManager.getDefault().getMultiSimConfiguration() == MultiSimVariants.DSDA) {
            return DBG;
        }
        return false;
    }

    public int getPrimaryCarrierSlotId() {
        int slotId = -1;
        List<SubscriptionInfo> subInfoList = SubscriptionManager.from(mContext).getActiveSubscriptionInfoList();
        int matchingCount = 0;
        if (subInfoList == null || subInfoList.size() < 1) {
            loge("No active subscriptions found!!");
            return -1;
        }
        for (SubscriptionInfo subInfo : subInfoList) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(subInfo.getMcc()));
            sb.append(String.valueOf(subInfo.getMnc()));
            String mccMnc = sb.toString();
            int provisionStatus = getCurrentUiccCardProvisioningStatus(subInfo.getSimSlotIndex());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("provisionStatus : ");
            sb2.append(provisionStatus);
            sb2.append(" slotId ");
            sb2.append(subInfo.getSimSlotIndex());
            logd(sb2.toString());
            if (provisionStatus == 1 && isPrimaryCarrierMccMnc(mccMnc)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Found a matching combination, slotId  ");
                sb3.append(subInfo.getSimSlotIndex());
                logd(sb3.toString());
                slotId = subInfo.getSimSlotIndex();
                matchingCount++;
            }
        }
        if (matchingCount > 1) {
            logd("Found multiple matches, returning primary slotid");
            slotId = Global.getInt(mContext.getContentResolver(), CONFIG_CURRENT_PRIMARY_SUB, slotId);
        }
        return slotId;
    }

    private boolean isPrimaryCarrierMccMnc(String mccMnc) {
        String str = mccMnc;
        for (String mccmnc : new String[]{"405840", "405854", "405855", "405856", "405857", "405858", "405859", "405860", "405861", "405862", "405863", "405864", "405865", "405866", "405867", "405868", "405869", "405870", "405871", "405872", "405873", "405874", "22201", "2221"}) {
            if (str.equals(mccmnc)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Found a matching combination  ");
                sb.append(str);
                logd(sb.toString());
                return DBG;
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Not found a matching combination  ");
        sb2.append(str);
        logd(sb2.toString());
        return false;
    }

    public boolean isPrimaryCarrierSlotId(int slotId) {
        SubscriptionInfo subInfo = SubscriptionManager.from(mContext).getActiveSubscriptionInfoForSimSlotIndex(slotId);
        if (subInfo == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("No active subscription found on slot ");
            sb.append(slotId);
            loge(sb.toString());
            return false;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(String.valueOf(subInfo.getMcc()));
        sb2.append(String.valueOf(subInfo.getMnc()));
        if (!isPrimaryCarrierMccMnc(sb2.toString())) {
            return false;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Found a matching combination, slotId  ");
        sb3.append(subInfo.getSimSlotIndex());
        logd(sb3.toString());
        return DBG;
    }

    public boolean setSmscAddress(int slotId, String smsc) {
        return this.mQtiSmscHelper.setSmscAddress(slotId, smsc);
    }

    public String getSmscAddress(int slotId) {
        return this.mQtiSmscHelper.getSmscAddress(slotId);
    }

    public boolean isVendorApkAvailable(String packageName) {
        try {
            mContext.getPackageManager().getPackageInfo(packageName, 0);
            return DBG;
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Vendor apk not available for ");
            sb.append(packageName);
            logd(sb.toString());
            return false;
        }
    }

    public Token enable5g(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().enable5g(slotId, client);
    }

    public Token disable5g(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().disable5g(slotId, client);
    }

    public Token enable5gOnly(int slotId, Client client) throws RemoteException {
        throw new RemoteException("not implemented");
    }

    public Token query5gStatus(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().query5gStatus(slotId, client);
    }

    public Token queryNrDcParam(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().queryNrDcParam(slotId, client);
    }

    public Token queryNrBearerAllocation(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().queryNrBearerAllocation(slotId, client);
    }

    public Token queryUpperLayerIndInfo(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().queryUpperLayerIndInfo(slotId, client);
    }

    public Token queryNrSignalStrength(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().queryNrSignalStrength(slotId, client);
    }

    public Token query5gConfigInfo(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().query5gConfigInfo(slotId, client);
    }

    public Token queryNrIconType(int slotId, Client client) throws RemoteException {
        return MainServiceImpl.getInstance().queryNrIconType(slotId, client);
    }

    public Client registerCallback(String packageName, INetworkCallback callback) throws RemoteException {
        return MainServiceImpl.getInstance().registerCallback(packageName, callback);
    }

    public void unRegisterCallback(INetworkCallback callback) throws RemoteException {
        MainServiceImpl.getInstance().unRegisterCallback(callback);
    }

    private void logd(String string) {
        Rlog.d(LOG_TAG, string);
    }

    private void loge(String string) {
        Rlog.e(LOG_TAG, string);
    }

    /* access modifiers changed from: protected */
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        if (mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Permission Denial: can't dump ExtPhone from pid=");
            sb.append(Binder.getCallingPid());
            sb.append(", uid=");
            sb.append(Binder.getCallingUid());
            sb.append("without permission ");
            sb.append("android.permission.DUMP");
            writer.println(sb.toString());
            writer.flush();
            return;
        }
        MainServiceImpl.getInstance().dump(fd, writer, args);
    }
}
