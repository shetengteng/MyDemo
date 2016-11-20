package com.stt.NetWorkDemo01.part11_RMI.test05_DGC.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloSerivce extends Remote {
	public boolean isAccess() throws RemoteException;

	public void access() throws RemoteException;

	public void bye() throws RemoteException;
}
