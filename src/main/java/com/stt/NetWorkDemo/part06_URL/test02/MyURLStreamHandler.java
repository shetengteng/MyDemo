package com.stt.NetWorkDemo.part06_URL.test02;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class MyURLStreamHandler extends URLStreamHandler {

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new MyURLConnection(u);
	}

}
