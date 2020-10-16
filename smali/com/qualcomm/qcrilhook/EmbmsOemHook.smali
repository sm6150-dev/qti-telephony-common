.class public Lcom/qualcomm/qcrilhook/EmbmsOemHook;
.super Landroid/os/Handler;
.source "EmbmsOemHook.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$EmbmsStatus;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetPLMNListResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$SigStrengthResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$DeliverLogPacketRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$SetTimeRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiDeActivateRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiActivateRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$DisableResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$EnableResponse;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$RequestIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescPerObjectControlIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$E911StateIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$Sib16Coverage;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$CoverageState;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$SaiIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$RadioStateIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$CellIdIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$OosState;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$StateChangeInfo;,
        Lcom/qualcomm/qcrilhook/EmbmsOemHook$UnsolObject;
    }
.end annotation


# static fields
.field private static final DEFAULT_PHONE:I = 0x0

.field private static final EMBMSHOOK_MSG_ID_ACTDEACT:S = 0x11s

.field private static final EMBMSHOOK_MSG_ID_ACTIVATE:S = 0x2s

.field private static final EMBMSHOOK_MSG_ID_CONTENT_DESCRIPTION:S = 0x1ds

.field private static final EMBMSHOOK_MSG_ID_DEACTIVATE:S = 0x3s

.field private static final EMBMSHOOK_MSG_ID_DELIVER_LOG_PACKET:S = 0x16s

.field private static final EMBMSHOOK_MSG_ID_DISABLE:S = 0x1s

.field private static final EMBMSHOOK_MSG_ID_ENABLE:S = 0x0s

.field private static final EMBMSHOOK_MSG_ID_GET_ACTIVE:S = 0x5s

.field private static final EMBMSHOOK_MSG_ID_GET_ACTIVE_LOG_PACKET_IDS:S = 0x15s

.field private static final EMBMSHOOK_MSG_ID_GET_AVAILABLE:S = 0x4s

.field private static final EMBMSHOOK_MSG_ID_GET_COVERAGE:S = 0x8s

.field private static final EMBMSHOOK_MSG_ID_GET_E911_STATE:S = 0x1bs

.field private static final EMBMSHOOK_MSG_ID_GET_EMBMS_STATUS:S = 0x21s

.field private static final EMBMSHOOK_MSG_ID_GET_INTERESTED_TMGI_LIST_RESP:S = 0x23s

.field private static final EMBMSHOOK_MSG_ID_GET_PLMN_LIST:S = 0x1fs

.field private static final EMBMSHOOK_MSG_ID_GET_SIB16_COVERAGE:S = 0x18s

.field private static final EMBMSHOOK_MSG_ID_GET_SIG_STRENGTH:S = 0x9s

.field private static final EMBMSHOOK_MSG_ID_GET_TIME:S = 0x1as

.field private static final EMBMSHOOK_MSG_ID_SET_TIME:S = 0x17s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_ACTIVE_TMGI_LIST:S = 0xcs

.field private static final EMBMSHOOK_MSG_ID_UNSOL_AVAILABLE_TMGI_LIST:S = 0xfs

.field private static final EMBMSHOOK_MSG_ID_UNSOL_CELL_ID:S = 0x12s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL:S = 0x1es

.field private static final EMBMSHOOK_MSG_ID_UNSOL_COVERAGE_STATE:S = 0xds

.field private static final EMBMSHOOK_MSG_ID_UNSOL_E911_STATE:S = 0x1cs

.field private static final EMBMSHOOK_MSG_ID_UNSOL_EMBMS_STATUS:S = 0x20s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_GET_INTERESTED_TMGI_LIST:S = 0x22s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_OOS_STATE:S = 0x10s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_RADIO_STATE:S = 0x13s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_SAI_LIST:S = 0x14s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_SIB16:S = 0x19s

.field private static final EMBMSHOOK_MSG_ID_UNSOL_STATE_CHANGE:S = 0xbs

.field private static final EMBMS_SERVICE_ID:S = 0x2s

.field private static final FAILURE:I = -0x1

.field private static LOG_TAG:Ljava/lang/String; = null

.field private static final OEM_HOOK_RESPONSE:I = 0x1

.field private static final ONE_BYTE:S = 0x1s

.field private static final QCRILHOOK_READY_CALLBACK:I = 0x2

.field private static final SIZE_OF_EACH_PLMN_IN_BYTES:S = 0x6s

.field private static final SIZE_OF_TMGI:I = 0x6

.field private static final SUCCESS:I = 0x0

.field private static final TLV_TYPE_ACTDEACTIVATE_REQ_ACT_TMGI:B = 0x3t

.field private static final TLV_TYPE_ACTDEACTIVATE_REQ_DEACT_TMGI:B = 0x4t

.field private static final TLV_TYPE_ACTDEACTIVATE_REQ_EARFCN_LIST:B = 0x6t

.field private static final TLV_TYPE_ACTDEACTIVATE_REQ_PRIORITY:B = 0x5t

.field private static final TLV_TYPE_ACTDEACTIVATE_REQ_SAI_LIST:B = 0x10t

.field private static final TLV_TYPE_ACTDEACTIVATE_RESP_ACTTMGI:B = 0x11t

.field private static final TLV_TYPE_ACTDEACTIVATE_RESP_ACT_CODE:B = 0x2t

.field private static final TLV_TYPE_ACTDEACTIVATE_RESP_DEACTTMGI:B = 0x12t

.field private static final TLV_TYPE_ACTDEACTIVATE_RESP_DEACT_CODE:B = 0x3t

.field private static final TLV_TYPE_ACTIVATE_REQ_EARFCN_LIST:B = 0x5t

.field private static final TLV_TYPE_ACTIVATE_REQ_PRIORITY:B = 0x4t

.field private static final TLV_TYPE_ACTIVATE_REQ_SAI_LIST:B = 0x10t

.field private static final TLV_TYPE_ACTIVATE_REQ_TMGI:B = 0x3t

.field private static final TLV_TYPE_ACTIVATE_RESP_TMGI:B = 0x11t

.field private static final TLV_TYPE_ACTIVELOGPACKETID_REQ_PACKET_ID_LIST:S = 0x2s

.field private static final TLV_TYPE_ACTIVELOGPACKETID_RESP_PACKET_ID_LIST:S = 0x2s

.field private static final TLV_TYPE_COMMON_REQ_CALL_ID:B = 0x2t

.field private static final TLV_TYPE_COMMON_REQ_TRACE_ID:B = 0x1t

.field private static final TLV_TYPE_COMMON_RESP_CALL_ID:B = 0x10t

.field private static final TLV_TYPE_COMMON_RESP_CODE:B = 0x2t

.field private static final TLV_TYPE_COMMON_RESP_TRACE_ID:B = 0x1t

.field private static final TLV_TYPE_CONTENT_DESCRIPTION_REQ_PARAMETER_ARRAY:B = 0x10t

.field private static final TLV_TYPE_CONTENT_DESCRIPTION_REQ_TMGI:B = 0x3t

.field private static final TLV_TYPE_DEACTIVATE_REQ_TMGI:B = 0x3t

.field private static final TLV_TYPE_DEACTIVATE_RESP_TMGI:B = 0x11t

.field private static final TLV_TYPE_DELIVERLOGPACKET_REQ_LOG_PACKET:S = 0x3s

.field private static final TLV_TYPE_DELIVERLOGPACKET_REQ_PACKET_ID:S = 0x2s

.field private static final TLV_TYPE_ENABLE_RESP_IFNAME:B = 0x11t

.field private static final TLV_TYPE_ENABLE_RESP_IF_INDEX:B = 0x12t

.field private static final TLV_TYPE_GET_ACTIVE_RESP_TMGI_ARRAY:B = 0x10t

.field private static final TLV_TYPE_GET_AVAILABLE_RESP_TMGI_ARRAY:B = 0x10t

