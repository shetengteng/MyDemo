package com.stt.MyDemo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.stt.MyDemo.BaseTest.MyBean;

public class BaseTest2 {

	@Test
	public void test01() {
		BufferedReader br = null;
		List<String[]> dataList = null;
		try {
			FileReader fr = new FileReader("d://test.txt");
			br = new BufferedReader(fr);
			String subData = null;
			dataList = new ArrayList<>();
			while ((subData = br.readLine()) != null) {
				if (StringUtils.isNotBlank(subData)) {
					String[] data = subData.split("\\|@\\|");
					dataList.add(data);
				}
			}
			for (String[] strings : dataList) {
				System.out.println("-----------");
				System.out.println(Arrays.asList(strings).toString());
				for (String string : strings) {
					System.out.println(string);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void test02() {

		new Thread(new MyTask()).start();
		new Thread(new MyTask()).start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static class MyTask implements Runnable {

		private static CompletionService<List<String>> completionService = new ExecutorCompletionService<List<String>>(
				Executors.newFixedThreadPool(4));

		@Override
		public void run() {

			System.out.println("--start---");
			completionService.submit(new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {
					Thread.sleep(4000);
					List<String> list = new ArrayList<>();
					for (int i = 0; i < 10; i++) {
						System.out.println("----");
						list.add(Thread.currentThread().getName() + "--" + i);
					}
					return list;
				}
			});

			try {
				Future<List<String>> result = completionService.take();
				List<String> list = result.get();
				for (String item : list) {
					System.out.println(item);
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}

	}

	@Test
	public void test03() {
		StringBuilder sb = new StringBuilder();
		sb.append("stt").append(",");
		sb.replace(sb.length() - 1, sb.length(), "");
		System.out.println(sb.toString());

	}

	@Test
	public void test04() throws InterruptedException, ExecutionException, Exception {

		Socket client = new Socket("10.10.116.45", 6076);
		InputStream in = client.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("F:\\lefu8\\work.jar"));

		byte[] buf = new byte[8912 * 10];
		int len = 0;
		System.out.println("start----");

		while ((len = bis.read(buf)) != -1) {
			bos.write(buf, 0, len);
		}
		bos.flush();
		System.out.println("end ----");

		bos.close();
		client.close();

	}

	@Test
	public void test05() {
		List<String> list = null;
		System.out.println(list.isEmpty());// error null point
	}

	@Test
	public void test06() {

		double d = 0.36;
		String format = new DecimalFormat("0").format(d * 100);
		System.out.println(format);
	}

	private String username = "shetengteng";
	private String password = "stt123456";
	private String hostname = "127.0.0.1";
	private int port = 22;

	private ChannelSftp sftpChannel = null;
	private JSch jsch = new JSch();
	private Session sshSession = null;

	@Test
	public void test07() {
		try {
			// 建立会话
			sshSession = jsch.getSession(username, hostname, port);
			sshSession.setPassword(password);
			// 设置不验证 HostKey
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(config);
			// 设置超时时间
			sshSession.setTimeout(10000);
			// 建立连接
			sshSession.connect();
			// 打开SFTP连接通道
			Channel channel = sshSession.openChannel("sftp");
			// 通道建立链接
			channel.connect();
			System.out.println("--------");
			sftpChannel = (ChannelSftp) channel;
			System.out.println("connection of session is finish.");

		} catch (JSchException e) {
			// 如果连接已打开，需要销毁连接
			if (sshSession.isConnected()) {
				sshSession.disconnect();
			}
			e.printStackTrace();
		}
	}

	@Test
	public void test08() throws Exception {
		Socket client = new Socket("10.10.116.45", 6076);
		InputStream in = new FileInputStream("d:\\proxy\\msftpsrvr.exe");
		OutputStream out = client.getOutputStream();

		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
		System.out.println("---end--");
		client.close();
	}

	@Test
	public void test09() {
		MyBean bean = new BaseTest().getBean();
		System.out.println(bean.getName());
	}

	// 测试值传递
	@Test
	public void test10() {
		List<String> list = new ArrayList<>();
		getList(list);
		System.out.println(list);
		for (String item : list) {
			System.out.println(item);
		}
	}

	public void getList(List<String> list) {
		ArrayList<String> list2 = new ArrayList<>();
		list2.add("stt");
		list.addAll(list2);
		System.out.println(list);
		System.out.println(list2);
	}

	@Test
	public void test11() {

		Object s = new String("77.8");
		Object d = 87.6d;
		System.out.println(Double.valueOf(88.8d));
		System.out.println(Double.valueOf("886.6"));
		System.out.println(Double.valueOf(s.toString()));
		System.out.println(Double.valueOf(d.toString()));
	}

	@Test
	public void test12() {
		System.out.println(new Date().toLocaleString());
	}

}
