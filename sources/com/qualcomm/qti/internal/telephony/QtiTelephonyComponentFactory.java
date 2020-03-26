package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.InboundSmsTracker;
import com.android.internal.telephony.MultiSimSettingController;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneNotifier;
import com.android.internal.telephony.PhoneSwitcher;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.SubscriptionInfoUpdater;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.WspTypeDecoder;
import com.android.internal.telephony.cdma.CdmaSubscriptionSourceManager;
import com.android.internal.telephony.cdma.EriManager;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.qualcomm.qti.internal.nrNetworkService.MainServiceImpl;
import com.qualcomm.qti.internal.telephony.dataconnection.QtiDcTracker;
import com.qualcomm.qti.internal.telephony.primarycard.QtiPrimaryCardController;

public class QtiTelephonyComponentFactory extends TelephonyComponentFactory {
    private static String LOG_TAG = "QtiTelephonyComponentFactory";
    private static QtiTelephonyComponentFactory sInstance;
    private QtiRIL[] mRil = {null, null};

    public QtiTelephonyComponentFactory() {
        sInstance = this;
    }

    public static QtiTelephonyComponentFactory getInstance() {
        return sInstance;
    }

    public GsmCdmaCallTracker makeGsmCdmaCallTracker(GsmCdmaPhone phone) {
        Rlog.d(LOG_TAG, "makeGsmCdmaCallTracker");
        return QtiTelephonyComponentFactory.super.makeGsmCdmaCallTracker(phone);
    }

    public SmsStorageMonitor makeSmsStorageMonitor(Phone phone) {
        Rlog.d(LOG_TAG, "makeSmsStorageMonitor");
        return QtiTelephonyComponentFactory.super.makeSmsStorageMonitor(phone);
    }

    public SmsUsageMonitor makeSmsUsageMonitor(Context context) {
        Rlog.d(LOG_TAG, "makeSmsUsageMonitor");
        return QtiTelephonyComponentFactory.super.makeSmsUsageMonitor(context);
    }

    public ServiceStateTracker makeServiceStateTracker(GsmCdmaPhone phone, CommandsInterface ci) {
        Rlog.d(LOG_TAG, "makeQtiServiceStateTracker");
        return new QtiServiceStateTracker(phone, ci);
    }

    public DcTracker makeDcTracker(Phone phone, int transportType) {
        Rlog.d(LOG_TAG, "makeQtiDcTracker");
        return new QtiDcTracker(phone, transportType);
    }

    public IccPhoneBookInterfaceManager makeIccPhoneBookInterfaceManager(Phone phone) {
        Rlog.d(LOG_TAG, "makeQtiIccPhoneBookInterfaceManager");
        return new QtiIccPhoneBookInterfaceManager(phone);
    }

    public IccSmsInterfaceManager makeIccSmsInterfaceManager(Phone phone) {
        Rlog.d(LOG_TAG, "makeIccSmsInterfaceManager");
        return QtiTelephonyComponentFactory.super.makeIccSmsInterfaceManager(phone);
    }

    public EriManager makeEriManager(Phone phone, int eriFileSource) {
        Rlog.d(LOG_TAG, "makeEriManager");
        return QtiTelephonyComponentFactory.super.makeEriManager(phone, eriFileSource);
    }

    public WspTypeDecoder makeWspTypeDecoder(byte[] pdu) {
        Rlog.d(LOG_TAG, "makeWspTypeDecoder");
        return QtiTelephonyComponentFactory.super.makeWspTypeDecoder(pdu);
    }

    public InboundSmsTracker makeInboundSmsTracker(byte[] pdu, long timestamp, int destPort, boolean is3gpp2, boolean is3gpp2WapPdu, String address, String displayAddr, String msgBody, boolean isClass0) {
        Rlog.d(LOG_TAG, "makeInboundSmsTracker");
        return QtiTelephonyComponentFactory.super.makeInboundSmsTracker(pdu, timestamp, destPort, is3gpp2, is3gpp2WapPdu, address, displayAddr, msgBody, isClass0);
    }

    public InboundSmsTracker makeInboundSmsTracker(byte[] pdu, long timestamp, int destPort, boolean is3gpp2, String address, String displayAddr, int referenceNumber, int sequenceNumber, int messageCount, boolean is3gpp2WapPdu, String msgBody, boolean isClass0) {
        Rlog.d(LOG_TAG, "makeInboundSmsTracker");
        return QtiTelephonyComponentFactory.super.makeInboundSmsTracker(pdu, timestamp, destPort, is3gpp2, address, displayAddr, referenceNumber, sequenceNumber, messageCount, is3gpp2WapPdu, msgBody, isClass0);
    }

