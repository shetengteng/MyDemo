package com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface HelloService extends Remote {
	public String echo(String msg) throws RemoteException;

	public Date getTime() throws RemoteException;
}
