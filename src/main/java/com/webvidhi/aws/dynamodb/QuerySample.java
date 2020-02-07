package com.webvidhi.aws.dynamodb;

import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

public class QuerySample {

	public static void query() {

		DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBSample.dynamoDB);
		Table table = dynamoDB.getTable("my-favorite-movies-table");

		HashMap<String, String> nameMap = new HashMap<String, String>();
		nameMap.put("#yr", "year");

		HashMap<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(":yyyy", 1980);

		QuerySpec spec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap);
		ItemCollection<QueryOutcome> collection = table.query(spec);
		try {
			System.out.println("Movies from 1985");
			collection = table.query(spec);
			Iterator<Item> iterator;
			iterator = collection.iterator();
			Item item = null;
			while (iterator.hasNext()) {
				item = iterator.next();
				System.out.println(item.getNumber("year") + ": " + item.getString("name"));
			}

		} catch (Exception e) {
			System.err.println("Unable to query movies from 1980");
			System.err.println(e.getMessage());
		}

	}

}
