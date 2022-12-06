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

import com.pixart.cartapi.model.Customer;
import com.pixart.cartapi.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cart-api/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@Operation(summary = "Get customer by username")
	@GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<Customer> getCustomer(@PathVariable String username) {

		Customer customer = customerService.getCustomerByUsername(username);

		return EntityModel.of(customer, linkTo(methodOn(CustomerController.class).getCustomer(username)).withSelfRel(),
				linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
	}

	@Operation(summary = "Get all customers")
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public CollectionModel<EntityModel<Customer>> getAllCustomers() {
		List<EntityModel<Customer>> customers = customerService.getAllCustomers().stream()
				.map(customer -> EntityModel.of(customer,
						linkTo(methodOn(CustomerController.class).getCustomer(customer.getUsername())).withSelfRel(),
						linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers")))
				.collect(Collectors.toList());

		return CollectionModel.of(customers,
				linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
		// return new ResponseEntity<>(customerService.getAllCustomers(),
		// HttpStatus.OK);

	}

}
