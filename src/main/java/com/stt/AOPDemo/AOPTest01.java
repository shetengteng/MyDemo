package com.stt.AOPDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AOPTest01 {
	/**
	 * 使用反射中的Proxy 和 InvocationHandler创建动态代理
	 * 使用Proxy 可以为一个或者多个接口动态的生成实现类--动态的创建代理类
	 * 使用Proxy 可以为一个或者多个接口动态创建实例--动态创建的代理实例
	 * Proxy提供的方法
	 * static Class<?> getProxyClass(ClassLoader loader,Class<?>...interfaces)---创建一个动态代理类，实现了inerfaces所指定的多个接口
	 * static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)---直接创建一个动态代理类对象，实现了interfaces指定的接口
	 * 执行代理对象的每个方法时，都会被替换执行InvocationHandler对象的invoke方法
	 * 如果用第一个方法创建了代理类对象，然后需要创建代理类实例对象，那么依然需要InvocationHandler对象，创建的实例对象都会有一个InvocationHandler对象与之对应
	 */
	public static void main(String[] args) {
		// 创建一个InvocationHandler对象
		InvocationHandler handler = new AOPTest01().new MyInvocationHandler();
		// 使用指定的InvocationHandler来生成一个动态代理对象
		Person p = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[] { Person.class }, handler);
		// 调用动态代理对象的walk() 和 sayHello()方法
		p.walk();
		p.sayHello("stt");
		System.out.println(p.getClass().getName());
		// 结果：每次调用接口的方法的时候，都要调用InvocationHandler,都会从该对象的invoke方法过一遍
		// 实际的普通的编程中，很少使用，但是在框架底层则会经常使用得到
	}

	private interface Person {
		void walk();

		void sayHello(String name);
	}

	private class MyInvocationHandler implements InvocationHandler {
		/**
		 * 执行动态代理的所有方法时，都会替换为如下的invoke方法
		 * proxy 代表动态代理对象
		 * method 动态代理对象要执行的方法
		 * args 动态代理对象调用目标方法中的输入实参
		 */
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("-----正在执行的方法：" + method);
			if (args != null && args.length != 0) {
				System.out.println("-----执行方法传入的实参:");
				for (Object val : args) {
					System.out.println(val);
				}
			} else {
				System.out.println("-----调用方法没有实参");
			}
			System.out.println(proxy.getClass().getName());
			System.out.println("------end");
			return null;
		}
	}

}
