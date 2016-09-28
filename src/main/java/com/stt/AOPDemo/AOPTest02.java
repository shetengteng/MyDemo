package com.stt.AOPDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AOPTest02 {
	public static void main(String[] args) {
		Dog dog = (Dog) MyProxyFactory.getProxy(new GunDog());
		dog.info();
		dog.run();
	}

	public static class MyProxyFactory {
		private static final MyProxyFactory myProxyFactory = new MyProxyFactory();

		private MyProxyFactory() {}

		/** 定义公共方法，该方法用于info和run中操作，而info和run中不需要显式的引用 */
		private class DogUtil {
			public void methodA() {
				System.out.println("methodA is operating.");
			}

			public void methodB() {
				System.out.println("methodB is operating.");
			}
		}

		public class MyInvocationHandler implements InvocationHandler {
			private Object target;

			public void setTarget(Object target) {
				this.target = target;
			}

			/** 动态对象执行所有的方法时，都会调用该方法 */
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				DogUtil du = new DogUtil();
				du.methodA();
				// 通过反射调用target中的method方法
				Object result = method.invoke(target, args);
				du.methodB();
				return result;
			}
		}

		/** 为指定对象获得代理对象 */
		public static Object getProxy(Object target) {
			// 创建一个MyInvocationHandler对象
			MyInvocationHandler handler = myProxyFactory.new MyInvocationHandler();
			handler.setTarget(target);
			Object result = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
			return result;
		}
	}
}
