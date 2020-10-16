.class Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;
.super Landroid/content/BroadcastReceiver;
.source "QtiCardInfoManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;


# direct methods
.method constructor <init>(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;)V
    .locals 0
    .param p1, "this$0"    # Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    .line 209
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;->this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 213
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    const-string v1, "android.intent.action.SIM_STATE_CHANGED"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 214
    const-string v0, "slot"

    const/4 v1, 0x0

    invoke-virtual {p2, v0, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v0

    .line 215
    .local v0, "slotId":I
    nop

    .line 216
    const-string v2, "ss"

    invoke-virtual {p2, v2}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 217
    .local v2, "stateExtra":Ljava/lang/String;
    iget-object v3, p0, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;->this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, " SIM_STATE_CHANGED intent received state is "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v5, " slotId + "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;->access$100(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;Ljava/lang/String;)V

    .line 220
    invoke-static {v0}, Landroid/telephony/SubscriptionManager;->isValidSlotIndex(I)Z

    move-result v3

    if-nez v3, :cond_0

    .line 221
    return-void

    .line 224
    :cond_0
    const-string v3, "LOADED"

    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_1

    .line 225
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;->this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;->access$200(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;)[Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;

    move-result-object v1

    aget-object v1, v1, v0

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;->access$300(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;)Z

    move-result v1

    if-nez v1, :cond_2

    .line 226
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;->this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;->access$200(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;)[Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;

    move-result-object v1

    aget-object v1, v1, v0

    const/4 v3, 0x1

    invoke-static {v1, v3}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;->access$302(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;Z)Z

    .line 227
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;->this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    invoke-static {v1, v0}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;->access$400(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;I)V

    goto :goto_0

    .line 230
    :cond_1
    iget-object v3, p0, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$1;->this$0:Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;

    invoke-static {v3}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;->access$200(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager;)[Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;

    move-result-object v3

    aget-object v3, v3, v0

    invoke-static {v3, v1}, Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;->access$302(Lcom/qualcomm/qti/internal/telephony/primarycard/QtiCardInfoManager$CardInfo;Z)Z

    .line 233
    .end local v0    # "slotId":I
    .end local v2    # "stateExtra":Ljava/lang/String;
    :cond_2
    :goto_0
    return-void
.end method
