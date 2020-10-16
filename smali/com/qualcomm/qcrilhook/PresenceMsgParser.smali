.class public Lcom/qualcomm/qcrilhook/PresenceMsgParser;
.super Ljava/lang/Object;
.source "PresenceMsgParser.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;,
        Lcom/qualcomm/qcrilhook/PresenceMsgParser$ListHeaderInfo;,
        Lcom/qualcomm/qcrilhook/PresenceMsgParser$ContactInfo;,
        Lcom/qualcomm/qcrilhook/PresenceMsgParser$MediaCapabilities;
    }
.end annotation


# static fields
.field private static LOG_TAG:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 17
    const-string v0, "PresenceMsgParser"

    sput-object v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser;->LOG_TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 16
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()Ljava/lang/String;
    .locals 1

    .line 16
    sget-object v0, Lcom/qualcomm/qcrilhook/PresenceMsgParser;->LOG_TAG:Ljava/lang/String;

    return-object v0
.end method

.method static parseEnablerState(Ljava/nio/ByteBuffer;)I
    .locals 4
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;

    .line 436
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    int-to-byte v0, v0

    .line 437
    .local v0, "type":B
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    int-to-short v1, v1

    .line 438
    .local v1, "len":S
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getInt()I

    move-result v2

    invoke-static {v2}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(I)J

    move-result-wide v2

    long-to-int v2, v2

    .line 440
    .local v2, "val":I
    return v2
.end method

.method static parseEnablerStateInd(Ljava/nio/ByteBuffer;)I
    .locals 4
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;

    .line 486
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    int-to-byte v0, v0

    .line 487
    .local v0, "tag":B
    nop

    .line 488
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    .line 487
    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    int-to-short v1, v1

    .line 489
    .local v1, "len":S
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getInt()I

    move-result v2

    invoke-static {v2}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(I)J

    move-result-wide v2

    long-to-int v2, v2

    .line 491
    .local v2, "val":I
    return v2
.end method

.method static parseGetEventReport(Ljava/nio/ByteBuffer;)I
    .locals 3
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;

    .line 466
    nop

    .line 467
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    .line 466
    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    int-to-byte v0, v0

    .line 468
    .local v0, "optionalTag":B
    nop

    .line 469
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    .line 468
    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    int-to-short v1, v1

    .line 470
    .local v1, "optionalLen":S
    nop

    .line 471
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v2

    .line 470
    invoke-static {v2}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v2

    int-to-byte v2, v2

    .line 473
    .local v2, "optionalVal":B
    return v2
.end method

.method static parseGetNotifyReq(Ljava/nio/ByteBuffer;)I
    .locals 3
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;

    .line 457
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    int-to-byte v0, v0

    .line 458
    .local v0, "optionalTag":B
    nop

    .line 459
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    .line 458
    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    int-to-short v1, v1

    .line 460
    .local v1, "optionalLen":S
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v2

    invoke-static {v2}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v2

    int-to-byte v2, v2

    .line 462
    .local v2, "optionalVal":B
    return v2
.end method

