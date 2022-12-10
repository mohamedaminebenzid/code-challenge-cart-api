package com.pixart.cartapi.service;

import com.pixart.cartapi.model.Cart;

public interface CartPriceCalculationService {

	void calculatePrice(Cart cart);
}
