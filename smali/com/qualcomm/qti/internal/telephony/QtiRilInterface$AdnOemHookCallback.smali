.class public Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;
.super Lcom/qualcomm/qcrilhook/OemHookCallback;
.source "QtiRilInterface.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4
    name = "AdnOemHookCallback"
.end annotation


# instance fields
.field mAppMessage:Landroid/os/Message;

.field mRspLength:I

.field final synthetic this$0:Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;


# direct methods
.method public constructor <init>(Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;Landroid/os/Message;I)V
    .locals 0
    .param p1, "this$0"    # Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;
    .param p2, "msg"    # Landroid/os/Message;
    .param p3, "length"    # I

    .line 286
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;

    .line 287
    invoke-direct {p0, p2}, Lcom/qualcomm/qcrilhook/OemHookCallback;-><init>(Landroid/os/Message;)V

    .line 288
    iput-object p2, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mAppMessage:Landroid/os/Message;

    .line 289
    iput p3, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mRspLength:I

    .line 290
    return-void
.end method


# virtual methods
.method public onOemHookException(I)V
    .locals 3
    .param p1, "phoneId"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 308
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;

    const-string v1, "AdnOemHookCallback: onOemHookException"

    invoke-static {v0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;->access$100(Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;Ljava/lang/String;)V

    .line 310
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mAppMessage:Landroid/os/Message;

    new-instance v1, Ljava/lang/Exception;

    const-string v2, "com.android.internal.telephony.CommandException: MODEM_ERR"

    invoke-direct {v1, v2}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    const/4 v2, 0x0

    invoke-static {v0, v2, v1}, Landroid/os/AsyncResult;->forMessage(Landroid/os/Message;Ljava/lang/Object;Ljava/lang/Throwable;)Landroid/os/AsyncResult;

    .line 312
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mAppMessage:Landroid/os/Message;

    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 313
    return-void
.end method

.method public onOemHookResponse([BI)V
    .locals 4
    .param p1, "response"    # [B
    .param p2, "phoneId"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 294
    const/4 v0, 0x0

    if-eqz p1, :cond_0

    .line 295
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "AdnOemHookCallback: onOemHookResponse = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;->access$100(Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;Ljava/lang/String;)V

    .line 297
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;

    iget v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mRspLength:I

    invoke-static {v1, p1, v2}, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;->access$400(Lcom/qualcomm/qti/internal/telephony/QtiRilInterface;[BI)[I

    move-result-object v1

    .line 298
    .local v1, "values":[I
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mAppMessage:Landroid/os/Message;

    invoke-static {v2, v1, v0}, Landroid/os/AsyncResult;->forMessage(Landroid/os/Message;Ljava/lang/Object;Ljava/lang/Throwable;)Landroid/os/AsyncResult;

    .line 299
    .end local v1    # "values":[I
    goto :goto_0

    .line 300
    :cond_0
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mAppMessage:Landroid/os/Message;

    new-instance v2, Ljava/lang/Exception;

    const-string v3, "QCRIL_EVT_HOOK_GET_ADN_RECORD failed"

    invoke-direct {v2, v3}, Ljava/lang/Exception;-><init>(Ljava/lang/String;)V

    invoke-static {v1, v0, v2}, Landroid/os/AsyncResult;->forMessage(Landroid/os/Message;Ljava/lang/Object;Ljava/lang/Throwable;)Landroid/os/AsyncResult;

    .line 303
    :goto_0
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiRilInterface$AdnOemHookCallback;->mAppMessage:Landroid/os/Message;

    invoke-virtual {v0}, Landroid/os/Message;->sendToTarget()V

    .line 304
    return-void
.end method
