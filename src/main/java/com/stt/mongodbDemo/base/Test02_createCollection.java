package com.stt.mongodbDemo.base;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class Test02_createCollection {

	MongoClient mongoClient = null;
	MongoDatabase mongoDatabase = null;

	@Before
	public void init() {
		try {
			// 设定要连接的mongo库的地址
			ServerAddress serverAddress = new ServerAddress("10.10.111.31", 27017);
			List<ServerAddress> addresses = Arrays.asList(serverAddress);

			String userName = "admin";
			String database = "admin";
			char[] password = "admin".toCharArray();
			// credential:凭据，凭证
			// 这里是使用的是MONGODB-CR的验证方式
			MongoCredential credential = MongoCredential.createMongoCRCredential(userName, database, password);
			// 默认的验证方式
			// MongoCredential credential =
			// MongoCredential.createCredential(userName, database, password);
			List<MongoCredential> credentials = Arrays.asList(credential);
			// 建立客户端
			mongoClient = new MongoClient(addresses, credentials);
			// 获取连接的数据库
			mongoDatabase = mongoClient.getDatabase("payinterface");
			System.out.println("Connect to database successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createCollection() {
		try {
			// 创建集合
			mongoDatabase.createCollection("InterfaceRemitBillHistory");
			System.out.println("create collection successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void release() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

}
