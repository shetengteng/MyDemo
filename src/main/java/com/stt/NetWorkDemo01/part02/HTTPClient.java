package com.stt.NetWorkDemo01.part02;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.ws.spi.http.HttpContext;

public class HTTPClient {

	String host="www.baidu.com";
	int port = 80;
	Socket socket;
	
	public void createSocket() throws Exception{
		socket = new Socket(host,port);
	}
	
	public void communicate() throws Exception {
		StringBuilder sb = new StringBuilder("GET"+"/index.jsp"+"HTTP/1.1\r\n");
		sb.append("Host:"+host+"\r\n");
		sb.append("Accept:*/*\r\n");
		sb.append("Accept-Language:zh-cn\r\n");
		sb.append("Accept-Encoding:gzip,deflate\r\n");
		sb.append("User-Agent:Mozilla/4.0(compatible;MSIE 6.0;Windows NT5.0)\r\n");
		sb.append("Connection: Keep-Alive\r\n\r\n");
		//发出HTTP请求
		OutputStream socketOut = socket.getOutputStream();
		socketOut.write(sb.toString().getBytes());
		socket.shutdownOutput();
		
		//接收响应结果
		InputStream socketIn = socket.getInputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len = -1;
		while((len = socketIn.read(buff))!=-1){
			buffer.write(buff, 0, len);
			//此处是示例使用ByteArrayOutputStream，而真实的情况应该直接打印到面板上
			//减少内存的使用
		}
		System.out.println(new String(buffer.toByteArray()));
		
		socket.close();
	}
	
	public static void main(String[] args) throws Exception {
		HTTPClient client = new HTTPClient();
		client.createSocket();
		client.communicate();
	}
	
	
}
