package com.stt.ThreadPatternDemo.part12.test01;

public class DisplayStringRequest extends MethodRequest{

	private final String dislayStr;
	protected DisplayStringRequest(Servant servant, String str) {
		//这里负责显示，不需要FutureResult对象
		super(servant, null);
		this.dislayStr = str;
	}

	@Override
	public void execute() {
		servant.displayString(dislayStr);
	}

}
