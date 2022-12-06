package com.pixart.cartapi.service;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Cart;

public interface CartService {

	Cart getCartById(Long id) throws ElementNotFoundException;

	Cart save(Cart cart);
	
	Cart checkout(Long cartId);
}
