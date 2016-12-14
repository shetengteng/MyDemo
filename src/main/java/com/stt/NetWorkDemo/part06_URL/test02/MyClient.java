package com.stt.NetWorkDemo.part06_URL.test02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MyClient {
	public static void main(String[] args) {
		try {
			// 设置获取流处理器的工厂类
			URL.setURLStreamHandlerFactory(new MyURLStreamHandlerFactory());
			// 设置内容处理器工厂类
			URLConnection.setContentHandlerFactory(new MyContentHandlerFactory());
			URL url = new URL("echo://localhost:8000");
			MyURLConnection connection = (MyURLConnection) url.openConnection();
			// 需要向服务器发送消息
			connection.setDoOutput(true);
			// 包装的打印输出流
			PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			// System.out.println("---client start---");
			while (true) {
				// 从控制面板上获取数据
				String msg = br.readLine();
				// 注意需要使用换行的，才能将消息发送出去
				pw.println(msg);
				String respMsg = (String) connection.getContent();
				System.out.println("---" + respMsg);
				if ("echo:exit".equals(respMsg)) {
					connection.disconnect();
					break;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
