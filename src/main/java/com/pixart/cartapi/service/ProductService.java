package com.pixart.cartapi.service;

import java.util.List;

import com.pixart.cartapi.exceptionhandler.ResourceNotFoundException;
import com.pixart.cartapi.model.Product;

public interface ProductService {
	
	 public List<Product> getAllProducts();

	 Product getProductByName(String name) throws ResourceNotFoundException;
}
