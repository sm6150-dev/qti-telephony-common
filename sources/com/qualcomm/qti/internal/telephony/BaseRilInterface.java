package com.qualcomm.qti.internal.telephony;

import android.os.Handler;

public interface BaseRilInterface {
    boolean isServiceReady();

    void registerForServiceReadyEvent(Handler handler, int i, Object obj);

    void sendPhoneStatus(int i, int i2);

    boolean setLocalCallHold(int i, boolean z);

    void unRegisterForServiceReadyEvent(Handler handler);
}
