package com.stt.ThreadPatternDemo.part12.test01;

public class RealResult extends Result{

	private final Object resultValue;
	public RealResult(Object resultValue) {
		this.resultValue = resultValue;
	}
	@Override
	public Object getResultValue() {
		return resultValue;
	}

}
