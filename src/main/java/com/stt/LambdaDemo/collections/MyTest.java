package com.stt.LambdaDemo.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class MyTest {

	List<Person> javaProgrammers = null;
	List<Person> phpProgrammers = null;

	/**
	 * 使用Lambdas和Streams Stream是对集合的包装,通常和lambda一起使用。 使用lambdas可以支持许多操作,如 map,
	 * filter, limit, sorted, count, min, max, sum, collect 等等。
	 * 同样,Stream使用懒运算,他们并不会真正地读取所有数据,遇到像getFirst() 这样的方法就会结束链式语法。
	 * 在接下来的例子中,我们将探索lambdas和streams 能做什么。
	 * 我们创建了一个Person类并使用这个类来添加一些数据到list中,将用于进一步流操作。 Person 只是一个简单的POJO类:
	 */

	@Before
	public void init() {

		javaProgrammers = new ArrayList<Person>() {
			{
				add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
				add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
				add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
				add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
				add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
				add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
				add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
				add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
				add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
				add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
			}
		};

		phpProgrammers = new ArrayList<Person>() {
			{
				add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
				add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
				add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
				add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
				add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
				add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
				add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
				add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
				add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
				add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
			}
		};
	}

	/**
	 * 使用forEach方法输出对象
	 */
	@Test
	public void test01() {
		Consumer<? super Person> println = (p) -> System.out.println(p);
		javaProgrammers.forEach(println);

		// 含有stream ，HashMap没有Stream
		Set<String> set = new HashSet<String>();

	}

	/**
	 * 使用forEach方法操作对象
	 */
	@Test
	public void test02() {
		Consumer<? super Person> addSalary = (p) -> p.setSalary(p.getSalary() / 100 * 5 + p.getSalary());
		// 操作语句
		javaProgrammers.forEach(addSalary);

		Consumer<? super Person> println = (p) -> System.out.println(p);
		javaProgrammers.forEach(println);

	}

	/**
	 * 使用stream()方法，调用其他 lambda支持的操作,如 map, filter, limit, sorted, count, min,
	 * max, sum, collect 等等
	 */
	/**
	 * filter 使用过滤方法，进行一定条件的过滤
	 */
	@Test
	public void test03() {
		// 设定过滤条件
		Predicate<? super Person> predicate = (p) -> p.getSalary() > 1200;
		Consumer<? super Person> println = (p) -> System.out.printf("%s %s;\r\n", p.getFirstName(), p.getSalary());
		javaProgrammers.stream().filter(predicate).forEach(println);

	}

	/**
	 * 设置多个过滤条件，同时可以连串使用
	 */
	@Test
	public void test04() {

		Predicate<? super Person> salary = (p) -> p.getSalary() > 1200;
		Predicate<? super Person> age = (p) -> p.getAge() > 20;
		Predicate<? super Person> gender = (p) -> "male".equals(p.getGender());
		Consumer<? super Person> println = (p) -> System.out.printf("%s %s;\r\n", p.getFirstName(), p.getSalary());
		javaProgrammers.stream().filter(salary).filter(age).filter(gender).forEach(println);

	}

	/**
	 * 使用limit方法，从上一个结果集合中获取相应的个数
	 */
	@Test
	public void test05() {
		Consumer<? super Person> println = (p) -> System.out.printf("%s %s;\r\n", p.getFirstName(), p.getSalary());
		javaProgrammers.stream().limit(3).forEach(println);
	}

	@Test
	public void test06() {
		Predicate<? super Person> salary = (p) -> p.getSalary() > 1200;
		Predicate<? super Person> age = (p) -> p.getAge() > 20;
		Predicate<? super Person> gender = (p) -> "male".equals(p.getGender());
		Consumer<? super Person> println = (p) -> System.out.printf("%s %s;\r\n", p.getFirstName(), p.getSalary());
		javaProgrammers.stream().limit(3).filter(salary).filter(age).filter(gender).limit(1).forEach(println);
		// 注意：forEach要放在最后执行,同时limit放的位置也决定了结果的不同，始终表示从上一个结果集获取n个元素
		System.out.println("----------");
		javaProgrammers.stream().filter(salary).filter(age).filter(gender).limit(3).forEach(println);
		//
		javaProgrammers.stream().limit(3).filter(salary).filter(age).filter(gender).limit(1).forEach(println);
	}

	/**
	 * 排序操作 sort方法
	 */
	@Test
	public void test07() {

		// Comparator<? super Person> comparator = (p1,p2) ->
		// p1.getFirstName().compareTo(p2.getFirstName();
		/*
		 * javaProgrammers.stream(). sorted((p1,p2) ->
		 * p1.getFirstName().compareTo(p2.getFirstName());
		 */

		Comparator<? super Person> sorted = (p1, p2) -> (p1.getFirstName().compareTo(p2.getFirstName()));
		List<Person> sortedProgrammers = javaProgrammers.stream().sorted(sorted).limit(3).collect(Collectors.toList());
		sortedProgrammers.forEach((p) -> System.out.printf("%s %s;\r\n", p.getFirstName(), p.getSalary()));

	}

	/**
	 * 最大最小值 min max方法
	 */
	@Test
	public void test08() {

		Comparator<? super Person> comparator = (p1, p2) -> (p1.getSalary() - p2.getSalary());
		Person person = javaProgrammers.stream().max(comparator).get();
		System.out.println(person);

	}

	/**
	 * Map Collect 注意：这里的map表示映射
	 * 
	 */
	@Test
	public void test09() {
		System.out.println("将 PHP programmers 的 first name 拼接成字符串:");
		String phpDevelopers = phpProgrammers.stream().map(Person::getFirstName).collect(Collectors.joining(" ; ")); // 在进一步的操作中可以作为标记(token)

		System.out.println("将 Java programmers 的 first name 存放到 Set:");
		Set<String> javaDevFirstName = javaProgrammers.stream().map(Person::getFirstName).collect(Collectors.toSet());

		javaDevFirstName.forEach((p) -> System.out.println(p));

		System.out.println("将 Java programmers 的 first name 存放到 TreeSet:");
		TreeSet<String> javaDevLastName = javaProgrammers.stream().map(Person::getLastName)
				.collect(Collectors.toCollection(TreeSet::new));
	}

	/**
	 * parallelStream ?
	 */

	@Test
	public void test10() {

		System.out.println("计算付给 Java programmers 的所有money:");
		int totalSalary = javaProgrammers.parallelStream().mapToInt(p -> p.getSalary()).sum();
		System.out.println(totalSalary);

	}

	/**
	 * 统计
	 */
	@Test
	public void test11() {

		// Person::getSalary 等价于
		// (p) -> p.getSalary()
		// 等价于
		// p -> p.getSalary()

		// 计算 count, min, max, sum, and average for numbers
		DoubleSummaryStatistics stats = javaProgrammers.stream().map(Person::getSalary).mapToDouble((p) -> p)
				.summaryStatistics();

		// DoubleSummaryStatistics stats = javaProgrammers.stream().map(p ->
		// p.getSalary()).mapToDouble((p) -> p)
		// .summaryStatistics();

		System.out.println("List中最大的数字 : " + stats.getMax());
		System.out.println("List中最小的数字 : " + stats.getMin());
		System.out.println("所有数字的总和   : " + stats.getSum());
		System.out.println("所有数字的平均值 : " + stats.getAverage());

	}

	@Test
	public void test12() {
	}

}
