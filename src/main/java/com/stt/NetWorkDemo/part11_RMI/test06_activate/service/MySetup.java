package com.stt.NetWorkDemo.part11_RMI.test06_activate.service;

import java.rmi.MarshalledObject;
import java.rmi.activation.Activatable;
import java.rmi.activation.ActivationDesc;
import java.rmi.activation.ActivationGroup;
import java.rmi.activation.ActivationGroupDesc;
import java.rmi.activation.ActivationGroupID;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class MySetup {
	public static void main(String[] args) {
		try {
			ActivationGroupDesc group = new ActivationGroupDesc(null, null);
			// 获取groupID
			ActivationGroupID groupID = ActivationGroup.getSystem().registerGroup(group);
			String classURL = System.getProperty("java.rmi.server.codebase");
			MarshalledObject<String> param1 = new MarshalledObject<String>("service01");
			MarshalledObject<String> param2 = new MarshalledObject<String>("service02");

			String className = "com.stt.NetWorkDemo01.part11_RMI.test06_activate.service.HelloServiceImpl";
			ActivationDesc desc1 = new ActivationDesc(groupID, className, classURL, param1);
			ActivationDesc desc2 = new ActivationDesc(groupID, className, classURL, param2);

			// 在rmid中进行注册
			HelloService s1 = (HelloService) Activatable.register(desc1);
			HelloService s2 = (HelloService) Activatable.register(desc2);

			System.out.println(s1.getClass().getName());

			LocateRegistry.createRegistry(9999);
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			prop.setProperty(Context.PROVIDER_URL, "rmi://localhost:9999");
			Context context = new InitialContext(prop);
			context.bind("Hello1", s1);
			context.bind("Hello2", s2);

			System.out.println("---registry Hello1 Hello2");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
