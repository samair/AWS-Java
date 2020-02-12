package com.karatfoods.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.karatfoods.api.model.Product;
import com.karatfoods.api.model.Response;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static Object input;
    private static InputStream anyInputStream;
    private static InputStream getInputStream;

    @BeforeClass
    public static void createInput() throws IOException {
        Product product = new Product();
        product.setProductName("Peas");
        product.setProductId(1234);
        product.setCatId(1);
        input = product;
        String responseString = new Gson().toJson(product);
        //anyInputStream = new ByteArrayInputStream(responseString.getBytes());
        
        Gson gson = new Gson();
    	
    	// 1. JSON file to Java object
    	Map object = gson.fromJson(new FileReader("/Users/samair/git/AWS-Java/serverless/src/test/java/com/karatfoods/api/payload_post_data.json"), Map.class);
        String respString = new Gson().toJson(object);
        System.out.println("Request :"+respString);
        anyInputStream = new ByteArrayInputStream(respString.getBytes());
        
        //Set up GET response
        
     	object = gson.fromJson(new FileReader("/Users/samair/git/AWS-Java/serverless/src/test/java/com/karatfoods/api/payload_get_data.json"), Map.class);
        respString = new Gson().toJson(object);
        System.out.println("Request Get :"+respString);
        getInputStream = new ByteArrayInputStream(respString.getBytes());
        
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

  //  @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        Assert.assertEquals("OK", output);
    }
  // @Test
    public void testLambdaDescription() {
    	 Product p = (Product)input;
    	 p.setDescription("Green Peas, fresh from farm.");
    	 LambdaFunctionHandler handler = new LambdaFunctionHandler();
         Context ctx = createContext();

         String output = handler.handleRequest(p, ctx);

         Assert.assertEquals("OK", output);
    }
    
    @Test
    public void testStreamHandler() {
    	
    	ByteArrayOutputStream out1 = new ByteArrayOutputStream();
    	GetItemsHandler handler = new GetItemsHandler();
    	Context ctx = createContext();
    	try {
			handler.handleRequest(anyInputStream, out1, ctx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 Response response = new Response();
 	    response.setBase64Encoded(true);
 	    response.setStatusCode(200);
 	    response.setBody("GET");
 	    Map <String,String > headers = new HashMap<>();
 	    headers.put("test", "test");
 	    response.setHeaders(headers);
    	
    	Assert.assertEquals(new Gson().toJson(response).toString(),out1.toString());
    }
    @Test
    public void testStreamHandler_Get() {
    	
    	ByteArrayOutputStream out2 = new ByteArrayOutputStream();
    	GetItemsHandler handler = new GetItemsHandler();
    	Context ctx = createContext();
    	try {
			handler.handleRequest(getInputStream, out2, ctx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 Response response = new Response();
 	    response.setBase64Encoded(true);
 	    response.setStatusCode(200);
 	    response.setBody("POST");
 	    Map <String,String > headers = new HashMap<>();
 	    headers.put("test", "test");
 	    response.setHeaders(headers);
    	
    	Assert.assertEquals(new Gson().toJson(response).toString(),out2.toString());
    }
}
