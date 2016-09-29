package com.stt.ThreadPatternDemo.part12.test01;

public class MakeStringRequest extends MethodRequest{

	private final int count;
	private final char c;
	
	protected MakeStringRequest(
			Servant servant, FutureResult future, int count, char c) {
		super(servant, future);
		this.count = count;
		this.c = c;
	}

	@Override
	public void execute() {
		//调用服务产生字符串结果RealResult
		Result result = servant.makeString(count, c);
		future.setResult(result);
	}

}
