package com.qualcomm.qti.internal.telephony.primarycard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.internal.annotations.VisibleForTesting;
import com.qualcomm.qti.internal.telephony.QtiUiccCardProvisioner;
import java.util.HashSet;
import java.util.Set;
import org.codeaurora.internal.IExtTelephony;

public class SubsidyLockSettingsObserver extends ContentObserver {
    public static final int AP_LOCKED = 102;
    public static final int PERMANENTLY_UNLOCKED = 100;
    public static final int SUBSIDY_LOCKED = 101;
    private static final String SUBSIDY_LOCK_SETTINGS = "subsidy_status";
    private static final String SUBSIDY_LOCK_SYSTEM_PROPERY = "ro.vendor.radio.subsidylock";
    private static final String SUBSIDY_STATE_KEY = "device_Subsidy_state";
    public static final int SUBSIDY_UNLOCKED = 103;
    private static final String TAG = "SubsidyLockSettingsObserver";
    /* access modifiers changed from: private */
    public static int mCurrentState = -1;
    private QtiCardInfoManager mCardInfoManager;
    /* access modifiers changed from: private */
    public Context mContext;
    private QtiPrimaryCardPriorityHandler mPriorityHandler;
    private SubsidySettingsHandler mSettingsHandler;

    public SubsidyLockSettingsObserver(Context context) {
        super((Handler) null);
        this.mContext = context;
        mCurrentState = PreferenceManager.getDefaultSharedPreferences(this.mContext).getInt(SUBSIDY_STATE_KEY, mCurrentState);
        Rlog.d(TAG, " in constructor, context =  " + this.mContext + " device state " + mCurrentState);
    }

    public void observe(QtiCardInfoManager cardInfoManager, QtiPrimaryCardPriorityHandler cardPriorityHandler) {
        ContentResolver resolver = this.mContext.getContentResolver();
        this.mCardInfoManager = cardInfoManager;
        this.mPriorityHandler = cardPriorityHandler;
        resolver.registerContentObserver(Settings.Secure.getUriFor(SUBSIDY_LOCK_SETTINGS), false, this);
        registerAllCardsAvailableCallback();
    }

    private void registerAllCardsAvailableCallback() {
        if (this.mSettingsHandler == null) {
            this.mSettingsHandler = new SubsidySettingsHandler(Looper.getMainLooper());
            this.mCardInfoManager.registerAllCardsInfoAvailable(this.mSettingsHandler, 4, (Object) null);
        }
    }

    public void onChange(boolean selfChange) {
        QtiPrimaryCardPriorityHandler qtiPrimaryCardPriorityHandler = this.mPriorityHandler;
        if (qtiPrimaryCardPriorityHandler != null) {
            qtiPrimaryCardPriorityHandler.reloadPriorityConfig();
            this.mPriorityHandler.loadCurrentPriorityConfigs(true);
        }
        HandlerThread thread = new HandlerThread("Subsidy Settings handler thread");
        thread.start();
        Handler handler = new SubsidySettingsHandler(thread.getLooper());
        if (isSubsidyLocked(this.mContext)) {
            handler.obtainMessage(0).sendToTarget();
        } else if (isPermanentlyUnlocked(this.mContext)) {
            handler.obtainMessage(2).sendToTarget();
        } else if (isSubsidyUnlocked(this.mContext)) {
            handler.obtainMessage(1, Integer.valueOf(SUBSIDY_UNLOCKED)).sendToTarget();
        }
    }

    public static boolean isSubsidyLocked(Context context) {
        int subsidyLock = Settings.Secure.getInt(context.getContentResolver(), SUBSIDY_LOCK_SETTINGS, -1);
        return subsidyLock == 101 || subsidyLock == 102;
    }

