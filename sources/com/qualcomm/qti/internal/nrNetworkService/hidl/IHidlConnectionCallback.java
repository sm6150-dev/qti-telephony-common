package com.qualcomm.qti.internal.nrNetworkService.hidl;

import org.codeaurora.internal.BearerAllocationStatus;
import org.codeaurora.internal.DcParam;
import org.codeaurora.internal.NrConfigType;
import org.codeaurora.internal.NrIconType;
import org.codeaurora.internal.SignalStrength;
import org.codeaurora.internal.Status;
import org.codeaurora.internal.Token;
import org.codeaurora.internal.UpperLayerIndInfo;

public interface IHidlConnectionCallback {
    void on5gConfigInfo(int i, Token token, Status status, NrConfigType nrConfigType);

    void on5gStatus(int i, Token token, Status status, boolean z);

    void onAnyNrBearerAllocation(int i, Token token, Status status, BearerAllocationStatus bearerAllocationStatus);

    void onEnableEndc(int i, Token token, Status status);

    void onEndcStatus(int i, Token token, Status status, boolean z);

    void onNrDcParam(int i, Token token, Status status, DcParam dcParam);

    void onNrIconType(int i, Token token, Status status, NrIconType nrIconType);

    void onSignalStrength(int i, Token token, Status status, SignalStrength signalStrength);

    void onUpperLayerIndInfo(int i, Token token, Status status, UpperLayerIndInfo upperLayerIndInfo);
}
