package com.stt.ThreadPatternDemo.part11.test02;

public class Log {
	private static final ThreadLocal<ThreadSpecificLog> threadLocal 
		= new ThreadLocal<>();
	
	private static ThreadSpecificLog getTSLog(){
		ThreadSpecificLog log = threadLocal.get();
		if(log == null){
			//如果为空则创建一个
			log = new ThreadSpecificLog(Thread.currentThread().getName()+"-log.txt");
			threadLocal.set(log);
			starWatcher(log);
		}
		return log;
	}
		
	public static void println(String logStr){
		getTSLog().println(logStr);
	}
	
	public static void close(){
		getTSLog().close();
	}
	
	private static void starWatcher(final ThreadSpecificLog log){
		//获取当前执行的线程
		final Thread target = Thread.currentThread();
		new Thread(){
			public void run() {
				try {
					target.join();
				} catch (Exception e) {
				}
				log.close();
			};
		}.start();
		
	}
	
}
