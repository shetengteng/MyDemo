package com.stt.NetWorkDemo.part10_reflection.test02_staticProxy;

import java.util.Date;

import com.stt.NetWorkDemo.part10_reflection.test02_staticProxy.service.HelloService;

public class HelloServiceProxy implements HelloService {

	// 表示被代理的HelloService实例
	private HelloService helloService;

	public HelloServiceProxy(HelloService helloService) {
		this.helloService = helloService;
	}

	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
	}

	@Override
	public String echo(String msg) {
		// 预处理
		System.out.println("代理类处理入参：" + msg);
		// 调用实际被代理对象的echo方法
		String result = helloService.echo(msg);
		// 事后进行处理
		result = "proxy--" + result;
		System.out.println("代理类处理结果：" + result);
		return result;
	}

	@Override
	public Date getTime() {
		System.out.println("预处理 getTime");
		Date result = helloService.getTime();
		System.out.println("事后处理 getTime：" + result);
		return result;
	}

}