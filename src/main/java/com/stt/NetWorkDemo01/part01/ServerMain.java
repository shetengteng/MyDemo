package com.stt.NetWorkDemo01.part01;

import java.io.IOException;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		EchoServer server = new EchoServer();
		server.service();
	}
	
}
