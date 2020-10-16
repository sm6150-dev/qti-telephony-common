.class public Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;
.super Ljava/lang/Object;
.source "EmbmsOemHook.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qcrilhook/EmbmsOemHook;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "TimeResponse"
.end annotation


# instance fields
.field public additionalInfo:Z

.field public code:I

.field public dayLightSaving:Z

.field public leapSeconds:B

.field public localTimeOffset:J

.field public status:I

.field final synthetic this$0:Lcom/qualcomm/qcrilhook/EmbmsOemHook;

.field public timeMseconds:J

.field public traceId:I


# direct methods
.method public constructor <init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IIJZJZB)V
    .locals 4
    .param p1, "this$0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p2, "traceId"    # I
    .param p3, "status"    # I
    .param p4, "timeMseconds"    # J
    .param p6, "additionalInfo"    # Z
    .param p7, "localTimeOffset"    # J
    .param p9, "dayLightSaving"    # Z
    .param p10, "leapSeconds"    # B

    .line 2236
    iput-object p1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->this$0:Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2176
    const/4 v0, 0x0

    iput v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->code:I

    .line 2177
    const-wide/16 v1, 0x0

    iput-wide v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->timeMseconds:J

    .line 2179
    iput-boolean v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    .line 2180
    iput-boolean v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->dayLightSaving:Z

    .line 2181
    iput-byte v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->leapSeconds:B

    .line 2182
    iput v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->traceId:I

    .line 2183
    iput-wide v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->localTimeOffset:J

    .line 2237
    iput p3, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->status:I

    .line 2238
    iput p2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->traceId:I

    .line 2239
    iput v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->code:I

    .line 2240
    iput-wide p4, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->timeMseconds:J

    .line 2241
    iput-wide p7, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->localTimeOffset:J

    .line 2242
    iput-boolean p6, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    .line 2243
    iput-boolean p9, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->dayLightSaving:Z

    .line 2244
    iput-byte p10, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->leapSeconds:B

    .line 2246
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "TimeResponse: traceId = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->traceId:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " code = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->code:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " timeMseconds = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->timeMseconds:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v2, "additionalInfo = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " localTimeOffset = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->localTimeOffset:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v2, " dayLightSaving = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->dayLightSaving:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " leapSeconds = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-byte v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->leapSeconds:B

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2251
    return-void
.end method

.method public constructor <init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V
    .locals 6
    .param p1, "this$0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p2, "status"    # I
    .param p3, "buf"    # Ljava/nio/ByteBuffer;

    .line 2185
    iput-object p1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->this$0:Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2176
    const/4 v0, 0x0

    iput v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->code:I

    .line 2177
    const-wide/16 v1, 0x0

    iput-wide v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->timeMseconds:J

    .line 2179
    iput-boolean v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    .line 2180
    iput-boolean v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->dayLightSaving:Z

    .line 2181
    iput-byte v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->leapSeconds:B

    .line 2182
    iput v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->traceId:I

    .line 2183
    iput-wide v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->localTimeOffset:J

    .line 2186
    iput p2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->status:I

    .line 2188
    :goto_0
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->hasRemaining()Z

    move-result v0

    if-eqz v0, :cond_4

    .line 2190
    :try_start_0
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->get()B

    move-result v0

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(B)S

    move-result v0

    .line 2191
    .local v0, "type":I
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    invoke-static {v1}, Lcom/qualcomm/qcrilhook/PrimitiveParser;->toUnsigned(S)I

    move-result v1

    .line 2193
    .local v1, "length":I
    const/4 v2, 0x1

    if-eq v0, v2, :cond_3

    const/4 v3, 0x2

    if-eq v0, v3, :cond_2

    const/4 v3, 0x3

    if-eq v0, v3, :cond_1

    packed-switch v0, :pswitch_data_0

    .line 2225
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "TimeResponse: Unexpected Type "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_1

    .line 2212
    :pswitch_0
    iput-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    .line 2213
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->get()B

    move-result v2

    int-to-long v2, v2

    iput-wide v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->localTimeOffset:J

    .line 2214
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "localTimeOffset = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v4, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->localTimeOffset:J

    invoke-virtual {v3, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2215
    goto/16 :goto_1

    .line 2207
    :pswitch_1
    iput-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    .line 2208
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->get()B

    move-result v2

    iput-byte v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->leapSeconds:B

    .line 2209
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "leapSeconds = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-byte v4, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->leapSeconds:B

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2210
    goto/16 :goto_1

    .line 2199
    :pswitch_2
    iput-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    .line 2200
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->get()B

    move-result v3

    .line 2201
    .local v3, "isdayLightSaving":B
    if-ne v3, v2, :cond_0

    .line 2202
    iput-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->dayLightSaving:Z

    .line 2204
    :cond_0
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "dayLightSaving = "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v5, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->dayLightSaving:Z

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2205
    goto :goto_1

    .line 2195
    .end local v3    # "isdayLightSaving":B
    :cond_1
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->getLong()J

    move-result-wide v2

    iput-wide v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->timeMseconds:J

    .line 2196
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "timeMseconds = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v4, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->timeMseconds:J

    invoke-virtual {v3, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2197
    goto :goto_1

    .line 2221
    :cond_2
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->getInt()I

    move-result v2

    iput v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->code:I

    .line 2222
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "code = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->code:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2223
    goto :goto_1

    .line 2217
    :cond_3
    invoke-virtual {p3}, Ljava/nio/ByteBuffer;->getInt()I

    move-result v2

    iput v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->traceId:I

    .line 2218
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v2

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "traceId = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v4, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->traceId:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catch Ljava/nio/BufferUnderflowException; {:try_start_0 .. :try_end_0} :catch_0

    .line 2219
    goto :goto_1

    .line 2228
    .end local v0    # "type":I
    .end local v1    # "length":I
    :catch_0
    move-exception v0

    .line 2229
    .local v0, "e":Ljava/nio/BufferUnderflowException;
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v1

    const-string v2, "Invalid format of byte buffer received in TimeResponse"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2230
    .end local v0    # "e":Ljava/nio/BufferUnderflowException;
    :goto_1
    goto/16 :goto_0

    .line 2232
    :cond_4
    invoke-static {}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->access$000()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "additionalInfo = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;->additionalInfo:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 2233
    return-void

    :pswitch_data_0
    .packed-switch 0x10
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
