package com.pixart.cartapi.service;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.CartItem;

public interface CartItemService {

	CartItem getCartItemById(Long id) throws ElementNotFoundException;
}
