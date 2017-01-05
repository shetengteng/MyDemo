package com.stt.Sigar;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

public class MonitorJVM {

	public static void main(String[] args) {
		try {
			Runtime runtime = Runtime.getRuntime();
			InetAddress localHost = InetAddress.getLocalHost();
			Map<String, String> envMap = System.getenv();
			Properties props = System.getProperties();
			System.out.println("用户名：" + envMap.get("USERNAME"));
			System.out.println("计算机名：" + envMap.get("COMPUTERNAME"));
			System.out.println("计算机域名：" + envMap.get("USERDOMAIN"));
			System.out.println("本地IP地址：" + localHost.getHostAddress());
			System.out.println("本地主机名：" + localHost.getHostName());
			System.out.println("JVM可以使用的总内存：" + runtime.totalMemory());
			System.out.println("JVM可以使用的剩余内存：" + runtime.freeMemory());
			System.out.println("JVM可以使用处理器个数：" + runtime.availableProcessors());
			System.out.println("java运行环境版本：" + props.getProperty("java.version"));
			System.out.println("java运行环境的供应商：" + props.getProperty("java.vendor"));
			System.out.println("java运行环境的供应商的URL：" + props.getProperty("java.vendor.url"));
			System.out.println("java的安装路径：" + props.getProperty("java.home"));
			System.out.println("java虚拟机规范版本：" + props.getProperty("java.vm.specification.version"));
			System.out.println("Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor"));
			System.out.println("Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name"));
			System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
			System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
			System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
			System.out.println("Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
			System.out.println("Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender"));
			System.out.println("Java运行时环境规范名称：    " + props.getProperty("java.specification.name"));
			System.out.println("Java的类格式版本号：    " + props.getProperty("java.class.version"));
			System.out.println("Java的类路径：    " + props.getProperty("java.class.path"));
			System.out.println("加载库时搜索的路径列表：    " + props.getProperty("java.library.path"));
			System.out.println("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
			System.out.println("一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs"));
			System.out.println("操作系统的名称：    " + props.getProperty("os.name"));
			System.out.println("操作系统的构架：    " + props.getProperty("os.arch"));
			System.out.println("操作系统的版本：    " + props.getProperty("os.version"));
			System.out.println("文件分隔符：    " + props.getProperty("file.separator"));
			System.out.println("路径分隔符：    " + props.getProperty("path.separator"));
			System.out.println("行分隔符：    " + props.getProperty("line.separator"));
			System.out.println("用户的账户名称：    " + props.getProperty("user.name"));
			System.out.println("用户的主目录：    " + props.getProperty("user.home"));
			System.out.println("用户的当前工作目录：    " + props.getProperty("user.dir"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
