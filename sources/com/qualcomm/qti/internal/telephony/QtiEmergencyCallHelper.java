package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyManager.MultiSimVariants;
import android.util.Log;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants.State;
import com.android.internal.telephony.PhoneFactory;

public class QtiEmergencyCallHelper {
    private static final String ALLOW_ECALL_ENHANCEMENT_PROPERTY = "persist.vendor.radio.enhance_ecall";
    private static final int INVALID = -1;
    private static final String LOG_TAG = "QtiEmergencyCallHelper";
    private static final int PRIMARY_STACK_MODEMID = 0;
    private static final int PROVISIONED = 1;
    private static QtiEmergencyCallHelper sInstance = null;

    public static int getPhoneIdForECall() {
        int phoneId;
        Phone[] phones;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        int voicePhoneId = scontrol.getPhoneId(scontrol.getDefaultVoiceSubId());
        TelephonyManager tm = TelephonyManager.getDefault();
        int phoneCount = tm.getPhoneCount();
        int phId = 0;
        if (!isDeviceInSingleStandby() && tm.getMultiSimConfiguration() != MultiSimVariants.DSDA) {
            for (Phone phone : PhoneFactory.getPhones()) {
                if (phone.getState() == State.OFFHOOK) {
                    String str = LOG_TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Call already active on phoneId: ");
                    sb.append(phone.getPhoneId());
                    Log.d(str, sb.toString());
                    return phone.getPhoneId();
                }
            }
        }
        int phoneId2 = -1;
        for (int phId2 = 0; phId2 < phoneCount; phId2++) {
            if (PhoneFactory.getPhone(phId2).getServiceState().getState() == 0) {
                phoneId2 = phId2;
                if (phoneId2 == voicePhoneId) {
                    break;
                }
            }
        }
        String str2 = LOG_TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Voice phoneId in service = ");
        sb2.append(phoneId2);
        Log.d(str2, sb2.toString());
        if (phoneId2 == -1) {
            phoneId = phoneId2;
            for (int phId3 = 0; phId3 < phoneCount; phId3++) {
                Phone phone2 = PhoneFactory.getPhone(phId3);
                int state = phone2.getServiceState().getState();
                if (phone2.getServiceState().isEmergencyOnly()) {
                    phoneId = phId3;
                    if (phoneId == voicePhoneId) {
                        break;
                    }
                }
            }
        } else {
            phoneId = phoneId2;
        }
        String str3 = LOG_TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Voice phoneId in Limited service = ");
        sb3.append(phoneId);
        Log.d(str3, sb3.toString());
        if (phoneId == -1) {
            phoneId = getPrimaryStackPhoneId();
            while (true) {
                int phId4 = phId;
                if (phId4 >= phoneCount) {
                    break;
                }
                QtiUiccCardProvisioner uiccProvisioner = QtiUiccCardProvisioner.getInstance();
                if (tm.getSimState(phId4) == 5 && uiccProvisioner.getCurrentUiccCardProvisioningStatus(phId4) == 1) {
                    phoneId = phId4;
                    if (phoneId == voicePhoneId) {
                        break;
                    }
                }
                phId = phId4 + 1;
            }
        }
        String str4 = LOG_TAG;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Voice phoneId in service = ");
        sb4.append(phoneId);
        sb4.append(" preferred phoneId =");
        sb4.append(voicePhoneId);
        Log.d(str4, sb4.toString());
        return phoneId;
    }

    public static int getPrimaryStackPhoneId() {
        int primayStackPhoneId = -1;
        int i = 0;
        while (true) {
            if (i >= TelephonyManager.getDefault().getPhoneCount()) {
                break;
            }
            Phone phone = PhoneFactory.getPhone(i);
            if (phone != null) {
                String str = LOG_TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Logical Modem id: ");
                sb.append(phone.getModemUuId());
                sb.append(" phoneId: ");
                sb.append(i);
                Log.d(str, sb.toString());
                String modemUuId = phone.getModemUuId();
                if (modemUuId != null && modemUuId.length() > 0 && !modemUuId.isEmpty() && Integer.parseInt(modemUuId) == 0) {
                    primayStackPhoneId = i;
                    String str2 = LOG_TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Primay Stack phone id: ");
                    sb2.append(primayStackPhoneId);
                    sb2.append(" selected");
                    Log.d(str2, sb2.toString());
                    break;
                }
            }
            i++;
        }
        if (primayStackPhoneId != -1) {
            return primayStackPhoneId;
        }
        Log.d(LOG_TAG, "Returning default phone id");
        return 0;
    }

