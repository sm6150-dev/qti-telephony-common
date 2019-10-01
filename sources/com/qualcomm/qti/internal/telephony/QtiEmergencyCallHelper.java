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

    public static int getPhoneIdForECall(Context context) {
        Phone[] phones;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        int voicePhoneId = scontrol.getPhoneId(scontrol.getDefaultVoiceSubId());
        int phoneId = -1;
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        int phoneCount = tm.getPhoneCount();
        boolean isDeviceInSingleStandby = isDeviceInSingleStandby(context);
        String str = LOG_TAG;
        if (!isDeviceInSingleStandby && tm.getMultiSimConfiguration() != MultiSimVariants.DSDA) {
            for (Phone phone : PhoneFactory.getPhones()) {
                if (phone.getState() == State.OFFHOOK) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Call already active on phoneId: ");
                    sb.append(phone.getPhoneId());
                    Log.d(str, sb.toString());
                    return phone.getPhoneId();
                }
            }
        }
        for (int phId = 0; phId < phoneCount; phId++) {
            if (PhoneFactory.getPhone(phId).getServiceState().getState() == 0) {
                phoneId = phId;
                if (phoneId == voicePhoneId) {
                    break;
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        String str2 = "Voice phoneId in service = ";
        sb2.append(str2);
        sb2.append(phoneId);
        Log.d(str, sb2.toString());
        if (phoneId == -1) {
            for (int phId2 = 0; phId2 < phoneCount; phId2++) {
                Phone phone2 = PhoneFactory.getPhone(phId2);
                int state = phone2.getServiceState().getState();
                if (phone2.getServiceState().isEmergencyOnly()) {
                    phoneId = phId2;
                    if (phoneId == voicePhoneId) {
                        break;
                    }
                }
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Voice phoneId in Limited service = ");
        sb3.append(phoneId);
        Log.d(str, sb3.toString());
        if (phoneId == -1) {
            phoneId = getPrimaryStackPhoneId(context);
            for (int phId3 = 0; phId3 < phoneCount; phId3++) {
                QtiUiccCardProvisioner uiccProvisioner = QtiUiccCardProvisioner.getInstance();
                if (tm.getSimState(phId3) == 5 && uiccProvisioner.getCurrentUiccCardProvisioningStatus(phId3) == 1) {
                    phoneId = phId3;
                    if (phoneId == voicePhoneId) {
                        break;
                    }
                }
            }
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str2);
        sb4.append(phoneId);
        sb4.append(" preferred phoneId =");
        sb4.append(voicePhoneId);
        Log.d(str, sb4.toString());
        return phoneId;
    }

    public static int getPrimaryStackPhoneId(Context context) {
        String str;
        int primayStackPhoneId = -1;
        int phoneCount = ((TelephonyManager) context.getSystemService("phone")).getPhoneCount();
        int i = 0;
        while (true) {
            str = LOG_TAG;
            if (i >= phoneCount) {
                break;
            }
            Phone phone = PhoneFactory.getPhone(i);
            if (phone != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Logical Modem id: ");
                sb.append(phone.getModemUuId());
                sb.append(" phoneId: ");
                sb.append(i);
                Log.d(str, sb.toString());
                String modemUuId = phone.getModemUuId();
                if (modemUuId != null && modemUuId.length() > 0 && !modemUuId.isEmpty() && Integer.parseInt(modemUuId) == 0) {
                    primayStackPhoneId = i;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Primay Stack phone id: ");
                    sb2.append(primayStackPhoneId);
                    sb2.append(" selected");
                    Log.d(str, sb2.toString());
                    break;
                }
            }
            i++;
        }
        if (primayStackPhoneId != -1) {
            return primayStackPhoneId;
        }
        Log.d(str, "Returning default phone id");
        return 0;
    }

    public static boolean isDeviceInSingleStandby(Context context) {
        boolean z = SystemProperties.getBoolean(ALLOW_ECALL_ENHANCEMENT_PROPERTY, true);
        String str = LOG_TAG;
        if (!z) {
            Log.d(str, "persist.vendor.radio.enhance_ecall not enabled");
            return false;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
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
                Log.d(str, "modem is in single standby mode");
                return true;
            }
        }
        Log.d(str, "modem is in dual standby mode");
        return false;
    }

    public static boolean isEmergencyNumber(Context context, String number) {
        boolean isEmergencyNum = false;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        Phone[] phones = PhoneFactory.getPhones();
        if (!isDeviceInSingleStandby(context)) {
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
        if (!isDeviceInSingleStandby(context)) {
            return PhoneNumberUtils.isLocalEmergencyNumber(context, scontrol.getDefaultVoiceSubId(), number);
        }
        for (Phone phone : phones) {
            isLocalEmergencyNum |= PhoneNumberUtils.isLocalEmergencyNumber(context, phone.getSubId(), number);
        }
        return isLocalEmergencyNum;
    }

    public static boolean isPotentialEmergencyNumber(Context context, String number) {
        boolean isPotentialEmergencyNum = false;
        QtiSubscriptionController scontrol = QtiSubscriptionController.getInstance();
        Phone[] phones = PhoneFactory.getPhones();
        if (!isDeviceInSingleStandby(context)) {
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
        if (!isDeviceInSingleStandby(context)) {
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
