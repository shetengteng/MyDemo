package com.stt.ThreadDemo.concurrentLibrary.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceTest {

	public static void main(String[] args) {
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
}
