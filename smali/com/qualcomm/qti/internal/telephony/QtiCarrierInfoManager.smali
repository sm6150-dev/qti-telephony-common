.class public Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager;
.super Lcom/android/internal/telephony/CarrierInfoManager;
.source "QtiCarrierInfoManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager$QtiCarrierInfoResponse;
    }
.end annotation


# static fields
.field private static final LOG_TAG:Ljava/lang/String; = "QtiCarrierInfoManager"


# instance fields
.field private final mPhone:Lcom/android/internal/telephony/Phone;


# direct methods
.method public constructor <init>(Lcom/android/internal/telephony/Phone;)V
    .locals 2
    .param p1, "phone"    # Lcom/android/internal/telephony/Phone;

    .line 33
    invoke-direct {p0}, Lcom/android/internal/telephony/CarrierInfoManager;-><init>()V

    .line 34
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager;->mPhone:Lcom/android/internal/telephony/Phone;

    .line 35
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/QtiTelephonyComponentFactory;->getInstance()Lcom/qualcomm/qti/internal/telephony/QtiTelephonyComponentFactory;

    move-result-object v0

    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager;->mPhone:Lcom/android/internal/telephony/Phone;

    invoke-virtual {v1}, Lcom/android/internal/telephony/Phone;->getPhoneId()I

    move-result v1

    invoke-virtual {v0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiTelephonyComponentFactory;->getRil(I)Lcom/qualcomm/qti/internal/telephony/QtiRIL;

    move-result-object v0

    new-instance v1, Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager$QtiCarrierInfoResponse;

    invoke-direct {v1, p0}, Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager$QtiCarrierInfoResponse;-><init>(Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager;)V

    .line 36
    invoke-virtual {v0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiRIL;->setImsiEncryptionResponseCallback(Lcom/qualcomm/qti/internal/telephony/QtiCarrierInfoManager$QtiCarrierInfoResponse;)V

    .line 37
    return-void
.end method
