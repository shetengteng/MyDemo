package com.stt.BankDemo.cmb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * HTTP通讯范例: 直接支付
 * 
 * @author 徐蓓
 */
public class HttpRequest {
	/**
	 * 生成请求报文
	 * 
	 * @return
	 */
	private String getRequestStr() {
		// 构造支付的请求报文
		XmlPacket xmlPkt = new XmlPacket("Payment", "USRA01");
		Map mpPodInfo = new Properties();
		mpPodInfo.put("BUSCOD", "N02031");
		xmlPkt.putProperty("SDKPAYRQX", mpPodInfo);
		Map mpPayInfo = new Properties();
		mpPayInfo.put("YURREF", "201009270001");
		mpPayInfo.put("DBTACC", "571905400910411");
		mpPayInfo.put("DBTBBK", "57");
		mpPayInfo.put("DBTBNK", "招商银行杭州分行营业部");
		mpPayInfo.put("DBTNAM", "NEXT TEST");
		mpPayInfo.put("DBTREL", "0000007715");
		mpPayInfo.put("TRSAMT", "1.01");
		mpPayInfo.put("CCYNBR", "10");
		mpPayInfo.put("STLCHN", "N");
		mpPayInfo.put("NUSAGE", "费用报销款");
		mpPayInfo.put("CRTACC", "571905400810812");
		mpPayInfo.put("CRTNAM", "测试收款户");
		mpPayInfo.put("CRTBNK", "招商银行");
		mpPayInfo.put("CTYCOD", "ZJHZ");
		mpPayInfo.put("CRTSQN", "摘要信息:[1.01]");
		xmlPkt.putProperty("SDKPAYDTX", mpPayInfo);
		return xmlPkt.toXmlString();
	}

	/**
	 * 连接前置机，发送请求报文，获得返回报文
	 * 
	 * @param data
	 * @return
	 * @throws MalformedURLException
	 */
	private String sendRequest(String data) {
		String result = "";
		try {
			URL url;
			url = new URL("http://localhost:8080");

			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os;
			os = conn.getOutputStream();
			os.write(data.toString().getBytes("gbk"));
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}

			System.out.println(result);
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理返回的结果
	 * 
	 * @param result
	 */
	private void processResult(String result) {
		if (result != null && result.length() > 0) {
			XmlPacket pktRsp = XmlPacket.valueOf(result);
			if (pktRsp != null) {
				String sRetCod = pktRsp.getRETCOD();
				if (sRetCod.equals("0")) {
					Map propPayResult = pktRsp.getProperty("NTQPAYRQZ", 0);
					String sREQSTS = (String) propPayResult.get("REQSTS");
					String sRTNFLG = (String) propPayResult.get("RTNFLG");
					if (sREQSTS.equals("FIN") && sRTNFLG.equals("F")) {
						System.out.println("支付失败：" + propPayResult.get("ERRTXT"));
					} else {
						System.out.println("支付已被银行受理（支付状态：" + sREQSTS + "）");
					}
				} else if (sRetCod.equals("-9")) {
					System.out.println("支付未知异常，请查询支付结果确认支付状态，错误信息：" + pktRsp.getERRMSG());
				} else {
					System.out.println("支付失败：" + pktRsp.getERRMSG());
				}
			} else {
				System.out.println("响应报文解析失败");
			}
		}
	}

	public static void main(String[] args) {
		try {
			HttpRequest request = new HttpRequest();

			// 生成请求报文
			String data = request.getRequestStr();

			// 连接前置机，发送请求报文，获得返回报文
			String result = request.sendRequest(data);

			// 处理返回的结果
			request.processResult(result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
