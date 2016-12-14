package com.stt.NetWorkDemo.part11_RMI.test05_DGC.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo.part11_RMI.test05_DGC.service.HelloSerivce;

public class SimpleClient {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			HelloSerivce hello = (HelloSerivce) context.lookup("Hello");
			hello.access();
			Thread.sleep(10000);
			hello.bye();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
