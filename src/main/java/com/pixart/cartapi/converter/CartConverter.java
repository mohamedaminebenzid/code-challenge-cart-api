package com.pixart.cartapi.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		List<CartItemDto> cartItemDtoList = cart.getCartItems().stream().map(cartItemConverter::toDto)
				.collect(Collectors.toList());
		cartDto.setCartItems(cartItemDtoList);
		return cartDto;
	}
}
