package com.stt.LambdaDemo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class HelloWorld {

	/* 集合操作List */
	@Test
	public void test01() {
		String[] atp = { "Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer",
				"Andy Murray", "Tomas Berdych", "Juan Martin Del Potro", "Richard Gasquet", "John Isner" };
		List<String> players = Arrays.asList(atp);

		// 打印出所有元素
		players.forEach((p) -> System.out.println(p));
		System.out.println("-----");
		players.forEach((p) -> System.out.println(p.length()));

		// jdk8 ::
		// 打印出所有元素
		players.forEach(System.out::println);

	}

	/* lambda 代替匿名内部类 */
	public static void main(String[] args) {

		// 原先的内部类
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("run001 ...");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		// 使用lambda表达式替代：可替代的前提，接口里面只有一个函数
		new Thread(() -> {
			while (true) {
				System.out.println("run002 ...");
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/* 使用Lambda表达式进行排序 ，传统方式实现 */
	@Test
	public void test02() {

		String[] players = { "Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer",
				"Andy Murray", "Tomas Berdych", "Juan Martin Del Potro", "Richard Gasquet", "John Isner" };

		Arrays.asList(players).forEach((p) -> System.out.println(p));

		System.out.println("-----");
		// 排序之后将players中的顺序改变
		Arrays.sort(players, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		Arrays.asList(players).forEach((p) -> System.out.println(p));
	}

	@Test
	public void test03() {

		String[] players = { "Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer",
				"Andy Murray", "Tomas Berdych", "Juan Martin Del Potro", "Richard Gasquet", "John Isner" };

		Arrays.asList(players).forEach((p) -> System.out.println(p));

		System.out.println("-----");
		// 由于Comparator接口只有一个实现方法，因此可以使用Lambdab表达式
		Comparator<String> comparator = (String o1, String o2) -> o1.compareTo(o2);
		Arrays.sort(players, comparator);
		/**
		 * 另一种写法 : Arrays.sort(players, (String o1, String o2) ->
		 * o1.compareTo(o2));
		 */

		Arrays.asList(players).forEach((p) -> System.out.println(p));
	}

}
