.class public Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
.super Ljava/lang/Object;
.source "MainServiceImpl.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$ClientBinderDeathRecipient;,
        Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;,
        Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Result;,
        Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$WorkerHandler;
    }
.end annotation


# static fields
.field private static final EVENT_ON_5G_CONFIG_INFO:I = 0x6

.field private static final EVENT_ON_5G_ENABLE_STATUS_CHANGE_IND:I = 0x1

.field private static final EVENT_ON_5G_SIGNAL_STRENGTH_CHANGE_IND:I = 0x2

.field private static final EVENT_ON_BEARER_ALLOCATION_CHANGE_IND:I = 0x0

.field private static final EVENT_ON_ENABLE_ENDC:I = 0x8

.field private static final EVENT_ON_ENDC_STATUS:I = 0x9

.field private static final EVENT_ON_NR_DUAL_CONNECTIVITY_CHANGE_IND:I = 0x3

.field private static final EVENT_ON_NR_ICON_TYPE:I = 0x7

.field private static final EVENT_ON_RESTRICT_DCNR_CHANGE:I = 0x4

.field private static final EVENT_ON_UPPER_LAYER_INDICATION_INFO:I = 0x5

.field private static final TAG:Ljava/lang/String; = "MainServiceImpl"

.field private static mContext:Landroid/content/Context;

.field private static sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;


# instance fields
.field private FAILED:Z

.field private SUCCESS:Z

.field private mAppOpsManager:Landroid/app/AppOpsManager;

.field private final mCallbackList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lorg/codeaurora/internal/INetworkCallback;",
            ">;"
        }
    .end annotation
.end field

.field private mClientIndex:I

.field mHidlConnectionCallback:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionCallback;

.field private mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

.field private mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/concurrent/ConcurrentHashMap<",
            "Ljava/lang/Integer;",
            "Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;",
            ">;"
        }
    .end annotation
.end field

.field private mWorkerThread:Landroid/os/HandlerThread;

