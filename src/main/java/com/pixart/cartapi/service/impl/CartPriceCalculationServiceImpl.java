package com.pixart.cartapi.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.pixart.cartapi.model.Cart;
import com.pixart.cartapi.model.CartItem;
import com.pixart.cartapi.service.CartPriceCalculationService;
import com.pixart.cartapi.utils.ArtWorkFileType;

@Service
public class CartPriceCalculationServiceImpl implements CartPriceCalculationService {

	@Override
	public void calculatePrice(Cart cart) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		LocalDate checkoutDate = cart.getCheckoutDate();
		for (CartItem item : cart.getCartItems()) {
			totalPrice = totalPrice.add(calculateItemPrice(item, checkoutDate));
		}
		cart.setPrice(totalPrice);
	}

	private BigDecimal calculateItemPrice(CartItem item, LocalDate checkoutDate) {
		return item.getBasePrice().multiply(BigDecimal.ONE.add(calculateTotalDiscount(item, checkoutDate)));
	}

	private BigDecimal calculateTotalDiscount(CartItem item, LocalDate checkoutDate) {
		return calculateQuanityDiscount(item).add(calculateDeliveryDateDiscount(item, checkoutDate))
				.add(calculateFileTypeDiscount(item));
	}

	private BigDecimal calculateQuanityDiscount(CartItem item) {
		Integer quantity = item.getQuantity();
		if (quantity < 100) {
			return BigDecimal.ZERO;
		}
		if (quantity < 250) {
			return new BigDecimal("-0.05");
		}
		if (quantity < 500) {
			return new BigDecimal("-0.1");
		}
		if (quantity < 1000) {
			return new BigDecimal("-0.15");
		}
		return new BigDecimal("-0.2");
	}

	private BigDecimal calculateDeliveryDateDiscount(CartItem item, LocalDate checkoutDate) {
		long numberOfDaysToDelivery = ChronoUnit.DAYS.between(checkoutDate, item.getDeliveryDate());
		if (numberOfDaysToDelivery <= 1) {
			return new BigDecimal("0.3");
		}
		if (numberOfDaysToDelivery <= 2) {
			return new BigDecimal("0.2");
		}
		if (numberOfDaysToDelivery <= 3) {
			return new BigDecimal("0.1");
		}
		return BigDecimal.ZERO;
	}

	private BigDecimal calculateFileTypeDiscount(CartItem item) {
		ArtWorkFileType fileType = item.getFileType();
		if (fileType == ArtWorkFileType.PDF) {
			return new BigDecimal("0.15");
		}
		if (fileType == ArtWorkFileType.AI) {
			return new BigDecimal("0.25");
		}
		return new BigDecimal("0.35");

	}

}
