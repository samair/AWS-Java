package com.karatfoods.api;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karatfoods.api.model.Product;

public class LambdaFunctionHandler{
//implements RequestHandler<Object, String> {

	private static AmazonDynamoDB dynamoDB;

	//@Override
	public String handleRequest(Object object, Context context) {
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.convertValue(object,Product.class);
		try {
			init();
			context.getLogger().log("ID: " + product.getProductId());
			DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
			mapper.save(product);
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
}
