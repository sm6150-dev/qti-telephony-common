.class Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;
.super Ljava/lang/Thread;
.source "ModemSarController.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qti/internal/telephony/ModemSarController;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "CmdProcThread"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread$CmdHandler;
    }
.end annotation


# instance fields
.field private mHandler:Landroid/os/Handler;


# direct methods
.method private constructor <init>()V
    .locals 1

    .line 464
    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    .line 465
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;->mHandler:Landroid/os/Handler;

    return-void
.end method

.method synthetic constructor <init>(Lcom/qualcomm/qti/internal/telephony/ModemSarController$1;)V
    .locals 0
    .param p1, "x0"    # Lcom/qualcomm/qti/internal/telephony/ModemSarController$1;

    .line 464
    invoke-direct {p0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;-><init>()V

    return-void
.end method


# virtual methods
.method public getCmdHandler()Landroid/os/Handler;
    .locals 2

    .line 468
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;->mHandler:Landroid/os/Handler;

    if-nez v0, :cond_0

    .line 469
    const-string v0, "ModemSarController"

    const-string v1, "getCmdHandler, handler is NULL!!!"

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 471
    :cond_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;->mHandler:Landroid/os/Handler;

    return-object v0
.end method

.method public run()V
    .locals 2

    .line 476
    invoke-static {}, Landroid/os/Looper;->prepare()V

    .line 477
    new-instance v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread$CmdHandler;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread$CmdHandler;-><init>(Lcom/qualcomm/qti/internal/telephony/ModemSarController$1;)V

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;->mHandler:Landroid/os/Handler;

    .line 478
    const-string v0, "CmdProcThread, thread is running up!!!!"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->access$300(Ljava/lang/String;)V

    .line 479
    invoke-static {}, Landroid/os/Looper;->loop()V

    .line 480
    return-void
.end method
