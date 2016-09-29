package com.stt.NetWorkDemo01.part01;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientMain {

	public static void main(String[] args) throws UnknownHostException, IOException {
		EchoClient client = new EchoClient();
		client.talk();
	}
	
}