    public static boolean isSubsidyUnlocked(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), SUBSIDY_LOCK_SETTINGS, -1) == 103;
    }

    public static boolean isPermanentlyUnlocked(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), SUBSIDY_LOCK_SETTINGS, -1) == 100;
    }

    private class SIMDeactivationRecords {
        private static final String KEY_DEACTIVATION_RECORD = "key_deactivation_record";
        private static final String PREFS_NAME = "deactivation_record";
        private SharedPreferences mSharedPrefs;
        private Set<String> mSimRecords = null;

        public SIMDeactivationRecords() {
            this.mSharedPrefs = SubsidyLockSettingsObserver.this.mContext.getSharedPreferences(PREFS_NAME, 0);
            parse();
        }

        private void parse() {
            Set<String> simRecords = this.mSharedPrefs.getStringSet(KEY_DEACTIVATION_RECORD, new HashSet());
            this.mSimRecords = new HashSet();
            this.mSimRecords.addAll(simRecords);
        }

        public void addRecord(String subId) {
            if (!isDeactivated(subId)) {
                this.mSimRecords.add(subId);
                this.mSharedPrefs.edit().putStringSet(KEY_DEACTIVATION_RECORD, this.mSimRecords).commit();
            }
        }

        public void removeRecord(String subId) {
            if (this.mSimRecords.contains(subId)) {
                this.mSimRecords.remove(subId);
            }
            this.mSharedPrefs.edit().putStringSet(KEY_DEACTIVATION_RECORD, this.mSimRecords).commit();
        }

        public boolean isDeactivated(String subId) {
            return this.mSimRecords.contains(subId);
        }
    }

    private class SubsidySettingsHandler extends Handler {
        public static final int MSG_ALL_CARDS_READY = 4;
        public static final int MSG_EXIT = 3;
        public static final int MSG_LOCKED = 0;
        public static final int MSG_RESTRICTED = 1;
        public static final int MSG_SET_PRIMARY_CARD = 5;
        public static final int MSG_UNLOCKED = 2;
        static final int PROVISIONED = 1;
        static final int SUCCESS = 0;
        private int mNumSimSlots = 0;

        public SubsidySettingsHandler(Looper looper) {
            super(looper);
            this.mNumSimSlots = ((TelephonyManager) SubsidyLockSettingsObserver.this.mContext.getSystemService("phone")).getPhoneCount();
        }

        public void handleMessage(Message msg) {
            Rlog.d(SubsidyLockSettingsObserver.TAG, " handleMessage, event  " + msg.what + " current state " + SubsidyLockSettingsObserver.mCurrentState);
            int i = msg.what;
            if (i == 0) {
                IExtTelephony mExtTelephony = IExtTelephony.Stub.asInterface(ServiceManager.getService("extphone"));
                int i2 = 0;
                while (i2 < this.mNumSimSlots) {
                    try {
                        SubscriptionInfo sir = SubscriptionManager.from(SubsidyLockSettingsObserver.this.mContext).getActiveSubscriptionInfoForSimSlotIndex(i2);
                        if (sir != null) {
                            if (sir.getMcc() != 0) {
                                if (mExtTelephony.isPrimaryCarrierSlotId(i2)) {
                                    QtiUiccCardProvisioner.make(SubsidyLockSettingsObserver.this.mContext).activateUiccCard(i2);
                                } else if (mExtTelephony.getCurrentUiccCardProvisioningStatus(i2) == 1 && QtiUiccCardProvisioner.make(SubsidyLockSettingsObserver.this.mContext).deactivateUiccCard(i2) == 0) {
                                    new SIMDeactivationRecords().addRecord(String.valueOf(sir.getSubscriptionId()));
                                }
                                i2++;
                            }
                        }
                        Rlog.e(SubsidyLockSettingsObserver.TAG, "Invalid sub info for slot id: " + i2 + ", not proceeding further.");
                        i2++;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                    }
                }
                SubsidyLockSettingsObserver.this.updateDeviceState(102);
                obtainMessage(3).sendToTarget();
            } else if (i == 1) {
                IExtTelephony mExtTelephony1 = IExtTelephony.Stub.asInterface(ServiceManager.getService("extphone"));
                int i3 = 0;
                while (i3 < this.mNumSimSlots) {
                    try {
                        SubscriptionInfo sir2 = SubscriptionManager.from(SubsidyLockSettingsObserver.this.mContext).getActiveSubscriptionInfoForSimSlotIndex(i3);
                        if (sir2 != null) {
                            if (sir2.getMcc() != 0) {
                                if (!mExtTelephony1.isPrimaryCarrierSlotId(i3)) {
                                    SIMDeactivationRecords records = new SIMDeactivationRecords();
                                    if (records.isDeactivated(String.valueOf(sir2.getSubscriptionId())) && mExtTelephony1.activateUiccCard(i3) == 0) {
                                        records.removeRecord(String.valueOf(sir2.getSubscriptionId()));
                                    }
                                } else if (SubsidyLockSettingsObserver.isSubsidyUnlocked(SubsidyLockSettingsObserver.this.mContext)) {
                                    if (mExtTelephony1.isPrimaryCarrierSlotId(i3)) {
                                        QtiUiccCardProvisioner.make(SubsidyLockSettingsObserver.this.mContext).activateUiccCard(i3);
                                    }
                                    i3++;
                                }
                                i3++;
                            }
                        }
                        Rlog.e(SubsidyLockSettingsObserver.TAG, "Invalid subscription info for slot id: " + i3 + ", not proceeding further.");
                        i3++;
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    } catch (NullPointerException e4) {
                        e4.printStackTrace();
                    }
                }
                if (msg.obj != null) {
                    SubsidyLockSettingsObserver.this.updateDeviceState(((Integer) msg.obj).intValue());
                }
                obtainMessage(3).sendToTarget();
            } else if (i == 2) {
                obtainMessage(1, 100).sendToTarget();
            } else if (i != 3) {
                if (i == 4) {
                    SubsidyLockSettingsObserver.this.onChange(false);
                } else if (i == 5) {
                    QtiPrimaryCardController.getInstance().trySetPrimarySub();
                }
            } else if (getLooper() != Looper.getMainLooper()) {
                getLooper().quitSafely();
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateDeviceState(int newState) {
        QtiPrimaryCardPriorityHandler qtiPrimaryCardPriorityHandler;
        if (mCurrentState != newState) {
            Rlog.d(TAG, " updateDeviceState, new state  " + newState);
            if ((newState == 100 || mCurrentState == 100) && (qtiPrimaryCardPriorityHandler = this.mPriorityHandler) != null) {
                qtiPrimaryCardPriorityHandler.reloadPriorityConfig();
                this.mPriorityHandler.loadCurrentPriorityConfigs(true);
            }
            if (mCurrentState == 100 && (newState == 102 || newState == 103)) {
                this.mSettingsHandler.obtainMessage(5).sendToTarget();
            } else if (newState == 100) {
                QtiPrimaryCardController.getInstance().saveUserSelectionMode();
            }
            saveDeviceState(newState);
        }
    }

    private void saveDeviceState(int newState) {
        mCurrentState = newState;
        PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putInt(SUBSIDY_STATE_KEY, newState).commit();
    }

    public static boolean isSubsidyLockFeatureEnabled() {
        if (SystemProperties.getInt(SUBSIDY_LOCK_SYSTEM_PROPERY, 0) == 1) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public Handler getHandler() {
        return this.mSettingsHandler;
    }
}