    public static boolean isDeviceInSingleStandby() {
        if (!SystemProperties.getBoolean(ALLOW_ECALL_ENHANCEMENT_PROPERTY, true)) {
            Log.d(LOG_TAG, "persist.vendor.radio.enhance_ecall not enabled");
            return false;
        }
        TelephonyManager tm = TelephonyManager.getDefault();
        int phoneCnt = tm.getPhoneCount();
        if (phoneCnt == 1) {
            return true;
        }
        int phoneId = 0;
        while (phoneId < phoneCnt) {
            QtiUiccCardProvisioner uiccProvisioner = QtiUiccCardProvisioner.getInstance();
            if (tm.getSimState(phoneId) == 5 && uiccProvisioner.getCurrentUiccCardProvisioningStatus(phoneId) == 1) {
                phoneId++;
            } else {
                Log.d(LOG_TAG, "modem is in single standby mode");
                return true;
            }
        }
        Log.d(LOG_TAG, "modem is in dual standby mode");
        return false;
    }

    public static boolean isEmergencyNumber(String number) {
        boolean isEmergencyNum = false;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        Phone[] phones = PhoneFactory.getPhones();
        if (!isDeviceInSingleStandby()) {
            return PhoneNumberUtils.isEmergencyNumber(scontrol.getDefaultVoiceSubId(), number);
        }
        for (Phone phone : phones) {
            isEmergencyNum |= PhoneNumberUtils.isEmergencyNumber(phone.getSubId(), number);
        }
        return isEmergencyNum;
    }

    public static boolean isLocalEmergencyNumber(Context context, String number) {
        boolean isLocalEmergencyNum = false;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        Phone[] phones = PhoneFactory.getPhones();
        if (!isDeviceInSingleStandby()) {
            return PhoneNumberUtils.isLocalEmergencyNumber(context, scontrol.getDefaultVoiceSubId(), number);
        }
        for (Phone phone : phones) {
            isLocalEmergencyNum |= PhoneNumberUtils.isLocalEmergencyNumber(context, phone.getSubId(), number);
        }
        return isLocalEmergencyNum;
    }

    public static boolean isPotentialEmergencyNumber(String number) {
        boolean isPotentialEmergencyNum = false;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        Phone[] phones = PhoneFactory.getPhones();
        if (!isDeviceInSingleStandby()) {
            return PhoneNumberUtils.isPotentialEmergencyNumber(scontrol.getDefaultVoiceSubId(), number);
        }
        for (Phone phone : phones) {
            isPotentialEmergencyNum |= PhoneNumberUtils.isPotentialEmergencyNumber(phone.getSubId(), number);
        }
        return isPotentialEmergencyNum;
    }

    public static boolean isPotentialLocalEmergencyNumber(Context context, String number) {
        boolean isPotentialLocalEmergencyNum = false;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        Phone[] phones = PhoneFactory.getPhones();
        if (!isDeviceInSingleStandby()) {
            return PhoneNumberUtils.isPotentialLocalEmergencyNumber(context, scontrol.getDefaultVoiceSubId(), number);
        }
        for (Phone phone : phones) {
            isPotentialLocalEmergencyNum |= PhoneNumberUtils.isPotentialLocalEmergencyNumber(context, phone.getSubId(), number);
        }
        return isPotentialLocalEmergencyNum;
    }

    public static boolean isEmergencyNumInternal(int subId, String number) {
        return PhoneNumberUtils.isEmergencyNumber(subId, number);
    }
}
