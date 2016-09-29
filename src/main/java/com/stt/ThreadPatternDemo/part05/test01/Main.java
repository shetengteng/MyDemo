package com.stt.ThreadPatternDemo.part05.test01;

public class Main {

	public static void main(String[] args) {

		Table table = new Table(3);
		new EaterThread("eater01", table, 5654).start();
		new EaterThread("eater02", table, 5651).start();
		new EaterThread("eater03", table, 5652).start();

		new MakerThread("maker01", table, 2654).start();
		new MakerThread("maker02", table, 2652).start();
		new MakerThread("maker03", table, 2656).start();

	}

}
