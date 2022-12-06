package com.pixart.cartapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Product;
import com.pixart.cartapi.repository.ProductRepository;
import com.pixart.cartapi.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductByName(String name) throws ElementNotFoundException {
		return productRepository.findByName(name).orElseThrow(() -> new ElementNotFoundException("Product", name));

	}

}
