package com.pixart.cartapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Cart;
import com.pixart.cartapi.repository.CartRepository;
import com.pixart.cartapi.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Override
	public Cart getCartById(Long id) throws ElementNotFoundException {
		return cartRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Cart", id));

	}

	@Override
	public Cart save(Cart cart) {
		cart.updateStatus();
		return cartRepository.save(cart);
	}

	@Override
	public Cart checkout(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ElementNotFoundException("Cart", cartId));
		cart.checkout();
		return cartRepository.save(cart);
	}

}
