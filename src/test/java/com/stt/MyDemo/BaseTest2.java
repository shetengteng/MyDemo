package com.stt.MyDemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

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
	public void test04() throws InterruptedException, ExecutionException {
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

}
