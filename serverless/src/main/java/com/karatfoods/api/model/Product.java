package com.karatfoods.api.model;

import java.math.BigInteger;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;


@DynamoDBTable(tableName="Products")
@Getter
@Setter
public class Product {
	

	private String productName;
	
	@DynamoDBHashKey(attributeName="productId") 
	private Integer productId;

	@DynamoDBRangeKey(attributeName="catId")
	private Integer catId;

	private String description;

	private Double price;

	private Double sellPrice;

	private String color;

	private String size;

	private String status;

	private BigInteger quantity;
	private Date date;

	private Integer plimit;
	
	
	

	
}
