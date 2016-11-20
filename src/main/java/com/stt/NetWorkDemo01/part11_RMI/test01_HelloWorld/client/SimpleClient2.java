package com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo01.part11_RMI.test01_HelloWorld.service.HelloService;

public class SimpleClient2 {

	public static void main(String[] args) {
		String url = "rmi://localhost:9999/";
		try {

			// 设置JNDI属性
			Properties prop = new Properties();
			// RMI的JNDI工厂类
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			// RMI服务器端访问的地址
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context namingContext = new InitialContext(prop);

			// 获取远程对象的存根
			HelloService service01 = (HelloService) namingContext.lookup("HelloService01");
			HelloService service02 = (HelloService) namingContext.lookup(url + "HelloService02");

			System.out.println("service01 的类" + service01.getClass().getName());
			// 获取存根类的父类
			Class<?>[] classes = service01.getClass().getInterfaces();
			for (Class<?> subClass : classes) {
				System.out.println("serivce01 类实现了：" + subClass.getName());
			}

			System.out.println("调用方法-----");
			System.out.println(service02.echo("stt"));
			System.out.println(service01.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
