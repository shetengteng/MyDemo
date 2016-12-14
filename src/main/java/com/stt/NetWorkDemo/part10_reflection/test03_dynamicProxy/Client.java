package com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy;

import com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy.service.HelloService;
import com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy.service.impl.HelloServiceImpl;

public class Client {

	public static void main(String[] args) {
		HelloService helloService = new HelloServiceImpl();
		HelloService serviceProxy = HelloServiceProxyFactory.getHelloServiceProxy(helloService);
		System.out.println("---" + serviceProxy.getClass().getName());
		System.out.println("echo:" + serviceProxy.echo("stt"));

	}

}
