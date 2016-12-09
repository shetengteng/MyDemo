package com.stt.ThreadDemo.concurrentLibrary.CallableAndFuture;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloWorld {

	public static void main(String[] args) {
		try {
			ExecutorService threadPool = Executors.newSingleThreadExecutor();
			Callable<String> task = new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(3000);
					return "helloWorld";
				}
			};
			// 获取结果
			Future<String> result = threadPool.submit(task);
			System.out.println(result.get());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 获取一组结果
			ExecutorService threadPool = Executors.newFixedThreadPool(3);
			// 将线程池作为入参放入完成服务中，在线程执行完毕后，由该服务获取结果
			CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool);
			// 放入10个线程任务
			for (int i = 0; i < 10; i++) {
				final Integer value = i;
				// 注意：这里需要的是completionService进行调用才会有效果
				completionService.submit(new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						Thread.sleep(new Random().nextInt(4000));
						return value;
					}
				});
			}
			// 哪个有结果就获取哪个结果,结果的顺序不一定与放入的顺序一致,与cup的执行顺序有关
			// CompletionService 与 ExecutorService 的区别是，前者提供了一个无界队列，可以从该队列中获取
			// 通过遍历获取
			for (int i = 0; i < 10; i++) {
				Future<Integer> result = completionService.take();
				System.out.println(result.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
