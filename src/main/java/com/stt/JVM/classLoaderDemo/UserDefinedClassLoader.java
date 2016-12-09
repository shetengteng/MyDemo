package com.stt.JVM.classLoaderDemo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class UserDefinedClassLoader extends ClassLoader {

	// 定义命名空间
	private String directory = "d:\\classes\\";
	// 定义扩展名
	private String extensionType = ".myclass";

	public UserDefinedClassLoader() {
	}

	public UserDefinedClassLoader(ClassLoader parent) {
		super(parent);
	}

	public Class findClass(String name) {
		byte[] data = loadClassData(name);
		// 读取name.myclass类型的数据，将其中的字节码文件放入类加载器中
		return defineClass(name, data, 0, data.length);
	}

	/**
	 * 获取自定义类的class文件
	 * @param name
	 * @return
	 */
	private byte[] loadClassData(String name) {
		byte[] data = null;
		try {
			// 获取myclass文件，将name中的包名称改为路径名
			FileInputStream input = new FileInputStream(new File(directory + name.replace(".", "\\") + extensionType));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			data = out.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
