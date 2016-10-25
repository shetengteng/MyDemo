package com.stt.NetWorkDemo01.part06_URL.test02;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class MyURLStreamHandlerFactory implements URLStreamHandlerFactory {

	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		// 这里使用了自定义的协议echo，然后调用自己的流处理器
		if ("echo".equals(protocol)) {
			return new MyURLStreamHandler();
		}
		return null;
	}

}
