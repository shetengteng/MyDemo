package com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 * 继承了UnicastRemoteObject类后，
 * 
 * @author Administrator
 *
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
	private static final long serialVersionUID = 8539516862526826703L;

	String name;

	/**
	 * 注意：构造函数必须要抛出RemoteException异常
	 * 
	 * @param name
	 * @throws RemoteException
	 */
	public HelloServiceImpl(String name) throws RemoteException {
		this.name = name;
	}

	@Override
	public String echo(String msg) throws RemoteException {
		System.out.println("name echo:" + msg);
		return "echo:" + msg + "  from:" + name;
	}

	@Override
	public Date getTime() throws RemoteException {
		System.out.println("name :" + name + " call getTime()");
		return new Date();
	}

}
