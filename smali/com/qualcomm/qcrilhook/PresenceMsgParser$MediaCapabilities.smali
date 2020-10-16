.class final enum Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;
.super Ljava/lang/Enum;
.source "PresenceMsgParser.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qcrilhook/PresenceMsgParser;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4018
    name = "MediaCapabilities"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

.field public static final enum FULL_DUPLEX:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

.field public static final enum HALF_RECEIVE_ONLY:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

.field public static final enum HALF_SEND_ONLY:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 21
    new-instance v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    const-string v1, "FULL_DUPLEX"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->FULL_DUPLEX:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    new-instance v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    const-string v1, "HALF_RECEIVE_ONLY"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->HALF_RECEIVE_ONLY:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    new-instance v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    const-string v1, "HALF_SEND_ONLY"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->HALF_SEND_ONLY:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    .line 20
    const/4 v1, 0x3

    new-array v1, v1, [Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    sget-object v5, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->FULL_DUPLEX:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    aput-object v5, v1, v2

    sget-object v2, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->HALF_RECEIVE_ONLY:Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    aput-object v2, v1, v3

    aput-object v0, v1, v4

    sput-object v1, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->$VALUES:[Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 20
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 20
    const-class v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    return-object v0
.end method

.method public static values()[Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;
    .locals 1

    .line 20
    sget-object v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->$VALUES:[Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    invoke-virtual {v0}, [Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;

    return-object v0
.end method
