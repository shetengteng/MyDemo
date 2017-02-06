package com.stt.BankDemo.pab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 这里用一句话描述这个类的作用
 * @see: KeyInitUtil 此处填写需要参考的类
 * @version 2016年4月20日 下午2:28:33
 * @author jingyuan.ma
 */
public class KeyInitUtil {
	private static Logger logger = LoggerFactory.getLogger(KeyInitUtil.class);
	private static final String PUBLIC_KEY = "PUBLIC_KEY";
	private static final String PRIVATE_KEY = "PRIVATE_KEY";
	private static Map<String, Key> keyMap = new HashMap<>();
	/** 串行处理 */
	private static final Lock lock = new ReentrantLock();

	/*
	 * private static void initPublicKey(String path) throws Exception {
	 * InputStream inputStream = null; BufferedReader br = null; try {
	 * inputStream = new FileInputStream(path); br = new BufferedReader(new
	 * InputStreamReader(inputStream)); StringBuilder sb = new StringBuilder();
	 * String readLine = null; while ((readLine = br.readLine()) != null) { if
	 * (readLine.charAt(0) == '-') { continue; } else { sb.append(readLine);
	 * sb.append('\r'); } } X509EncodedKeySpec pubX509 = new
	 * X509EncodedKeySpec(Base64.decodeBase64(sb.toString())); KeyFactory
	 * keyFactory = KeyFactory.getInstance("RSA"); PublicKey publicKey =
	 * keyFactory.generatePublic(pubX509); keyMap.put(PUBLIC_KEY, publicKey); }
	 * catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException
	 * e) { logger.error(e.getMessage(), e); throw e; } finally { if (br !=
	 * null) try { br.close(); } catch (Exception e) {
	 * logger.error(e.getMessage(), e); } if (inputStream != null) try {
	 * inputStream.close(); } catch (Exception e) { logger.error(e.getMessage(),
	 * e); } } }
	 */

	private static void initPrivateKey(String path) throws Exception {
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			inputStream = new FileInputStream(path);
			br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
			keyMap.put(PRIVATE_KEY, privateKey);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

	public static boolean init(String privateKeyPath, String publicKeyPath) throws Exception {
		try {
			lock.lock();
			if (keyMap.get(PUBLIC_KEY) != null && keyMap.get(PRIVATE_KEY) != null) {
				return true;
			}
			// initPublicKey(publicKeyPath);
			initPrivateKey(privateKeyPath);

			return true;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * public static PublicKey getPublicKey(Properties prop) throws Exception {
	 * Key publicKey = keyMap.get(PUBLIC_KEY); if (publicKey == null) {
	 * init(prop.getProperty(C.PRIVATE_KEY_PATH),
	 * prop.getProperty(C.PUBLIC_KEY_PATH)); return (PublicKey)
	 * keyMap.get(PUBLIC_KEY); } return (PublicKey) publicKey; }
	 */

	public static PrivateKey getPrivateKey(Properties prop) throws Exception {
		Key privateKey = keyMap.get(PRIVATE_KEY);
		if (privateKey == null) {
			init(prop.getProperty(Pab393001Constants.privateKeyPath), null);
			return (PrivateKey) keyMap.get(PRIVATE_KEY);
		}
		return (PrivateKey) privateKey;
	}

	public static PrivateKey getPrivateKey() throws Exception {
		return (PrivateKey) keyMap.get(PRIVATE_KEY);

	}

	/*
	 * public static PublicKey getPublicKey() throws Exception { return
	 * (PublicKey) keyMap.get(PUBLIC_KEY); }
	 */
}
