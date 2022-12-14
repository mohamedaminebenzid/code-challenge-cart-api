package com.pixart.cartapi.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pixart.cartapi.dto.CartItemDto;
import com.pixart.cartapi.model.CartItem;
import com.pixart.cartapi.service.ProductService;

@Component
public class CartItemConverter {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductService productService;

	public CartItemDto toDto(CartItem cartItem) {
		CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
		cartItemDto.setPoductSku(cartItem.getProduct().getSku());
		cartItemDto.setProductName(cartItem.getProduct().getName());
		cartItemDto.setCartStatus(cartItem.getCart().getStatus());
		cartItemDto.setCartId(cartItem.getCart().getId());
		return cartItemDto;
	}

	public CartItem toEntity(CartItemDto cartItemDto) {
		CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
		cartItem.setProduct(productService.getProductByName(cartItemDto.getProductName()));
		return cartItem;
	}
}
