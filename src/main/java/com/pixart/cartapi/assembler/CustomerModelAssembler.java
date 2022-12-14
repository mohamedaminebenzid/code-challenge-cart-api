package com.pixart.cartapi.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pixart.cartapi.controller.CartController;
import com.pixart.cartapi.controller.CustomerController;
import com.pixart.cartapi.model.Customer;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

	@Override
	public EntityModel<Customer> toModel(Customer customer) {

		return EntityModel.of(customer,
				linkTo(methodOn(CustomerController.class).getCustomer(customer.getUsername())).withSelfRel(),
				linkTo(methodOn(CartController.class).createNewCart(customer.getUsername()))
						.withRel("create-new-cart"));
	}
}