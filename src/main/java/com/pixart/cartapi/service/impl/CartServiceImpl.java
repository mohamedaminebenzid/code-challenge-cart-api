package com.pixart.cartapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Cart;
import com.pixart.cartapi.model.CartItem;
import com.pixart.cartapi.repository.CartRepository;
import com.pixart.cartapi.service.CartPriceCalculationService;
import com.pixart.cartapi.service.CartService;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartPriceCalculationService cartPriceCalculationService;

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
	public Cart addItemToCart(Cart cart, CartItem cartItem) {
		cart.addItem(cartItem);
		return save(cart);
	}

	@Override
	public Cart deleteItem(CartItem cartItem) {
		Cart cart = cartItem.getCart();
		cart.removeItem(cartItem);
		return save(cart);

	}

	@Override
	public Cart checkout(Long cartId) throws ElementNotFoundException {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ElementNotFoundException("Cart", cartId));
		cart.checkout();
		cartPriceCalculationService.calculatePrice(cart);
		return cartRepository.save(cart);
	}

}
