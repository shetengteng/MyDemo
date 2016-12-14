package com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.stt.NetWorkDemo.part10_reflection.test03_dynamicProxy.service.HelloService;

public class HelloServiceProxyFactory {
	/**
	 * 创建一个实现了HelloService接口的动态代理类的示例 ，参数HelloService引用被代理的HelloService实例
	 */
	public static HelloService getHelloServiceProxy(final HelloService helloService) {
		// 创建一个InvocationHandler的匿名类实例
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
				System.out.println("before calling" + method);// 预处理
				Object result = method.invoke(helloService, params);
				System.out.println("after calling" + method);// 事后处理
				return result;
			}
		};
		return (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),
				helloService.getClass().getInterfaces(), handler);
	}
}
