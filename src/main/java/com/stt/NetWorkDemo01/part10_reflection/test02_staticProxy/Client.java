package com.stt.NetWorkDemo01.part10_reflection.test02_staticProxy;

import com.stt.NetWorkDemo01.part10_reflection.test02_staticProxy.service.HelloService;
import com.stt.NetWorkDemo01.part10_reflection.test02_staticProxy.service.impl.HelloServiceImpl;

public class Client {

	public static void main(String[] args) {
		HelloService helloService = new HelloServiceImpl();
		HelloServiceProxy proxy = new HelloServiceProxy(helloService);
		proxy.echo("stt");
	}

}
