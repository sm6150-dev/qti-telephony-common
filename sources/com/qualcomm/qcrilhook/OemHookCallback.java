package com.qualcomm.qcrilhook;

import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.qualcomm.qcrilhook.IOemHookCallback.Stub;
import com.qualcomm.qcrilhook.QmiOemHookConstants.ResponseType;

public class OemHookCallback extends Stub {
    Message mAppMessage;

    public OemHookCallback(Message msg) {
        this.mAppMessage = msg;
    }

    public void onOemHookException(int phoneId) throws RemoteException {
        StringBuilder sb = new StringBuilder();
        sb.append("mPhoneId: ");
        sb.append(phoneId);
        Log.w("onOemHookException", sb.toString());
    }

    public void onOemHookResponse(byte[] response, int phoneId) throws RemoteException {
        StringBuilder sb = new StringBuilder();
        sb.append("mPhoneId: ");
        sb.append(phoneId);
        Log.w("OemHookCallback", sb.toString());
        QmiOemHook.receive(response, this.mAppMessage, ResponseType.IS_ASYNC_RESPONSE, phoneId);
    }
}
