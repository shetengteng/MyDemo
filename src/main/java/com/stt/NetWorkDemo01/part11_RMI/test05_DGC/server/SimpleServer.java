package com.stt.NetWorkDemo01.part11_RMI.test05_DGC.server;

import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo01.part11_RMI.test05_DGC.service.HelloSerivce;
import com.stt.NetWorkDemo01.part11_RMI.test05_DGC.service.HelloSerivceImpl;

public class SimpleServer {

	public static void main(String[] args) {
		try {

			// 设置租约时间为3s
			System.setProperty("java.rmi.dgc.leaseValue", "10");

			LocateRegistry.createRegistry(9999);
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			HelloSerivce hello = new HelloSerivceImpl();
			context.bind("Hello", hello);
			System.out.println("---registry a hello---");
			// 一直等到有client
			while (!hello.isAccess())
				Thread.sleep(5000);
			context.unbind("Hello");
			System.out.println("----unbind hello---");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
