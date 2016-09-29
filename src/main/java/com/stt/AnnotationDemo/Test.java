package com.stt.AnnotationDemo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		AnnotationTest bean = new AnnotationTest();

		// 获取该方法上面的注解
		Annotation[] annotations = bean.getClass().getMethod("getInfo").getAnnotations();

		for (Annotation annotation : annotations) {
			// 输出为：@com.stt.annotation.MyAnnotation(name=method, length=0)
			System.out.println(annotation);

			// 如果是该注解类型
			if (annotation instanceof MyAnnotation) {
				String name = ((MyAnnotation) annotation).name();
				// 输出name元素
				System.out.println(name);
				// 输出length元素
				int length = ((MyAnnotation) annotation).length();
				System.out.println(length);
			}

		}

		Method getInfoM = bean.getClass().getMethod("getInfo");
		// 判断该类上是否有MyAnnotation注解
		boolean annotationPresent = getInfoM.isAnnotationPresent(MyAnnotation.class);
		System.out.println(annotationPresent);

		// 获取字段上的注解信息
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Annotation[] annotations2 = field.getAnnotations();
			for (Annotation annotation : annotations2) {
				// 如果是该注解类型
				if (annotation instanceof MyAnnotation) {
					String name = ((MyAnnotation) annotation).name();
					// 输出name元素
					System.out.println(name);
					// 输出length元素
					int length = ((MyAnnotation) annotation).length();
					System.out.println(length);
				}
			}
		}
	}

}
