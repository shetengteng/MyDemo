package com.stt.NetWorkDemo.part11_RMI.test01_HelloWorld.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.stt.NetWorkDemo.part11_RMI.test01_HelloWorld.service.HelloService;

public class SimpleClient {

	/**
	 * 打印远端的存根对象
	 * 
	 * @throws NamingException
	 */
	public static void showRemoteObjects(Context namingContext) throws NamingException {
		NamingEnumeration<NameClassPair> e = namingContext.list("rmi:");
		System.out.println("---存根---");
		System.out.println(e.hasMore());
		// System.out.println(e.next().getName());
		System.out.println("-------------");
	}

	public static void main(String[] args) {
		String url = "rmi://localhost:9999/";
		try {
			Context namingContext = new InitialContext();
			// 显示注册的存根类
			showRemoteObjects(namingContext);

			// 获取远程对象的存根
			HelloService service01 = (HelloService) namingContext.lookup(url + "HelloService01");
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
