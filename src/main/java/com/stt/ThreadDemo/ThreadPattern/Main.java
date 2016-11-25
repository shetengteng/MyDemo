package com.stt.ThreadDemo.ThreadPattern;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) throws InterruptedException, ParseException {


	//	test03();
	//test04(8);
	//	test05();
	}

	private static void test06(){
		
	}
	
	private static void test05(){
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(localHost.getHostAddress().replace(".", ""));
	}
	
	private static void test04(Integer max){
		
		List<Integer> params = new ArrayList<Integer>();
		for(int i=0;i<max;i++ ){
			params.add(i);
		}
		
		
		// 将传入的参数进行分批进行查询操作
		List<Integer> subParams = new ArrayList<Integer>();
		for (int i = 0, j = 0; i < params.size(); i++) {
			if (j / 10 == 0) {
				subParams.add(params.get(i));
				j++;
			} else if (j / 10 == 1) {
				System.out.println(subParams.toString());
				j = 1;
				subParams = new ArrayList<Integer>();
				subParams.add(params.get(i));
			}

			// 执行最后剩下的不足500的元素
			if (i == params.size() - 1) {
				System.out.println(subParams.toString());
			}
		}
		
	}
	

	private static void test01(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String source = "2016-05-31";
	//	Date parse = sdf.parse(source);
	//	String out = sdf.format(parse)+" 00:00:00"; 
		
	//	System.out.println(out);
		
		
	//System.out.println(test01());
		
//		MyThread t = new MyThread();
//		t.start();
//		Thread.sleep(1000L);
//		t.start();
//		
	//	String[] checkDate = checkDate("2016-05-07","2016-05-14");
	//	System.out.println(checkDate[0]+"::"+checkDate[1]);
	}
	
	private static void test02(){
		
		String[] params = new String[]{"aa","bb","cc"};
		System.out.println(Arrays.asList(params).toString());
		
	}
	
	private static void test03(){
		
		System.out.println(501/500);
		
	}
	
	

private static String[] checkDate(String startDate, String endDate) throws ParseException {

	String[] result = new String[2];
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 如果第一个为空，则查询当天
	if ("".equals(startDate)) {
		startDate = sdf.format(new Date());
		endDate = startDate;
		// 如果只有第一个，则查询那天的
	} else if ("".equals(endDate)) {
		Date sDate = sdf.parse(startDate);
		startDate = sdf.format(sDate);
		endDate = startDate;
	} else {
		// 如果2个都有，则查询该日期范围内的
		Date sDate = sdf.parse(startDate);
		Date eDate = sdf.parse(endDate);
		startDate = sdf.format(sDate);
		endDate = sdf.format(eDate);
	}

	// 检测输入的日期是否符合标准
	result[0] = startDate + " 00:00:00";
	result[1] = endDate + " 23:59:59";

	
	
	return result;
}

	}
