package com.stt.NetWorkDemo01.part10_reflection.test04_remoteCallByProxy.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;

import com.stt.NetWorkDemo01.part10_reflection.test04_remoteCallByProxy.bean.Call;

public class ProxyFactory {

	public static Object getProxy(final Class classType, final String host, final int port) {
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
				Connector connector = null;
				try {
					connector = new Connector(host, port);
					Call call = new Call(classType.getName(), method.getName(), method.getParameterTypes(), params);
					connector.send(call);
					// 接收call
					call = (Call) connector.receive();
					Object result = call.getResult();
					// 如果远端抛出的是异常，这里需要抛出
					if (result instanceof Throwable) {
						throw new RemoteException("remoteException", (Throwable) result);
					} else {
						return result;
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(e);
				} finally {
					if (connector != null) {
						connector.close();
					}
				}
			}
		};
		// 这里由于是客户端，调用的是接口，那么接口本身就是父类，因此用Class[]{classType} 比较好
		return Proxy.newProxyInstance(classType.getClassLoader(), new Class[] { classType }, handler);
	}
}
