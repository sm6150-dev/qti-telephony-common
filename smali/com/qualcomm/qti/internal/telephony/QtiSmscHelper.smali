.class public Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;
.super Landroid/os/Handler;
.source "QtiSmscHelper.java"


# static fields
.field private static final EVENT_GET_SMSC:I = 0x2

.field private static final EVENT_SET_SMSC:I = 0x1

.field private static final LOG_TAG:Ljava/lang/String; = "QtiSmscHelper"

.field private static PHONE_COUNT:I


# instance fields
.field private mGetLock:Ljava/lang/Object;

.field private final mPhones:[Lcom/android/internal/telephony/Phone;

.field private mSetLock:Ljava/lang/Object;

.field private final mSmscArray:Ljava/util/concurrent/atomic/AtomicReferenceArray;

.field private volatile mSuccess:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;

    .line 37
    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-direct {p0, v0}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    .line 29
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSetLock:Ljava/lang/Object;

    .line 30
    new-instance v0, Ljava/lang/Object;

    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mGetLock:Ljava/lang/Object;

    .line 32
    invoke-static {}, Lcom/android/internal/telephony/PhoneFactory;->getPhones()[Lcom/android/internal/telephony/Phone;

    move-result-object v0

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mPhones:[Lcom/android/internal/telephony/Phone;

    .line 34
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSuccess:Z

    .line 39
    const-string v0, "phone"

    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    .line 40
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getPhoneCount()I

    move-result v0

    sput v0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->PHONE_COUNT:I

    .line 41
    new-instance v0, Ljava/util/concurrent/atomic/AtomicReferenceArray;

    sget v1, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->PHONE_COUNT:I

    new-array v1, v1, [Ljava/lang/String;

    invoke-direct {v0, v1}, Ljava/util/concurrent/atomic/AtomicReferenceArray;-><init>([Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSmscArray:Ljava/util/concurrent/atomic/AtomicReferenceArray;

    .line 42
    return-void
.end method

.method private isValidPhoneId(I)Z
    .locals 1
    .param p1, "slotId"    # I

    .line 106
    if-ltz p1, :cond_0

    sget v0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->PHONE_COUNT:I

    if-ge p1, v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method


# virtual methods
.method public getSmscAddress(I)Ljava/lang/String;
    .locals 4
    .param p1, "slotId"    # I

    .line 89
    invoke-direct {p0, p1}, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->isValidPhoneId(I)Z

    move-result v0

    if-nez v0, :cond_0

    .line 90
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Invalid phone id = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "QtiSmscHelper"

    invoke-static {v1, v0}, Landroid/telephony/Rlog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 91
    const/4 v0, 0x0

    return-object v0

    .line 93
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mGetLock:Ljava/lang/Object;

    monitor-enter v0

    .line 94
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mPhones:[Lcom/android/internal/telephony/Phone;

    aget-object v1, v1, p1

    const/4 v2, 0x2

    const/4 v3, -0x1

    invoke-virtual {p0, v2, p1, v3}, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->obtainMessage(III)Landroid/os/Message;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/android/internal/telephony/Phone;->getSmscAddress(Landroid/os/Message;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 97
    :try_start_1
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mGetLock:Ljava/lang/Object;

    invoke-virtual {v1}, Ljava/lang/Object;->wait()V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 100
    goto :goto_0

    .line 98
    :catch_0
    move-exception v1

    .line 101
    :goto_0
    :try_start_2
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSmscArray:Ljava/util/concurrent/atomic/AtomicReferenceArray;

    invoke-virtual {v1, p1}, Ljava/util/concurrent/atomic/AtomicReferenceArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    monitor-exit v0

    return-object v1

    .line 102
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v1
.end method

.method public handleMessage(Landroid/os/Message;)V
    .locals 5
    .param p1, "msg"    # Landroid/os/Message;

    .line 45
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Landroid/os/AsyncResult;

    .line 46
    .local v0, "ar":Landroid/os/AsyncResult;
    iget v1, p1, Landroid/os/Message;->what:I

    const/4 v2, 0x1

    if-eq v1, v2, :cond_2

    const/4 v2, 0x2

    if-eq v1, v2, :cond_0

    goto :goto_1

    .line 48
    :cond_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mGetLock:Ljava/lang/Object;

    monitor-enter v1

    .line 49
    if-eqz v0, :cond_1

    :try_start_0
    iget-object v2, v0, Landroid/os/AsyncResult;->exception:Ljava/lang/Throwable;

    if-nez v2, :cond_1

    .line 50
    const-string v2, "QtiSmscHelper"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "smsc = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v4, v0, Landroid/os/AsyncResult;->result:Ljava/lang/Object;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v4, " on phone = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p1, Landroid/os/Message;->arg1:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/telephony/Rlog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 51
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSmscArray:Ljava/util/concurrent/atomic/AtomicReferenceArray;

    iget v3, p1, Landroid/os/Message;->arg1:I

    iget-object v4, v0, Landroid/os/AsyncResult;->result:Ljava/lang/Object;

    invoke-virtual {v2, v3, v4}, Ljava/util/concurrent/atomic/AtomicReferenceArray;->set(ILjava/lang/Object;)V

    .line 53
    :cond_1
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mGetLock:Ljava/lang/Object;

    invoke-virtual {v2}, Ljava/lang/Object;->notifyAll()V

    .line 54
    monitor-exit v1

    .line 55
    goto :goto_1

    .line 54
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v2

    .line 57
    :cond_2
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSetLock:Ljava/lang/Object;

    monitor-enter v1

    .line 58
    if-eqz v0, :cond_3

    :try_start_1
    iget-object v3, v0, Landroid/os/AsyncResult;->exception:Ljava/lang/Throwable;

    if-nez v3, :cond_3

    goto :goto_0

    .line 60
    :catchall_1
    move-exception v2

    goto :goto_2

    .line 58
    :cond_3
    const/4 v2, 0x0

    :goto_0
    iput-boolean v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSuccess:Z

    .line 59
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSetLock:Ljava/lang/Object;

    invoke-virtual {v2}, Ljava/lang/Object;->notifyAll()V

    .line 60
    monitor-exit v1

    .line 63
    :goto_1
    return-void

    .line 60
    :goto_2
    monitor-exit v1
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    throw v2
.end method

.method public setSmscAddress(ILjava/lang/String;)Z
    .locals 5
    .param p1, "slotId"    # I
    .param p2, "smsc"    # Ljava/lang/String;

    .line 66
    invoke-direct {p0, p1}, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->isValidPhoneId(I)Z

    move-result v0

    if-nez v0, :cond_0

    .line 67
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Invalid phone id = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "QtiSmscHelper"

    invoke-static {v1, v0}, Landroid/telephony/Rlog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 68
    const/4 v0, 0x0

    return v0

    .line 70
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSetLock:Ljava/lang/Object;

    monitor-enter v0

    .line 71
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSmscArray:Ljava/util/concurrent/atomic/AtomicReferenceArray;

    invoke-virtual {v1, p1}, Ljava/util/concurrent/atomic/AtomicReferenceArray;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    invoke-static {v1, p2}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v1

    const/4 v2, 0x1

    if-eqz v1, :cond_1

    .line 72
    const-string v1, "QtiSmscHelper"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "the same smsc is there on phone = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v1, v3}, Landroid/telephony/Rlog;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 73
    monitor-exit v0

    return v2

    .line 75
    :cond_1
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mPhones:[Lcom/android/internal/telephony/Phone;

    aget-object v1, v1, p1

    const/4 v3, -0x1

    invoke-virtual {p0, v2, p1, v3}, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->obtainMessage(III)Landroid/os/Message;

    move-result-object v2

    invoke-virtual {v1, p2, v2}, Lcom/android/internal/telephony/Phone;->setSmscAddress(Ljava/lang/String;Landroid/os/Message;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 78
    :try_start_1
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSetLock:Ljava/lang/Object;

    invoke-virtual {v1}, Ljava/lang/Object;->wait()V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 81
    goto :goto_0

    .line 79
    :catch_0
    move-exception v1

    .line 83
    :goto_0
    :try_start_2
    iget-boolean v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSuccess:Z

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSmscArray:Ljava/util/concurrent/atomic/AtomicReferenceArray;

    invoke-virtual {v1, p1, p2}, Ljava/util/concurrent/atomic/AtomicReferenceArray;->set(ILjava/lang/Object;)V

    .line 84
    :cond_2
    iget-boolean v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiSmscHelper;->mSuccess:Z

    monitor-exit v0

    return v1

    .line 85
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    throw v1
.end method
