package com.karatfoods.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karatfoods.api.model.Response;

public class GetItemsHandler implements RequestStreamHandler {

	// private static AmazonDynamoDB dynamoDB;

	public static void init() throws Exception {

		// dynamoDB = AmazonDynamoDBClientBuilder.standard().withRegion("us-east-1")
		// .build();
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	

		JsonObject event = (JsonObject) JsonParser.parseReader(reader);

		context.getLogger().log("Got :" + event.toString() + " : " + context.getFunctionName());
		context.getLogger().log(event.get("httpMethod").getAsString());

		String httpMethod = event.get("httpMethod").getAsString();
		
		switch (httpMethod) {
		case "GET":
			System.out.println("Got a GET http request");
			break;
		case "POST":
			System.out.println("Got a POST http request");
			break;	
		}
		Response response = new Response();
		response.setBase64Encoded(true);
		response.setStatusCode(200);
		response.setBody(httpMethod);
		Map<String, String> headers = new HashMap<>();
		headers.put("test", "test");
		response.setHeaders(headers);
		OutputStreamWriter outWriter = new OutputStreamWriter(output, "UTF-8");

		String responseString = new Gson().toJson(response);
		context.getLogger().log("Response : " + responseString);
		outWriter.write(responseString);
		outWriter.flush();
		outWriter.close();
		

	}
}
