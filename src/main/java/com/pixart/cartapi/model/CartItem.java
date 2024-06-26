package com.pixart.cartapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pixart.cartapi.utils.ArtWorkFileType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private Integer quantity;

	private LocalDate deliveryDate;

	@Enumerated(EnumType.STRING)
	private ArtWorkFileType fileType;

	public BigDecimal getBasePrice() {
		return product.getPrice();
	}

}
