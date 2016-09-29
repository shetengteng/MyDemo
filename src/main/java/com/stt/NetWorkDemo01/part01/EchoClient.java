package com.stt.NetWorkDemo01.part01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	/**定义要链接的服务器IP，也可以是localhost*/
	private String remoteIP = "127.0.0.1";
	/**定义链接服务器的Port*/
	private int port = 8000;
	/**定义客户端对象*/
	private Socket socket;
	
	public EchoClient() throws UnknownHostException, IOException {
		// 开始与服务端对象建立链接
		socket = new Socket(remoteIP,port);
	}
	
	/**
	 * 客户端的操作
	 */
	public void talk(){
		try {
			// 定义输入流，从服务器端接收数据
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			//定义输出流，将数据写到服务器端
			PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
			
			//定义本地输出流，从控制台上将数据写入到PrintWriter上
			BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while((msg = localReader.readLine())!=null){
				
				// 发送msg数据给服务器
				pw.println(msg);
				
				//从服务器端接收数据，如果设置读取超时，如果超时，应该停止读取
				String result = br.readLine();
				System.out.println(result);
				
				// 从控制台上接收到的数据如果是exit，表示客户端中断服务，然后服务端也中断服务
				if("exit".equals(msg)){
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
