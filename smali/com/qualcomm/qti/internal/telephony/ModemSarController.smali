.class public Lcom/qualcomm/qti/internal/telephony/ModemSarController;
.super Ljava/lang/Object;
.source "ModemSarController.java"

# interfaces
.implements Lcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;
    }
.end annotation


# static fields
.field private static CHARGEState:I = 0x0

.field private static final DEVICE_INFO_HW_CN:Ljava/lang/String; = "CN"

.field private static final DEVICE_INFO_HW_GLOBAL:Ljava/lang/String; = "GLOBAL"

.field private static final DEVICE_INFO_HW_INDIA:Ljava/lang/String; = "INDIA"

.field private static final DEVICE_INFO_HW_LEVEL_P0:Ljava/lang/String; = "P0"

.field private static final DEVICE_INFO_HW_LEVEL_P1:Ljava/lang/String; = "P1"

.field private static final DEVICE_INFO_HW_LEVEL_P2:Ljava/lang/String; = "P2"

.field private static final DEVICE_INFO_SW_GLOBAL:Ljava/lang/String; = "global"

.field private static final DEVICE_INFO_SW_INDIA:Ljava/lang/String; = "in_global"

.field private static final DEVICE_TYPE_F10:Ljava/lang/String; = "davinci"

.field private static final DEVICE_TYPE_GRUS:Ljava/lang/String; = "grus"

.field private static final DEVICE_TYPE_PHOENIXIN:Ljava/lang/String; = "phoenixin"

.field private static final DEVICE_TYPE_TOCO:Ljava/lang/String; = "toco"

.field private static final DEVICE_TYPE_TUCANA:Ljava/lang/String; = "tucana"

.field private static final DSI_0:I = 0x0

.field private static final DSI_1:I = 0x1

.field private static final DSI_2:I = 0x2

.field private static final DSI_3:I = 0x3

.field private static final DSI_4:I = 0x4

.field private static final DSI_5:I = 0x5

.field private static final DSI_6:I = 0x6

.field private static final DSI_7:I = 0x7

.field private static final DSI_8:I = 0x8

.field private static DSI_Current:I = 0x0

.field private static DSI_Hash:Ljava/util/HashMap; = null
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field private static DSI_History:I = 0x0

.field private static final EVENT_CHARGE:I = 0x5

.field private static final EVENT_Hotspot:I = 0x3

.field private static final EVENT_Receiver:I = 0x2

.field private static final EVENT_SAR_SENSOR:I = 0x1

.field private static final EVENT_WIFI:I = 0x4

.field private static HotspotState:I = 0x0

.field private static final HotspotStateNum:I = 0x2

.field public static final LOG_TAG:Ljava/lang/String; = "ModemSarController"

.field private static final PROPERTY_CHARGE_SAR_CONTROL:Ljava/lang/String; = "persist.radio.charge_sar_control"

.field private static final PROPERTY_DEVICE_INFO_HW:Ljava/lang/String; = "ro.boot.hwc"

.field private static final PROPERTY_DEVICE_INFO_HW_LEVEL:Ljava/lang/String; = "ro.boot.hwlevel"

.field private static final PROPERTY_DEVICE_INFO_SW:Ljava/lang/String; = "ro.product.mod_device"

.field private static final PROPERTY_DEVICE_NAME:Ljava/lang/String; = "ro.product.device"

.field private static final PROPERTY_DYNAMIC_SAR_MAIN_CONTROL:Ljava/lang/String; = "persist.vendor.radio.dynamic_sar"

.field private static final PROPERTY_DYNAMIC_SAR_TEST_CONTROL:Ljava/lang/String; = "persist.radio.sar_test"

.field private static ReceiverState:I = 0x0

.field private static final ReceiverStateNum:I = 0x2

.field private static SarSensorState:I = 0x0

.field private static WIFIState:I = 0x0

.field private static final WIFIStateNum:I = 0x2

.field private static mCHARGESAREnabled:Z

.field private static mDeviceHW:Ljava/lang/String;

.field private static mDeviceName:Ljava/lang/String;

.field private static mDeviceSW:Ljava/lang/String;

