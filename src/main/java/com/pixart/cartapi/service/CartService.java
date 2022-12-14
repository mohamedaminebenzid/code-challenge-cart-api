package com.pixart.cartapi.service;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Cart;
import com.pixart.cartapi.model.CartItem;

public interface CartService {

	Cart getCartById(Long id) throws ElementNotFoundException;

	Cart save(Cart cart);

	Cart checkout(Long cartId) throws ElementNotFoundException;

	Cart addItemToCart(Cart cart, CartItem cartItem);

	Cart deleteItem(CartItem cartItem);
}
