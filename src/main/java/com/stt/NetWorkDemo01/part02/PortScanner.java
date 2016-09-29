package com.stt.NetWorkDemo01.part02;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PortScanner {
	
	
	public static void main(String[] args) {
		
		String host = "localhost";
		scan(host);
		
	}

	/**
	 * 扫描端口：使用socket是否抛出异常来判断 
	 * 			有异常抛出，说明该端口没有被使用
	 * 			没有异常，说明该端口提供了服务
	 * @param host
	 */
	private static void scan(String host){
		Socket socket = null;
		for(int port = 1;port<1024;port++){
			try {
				socket = new Socket(host,port);
				System.out.println("There is a server on port:"+port);
			} catch (IOException e) {
				//System.out.println("cannot connect to port:"+port);
			}finally{
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {}
				}
			}
		}
	}
	
}
