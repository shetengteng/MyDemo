package com.stt.JVM.classLoaderDemo;

import java.net.URL;
import java.net.URLClassLoader;

public class HelloWorld {

	public static void main(String[] args) {

		try {
			UserDefinedClassLoader userLoader = new UserDefinedClassLoader();
			// 从d:/classes/helloWorld.myclass加载
			Class<?> class1 = userLoader.loadClass("helloWorld");

			URL url = new URL("file:/d:/classes/");
			// 通过url加载器加载
			ClassLoader urlLoader = new URLClassLoader(new URL[] { url });
			Class<?> class2 = urlLoader.loadClass("helloWorld");

			System.out.println("class1 classLoader:" + class1.getClassLoader());
			System.out.println("class2 classLoader:" + class2.getClassLoader());

			// 结果：
			// class1
			// classLoader:com.stt.JVM.classLoaderDemo.UserDefinedClassLoader@511d50c0
			// class2 classLoader:java.net.URLClassLoader@5e2de80c
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
