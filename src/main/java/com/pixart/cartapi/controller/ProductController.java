package com.pixart.cartapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pixart.cartapi.model.Product;
import com.pixart.cartapi.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cart-api/v1/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@Operation(summary = "Get product by name")
	@GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<Product> getProduct(@PathVariable String name) {

		Product product = productService.getProductByName(name);

		return EntityModel.of(product, linkTo(methodOn(ProductController.class).getProduct(name)).withSelfRel(),
				linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
	}

	@Operation(summary = "Get all products")
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<EntityModel<Product>> getAllProducts() {

		List<EntityModel<Product>> products = productService.getAllProducts().stream()
				.map(product -> EntityModel.of(product,
						linkTo(methodOn(ProductController.class).getProduct(product.getName())).withSelfRel(),
						linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
				.collect(Collectors.toList());

		return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
	}

//	@GetMapping("/")
//	public List<Product> getAllProducts() {
//		return productService.getAllProducts();
//	}

}
