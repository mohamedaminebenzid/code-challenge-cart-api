package com.pixart.cartapi.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pixart.cartapi.controller.CartController;
import com.pixart.cartapi.dto.CartDto;
import com.pixart.cartapi.utils.CartStatus;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<CartDto, EntityModel<CartDto>> {

	@Override
	public EntityModel<CartDto> toModel(CartDto cart) {

		EntityModel<CartDto> cartModel = EntityModel.of(cart,
				linkTo(methodOn(CartController.class).getCart(cart.getId())).withSelfRel());

		// Conditional links based on state of the cart

		if (cart.getStatus() == CartStatus.BUILDING) {
			cartModel.add(linkTo(methodOn(CartController.class).checkoutCart(cart.getId())).withRel("checkout"));
		}

		if (cart.getStatus() != CartStatus.CHECKOUT) {
			cartModel.add(
					linkTo(methodOn(CartController.class).addNewItemToCart(cart.getId(), null)).withRel("add-item"));
		}
		return cartModel;
	}
}