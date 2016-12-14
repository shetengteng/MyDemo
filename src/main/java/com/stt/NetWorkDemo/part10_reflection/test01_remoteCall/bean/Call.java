package com.stt.NetWorkDemo.part10_reflection.test01_remoteCall.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用于server与client之间传递的bean对象
 * server端收到该Call对象后，反序列化一个接口对象实现类，并执行，将返回结果也封装在一个Call，返回给client端
 * notice：要实现可序列化类
 * 
 * @author Administrator
 *
 */
public class Call implements Serializable {
	/**
	 * 类名或者方法名
	 */
	private String className;
	/**
	 * 表示方法名
	 */
	private String methodName;
	/**
	 * 表示方法参数类型
	 */
	private Class[] paramTypes;
	/**
	 * 表示方法的参数值，注意：考虑使用map，好像不行，因为参数可能类型相同
	 */
	private Object[] params;
	/**
	 * 返回的结果，如果运行异常，那么result就代表那个异常
	 */
	private Object result;

	public Call() {
	}

	public Call(String className, String methodName, Class[] paramTypes, Object[] params) {
		this.className = className;
		this.methodName = methodName;
		this.paramTypes = paramTypes;
		this.params = params;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Call [className=" + className + ", methodName=" + methodName + ", paramTypes="
				+ Arrays.toString(paramTypes) + ", params=" + Arrays.toString(params) + ", result=" + result + "]";
	}

}