    public ImsPhoneCallTracker makeImsPhoneCallTracker(ImsPhone imsPhone) {
        Rlog.d(LOG_TAG, "makeImsPhoneCallTracker");
        return QtiTelephonyComponentFactory.super.makeImsPhoneCallTracker(imsPhone);
    }

    public CdmaSubscriptionSourceManager getCdmaSubscriptionSourceManagerInstance(Context context, CommandsInterface ci, Handler h, int what, Object obj) {
        Rlog.d(LOG_TAG, "getCdmaSubscriptionSourceManagerInstance");
        return QtiTelephonyComponentFactory.super.getCdmaSubscriptionSourceManagerInstance(context, ci, h, what, obj);
    }

    public IDeviceIdleController getIDeviceIdleController() {
        Rlog.d(LOG_TAG, "getIDeviceIdleController");
        return QtiTelephonyComponentFactory.super.getIDeviceIdleController();
    }

    public Phone makePhone(Context context, CommandsInterface ci, PhoneNotifier notifier, int phoneId, int precisePhoneType, TelephonyComponentFactory telephonyComponentFactory) {
        Rlog.d(LOG_TAG, "makePhone");
        return new QtiGsmCdmaPhone(context, ci, notifier, phoneId, precisePhoneType, telephonyComponentFactory);
    }

    public SubscriptionController initSubscriptionController(Context c, CommandsInterface[] ci) {
        Rlog.d(LOG_TAG, "initSubscriptionController");
        return QtiSubscriptionController.init(c, ci);
    }

    public SubscriptionInfoUpdater makeSubscriptionInfoUpdater(Looper looper, Context context, Phone[] phones, CommandsInterface[] ci) {
        Rlog.d(LOG_TAG, "makeSubscriptionInfoUpdater");
        return QtiSubscriptionInfoUpdater.init(looper, context, phones, ci);
    }

    public void makeExtTelephonyClasses(Context context, Phone[] phones, CommandsInterface[] commandsInterfaces) {
        Rlog.d(LOG_TAG, " makeExtTelephonyClasses ");
        String value = Settings.Global.getString(context.getContentResolver(), "settings_network_and_internet_v2");
        if (value == null || !value.equals("false")) {
            Settings.Global.putString(context.getContentResolver(), "settings_network_and_internet_v2", "false");
        }
        QtiPhoneUtils.init(context);
        QtiUiccCardProvisioner.make(context, commandsInterfaces);
        QtiDepersoSupplier.make(context);
        QtiRadioCapabilityController.make(context, phones, commandsInterfaces);
        QtiPrimaryCardController.init(context, phones, commandsInterfaces);
        QtiCallStateNotifier.init(phones);
        try {
            MainServiceImpl.init(context);
            ExtTelephonyServiceImpl.init(context);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            Rlog.e(LOG_TAG, "Error creating ExtTelephonyServiceImpl");
        }
    }

    public PhoneSwitcher makePhoneSwitcher(int maxActivePhones, int numPhones, Context context, SubscriptionController sc, Looper looper, ITelephonyRegistry tr, CommandsInterface[] cis, Phone[] phones) {
        Rlog.d(LOG_TAG, "makeQtiPhoneSwitcher");
        ModemSarController.make(context);
        if (WifiSarController.isNeeded()) {
            new WifiSarController(context);
        }
        return QtiPhoneSwitcher.make(maxActivePhones, numPhones, context, sc, looper, tr, cis, phones);
    }

    public MultiSimSettingController initMultiSimSettingController(Context c, SubscriptionController sc) {
        Rlog.i(LOG_TAG, "initMultiSimSettingController");
        return QtiMultiSimSettingController.init(c, sc);
    }

    public RIL makeRIL(Context context, int preferredNetworkType, int cdmaSubscription, Integer instanceId) {
        Rlog.d(LOG_TAG, "makeQtiRIL");
        int intValue = instanceId.intValue();
        QtiRIL[] qtiRILArr = this.mRil;
        if (intValue < qtiRILArr.length) {
            qtiRILArr[instanceId.intValue()] = new QtiRIL(context, preferredNetworkType, cdmaSubscription, instanceId);
            return this.mRil[instanceId.intValue()];
        }
        throw new RuntimeException("RilInstance = " + instanceId + " not allowed!");
    }

    public QtiRIL getRil(int slotId) {
        QtiRIL[] qtiRILArr = this.mRil;
        if (slotId < qtiRILArr.length) {
            return qtiRILArr[slotId];
        }
        return null;
    }
}
