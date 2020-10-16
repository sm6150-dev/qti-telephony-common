.class Lcom/qualcomm/qti/internal/telephony/DynamicSarService$3;
.super Ljava/lang/Thread;
.source "DynamicSarService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->startPollAudioReceiverStatus()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;


# direct methods
.method constructor <init>(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)V
    .locals 0
    .param p1, "this$0"    # Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    .line 240
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$3;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    .line 243
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$3;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$1100(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)V

    .line 244
    return-void
.end method
