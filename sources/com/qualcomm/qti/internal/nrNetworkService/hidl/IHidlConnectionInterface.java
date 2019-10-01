package com.qualcomm.qti.internal.nrNetworkService.hidl;

import android.os.RemoteException;
import org.codeaurora.internal.Token;

public interface IHidlConnectionInterface {
    Token disable5g(int i) throws RemoteException;

    Token enable5g(int i) throws RemoteException;

    Token enable5gOnly(int i) throws RemoteException;

    Token enableEndc(int i, boolean z) throws RemoteException;

    Token query5gConfigInfo(int i) throws RemoteException;

    Token query5gStatus(int i) throws RemoteException;

    Token queryEndcStatus(int i) throws RemoteException;

    Token queryNrBearerAllocation(int i) throws RemoteException;

    Token queryNrDcParam(int i) throws RemoteException;

    Token queryNrIconType(int i) throws RemoteException;

    Token queryNrSignalStrength(int i) throws RemoteException;

    Token queryUpperLayerIndInfo(int i) throws RemoteException;

    void registerCallback(IHidlConnectionCallback iHidlConnectionCallback);

    void unRegisterCallback(IHidlConnectionCallback iHidlConnectionCallback);
}
