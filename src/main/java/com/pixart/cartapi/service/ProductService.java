package com.pixart.cartapi.service;

import java.util.List;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Product;

public interface ProductService {
	
	public List<Product> getAllProducts();

	public Product getProductByName(String name) throws ElementNotFoundException;
}
