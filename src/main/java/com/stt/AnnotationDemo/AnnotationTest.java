package com.stt.AnnotationDemo;

public class AnnotationTest {

	@MyAnnotation(name = "test",length = 20)
	private String name;
	
	@MyAnnotation(length = 10)
	private String address;

	@MyAnnotation(name = "method",length = 0)
	public String getInfo(){
		return "annotation";
	}
	
}
