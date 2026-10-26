package com.online.shopping_app.exception;

public class ProductNotFoundException extends RuntimeException{
	
	public ProductNotFoundException(Long id) {
		super("Product Not Found : " + id);
	}
	
}