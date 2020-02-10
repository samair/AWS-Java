package com.karatfoods.api;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.karatfoods.api.model.Product;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        Product product = new Product();
        product.setProductName("Peas");
        product.setProductId(1234);
        product.setCatId(1);
        input = product;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        Assert.assertEquals("OK", output);
    }
    @Test
    public void testLambdaDescription() {
    	 Product p = (Product)input;
    	 p.setDescription("Green Peas, fresh from farm.");
    	 LambdaFunctionHandler handler = new LambdaFunctionHandler();
         Context ctx = createContext();

         String output = handler.handleRequest(p, ctx);

         Assert.assertEquals("OK", output);
    }
}
