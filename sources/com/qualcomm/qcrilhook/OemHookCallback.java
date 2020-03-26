package com.qualcomm.qcrilhook;

import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.qualcomm.qcrilhook.IOemHookCallback;
import com.qualcomm.qcrilhook.QmiOemHookConstants;

public class OemHookCallback extends IOemHookCallback.Stub {
    Message mAppMessage;

    public OemHookCallback(Message msg) {
        this.mAppMessage = msg;
    }

    public void onOemHookException(int phoneId) throws RemoteException {
        Log.w("onOemHookException", "mPhoneId: " + phoneId);
    }

    public void onOemHookResponse(byte[] response, int phoneId) throws RemoteException {
        Log.w("OemHookCallback", "mPhoneId: " + phoneId);
        QmiOemHook.receive(response, this.mAppMessage, QmiOemHookConstants.ResponseType.IS_ASYNC_RESPONSE, phoneId);
    }
}