.field private static final TLV_TYPE_GET_COVERAGE_STATE_RESP_STATE:B = 0x10t

.field private static final TLV_TYPE_GET_E911_RESP_STATE:S = 0x10s

.field private static final TLV_TYPE_GET_EMBMS_STATUS_RESP:S = 0x2s

.field private static final TLV_TYPE_GET_INTERESTED_TMGI_LIST_RESP_TMGI:B = 0x3t

.field private static final TLV_TYPE_GET_PLMN_LIST_RESP_PLMN_LIST:B = 0x2t

.field private static final TLV_TYPE_GET_SIG_STRENGTH_RESP_ACTIVE_TMGI_LIST:B = 0x14t

.field private static final TLV_TYPE_GET_SIG_STRENGTH_RESP_EXCESS_SNR:B = 0x12t

.field private static final TLV_TYPE_GET_SIG_STRENGTH_RESP_MBSFN_AREA_ID:B = 0x10t

.field private static final TLV_TYPE_GET_SIG_STRENGTH_RESP_NUMBER_OF_TMGI_PER_MBSFN:B = 0x13t

.field private static final TLV_TYPE_GET_SIG_STRENGTH_RESP_SNR:B = 0x11t

.field private static final TLV_TYPE_GET_TIME_RESP_DAY_LIGHT_SAVING:B = 0x10t

.field private static final TLV_TYPE_GET_TIME_RESP_LEAP_SECONDS:B = 0x11t

.field private static final TLV_TYPE_GET_TIME_RESP_LOCAL_TIME_OFFSET:B = 0x12t

.field private static final TLV_TYPE_GET_TIME_RESP_TIME_MSECONDS:B = 0x3t

.field private static final TLV_TYPE_SET_TIME_REQ_SNTP_SUCCESS:B = 0x1t

.field private static final TLV_TYPE_SET_TIME_REQ_TIME_MSECONDS:B = 0x10t

.field private static final TLV_TYPE_SET_TIME_REQ_TIME_STAMP:B = 0x11t

.field private static final TLV_TYPE_UNSOL_ACTIVE_IND_TMGI_ARRAY:S = 0x2s

.field private static final TLV_TYPE_UNSOL_AVAILABLE_IND_TMGI_ARRAY_OR_RESPONSE_CODE:S = 0x2s

.field private static final TLV_TYPE_UNSOL_CELL_ID_IND_CID:S = 0x4s

.field private static final TLV_TYPE_UNSOL_CELL_ID_IND_MCC:S = 0x2s

.field private static final TLV_TYPE_UNSOL_CELL_ID_IND_MNC:S = 0x3s

.field private static final TLV_TYPE_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL_CONTENT_CONTROL:S = 0x10s

.field private static final TLV_TYPE_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL_STATUS_CONTROL:S = 0x11s

.field private static final TLV_TYPE_UNSOL_CONTENT_DESC_PER_OBJ_CONTROL_TMGI:S = 0x2s

.field private static final TLV_TYPE_UNSOL_COVERAGE_IND_STATE_OR_RESPONSE_CODE:S = 0x2s

.field private static final TLV_TYPE_UNSOL_E911_STATE_OR_RESPONSE_CODE:S = 0x2s

.field private static final TLV_TYPE_UNSOL_EMBMS_STATUS:S = 0x1s

.field private static final TLV_TYPE_UNSOL_OOS_IND_STATE:S = 0x2s

.field private static final TLV_TYPE_UNSOL_OOS_IND_TMGI_ARRAY:S = 0x3s

.field private static final TLV_TYPE_UNSOL_RADIO_STATE:S = 0x2s

.field private static final TLV_TYPE_UNSOL_SAI_IND_AVAILABLE_SAI_LIST:S = 0x4s

.field private static final TLV_TYPE_UNSOL_SAI_IND_CAMPED_SAI_LIST:S = 0x2s

.field private static final TLV_TYPE_UNSOL_SAI_IND_SAI_PER_GROUP_LIST:S = 0x3s

.field private static final TLV_TYPE_UNSOL_SIB16:S = 0x1s

.field private static final TLV_TYPE_UNSOL_STATE_IND_IF_INDEX:S = 0x3s

.field private static final TLV_TYPE_UNSOL_STATE_IND_IP_ADDRESS:S = 0x2s

.field private static final TLV_TYPE_UNSOL_STATE_IND_STATE:S = 0x1s

.field private static final TWO_BYTES:S = 0x2s

.field private static final UNSOL_BASE_QCRILHOOK:I = 0x1000

.field public static final UNSOL_TYPE_ACTIVE_TMGI_LIST:I = 0x2

.field public static final UNSOL_TYPE_AVAILABLE_TMGI_LIST:I = 0x4

.field public static final UNSOL_TYPE_BROADCAST_COVERAGE:I = 0x3

.field public static final UNSOL_TYPE_CELL_ID:I = 0x6

.field public static final UNSOL_TYPE_CONTENT_DESC_PER_OBJ_CONTROL:I = 0xb

.field public static final UNSOL_TYPE_E911_STATE:I = 0xa

.field public static final UNSOL_TYPE_EMBMSOEMHOOK_READY_CALLBACK:I = 0x1001

.field public static final UNSOL_TYPE_EMBMS_STATUS:I = 0xc

.field public static final UNSOL_TYPE_GET_INTERESTED_TMGI_LIST:I = 0xd

.field public static final UNSOL_TYPE_OOS_STATE:I = 0x5

.field public static final UNSOL_TYPE_RADIO_STATE:I = 0x7

.field public static final UNSOL_TYPE_SAI_LIST:I = 0x8

.field public static final UNSOL_TYPE_SIB16_COVERAGE:I = 0x9

.field public static final UNSOL_TYPE_STATE_CHANGE:I = 0x1

.field private static mRefCount:I

.field private static sInstance:Lcom/qualcomm/qcrilhook/EmbmsOemHook;


# instance fields
.field private mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

.field private mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 38
    const-string v0, "EmbmsOemHook"

    sput-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    .line 238
    const/4 v0, 0x0

    sput v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRefCount:I

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;

    .line 244
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    .line 245
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v1, "EmbmsOemHook ()"

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 246
    invoke-static {p1}, Lcom/qualcomm/qcrilhook/QmiOemHook;->getInstance(Landroid/content/Context;)Lcom/qualcomm/qcrilhook/QmiOemHook;

    move-result-object v0

    iput-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    .line 247
    const/4 v0, 0x2

    const/4 v1, 0x1

    invoke-static {v0, p0, v1}, Lcom/qualcomm/qcrilhook/QmiOemHook;->registerService(SLandroid/os/Handler;I)V

    .line 249
    const/4 v1, 0x0

    invoke-static {p0, v0, v1}, Lcom/qualcomm/qcrilhook/QmiOemHook;->registerOnReadyCb(Landroid/os/Handler;ILjava/lang/Object;)V

    .line 250
    new-instance v0, Lorg/codeaurora/telephony/utils/RegistrantList;

    invoke-direct {v0}, Lorg/codeaurora/telephony/utils/RegistrantList;-><init>()V

    iput-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    .line 251
    return-void
.end method

