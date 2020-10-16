.class public Lcom/qualcomm/qti/internal/telephony/dataconnection/QtiDataResetEventTracker;
.super Lcom/android/internal/telephony/vendor/dataconnection/VendorDataResetEventTracker;
.source "QtiDataResetEventTracker.java"


# direct methods
.method public constructor <init>(ILcom/android/internal/telephony/Phone;Lcom/android/internal/telephony/vendor/dataconnection/VendorDataResetEventTracker$ResetEventListener;)V
    .locals 0
    .param p1, "transportType"    # I
    .param p2, "phone"    # Lcom/android/internal/telephony/Phone;
    .param p3, "listener"    # Lcom/android/internal/telephony/vendor/dataconnection/VendorDataResetEventTracker$ResetEventListener;

    .line 15
    invoke-direct {p0, p1, p2, p3}, Lcom/android/internal/telephony/vendor/dataconnection/VendorDataResetEventTracker;-><init>(ILcom/android/internal/telephony/Phone;Lcom/android/internal/telephony/vendor/dataconnection/VendorDataResetEventTracker$ResetEventListener;)V

    .line 16
    return-void
.end method
