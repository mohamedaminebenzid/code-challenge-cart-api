package com.pixart.cartapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.hateoas.EntityModel;

import com.pixart.cartapi.utils.CartStatus;

import lombok.Data;

@Data
public class CartDto {

	private Long id;

	private String customer;

	private CartStatus status;

	private BigDecimal price;

	private LocalDateTime creationDate;

	private LocalDateTime updateDate;

	private LocalDate checkoutDate;

	private List<EntityModel<CartItemDto>> cartItems;
}
