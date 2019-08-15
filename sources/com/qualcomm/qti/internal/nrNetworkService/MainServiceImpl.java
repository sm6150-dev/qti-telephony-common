package com.qualcomm.qti.internal.nrNetworkService;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.qualcomm.qti.internal.nrNetworkService.hidl.IHidlConnectionCallback;
import com.qualcomm.qti.internal.nrNetworkService.hidl.IHidlConnectionInterface;
import com.qualcomm.qti.internal.nrNetworkService.hidl.QtiRadioHidlClient;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.codeaurora.internal.BearerAllocationStatus;
import org.codeaurora.internal.Client;
import org.codeaurora.internal.DcParam;
import org.codeaurora.internal.INetworkCallback;
import org.codeaurora.internal.NrConfigType;
import org.codeaurora.internal.NrIconType;
import org.codeaurora.internal.SignalStrength;
import org.codeaurora.internal.Status;
import org.codeaurora.internal.Token;
import org.codeaurora.internal.UpperLayerIndInfo;

public class MainServiceImpl {
    private static final int EVENT_ON_5G_CONFIG_INFO = 6;
    private static final int EVENT_ON_5G_ENABLE_STATUS_CHANGE_IND = 1;
    private static final int EVENT_ON_5G_SIGNAL_STRENGTH_CHANGE_IND = 2;
    private static final int EVENT_ON_BEARER_ALLOCATION_CHANGE_IND = 0;
    private static final int EVENT_ON_NR_DUAL_CONNECTIVITY_CHANGE_IND = 3;
    private static final int EVENT_ON_NR_ICON_TYPE = 7;
    private static final int EVENT_ON_RESTRICT_DCNR_CHANGE = 4;
    private static final int EVENT_ON_UPPER_LAYER_INDICATION_INFO = 5;
    private static final String TAG = "MainServiceImpl";
    private static Context mContext;
    private static MainServiceImpl sInstance = null;
    private boolean FAILED = false;
    private boolean SUCCESS = true;
    private AppOpsManager mAppOpsManager;
    private final ArrayList<INetworkCallback> mCallbackList = new ArrayList<>();
    private int mClientIndex = -1;
    IHidlConnectionCallback mHidlConnectionCallback = new IHidlConnectionCallback() {
        public void on5gStatus(int slotId, Token token, Status status, boolean enableStatus) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("on5gStatus slotId = ");
            sb.append(slotId);
            sb.append(" token = ");
            sb.append(token);
            sb.append(" status = ");
            sb.append(status);
            sb.append(" enableStatus = ");
            sb.append(enableStatus);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(1, slotId, -1, new Result(token, status, Boolean.valueOf(enableStatus))));
        }

        public void onNrDcParam(int slotId, Token token, Status status, DcParam dcParam) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrDcParam slotId = ");
            sb.append(slotId);
            sb.append(" token = ");
            sb.append(token);
            sb.append(" status = ");
            sb.append(status);
            sb.append(" DcParam = ");
            sb.append(dcParam);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(3, slotId, -1, new Result(token, status, dcParam)));
        }

        public void onAnyNrBearerAllocation(int slotId, Token token, Status status, BearerAllocationStatus bearerStatus) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onAnyNrBearerAllocation slotId = ");
            sb.append(slotId);
            sb.append(" bearerStatus = ");
            sb.append(bearerStatus);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(0, slotId, -1, new Result(token, status, bearerStatus)));
        }

        public void onUpperLayerIndInfo(int slotId, Token token, Status status, UpperLayerIndInfo ulInfo) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onUpperLayerIndInfo slotId = ");
            sb.append(slotId);
            sb.append(" UpperLayerIndInfo = ");
            sb.append(ulInfo);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(5, slotId, -1, new Result(token, status, ulInfo)));
        }

        public void on5gConfigInfo(int slotId, Token token, Status status, NrConfigType nrConfigType) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("on5gConfigInfo slotId = ");
            sb.append(slotId);
            sb.append(" NrConfigType = ");
            sb.append(nrConfigType);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(6, slotId, -1, new Result(token, status, nrConfigType)));
        }

        public void onSignalStrength(int slotId, Token token, Status status, SignalStrength signalStrength) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onSignalStrength slotId = ");
            sb.append(slotId);
            sb.append(" signalStrength = ");
            sb.append(signalStrength);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(2, slotId, -1, new Result(token, status, signalStrength)));
        }

        public void onNrIconType(int slotId, Token token, Status status, NrIconType nrIconType) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onNrIconType slotId = ");
            sb.append(slotId);
            sb.append(" NrIconType = ");
            sb.append(nrIconType);
            Log.d(str, sb.toString());
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(7, slotId, -1, new Result(token, status, nrIconType)));
        }
    };
    private IHidlConnectionInterface mHidlConnectionInterface;
    private ConcurrentHashMap<Integer, Transaction> mInflightRequests = new ConcurrentHashMap<>();
    private HandlerThread mWorkerThread = new HandlerThread("MainServiceImplBgThread");
    /* access modifiers changed from: private */
    public Handler mWorkerThreadHandler;

    class ClientBinderDeathRecipient implements DeathRecipient {
        INetworkCallback mCallback;

        public ClientBinderDeathRecipient(INetworkCallback callback) {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("registering for client cb = ");
            sb.append(callback);
            sb.append(" binder = ");
            sb.append(callback.asBinder());
            sb.append(" death notification");
            Log.d(str, sb.toString());
            this.mCallback = callback;
        }

        public void binderDied() {
            String str = MainServiceImpl.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Client callback = ");
            sb.append(this.mCallback);
            sb.append(" binder = ");
            sb.append(this.mCallback.asBinder());
            sb.append("died");
            Log.d(str, sb.toString());
            this.mCallback.asBinder().unlinkToDeath(this, 0);
            try {
                MainServiceImpl.this.unRegisterCallback(this.mCallback);
            } catch (RemoteException e) {
                String str2 = MainServiceImpl.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Exception while unregistering callback = ");
                sb2.append(this.mCallback);
                sb2.append(" binder = ");
                sb2.append(this.mCallback.asBinder());
                Log.d(str2, sb2.toString());
            }
        }
    }

    class Result {
        Object mData;
        Status mStatus;
        Token mToken;

        public Result(Token mToken2, Status mStatus2, Object mData2) {
            this.mToken = mToken2;
            this.mStatus = mStatus2;
            this.mData = mData2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Result{mToken=");
            sb.append(this.mToken);
            sb.append(", mStatus=");
            sb.append(this.mStatus);
            sb.append(", mData=");
            sb.append(this.mData);
            sb.append('}');
            return sb.toString();
        }
    }

    class Transaction {
        Client mClient;
        String mName;
        Token mToken;

        public Transaction(Token token, String name, Client client) {
            this.mToken = token;
            this.mName = name;
            this.mClient = client;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Transaction{mToken=");
            sb.append(this.mToken);
            sb.append(", mName='");
            sb.append(this.mName);
            sb.append('\'');
            sb.append(", mClient=");
            sb.append(this.mClient);
            sb.append('}');
            return sb.toString();
        }
    }

    private class WorkerHandler extends Handler {
        private static final String TAG = "MainServiceImplHandler: ";

        public WorkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("handleMessage msg.what = ");
            sb.append(msg.what);
            Log.d(str, sb.toString());
            switch (msg.what) {
                case 0:
                    Log.d(TAG, "EVENT_ON_BEARER_ALLOCATION_CHANGE_IND");
                    Result result = (Result) msg.obj;
                    MainServiceImpl.this.onAnyNrBearerAllocation(msg.arg1, result.mToken, result.mStatus, (BearerAllocationStatus) result.mData);
                    return;
                case 1:
                    Log.d(TAG, "EVENT_ON_5G_ENABLE_STATUS");
                    Result result2 = (Result) msg.obj;
                    MainServiceImpl.this.on5gStatus(msg.arg1, result2.mToken, result2.mStatus, ((Boolean) result2.mData).booleanValue());
                    return;
                case 2:
                    Log.d(TAG, "EVENT_ON_5G_SIGNAL_STRENGTH_CHANGE_IND");
                    Result result3 = (Result) msg.obj;
                    MainServiceImpl.this.onSignalStrength(msg.arg1, result3.mToken, result3.mStatus, (SignalStrength) result3.mData);
                    return;
                case 3:
                    Log.d(TAG, "EVENT_ON_NR_DUAL_CONNECTIVITY_CHANGE_IND");
                    Result result4 = (Result) msg.obj;
                    MainServiceImpl.this.onNrDcParam(msg.arg1, result4.mToken, result4.mStatus, (DcParam) result4.mData);
                    return;
                case 5:
                    Log.d(TAG, "EVENT_ON_UPPER_LAYER_INDICATION_INFO");
                    Result result5 = (Result) msg.obj;
                    MainServiceImpl.this.onUpperLayerIndInfo(msg.arg1, result5.mToken, result5.mStatus, (UpperLayerIndInfo) result5.mData);
                    return;
                case 6:
                    Log.d(TAG, "EVENT_ON_5G_CONFIG_INFO");
                    Result result6 = (Result) msg.obj;
                    MainServiceImpl.this.on5gConfigInfo(msg.arg1, result6.mToken, result6.mStatus, (NrConfigType) result6.mData);
                    return;
                case 7:
                    Log.d(TAG, "EVENT_ON_NR_ICON_TYPE");
                    Result result7 = (Result) msg.obj;
                    MainServiceImpl.this.onNrIconType(msg.arg1, result7.mToken, result7.mStatus, (NrIconType) result7.mData);
                    return;
                default:
                    return;
            }
        }
    }

    public static MainServiceImpl init(Context context) {
        MainServiceImpl mainServiceImpl;
        synchronized (MainServiceImpl.class) {
            mContext = context;
            if (sInstance == null) {
                sInstance = new MainServiceImpl(context);
            } else {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("init() called multiple times!  sInstance = ");
                sb.append(sInstance);
                Log.wtf(str, sb.toString());
            }
            mainServiceImpl = sInstance;
        }
        return mainServiceImpl;
    }

    public static MainServiceImpl getInstance() {
        if (sInstance == null) {
            Log.wtf(TAG, "getInstance null");
        }
        return sInstance;
    }

    public MainServiceImpl(Context context) {
        Log.d(TAG, TAG);
        mContext = context;
        this.mAppOpsManager = (AppOpsManager) mContext.getSystemService("appops");
        setHidlClient(new QtiRadioHidlClient());
        this.mWorkerThread.start();
        setLooper(this.mWorkerThread.getLooper());
    }

    @VisibleForTesting
    public MainServiceImpl(Context context, IHidlConnectionInterface hidlClient, Looper workerLooper) {
        Log.d(TAG, TAG);
        mContext = context;
        this.mAppOpsManager = (AppOpsManager) mContext.getSystemService("appops");
        setHidlClient(hidlClient);
        setLooper(workerLooper);
    }

    public void setLooper(Looper workerLooper) {
        this.mWorkerThreadHandler = new WorkerHandler(workerLooper);
    }

    public void setHidlClient(IHidlConnectionInterface hidlClient) {
        this.mHidlConnectionInterface = hidlClient;
        this.mHidlConnectionInterface.registerCallback(this.mHidlConnectionCallback);
    }

    public Token enable5g(int slotId, Client client) throws RemoteException {
        int uid = Binder.getCallingUid();
        String packageName = mContext.getPackageManager().getNameForUid(uid);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("enable5g: slotId = ");
        sb.append(slotId);
        sb.append(" uid = ");
        sb.append(uid);
        sb.append(" package=");
        sb.append(packageName);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.enable5g(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "Enable5g", client));
        return token;
    }

    public Token disable5g(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("disable5g: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.disable5g(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "disable5g", client));
        return token;
    }

    public Token enable5gOnly(int slotId, Client client) throws RemoteException {
        StringBuilder sb = new StringBuilder();
        sb.append("enable5gOnly: slotId = ");
        sb.append(slotId);
        Log.d(TAG, sb.toString());
        throw new RemoteException("not implemented");
    }

    public Token query5gStatus(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("query5gStatus: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.query5gStatus(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "query5gStatus", client));
        return token;
    }

    public Token queryNrDcParam(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrDcParam: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.queryNrDcParam(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrDcParam", client));
        return token;
    }

    public Token queryNrBearerAllocation(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrBearerAllocation: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.queryNrBearerAllocation(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrBearerAllocation", client));
        return token;
    }

    public Token queryNrSignalStrength(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrSignalStrength: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.queryNrSignalStrength(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrSignalStrength", client));
        return token;
    }

    public Token queryUpperLayerIndInfo(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryUpperLayerIndInfo: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.queryUpperLayerIndInfo(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryUpperLayerIndInfo", client));
        return token;
    }

    public Token query5gConfigInfo(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("query5gConfigInfo: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.query5gConfigInfo(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "query5gConfigInfo", client));
        return token;
    }

    public Token queryNrIconType(int slotId, Client client) throws RemoteException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("queryNrIconType: slotId = ");
        sb.append(slotId);
        Log.d(str, sb.toString());
        Token token = this.mHidlConnectionInterface.queryNrIconType(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrIconType", client));
        return token;
    }

    private boolean addCallback(INetworkCallback callback) {
        IBinder binder = callback.asBinder();
        synchronized (this.mCallbackList) {
            Iterator it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                if (((INetworkCallback) it.next()).asBinder().equals(binder)) {
                    boolean z = this.FAILED;
                    return z;
                }
            }
            String str = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("add callback= ");
            sb.append(callback);
            Log.d(str, sb.toString());
            this.mCallbackList.add(callback);
            return this.SUCCESS;
        }
    }

    private void removeCallback(INetworkCallback callback) {
        IBinder binder = callback.asBinder();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("removeCallback: callback= ");
        sb.append(callback);
        sb.append(", Binder = ");
        sb.append(binder);
        Log.d(str, sb.toString());
        synchronized (this.mCallbackList) {
            Iterator it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                INetworkCallback it2 = (INetworkCallback) it.next();
                if (it2.asBinder().equals(binder)) {
                    String str2 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("remove callback= ");
                    sb2.append(it2);
                    Log.d(str2, sb2.toString());
                    this.mCallbackList.remove(it2);
                    return;
                }
            }
        }
    }

    private void removeClientFromInflightRequests(INetworkCallback callback) {
        for (Integer intValue : this.mInflightRequests.keySet()) {
            int key = intValue.intValue();
            if (((Transaction) this.mInflightRequests.get(Integer.valueOf(key))).mClient.getCallback().asBinder() == callback.asBinder()) {
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("removeClientFromInflightRequests: Token = ");
                sb.append(key);
                sb.append(" => ");
                sb.append(this.mInflightRequests.get(Integer.valueOf(key)));
                Log.d(str, sb.toString());
                this.mInflightRequests.remove(Integer.valueOf(key));
            }
        }
    }

    public Client registerCallback(String packageName, INetworkCallback callback) throws RemoteException {
        IBinder binder = callback.asBinder();
        binder.linkToDeath(new ClientBinderDeathRecipient(callback), 0);
        int uid = Binder.getCallingUid();
        String callerPackageName = mContext.getPackageManager().getNameForUid(uid);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("registerCallback: uid = ");
        sb.append(uid);
        sb.append(" callerPackage=");
        sb.append(callerPackageName);
        sb.append("callback = ");
        sb.append(callback);
        sb.append("binder = ");
        sb.append(binder);
        Log.d(str, sb.toString());
        this.mAppOpsManager.checkPackage(Binder.getCallingUid(), packageName);
        if (addCallback(callback) == this.SUCCESS) {
            int i = this.mClientIndex + 1;
            this.mClientIndex = i;
            Client client = new Client(i, uid, packageName, callback);
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("registerCallback: client = ");
            sb2.append(client);
            Log.d(str2, sb2.toString());
            return client;
        }
        Log.d(TAG, "registerCallback: callback could not be added.");
        return null;
    }

    public void unRegisterCallback(INetworkCallback callback) throws RemoteException {
        removeCallback(callback);
        removeClientFromInflightRequests(callback);
    }

    /* access modifiers changed from: 0000 */
    public ArrayList<INetworkCallback> retrieveCallbacks(int tokenKey) {
        ArrayList<INetworkCallback> list = new ArrayList<>();
        if (tokenKey == -1) {
            return this.mCallbackList;
        }
        if (!this.mInflightRequests.containsKey(Integer.valueOf(tokenKey))) {
            return list;
        }
        list.add(((Transaction) this.mInflightRequests.get(Integer.valueOf(tokenKey))).mClient.getCallback());
        return list;
    }

    /* access modifiers changed from: private */
    public void on5gStatus(int slotId, Token token, Status status, boolean enableStatus) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("on5gStatus: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.on5gStatus(slotId, token, status, enableStatus);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("on5gStatus: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void onNrDcParam(int slotId, Token token, Status status, DcParam dcParam) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onNrDcParam: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.onNrDcParam(slotId, token, status, dcParam);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("on5gStatus: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void onUpperLayerIndInfo(int slotId, Token token, Status status, UpperLayerIndInfo ulInfo) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onUpperLayerIndInfo: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.onUpperLayerIndInfo(slotId, token, status, ulInfo);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onUpperLayerIndInfo: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void on5gConfigInfo(int slotId, Token token, Status status, NrConfigType nrConfigType) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("on5gConfigInfo: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.on5gConfigInfo(slotId, token, status, nrConfigType);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("on5gConfigInfo: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void onAnyNrBearerAllocation(int slotId, Token token, Status status, BearerAllocationStatus bearerStatus) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onAnyNrBearerAllocation: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.onAnyNrBearerAllocation(slotId, token, status, bearerStatus);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("on5gStatus: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void onSignalStrength(int slotId, Token token, Status status, SignalStrength signalStrength) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onSignalStrength: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.onSignalStrength(slotId, token, status, signalStrength);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("on5gStatus: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void onNrIconType(int slotId, Token token, Status status, NrIconType nrIconType) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    INetworkCallback callback = (INetworkCallback) it.next();
                    String str = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("onNrIconType: Responding back for transaction = ");
                    sb.append(this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    Log.d(str, sb.toString());
                    callback.onNrIconType(slotId, token, status, nrIconType);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            String str2 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("onNrIconType: Exception = ");
            sb2.append(e);
            Log.d(str2, sb2.toString());
        }
    }

    public int getAidlClientsCount() {
        int size;
        synchronized (this.mCallbackList) {
            size = this.mCallbackList.size();
        }
        return size;
    }

    public int getInflightRequestsCount() {
        return this.mInflightRequests.size();
    }

    private void dumpAidlClients(PrintWriter pw) {
        synchronized (this.mCallbackList) {
            Iterator it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                INetworkCallback callback = (INetworkCallback) it.next();
                IBinder binder = callback.asBinder();
                StringBuilder sb = new StringBuilder();
                sb.append("Callback = ");
                sb.append(callback);
                sb.append("-> Binder = ");
                sb.append(binder);
                pw.println(sb.toString());
            }
        }
    }

    private void dumpInflightRequests(PrintWriter pw) {
        for (Integer key : this.mInflightRequests.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Token = ");
            sb.append(key);
            sb.append(" => ");
            sb.append(this.mInflightRequests.get(key));
            pw.println(sb.toString());
        }
    }

    public void dump(FileDescriptor fd, PrintWriter printwriter, String[] args) {
        PrintWriter pw = printwriter;
        pw.println("5G-Middleware:");
        StringBuilder sb = new StringBuilder();
        sb.append("mHidlConnectionInterface = ");
        sb.append(this.mHidlConnectionInterface);
        pw.println(sb.toString());
        pw.println("AIDL clients : ");
        dumpAidlClients(pw);
        pw.flush();
        pw.println("Inflight requests : ");
        dumpInflightRequests(pw);
        pw.flush();
    }
}
