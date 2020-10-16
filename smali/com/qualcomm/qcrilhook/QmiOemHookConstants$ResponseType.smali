.class public final enum Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;
.super Ljava/lang/Enum;
.source "QmiOemHookConstants.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qcrilhook/QmiOemHookConstants;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ResponseType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

.field public static final enum IS_ASYNC_RESPONSE:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

.field public static final enum IS_SYNC_RESPONSE:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

.field public static final enum IS_UNSOL:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 53
    new-instance v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    const-string v1, "IS_SYNC_RESPONSE"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->IS_SYNC_RESPONSE:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    new-instance v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    const-string v1, "IS_ASYNC_RESPONSE"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->IS_ASYNC_RESPONSE:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    new-instance v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    const-string v1, "IS_UNSOL"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->IS_UNSOL:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    .line 52
    const/4 v1, 0x3

    new-array v1, v1, [Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    sget-object v5, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->IS_SYNC_RESPONSE:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    aput-object v5, v1, v2

    sget-object v2, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->IS_ASYNC_RESPONSE:Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    aput-object v2, v1, v3

    aput-object v0, v1, v4

    sput-object v1, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->$VALUES:[Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 52
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 52
    const-class v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    return-object v0
.end method

.method public static values()[Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;
    .locals 1

    .line 52
    sget-object v0, Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->$VALUES:[Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    invoke-virtual {v0}, [Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/qualcomm/qcrilhook/QmiOemHookConstants$ResponseType;

    return-object v0
.end method
