.class Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;
.super Landroid/content/BroadcastReceiver;
.source "DynamicSarService.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/qualcomm/qti/internal/telephony/DynamicSarService;
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

    .line 134
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 137
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 138
    .local v0, "action":Ljava/lang/String;
    const-string v1, "android.net.wifi.STATE_CHANGE"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x0

    if-eqz v1, :cond_2

    .line 139
    const-string v1, "networkInfo"

    invoke-virtual {p2, v1}, Landroid/content/Intent;->getParcelableExtra(Ljava/lang/String;)Landroid/os/Parcelable;

    move-result-object v1

    check-cast v1, Landroid/net/NetworkInfo;

    .line 142
    .local v1, "info":Landroid/net/NetworkInfo;
    invoke-virtual {v1}, Landroid/net/NetworkInfo;->isConnected()Z

    move-result v3

    if-eqz v3, :cond_0

    .line 143
    const/4 v3, 0x1

    .local v3, "wifiState":I
    goto :goto_0

    .line 145
    .end local v3    # "wifiState":I
    :cond_0
    const/4 v3, 0x0

    .line 147
    .restart local v3    # "wifiState":I
    :goto_0
    iget-object v4, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v4}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$000(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)I

    move-result v4

    if-eq v3, v4, :cond_1

    .line 148
    iget-object v4, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v4}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$100(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)Ljava/util/ArrayList;

    move-result-object v5

    invoke-virtual {v4, v5, v2, v3}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->callChangeListeners(Ljava/util/ArrayList;II)V

    .line 149
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v2, v3}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$002(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;I)I

    .line 151
    .end local v1    # "info":Landroid/net/NetworkInfo;
    .end local v3    # "wifiState":I
    :cond_1
    goto/16 :goto_2

    :cond_2
    const-string v1, "android.net.wifi.WIFI_AP_STATE_CHANGED"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    const/4 v3, 0x1

    if-eqz v1, :cond_5

    .line 152
    const-string v1, "wifi_state"

    invoke-virtual {p2, v1, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 154
    .local v1, "state":I
    const/16 v2, 0xd

    if-ne v1, v2, :cond_3

    .line 155
    const/4 v2, 0x1

    .local v2, "hotspotState":I
    goto :goto_1

    .line 157
    .end local v2    # "hotspotState":I
    :cond_3
    const/4 v2, 0x0

    .line 159
    .restart local v2    # "hotspotState":I
    :goto_1
    iget-object v4, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v4}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$200(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)I

    move-result v4

    if-eq v2, v4, :cond_4

    .line 160
    iget-object v4, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v4}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$300(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)Ljava/util/ArrayList;

    move-result-object v5

    invoke-virtual {v4, v5, v3, v2}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->callChangeListeners(Ljava/util/ArrayList;II)V

    .line 161
    iget-object v3, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v3, v2}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$202(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;I)I

    .line 163
    .end local v1    # "state":I
    .end local v2    # "hotspotState":I
    :cond_4
    goto :goto_2

    :cond_5
    const-string v1, "android.intent.action.ACTION_POWER_CONNECTED"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_6

    .line 164
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$400(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)Ljava/util/ArrayList;

    move-result-object v2

    const/4 v4, 0x6

    invoke-virtual {v1, v2, v4, v3}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->callChangeListeners(Ljava/util/ArrayList;II)V

    goto :goto_2

    .line 165
    :cond_6
    const-string v1, "android.intent.action.ACTION_POWER_DISCONNECTED"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_7

    .line 166
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$500(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)Ljava/util/ArrayList;

    move-result-object v3

    const/4 v4, 0x7

    invoke-virtual {v1, v3, v4, v2}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->callChangeListeners(Ljava/util/ArrayList;II)V

    goto :goto_2

    .line 167
    :cond_7
    const-string v1, "android.net.wifi.COUNTRY_CODE_CHANGED"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8

    .line 169
    iget-object v1, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v1}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$600(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)I

    move-result v1

    .line 170
    .local v1, "mccState":I
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$700(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)I

    move-result v2

    if-eq v1, v2, :cond_8

    .line 171
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v2}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$800(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;)Ljava/util/ArrayList;

    move-result-object v3

    const/16 v4, 0x8

    invoke-virtual {v2, v3, v4, v1}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->callChangeListeners(Ljava/util/ArrayList;II)V

    .line 172
    iget-object v2, p0, Lcom/qualcomm/qti/internal/telephony/DynamicSarService$1;->this$0:Lcom/qualcomm/qti/internal/telephony/DynamicSarService;

    invoke-static {v2, v1}, Lcom/qualcomm/qti/internal/telephony/DynamicSarService;->access$702(Lcom/qualcomm/qti/internal/telephony/DynamicSarService;I)I

    .line 175
    .end local v1    # "mccState":I
    :cond_8
    :goto_2
    return-void
.end method
