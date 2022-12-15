package com.pixart.cartapi.service;

import com.pixart.cartapi.exceptionhandler.ResourceNotFoundException;
import com.pixart.cartapi.model.Cart;
import com.pixart.cartapi.model.CartItem;

public interface CartService {

	Cart getCartById(Long id) throws ResourceNotFoundException;

	Cart save(Cart cart);

	Cart checkout(Long cartId) throws ResourceNotFoundException;

	Cart addItemToCart(Cart cart, CartItem cartItem);

	Cart deleteItem(CartItem cartItem);
}
