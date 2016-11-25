package com.stt.ThreadDemo.ThreadPattern.part09.test02;

public class Retriever {

	public static Content retrieve(final String urlStr){
		final AsynContentImpl content = new AsynContentImpl();
		new Thread(){
			public void run() {
				SynContentImpl realContent = new SynContentImpl(urlStr);
				content.setSynContent(realContent);
				
			};
		}.start();
		return content;
	}
	
}
