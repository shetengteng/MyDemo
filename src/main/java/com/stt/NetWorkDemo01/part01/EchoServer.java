package com.stt.NetWorkDemo01.part01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	/**定义端口*/
	private int port = 8000;
	/**定义TCP通信的服务端*/
	private ServerSocket serverSocket;
	 
	public EchoServer() throws IOException{
		//初始化TCP服务端，指定监听端口，IP默认为本机
		serverSocket = new ServerSocket(port);
		System.out.println("server begin...");
	}
	
	/**
	 * 提供服务
	 */
	public void service(){
		//使用while--true 不断的进行获取TCP客户端对象
		while(true){
			//定义获取的TCP远端对象，远端的客户端对象
			Socket socket = null;
			
			try {
				// 等待客户端链接，开启监听，此处为阻塞式方法，在此阻塞
				socket = serverSocket.accept();
				// 打印收到的连接的客户端的IP与Port
				System.out.println("new connection accepted"+socket.getInetAddress()+":"+socket.getPort());
				
				// 从客户端对象获取输入流，接收数据
				InputStream in = socket.getInputStream();
				// 使用修饰类修饰in，使其更加容易操作
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				// 从客户端对象获取输出流，发送数据给客户端
				OutputStream out  = socket.getOutputStream();
				// 使用默认字符集进行转义，并且true表示自动flush操作
				PrintWriter pw = new PrintWriter(out,true);
				
				String msg = null;
				while((msg = br.readLine())!=null){
					System.out.println("receive:"+msg);
					
					//接收到消息后，再将该消息返回，表示接收到了
					pw.println("echo:"+msg);
					
					//这里收到了结束的消息，中止接收消息，此次TCP链接准备中断
					if("exit".equals(msg)){
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(socket!=null){
					try {
						//socket断开链接，会自动的关闭输入输出流
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
