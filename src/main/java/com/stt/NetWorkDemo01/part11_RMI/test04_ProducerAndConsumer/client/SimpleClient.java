package com.stt.NetWorkDemo01.part11_RMI.test04_ProducerAndConsumer.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.stt.NetWorkDemo01.part11_RMI.test04_ProducerAndConsumer.service.Stack;

public class SimpleClient {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			Stack stack = (Stack) context.lookup("MyStack");

			Producer producer = new Producer(stack, "pro-01");
			Producer producer2 = new Producer(stack, "pro-02");
			Consumer consumer = new Consumer(stack, "consumer");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}

/** 生产者 */
class Producer extends Thread {
	private Stack myStack;

	public Producer(Stack s, String name) {
		super(name);
		this.myStack = s;
		start();
	}

	public void run() {
		String goods;
		// 多个生产者
		try {
			while (true) {
				synchronized (myStack) {
					goods = "goods" + myStack.getPoint();
					myStack.push(goods);
					System.out.println(getName() + "push to : " + myStack.getName() + " good ：" + goods);
				}
				Thread.sleep(600);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/** 消费者 */
class Consumer extends Thread {
	private Stack myStack;

	public Consumer(Stack s, String name) {
		super(name);
		this.myStack = s;
		start();
	}

	public void run() {
		try {
			while (true) {
				String pop = myStack.pop();
				System.out.println(getName() + "consuming goods from :" + myStack.getName() + " good:" + pop);
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
