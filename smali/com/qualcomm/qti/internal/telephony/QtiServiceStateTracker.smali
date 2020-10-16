.class public Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;
.super Lcom/android/internal/telephony/vendor/VendorServiceStateTracker;
.source "QtiServiceStateTracker.java"


# static fields
.field private static final LOG_TAG:Ljava/lang/String; = "QtiServiceStateTracker"


# instance fields
.field private mIsImsCallingEnabled:Z


# direct methods
.method public constructor <init>(Lcom/android/internal/telephony/GsmCdmaPhone;Lcom/android/internal/telephony/CommandsInterface;)V
    .locals 1
    .param p1, "phone"    # Lcom/android/internal/telephony/GsmCdmaPhone;
    .param p2, "ci"    # Lcom/android/internal/telephony/CommandsInterface;

    .line 23
    invoke-direct {p0, p1, p2}, Lcom/android/internal/telephony/vendor/VendorServiceStateTracker;-><init>(Lcom/android/internal/telephony/GsmCdmaPhone;Lcom/android/internal/telephony/CommandsInterface;)V

    .line 24
    invoke-direct {p0}, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->isImsCallingEnabled()Z

    move-result v0

    iput-boolean v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mIsImsCallingEnabled:Z

    .line 25
    return-void
.end method

.method private isImsCallingEnabled()Z
    .locals 1

    .line 57
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    .line 58
    invoke-virtual {v0}, Lcom/android/internal/telephony/GsmCdmaPhone;->isVolteEnabled()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    invoke-virtual {v0}, Lcom/android/internal/telephony/GsmCdmaPhone;->isWifiCallingEnabled()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    .line 59
    invoke-virtual {v0}, Lcom/android/internal/telephony/GsmCdmaPhone;->isVideoEnabled()Z

    move-result v0

    if-eqz v0, :cond_1

    :cond_0
    const/4 v0, 0x1

    goto :goto_0

    :cond_1
    const/4 v0, 0x0

    .line 57
    :goto_0
    return v0
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 3
    .param p1, "msg"    # Landroid/os/Message;

    .line 29
    iget v0, p1, Landroid/os/Message;->what:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    .line 30
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    iget-object v0, v0, Lcom/android/internal/telephony/GsmCdmaPhone;->mCi:Lcom/android/internal/telephony/CommandsInterface;

    invoke-interface {v0}, Lcom/android/internal/telephony/CommandsInterface;->getRadioState()I

    move-result v0

    if-nez v0, :cond_0

    .line 31
    invoke-virtual {p0}, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->setPowerStateToDesired()V

    .line 32
    const-string v0, "Trigger as manual polling"

    invoke-virtual {p0, v0}, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->log(Ljava/lang/String;)V

    .line 33
    invoke-virtual {p0}, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->pollState()V

    goto :goto_0

    .line 35
    :cond_0
    invoke-super {p0, p1}, Lcom/android/internal/telephony/vendor/VendorServiceStateTracker;->handleMessage(Landroid/os/Message;)V

    goto :goto_0

    .line 37
    :cond_1
    iget v0, p1, Landroid/os/Message;->what:I

    const/16 v1, 0x30

    if-ne v0, v1, :cond_3

    .line 38
    invoke-super {p0, p1}, Lcom/android/internal/telephony/vendor/VendorServiceStateTracker;->handleMessage(Landroid/os/Message;)V

    .line 40
    iget-boolean v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mIsImsCallingEnabled:Z

    .line 41
    .local v0, "oldImsCallingEnabled":Z
    invoke-direct {p0}, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->isImsCallingEnabled()Z

    move-result v1

    iput-boolean v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mIsImsCallingEnabled:Z

    .line 43
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mSS:Landroid/telephony/ServiceState;

    invoke-virtual {v1}, Landroid/telephony/ServiceState;->getState()I

    move-result v1

    if-eqz v1, :cond_2

    iget-boolean v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mIsImsCallingEnabled:Z

    if-eq v0, v1, :cond_2

    .line 45
    const-string v1, "Notify service state as IMS caps will only affect the merged service state"

    invoke-virtual {p0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->log(Ljava/lang/String;)V

    .line 46
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/QtiServiceStateTracker;->mPhone:Lcom/android/internal/telephony/GsmCdmaPhone;

    invoke-virtual {v2}, Lcom/android/internal/telephony/GsmCdmaPhone;->getServiceState()Landroid/telephony/ServiceState;

    move-result-object v2

    invoke-virtual {v1, v2}, Lcom/android/internal/telephony/GsmCdmaPhone;->notifyServiceStateChanged(Landroid/telephony/ServiceState;)V

    .line 48
    .end local v0    # "oldImsCallingEnabled":Z
    :cond_2
    goto :goto_0

    .line 49
    :cond_3
    invoke-super {p0, p1}, Lcom/android/internal/telephony/vendor/VendorServiceStateTracker;->handleMessage(Landroid/os/Message;)V

    .line 51
    :goto_0
    return-void
.end method
