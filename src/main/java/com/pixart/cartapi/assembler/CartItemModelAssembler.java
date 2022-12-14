package com.pixart.cartapi.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pixart.cartapi.controller.CartController;
import com.pixart.cartapi.dto.CartItemDto;
import com.pixart.cartapi.utils.CartStatus;

@Component
public class CartItemModelAssembler implements RepresentationModelAssembler<CartItemDto, EntityModel<CartItemDto>> {

	@Override
	public EntityModel<CartItemDto> toModel(CartItemDto cartItem) {

		EntityModel<CartItemDto> cartItemModel = EntityModel.of(cartItem,
				linkTo(methodOn(CartController.class).getCartItem(cartItem.getId())).withSelfRel());

		cartItemModel.add(linkTo(methodOn(CartController.class).getCart(cartItem.getCartId())).withRel("parent-cart"));

		if (cartItem.getCartStatus() != CartStatus.CHECKOUT) {
			cartItemModel
					.add(linkTo(methodOn(CartController.class).deleteCartItem(cartItem.getId())).withRel("delete"));
			cartItemModel
					.add(linkTo(methodOn(CartController.class).editCartItem(cartItem.getId(), null)).withRel("edit"));
		}
		return cartItemModel;
	}
}