.method static parseNotifyUpdate(Ljava/nio/ByteBuffer;II)Ljava/util/ArrayList;
    .locals 10
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;
    .param p1, "responseSize"    # I
    .param p2, "successStatus"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/nio/ByteBuffer;",
            "II)",
            "Ljava/util/ArrayList<",
            "Lcom/qualcomm/qcrilhook/PresenceMsgParser$ContactInfo;",
            ">;"
        }
    .end annotation

    .line 396
    const/4 v0, 0x1

    .line 397
    .local v0, "NOTIFY_DETAIL_TYPE":S
    const/16 v1, 0x10

    .line 398
    .local v1, "IMSP_SUBSCRIBE_CALLID_TYPE":S
    const/4 v2, 0x0

    .line 400
    .local v2, "callId":I
    sget-object v3, Lcom/qualcomm/qcrilhook/PresenceMsgParser;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "notifyUpdate(), Thread="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {}, Ljava/lang/Thread;->currentThread()Ljava/lang/Thread;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/Thread;->getName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 402
    :goto_0
    if-lez p1, :cond_3

    .line 403
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v3

    invoke-static {v3}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v3

    .line 404
    .local v3, "type":S
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v4

    invoke-static {v4}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v4

    .line 406
    .local v4, "length":I
    const/4 v5, 0x1

    if-eq v3, v5, :cond_2

    const/16 v5, 0x10

    if-eq v3, v5, :cond_0

    goto :goto_2

    .line 408
    :cond_0
    new-array v5, v4, [B

    .line 409
    .local v5, "data":[B
    const/4 v6, 0x0

    .local v6, "i":I
    :goto_1
    if-ge v6, v4, :cond_1

    .line 410
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v7

    aput-byte v7, v5, v6

    .line 409
    add-int/lit8 v6, v6, 0x1

    goto :goto_1

    .line 412
    .end local v6    # "i":I
    :cond_1
    const/4 v6, 0x0

    aget-byte v6, v5, v6

    invoke-static {v6}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v2

    .line 413
    sget-object v6, Lcom/qualcomm/qcrilhook/PresenceMsgParser;->LOG_TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "callId = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v6, v7}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 414
    nop

    .line 428
    .end local v5    # "data":[B
    :goto_2
    add-int/lit8 v5, v4, 0x3

    sub-int/2addr p1, v5

    .line 431
    .end local v3    # "type":S
    .end local v4    # "length":I
    goto :goto_0

    .line 416
    .restart local v3    # "type":S
    .restart local v4    # "length":I
    :cond_2
    sget-object v5, Lcom/qualcomm/qcrilhook/PresenceMsgParser;->LOG_TAG:Ljava/lang/String;

    const-string v6, "NOTIFY_DETAIL_TYPE"

    invoke-static {v5, v6}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 418
    new-instance v5, Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;

    invoke-direct {v5, p0, v4}, Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;-><init>(Ljava/nio/ByteBuffer;I)V

    .line 420
    .local v5, "parser":Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;
    invoke-static {v5}, Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;->access$100(Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;)Ljava/util/ArrayList;

    move-result-object v6

    .line 421
    .local v6, "parsedContactList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/qualcomm/qcrilhook/PresenceMsgParser$ContactInfo;>;"
    sget-object v7, Lcom/qualcomm/qcrilhook/PresenceMsgParser;->LOG_TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "parsed contact info "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 422
    return-object v6

    .line 432
    .end local v3    # "type":S
    .end local v4    # "length":I
    .end local v5    # "parser":Lcom/qualcomm/qcrilhook/PresenceMsgParser$PresenceRichNotifyParser;
    .end local v6    # "parsedContactList":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/qualcomm/qcrilhook/PresenceMsgParser$ContactInfo;>;"
    :cond_3
    const/4 v3, 0x0

    return-object v3
.end method

.method static parseNotifyUpdateXML(Ljava/nio/ByteBuffer;)Ljava/lang/String;
    .locals 5
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;

    .line 444
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    int-to-byte v0, v0

    .line 445
    .local v0, "tag":B
    nop

    .line 446
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    .line 445
    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    int-to-short v1, v1

    .line 447
    .local v1, "len":S
    new-array v2, v1, [B

    .line 449
    .local v2, "data":[B
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v1, :cond_0

    .line 450
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v4

    aput-byte v4, v2, v3

    .line 449
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 452
    .end local v3    # "i":I
    :cond_0
    new-instance v3, Ljava/lang/String;

    invoke-direct {v3, v2}, Ljava/lang/String;-><init>([B)V

    return-object v3
.end method

.method static parsePublishTrigger(Ljava/nio/ByteBuffer;)I
    .locals 4
    .param p0, "respByteBuf"    # Ljava/nio/ByteBuffer;

    .line 477
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    int-to-byte v0, v0

    .line 478
    .local v0, "tag":B
    nop

    .line 479
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    .line 478
    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    int-to-short v1, v1

    .line 480
    .local v1, "len":S
    invoke-virtual {p0}, Ljava/nio/ByteBuffer;->getInt()I

    move-result v2

    invoke-static {v2}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(I)J

    move-result-wide v2

    long-to-int v2, v2

    .line 482
    .local v2, "val":I
    return v2
.end method
