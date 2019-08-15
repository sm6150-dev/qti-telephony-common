package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.InboundSmsTracker;
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

    public DcTracker makeDcTracker(Phone phone) {
        Rlog.d(LOG_TAG, "makeQtiDcTracker");
        return new QtiDcTracker(phone, 1);
    }

    public IccPhoneBookInterfaceManager makeIccPhoneBookInterfaceManager(Phone phone) {
        Rlog.d(LOG_TAG, "makeQtiIccPhoneBookInterfaceManager");
        return new QtiIccPhoneBookInterfaceManager(phone);
    }

    public IccSmsInterfaceManager makeIccSmsInterfaceManager(Phone phone) {
        Rlog.d(LOG_TAG, "makeIccSmsInterfaceManager");
        return QtiTelephonyComponentFactory.super.makeIccSmsInterfaceManager(phone);
    }

    public EriManager makeEriManager(Phone phone, Context context, int eriFileSource) {
        Rlog.d(LOG_TAG, "makeEriManager");
        return QtiTelephonyComponentFactory.super.makeEriManager(phone, context, eriFileSource);
    }

    public WspTypeDecoder makeWspTypeDecoder(byte[] pdu) {
        Rlog.d(LOG_TAG, "makeWspTypeDecoder");
        return QtiTelephonyComponentFactory.super.makeWspTypeDecoder(pdu);
    }

    public InboundSmsTracker makeInboundSmsTracker(byte[] pdu, long timestamp, int destPort, boolean is3gpp2, boolean is3gpp2WapPdu, String address, String displayAddr, String msgBody) {
        Rlog.d(LOG_TAG, "makeInboundSmsTracker");
        return QtiTelephonyComponentFactory.super.makeInboundSmsTracker(pdu, timestamp, destPort, is3gpp2, is3gpp2WapPdu, address, displayAddr, msgBody);
    }

    public InboundSmsTracker makeInboundSmsTracker(byte[] pdu, long timestamp, int destPort, boolean is3gpp2, String address, String displayAddr, int referenceNumber, int sequenceNumber, int messageCount, boolean is3gpp2WapPdu, String msgBody) {
        Rlog.d(LOG_TAG, "makeInboundSmsTracker");
        return QtiTelephonyComponentFactory.super.makeInboundSmsTracker(pdu, timestamp, destPort, is3gpp2, address, displayAddr, referenceNumber, sequenceNumber, messageCount, is3gpp2WapPdu, msgBody);
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
        QtiGsmCdmaPhone qtiGsmCdmaPhone = new QtiGsmCdmaPhone(context, ci, notifier, phoneId, precisePhoneType, telephonyComponentFactory);
        return qtiGsmCdmaPhone;
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
        QtiUiccCardProvisioner.make(context);
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
        Context context2;
        Rlog.d(LOG_TAG, "makeQtiPhoneSwitcher");
        ModemSarController.make(context);
        if (WifiSarController.isNeeded()) {
            context2 = context;
            new WifiSarController(context2);
        } else {
            context2 = context;
        }
        QtiPhoneSwitcher qtiPhoneSwitcher = new QtiPhoneSwitcher(maxActivePhones, numPhones, context2, sc, looper, tr, cis, phones);
        return qtiPhoneSwitcher;
    }

    public RIL makeRIL(Context context, int preferredNetworkType, int cdmaSubscription, Integer instanceId) {
        Rlog.d(LOG_TAG, "makeQtiRIL");
        if (instanceId.intValue() < this.mRil.length) {
            this.mRil[instanceId.intValue()] = new QtiRIL(context, preferredNetworkType, cdmaSubscription, instanceId);
            return this.mRil[instanceId.intValue()];
        }
        StringBuilder sb = new StringBuilder();
        sb.append("RilInstance = ");
        sb.append(instanceId);
        sb.append(" not allowed!");
        throw new RuntimeException(sb.toString());
    }

    public QtiRIL getRil(int slotId) {
        if (slotId < this.mRil.length) {
            return this.mRil[slotId];
        }
        return null;
    }
}
