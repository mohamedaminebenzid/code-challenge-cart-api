package com.pixart.cartapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pixart.cartapi.utils.CartStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Enumerated(EnumType.STRING)
	private CartStatus status;

	private BigDecimal price;

	private LocalDateTime creationDate;

	private LocalDateTime updateDate;

	private LocalDate checkoutDate;

	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems = new ArrayList<>();

	public static Cart newInstance(Customer customer) {
		Cart cart = new Cart();
		cart.customer = customer;
		return cart;
	}

	public void addItem(CartItem item) {
		cartItems.add(item);
		item.setCart(this);
	}

	public void removeItem(CartItem item) {
		cartItems.remove(item);
	}

	public void updateStatus() {

		if (cartItems.isEmpty()) {
			status = CartStatus.CREATED;
			creationDate = getCurrentDateTime();
		} else {
			status = CartStatus.BUILDING;
			updateDate = getCurrentDateTime();
		}
	}

	public void checkout() {
		if (status == CartStatus.BUILDING) {
			status = CartStatus.CHECKOUT;
			checkoutDate = LocalDate.now();
			updateDate = getCurrentDateTime();
		}
	}

	private static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

}
