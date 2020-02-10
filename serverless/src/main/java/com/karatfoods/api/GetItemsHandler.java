package com.karatfoods.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.karatfoods.api.model.Product;

public class GetItemsHandler implements RequestStreamHandler {

	private static AmazonDynamoDB dynamoDB;

	public String handleRequest(Object object, Context context) {
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.convertValue(object,Product.class);
		try {
			init();
			context.getLogger().log("ID: " + product.getProductId());
			context.
			
			 String value = "113";
	        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
	        eav.put(":val1", new AttributeValue().withN(value));
	        
	        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
	            .withFilterExpression("productId = :val1").withExpressionAttributeValues(eav);
	        
	        
			DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
			List<Product> products = mapper.scan(Product.class, scanExpression);
			
			for (Product p : products) {
				context.getLogger().log("Category is :"+ String.valueOf(p.getCatId()));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "OK";
	}

	public static void init() throws Exception {
		/*
		 * The ProfileCredentialsProvider will return your [default] credential profile
		 * by reading from the credentials file located at
		 * (C:\\Users\\statiraju\\.aws\\credentials).
		 
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (C:\\Users\\statiraju\\.aws\\credentials), and is in valid format.", e);
		}
		*/
		dynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion("us-east-1")
				.build();
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode query = mapper.readValue(reader, JsonNode.class);
	    
	    context.getLogger().log("Got :"+query.get("productId"));    
	     
	    ObjectNode objectNode1 = mapper.createObjectNode();
        objectNode1.put("bookName", "Java");
        
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(objectNode1.toString());
	}
}
