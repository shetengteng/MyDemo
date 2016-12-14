package com.stt.NetWorkDemo.part11_RMI.test06_activate.service;

import java.rmi.MarshalledObject;
import java.rmi.RemoteException;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationID;
import java.util.Date;

public class HelloServiceImpl extends Activatable implements HelloService {
	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * 在MarshalledObject 类型的参数data中封装了传递给构造函数的序列化数据 ，调用该类的get方法可以获取反序列化数据
	 * 
	 * @param id
	 * @param data
	 * @throws RemoteException
	 */
	protected HelloServiceImpl(ActivationID id, MarshalledObject<?> data) throws RemoteException {
		// 这里的端口设置为0表示创建一个匿名端口进行监听
		super(id, 0);
		try {
			this.name = (String) data.get();
			System.out.println("create object:" + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String echo(String msg) throws RemoteException {
		return "echo:" + msg + " from " + name;
	}

	@Override
	public Date getTime() throws RemoteException {
		System.out.println(name + " call getTime");
		return new Date();
	}
}
