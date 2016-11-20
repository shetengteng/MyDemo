package com.stt.NetWorkDemo01.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.stt.NetWorkDemo01.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service.Flight;
import com.stt.NetWorkDemo01.part11_RMI.test03_FlightFactory_SerializableParamAndReturn.service.FlightFactory;

public class SimpleClient {

	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			FlightFactory factory = (FlightFactory) context.lookup("FlightFactory");
			Flight flight01 = factory.getFlight("stt01");
			flight01.setOrigin("beijing");
			flight01.setDestination("hefei");
			System.out.println(flight01.getOrigin() + "--->" + flight01.getDestination());

			Flight flight02 = factory.getFlight("stt01");
			System.out.println(flight02.getFlightNumber());
			System.out.println(flight02.getOrigin() + "--->" + flight02.getDestination());
			System.out.println("flight01 class name :" + flight01.getClass().getName());
			System.out.println("flight02 class name :" + flight02.getClass().getName());

			System.out.println("==:" + (flight01 == flight02));
			System.out.println("equals:" + flight01.equals(flight02));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
