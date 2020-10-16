.class Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$PublishStructRequest;
.super Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiStructType;
.source "PresenceMsgBuilder.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "PublishStructRequest"
.end annotation


# static fields
.field public static final PUBLISH_PRESENCE_INFO_TYPE:S = 0x10s

.field public static final PUBLISH_STATUS_TYPE:S = 0x1s


# instance fields
.field mPresence_info:Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$Imsp_presence_info_struct;

.field mPublish_status:Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 448
    invoke-direct {p0}, Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiStructType;-><init>()V

    .line 449
    return-void
.end method

.method public constructor <init>(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIII)V
    .locals 14
    .param p1, "publish_status"    # I
    .param p2, "contact_uri"    # Ljava/lang/String;
    .param p3, "description"    # Ljava/lang/String;
    .param p4, "ver"    # Ljava/lang/String;
    .param p5, "service_id"    # Ljava/lang/String;
    .param p6, "is_audio_supported"    # I
    .param p7, "audio_capability"    # I
    .param p8, "is_video_supported"    # I
    .param p9, "video_capability"    # I

    .line 459
    move-object v0, p0

    invoke-direct {p0}, Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiStructType;-><init>()V

    .line 461
    new-instance v1, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    move v2, p1

    int-to-long v3, v2

    invoke-direct {v1, v3, v4}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;-><init>(J)V

    iput-object v1, v0, Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$PublishStructRequest;->mPublish_status:Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    .line 462
    new-instance v1, Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$Imsp_presence_info_struct;

    move-object v5, v1

    move-object/from16 v6, p2

    move-object/from16 v7, p3

    move-object/from16 v8, p4

    move-object/from16 v9, p5

    move/from16 v10, p6

    move/from16 v11, p7

    move/from16 v12, p8

    move/from16 v13, p9

    invoke-direct/range {v5 .. v13}, Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$Imsp_presence_info_struct;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIII)V

    iput-object v1, v0, Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$PublishStructRequest;->mPresence_info:Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$Imsp_presence_info_struct;

    .line 470
    return-void
.end method


# virtual methods
.method public getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;
    .locals 3

    .line 474
    const/4 v0, 0x2

    new-array v0, v0, [Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    iget-object v1, p0, Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$PublishStructRequest;->mPublish_status:Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    iget-object v1, p0, Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$PublishStructRequest;->mPresence_info:Lcom/qualcomm/qcrilhook/PresenceMsgBuilder$Publish$Imsp_presence_info_struct;

    const/4 v2, 0x1

    aput-object v1, v0, v2

    return-object v0
.end method

.method public getTypes()[S
    .locals 1

    .line 481
    const/4 v0, 0x2

    new-array v0, v0, [S

    fill-array-data v0, :array_0

    return-object v0

    nop

    :array_0
    .array-data 2
        0x1s
        0x10s
    .end array-data
.end method