.field private static mDynmaicSarMainEnabled:Z

.field private static mDynmaicSarTestEnabled:Z

.field private static mQcRilHook:Lcom/qualcomm/qcrilhook/QcRilHook;

.field private static mQcRilHookReady:Z

.field private static mQcrilHookCb:Lcom/qualcomm/qcrilhook/QcRilHookCallback;

.field private static mSarSensorEnabled:Z

.field private static sIntance:Lcom/qualcomm/qti/internal/telephony/ModemSarController;


# instance fields
.field mCmdProc:Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;

.field private mContext:Landroid/content/Context;

.field private mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 79
    const/4 v0, 0x0

    sput v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->SarSensorState:I

    .line 80
    sput v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->ReceiverState:I

    .line 81
    sput v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->HotspotState:I

    .line 82
    sput v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->WIFIState:I

    .line 83
    sput v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->CHARGEState:I

    .line 85
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    .line 87
    sput v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_History:I

    .line 96
    const/4 v1, 0x0

    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    .line 97
    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    .line 98
    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    .line 102
    sput-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarTestEnabled:Z

    .line 103
    sput-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarMainEnabled:Z

    .line 104
    const/4 v1, 0x1

    sput-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    .line 105
    sput-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCHARGESAREnabled:Z

    .line 109
    new-instance v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController$1;

    invoke-direct {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController$1;-><init>()V

    sput-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mQcrilHookCb:Lcom/qualcomm/qcrilhook/QcRilHookCallback;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .locals 4
    .param p1, "context"    # Landroid/content/Context;

    .line 125
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 95
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCmdProc:Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;

    .line 126
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mContext:Landroid/content/Context;

    .line 129
    const-string v1, "ro.product.device"

    invoke-static {v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    .line 130
    const-string v1, "ro.boot.hwc"

    invoke-static {v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    .line 131
    const-string v1, "ro.product.mod_device"

    invoke-static {v1}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    .line 132
    const-string v1, "persist.radio.sar_test"

    const/4 v2, 0x0

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    sput-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarTestEnabled:Z

    .line 133
    const-string v1, "persist.vendor.radio.dynamic_sar"

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    sput-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarMainEnabled:Z

    .line 134
    const-string v1, "persist.radio.charge_sar_control"

    invoke-static {v1, v2}, Landroid/os/SystemProperties;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    sput-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCHARGESAREnabled:Z

    .line 135
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Device name = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, ", DeviceHW = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, ", DeviceSW = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, ", mDynmaicSarTestEnabled = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-boolean v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarTestEnabled:Z

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, ", mDynmaicSarMainEnabled = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-boolean v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarMainEnabled:Z

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, ", mSarSensorEnabled = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-boolean v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v3, ", mCHARGESAREnabled = "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-boolean v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCHARGESAREnabled:Z

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 138
    sget-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarTestEnabled:Z

    const/4 v3, 0x1

    if-nez v1, :cond_1

    .line 140
    sget-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDynmaicSarMainEnabled:Z

    if-nez v1, :cond_0

    .line 141
    const-string v0, "Sar main switch is not started, ModemSarController do nothing"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 142
    return-void

    .line 146
    :cond_0
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->onInitSarProdcutParameter()Z

    move-result v1

    if-eq v3, v1, :cond_2

    .line 147
    const-string v0, "wil not start dynamic sar service"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 148
    return-void

    .line 153
    :cond_1
    const-string v1, "Warning, Sar Test switch is started."

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 154
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    .line 159
    :cond_2
    invoke-static {p1}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->getInstance(Landroid/content/Context;)Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    move-result-object v1

    iput-object v1, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    .line 160
    if-nez v1, :cond_3

    .line 161
    const-string v0, "DynamicSarService start failed"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 162
    return-void

    .line 166
    :cond_3
    new-instance v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;

    invoke-direct {v1, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;-><init>(Lcom/qualcomm/qti/internal/telephony/ModemSarController$1;)V

    iput-object v1, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCmdProc:Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;

    .line 167
    if-nez v1, :cond_4

    .line 168
    const-string v0, "mCmdProc is null, CmdProcThread init failed"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 169
    return-void

    .line 173
    :cond_4
    new-instance v0, Lcom/qualcomm/qcrilhook/QcRilHook;

    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mQcrilHookCb:Lcom/qualcomm/qcrilhook/QcRilHookCallback;

    invoke-direct {v0, p1, v1}, Lcom/qualcomm/qcrilhook/QcRilHook;-><init>(Landroid/content/Context;Lcom/qualcomm/qcrilhook/QcRilHookCallback;)V

    sput-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mQcRilHook:Lcom/qualcomm/qcrilhook/QcRilHook;

    .line 174
    if-nez v0, :cond_5

    .line 175
    const-string v0, "QcRilHook start failed"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 176
    return-void

    .line 180
    :cond_5
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCmdProc:Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;

    invoke-virtual {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;->start()V

    .line 183
    sget-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    if-eqz v0, :cond_6

    .line 184
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    const/4 v1, 0x3

    invoke-virtual {v0, v1, p0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->registerStateChangeListener(ILcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;)V

    .line 187
    :cond_6
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    const/4 v1, 0x5

    invoke-virtual {v0, v1, p0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->registerStateChangeListener(ILcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;)V

    .line 188
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-virtual {v0, v3, p0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->registerStateChangeListener(ILcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;)V

    .line 189
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-virtual {v0, v2, p0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->registerStateChangeListener(ILcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;)V

    .line 191
    sget-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCHARGESAREnabled:Z

    if-eqz v0, :cond_7

    .line 192
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    const/4 v1, 0x6

    invoke-virtual {v0, v1, p0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->registerStateChangeListener(ILcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;)V

    .line 193
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mService:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    const/4 v1, 0x7

    invoke-virtual {v0, v1, p0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->registerStateChangeListener(ILcom/qualcomm/qti/internal/telephony/DynamicSarService$SarControllerClient;)V

    .line 196
    :cond_7
    return-void
.end method

.method private static DSI_Hash_Init_Common_Cfg()V
    .locals 7

    .line 406
    const-string v0, "DSI_Hash_Init_Common_Cfg"

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 412
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-static {v1, v1, v1, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v3

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v0, v3, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 413
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    const/4 v3, 0x1

    .line 416
    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    .line 413
    invoke-static {v1, v1, v1, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v5

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v5, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 416
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    const/4 v2, 0x2

    .line 421
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    .line 416
    invoke-static {v2, v3, v1, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v6

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-virtual {v0, v6, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 417
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v3, v1, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v6

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-virtual {v0, v6, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 418
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v1, v3, v1, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v6

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    invoke-virtual {v0, v6, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 421
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v3, v1, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 422
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v3, v1, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 423
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v1, v3, v1, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 426
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v1, v1, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x3

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 429
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v1, v1, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x4

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 432
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v1, v1, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x5

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 435
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v1, v1, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x6

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 438
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v1, v3, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x7

    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 439
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v1, v3, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 440
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v3, v3, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v4

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    invoke-virtual {v0, v4, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 441
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v2, v3, v3, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 442
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v1, v3, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 443
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v1, v3, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 444
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v3, v3, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 445
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v3, v3, v3, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 446
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v1, v1, v3, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 447
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v1, v1, v3, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 448
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v1, v3, v3, v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v2

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {v0, v2, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 449
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    invoke-static {v1, v3, v3, v3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v1

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1, v5}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 451
    return-void
.end method

.method private static DSI_Hash_Key_Convert(IIII)I
    .locals 2
    .param p0, "sarsensorstate"    # I
    .param p1, "receiverstate"    # I
    .param p2, "hotspotstate"    # I
    .param p3, "wifistate"    # I

    .line 397
    mul-int/lit8 v0, p0, 0x8

    .line 398
    .local v0, "hash_key":I
    mul-int/lit8 v1, p1, 0x4

    add-int/2addr v0, v1

    .line 399
    mul-int/lit8 v1, p2, 0x2

    add-int/2addr v0, v1

    .line 400
    add-int/2addr v0, p3

    .line 401
    return v0
.end method

.method static synthetic access$000()Z
    .locals 1

    .line 33
    sget-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mQcRilHookReady:Z

    return v0
.end method

.method static synthetic access$002(Z)Z
    .locals 0
    .param p0, "x0"    # Z

    .line 33
    sput-boolean p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mQcRilHookReady:Z

    return p0
.end method

.method static synthetic access$1000()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_History:I

    return v0
.end method

.method static synthetic access$1002(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_History:I

    return p0
.end method

.method static synthetic access$1100()Z
    .locals 1

    .line 33
    sget-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCHARGESAREnabled:Z

    return v0
.end method

.method static synthetic access$1200()Ljava/lang/String;
    .locals 1

    .line 33
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1300()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Current:I

    return v0
.end method

.method static synthetic access$1302(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Current:I

    return p0
.end method

.method static synthetic access$1400(IIII)I
    .locals 1
    .param p0, "x0"    # I
    .param p1, "x1"    # I
    .param p2, "x2"    # I
    .param p3, "x3"    # I

    .line 33
    invoke-static {p0, p1, p2, p3}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Key_Convert(IIII)I

    move-result v0

    return v0
.end method

.method static synthetic access$1500()Ljava/util/HashMap;
    .locals 1

    .line 33
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash:Ljava/util/HashMap;

    return-object v0
.end method

.method static synthetic access$1600()Z
    .locals 1

    .line 33
    sget-boolean v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    return v0
.end method

.method static synthetic access$300(Ljava/lang/String;)V
    .locals 0
    .param p0, "x0"    # Ljava/lang/String;

    .line 33
    invoke-static {p0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$400()Lcom/qualcomm/qcrilhook/QcRilHook;
    .locals 1

    .line 33
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mQcRilHook:Lcom/qualcomm/qcrilhook/QcRilHook;

    return-object v0
.end method

.method static synthetic access$500()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->SarSensorState:I

    return v0
.end method

.method static synthetic access$502(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->SarSensorState:I

    return p0
.end method

.method static synthetic access$600()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->ReceiverState:I

    return v0
.end method

.method static synthetic access$602(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->ReceiverState:I

    return p0
.end method

.method static synthetic access$700()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->HotspotState:I

    return v0
.end method

.method static synthetic access$702(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->HotspotState:I

    return p0
.end method

.method static synthetic access$800()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->WIFIState:I

    return v0
.end method

.method static synthetic access$802(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->WIFIState:I

    return p0
.end method

.method static synthetic access$900()I
    .locals 1

    .line 33
    sget v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->CHARGEState:I

    return v0
.end method

.method static synthetic access$902(I)I
    .locals 0
    .param p0, "x0"    # I

    .line 33
    sput p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->CHARGEState:I

    return p0
.end method

.method private static final log(Ljava/lang/String;)V
    .locals 1
    .param p0, "s"    # Ljava/lang/String;

    .line 461
    const-string v0, "ModemSarController"

    invoke-static {v0, p0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 462
    return-void
.end method

.method public static make(Landroid/content/Context;)V
    .locals 1
    .param p0, "context"    # Landroid/content/Context;

    .line 120
    sget-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sIntance:Lcom/qualcomm/qti/internal/telephony/ModemSarController;

    if-nez v0, :cond_0

    .line 121
    new-instance v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;

    invoke-direct {v0, p0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;-><init>(Landroid/content/Context;)V

    sput-object v0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sIntance:Lcom/qualcomm/qti/internal/telephony/ModemSarController;

    .line 123
    :cond_0
    return-void
.end method

.method private static onInitSarParameterF10()Z
    .locals 5

    .line 244
    const/4 v0, 0x1

    .line 245
    .local v0, "sarFlag":Z
    const-string v1, "P2"

    .line 247
    .local v1, "hwLevel":Ljava/lang/String;
    const-string v2, "onInitSarParameterF10"

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 249
    sget-object v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const/4 v3, 0x0

    if-nez v2, :cond_0

    .line 250
    const-string v2, "mDeviceSW is null"

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 251
    return v3

    .line 254
    :cond_0
    sget-object v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    if-nez v2, :cond_1

    .line 255
    const-string v2, "mDeviceHW is null"

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 256
    return v3

    .line 259
    :cond_1
    const-string v2, "ro.boot.hwlevel"

    invoke-static {v2}, Landroid/os/SystemProperties;->get(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 260
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "hwLevel: "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 262
    const-string v2, "P0"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_5

    const-string v2, "P1"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_2

    goto :goto_0

    .line 267
    :cond_2
    sget-object v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const-string v4, "global"

    invoke-virtual {v2, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_3

    sget-object v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v4, "GLOBAL"

    invoke-virtual {v2, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 269
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    goto :goto_1

    .line 271
    :cond_3
    sget-object v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const-string v4, "in_global"

    invoke-virtual {v2, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_4

    sget-object v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v4, "INDIA"

    invoke-virtual {v2, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 273
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    .line 274
    sput-boolean v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    .line 275
    const-string v2, "India version not supprot sar sensor, will not listen it"

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    goto :goto_1

    .line 279
    :cond_4
    const-string v2, "sw and hw info is mismatch or not correct, pls check your sw and hw info"

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 280
    const/4 v0, 0x0

    goto :goto_1

    .line 264
    :cond_5
    :goto_0
    const-string v2, "RFNV about sar is not valid in P0 and P1, pls use test switch to open sar after you are sure RFNV of sar is good"

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 265
    const/4 v0, 0x0

    .line 284
    :goto_1
    return v0
.end method

.method private static onInitSarParameterF2()Z
    .locals 3

    .line 223
    const/4 v0, 0x1

    .line 225
    .local v0, "sarFlag":Z
    const-string v1, "onInitSarParameterF2"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 227
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v2, "CN"

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_0

    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const-string v2, "global"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 229
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    goto :goto_0

    .line 233
    :cond_0
    const/4 v0, 0x0

    .line 234
    const-string v1, "not support this version"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 238
    :goto_0
    return v0
.end method

.method private static onInitSarParameterF4()Z
    .locals 3

    .line 323
    const/4 v0, 0x1

    .line 325
    .local v0, "sarFlag":Z
    const-string v1, "onInitSarParameterF4"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 327
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const-string v2, "global"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v2, "GLOBAL"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 329
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    goto :goto_0

    .line 333
    :cond_0
    const/4 v0, 0x0

    .line 334
    const-string v1, "not support this version"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 338
    :goto_0
    return v0
.end method

.method private static onInitSarParameterF4L()Z
    .locals 3

    .line 344
    const/4 v0, 0x1

    .line 346
    .local v0, "sarFlag":Z
    const-string v1, "onInitSarParameterF4L"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 348
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const-string v2, "global"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v2, "GLOBAL"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 350
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    .line 351
    const-string v1, "Global version diaable sar sensor"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    goto :goto_0

    .line 353
    :cond_0
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const-string v2, "in_global"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v2, "INDIA"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 355
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    .line 356
    const/4 v1, 0x0

    sput-boolean v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    .line 357
    const-string v1, "India version not supprot sar sensor, will not listen it"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    goto :goto_0

    .line 361
    :cond_1
    const/4 v0, 0x0

    .line 362
    const-string v1, "not support this version"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 366
    :goto_0
    return v0
.end method

.method private static onInitSarParameterG7B()Z
    .locals 4

    .line 290
    const/4 v0, 0x1

    .line 292
    .local v0, "sarFlag":Z
    const-string v1, "onInitSarParameterG7B"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 294
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceSW:Ljava/lang/String;

    const/4 v2, 0x0

    if-nez v1, :cond_0

    .line 295
    const-string v1, "mDeviceSW is null"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 296
    return v2

    .line 299
    :cond_0
    sget-object v3, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    if-nez v3, :cond_1

    .line 300
    const-string v1, "mDeviceHW is null"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 301
    return v2

    .line 304
    :cond_1
    const-string v3, "in_global"

    invoke-virtual {v1, v3}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_2

    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceHW:Ljava/lang/String;

    const-string v3, "INDIA"

    invoke-virtual {v1, v3}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 306
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->DSI_Hash_Init_Common_Cfg()V

    .line 307
    sput-boolean v2, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mSarSensorEnabled:Z

    .line 308
    const-string v1, "India version not supprot sar sensor, will not listen it"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    goto :goto_0

    .line 312
    :cond_2
    const-string v1, "sw and hw info is mismatch or not correct, pls check your sw and hw info"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 313
    const/4 v0, 0x0

    .line 317
    :goto_0
    return v0
.end method

.method public static onInitSarProdcutParameter()Z
    .locals 3

    .line 200
    const/4 v0, 0x0

    .line 202
    .local v0, "sarFlag":Z
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    if-nez v1, :cond_0

    .line 203
    const-string v1, "mDeviceName is null"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    goto :goto_0

    .line 204
    :cond_0
    const-string v2, "davinci"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 205
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->onInitSarParameterF10()Z

    move-result v0

    goto :goto_0

    .line 206
    :cond_1
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    const-string v2, "grus"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 207
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->onInitSarParameterF2()Z

    move-result v0

    goto :goto_0

    .line 208
    :cond_2
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    const-string v2, "phoenixin"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_3

    .line 209
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->onInitSarParameterG7B()Z

    move-result v0

    goto :goto_0

    .line 210
    :cond_3
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    const-string v2, "tucana"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_4

    .line 211
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->onInitSarParameterF4()Z

    move-result v0

    goto :goto_0

    .line 212
    :cond_4
    sget-object v1, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mDeviceName:Ljava/lang/String;

    const-string v2, "toco"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_5

    .line 213
    invoke-static {}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->onInitSarParameterF4L()Z

    move-result v0

    goto :goto_0

    .line 215
    :cond_5
    const-string v1, "not support this product"

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 218
    :goto_0
    return v0
.end method

.method private sendMsgToHandler(III)V
    .locals 2
    .param p1, "message"    # I
    .param p2, "arg1"    # I
    .param p3, "arg2"    # I

    .line 454
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "sendMsgToHandler, Message = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", arg1 = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", arg2 = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 455
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->mCmdProc:Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;

    invoke-virtual {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController$CmdProcThread;->getCmdHandler()Landroid/os/Handler;

    move-result-object v0

    .line 456
    .local v0, "handler":Landroid/os/Handler;
    invoke-virtual {v0, p1, p2, p3}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v1

    .line 457
    .local v1, "msg":Landroid/os/Message;
    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    .line 458
    return-void
.end method


# virtual methods
.method public onStateChanged(II)V
    .locals 3
    .param p1, "type"    # I
    .param p2, "value"    # I

    .line 371
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "onStateChanged: type = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", value = "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->log(Ljava/lang/String;)V

    .line 372
    const/4 v0, 0x0

    if-eqz p1, :cond_5

    const/4 v1, 0x3

    const/4 v2, 0x1

    if-eq p1, v2, :cond_4

    if-eq p1, v1, :cond_3

    const/4 v1, 0x5

    if-eq p1, v1, :cond_2

    const/4 v2, 0x6

    if-eq p1, v2, :cond_1

    const/4 v2, 0x7

    if-eq p1, v2, :cond_0

    goto :goto_0

    .line 389
    :cond_0
    invoke-direct {p0, v1, p2, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sendMsgToHandler(III)V

    .line 390
    goto :goto_0

    .line 386
    :cond_1
    invoke-direct {p0, v1, p2, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sendMsgToHandler(III)V

    .line 387
    goto :goto_0

    .line 377
    :cond_2
    const/4 v1, 0x2

    invoke-direct {p0, v1, p2, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sendMsgToHandler(III)V

    .line 378
    goto :goto_0

    .line 374
    :cond_3
    invoke-direct {p0, v2, p2, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sendMsgToHandler(III)V

    .line 375
    goto :goto_0

    .line 380
    :cond_4
    invoke-direct {p0, v1, p2, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sendMsgToHandler(III)V

    .line 381
    goto :goto_0

    .line 383
    :cond_5
    const/4 v1, 0x4

    invoke-direct {p0, v1, p2, v0}, Lcom/qualcomm/qti/internal/telephony/ModemSarController;->sendMsgToHandler(III)V

    .line 384
    nop

    .line 393
    :goto_0
    return-void
.end method
