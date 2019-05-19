package br.com.involvesfullstack.gateway.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoDbFactory {
	
	@Value("${spring.data.mongodb.uri}")
	private String databaseUri;
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	private MongoClient client;

	public MongoDatabase getDb() {
		ConnectionString connectionString = new ConnectionString(databaseUri);
		client = MongoClients.create(connectionString);
		
		MongoDatabase database = client.getDatabase(databaseName);
		return database;
	}
}
