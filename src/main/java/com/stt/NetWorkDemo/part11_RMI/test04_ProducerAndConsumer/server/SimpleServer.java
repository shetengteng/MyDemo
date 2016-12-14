package com.stt.NetWorkDemo.part11_RMI.test04_ProducerAndConsumer.server;

import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo.part11_RMI.test04_ProducerAndConsumer.service.Stack;
import com.stt.NetWorkDemo.part11_RMI.test04_ProducerAndConsumer.service.StackImpl;

public class SimpleServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(9999);
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			Stack stack = new StackImpl("a stack");
			context.bind("MyStack", stack);
			System.out.println("---registry a stack---");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
