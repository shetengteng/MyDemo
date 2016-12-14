package com.stt.NetWorkDemo.part10_reflection.test01_remoteCall.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.stt.NetWorkDemo.part10_reflection.test01_remoteCall.bean.Call;

public class SimplClient {

	public static Call invoke(Call call) {
		try {
			Socket socket = new Socket("localhost", 8888);
			OutputStream out = socket.getOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			InputStream in = socket.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);

			objOut.writeObject(call);
			Call result = (Call) objIn.readObject();
			socket.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println("----client-----");
		Call call = new Call("com.stt.NetWorkDemo01.part10_reflection.test01_remoteCall.service.HelloService", "echo",
				new Class[] { String.class }, new Object[] { "stt" });
		call = invoke(call);
		System.out.println(call.getResult());

		call = new Call("com.stt.NetWorkDemo01.part10_reflection.test01_remoteCall.service.HelloService", "getTime",
				new Class[] {}, new Object[] {});
		call = invoke(call);
		System.out.println(call.getResult());

	}

}
