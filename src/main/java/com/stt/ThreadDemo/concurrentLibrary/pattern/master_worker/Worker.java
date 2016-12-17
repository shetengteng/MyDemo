package com.stt.ThreadDemo.concurrentLibrary.pattern.master_worker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Worker 线程
 * @author Administrator
 *
 */
public class Worker implements Runnable {

	/**有一个任务的队列，从中获取任务进行执行，该队列从外部传入*/
	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();
	/**有一个返回任务执行完毕后的结果的队列*/
	private ConcurrentHashMap<String, Object> results = new ConcurrentHashMap<>();

	public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}

	public void setResults(ConcurrentHashMap<String, Object> results) {
		this.results = results;
	}

	@Override
	public void run() {
		while (true) {
			Task input = taskQueue.poll();
			// 说明没有任务了，当前的线程执行完毕
			if (input == null)
				break;
			Object result = handle(input);
			// 将结果放入集合中
			results.put(input.getId(), result);

		}
	}

	private Object handle(Task input) {
		Object result = null;
		try {
			// 这里模拟运算的时间
			Thread.sleep(500);
			result = input.getResult();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
}
