package com.qualcomm.qti.internal.nrNetworkService.hidl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import org.codeaurora.internal.BearerAllocationStatus;
import org.codeaurora.internal.DcParam;
import org.codeaurora.internal.NrConfigType;
import org.codeaurora.internal.NrIconType;
import org.codeaurora.internal.SignalStrength;
import org.codeaurora.internal.Status;
import org.codeaurora.internal.Token;
import org.codeaurora.internal.UpperLayerIndInfo;

public class FakeHidlConnectionInterfaceImpl implements IHidlConnectionInterface {
    private static final String TAG = "FakeHidlConnectionInterfaceImpl";
    private final int EVENT_SIMULATE_5G_DISABLED = 1;
    private final int EVENT_SIMULATE_5G_ENABLED = 0;
    private final int EVENT_SIMULATE_ENABLE_ENDC = 9;
    private final int EVENT_SIMULATE_QUERY_5G_STATUS = 2;
    private final int EVENT_SIMULATE_QUERY_BEARER_ALLOCATION = 4;
    private final int EVENT_SIMULATE_QUERY_CONFIG_TYPE = 7;
    private final int EVENT_SIMULATE_QUERY_ENDC_STATUS = 10;
    private final int EVENT_SIMULATE_QUERY_ICON_TYPE = 8;
    private final int EVENT_SIMULATE_QUERY_NRDC_PARAM = 3;
    private final int EVENT_SIMULATE_QUERY_SIGNAL_STRENGTH = 5;
    private final int EVENT_SIMULATE_QUERY_UPPER_LAYER_IND_INFO = 6;
    /* access modifiers changed from: private */
    public final Token UNSOL = new Token(-1);
    /* access modifiers changed from: private */
    public boolean m5gEnabledState = false;
    /* access modifiers changed from: private */
    public IHidlConnectionCallback mCallback;
    private int mSerial = -1;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread = new HandlerThread("FakeHidlConnectionInterfaceImplBgThread");

    public FakeHidlConnectionInterfaceImpl() {
        this.mWorkerThread.start();
        Looper workerLooper = this.mWorkerThread.getLooper();
        this.mWorkerHandler = new WorkerHandler(workerLooper);
        Log.d(TAG, "constructor... using its own bg thread Looper = " + workerLooper);
    }

    public FakeHidlConnectionInterfaceImpl(Looper workerLooper) {
        Log.d(TAG, "constructor... Looper = " + workerLooper);
        this.mWorkerHandler = new WorkerHandler(workerLooper);
    }

    private Token getNextToken() {
        int i = this.mSerial + 1;
        this.mSerial = i;
        return new Token(i);
    }

    private class WorkerHandler extends Handler {
        private static final String TAG = "FakeHidleConnection-WorkerHandler";

        public WorkerHandler() {
        }

