.class Lcom/qualcomm/qcrilhook/QmiOemHook$1;
.super Ljava/lang/Object;
.source "QmiOemHook.java"

# interfaces
.implements Lcom/qualcomm/qcrilhook/QcRilHookCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qcrilhook/QmiOemHook;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/qualcomm/qcrilhook/QmiOemHook;


# direct methods
.method constructor <init>(Lcom/qualcomm/qcrilhook/QmiOemHook;)V
    .locals 0
    .param p1, "this$0"    # Lcom/qualcomm/qcrilhook/QmiOemHook;

    .line 63
    iput-object p1, p0, Lcom/qualcomm/qcrilhook/QmiOemHook$1;->this$0:Lcom/qualcomm/qcrilhook/QmiOemHook;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public declared-synchronized onQcRilHookDisconnected()V
    .locals 3

    monitor-enter p0

    .line 72
    const/4 v0, 0x0

    :try_start_0
    invoke-static {v0}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$002(Z)Z

    .line 73
    new-instance v0, Lorg/codeaurora/telephony/utils/AsyncResult;

    invoke-static {}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$000()Z

    move-result v1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    const/4 v2, 0x0

    invoke-direct {v0, v2, v1, v2}, Lorg/codeaurora/telephony/utils/AsyncResult;-><init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Throwable;)V

    .line 74
    .local v0, "ar":Lorg/codeaurora/telephony/utils/AsyncResult;
    invoke-static {}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$100()Ljava/lang/String;

    move-result-object v1

    const-string v2, "onQcRilHookReadyCb: service disconnected; notifying registrants."

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 75
    invoke-static {}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$200()Lorg/codeaurora/telephony/utils/RegistrantList;

    move-result-object v1

    invoke-virtual {v1, v0}, Lorg/codeaurora/telephony/utils/RegistrantList;->notifyRegistrants(Lorg/codeaurora/telephony/utils/AsyncResult;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 76
    monitor-exit p0

    return-void

    .line 71
    .end local v0    # "ar":Lorg/codeaurora/telephony/utils/AsyncResult;
    .end local p0    # "this":Lcom/qualcomm/qcrilhook/QmiOemHook$1;
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public onQcRilHookReady()V
    .locals 3

    .line 65
    const/4 v0, 0x1

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$002(Z)Z

    .line 66
    new-instance v0, Lorg/codeaurora/telephony/utils/AsyncResult;

    invoke-static {}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$000()Z

    move-result v1

    invoke-static {v1}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    const/4 v2, 0x0

    invoke-direct {v0, v2, v1, v2}, Lorg/codeaurora/telephony/utils/AsyncResult;-><init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Throwable;)V

    .line 67
    .local v0, "ar":Lorg/codeaurora/telephony/utils/AsyncResult;
    invoke-static {}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$100()Ljava/lang/String;

    move-result-object v1

    const-string v2, "onQcRilHookReadyCb notifying registrants"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 68
    invoke-static {}, Lcom/qualcomm/qcrilhook/QmiOemHook;->access$200()Lorg/codeaurora/telephony/utils/RegistrantList;

    move-result-object v1

    invoke-virtual {v1, v0}, Lorg/codeaurora/telephony/utils/RegistrantList;->notifyRegistrants(Lorg/codeaurora/telephony/utils/AsyncResult;)V

    .line 69
    return-void
.end method
