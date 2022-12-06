package com.pixart.cartapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.CartItem;
import com.pixart.cartapi.repository.CartItemRepository;
import com.pixart.cartapi.service.CartItemService;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public CartItem getCartItemById(Long id) throws ElementNotFoundException {
		return cartItemRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Cart Item", id));
	}

	@Override
	public CartItem save(CartItem cartItem) {

		return cartItemRepository.save(cartItem);
	}

	@Override
	public void delete(Long itemId) {
		if (!cartItemRepository.existsById(itemId)) {
			throw new ElementNotFoundException("Cart Item", itemId);
		}
		cartItemRepository.deleteById(itemId);
	}

}
