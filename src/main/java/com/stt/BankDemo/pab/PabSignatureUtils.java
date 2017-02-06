package com.stt.BankDemo.pab;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: PabSignatureUtils 此处填写需要参考的类
 * @version 2017年2月4日 上午10:58:11
 * @author zhongxuan.fan
 */
public class PabSignatureUtils {

	private static Logger logger = LoggerFactory.getLogger(PabSignatureUtils.class);

	public static String base64encode(String msg) throws UnsupportedEncodingException {
		byte[] bytes = msg.getBytes();
		return new String(Base64.encodeBase64(bytes), "UTF-8");
	}

	public static String base64encode(byte[] msg) throws UnsupportedEncodingException {
		return new String(Base64.encodeBase64(msg), "UTF-8");
	}

	public static byte[] base64decode(String msg) throws UnsupportedEncodingException {
		return Base64.decodeBase64(msg);
	}

	/**
	 * @Description RSA加密
	 * @param data
	 * @param prop
	 * @return
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static byte[] encryptByPrivateKey(String data, Properties prop) throws Exception {
		PrivateKey privateKey = KeyInitUtil.getPrivateKey(prop);
		Cipher cipher = Cipher.getInstance(KeyFactory.getInstance("RSA").getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data.getBytes());
	}

	/**
	 * @Description MD5加密
	 * @param plainText 原串
	 * @return 摘要串
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static String getMD5Encoding(String plainText) {
		byte[] input = plainText.getBytes();// 声明16进制字母
		char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hex = new char[32];
		;
		try {
			// 获得一个MD5摘要算法的对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			/*
			 * MD5算法的结果是128位一个整数，在这里javaAPI已经把结果转换成字节数组了
			 */
			byte[] digest = md.digest();// 获得MD5的摘要结果
			byte b = 0;
			for (int i = 0; i < 16; i++) {
				b = digest[i];
				hex[2 * i] = hexChar[b >>> 4 & 0xf];// 取每一个字节的低四位换成16进制字母
				hex[2 * i + 1] = hexChar[b & 0xf];// 取每一个字节的高四位换成16进制字母
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error("{}", e);
			throw new RuntimeException(e.getMessage());
		}
		return new String(hex).toLowerCase();
	}

	/**
	 * @Description DES加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static byte[] desEncode(String data, String key) throws Exception {
		// 实例化DES密钥材料
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
		// 实例化秘密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		// 生成秘密密钥
		Key k = keyFactory.generateSecret(dks);
		/**
		 * 实例化
		 * 使用PKCS7Padding填充方式，按如下代码实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
		 */
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data.getBytes());
	}

	/**
	 * @Description DES解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @see 需要参考的类或方法
	 */
	public static byte[] desDecode(byte[] data, String key) throws Exception {
		// 实例化DES密钥材料
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		// 实例化秘密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		// 生成秘密密钥
		Key k = keyFactory.generateSecret(dks);
		/**
		 * 实例化
		 * 使用PKCS7Padding填充方式，按如下代码实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC");
		 */
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	public static String encode(String xml, String merchantId, String merLevel, Properties prop) throws Exception {
		String desKey = getMD5Encoding(prop.getProperty(Pab393001Constants.desKey));

		String first = base64encode((merchantId + "|" + merLevel));
		String second = base64encode(encryptByPrivateKey(desKey, prop));
		String third = base64encode(desEncode(xml, desKey));
		String fourth = getMD5Encoding(xml);
		String reqXml = first + "|" + second + "|" + third + "|" + fourth;
		return reqXml;
	}

	public static String decode(String data, Properties prop) throws Exception {
		String desKey = getMD5Encoding(prop.getProperty(Pab393001Constants.desKey));
		String[] list = data.split("\\|");
		if (list.length != 3)
			throw new RuntimeException("格式不正确,data:" + data);

		if ("1".equals(list[0])) {
			String returnXml = new String(desDecode(base64decode(list[1]), desKey), "UTF-8");
			return returnXml;
		} else if ("0".equals(list[0])) {
			throw new RuntimeException("错误代码: " + list[1] + "\r\n错误描述 : " + base64decode(list[2]));
		} else {
			throw new RuntimeException("格式不正确,data:" + data);
		}
	}

	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();
		prop.setProperty(Pab393001Constants.desKey, "123456");
		prop.setProperty(Pab393001Constants.privateKeyPath, "H:/pab393001.cer");
		// String resp =
		// "1|xlOBJ3beeUIm0ec+J55y6QBG2taezi/cD7/vh+BpWfn1SFSb+ppDflVLmjVqL5uieJCOIzfuUNcqSNVg1U8ETEsIY2gnzXuMg0x98BLzbNCC/2/eWuULB4sFXhgZMybQxLAHuWa9MTW2SHKfREPZc2Ry2jhJ7JBSjT6IumL+2XwHsyAfFiSObK4EukYNXD87Q/XiM7KGHXoCMMFr5Ilv0W+f5i2ktm2fHhafOjpSD4Kw21Gh9dQ+udPuF5zEkxBhSp8SfR4psOQh1zJtdgqyeXSHc+mUFyTCfd0ecXzaMHiq3fKbQvHK7ydct/Zvh0vbwlBz5K8lKHoDObjEzO6mgWhQdBgH/0dMtfFS+8MlOjlcOJ8g5R+HMLtpg6l72XcOCT3LiBba8ZMpMRygWH77ZxDAEzCmA+hb4Bejjo6s5T/AJjXAVBT6NtCxozb3QWLHLzVRpyb4Z/zYjkU7EQxVvzrKfOkVXtIwg2CCWjdbr/DemEaB5Cr/u4c4Z3q5BKrCIVq1uBi8Uwo3acHaE6SCg9Sw8uuDMRufou5KZKISyxxuuEhZXC1Fy8mLib6X7RyQP7kRNtL/56j0br1ixOo7lDj6Un3FGrcJacIMcB/ISWw26GwTPZ6Kql3+s0tP9sG0|9C4F3AFF6FA424CD35B15BBF1B769F12";
		/*
		 * String resp =
		 * "1|QzHOE0tRePMeHr3jcpSH5dzW5Vz6b8UZgkVDXWIVoJaTJawDx7kIt7589rBDb6VHshxHPP6OACNhy3hoIuX3LUrC5W5I3V7/jU62QVMmzJa3xtY11Ru4Y+wuZ6vStJkfE2aSYnSCwr4fuIjRfMoEWOzrExjMihAUFSTq6zHYbljjqMBq6WQxQR5Zi+V7Mf9ry0UE4FZ4CIHFvGC08B9JEyNVyHQuklpsV2NmEkXhft3vsTLjPhyxYy1kXoOgXqXvnp5x8SwnJB+nD67d8eT8KktzsEi0pWiwd4gqHRBH5gWPWEqTY/YajbzC4x1BwmApLfD9N/cVNZ6FLXp82pJIc0OLxnf4UU9dnCBILt77ywIJTYDdDCFLDw==|C930912F77D5C0FD2D5076F63A04CD67"
		 * ; System.out.println(decode(resp, prop));
		 */
		String resp = "1|QzHOE0tRePMeHr3jcpSH5dzW5Vz6b8UZgkVDXWIVoJaTJawDx7kIt7589rBDb6VHshxHPP6OACNhy3hoIuX3LUrC5W5I3V7/jU62QVMmzJa3xtY11Ru4Y+wuZ6vStJkfE2aSYnSCwr4fuIjRfMoEWOzrExjMihAUuuio3gIuL37paJLHJsTwNZwB3CJPuvgtJKPavDDQkylqWVEj6zFS1fSMc0OJDaflHofCjygJLLRvS2O8eEwXOLvVBsIYTas6mAinwGlFujYsLItMGph5ii3w/Tf3FTWeDrmYEjt0GDB4rr6CWTWJupYTV8rZuHouOgZnjDsJaTWGACnK0pKAczwQKYKZ08NNBjKHuo1TIyKjS7R9etFfoMuDfw2q6TrQNbcQDl96QFRnvsvLk5Z6eYGI8EmnLvNRWjdeGGs4VhGOr68qjXL3wbWhMThhFvrpUS8o4GJTyqyKimalmP2WK77hvzlSLJlfM95kxXJesAGCx+JU+GjgqFueqH/leatpKZKppL7VcyqulEru1aZi4//KkqeTLgLp64zdcp3RVTKRCezdePFRpNap4G/JKcqpArAhsEZwZxPw23Eouo6q22lT90EzMtH0LV1WjUe3FX3qgyhY5QImfQlNgN0MIUsP|02AFE28437A9509DCFD40296FF5C1D5A";
		System.out.println(decode(resp, prop));
		/*
		 * String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		 * "<forpay application=\"Pay.Req\"><merchantId>JG2649014979</merchantId><merLevel>2</merLevel><merchantOrderId>17020613530147500000</merchantOrderId><transTime>20170206135304</transTime><amount>100.0</amount><account>6226090000000048</account><accName>张三</accName><accType>1</accType><accBankCode>308584000013</accBankCode></forpay>"
		 * ; System.out.println(encode(s, "JG2649014979", "2", prop));
		 */
	}
}
