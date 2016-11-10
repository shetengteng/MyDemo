package com.stt.NetWorkDemo01.part10_reflection.test04_remoteCallByProxy.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.stt.NetWorkDemo01.part10_reflection.test04_remoteCallByProxy.bean.Call;
import com.stt.NetWorkDemo01.part10_reflection.test04_remoteCallByProxy.service.impl.HelloServiceImpl;

public class SimpleServer {
	/**
	 * 存放远端对象的缓存
	 */
	private Map<String, Object> remoteObjects = new HashMap<>();

	/**
	 * 将一个远端的对象放入缓存中
	 * 
	 * @param className
	 * @param remoteObject
	 */
	public void register(String className, Object remoteObject) {
		remoteObjects.put(className, remoteObject);
	}

	/**
	 * 提供远端服务
	 * 
	 * @throws Exception
	 */
	public void service() throws Exception {
		ServerSocket server = new ServerSocket(8888);
		System.out.println("----server start----");
		while (true) {
			Socket socket = server.accept();
			System.out.println("recieve a socket" + socket.getInetAddress());
			InputStream in = socket.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);
			OutputStream out = socket.getOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			// 通过反序列化接收call
			Call call = (Call) objIn.readObject();
			System.out.println("recieve:" + call);
			// 通过反射调用call中的方法，执行本地对应方法，将结果放入
			call = invoke(call);
			// 输出call
			objOut.writeObject(call);

			objOut.close();
			objIn.close();
			socket.close();
		}
	}

	/**
	 * 反射调用call中的方法
	 * 
	 * @param call
	 * @return
	 */
	private Call invoke(Call call) {
		Object result = null;
		try {
			String className = call.getClassName();
			String methodName = call.getMethodName();
			Class[] paramTypes = call.getParamTypes();
			Object[] params = call.getParams();
			// 获取类
			Class classType = Class.forName(className);
			Method method = classType.getMethod(methodName, paramTypes);
			// 从缓存中取出远程端要调用的服务类对象
			// 通过类名返回相应的对象
			Object remoteObject = remoteObjects.get(className);
			if (remoteObject == null) {
				throw new Exception(className + "要调用的方法不存在");
			} else {
				result = method.invoke(remoteObject, params);
			}
			call.setResult(result);
		} catch (Exception e) {
			// 如果有异常，将异常信息放入结果中
			call.setResult(e);
		}
		return call;
	}

	public static void main(String[] args) {
		try {
			SimpleServer server = new SimpleServer();
			server.register("com.stt.NetWorkDemo01.part10_reflection.test04_remoteCallByProxy.service.HelloService",
					new HelloServiceImpl());
			server.service();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
