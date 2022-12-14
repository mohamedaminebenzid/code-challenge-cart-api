package com.pixart.cartapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.pixart.cartapi.assembler.CartItemModelAssembler;
import com.pixart.cartapi.dto.CartDto;
import com.pixart.cartapi.dto.CartItemDto;
import com.pixart.cartapi.model.Cart;

@Component
public class CartConverter {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartItemConverter cartItemConverter;

	@Autowired
	private CartItemModelAssembler cartItemModelAssembler;

	public CartDto toDto(Cart cart) {
		CartDto cartDto = modelMapper.map(cart, CartDto.class);
		cartDto.setCustomer(cart.getCustomer().getUsername());
		List<EntityModel<CartItemDto>> cartItemDtoList = cart.getCartItems().stream().map(cartItemConverter::toDto)
				.map(cartItemModelAssembler::toModel).collect(Collectors.toList());
		cartDto.setCartItems(cartItemDtoList);
		return cartDto;
	}
}
