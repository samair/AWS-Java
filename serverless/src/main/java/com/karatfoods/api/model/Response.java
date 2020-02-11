package com.karatfoods.api.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
	
	private boolean isBase64Encoded;
	
	private int statusCode;
	
	private Map<String,String> headers;
	
	private String body;

}
