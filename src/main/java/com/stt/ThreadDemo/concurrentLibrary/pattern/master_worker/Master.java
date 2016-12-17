package com.stt.ThreadDemo.concurrentLibrary.pattern.master_worker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Master-Worker模式：
 * 常用的并行模式，Master负责	接收任务和处理任务执行后的结果，Worker负责处理子任务
 * 各个Worker处理完任务后，将结果返回给Master
 * 好处：将一个大任务分割成各个小任务，并行进行，提高系统吞吐量
 * @author Administrator
 */
public class Master {

	/**1. 存放任务元素
	 * 使用无锁无界线程安全队列，先入先出，本质上是使用了CAS
	 * 注意：每个函数操作是原子性的，但是组合在一起使用，不是原子操作，需要上锁
	 * */
	private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();

	/** 2.存放workers的集合，一旦初始化后，基本上不变 */
	private Map<String, Thread> workers = new HashMap<>();

	/**
	 * 3.用于存放workers线程返回的结果集合
	 * ConcurrentHashMap本质上是由16个HashTable组合而成的，因此同时能支持16个线程同时操作
	 * HashTable是线程安全的，每个HashTable操作是上锁的
	 */
	private ConcurrentHashMap<String, Object> results = new ConcurrentHashMap<>();

	/** 4.构造函数，用于初始化worker线程*/
	public Master(Worker worker, int workerCount) {
		worker.setResults(results);
		worker.setTaskQueue(taskQueue);
		// 初始化线程个数
		for (int i = 0; i < workerCount; i++) {
			workers.put("worker-" + (i + 1), new Thread(worker));
		}
	}

	/**5.提交任务*/
	public void submit(Task task) {
		taskQueue.add(task);
	}

	/**6.执行所有的任务*/
	public void execute() {
		// 开启所有的线程
		for (Entry<String, Thread> item : workers.entrySet()) {
			item.getValue().start();
		}
	}

	/**7.判断是否已经运行结束
	 * 判断每个线程的状态,这里当线程处于终止状态时，就可以判断执行完毕
	 * 说明每个线程执行完毕后，就消失了，而非等待新的任务的到来
	 * 可以使用wait的方式，如果线程获取不到task，就wait
	 * 当所有的线程都处于wait的状态，则说明执行完了，这样不用每次都初始化，耗费资源
	 * */
	public boolean isComplete() {
		for (Entry<String, Thread> item : workers.entrySet()) {
			if (item.getValue().getState() != Thread.State.TERMINATED)
				return false;
		}
		return true;
	}

	/**8.获取最终结果
	 * 最终结果多样，这里只是统计最后的结果
	 * */
	public int getResult() {
		int result = 0;
		for (Entry<String, Object> item : results.entrySet()) {
			result += (Integer) item.getValue();
		}
		return result;
	}
}
