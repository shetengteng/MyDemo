package com.stt.NetWorkDemo.part11_RMI.test01_HelloWorld.server;

import java.rmi.Naming;

import com.stt.NetWorkDemo.part11_RMI.test01_HelloWorld.service.HelloService;
import com.stt.NetWorkDemo.part11_RMI.test01_HelloWorld.service.HelloServiceImpl;

public class SimpleServer2 {

	public static void main(String[] args) {
		try {
			HelloService service01 = new HelloServiceImpl("service01");
			HelloService service02 = new HelloServiceImpl("service02");

			// 这里创建语句最重要：注册RMI服务端口
			// LocateRegistry.createRegistry(9999);
			Naming.bind("rmi://localhost:9999/HelloService01", service01);
			Naming.bind("rmi://localhost:9999/HelloService02", service02);
			System.out.println("注册完毕...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
