.class Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;
.super Ljava/lang/Object;
.source "QtiGsmCdmaPhone.java"

# interfaces
.implements Lcom/android/ims/FeatureConnector$Listener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;-><init>(Landroid/content/Context;Lcom/android/internal/telephony/CommandsInterface;Lcom/android/internal/telephony/PhoneNotifier;ZIILcom/android/internal/telephony/TelephonyComponentFactory;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lcom/android/ims/FeatureConnector$Listener<",
        "Lcom/android/ims/ImsManager;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

.field final synthetic val$context:Landroid/content/Context;

.field final synthetic val$phoneId:I


# direct methods
.method constructor <init>(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;ILandroid/content/Context;)V
    .locals 0
    .param p1, "this$0"    # Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    .line 96
    iput-object p1, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    iput p2, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->val$phoneId:I

    iput-object p3, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->val$context:Landroid/content/Context;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public connectionReady(Lcom/android/ims/ImsManager;)V
    .locals 2
    .param p1, "manager"    # Lcom/android/ims/ImsManager;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/android/ims/ImsException;
        }
    .end annotation

    .line 99
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;->access$000(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;)Ljava/util/BitSet;

    move-result-object v0

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Ljava/util/BitSet;->set(I)V

    .line 101
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;->access$000(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;)Ljava/util/BitSet;

    move-result-object v0

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/BitSet;->set(I)V

    .line 102
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    iget v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->val$phoneId:I

    invoke-static {v0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;->access$100(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;I)V

    .line 103
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    const-string v1, "IMS stack is ready."

    invoke-static {v0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;->access$200(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;Ljava/lang/String;)V

    .line 104
    return-void
.end method

.method public bridge synthetic connectionReady(Ljava/lang/Object;)V
    .locals 0
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Lcom/android/ims/ImsException;
        }
    .end annotation

    .line 96
    check-cast p1, Lcom/android/ims/ImsManager;

    invoke-virtual {p0, p1}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->connectionReady(Lcom/android/ims/ImsManager;)V

    return-void
.end method

.method public connectionUnavailable()V
    .locals 2

    .line 108
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    invoke-static {v0}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;->access$000(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;)Ljava/util/BitSet;

    move-result-object v0

    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Ljava/util/BitSet;->clear(I)V

    .line 109
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->this$0:Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;

    const-string v1, "IMS stack is unready."

    invoke-static {v0, v1}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;->access$200(Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone;Ljava/lang/String;)V

    .line 110
    return-void
.end method

.method public getFeatureManager()Lcom/android/ims/ImsManager;
    .locals 2

    .line 114
    iget-object v0, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->val$context:Landroid/content/Context;

    iget v1, p0, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->val$phoneId:I

    invoke-static {v0, v1}, Lcom/android/ims/ImsManager;->getInstance(Landroid/content/Context;I)Lcom/android/ims/ImsManager;

    move-result-object v0

    return-object v0
.end method

.method public bridge synthetic getFeatureManager()Ljava/lang/Object;
    .locals 1

    .line 96
    invoke-virtual {p0}, Lcom/qualcomm/qti/internal/telephony/QtiGsmCdmaPhone$1;->getFeatureManager()Lcom/android/ims/ImsManager;

    move-result-object v0

    return-object v0
.end method
