package com.stt.NetWorkDemo.part10_reflection.test04_remoteCallByProxy.client;

import com.stt.NetWorkDemo.part10_reflection.test04_remoteCallByProxy.service.HelloService;

public class SimpleClient {

	public static void main(String[] args) {

		HelloService service = (HelloService) ProxyFactory.getProxy(HelloService.class, "localhost", 8888);
		System.out.println("---client start---");
		System.out.println(service.echo("stt"));
		System.out.println(service.getTime());
	}
}
