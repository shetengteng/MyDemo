package com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.server;

import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.service.HelloService;
import com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.service.HelloServiceImpl;

public class SimpleServer {

	public static void main(String[] args) {
		try {
			HelloService service01 = new HelloServiceImpl("service01");
			HelloService service02 = new HelloServiceImpl("service02");

			// 这里创建语句最重要：注册RMI服务端口
			LocateRegistry.createRegistry(9999);
			// 设置JNDI属性
			Properties prop = new Properties();
			// RMI的JNDI工厂类
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			// RMI服务器端访问的地址
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context namingContext = new InitialContext(prop);
			// 进行绑定操作，添加到注册表进程中，注意，名称必须是rmi:开头
			// 访问的默认端口是1099
			namingContext.rebind("HelloService01", service01);
			namingContext.rebind("rmi://localhost:9999/HelloService02", service02);

			// Naming.bind("rmi://localhost:9999/HelloService01", service01);

			System.out.println("注册完毕...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
