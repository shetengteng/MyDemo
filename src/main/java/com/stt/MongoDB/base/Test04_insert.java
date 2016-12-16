package com.stt.MongoDB.base;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Test04_insert {

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
	public void insert() {
		try {
			// 获得集合对象,getCollection还可以放入一个Class<T>的参数，可用于返回对象
			// 注意，即使获取的collection不存在，也不会报错，因为会自动创建一个
			MongoCollection<Document> collection = mongoDatabase.getCollection("InterfaceRemitBillHistory");

			// collection 的插入操作，可以使用insertOne,表示插入一个，这使用insertMany
			Document doc = new Document();
			doc.append("batchCode", "2015090702");
			doc.append("billCode", "CMBC4000003");
			doc.append("requestSeriaNum", "CMBC4000003");
			doc.append("handlerType", "AUTO");
			doc.append("cost", 0.2);
			doc.append("status", "CONFIRM");
			collection.insertMany(Arrays.asList(doc));
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