.field private mWorkerThreadHandler:Landroid/os/Handler;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 70
    const/4 v0, 0x0

    sput-object v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;

    .line 304
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 58
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->SUCCESS:Z

    .line 59
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->FAILED:Z

    .line 63
    const/4 v0, -0x1

    iput v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mClientIndex:I

    .line 64
    new-instance v0, Landroid/os/HandlerThread;

    const-string v1, "MainServiceImplBgThread"

    invoke-direct {v0, v1}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mWorkerThread:Landroid/os/HandlerThread;

    .line 67
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    .line 68
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 198
    new-instance v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$1;

    invoke-direct {v0, p0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$1;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionCallback:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionCallback;

    .line 305
    const-string v0, "MainServiceImpl"

    invoke-static {v0, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 306
    sput-object p1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mContext:Landroid/content/Context;

    .line 307
    const-string v0, "appops"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/AppOpsManager;

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mAppOpsManager:Landroid/app/AppOpsManager;

    .line 308
    new-instance v0, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/QtiRadioHidlClient;

    invoke-direct {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/QtiRadioHidlClient;-><init>()V

    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->setHidlClient(Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;)V

    .line 309
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mWorkerThread:Landroid/os/HandlerThread;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->start()V

    .line 310
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mWorkerThread:Landroid/os/HandlerThread;

    invoke-virtual {v0}, Landroid/os/HandlerThread;->getLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->setLooper(Landroid/os/Looper;)V

    .line 311
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;Landroid/os/Looper;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "hidlClient"    # Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;
    .param p3, "workerLooper"    # Landroid/os/Looper;

    .line 315
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 58
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->SUCCESS:Z

    .line 59
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->FAILED:Z

    .line 63
    const/4 v0, -0x1

    iput v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mClientIndex:I

    .line 64
    new-instance v0, Landroid/os/HandlerThread;

    const-string v1, "MainServiceImplBgThread"

    invoke-direct {v0, v1}, Landroid/os/HandlerThread;-><init>(Ljava/lang/String;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mWorkerThread:Landroid/os/HandlerThread;

    .line 67
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    .line 68
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 198
    new-instance v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$1;

    invoke-direct {v0, p0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$1;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionCallback:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionCallback;

    .line 316
    const-string v0, "MainServiceImpl"

    invoke-static {v0, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 317
    sput-object p1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mContext:Landroid/content/Context;

    .line 318
    const-string v0, "appops"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/AppOpsManager;

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mAppOpsManager:Landroid/app/AppOpsManager;

    .line 319
    invoke-virtual {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->setHidlClient(Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;)V

    .line 320
    invoke-virtual {p0, p3}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->setLooper(Landroid/os/Looper;)V

    .line 321
    return-void
.end method

.method static synthetic access$000(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/BearerAllocationStatus;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Lorg/codeaurora/internal/BearerAllocationStatus;

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onAnyNrBearerAllocation(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/BearerAllocationStatus;)V

    return-void
.end method

.method static synthetic access$100(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Z

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->on5gStatus(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V

    return-void
.end method

.method static synthetic access$200(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/SignalStrength;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Lorg/codeaurora/internal/SignalStrength;

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onSignalStrength(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/SignalStrength;)V

    return-void
.end method

.method static synthetic access$300(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/DcParam;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Lorg/codeaurora/internal/DcParam;

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onNrDcParam(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/DcParam;)V

    return-void
.end method

.method static synthetic access$400(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/UpperLayerIndInfo;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Lorg/codeaurora/internal/UpperLayerIndInfo;

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onUpperLayerIndInfo(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/UpperLayerIndInfo;)V

    return-void
.end method

.method static synthetic access$500(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrConfigType;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Lorg/codeaurora/internal/NrConfigType;

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->on5gConfigInfo(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrConfigType;)V

    return-void
.end method

.method static synthetic access$600(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrIconType;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Lorg/codeaurora/internal/NrIconType;

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onNrIconType(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrIconType;)V

    return-void
.end method

.method static synthetic access$700(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;

    .line 44
    invoke-direct {p0, p1, p2, p3}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onEnableEndc(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;)V

    return-void
.end method

.method static synthetic access$800(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V
    .locals 0
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .param p1, "x1"    # I
    .param p2, "x2"    # Lorg/codeaurora/internal/Token;
    .param p3, "x3"    # Lorg/codeaurora/internal/Status;
    .param p4, "x4"    # Z

    .line 44
    invoke-direct {p0, p1, p2, p3, p4}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->onEndcStatus(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V

    return-void
.end method

.method static synthetic access$900(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;)Landroid/os/Handler;
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    .line 44
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mWorkerThreadHandler:Landroid/os/Handler;

    return-object v0
.end method

.method private addCallback(Lorg/codeaurora/internal/INetworkCallback;)Z
    .locals 5
    .param p1, "callback"    # Lorg/codeaurora/internal/INetworkCallback;

    .line 449
    invoke-interface {p1}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v0

    .line 450
    .local v0, "binder":Landroid/os/IBinder;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1

    .line 451
    :try_start_0
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 452
    .local v3, "it":Lorg/codeaurora/internal/INetworkCallback;
    invoke-interface {v3}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_0

    .line 454
    iget-boolean v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->FAILED:Z

    monitor-exit v1

    return v2

    .line 456
    .end local v3    # "it":Lorg/codeaurora/internal/INetworkCallback;
    :cond_0
    goto :goto_0

    .line 457
    :cond_1
    const-string v2, "MainServiceImpl"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "add callback= "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 458
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v2, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 459
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 460
    iget-boolean v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->SUCCESS:Z

    return v1

    .line 459
    :catchall_0
    move-exception v2

    :try_start_1
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v2
.end method

.method private dumpAidlClients(Ljava/io/PrintWriter;)V
    .locals 6
    .param p1, "pw"    # Ljava/io/PrintWriter;

    .line 738
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v0

    .line 739
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lorg/codeaurora/internal/INetworkCallback;

    .line 740
    .local v2, "callback":Lorg/codeaurora/internal/INetworkCallback;
    invoke-interface {v2}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v3

    .line 741
    .local v3, "binder":Landroid/os/IBinder;
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Callback = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v5, "-> Binder = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {p1, v4}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 742
    .end local v2    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    .end local v3    # "binder":Landroid/os/IBinder;
    goto :goto_0

    .line 743
    :cond_0
    monitor-exit v0

    .line 744
    return-void

    .line 743
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method private dumpInflightRequests(Ljava/io/PrintWriter;)V
    .locals 4
    .param p1, "pw"    # Ljava/io/PrintWriter;

    .line 747
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Ljava/util/concurrent/ConcurrentHashMap;->keySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    .line 748
    .local v1, "key":Ljava/lang/Integer;
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Token = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v3, " => "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v3, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v3, v1}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p1, v2}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 749
    .end local v1    # "key":Ljava/lang/Integer;
    goto :goto_0

    .line 750
    :cond_0
    return-void
.end method

.method public static getInstance()Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .locals 2

    .line 298
    sget-object v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    if-nez v0, :cond_0

    .line 299
    const-string v0, "MainServiceImpl"

    const-string v1, "getInstance null"

    invoke-static {v0, v1}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;)I

    .line 301
    :cond_0
    sget-object v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    return-object v0
.end method

.method public static init(Landroid/content/Context;)Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .locals 4
    .param p0, "context"    # Landroid/content/Context;

    .line 286
    const-class v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    monitor-enter v0

    .line 287
    :try_start_0
    sput-object p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mContext:Landroid/content/Context;

    .line 288
    sget-object v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    if-nez v1, :cond_0

    .line 289
    new-instance v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    invoke-direct {v1, p0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;-><init>(Landroid/content/Context;)V

    sput-object v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    goto :goto_0

    .line 291
    :cond_0
    const-string v1, "MainServiceImpl"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "init() called multiple times!  sInstance = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->wtf(Ljava/lang/String;Ljava/lang/String;)I

    .line 293
    :goto_0
    sget-object v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->sInstance:Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;

    monitor-exit v0

    return-object v1

    .line 294
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method private isClientValid(Lorg/codeaurora/internal/Client;)Z
    .locals 6
    .param p1, "client"    # Lorg/codeaurora/internal/Client;

    .line 489
    const/4 v0, 0x0

    if-eqz p1, :cond_3

    invoke-virtual {p1}, Lorg/codeaurora/internal/Client;->getCallback()Lorg/codeaurora/internal/INetworkCallback;

    move-result-object v1

    if-nez v1, :cond_0

    goto :goto_1

    .line 493
    :cond_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1

    .line 494
    :try_start_0
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_2

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 495
    .local v3, "it":Lorg/codeaurora/internal/INetworkCallback;
    invoke-interface {v3}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v4

    invoke-virtual {p1}, Lorg/codeaurora/internal/Client;->getCallback()Lorg/codeaurora/internal/INetworkCallback;

    move-result-object v5

    invoke-interface {v5}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 496
    const/4 v0, 0x1

    monitor-exit v1

    return v0

    .line 498
    .end local v3    # "it":Lorg/codeaurora/internal/INetworkCallback;
    :cond_1
    goto :goto_0

    .line 499
    :cond_2
    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 500
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "This client is unregistered: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 501
    return v0

    .line 499
    :catchall_0
    move-exception v0

    :try_start_1
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    throw v0

    .line 490
    :cond_3
    :goto_1
    const-string v1, "MainServiceImpl"

    const-string v2, "Invalid client"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 491
    return v0
.end method

.method private on5gConfigInfo(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrConfigType;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "nrConfigType"    # Lorg/codeaurora/internal/NrConfigType;

    .line 630
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 631
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 632
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 633
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "on5gConfigInfo: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 634
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 633
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 635
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->on5gConfigInfo(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrConfigType;)V

    .line 636
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 637
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 638
    :cond_0
    monitor-exit v1

    .line 641
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 638
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "nrConfigType":Lorg/codeaurora/internal/NrConfigType;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 639
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "nrConfigType":Lorg/codeaurora/internal/NrConfigType;
    :catch_0
    move-exception v0

    .line 640
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "on5gConfigInfo: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 642
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private on5gStatus(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "enableStatus"    # Z

    .line 580
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 581
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 582
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 583
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "on5gStatus: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 584
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 583
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 585
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->on5gStatus(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V

    .line 586
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 587
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 588
    :cond_0
    monitor-exit v1

    .line 591
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 588
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "enableStatus":Z
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 589
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "enableStatus":Z
    :catch_0
    move-exception v0

    .line 590
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "on5gStatus: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 592
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onAnyNrBearerAllocation(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/BearerAllocationStatus;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "bearerStatus"    # Lorg/codeaurora/internal/BearerAllocationStatus;

    .line 647
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 648
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 649
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 650
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onAnyNrBearerAllocation: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 651
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 650
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 652
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->onAnyNrBearerAllocation(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/BearerAllocationStatus;)V

    .line 653
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 654
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 655
    :cond_0
    monitor-exit v1

    .line 658
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 655
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "bearerStatus":Lorg/codeaurora/internal/BearerAllocationStatus;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 656
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "bearerStatus":Lorg/codeaurora/internal/BearerAllocationStatus;
    :catch_0
    move-exception v0

    .line 657
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "on5gStatus: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 659
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onEnableEndc(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;

    .line 697
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 698
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 699
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 700
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onEnableEndc: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 701
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 700
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 702
    invoke-interface {v3, p1, p2, p3}, Lorg/codeaurora/internal/INetworkCallback;->onEnableEndc(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;)V

    .line 703
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 704
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 705
    :cond_0
    monitor-exit v1

    .line 708
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 705
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 706
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    :catch_0
    move-exception v0

    .line 707
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onEnableEndc: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 709
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onEndcStatus(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "enableStatus"    # Z

    .line 713
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 714
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 715
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 716
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onEndcStatus: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 717
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 716
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 718
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->onEndcStatus(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Z)V

    .line 719
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 720
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 721
    :cond_0
    monitor-exit v1

    .line 724
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 721
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "enableStatus":Z
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 722
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "enableStatus":Z
    :catch_0
    move-exception v0

    .line 723
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onEndcStatus: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 725
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onNrDcParam(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/DcParam;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "dcParam"    # Lorg/codeaurora/internal/DcParam;

    .line 596
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 597
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 598
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 599
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onNrDcParam: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 600
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 599
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 601
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->onNrDcParam(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/DcParam;)V

    .line 602
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 603
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 604
    :cond_0
    monitor-exit v1

    .line 607
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 604
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "dcParam":Lorg/codeaurora/internal/DcParam;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 605
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "dcParam":Lorg/codeaurora/internal/DcParam;
    :catch_0
    move-exception v0

    .line 606
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "on5gStatus: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 608
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onNrIconType(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrIconType;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "nrIconType"    # Lorg/codeaurora/internal/NrIconType;

    .line 681
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 682
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 683
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 684
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onNrIconType: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 685
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 684
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 686
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->onNrIconType(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/NrIconType;)V

    .line 687
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 688
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 689
    :cond_0
    monitor-exit v1

    .line 692
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 689
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "nrIconType":Lorg/codeaurora/internal/NrIconType;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 690
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "nrIconType":Lorg/codeaurora/internal/NrIconType;
    :catch_0
    move-exception v0

    .line 691
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onNrIconType: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 693
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onSignalStrength(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/SignalStrength;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "signalStrength"    # Lorg/codeaurora/internal/SignalStrength;

    .line 664
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 665
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 666
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 667
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onSignalStrength: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 668
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 667
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 669
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->onSignalStrength(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/SignalStrength;)V

    .line 670
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 671
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 672
    :cond_0
    monitor-exit v1

    .line 675
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 672
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "signalStrength":Lorg/codeaurora/internal/SignalStrength;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 673
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "signalStrength":Lorg/codeaurora/internal/SignalStrength;
    :catch_0
    move-exception v0

    .line 674
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "on5gStatus: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 676
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private onUpperLayerIndInfo(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/UpperLayerIndInfo;)V
    .locals 8
    .param p1, "slotId"    # I
    .param p2, "token"    # Lorg/codeaurora/internal/Token;
    .param p3, "status"    # Lorg/codeaurora/internal/Status;
    .param p4, "ulInfo"    # Lorg/codeaurora/internal/UpperLayerIndInfo;

    .line 613
    :try_start_0
    invoke-virtual {p2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v0

    .line 614
    .local v0, "tokenKey":I
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 615
    :try_start_1
    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->retrieveCallbacks(I)Ljava/util/ArrayList;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 616
    .local v3, "callback":Lorg/codeaurora/internal/INetworkCallback;
    const-string v4, "MainServiceImpl"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "onUpperLayerIndInfo: Responding back for transaction = "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 617
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v6, v7}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 616
    invoke-static {v4, v5}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 618
    invoke-interface {v3, p1, p2, p3, p4}, Lorg/codeaurora/internal/INetworkCallback;->onUpperLayerIndInfo(ILorg/codeaurora/internal/Token;Lorg/codeaurora/internal/Status;Lorg/codeaurora/internal/UpperLayerIndInfo;)V

    .line 619
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 620
    nop

    .end local v3    # "callback":Lorg/codeaurora/internal/INetworkCallback;
    goto :goto_0

    .line 621
    :cond_0
    monitor-exit v1

    .line 624
    .end local v0    # "tokenKey":I
    goto :goto_1

    .line 621
    .restart local v0    # "tokenKey":I
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .end local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .end local p1    # "slotId":I
    .end local p2    # "token":Lorg/codeaurora/internal/Token;
    .end local p3    # "status":Lorg/codeaurora/internal/Status;
    .end local p4    # "ulInfo":Lorg/codeaurora/internal/UpperLayerIndInfo;
    :try_start_2
    throw v2
    :try_end_2
    .catch Landroid/os/RemoteException; {:try_start_2 .. :try_end_2} :catch_0

    .line 622
    .end local v0    # "tokenKey":I
    .restart local p0    # "this":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;
    .restart local p1    # "slotId":I
    .restart local p2    # "token":Lorg/codeaurora/internal/Token;
    .restart local p3    # "status":Lorg/codeaurora/internal/Status;
    .restart local p4    # "ulInfo":Lorg/codeaurora/internal/UpperLayerIndInfo;
    :catch_0
    move-exception v0

    .line 623
    .local v0, "e":Landroid/os/RemoteException;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onUpperLayerIndInfo: Exception = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 625
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_1
    return-void
.end method

.method private removeCallback(Lorg/codeaurora/internal/INetworkCallback;)V
    .locals 6
    .param p1, "callback"    # Lorg/codeaurora/internal/INetworkCallback;

    .line 464
    invoke-interface {p1}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v0

    .line 465
    .local v0, "binder":Landroid/os/IBinder;
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "removeCallback: callback= "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, ", Binder = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "MainServiceImpl"

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 466
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v1

    .line 467
    :try_start_0
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v2}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lorg/codeaurora/internal/INetworkCallback;

    .line 468
    .local v3, "it":Lorg/codeaurora/internal/INetworkCallback;
    invoke-interface {v3}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/Object;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_0

    .line 469
    const-string v2, "MainServiceImpl"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "remove callback= "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 470
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v2, v3}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 471
    monitor-exit v1

    return-void

    .line 473
    .end local v3    # "it":Lorg/codeaurora/internal/INetworkCallback;
    :cond_0
    goto :goto_0

    .line 474
    :cond_1
    monitor-exit v1

    .line 475
    return-void

    .line 474
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v2
.end method

.method private removeClientFromInflightRequests(Lorg/codeaurora/internal/INetworkCallback;)V
    .locals 6
    .param p1, "callback"    # Lorg/codeaurora/internal/INetworkCallback;

    .line 478
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Ljava/util/concurrent/ConcurrentHashMap;->keySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    .line 479
    .local v1, "key":I
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    .line 480
    .local v2, "txn":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;
    iget-object v3, v2, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;->mClient:Lorg/codeaurora/internal/Client;

    invoke-virtual {v3}, Lorg/codeaurora/internal/Client;->getCallback()Lorg/codeaurora/internal/INetworkCallback;

    move-result-object v3

    invoke-interface {v3}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v3

    invoke-interface {p1}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v4

    if-ne v3, v4, :cond_0

    .line 481
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "removeClientFromInflightRequests: Token = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " => "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    .line 482
    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 481
    const-string v4, "MainServiceImpl"

    invoke-static {v4, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 483
    iget-object v3, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/util/concurrent/ConcurrentHashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 485
    .end local v1    # "key":I
    .end local v2    # "txn":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;
    :cond_0
    goto :goto_0

    .line 486
    :cond_1
    return-void
.end method


# virtual methods
.method public disable5g(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 346
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "disable5g: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 347
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 348
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 349
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "disable5g"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 350
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->disable5g(ILorg/codeaurora/internal/Token;)V

    .line 351
    return-object v0
.end method

.method public dump(Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    .locals 3
    .param p1, "fd"    # Ljava/io/FileDescriptor;
    .param p2, "printwriter"    # Ljava/io/PrintWriter;
    .param p3, "args"    # [Ljava/lang/String;

    .line 754
    move-object v0, p2

    .line 755
    .local v0, "pw":Ljava/io/PrintWriter;
    const-string v1, "5G-Middleware:"

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 756
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mHidlConnectionInterface = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 757
    const-string v1, "AIDL clients : "

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 758
    invoke-direct {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->dumpAidlClients(Ljava/io/PrintWriter;)V

    .line 759
    invoke-virtual {v0}, Ljava/io/PrintWriter;->flush()V

    .line 761
    const-string v1, "Inflight requests : "

    invoke-virtual {v0, v1}, Ljava/io/PrintWriter;->println(Ljava/lang/String;)V

    .line 762
    invoke-direct {p0, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->dumpInflightRequests(Ljava/io/PrintWriter;)V

    .line 763
    invoke-virtual {v0}, Ljava/io/PrintWriter;->flush()V

    .line 764
    return-void
.end method

.method public enable5g(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 7
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 334
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v0

    .line 335
    .local v0, "uid":I
    sget-object v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/content/pm/PackageManager;->getNameForUid(I)Ljava/lang/String;

    move-result-object v1

    .line 336
    .local v1, "packageName":Ljava/lang/String;
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "enable5g: slotId = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " uid = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v3, " package="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "MainServiceImpl"

    invoke-static {v3, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 338
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v2

    if-nez v2, :cond_0

    const/4 v2, 0x0

    return-object v2

    .line 339
    :cond_0
    iget-object v2, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v2}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v2

    .line 340
    .local v2, "token":Lorg/codeaurora/internal/Token;
    iget-object v3, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v2}, Lorg/codeaurora/internal/Token;->get()I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    new-instance v5, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v6, "Enable5g"

    invoke-direct {v5, p0, v2, v6, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v3, v4, v5}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 341
    iget-object v3, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v3, p1, v2}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->enable5g(ILorg/codeaurora/internal/Token;)V

    .line 342
    return-object v2
.end method

.method public enable5gOnly(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 2
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 355
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "enable5gOnly: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 356
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 357
    :cond_0
    new-instance v0, Landroid/os/RemoteException;

    const-string v1, "not implemented"

    invoke-direct {v0, v1}, Landroid/os/RemoteException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public enableEndc(IZLorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "enabled"    # Z
    .param p3, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 429
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "enableEndc: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 430
    invoke-direct {p0, p3}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 431
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 432
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "enableEndc"

    invoke-direct {v3, p0, v0, v4, p3}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 434
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, p2, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->enableEndc(IZLorg/codeaurora/internal/Token;)V

    .line 435
    return-object v0
.end method

.method public getAidlClientsCount()I
    .locals 2

    .line 728
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    monitor-enter v0

    .line 729
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    monitor-exit v0

    return v1

    .line 730
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method public getInflightRequestsCount()I
    .locals 1

    .line 734
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Ljava/util/concurrent/ConcurrentHashMap;->size()I

    move-result v0

    return v0
.end method

.method public query5gConfigInfo(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 409
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "query5gConfigInfo: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 410
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 411
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 412
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "query5gConfigInfo"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 414
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->query5gConfigInfo(ILorg/codeaurora/internal/Token;)V

    .line 415
    return-object v0
.end method

.method public query5gStatus(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 361
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "query5gStatus: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 362
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 363
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 364
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "query5gStatus"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 365
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->query5gStatus(ILorg/codeaurora/internal/Token;)V

    .line 366
    return-object v0
.end method

.method public queryEndcStatus(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 439
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "queryEndcStatus: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 440
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 441
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 442
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "queryEndcStatus"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 444
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->queryEndcStatus(ILorg/codeaurora/internal/Token;)V

    .line 445
    return-object v0
.end method

.method public queryNrBearerAllocation(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 379
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "queryNrBearerAllocation: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 380
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 381
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 382
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "queryNrBearerAllocation"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 384
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->queryNrBearerAllocation(ILorg/codeaurora/internal/Token;)V

    .line 385
    return-object v0
.end method

.method public queryNrDcParam(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 370
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "queryNrDcParam: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 371
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 372
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 373
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "queryNrDcParam"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 374
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->queryNrDcParam(ILorg/codeaurora/internal/Token;)V

    .line 375
    return-object v0
.end method

.method public queryNrIconType(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 419
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "queryNrIconType: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 420
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 421
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 422
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "queryNrIconType"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 424
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->queryNrIconType(ILorg/codeaurora/internal/Token;)V

    .line 425
    return-object v0
.end method

.method public queryNrSignalStrength(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 389
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "queryNrSignalStrength: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 390
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 391
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 392
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "queryNrSignalStrength"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 394
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->queryNrSignalStrength(ILorg/codeaurora/internal/Token;)V

    .line 395
    return-object v0
.end method

.method public queryUpperLayerIndInfo(ILorg/codeaurora/internal/Client;)Lorg/codeaurora/internal/Token;
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "client"    # Lorg/codeaurora/internal/Client;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 399
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "queryUpperLayerIndInfo: slotId = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "MainServiceImpl"

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 400
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 401
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->generateNextToken()Lorg/codeaurora/internal/Token;

    move-result-object v0

    .line 402
    .local v0, "token":Lorg/codeaurora/internal/Token;
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-virtual {v0}, Lorg/codeaurora/internal/Token;->get()I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    new-instance v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    const-string v4, "queryUpperLayerIndInfo"

    invoke-direct {v3, p0, v0, v4, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/Token;Ljava/lang/String;Lorg/codeaurora/internal/Client;)V

    invoke-virtual {v1, v2, v3}, Ljava/util/concurrent/ConcurrentHashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 404
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    invoke-interface {v1, p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->queryUpperLayerIndInfo(ILorg/codeaurora/internal/Token;)V

    .line 405
    return-object v0
.end method

.method public registerCallback(Ljava/lang/String;Lorg/codeaurora/internal/INetworkCallback;)Lorg/codeaurora/internal/Client;
    .locals 7
    .param p1, "packageName"    # Ljava/lang/String;
    .param p2, "callback"    # Lorg/codeaurora/internal/INetworkCallback;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 506
    const/4 v0, 0x0

    .line 507
    .local v0, "client":Lorg/codeaurora/internal/Client;
    invoke-interface {p2}, Lorg/codeaurora/internal/INetworkCallback;->asBinder()Landroid/os/IBinder;

    move-result-object v1

    .line 509
    .local v1, "binder":Landroid/os/IBinder;
    new-instance v2, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$ClientBinderDeathRecipient;

    invoke-direct {v2, p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$ClientBinderDeathRecipient;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Lorg/codeaurora/internal/INetworkCallback;)V

    const/4 v3, 0x0

    invoke-interface {v1, v2, v3}, Landroid/os/IBinder;->linkToDeath(Landroid/os/IBinder$DeathRecipient;I)V

    .line 511
    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v2

    .line 512
    .local v2, "uid":I
    sget-object v3, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mContext:Landroid/content/Context;

    invoke-virtual {v3}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v3

    invoke-virtual {v3, v2}, Landroid/content/pm/PackageManager;->getNameForUid(I)Ljava/lang/String;

    move-result-object v3

    .line 513
    .local v3, "callerPackageName":Ljava/lang/String;
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "registerCallback: uid = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, " callerPackage="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, "callback = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v5, "binder = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    const-string v5, "MainServiceImpl"

    invoke-static {v5, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 515
    iget-object v4, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mAppOpsManager:Landroid/app/AppOpsManager;

    invoke-static {}, Landroid/os/Binder;->getCallingUid()I

    move-result v6

    invoke-virtual {v4, v6, p1}, Landroid/app/AppOpsManager;->checkPackage(ILjava/lang/String;)V

    .line 517
    invoke-direct {p0, p2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->addCallback(Lorg/codeaurora/internal/INetworkCallback;)Z

    move-result v4

    iget-boolean v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->SUCCESS:Z

    if-ne v4, v6, :cond_0

    .line 518
    new-instance v4, Lorg/codeaurora/internal/Client;

    iget v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mClientIndex:I

    add-int/lit8 v6, v6, 0x1

    iput v6, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mClientIndex:I

    invoke-direct {v4, v6, v2, p1, p2}, Lorg/codeaurora/internal/Client;-><init>(IILjava/lang/String;Lorg/codeaurora/internal/INetworkCallback;)V

    move-object v0, v4

    .line 519
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "registerCallback: client = "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v5, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 522
    :cond_0
    const-string v4, "registerCallback: callback could not be added."

    invoke-static {v5, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 524
    :goto_0
    return-object v0
.end method

.method retrieveCallbacks(I)Ljava/util/ArrayList;
    .locals 5
    .param p1, "tokenKey"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/ArrayList<",
            "Lorg/codeaurora/internal/INetworkCallback;",
            ">;"
        }
    .end annotation

    .line 559
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 560
    .local v0, "list":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lorg/codeaurora/internal/INetworkCallback;>;"
    const/4 v1, -0x1

    if-eq p1, v1, :cond_1

    .line 561
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/concurrent/ConcurrentHashMap;->containsKey(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 562
    iget-object v1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mInflightRequests:Ljava/util/concurrent/ConcurrentHashMap;

    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/util/concurrent/ConcurrentHashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;

    .line 563
    .local v1, "txn":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;
    iget-object v2, v1, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;->mClient:Lorg/codeaurora/internal/Client;

    .line 564
    .local v2, "client":Lorg/codeaurora/internal/Client;
    invoke-direct {p0, v2}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->isClientValid(Lorg/codeaurora/internal/Client;)Z

    move-result v3

    if-eqz v3, :cond_0

    .line 565
    invoke-virtual {v2}, Lorg/codeaurora/internal/Client;->getCallback()Lorg/codeaurora/internal/INetworkCallback;

    move-result-object v3

    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_0

    .line 567
    :cond_0
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "This client is invalid now: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const-string v4, "MainServiceImpl"

    invoke-static {v4, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 569
    .end local v1    # "txn":Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$Transaction;
    .end local v2    # "client":Lorg/codeaurora/internal/Client;
    :goto_0
    goto :goto_1

    .line 571
    :cond_1
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mCallbackList:Ljava/util/ArrayList;

    .line 574
    :cond_2
    :goto_1
    return-object v0
.end method

.method public setHidlClient(Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;)V
    .locals 1
    .param p1, "hidlClient"    # Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    .line 328
    iput-object p1, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionInterface:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;

    .line 329
    iget-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mHidlConnectionCallback:Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionCallback;

    invoke-interface {p1, v0}, Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionInterface;->registerCallback(Lcom/qualcomm/qti/internal/nrNetworkService/hidl/IHidlConnectionCallback;)V

    .line 330
    return-void
.end method

.method public setLooper(Landroid/os/Looper;)V
    .locals 1
    .param p1, "workerLooper"    # Landroid/os/Looper;

    .line 324
    new-instance v0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$WorkerHandler;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl$WorkerHandler;-><init>(Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;Landroid/os/Looper;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->mWorkerThreadHandler:Landroid/os/Handler;

    .line 325
    return-void
.end method

.method public unRegisterCallback(Lorg/codeaurora/internal/INetworkCallback;)V
    .locals 0
    .param p1, "callback"    # Lorg/codeaurora/internal/INetworkCallback;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 528
    invoke-direct {p0, p1}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->removeCallback(Lorg/codeaurora/internal/INetworkCallback;)V

    .line 529
    invoke-direct {p0, p1}, Lcom/qualcomm/qti/internal/nrNetworkService/MainServiceImpl;->removeClientFromInflightRequests(Lorg/codeaurora/internal/INetworkCallback;)V

    .line 530
    return-void
.end method
