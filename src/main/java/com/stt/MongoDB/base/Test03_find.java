package com.stt.MongoDB.base;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Test03_find {

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
	public void find() {
		// 获得集合对象,getCollection还可以放入一个Class<T>的参数，可用于返回对象
		MongoCollection<Document> collection = mongoDatabase.getCollection("FundAccount");
		// 返回查询的结果集合
		FindIterable<Document> find = collection.find().limit(10).skip(0);
		// 返回的结果集合的游标
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	// 表示bankCode= 'ABC' and status = 'ENABLE' and (accountType = 'MANAGEMENT'
	// or accountPurpose = 'MANUAL')
	@Test
	public void find2() {
		// 获得集合对象,getCollection还可以放入一个Class<T>的参数，可用于返回对象
		MongoCollection<Document> collection = mongoDatabase.getCollection("FundAccount");
		// 设置查询条件
		Bson eq = Filters.eq("bankCode", "ABC");
		Document filter = new Document();
		filter.put("status", "ENABLE");
		filter.append("$or",
				Arrays.asList(new Document("accountType", "MANAGEMENT"), new Document("accountPurpose", "MANUAL")));
		System.out.println(filter);
		// 返回查询的结果集合
		FindIterable<Document> find = collection.find(Filters.and(eq, filter)).limit(10).skip(0);
		// 返回的结果集合的游标
		MongoCursor<Document> iterator = find.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	@After
	public void release() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
}
