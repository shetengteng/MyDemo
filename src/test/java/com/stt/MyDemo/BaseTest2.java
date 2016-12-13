package com.stt.MyDemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
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
		CompletionService<List<String>> completionService = new ExecutorCompletionService<List<String>>(
				Executors.newFixedThreadPool(4));

		completionService.submit(new Callable<List<String>>() {
			@Override
			public List<String> call() throws Exception {
				// Thread.sleep(4000);
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

	@Test
	public void test04() throws InterruptedException, ExecutionException {

		ForkJoinPool threadPool = new ForkJoinPool(4);

		List<ForkJoinTask<List<String>>> resultList = new ArrayList<>();
		for (int j = 0; j < 10; j++) {
			final int tem = j;
			ForkJoinTask<List<String>> result = threadPool.submit(new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {
					List<String> list = new ArrayList<>();
					Thread.sleep(400 * new Random().nextInt(20));
					for (int i = 0; i < 10; i++) {
						System.out.println("----");
						list.add(tem + "--" + Thread.currentThread().getName() + "--" + i);
					}
					return list;
				}
			});
			resultList.add(result);
		}
		for (ForkJoinTask<List<String>> result : resultList) {
			List<String> list = result.get();
			for (String item : list) {
				System.out.println(item);
			}
		}

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

	/**
	 * CopyOnWriteArrayList
	 * 可能会出现的问题：在修改过程中有读取，但读取仍旧没有结束，而修改（删除）已经结束
	 */
	@Test
	public void test07() {
		// 测试 CopyOnWriteArrayList，测试一个线程读，一个线程写，读线程执行中嵌套着写线程，最后读的结果是否是最新的
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add("list_" + i);
		}
		// 这种情况下会抛出异常，边界异常
		// 是个坑，需要注意
		// 如果只是修改，而非删除操作的话，应该是没有问题的

		int sum = list.size();

		for (int i = 0; i < sum; i++) {
			System.out.println("---" + list.size());
			if (i == 4) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						list.remove(7);
					}
				}).start();
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(list.get(i));
		}
	}
}
