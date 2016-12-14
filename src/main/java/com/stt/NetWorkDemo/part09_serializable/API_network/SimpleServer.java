package com.stt.NetWorkDemo.part09_serializable.API_network;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.stt.NetWorkDemo.part09_serializable.API_local.Customer;

/**
 * 发送对象的服务端
 * 
 * @author Administrator
 *
 */
public class SimpleServer {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8888);
			while (true) {
				Socket socket = server.accept();
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream objOut = new ObjectOutputStream(out);

				Customer obj = new Customer();
				obj.setAge(22);
				obj.setName("stt");

				objOut.writeObject(obj);
				objOut.writeObject(obj);
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