.method static synthetic access$000()Ljava/lang/String;
    .locals 1

    .line 37
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)[B
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p1, "x1"    # Ljava/nio/ByteBuffer;

    .line 37
    invoke-direct {p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->parseTmgi(Ljava/nio/ByteBuffer;)[B

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$200(Lcom/qualcomm/qcrilhook/EmbmsOemHook;S[B)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p1, "x1"    # S
    .param p2, "x2"    # [B

    .line 37
    invoke-direct {p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->byteArrayToQmiArray(S[B)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$300(Lcom/qualcomm/qcrilhook/EmbmsOemHook;S[I)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p1, "x1"    # S
    .param p2, "x2"    # [I

    .line 37
    invoke-direct {p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->intArrayToQmiArray(S[I)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$400(Lcom/qualcomm/qcrilhook/EmbmsOemHook;S[IS)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p1, "x1"    # S
    .param p2, "x2"    # [I
    .param p3, "x3"    # S

    .line 37
    invoke-direct {p0, p1, p2, p3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->intArrayToQmiArray(S[IS)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$500(Lcom/qualcomm/qcrilhook/EmbmsOemHook;S[B)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p1, "x1"    # S
    .param p2, "x2"    # [B

    .line 37
    invoke-direct {p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->tmgiListArrayToQmiArray(S[B)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$600(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)[B
    .locals 1
    .param p0, "x0"    # Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .param p1, "x1"    # Ljava/nio/ByteBuffer;

    .line 37
    invoke-direct {p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->parseActiveTmgi(Ljava/nio/ByteBuffer;)[B

    move-result-object v0

    return-object v0
.end method

.method private byteArrayToQmiArray(S[B)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 4
    .param p1, "vSize"    # S
    .param p2, "arr"    # [B
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(S[B)",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray<",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;",
            ">;"
        }
    .end annotation

    .line 1642
    array-length v0, p2

    new-array v0, v0, [Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    .line 1643
    .local v0, "qmiByteArray":[Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    array-length v2, p2

    if-ge v1, v2, :cond_0

    .line 1644
    new-instance v2, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    aget-byte v3, p2, v1

    invoke-direct {v2, v3}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;-><init>(B)V

    aput-object v2, v0, v1

    .line 1643
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 1646
    .end local v1    # "i":I
    :cond_0
    new-instance v1, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    const-class v2, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    invoke-direct {v1, v0, v2, p1}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;-><init>([Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Ljava/lang/Class;S)V

    return-object v1
.end method

.method public static bytesToHexString([B)Ljava/lang/String;
    .locals 5
    .param p0, "bytes"    # [B

    .line 765
    if-nez p0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 767
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    array-length v1, p0

    mul-int/lit8 v1, v1, 0x2

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(I)V

    .line 769
    .local v0, "ret":Ljava/lang/StringBuilder;
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    array-length v2, p0

    if-ge v1, v2, :cond_1

    .line 771
    aget-byte v2, p0, v1

    shr-int/lit8 v2, v2, 0x4

    and-int/lit8 v2, v2, 0xf

    .line 772
    .local v2, "b":I
    const-string v3, "0123456789abcdef"

    invoke-virtual {v3, v2}, Ljava/lang/String;->charAt(I)C

    move-result v4

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 773
    aget-byte v4, p0, v1

    and-int/lit8 v2, v4, 0xf

    .line 774
    invoke-virtual {v3, v2}, Ljava/lang/String;->charAt(I)C

    move-result v3

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 769
    .end local v2    # "b":I
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 777
    .end local v1    # "i":I
    :cond_1
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static declared-synchronized getInstance(Landroid/content/Context;)Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    .locals 3
    .param p0, "context"    # Landroid/content/Context;

    const-class v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    monitor-enter v0

    .line 255
    :try_start_0
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->sInstance:Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    if-nez v1, :cond_0

    .line 256
    new-instance v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    invoke-direct {v1, p0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;-><init>(Landroid/content/Context;)V

    sput-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->sInstance:Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    .line 257
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "Singleton Instance of Embms created."

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 259
    :cond_0
    sget v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRefCount:I

    add-int/lit8 v1, v1, 0x1

    sput v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRefCount:I

    .line 260
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->sInstance:Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    .line 254
    .end local p0    # "context":Landroid/content/Context;
    :catchall_0
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method private handleResponse(Ljava/util/HashMap;)V
    .locals 16
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/HashMap<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Object;",
            ">;)V"
        }
    .end annotation

    .line 324
    .local p1, "map":Ljava/util/HashMap;, "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Object;>;"
    move-object/from16 v0, p0

    move-object/from16 v1, p1

    const/16 v2, 0x8

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-virtual {v1, v3}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Short;

    invoke-virtual {v3}, Ljava/lang/Short;->shortValue()S

    move-result v3

    .line 325
    .local v3, "msgId":S
    const/4 v4, 0x2

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    invoke-virtual {v1, v5}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Integer;

    invoke-virtual {v5}, Ljava/lang/Integer;->intValue()I

    move-result v5

    .line 326
    .local v5, "responseSize":I
    const/4 v6, 0x3

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    invoke-virtual {v1, v7}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    check-cast v7, Ljava/lang/Integer;

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v7

    .line 327
    .local v7, "successStatus":I
    const/4 v8, 0x4

    invoke-static {v8}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    invoke-virtual {v1, v9}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Landroid/os/Message;

    .line 328
    .local v9, "msg":Landroid/os/Message;
    const/16 v10, 0x9

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v11

    invoke-virtual {v1, v11}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Ljava/lang/Integer;

    invoke-virtual {v11}, Ljava/lang/Integer;->intValue()I

    move-result v11

    .line 329
    .local v11, "phoneId":I
    if-eqz v9, :cond_0

    .line 330
    iput v11, v9, Landroid/os/Message;->arg1:I

    .line 332
    :cond_0
    const/4 v12, 0x6

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v13

    invoke-virtual {v1, v13}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v13

    check-cast v13, Ljava/nio/ByteBuffer;

    .line 335
    .local v13, "respByteBuf":Ljava/nio/ByteBuffer;
    sget-object v14, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v15, Ljava/lang/StringBuilder;

    invoke-direct {v15}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, " responseSize="

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " successStatus="

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, "phoneId: "

    invoke-virtual {v15, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v15, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v15}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v14, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 339
    const/4 v8, 0x5

    packed-switch v3, :pswitch_data_0

    .line 510
    :pswitch_0
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "received unexpected msgId "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_0

    .line 506
    :pswitch_1
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, " getInterestedTmgiListResponse ack successStatus="

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 508
    goto/16 :goto_0

    .line 500
    :pswitch_2
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$RequestIndication;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$RequestIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 501
    .local v2, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$RequestIndication;
    const/16 v4, 0xd

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 502
    goto/16 :goto_0

    .line 495
    .end local v2    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$RequestIndication;
    :pswitch_3
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$EmbmsStatus;

    invoke-direct {v2, v0, v13, v3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$EmbmsStatus;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;I)V

    .line 496
    .local v2, "status":Lcom/qualcomm/qcrilhook/EmbmsOemHook$EmbmsStatus;
    const/16 v4, 0xc

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 497
    goto/16 :goto_0

    .line 456
    .end local v2    # "status":Lcom/qualcomm/qcrilhook/EmbmsOemHook$EmbmsStatus;
    :pswitch_4
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetPLMNListResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetPLMNListResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 457
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 458
    goto/16 :goto_0

    .line 488
    :pswitch_5
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescPerObjectControlIndication;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescPerObjectControlIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 490
    .local v2, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescPerObjectControlIndication;
    const/16 v4, 0xb

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 491
    goto/16 :goto_0

    .line 452
    .end local v2    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescPerObjectControlIndication;
    :pswitch_6
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, " contentDescription response successStatus="

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 453
    goto/16 :goto_0

    .line 463
    :pswitch_7
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$E911StateIndication;

    invoke-direct {v2, v0, v13, v3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$E911StateIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;S)V

    .line 464
    .local v2, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$E911StateIndication;
    const/16 v4, 0xa

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 465
    goto/16 :goto_0

    .line 423
    .end local v2    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$E911StateIndication;
    :pswitch_8
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TimeResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 424
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 425
    goto/16 :goto_0

    .line 430
    :pswitch_9
    const/16 v2, 0x18

    if-ne v3, v2, :cond_1

    if-eqz v7, :cond_1

    .line 431
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Error received in EMBMSHOOK_MSG_ID_GET_SIB16_COVERAGE: "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 433
    goto/16 :goto_0

    .line 436
    :cond_1
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$Sib16Coverage;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$Sib16Coverage;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 437
    .local v2, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$Sib16Coverage;
    invoke-direct {v0, v10, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 438
    goto/16 :goto_0

    .line 418
    .end local v2    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$Sib16Coverage;
    :pswitch_a
    iput v7, v9, Landroid/os/Message;->arg1:I

    .line 419
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 420
    goto/16 :goto_0

    .line 447
    :pswitch_b
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, " deliverLogPacket response successStatus="

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 448
    goto/16 :goto_0

    .line 441
    :pswitch_c
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 442
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 443
    goto/16 :goto_0

    .line 483
    :pswitch_d
    new-instance v4, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SaiIndication;

    invoke-direct {v4, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SaiIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 484
    .local v4, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$SaiIndication;
    invoke-direct {v0, v2, v4, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 485
    goto/16 :goto_0

    .line 478
    .end local v4    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$SaiIndication;
    :pswitch_e
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$RadioStateIndication;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$RadioStateIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 479
    .local v2, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$RadioStateIndication;
    const/4 v4, 0x7

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 480
    goto/16 :goto_0

    .line 473
    .end local v2    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$RadioStateIndication;
    :pswitch_f
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$CellIdIndication;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$CellIdIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 474
    .local v2, "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$CellIdIndication;
    invoke-direct {v0, v12, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 475
    goto/16 :goto_0

    .line 357
    .end local v2    # "ind":Lcom/qualcomm/qcrilhook/EmbmsOemHook$CellIdIndication;
    :pswitch_10
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 358
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 359
    goto/16 :goto_0

    .line 468
    :pswitch_11
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$OosState;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$OosState;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 469
    .local v2, "state":Lcom/qualcomm/qcrilhook/EmbmsOemHook$OosState;
    invoke-direct {v0, v8, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 470
    goto/16 :goto_0

    .line 362
    .end local v2    # "state":Lcom/qualcomm/qcrilhook/EmbmsOemHook$OosState;
    :pswitch_12
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$StateChangeInfo;

    invoke-direct {v2, v0, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$StateChangeInfo;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;)V

    .line 363
    .local v2, "info":Lcom/qualcomm/qcrilhook/EmbmsOemHook$StateChangeInfo;
    const/4 v4, 0x1

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 364
    goto/16 :goto_0

    .line 413
    .end local v2    # "info":Lcom/qualcomm/qcrilhook/EmbmsOemHook$StateChangeInfo;
    :pswitch_13
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SigStrengthResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SigStrengthResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 414
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 415
    goto/16 :goto_0

    .line 402
    :pswitch_14
    if-ne v3, v2, :cond_2

    if-eqz v7, :cond_2

    .line 403
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Error received in EMBMSHOOK_MSG_ID_GET_COVERAGE: "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 405
    goto/16 :goto_0

    .line 408
    :cond_2
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$CoverageState;

    invoke-direct {v2, v0, v13, v3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$CoverageState;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;S)V

    .line 409
    .local v2, "cs":Lcom/qualcomm/qcrilhook/EmbmsOemHook$CoverageState;
    invoke-direct {v0, v6, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 410
    goto :goto_0

    .line 387
    .end local v2    # "cs":Lcom/qualcomm/qcrilhook/EmbmsOemHook$CoverageState;
    :pswitch_15
    if-ne v3, v8, :cond_3

    if-eqz v7, :cond_3

    .line 388
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Error received in EMBMSHOOK_MSG_ID_GET_ACTIVE: "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 390
    goto :goto_0

    .line 392
    :cond_3
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;

    invoke-direct {v2, v0, v13, v3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;S)V

    .line 393
    .local v2, "list":Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;
    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 394
    goto :goto_0

    .line 372
    .end local v2    # "list":Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;
    :pswitch_16
    const/4 v2, 0x4

    if-ne v3, v2, :cond_4

    if-eqz v7, :cond_4

    .line 373
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "Error received in EMBMSHOOK_MSG_ID_GET_AVAILABLE: "

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v2, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 375
    goto :goto_0

    .line 377
    :cond_4
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;

    invoke-direct {v2, v0, v13, v3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;Ljava/nio/ByteBuffer;S)V

    .line 378
    .restart local v2    # "list":Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;
    const/4 v4, 0x4

    invoke-direct {v0, v4, v2, v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 379
    goto :goto_0

    .line 352
    .end local v2    # "list":Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiListIndication;
    :pswitch_17
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 353
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 354
    goto :goto_0

    .line 346
    :pswitch_18
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$DisableResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$DisableResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 347
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 348
    goto :goto_0

    .line 341
    :pswitch_19
    new-instance v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook$EnableResponse;

    invoke-direct {v2, v0, v7, v13}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$EnableResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/nio/ByteBuffer;)V

    iput-object v2, v9, Landroid/os/Message;->obj:Ljava/lang/Object;

    .line 342
    invoke-virtual {v9}, Landroid/os/Message;->sendToTarget()V

    .line 343
    nop

    .line 513
    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_19
        :pswitch_18
        :pswitch_17
        :pswitch_17
        :pswitch_16
        :pswitch_15
        :pswitch_0
        :pswitch_0
        :pswitch_14
        :pswitch_13
        :pswitch_0
        :pswitch_12
        :pswitch_15
        :pswitch_14
        :pswitch_0
        :pswitch_16
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_3
        :pswitch_2
        :pswitch_1
    .end packed-switch
.end method

.method private intArrayToQmiArray(S[I)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 6
    .param p1, "vSize"    # S
    .param p2, "arr"    # [I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(S[I)",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray<",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;",
            ">;"
        }
    .end annotation

    .line 1683
    if-nez p2, :cond_0

    const/4 v0, 0x0

    goto :goto_0

    :cond_0
    array-length v0, p2

    .line 1685
    .local v0, "length":I
    :goto_0
    new-array v1, v0, [Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    .line 1686
    .local v1, "qmiIntArray":[Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_1
    if-ge v2, v0, :cond_1

    .line 1687
    new-instance v3, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    aget v4, p2, v2

    int-to-long v4, v4

    invoke-direct {v3, v4, v5}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;-><init>(J)V

    aput-object v3, v1, v2

    .line 1686
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 1689
    .end local v2    # "i":I
    :cond_1
    new-instance v2, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    const-class v3, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    invoke-direct {v2, v1, v3, p1}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;-><init>([Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Ljava/lang/Class;S)V

    return-object v2
.end method

.method private intArrayToQmiArray(S[IS)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 6
    .param p1, "vSize"    # S
    .param p2, "arr"    # [I
    .param p3, "numOfElements"    # S
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(S[IS)",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray<",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;",
            ">;"
        }
    .end annotation

    .line 1702
    if-nez p2, :cond_0

    const/4 v0, 0x0

    goto :goto_0

    :cond_0
    array-length v0, p2

    .line 1704
    .local v0, "length":I
    :goto_0
    new-array v1, v0, [Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    .line 1705
    .local v1, "qmiIntArray":[Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_1
    if-ge v2, v0, :cond_1

    .line 1706
    new-instance v3, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    aget v4, p2, v2

    int-to-long v4, v4

    invoke-direct {v3, v4, v5}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;-><init>(J)V

    aput-object v3, v1, v2

    .line 1705
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 1708
    .end local v2    # "i":I
    :cond_1
    new-instance v2, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    const-class v3, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiInteger;

    invoke-direct {v2, v1, v3, p1, p3}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;-><init>([Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Ljava/lang/Class;SS)V

    return-object v2
.end method

.method private notifyUnsol(ILjava/lang/Object;I)V
    .locals 5
    .param p1, "type"    # I
    .param p2, "payload"    # Ljava/lang/Object;
    .param p3, "phoneId"    # I

    .line 519
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$UnsolObject;

    invoke-direct {v0, p0, p1, p2, p3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$UnsolObject;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;ILjava/lang/Object;I)V

    .line 520
    .local v0, "obj":Lcom/qualcomm/qcrilhook/EmbmsOemHook$UnsolObject;
    new-instance v1, Lorg/codeaurora/telephony/utils/AsyncResult;

    const/4 v2, 0x0

    invoke-direct {v1, v2, v0, v2}, Lorg/codeaurora/telephony/utils/AsyncResult;-><init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Throwable;)V

    .line 521
    .local v1, "ar":Lorg/codeaurora/telephony/utils/AsyncResult;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "Notifying registrants type = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 522
    iget-object v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    invoke-virtual {v2, v1}, Lorg/codeaurora/telephony/utils/RegistrantList;->notifyRegistrants(Lorg/codeaurora/telephony/utils/AsyncResult;)V

    .line 523
    return-void
.end method

.method private parseActiveTmgi(Ljava/nio/ByteBuffer;)[B
    .locals 8
    .param p1, "buf"    # Ljava/nio/ByteBuffer;

    .line 1043
    const/4 v0, 0x0

    .line 1044
    .local v0, "index":I
    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->getShort()S

    move-result v1

    .line 1045
    .local v1, "totalTmgis":S
    mul-int/lit8 v2, v1, 0x6

    new-array v2, v2, [B

    .line 1047
    .local v2, "tmgi":[B
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v1, :cond_1

    .line 1048
    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->get()B

    move-result v4

    .line 1049
    .local v4, "tmgiLength":B
    const/4 v5, 0x0

    .local v5, "j":I
    :goto_1
    if-ge v5, v4, :cond_0

    .line 1050
    add-int/lit8 v6, v0, 0x1

    .end local v0    # "index":I
    .local v6, "index":I
    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->get()B

    move-result v7

    aput-byte v7, v2, v0

    .line 1049
    add-int/lit8 v5, v5, 0x1

    move v0, v6

    goto :goto_1

    .line 1047
    .end local v4    # "tmgiLength":B
    .end local v5    # "j":I
    .end local v6    # "index":I
    .restart local v0    # "index":I
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1053
    .end local v3    # "i":I
    :cond_1
    return-object v2
.end method

.method private parseTmgi(Ljava/nio/ByteBuffer;)[B
    .locals 8
    .param p1, "buf"    # Ljava/nio/ByteBuffer;

    .line 1029
    const/4 v0, 0x0

    .line 1030
    .local v0, "index":I
    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->get()B

    move-result v1

    .line 1031
    .local v1, "totalTmgis":B
    mul-int/lit8 v2, v1, 0x6

    new-array v2, v2, [B

    .line 1033
    .local v2, "tmgi":[B
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v1, :cond_1

    .line 1034
    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->get()B

    move-result v4

    .line 1035
    .local v4, "tmgiLength":B
    const/4 v5, 0x0

    .local v5, "j":I
    :goto_1
    if-ge v5, v4, :cond_0

    .line 1036
    add-int/lit8 v6, v0, 0x1

    .end local v0    # "index":I
    .local v6, "index":I
    invoke-virtual {p1}, Ljava/nio/ByteBuffer;->get()B

    move-result v7

    aput-byte v7, v2, v0

    .line 1035
    add-int/lit8 v5, v5, 0x1

    move v0, v6

    goto :goto_1

    .line 1033
    .end local v4    # "tmgiLength":B
    .end local v5    # "j":I
    .end local v6    # "index":I
    .restart local v0    # "index":I
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1039
    .end local v3    # "i":I
    :cond_1
    return-object v2
.end method

.method private tmgiListArrayToQmiArray(S[B)Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;
    .locals 10
    .param p1, "vSize"    # S
    .param p2, "tmgiList"    # [B
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(S[B)",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray<",
            "Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;",
            ">;"
        }
    .end annotation

    .line 1654
    if-nez p2, :cond_0

    const/4 v0, 0x0

    goto :goto_0

    :cond_0
    array-length v0, p2

    .line 1656
    .local v0, "length":I
    :goto_0
    div-int/lit8 v1, v0, 0x6

    .line 1657
    .local v1, "numOfTmgi":I
    mul-int/lit8 v2, v1, 0x1

    add-int/2addr v0, v2

    .line 1658
    new-array v2, v0, [Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    .line 1659
    .local v2, "qmiByteArray":[Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;
    const/4 v3, 0x0

    .line 1664
    .local v3, "index":I
    const/4 v4, 0x0

    .local v4, "i":I
    :goto_1
    if-ge v4, v1, :cond_2

    .line 1665
    add-int/lit8 v5, v3, 0x1

    .end local v3    # "index":I
    .local v5, "index":I
    new-instance v6, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    const/4 v7, 0x6

    invoke-direct {v6, v7}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;-><init>(I)V

    aput-object v6, v2, v3

    .line 1666
    mul-int/lit8 v3, v4, 0x6

    .local v3, "j":I
    :goto_2
    add-int/lit8 v6, v4, 0x1

    mul-int/2addr v6, v7

    if-ge v3, v6, :cond_1

    .line 1667
    add-int/lit8 v6, v5, 0x1

    .end local v5    # "index":I
    .local v6, "index":I
    new-instance v8, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    aget-byte v9, p2, v3

    invoke-direct {v8, v9}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;-><init>(B)V

    aput-object v8, v2, v5

    .line 1666
    add-int/lit8 v3, v3, 0x1

    move v5, v6

    goto :goto_2

    .line 1664
    .end local v3    # "j":I
    .end local v6    # "index":I
    .restart local v5    # "index":I
    :cond_1
    add-int/lit8 v4, v4, 0x1

    move v3, v5

    goto :goto_1

    .line 1674
    .end local v4    # "i":I
    .end local v5    # "index":I
    .local v3, "index":I
    :cond_2
    const/4 v4, 0x7

    .line 1675
    .local v4, "numOfElements":S
    new-instance v5, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;

    const-class v6, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiByte;

    invoke-direct {v5, v2, v6, p1, v4}, Lcom/qualcomm/qcrilhook/QmiPrimitiveTypes$QmiArray;-><init>([Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Ljava/lang/Class;SS)V

    return-object v5
.end method


# virtual methods
.method public actDeactTmgi(IB[B[BI[I[ILandroid/os/Message;I)I
    .locals 12
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "actTmgi"    # [B
    .param p4, "deActTmgi"    # [B
    .param p5, "priority"    # I
    .param p6, "saiList"    # [I
    .param p7, "earfcnList"    # [I
    .param p8, "msg"    # Landroid/os/Message;
    .param p9, "phoneId"    # I

    .line 608
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "actDeactTmgi called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move/from16 v2, p9

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 609
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactRequest;

    move-object v3, v0

    move-object v4, p0

    move v5, p1

    move v6, p2

    move-object v7, p3

    move-object/from16 v8, p4

    move/from16 v9, p5

    move-object/from16 v10, p6

    move-object/from16 v11, p7

    invoke-direct/range {v3 .. v11}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB[B[BI[I[I)V

    move-object v1, v0

    .line 613
    .local v1, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactRequest;
    move-object v10, p0

    :try_start_0
    iget-object v3, v10, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v4, 0x2

    const/16 v5, 0x11

    .line 615
    invoke-virtual {v1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactRequest;->getTypes()[S

    move-result-object v6

    invoke-virtual {v1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActDeactRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v7

    .line 613
    move-object/from16 v8, p8

    move/from16 v9, p9

    invoke-virtual/range {v3 .. v9}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 619
    nop

    .line 620
    const/4 v0, 0x0

    return v0

    .line 616
    :catch_0
    move-exception v0

    .line 617
    .local v0, "e":Ljava/io/IOException;
    sget-object v3, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v4, "IOException occurred during activate-deactivate !!!!!!"

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 618
    const/4 v3, -0x1

    return v3
.end method

.method public activateTmgi(IB[BI[I[ILandroid/os/Message;I)I
    .locals 11
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "tmgi"    # [B
    .param p4, "priority"    # I
    .param p5, "saiList"    # [I
    .param p6, "earfcnList"    # [I
    .param p7, "msg"    # Landroid/os/Message;
    .param p8, "phoneId"    # I

    .line 569
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "activateTmgi called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move/from16 v2, p8

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 570
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiActivateRequest;

    move-object v3, v0

    move-object v4, p0

    move v5, p1

    move v6, p2

    move-object v7, p3

    move v8, p4

    move-object/from16 v9, p5

    move-object/from16 v10, p6

    invoke-direct/range {v3 .. v10}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiActivateRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB[BI[I[I)V

    move-object v1, v0

    .line 574
    .local v1, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiActivateRequest;
    move-object v10, p0

    :try_start_0
    iget-object v3, v10, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v4, 0x2

    const/4 v5, 0x2

    .line 576
    invoke-virtual {v1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiActivateRequest;->getTypes()[S

    move-result-object v6

    invoke-virtual {v1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiActivateRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v7

    .line 574
    move-object/from16 v8, p7

    move/from16 v9, p8

    invoke-virtual/range {v3 .. v9}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 580
    nop

    .line 581
    const/4 v0, 0x0

    return v0

    .line 577
    :catch_0
    move-exception v0

    .line 578
    .local v0, "e":Ljava/io/IOException;
    sget-object v3, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v4, "IOException occurred during activate !!!!!!"

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 579
    const/4 v3, -0x1

    return v3
.end method

.method public contentDescription(IB[BI[I[ILandroid/os/Message;I)I
    .locals 17
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "tmgi"    # [B
    .param p4, "numberOfParameter"    # I
    .param p5, "parameterCode"    # [I
    .param p6, "parameterValue"    # [I
    .param p7, "msg"    # Landroid/os/Message;
    .param p8, "phoneId"    # I

    move/from16 v1, p4

    move-object/from16 v2, p5

    move-object/from16 v3, p6

    .line 880
    const/4 v4, -0x1

    :try_start_0
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v6, "contentDescription called on PhoneId: "

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_3

    move/from16 v6, p8

    :try_start_1
    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v0, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_2

    .line 881
    const-string v0, " parameterValue = "

    const/4 v5, 0x0

    if-eqz v2, :cond_0

    if-nez v3, :cond_1

    .line 882
    :cond_0
    :try_start_2
    sget-object v7, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "contentDescription: either parameterCode or parameterValue is nullparameterCode = "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 885
    new-array v7, v5, [I
    :try_end_2
    .catch Ljava/io/IOException; {:try_start_2 .. :try_end_2} :catch_2

    move-object v2, v7

    .line 886
    .end local p5    # "parameterCode":[I
    .local v2, "parameterCode":[I
    :try_start_3
    new-array v7, v5, [I
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_1

    move-object v3, v7

    .line 889
    .end local p6    # "parameterValue":[I
    .local v3, "parameterValue":[I
    :cond_1
    :try_start_4
    array-length v7, v2

    if-ne v1, v7, :cond_4

    array-length v7, v3

    if-ne v1, v7, :cond_4

    array-length v7, v2

    array-length v8, v3

    if-eq v7, v8, :cond_2

    goto :goto_1

    .line 900
    :cond_2
    mul-int/lit8 v0, v1, 0x2

    .line 901
    .local v0, "parameterArraySize":I
    const/4 v7, 0x0

    .line 902
    .local v7, "pointer":I
    new-array v8, v0, [I

    move-object v15, v8

    .line 903
    .local v15, "parameterArray":[I
    const/4 v8, 0x0

    move/from16 v16, v7

    .end local v7    # "pointer":I
    .local v8, "i":I
    .local v16, "pointer":I
    :goto_0
    if-ge v8, v0, :cond_3

    .line 904
    aget v7, v2, v16

    aput v7, v15, v8

    .line 905
    add-int/lit8 v7, v8, 0x1

    aget v9, v3, v16

    aput v9, v15, v7

    .line 906
    add-int/lit8 v16, v16, 0x1

    .line 903
    add-int/lit8 v8, v8, 0x2

    goto :goto_0

    .line 908
    .end local v8    # "i":I
    :cond_3
    sget-object v7, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "contentDescription: parameterArray: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 909
    invoke-static {v15}, Ljava/util/Arrays;->toString([I)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    .line 908
    invoke-static {v7, v8}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 911
    new-instance v7, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;

    move-object v9, v7

    move-object/from16 v10, p0

    move/from16 v11, p1

    move/from16 v12, p2

    move-object/from16 v13, p3

    move-object v14, v15

    invoke-direct/range {v9 .. v14}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB[B[I)V

    move-object v14, v7

    .line 914
    .local v14, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;
    move-object/from16 v13, p0

    iget-object v7, v13, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v8, 0x2

    const/16 v9, 0x1d

    .line 915
    invoke-virtual {v14}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;->getTypes()[S

    move-result-object v10

    invoke-virtual {v14}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v11

    .line 914
    move-object/from16 v12, p7

    move/from16 v13, p8

    invoke-virtual/range {v7 .. v13}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V

    .line 919
    .end local v0    # "parameterArraySize":I
    .end local v14    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$ContentDescriptionReq;
    .end local v15    # "parameterArray":[I
    .end local v16    # "pointer":I
    nop

    .line 921
    return v5

    .line 892
    :cond_4
    :goto_1
    sget-object v5, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "contentDescription: Invalid input, numberOfParameter = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v8, " parameterCode = "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v7, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v5, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_0

    .line 895
    return v4

    .line 916
    :catch_0
    move-exception v0

    goto :goto_2

    .end local v3    # "parameterValue":[I
    .restart local p6    # "parameterValue":[I
    :catch_1
    move-exception v0

    goto :goto_2

    .end local v2    # "parameterCode":[I
    .restart local p5    # "parameterCode":[I
    :catch_2
    move-exception v0

    goto :goto_2

    :catch_3
    move-exception v0

    move/from16 v6, p8

    .line 917
    .end local p5    # "parameterCode":[I
    .end local p6    # "parameterValue":[I
    .local v0, "e":Ljava/io/IOException;
    .restart local v2    # "parameterCode":[I
    .restart local v3    # "parameterValue":[I
    :goto_2
    sget-object v5, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v7, "IOException occurred during contentDescription !!!!!!"

    invoke-static {v5, v7}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 918
    return v4
.end method

.method public deactivateTmgi(IB[BLandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "tmgi"    # [B
    .param p4, "msg"    # Landroid/os/Message;
    .param p5, "phoneId"    # I

    .line 588
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "deactivateTmgi called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 589
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiDeActivateRequest;

    invoke-direct {v0, p0, p1, p3, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiDeActivateRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I[BB)V

    .line 592
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiDeActivateRequest;
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/4 v3, 0x3

    .line 594
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiDeActivateRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$TmgiDeActivateRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 592
    move-object v6, p4

    move v7, p5

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 598
    nop

    .line 599
    const/4 v1, 0x0

    return v1

    .line 595
    :catch_0
    move-exception v1

    .line 596
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "IOException occurred during deactivate !!!!!!"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 597
    const/4 v2, -0x1

    return v2
.end method

.method public deliverLogPacket(II[BI)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "logPacketId"    # I
    .param p3, "logPacket"    # [B
    .param p4, "phoneId"    # I

    .line 744
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "deliverLogPacket called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 745
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$DeliverLogPacketRequest;

    invoke-direct {v0, p0, p1, p2, p3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$DeliverLogPacketRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;II[B)V

    .line 748
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$DeliverLogPacketRequest;
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x16

    .line 750
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$DeliverLogPacketRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$DeliverLogPacketRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    const/4 v6, 0x0

    .line 748
    move v7, p4

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 754
    nop

    .line 755
    const/4 v1, 0x0

    return v1

    .line 751
    :catch_0
    move-exception v1

    .line 752
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "IOException occurred during deliver logPacket !!!!!!"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 753
    const/4 v2, -0x1

    return v2
.end method

.method public disable(IBLandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "msg"    # Landroid/os/Message;
    .param p4, "phoneId"    # I

    .line 705
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "disable called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 706
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;

    invoke-direct {v0, p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB)V

    .line 708
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/4 v3, 0x1

    .line 710
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 708
    move-object v6, p3

    move v7, p4

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 714
    nop

    .line 715
    const/4 v1, 0x0

    return v1

    .line 711
    :catch_0
    move-exception v1

    .line 712
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "IOException occurred during disable !!!!!!"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 713
    const/4 v2, -0x1

    return v2
.end method

.method public declared-synchronized dispose()V
    .locals 3

    monitor-enter p0

    .line 269
    :try_start_0
    sget v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRefCount:I

    add-int/lit8 v0, v0, -0x1

    sput v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRefCount:I

    if-nez v0, :cond_0

    .line 270
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v1, "dispose(): Unregistering receiver"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 271
    const/4 v0, 0x2

    invoke-static {v0}, Lcom/qualcomm/qcrilhook/QmiOemHook;->unregisterService(I)V

    .line 272
    invoke-static {p0}, Lcom/qualcomm/qcrilhook/QmiOemHook;->unregisterOnReadyCb(Landroid/os/Handler;)V

    .line 273
    iget-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/QmiOemHook;->dispose()V

    .line 274
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    .line 275
    sput-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->sInstance:Lcom/qualcomm/qcrilhook/EmbmsOemHook;

    .line 276
    iget-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    invoke-virtual {v0}, Lorg/codeaurora/telephony/utils/RegistrantList;->removeCleared()V

    goto :goto_0

    .line 278
    .end local p0    # "this":Lcom/qualcomm/qcrilhook/EmbmsOemHook;
    :cond_0
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "dispose mRefCount = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRefCount:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 280
    :goto_0
    monitor-exit p0

    return-void

    .line 268
    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public enable(ILandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "msg"    # Landroid/os/Message;
    .param p3, "phoneId"    # I

    .line 552
    :try_start_0
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "enable called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 553
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 555
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/4 v3, 0x0

    .line 556
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 555
    move-object v6, p2

    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 560
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 561
    const/4 v0, 0x0

    return v0

    .line 557
    :catch_0
    move-exception v0

    .line 558
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during enable !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 559
    const/4 v1, -0x1

    return v1
.end method

.method public getActiveLogPacketIDs(I[ILandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "supportedLogPacketIdList"    # [I
    .param p3, "msg"    # Landroid/os/Message;
    .param p4, "phoneId"    # I

    .line 724
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getActiveLogPacketIDs called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 725
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsRequest;

    invoke-direct {v0, p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I[I)V

    .line 729
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsRequest;
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x15

    .line 731
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$ActiveLogPacketIDsRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 729
    move-object v6, p3

    move v7, p4

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 735
    nop

    .line 736
    const/4 v1, 0x0

    return v1

    .line 732
    :catch_0
    move-exception v1

    .line 733
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "IOException occurred during activate log packet ID\'s !!!!!!"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 734
    const/4 v2, -0x1

    return v2
.end method

.method public getActiveTMGIList(IBI)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "phoneId"    # I

    .line 649
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getActiveTMGIList called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 650
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;

    invoke-direct {v0, p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB)V

    .line 653
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/4 v3, 0x5

    .line 655
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    const/4 v6, 0x0

    .line 653
    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 659
    nop

    .line 660
    const/4 v1, 0x0

    return v1

    .line 656
    :catch_0
    move-exception v1

    .line 657
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "IOException occurred during getActiveTMGIList !!!!!!"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 658
    const/4 v2, -0x1

    return v2
.end method

.method public getAvailableTMGIList(IBI)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "phoneId"    # I

    .line 629
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getAvailableTMGIList called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 630
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;

    invoke-direct {v0, p0, p1, p2}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB)V

    .line 633
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;
    :try_start_0
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/4 v3, 0x4

    .line 635
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GenericRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    const/4 v6, 0x0

    .line 633
    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 639
    nop

    .line 640
    const/4 v1, 0x0

    return v1

    .line 636
    :catch_0
    move-exception v1

    .line 637
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "IOException occurred during getAvailableTMGIList !!!!!!"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 638
    const/4 v2, -0x1

    return v2
.end method

.method public getCoverageState(II)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "phoneId"    # I

    .line 670
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getCoverageState called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 672
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 674
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x8

    .line 675
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    const/4 v6, 0x0

    .line 674
    move v7, p2

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 679
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 680
    const/4 v0, 0x0

    return v0

    .line 676
    :catch_0
    move-exception v0

    .line 677
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getActiveTMGIList !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 678
    const/4 v1, -0x1

    return v1
.end method

.method public getE911State(ILandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "msg"    # Landroid/os/Message;
    .param p3, "phoneId"    # I

    .line 859
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getE911State called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 861
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 863
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x1b

    .line 864
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 863
    move-object v6, p2

    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 868
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 870
    const/4 v0, 0x0

    return v0

    .line 865
    :catch_0
    move-exception v0

    .line 866
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getE911State !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 867
    const/4 v1, -0x1

    return v1
.end method

.method public getEmbmsStatus(II)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "phoneId"    # I

    .line 816
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getEmbmsStatus called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 818
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 820
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x21

    .line 821
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    const/4 v6, 0x0

    .line 820
    move v7, p2

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 825
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 827
    const/4 v0, 0x0

    return v0

    .line 822
    :catch_0
    move-exception v0

    .line 823
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getEmbmsStatus !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 824
    const/4 v1, -0x1

    return v1
.end method

.method public getInterestedTMGIListResponse(IB[BILandroid/os/Message;)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "callId"    # B
    .param p3, "tmgiList"    # [B
    .param p4, "phoneId"    # I
    .param p5, "msg"    # Landroid/os/Message;

    .line 947
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;

    invoke-direct {v0, p0, p1, p2, p3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;IB[B)V

    .line 950
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x23

    .line 951
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;->getTypes()[S

    move-result-object v4

    .line 952
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 950
    move-object v6, p5

    move v7, p4

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 956
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$GetInterestedTmgiResponse;
    nop

    .line 957
    const/4 v0, 0x0

    return v0

    .line 953
    :catch_0
    move-exception v0

    .line 954
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getInterestedTMGIListResponse !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 955
    const/4 v1, -0x1

    return v1
.end method

.method public getPLMNListRequest(ILandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "msg"    # Landroid/os/Message;
    .param p3, "phoneId"    # I

    .line 928
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getPLMNListRequest called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 930
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 932
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x1f

    .line 933
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 932
    move-object v6, p2

    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 937
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 938
    const/4 v0, 0x0

    return v0

    .line 934
    :catch_0
    move-exception v0

    .line 935
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getPLMNListRequest !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 936
    const/4 v1, -0x1

    return v1
.end method

.method public getSib16CoverageStatus(Landroid/os/Message;I)I
    .locals 3
    .param p1, "msg"    # Landroid/os/Message;
    .param p2, "phoneId"    # I

    .line 801
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getSib16CoverageStatus called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 803
    :try_start_0
    iget-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v1, 0x2

    const/16 v2, 0x18

    invoke-virtual {v0, v1, v2, p1, p2}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SSLandroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 808
    nop

    .line 809
    const/4 v0, 0x0

    return v0

    .line 805
    :catch_0
    move-exception v0

    .line 806
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getSIB16 !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 807
    const/4 v1, -0x1

    return v1
.end method

.method public getSignalStrength(ILandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "msg"    # Landroid/os/Message;
    .param p3, "phoneId"    # I

    .line 688
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getSignalStrength called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 690
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 692
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x9

    .line 693
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 692
    move-object v6, p2

    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 697
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 698
    const/4 v0, 0x0

    return v0

    .line 694
    :catch_0
    move-exception v0

    .line 695
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during enable !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 696
    const/4 v1, -0x1

    return v1
.end method

.method public getTime(ILandroid/os/Message;I)I
    .locals 8
    .param p1, "traceId"    # I
    .param p2, "msg"    # Landroid/os/Message;
    .param p3, "phoneId"    # I

    .line 784
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getTime called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 786
    :try_start_0
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;

    invoke-direct {v0, p0, p1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;I)V

    .line 788
    .local v0, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v2, 0x2

    const/16 v3, 0x1a

    .line 789
    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getTypes()[S

    move-result-object v4

    invoke-virtual {v0}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v5

    .line 788
    move-object v6, p2

    move v7, p3

    invoke-virtual/range {v1 .. v7}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 793
    .end local v0    # "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$BasicRequest;
    nop

    .line 794
    const/4 v0, 0x0

    return v0

    .line 790
    :catch_0
    move-exception v0

    .line 791
    .local v0, "e":Ljava/io/IOException;
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "IOException occurred during getTime !!!!!!"

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 792
    const/4 v1, -0x1

    return v1
.end method

.method public handleMessage(Landroid/os/Message;)V
    .locals 4
    .param p1, "msg"    # Landroid/os/Message;

    .line 287
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "received message : "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p1, Landroid/os/Message;->what:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 288
    iget-object v0, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    check-cast v0, Lorg/codeaurora/telephony/utils/AsyncResult;

    .line 290
    .local v0, "ar":Lorg/codeaurora/telephony/utils/AsyncResult;
    iget v1, p1, Landroid/os/Message;->what:I

    const/4 v2, 0x1

    if-eq v1, v2, :cond_1

    const/4 v2, 0x2

    if-eq v1, v2, :cond_0

    .line 306
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Unexpected message received from QmiOemHook what = "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v3, p1, Landroid/os/Message;->what:I

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_0

    .line 301
    :cond_0
    iget-object v1, v0, Lorg/codeaurora/telephony/utils/AsyncResult;->result:Ljava/lang/Object;

    .line 303
    .local v1, "payload":Ljava/lang/Object;
    const/16 v2, 0x1001

    const/4 v3, 0x0

    invoke-direct {p0, v2, v1, v3}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->notifyUnsol(ILjava/lang/Object;I)V

    .line 304
    goto :goto_0

    .line 292
    .end local v1    # "payload":Ljava/lang/Object;
    :cond_1
    iget-object v1, v0, Lorg/codeaurora/telephony/utils/AsyncResult;->result:Ljava/lang/Object;

    check-cast v1, Ljava/util/HashMap;

    .line 293
    .local v1, "map":Ljava/util/HashMap;, "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Object;>;"
    if-nez v1, :cond_2

    .line 294
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "Hashmap async userobj is NULL"

    invoke-static {v2, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 295
    return-void

    .line 298
    :cond_2
    invoke-direct {p0, v1}, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->handleResponse(Ljava/util/HashMap;)V

    .line 299
    nop

    .line 310
    .end local v1    # "map":Ljava/util/HashMap;, "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/Object;>;"
    :goto_0
    return-void
.end method

.method public registerForNotifications(Landroid/os/Handler;ILjava/lang/Object;)V
    .locals 4
    .param p1, "h"    # Landroid/os/Handler;
    .param p2, "what"    # I
    .param p3, "obj"    # Ljava/lang/Object;

    .line 529
    new-instance v0, Lorg/codeaurora/telephony/utils/Registrant;

    invoke-direct {v0, p1, p2, p3}, Lorg/codeaurora/telephony/utils/Registrant;-><init>(Landroid/os/Handler;ILjava/lang/Object;)V

    .line 530
    .local v0, "r":Lorg/codeaurora/telephony/utils/Registrant;
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    monitor-enter v1

    .line 531
    :try_start_0
    sget-object v2, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v3, "Adding a registrant"

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 532
    iget-object v2, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    invoke-virtual {v2, v0}, Lorg/codeaurora/telephony/utils/RegistrantList;->add(Lorg/codeaurora/telephony/utils/Registrant;)V

    .line 533
    monitor-exit v1

    .line 534
    return-void

    .line 533
    :catchall_0
    move-exception v2

    monitor-exit v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v2
.end method

.method public setTime(ZJJLandroid/os/Message;I)I
    .locals 16
    .param p1, "sntpSuccess"    # Z
    .param p2, "timeMseconds"    # J
    .param p4, "timeStamp"    # J
    .param p6, "msg"    # Landroid/os/Message;
    .param p7, "phoneId"    # I

    .line 835
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setTime called on PhoneId: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move/from16 v2, p7

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 836
    const/4 v0, 0x0

    .line 837
    .local v0, "success":B
    if-eqz p1, :cond_0

    .line 838
    const/4 v0, 0x1

    move v1, v0

    goto :goto_0

    .line 837
    :cond_0
    move v1, v0

    .line 840
    .end local v0    # "success":B
    .local v1, "success":B
    :goto_0
    sget-object v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "setTime success = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v4, " timeMseconds = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-wide/from16 v10, p2

    invoke-virtual {v3, v10, v11}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v4, " timeStamp = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-wide/from16 v12, p4

    invoke-virtual {v3, v12, v13}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 842
    new-instance v0, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SetTimeRequest;

    move-object v3, v0

    move-object/from16 v4, p0

    move v5, v1

    move-wide/from16 v6, p2

    move-wide/from16 v8, p4

    invoke-direct/range {v3 .. v9}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SetTimeRequest;-><init>(Lcom/qualcomm/qcrilhook/EmbmsOemHook;BJJ)V

    move-object v14, v0

    .line 844
    .local v14, "req":Lcom/qualcomm/qcrilhook/EmbmsOemHook$SetTimeRequest;
    move-object/from16 v15, p0

    :try_start_0
    iget-object v3, v15, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mQmiOemHook:Lcom/qualcomm/qcrilhook/QmiOemHook;

    const/4 v4, 0x2

    const/16 v5, 0x17

    .line 845
    invoke-virtual {v14}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SetTimeRequest;->getTypes()[S

    move-result-object v6

    invoke-virtual {v14}, Lcom/qualcomm/qcrilhook/EmbmsOemHook$SetTimeRequest;->getItems()[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;

    move-result-object v7

    .line 844
    move-object/from16 v8, p6

    move/from16 v9, p7

    invoke-virtual/range {v3 .. v9}, Lcom/qualcomm/qcrilhook/QmiOemHook;->sendQmiMessageAsync(SS[S[Lcom/qualcomm/qcrilhook/BaseQmiTypes$BaseQmiItemType;Landroid/os/Message;I)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 849
    nop

    .line 851
    const/4 v0, 0x0

    return v0

    .line 846
    :catch_0
    move-exception v0

    .line 847
    .local v0, "e":Ljava/io/IOException;
    sget-object v3, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v4, "IOException occured during setTime !!!!!!"

    invoke-static {v3, v4}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 848
    const/4 v3, -0x1

    return v3
.end method

.method public unregisterForNotifications(Landroid/os/Handler;)V
    .locals 3
    .param p1, "h"    # Landroid/os/Handler;

    .line 541
    iget-object v0, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    monitor-enter v0

    .line 542
    :try_start_0
    sget-object v1, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->LOG_TAG:Ljava/lang/String;

    const-string v2, "Removing a registrant"

    invoke-static {v1, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 543
    iget-object v1, p0, Lcom/qualcomm/qcrilhook/EmbmsOemHook;->mRegistrants:Lorg/codeaurora/telephony/utils/RegistrantList;

    invoke-virtual {v1, p1}, Lorg/codeaurora/telephony/utils/RegistrantList;->remove(Landroid/os/Handler;)V

    .line 544
    monitor-exit v0

    .line 545
    return-void

    .line 544
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method
