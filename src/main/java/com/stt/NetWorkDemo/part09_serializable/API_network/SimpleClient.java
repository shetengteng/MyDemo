package com.stt.NetWorkDemo.part09_serializable.API_network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.stt.NetWorkDemo.part09_serializable.API_local.Customer;

public class SimpleClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 8888);
			InputStream in = socket.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);
			Customer cus1 = (Customer) objIn.readObject();
			Customer cus2 = (Customer) objIn.readObject();
			System.out.println(cus1);
			System.out.println(cus2);
			// 判断2者是否是同一个对象
			System.out.println(cus1 == cus2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