        public WorkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            DcParam dcParam;
            BearerAllocationStatus bearerAllocationStatus;
            UpperLayerIndInfo upperLayerIndInfo;
            Log.d(TAG, "handleMessage msg.what = " + msg.what);
            SignalStrength ss = null;
            switch (msg.what) {
                case 0:
                    int slotId = msg.arg1;
                    Token token = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_5G_ENABLED: token = " + token);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gStatus(slotId, token, new Status(1), true);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onNrDcParam(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new DcParam(1, 1));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onUpperLayerIndInfo(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new UpperLayerIndInfo(1, 1));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onAnyNrBearerAllocation(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new BearerAllocationStatus(2));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onSignalStrength(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new SignalStrength());
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gConfigInfo(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new NrConfigType(0));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onNrIconType(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new NrIconType(1));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gStatus(slotId, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), true);
                        boolean unused = FakeHidlConnectionInterfaceImpl.this.m5gEnabledState = true;
                        return;
                    }
                    return;
                case 1:
                    int slotId2 = msg.arg1;
                    Token token2 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_5G_DISABLED: token = " + token2);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gStatus(slotId2, token2, new Status(1), false);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onNrDcParam(slotId2, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new DcParam(0, 0));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onUpperLayerIndInfo(slotId2, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new UpperLayerIndInfo(0, 0));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onAnyNrBearerAllocation(slotId2, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new BearerAllocationStatus(0));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onSignalStrength(slotId2, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), (SignalStrength) null);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onNrIconType(slotId2, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), new NrIconType(0));
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gStatus(slotId2, FakeHidlConnectionInterfaceImpl.this.UNSOL, new Status(1), false);
                        boolean unused2 = FakeHidlConnectionInterfaceImpl.this.m5gEnabledState = false;
                        return;
                    }
                    return;
                case 2:
                    int slotId3 = msg.arg1;
                    Token token3 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_5G_STATUS: token = " + token3);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gStatus(slotId3, token3, new Status(1), FakeHidlConnectionInterfaceImpl.this.m5gEnabledState);
                        return;
                    }
                    return;
                case 3:
                    int slotId4 = msg.arg1;
                    Token token4 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_NRDC_PARAM: token = " + token4);
                        if (FakeHidlConnectionInterfaceImpl.this.m5gEnabledState) {
                            dcParam = new DcParam(1, 1);
                        } else {
                            dcParam = new DcParam(0, 0);
                        }
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onNrDcParam(slotId4, token4, new Status(1), dcParam);
                        return;
                    }
                    return;
                case 4:
                    int slotId5 = msg.arg1;
                    Token token5 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_BEARER_ALLOCATION: token = " + token5);
                        if (FakeHidlConnectionInterfaceImpl.this.m5gEnabledState) {
                            bearerAllocationStatus = new BearerAllocationStatus(2);
                        } else {
                            bearerAllocationStatus = new BearerAllocationStatus(0);
                        }
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onAnyNrBearerAllocation(slotId5, token5, new Status(1), bearerAllocationStatus);
                        return;
                    }
                    return;
                case 5:
                    int slotId6 = msg.arg1;
                    Token token6 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_SIGNAL_STRENGTH: token = " + token6);
                        if (FakeHidlConnectionInterfaceImpl.this.m5gEnabledState) {
                            ss = new SignalStrength();
                        }
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onSignalStrength(slotId6, token6, new Status(1), ss);
                        return;
                    }
                    return;
                case 6:
                    int slotId7 = msg.arg1;
                    Token token7 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_UPPER_LAYER_IND_INFO: token = " + token7);
                        if (FakeHidlConnectionInterfaceImpl.this.m5gEnabledState) {
                            upperLayerIndInfo = new UpperLayerIndInfo(1, 1);
                        } else {
                            upperLayerIndInfo = new UpperLayerIndInfo(0, 0);
                        }
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onUpperLayerIndInfo(slotId7, token7, new Status(1), upperLayerIndInfo);
                        return;
                    }
                    return;
                case 7:
                    int slotId8 = msg.arg1;
                    Token token8 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_CONFIG_TYPE: token = " + token8);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.on5gConfigInfo(slotId8, token8, new Status(1), new NrConfigType(0));
                        return;
                    }
                    return;
                case 8:
                    int slotId9 = msg.arg1;
                    Token token9 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_ICON_TYPE: token = " + token9);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onNrIconType(slotId9, token9, new Status(1), new NrIconType(1));
                        return;
                    }
                    return;
                case 9:
                    int slotId10 = msg.arg1;
                    Token token10 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_ENABLE_ENDC: token = " + token10);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onEnableEndc(slotId10, token10, new Status(1));
                        return;
                    }
                    return;
                case 10:
                    int slotId11 = msg.arg1;
                    Token token11 = (Token) msg.obj;
                    if (FakeHidlConnectionInterfaceImpl.this.mCallback != null) {
                        Log.d(TAG, "EVENT_SIMULATE_QUERY_ENDC_STATUS: token = " + token11);
                        FakeHidlConnectionInterfaceImpl.this.mCallback.onEndcStatus(slotId11, token11, new Status(1), true);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public Token enable5g(int slotId) throws RemoteException {
        Log.d(TAG, "enable5g: ");
        Token token = getNextToken();
        if (this.mCallback != null) {
            Handler handler = this.mWorkerHandler;
            handler.sendMessageDelayed(handler.obtainMessage(0, slotId, -1, token), 2000);
        }
        Log.d(TAG, "enable5g: token = " + token);
        return token;
    }

    public Token disable5g(int slotId) throws RemoteException {
        Log.d(TAG, "disable5g: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1, slotId, -1, token), 2000);
        Log.d(TAG, "disable5g: token = " + token);
        return token;
    }

    public Token enable5gOnly(int slotId) throws RemoteException {
        return null;
    }

    public Token query5gStatus(int slotId) throws RemoteException {
        Log.d(TAG, "query5gStatus: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(2, slotId, -1, token));
        Log.d(TAG, "query5gStatus: token = " + token);
        return token;
    }

    public Token query5gConfigInfo(int slotId) throws RemoteException {
        Log.d(TAG, "query5gConfigInfo: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(7, slotId, -1, token));
        Log.d(TAG, "query5gConfigInfo: token = " + token);
        return token;
    }

    public Token queryNrDcParam(int slotId) throws RemoteException {
        Log.d(TAG, "queryNrDcParam: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(3, slotId, -1, token));
        Log.d(TAG, "queryNrDcParam: token = " + token);
        return token;
    }

    public Token queryNrBearerAllocation(int slotId) throws RemoteException {
        Log.d(TAG, "queryNrBearerAllocation: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(4, slotId, -1, token));
        Log.d(TAG, "queryNrBearerAllocation: token = " + token);
        return token;
    }

    public Token queryNrSignalStrength(int slotId) throws RemoteException {
        Log.d(TAG, "queryNrSignalStrength: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(5, slotId, -1, token));
        Log.d(TAG, "queryNrSignalStrength: token = " + token);
        return token;
    }

    public Token queryUpperLayerIndInfo(int slotId) throws RemoteException {
        Log.d(TAG, "queryUpperLayerIndInfo: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(6, slotId, -1, token));
        Log.d(TAG, "queryUpperLayerIndInfo: token = " + token);
        return token;
    }

    public Token queryNrIconType(int slotId) throws RemoteException {
        Log.d(TAG, "queryNrIconType: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(8, slotId, -1, token));
        Log.d(TAG, "queryNrIconType: token = " + token);
        return token;
    }

    public Token enableEndc(int slotId, boolean enable) throws RemoteException {
        Log.d(TAG, "enableEndc: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(9, slotId, -1, token));
        Log.d(TAG, "enableEndc: token = " + token);
        return token;
    }

    public Token queryEndcStatus(int slotId) throws RemoteException {
        Log.d(TAG, "queryEndcStatus: ");
        Token token = getNextToken();
        Handler handler = this.mWorkerHandler;
        handler.sendMessage(handler.obtainMessage(10, slotId, -1, token));
        Log.d(TAG, "queryEndcStatus: token = " + token);
        return token;
    }

    public void registerCallback(IHidlConnectionCallback callback) {
        Log.d(TAG, "registerCallback: callback = " + callback);
        this.mCallback = callback;
    }

    public void unRegisterCallback(IHidlConnectionCallback callback) {
        Log.d(TAG, "unRegisterCallback: callback = " + callback);
        if (this.mCallback == callback) {
            this.mCallback = null;
        }
    }
}
