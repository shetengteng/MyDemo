package com.stt.NetWorkDemo.part11_RMI.test05_DGC.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;

public class HelloSerivceImpl extends UnicastRemoteObject implements HelloSerivce, Unreferenced {
	private static final long serialVersionUID = -6981207409158372888L;
	private boolean isAccessed = false;

	public HelloSerivceImpl() throws RemoteException {
	}

	@Override
	public boolean isAccess() throws RemoteException {
		return isAccessed;
	}

	@Override
	public void access() throws RemoteException {
		System.out.println("HelloSerivceImpl:已被调用");
		isAccessed = true;
	}

	@Override
	public void bye() throws RemoteException {
		System.out.println("HelloServiceImpl:被引用取消一次");
	}

	@Override
	public void unreferenced() {
		System.out.println("释放资源处理：不再被引用");
	}
}
