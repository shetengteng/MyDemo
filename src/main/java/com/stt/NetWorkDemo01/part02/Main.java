package com.stt.NetWorkDemo01.part02;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class Main {

	
	public static void main(String[] args) {
		
		try {
			test02();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**测试链接超时初始化*/
	private void test01(){
		try {
			Socket socket = new Socket();
			SocketAddress remoteAddr = new InetSocketAddress("127.0.0.1", 8000);
			socket.connect(remoteAddr,60000);
		} catch (IOException e) {
		}
	}
	
	/**测试IP对象的获取*/
	private static void test02() throws UnknownHostException{
		
		//返回本地主机的IP地址
		InetAddress addr1 = InetAddress.getLocalHost();
		
		// 返回代表某一个IP的地址
		InetAddress addr2 = InetAddress.getByName("localhost");//127.0.0.1
		InetAddress addr4 = InetAddress.getByName("222.34.5.7");
		
		// 返回某一域名的IP地址
		InetAddress addr3 = InetAddress.getByName("www.baidu.com");
		
		System.out.println(addr3.getHostAddress());
		System.out.println(addr4.getHostAddress());
	}
	
	/**测试异常*/
	
	public class SimpleServer{
		
		public void start() throws Exception{
			//这里设置了请求的队列的长度为2
			ServerSocket server = new ServerSocket(8000,2);
			Thread.sleep(36000000);
		}
	}
	
	public class SimpleClient{
		public void start() throws UnknownHostException, IOException{
			Socket s1 = new Socket("localhost",8000);
			System.out.println("第一次链接成功");
			Socket s2 = new Socket("localhost",8000);
			System.out.println("第二次链接成功");
			Socket s3 = new Socket("localhost",8000);
			System.out.println("第三次链接成功");
		}
	}
	
	@Test
	public void test03() throws UnknownHostException, IOException{
		new SimpleClient().start();
	}
	@Test
	public void test04() throws Exception{
		new SimpleServer().start();
	}
	
	
	public void test05() throws UnknownHostException, IOException{
		Socket socket = new Socket();
		//绑定到本机的地址
		socket.bind(new InetSocketAddress(InetAddress.getByName("128.0.0.2"), 8000));
		//定义远端的服务器的IP和Port
		SocketAddress remotAddr = new InetSocketAddress("localhost",8000);
		socket.connect(remotAddr);
	
	}
	
	
	
}
