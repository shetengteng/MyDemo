package com.stt.ThreadDemo.concurrentLibrary.Lock_Condition.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 简单的缓存系统示例
 * 
 * @author Administrator
 *
 */
public class CacheDemo {
	/** 设置缓存结构 */
	private Map<String, Object> cache = new HashMap<String, Object>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	public Object getData(String name) {
		rwl.readLock().lock();
		Object value = null;
		try {
			value = cache.get(name);
			// 从数据库中获取
			if (value == null) {
				// 注意： 在读锁的情况下，不能进入到写锁
				// 如果其他的线程也执行，那么也到这里进行了unlock操作
				// 因为有多个读线程可以进入到此处
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				// 注意：这里的二次判断，防止其他的线程进入到这里再次查询数据库
				// 类似于单例的二次判断原理
				if (value == null) {
					try {
						value = "hello";// 模拟从数据库中进行获取
						cache.put(name, value);
						// 因为就一个写线程进入到这里，同时其他的读线程在第一个readLock时，进入了waitset
						// 此处有写锁的降级，降级为读锁
						rwl.readLock().lock();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						rwl.writeLock().unlock();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			rwl.readLock().unlock();
		}
		return value;
	}
}
