package com.stt.ThreadDemo.ThreadPattern.part09_Future.test01;

public class Host {

	//调用该方法，直接返回一个data数据的收据
	public Data request(final int count,final char c){
		System.out.println("request: count--"+count +" c--"+c+" begin");
		
		//建立FutureData的实例
		final FutureData future = new FutureData();
		
		//使用Thread per message 模式，启动线程
		new Thread(){
			public void run() {
				try {
					RealData realData = new RealData(count, c);
					future.setReaLData(realData);
				} catch (Exception e) {
					//如果有异常，则在future中设置，
					//当外部调用future.getContent()时，将异常抛出
					future.setException(e);
				}
				
			};
		}.start();
		System.out.println("request:count --"+count+" c--"+c+" end");
		
		// 返回提货单
		return future;
	}
	
}
