package com.stt.NetWorkDemo01.part03.test03;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup {

	/** 是否关闭线程池 */
	private boolean isClosed = false;
	/** 工作队列 */
	private LinkedList<Runnable> workQueue;
	/** 线程池的ID */
	private static int threadPoolId;
	/** 工作线程的ID */
	private int threadId;

	public ThreadPool(String name) {
		// 注意：必须要调用ThreadGroup的构造方法
		super(name);
	}

	public ThreadPool(int poolSize) {
		super("ThreadPool" + (threadPoolId++));
		setDaemon(true);
		// 创建工作队列
		workQueue = new LinkedList<>();
		for (int i = 0; i < poolSize; i++) {
			// 创建工作线程，并将工作线程放入线程组中
			new WorkThread().start();
		}
	}

	/** 向工作队列中添加一个新的线程任务，交给工作线程处理 */
	public synchronized void execute(Runnable task) {
		// 如果线程池已经关闭，则抛出异常
		if (isClosed) {
			throw new IllegalStateException();
		}
		if (task != null) {
			// 将任务放在workQueue中
			workQueue.push(task);
			this.notifyAll();
		}
	}

	/**
	 * 获取一个工作任务，如果没有工作任务，则该工作线程睡眠
	 * 
	 * @throws InterruptedException
	 */
	public synchronized Runnable getTask() throws InterruptedException {
		while (workQueue.size() == 0) {
			if (isClosed)
				return null;
			this.wait();
		}
		return workQueue.pop();
	}

	/** 直接关闭线程池 */
	public synchronized void close() {
		if (!isClosed) {
			isClosed = true;
			workQueue.clear();
			// 中断工作中的线程
			interrupt();
		}
	}

	/** 等待工作线程全部执行完毕后关闭 */
	public void join() {
		synchronized (this) {
			isClosed = true;
			// 唤醒在waitSet中的工作线程，并没有interrupt()
			// 那么在while(!isInterrupted())中会继续执行
			notifyAll();
		}

		// 使用ThreadGroup中的activeCount判断活动的线程的个数
		Thread[] threads = new Thread[activeCount()];
		// 将所有的活动的线程放在threads中
		int count = enumerate(threads);
		for (int i = 0; i < count; i++) {
			try {
				// 等待工作的线程结束
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * 工作线程，内部类
	 */
	private class WorkThread extends Thread {
		public WorkThread() {
			// 调用构造函数，将本线程，添加到ThreadGroup中
			// 添加到线程组中
			super(ThreadPool.this, "WorkThread-" + (threadId++));
		}

		/**
		 * interrupt()方法在此处的作用： 如果关闭时使用了interrupt()
		 * --isInterrupted()为true，线程进入while判断时退出
		 * --线程在getTask时在wait中，在interrupt()后抛出异常，task为null，退出
		 * --线程在执行task.run时不受影响，继续执行，执行完毕后，通过while判断退出
		 * 
		 */

		@Override
		public void run() {
			// 判断线程是否中断
			while (!isInterrupted()) {
				Runnable task = null;
				try {
					// 获取任务
					task = getTask();
				} catch (InterruptedException e) {
				}
				// 如果获取的该任务为空，则使用balking pattern
				if (task == null)
					return;
				// 如果有任务，则执行
				try {
					task.run();
				} catch (Exception e) {
				}
			} // while
		}// run
	}// WorkThread
}
