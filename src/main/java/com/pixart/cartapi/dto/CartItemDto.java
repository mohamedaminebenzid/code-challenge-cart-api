package com.pixart.cartapi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pixart.cartapi.utils.ArtWorkFileType;
import com.pixart.cartapi.utils.CartStatus;

import lombok.Data;

@Data
public class CartItemDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String poductSku;

	private String productName;

	private ArtWorkFileType fileType;

	private Integer quantity;

	private LocalDate deliveryDate;
	
	@JsonIgnore
	private CartStatus cartStatus;
	
	@JsonIgnore
	private Long cartId;

}
