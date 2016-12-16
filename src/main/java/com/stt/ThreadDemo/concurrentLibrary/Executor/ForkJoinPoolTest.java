package com.stt.ThreadDemo.concurrentLibrary.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * 并行计算线程池
 * @author Administrator
 *
 */
public class ForkJoinPoolTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

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

}
