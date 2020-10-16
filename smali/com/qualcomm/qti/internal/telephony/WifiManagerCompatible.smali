.class public Lcom/qualcomm/qti/internal/telephony/WifiManagerCompatible;
.super Ljava/lang/Object;
.source "WifiManagerCompatible.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static isNeeded()Z
    .locals 1

    .line 15
    sget-boolean v0, Lmiui/os/Build;->IS_GLOBAL_BUILD:Z

    return v0
.end method

.method public static setSARLimit(Landroid/content/Context;I)V
    .locals 1
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "set"    # I

    .line 9
    const-string v0, "MiuiWifiService"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/MiuiWifiManager;

    .line 10
    .local v0, "mws":Landroid/net/wifi/MiuiWifiManager;
    if-eqz v0, :cond_0

    .line 11
    invoke-virtual {v0, p1}, Landroid/net/wifi/MiuiWifiManager;->setSARLimit(I)V

    .line 12
    :cond_0
    return-void
.end method
