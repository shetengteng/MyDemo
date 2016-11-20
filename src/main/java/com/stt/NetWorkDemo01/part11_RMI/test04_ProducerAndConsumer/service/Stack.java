package com.stt.NetWorkDemo01.part11_RMI.test04_ProducerAndConsumer.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 用于存放商品，放置于服务端，供给客户端调用的远程对象
 * 
 * @author Administrator
 *
 */
public interface Stack extends Remote {

	public String getName() throws RemoteException;

	public int getPoint() throws RemoteException;

	public String pop() throws RemoteException;

	public void push(String goods) throws RemoteException;
}
