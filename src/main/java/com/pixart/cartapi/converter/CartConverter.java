package com.pixart.cartapi.converter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.pixart.cartapi.controller.CartController;
import com.pixart.cartapi.dto.CartDto;
import com.pixart.cartapi.dto.CartItemDto;
import com.pixart.cartapi.model.Cart;

@Component
public class CartConverter {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartItemConverter cartItemConverter;

	public CartDto toDto(Cart cart) {
		CartDto cartDto = modelMapper.map(cart, CartDto.class);
		cartDto.setCustomer(cart.getCustomer().getUsername());
		List<EntityModel<CartItemDto>> cartItemDtoList = cart.getCartItems().stream().map(cartItemConverter::toDto)
				.map(item -> EntityModel.of(item,
						linkTo(methodOn(CartController.class).getCartItem(item.getId())).withSelfRel()))
				.collect(Collectors.toList());
		cartDto.setCartItems(cartItemDtoList);
		return cartDto;
	}
}
