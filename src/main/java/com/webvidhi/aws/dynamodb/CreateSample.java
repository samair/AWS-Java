package com.webvidhi.aws.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.webvidhi.aws.dynamodb.model.Product;

public class CreateSample {
	
	
	public static void create() {
		Product product = new Product();
		product.setProductId(752);
		product.setCatId(1);
		product.setDescription("Test");
		product.setPrice(811.00);
		product.setSellPrice(800.00);
		
		DynamoDBMapper mapper = new DynamoDBMapper(AmazonDynamoDBSample.dynamoDB);
		mapper.save(product);
	}

}
