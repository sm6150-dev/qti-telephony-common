package com.qualcomm.qti.internal.telephony;

import android.content.Context;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;

public class QtiEmergencyCallHelper {
    private static final String ALLOW_ECALL_ENHANCEMENT_PROPERTY = "persist.vendor.radio.enhance_ecall";
    private static final int INVALID = -1;
    private static final String LOG_TAG = "QtiEmergencyCallHelper";
    private static final int PRIMARY_STACK_MODEMID = 0;
    private static final int PROVISIONED = 1;
    private static QtiEmergencyCallHelper sInstance = null;

    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x006d: MOVE  (r2v13 'phoneId' int) = (r5v2 'phId' int)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    public static int getPhoneIdForECall(android.content.Context r12) {
        /*
            com.qualcomm.qti.internal.telephony.QtiSubscriptionController r0 = com.qualcomm.qti.internal.telephony.QtiSubscriptionController.getInstance()
            int r1 = r0.getDefaultVoiceSubId()
            int r1 = r0.getPhoneId(r1)
            r2 = -1
            java.lang.String r3 = "phone"
            java.lang.Object r3 = r12.getSystemService(r3)
            android.telephony.TelephonyManager r3 = (android.telephony.TelephonyManager) r3
            int r4 = r3.getPhoneCount()
            boolean r5 = isDeviceInSingleStandby(r12)
            java.lang.String r6 = "QtiEmergencyCallHelper"
            if (r5 != 0) goto L_0x005c
            android.telephony.TelephonyManager$MultiSimVariants r5 = r3.getMultiSimConfiguration()
            android.telephony.TelephonyManager$MultiSimVariants r7 = android.telephony.TelephonyManager.MultiSimVariants.DSDA
            if (r5 == r7) goto L_0x005c
            com.android.internal.telephony.Phone[] r5 = com.android.internal.telephony.PhoneFactory.getPhones()
            int r7 = r5.length
            r8 = 0
        L_0x0030:
            if (r8 >= r7) goto L_0x005c
            r9 = r5[r8]
            com.android.internal.telephony.PhoneConstants$State r10 = r9.getState()
            com.android.internal.telephony.PhoneConstants$State r11 = com.android.internal.telephony.PhoneConstants.State.OFFHOOK
            if (r10 != r11) goto L_0x0059
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "Call already active on phoneId: "
            r5.append(r7)
            int r7 = r9.getPhoneId()
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r6, r5)
            int r5 = r9.getPhoneId()
            return r5
        L_0x0059:
            int r8 = r8 + 1
            goto L_0x0030
        L_0x005c:
            r5 = 0
        L_0x005d:
            if (r5 >= r4) goto L_0x0074
            com.android.internal.telephony.Phone r7 = com.android.internal.telephony.PhoneFactory.getPhone(r5)
            android.telephony.ServiceState r8 = r7.getServiceState()
            int r8 = r8.getState()
            if (r8 != 0) goto L_0x0071
            r2 = r5
            if (r2 != r1) goto L_0x0071
            goto L_0x0074
        L_0x0071:
            int r5 = r5 + 1
            goto L_0x005d
        L_0x0074:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "Voice phoneId in service = "
            r5.append(r7)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r6, r5)
            r5 = -1
            if (r2 != r5) goto L_0x00ab
            r8 = 0
        L_0x008c:
            if (r8 >= r4) goto L_0x00ab
            com.android.internal.telephony.Phone r9 = com.android.internal.telephony.PhoneFactory.getPhone(r8)
            android.telephony.ServiceState r10 = r9.getServiceState()
            int r10 = r10.getState()
            android.telephony.ServiceState r11 = r9.getServiceState()
            boolean r11 = r11.isEmergencyOnly()
            if (r11 == 0) goto L_0x00a8
            r2 = r8
            if (r2 != r1) goto L_0x00a8
            goto L_0x00ab
        L_0x00a8:
            int r8 = r8 + 1
            goto L_0x008c
        L_0x00ab:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Voice phoneId in Limited service = "
            r8.append(r9)
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r6, r8)
            if (r2 != r5) goto L_0x00e1
            int r2 = getPrimaryStackPhoneId(r12)
            r5 = 0
        L_0x00c6:
            if (r5 >= r4) goto L_0x00e1
            com.qualcomm.qti.internal.telephony.QtiUiccCardProvisioner r8 = com.qualcomm.qti.internal.telephony.QtiUiccCardProvisioner.getInstance()
            int r9 = r3.getSimState(r5)
            r10 = 5
            if (r9 != r10) goto L_0x00de
            int r9 = r8.getCurrentUiccCardProvisioningStatus(r5)
            r10 = 1
            if (r9 != r10) goto L_0x00de
            r2 = r5
            if (r2 != r1) goto L_0x00de
            goto L_0x00e1
        L_0x00de:
            int r5 = r5 + 1
            goto L_0x00c6
        L_0x00e1:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r7)
            r5.append(r2)
            java.lang.String r7 = " preferred phoneId ="
            r5.append(r7)
            r5.append(r1)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r6, r5)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qualcomm.qti.internal.telephony.QtiEmergencyCallHelper.getPhoneIdForECall(android.content.Context):int");
    }

    public static int getPrimaryStackPhoneId(Context context) {
        int primayStackPhoneId = -1;
        int phoneCount = ((TelephonyManager) context.getSystemService("phone")).getPhoneCount();
        int i = 0;
        while (true) {
            if (i >= phoneCount) {
                break;
            }
            Phone phone = PhoneFactory.getPhone(i);
            if (phone != null) {
                Log.d(LOG_TAG, "Logical Modem id: " + phone.getModemUuId() + " phoneId: " + i);
                String modemUuId = phone.getModemUuId();
                if (modemUuId != null && modemUuId.length() > 0 && !modemUuId.isEmpty() && Integer.parseInt(modemUuId) == 0) {
                    primayStackPhoneId = i;
                    Log.d(LOG_TAG, "Primay Stack phone id: " + primayStackPhoneId + " selected");
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

    public static boolean isDeviceInSingleStandby(Context context) {
        if (!SystemProperties.getBoolean(ALLOW_ECALL_ENHANCEMENT_PROPERTY, true)) {
            Log.d(LOG_TAG, "persist.vendor.radio.enhance_ecall not enabled");
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
                Log.d(LOG_TAG, "modem is in single standby mode");
                return true;
            }
        }
        Log.d(LOG_TAG, "modem is in dual standby mode");
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
