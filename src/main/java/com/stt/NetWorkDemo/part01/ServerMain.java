package com.stt.NetWorkDemo.part01;

import java.io.IOException;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		EchoServer server = new EchoServer();
		server.service();
	}
	
}
