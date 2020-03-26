package com.qualcomm.qti.internal.nrNetworkService;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
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
    private static final int EVENT_ON_ENABLE_ENDC = 8;
    private static final int EVENT_ON_ENDC_STATUS = 9;
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
            Log.d(MainServiceImpl.TAG, "on5gStatus slotId = " + slotId + " token = " + token + " status = " + status + " enableStatus = " + enableStatus);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(1, slotId, -1, new Result(token, status, Boolean.valueOf(enableStatus))));
        }

        public void onNrDcParam(int slotId, Token token, Status status, DcParam dcParam) {
            Log.d(MainServiceImpl.TAG, "onNrDcParam slotId = " + slotId + " token = " + token + " status = " + status + " DcParam = " + dcParam);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(3, slotId, -1, new Result(token, status, dcParam)));
        }

        public void onAnyNrBearerAllocation(int slotId, Token token, Status status, BearerAllocationStatus bearerStatus) {
            Log.d(MainServiceImpl.TAG, "onAnyNrBearerAllocation slotId = " + slotId + " bearerStatus = " + bearerStatus);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(0, slotId, -1, new Result(token, status, bearerStatus)));
        }

        public void onUpperLayerIndInfo(int slotId, Token token, Status status, UpperLayerIndInfo ulInfo) {
            Log.d(MainServiceImpl.TAG, "onUpperLayerIndInfo slotId = " + slotId + " UpperLayerIndInfo = " + ulInfo);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(5, slotId, -1, new Result(token, status, ulInfo)));
        }

        public void on5gConfigInfo(int slotId, Token token, Status status, NrConfigType nrConfigType) {
            Log.d(MainServiceImpl.TAG, "on5gConfigInfo slotId = " + slotId + " NrConfigType = " + nrConfigType);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(6, slotId, -1, new Result(token, status, nrConfigType)));
        }

        public void onSignalStrength(int slotId, Token token, Status status, SignalStrength signalStrength) {
            Log.d(MainServiceImpl.TAG, "onSignalStrength slotId = " + slotId + " signalStrength = " + signalStrength);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(2, slotId, -1, new Result(token, status, signalStrength)));
        }

        public void onNrIconType(int slotId, Token token, Status status, NrIconType nrIconType) {
            Log.d(MainServiceImpl.TAG, "onNrIconType slotId = " + slotId + " NrIconType = " + nrIconType);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(7, slotId, -1, new Result(token, status, nrIconType)));
        }

        public void onEnableEndc(int slotId, Token token, Status status) {
            Log.d(MainServiceImpl.TAG, "onEnableEndc slotId = " + slotId + " token = " + token + " status = " + status);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(8, slotId, -1, new Result(token, status, (Object) null)));
        }

        public void onEndcStatus(int slotId, Token token, Status status, boolean enableStatus) {
            Log.d(MainServiceImpl.TAG, "onEndcStatus slotId = " + slotId + " token = " + token + " status = " + status + " enable = " + enableStatus);
            MainServiceImpl.this.mWorkerThreadHandler.sendMessage(MainServiceImpl.this.mWorkerThreadHandler.obtainMessage(9, slotId, -1, new Result(token, status, Boolean.valueOf(enableStatus))));
        }
    };
    private IHidlConnectionInterface mHidlConnectionInterface;
    private ConcurrentHashMap<Integer, Transaction> mInflightRequests = new ConcurrentHashMap<>();
    private HandlerThread mWorkerThread = new HandlerThread("MainServiceImplBgThread");
    /* access modifiers changed from: private */
    public Handler mWorkerThreadHandler;

    private class WorkerHandler extends Handler {
        private static final String TAG = "MainServiceImplHandler: ";

        public WorkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage msg.what = " + msg.what);
            switch (msg.what) {
                case 0:
                    Log.d(TAG, "EVENT_ON_BEARER_ALLOCATION_CHANGE_IND");
                    int slotId = msg.arg1;
                    Result result = (Result) msg.obj;
                    MainServiceImpl.this.onAnyNrBearerAllocation(slotId, result.mToken, result.mStatus, (BearerAllocationStatus) result.mData);
                    return;
                case 1:
                    Log.d(TAG, "EVENT_ON_5G_ENABLE_STATUS");
                    int slotId2 = msg.arg1;
                    Result result2 = (Result) msg.obj;
                    MainServiceImpl.this.on5gStatus(slotId2, result2.mToken, result2.mStatus, ((Boolean) result2.mData).booleanValue());
                    return;
                case 2:
                    Log.d(TAG, "EVENT_ON_5G_SIGNAL_STRENGTH_CHANGE_IND");
                    int slotId3 = msg.arg1;
                    Result result3 = (Result) msg.obj;
                    MainServiceImpl.this.onSignalStrength(slotId3, result3.mToken, result3.mStatus, (SignalStrength) result3.mData);
                    return;
                case 3:
                    Log.d(TAG, "EVENT_ON_NR_DUAL_CONNECTIVITY_CHANGE_IND");
                    int slotId4 = msg.arg1;
                    Result result4 = (Result) msg.obj;
                    MainServiceImpl.this.onNrDcParam(slotId4, result4.mToken, result4.mStatus, (DcParam) result4.mData);
                    return;
                case 5:
                    Log.d(TAG, "EVENT_ON_UPPER_LAYER_INDICATION_INFO");
                    int slotId5 = msg.arg1;
                    Result result5 = (Result) msg.obj;
                    MainServiceImpl.this.onUpperLayerIndInfo(slotId5, result5.mToken, result5.mStatus, (UpperLayerIndInfo) result5.mData);
                    return;
                case 6:
                    Log.d(TAG, "EVENT_ON_5G_CONFIG_INFO");
                    int slotId6 = msg.arg1;
                    Result result6 = (Result) msg.obj;
                    MainServiceImpl.this.on5gConfigInfo(slotId6, result6.mToken, result6.mStatus, (NrConfigType) result6.mData);
                    return;
                case 7:
                    Log.d(TAG, "EVENT_ON_NR_ICON_TYPE");
                    int slotId7 = msg.arg1;
                    Result result7 = (Result) msg.obj;
                    MainServiceImpl.this.onNrIconType(slotId7, result7.mToken, result7.mStatus, (NrIconType) result7.mData);
                    return;
                case 8:
                    Log.d(TAG, "EVENT_ON_ENABLE_ENDC");
                    int slotId8 = msg.arg1;
                    Result result8 = (Result) msg.obj;
                    MainServiceImpl.this.onEnableEndc(slotId8, result8.mToken, result8.mStatus);
                    return;
                case 9:
                    Log.d(TAG, "EVENT_ON_ENDC_STATUS");
                    int slotId9 = msg.arg1;
                    Result result9 = (Result) msg.obj;
                    MainServiceImpl.this.onEndcStatus(slotId9, result9.mToken, result9.mStatus, ((Boolean) result9.mData).booleanValue());
                    return;
                default:
                    return;
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
            return "Result{mToken=" + this.mToken + ", mStatus=" + this.mStatus + ", mData=" + this.mData + '}';
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
            return "Transaction{mToken=" + this.mToken + ", mName='" + this.mName + '\'' + ", mClient=" + this.mClient + '}';
        }
    }

    public static MainServiceImpl init(Context context) {
        MainServiceImpl mainServiceImpl;
        synchronized (MainServiceImpl.class) {
            mContext = context;
            if (sInstance == null) {
                sInstance = new MainServiceImpl(context);
            } else {
                Log.wtf(TAG, "init() called multiple times!  sInstance = " + sInstance);
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
        Log.d(TAG, "enable5g: slotId = " + slotId + " uid = " + uid + " package=" + packageName);
        Token token = this.mHidlConnectionInterface.enable5g(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "Enable5g", client));
        return token;
    }

    public Token disable5g(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "disable5g: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.disable5g(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "disable5g", client));
        return token;
    }

    public Token enable5gOnly(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "enable5gOnly: slotId = " + slotId);
        throw new RemoteException("not implemented");
    }

    public Token query5gStatus(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "query5gStatus: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.query5gStatus(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "query5gStatus", client));
        return token;
    }

    public Token queryNrDcParam(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "queryNrDcParam: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.queryNrDcParam(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrDcParam", client));
        return token;
    }

    public Token queryNrBearerAllocation(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "queryNrBearerAllocation: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.queryNrBearerAllocation(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrBearerAllocation", client));
        return token;
    }

    public Token queryNrSignalStrength(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "queryNrSignalStrength: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.queryNrSignalStrength(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrSignalStrength", client));
        return token;
    }

    public Token queryUpperLayerIndInfo(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "queryUpperLayerIndInfo: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.queryUpperLayerIndInfo(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryUpperLayerIndInfo", client));
        return token;
    }

    public Token query5gConfigInfo(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "query5gConfigInfo: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.query5gConfigInfo(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "query5gConfigInfo", client));
        return token;
    }

    public Token queryNrIconType(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "queryNrIconType: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.queryNrIconType(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryNrIconType", client));
        return token;
    }

    public Token enableEndc(int slotId, boolean enabled, Client client) throws RemoteException {
        Log.d(TAG, "enableEndc: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.enableEndc(slotId, enabled);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "enableEndc", client));
        return token;
    }

    public Token queryEndcStatus(int slotId, Client client) throws RemoteException {
        Log.d(TAG, "queryEndcStatus: slotId = " + slotId);
        Token token = this.mHidlConnectionInterface.queryEndcStatus(slotId);
        this.mInflightRequests.put(Integer.valueOf(token.get()), new Transaction(token, "queryEndcStatus", client));
        return token;
    }

    private boolean addCallback(INetworkCallback callback) {
        IBinder binder = callback.asBinder();
        synchronized (this.mCallbackList) {
            Iterator<INetworkCallback> it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                if (it.next().asBinder().equals(binder)) {
                    boolean z = this.FAILED;
                    return z;
                }
            }
            Log.d(TAG, "add callback= " + callback);
            this.mCallbackList.add(callback);
            return this.SUCCESS;
        }
    }

    private void removeCallback(INetworkCallback callback) {
        IBinder binder = callback.asBinder();
        Log.d(TAG, "removeCallback: callback= " + callback + ", Binder = " + binder);
        synchronized (this.mCallbackList) {
            Iterator<INetworkCallback> it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                INetworkCallback it2 = it.next();
                if (it2.asBinder().equals(binder)) {
                    Log.d(TAG, "remove callback= " + it2);
                    this.mCallbackList.remove(it2);
                    return;
                }
            }
        }
    }

    private void removeClientFromInflightRequests(INetworkCallback callback) {
        for (Integer intValue : this.mInflightRequests.keySet()) {
            int key = intValue.intValue();
            if (this.mInflightRequests.get(Integer.valueOf(key)).mClient.getCallback().asBinder() == callback.asBinder()) {
                Log.d(TAG, "removeClientFromInflightRequests: Token = " + key + " => " + this.mInflightRequests.get(Integer.valueOf(key)));
                this.mInflightRequests.remove(Integer.valueOf(key));
            }
        }
    }

    public Client registerCallback(String packageName, INetworkCallback callback) throws RemoteException {
        IBinder binder = callback.asBinder();
        binder.linkToDeath(new ClientBinderDeathRecipient(callback), 0);
        int uid = Binder.getCallingUid();
        String callerPackageName = mContext.getPackageManager().getNameForUid(uid);
        Log.d(TAG, "registerCallback: uid = " + uid + " callerPackage=" + callerPackageName + "callback = " + callback + "binder = " + binder);
        this.mAppOpsManager.checkPackage(Binder.getCallingUid(), packageName);
        if (addCallback(callback) == this.SUCCESS) {
            int i = this.mClientIndex + 1;
            this.mClientIndex = i;
            Client client = new Client(i, uid, packageName, callback);
            Log.d(TAG, "registerCallback: client = " + client);
            return client;
        }
        Log.d(TAG, "registerCallback: callback could not be added.");
        return null;
    }

    public void unRegisterCallback(INetworkCallback callback) throws RemoteException {
        removeCallback(callback);
        removeClientFromInflightRequests(callback);
    }

    class ClientBinderDeathRecipient implements IBinder.DeathRecipient {
        INetworkCallback mCallback;

        public ClientBinderDeathRecipient(INetworkCallback callback) {
            Log.d(MainServiceImpl.TAG, "registering for client cb = " + callback + " binder = " + callback.asBinder() + " death notification");
            this.mCallback = callback;
        }

        public void binderDied() {
            Log.d(MainServiceImpl.TAG, "Client callback = " + this.mCallback + " binder = " + this.mCallback.asBinder() + "died");
            this.mCallback.asBinder().unlinkToDeath(this, 0);
            try {
                MainServiceImpl.this.unRegisterCallback(this.mCallback);
            } catch (RemoteException e) {
                Log.d(MainServiceImpl.TAG, "Exception while unregistering callback = " + this.mCallback + " binder = " + this.mCallback.asBinder());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<INetworkCallback> retrieveCallbacks(int tokenKey) {
        ArrayList<INetworkCallback> list = new ArrayList<>();
        if (tokenKey == -1) {
            return this.mCallbackList;
        }
        if (!this.mInflightRequests.containsKey(Integer.valueOf(tokenKey))) {
            return list;
        }
        list.add(this.mInflightRequests.get(Integer.valueOf(tokenKey)).mClient.getCallback());
        return list;
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void on5gStatus(int slotId, Token token, Status status, boolean enableStatus) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "on5gStatus: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().on5gStatus(slotId, token, status, enableStatus);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "on5gStatus: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onNrDcParam(int slotId, Token token, Status status, DcParam dcParam) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onNrDcParam: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onNrDcParam(slotId, token, status, dcParam);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "on5gStatus: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onUpperLayerIndInfo(int slotId, Token token, Status status, UpperLayerIndInfo ulInfo) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onUpperLayerIndInfo: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onUpperLayerIndInfo(slotId, token, status, ulInfo);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "onUpperLayerIndInfo: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void on5gConfigInfo(int slotId, Token token, Status status, NrConfigType nrConfigType) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "on5gConfigInfo: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().on5gConfigInfo(slotId, token, status, nrConfigType);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "on5gConfigInfo: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onAnyNrBearerAllocation(int slotId, Token token, Status status, BearerAllocationStatus bearerStatus) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onAnyNrBearerAllocation: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onAnyNrBearerAllocation(slotId, token, status, bearerStatus);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "on5gStatus: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onSignalStrength(int slotId, Token token, Status status, SignalStrength signalStrength) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onSignalStrength: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onSignalStrength(slotId, token, status, signalStrength);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "on5gStatus: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onNrIconType(int slotId, Token token, Status status, NrIconType nrIconType) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onNrIconType: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onNrIconType(slotId, token, status, nrIconType);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "onNrIconType: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onEnableEndc(int slotId, Token token, Status status) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onEnableEndc: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onEnableEndc(slotId, token, status);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "onEnableEndc: Exception = " + e);
        }
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* access modifiers changed from: private */
    public void onEndcStatus(int slotId, Token token, Status status, boolean enableStatus) {
        try {
            int tokenKey = token.get();
            synchronized (this.mCallbackList) {
                Iterator<INetworkCallback> it = retrieveCallbacks(tokenKey).iterator();
                while (it.hasNext()) {
                    Log.d(TAG, "onEndcStatus: Responding back for transaction = " + this.mInflightRequests.get(Integer.valueOf(tokenKey)));
                    it.next().onEndcStatus(slotId, token, status, enableStatus);
                    this.mInflightRequests.remove(Integer.valueOf(tokenKey));
                }
            }
        } catch (RemoteException e) {
            Log.d(TAG, "onEndcStatus: Exception = " + e);
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
            Iterator<INetworkCallback> it = this.mCallbackList.iterator();
            while (it.hasNext()) {
                INetworkCallback callback = it.next();
                IBinder binder = callback.asBinder();
                pw.println("Callback = " + callback + "-> Binder = " + binder);
            }
        }
    }

    private void dumpInflightRequests(PrintWriter pw) {
        for (Integer key : this.mInflightRequests.keySet()) {
            pw.println("Token = " + key + " => " + this.mInflightRequests.get(key));
        }
    }

    public void dump(FileDescriptor fd, PrintWriter printwriter, String[] args) {
        PrintWriter pw = printwriter;
        pw.println("5G-Middleware:");
        pw.println("mHidlConnectionInterface = " + this.mHidlConnectionInterface);
        pw.println("AIDL clients : ");
        dumpAidlClients(pw);
        pw.flush();
        pw.println("Inflight requests : ");
        dumpInflightRequests(pw);
        pw.flush();
    }
}
