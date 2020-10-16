.class public final enum Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;
.super Ljava/lang/Enum;
.source "PresenceOemHook.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qcrilhook/PresenceOemHook;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "SubscriptionType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

.field public static final enum NONE:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

.field public static final enum POLLING:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

.field public static final enum SIMPLE:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 63
    new-instance v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    const-string v1, "NONE"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->NONE:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    new-instance v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    const-string v1, "SIMPLE"

    const/4 v3, 0x1

    invoke-direct {v0, v1, v3}, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->SIMPLE:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    new-instance v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    const-string v1, "POLLING"

    const/4 v4, 0x2

    invoke-direct {v0, v1, v4}, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;-><init>(Ljava/lang/String;I)V

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->POLLING:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    .line 62
    const/4 v1, 0x3

    new-array v1, v1, [Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    sget-object v5, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->NONE:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    aput-object v5, v1, v2

    sget-object v2, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->SIMPLE:Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    aput-object v2, v1, v3

    aput-object v0, v1, v4

    sput-object v1, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->$VALUES:[Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 62
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;
    .locals 1
    .param p0, "name"    # Ljava/lang/String;

    .line 62
    const-class v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    return-object v0
.end method

.method public static values()[Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;
    .locals 1

    .line 62
    sget-object v0, Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->$VALUES:[Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    invoke-virtual {v0}, [Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [Lcom/qualcomm/qcrilhook/PresenceOemHook$SubscriptionType;

    return-object v0
.end method
