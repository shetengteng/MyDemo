package com.stt.ThreadDemo.ThreadPattern.part11.test01;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {

	private static PrintWriter writer = null;
	//初始化,需要捕获writer抛出的异常
	static{
		try {
			writer = new PrintWriter(new FileWriter("log.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//添加一条日志记录
	public static void println(String logStr){
		writer.println(logStr);
	}
	//关闭日志
	public static void close(){
		writer.println("end of log");
		writer.close();
	}
	
}
