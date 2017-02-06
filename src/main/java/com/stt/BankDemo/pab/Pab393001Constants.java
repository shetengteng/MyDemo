package com.stt.BankDemo.pab;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: Pab393001Constants 此处填写需要参考的类
 * @version 2017年2月4日 上午9:32:22
 * @author zhongxuan.fan
 */
public final class Pab393001Constants {
	// 代理
	public static final String proxyIp = "proxyIp";
	public static final String proxyPort = "proxyPort";

	// 商户编号
	public static final String merchantId = "merchantId";
	// 账户类型，默认为1（0对公；1对私），可不配置
	public static final String accType = "accType";
	// 交易地址
	public static final String remitUri = "remitUri";

	// des加密密钥
	public static final String desKey = "desKey";

	public static final String privateKeyPath = "privateKeyPath";

	// ---------------------------华丽的分割线,上面为prop中需要配置的，下面为常量--------------------------------------------

	public static final String application = "Pay.Req";
	public static final String queryApplication = "PayStatus.Req";

	public static final String version = "1.0";

	public static final String merLevel = "2";

}
