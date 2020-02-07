package com.webvidhi.aws.dynamodb;

import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class ScanSample {

	
	public static void scanTable() throws Exception {
	//	AmazonDynamoDBSample.init();
		ScanRequest scanRequest = new ScanRequest()
                .withTableName("my-favorite-movies-table");
		AmazonDynamoDBSample.dynamoDB.scan(scanRequest);
		
		  ScanResult result = AmazonDynamoDBSample.dynamoDB.scan(scanRequest);

          for (Map<String, AttributeValue> item : result.getItems()) {
              Set<String> keys = item.keySet();

              for (String key : keys) {

                  System.out.println ("The key name is "+key +"\n" );
                  if (null != item.get(key).getS()) {
                	  System.out.println("The value is "+item.get(key).getS());
                  }else
                  {
                	  System.out.println("The value is "+item.get(key).getM());
                  }
                 

              }
          }
		
		// Now Scan the database
		
	}
}
