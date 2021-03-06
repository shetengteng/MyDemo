package com.stt.NetWorkDemo.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.server;

import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service.FlightFactory;
import com.stt.NetWorkDemo.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service.FlightFactoryImpl;

public class SimpleServer {

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(9999);
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			FlightFactory factory = new FlightFactoryImpl();
			context.rebind("FlightFactory", factory);
			System.out.println("---server start---");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
