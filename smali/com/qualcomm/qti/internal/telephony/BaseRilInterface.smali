.class public interface abstract Lcom/qualcomm/qti/internal/telephony/BaseRilInterface;
.super Ljava/lang/Object;
.source "BaseRilInterface.java"


# virtual methods
.method public abstract isServiceReady()Z
.end method

.method public abstract registerForServiceReadyEvent(Landroid/os/Handler;ILjava/lang/Object;)V
.end method

.method public abstract sendPhoneStatus(II)V
.end method

.method public abstract setLocalCallHold(IZ)Z
.end method

.method public abstract unRegisterForServiceReadyEvent(Landroid/os/Handler;)V
.end method
