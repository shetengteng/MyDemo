package com.stt.BankDemo.ceb;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SSLClientTest {

	public static void main(String[] args) throws Exception {
		HttpsClient httpsClient = new HttpsClient();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		path = URLDecoder.decode(path, "utf-8");
		// 设置客户端密钥库
		httpsClient.setKeyStoreParameters(path + "client.pfx", "Abcd1234", "PKCS12");
		// 设置客户端信任库
		httpsClient.setTrustStoreParameters(path + "/cacerts", "changeit", "jks");
		// ip
		httpsClient.setHost("10.1.91.90");
		// 端口
		httpsClient.setPort(7765);

		// 测试，模拟9000交换密钥交易
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String operationDate = sf.format(new Date());
		String message = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><in><head><version>1.0.1</version><InstID>08802382</InstID><trmSeqNum>77777777</trmSeqNum><tranDate>20111209</tranDate><tranTime>141550</tranTime><tradeCode>9000</tradeCode><servName>Ser1</servName><reserve1></reserve1><reserve2></reserve2><reserve3></reserve3></head><body><operationDate>"
				+ operationDate + "</operationDate><field1></field1></body></in>";
		System.out.println(httpsClient.sengMessage(message));
	}
}
