package com.stt.NetWorkDemo.part06_URL.test02;

import java.net.ContentHandler;
import java.net.ContentHandlerFactory;

public class MyContentHandlerFactory implements ContentHandlerFactory {

	@Override
	public ContentHandler createContentHandler(String mimetype) {
		// 依据自己的类型返回对应的内容处理器
		if ("text/plain".equals(mimetype)) {
			return new MyContentHandler();
		}
		return null;
	}

}
