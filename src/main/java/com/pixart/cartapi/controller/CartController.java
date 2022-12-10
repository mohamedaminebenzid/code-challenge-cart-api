package com.pixart.cartapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pixart.cartapi.converter.CartConverter;
import com.pixart.cartapi.converter.CartItemConverter;
import com.pixart.cartapi.dto.CartDto;
import com.pixart.cartapi.dto.CartItemDto;
import com.pixart.cartapi.model.Cart;
import com.pixart.cartapi.model.CartItem;
import com.pixart.cartapi.model.Customer;
import com.pixart.cartapi.service.CartItemService;
import com.pixart.cartapi.service.CartService;
import com.pixart.cartapi.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cart-api/v1")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CartItemConverter cartItemConverter;

	@Autowired
	private CartConverter cartConverter;

	@Operation(summary = "Create new cart for the current customer")
	@PostMapping(path = "/customers/{customer-username}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CartDto> createNewCart(@PathVariable("customer-username") String username) {

		Customer customer = customerService.getCustomerByUsername(username);

		Cart cart = cartService.save(Cart.newInstance(customer));

		return new ResponseEntity<>(cartConverter.toDto(cart), HttpStatus.CREATED);
	}

	@Operation(summary = "Add new item to the current cart")
	@PostMapping(path = "/carts/{cart-id}/items", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CartDto> addNewItemToCart(@PathVariable("cart-id") Long cartId,
			@RequestBody CartItemDto carItemDto) {

		Cart cart = cartService.getCartById(cartId);
		CartItem cartItem = cartItemConverter.toEntity(carItemDto);
		cart.addCartItem(cartItem);
		cart = cartService.save(cart);
		return new ResponseEntity<>(cartConverter.toDto(cart), HttpStatus.CREATED);
	}

	@Operation(summary = "Get items of the current cart")
	@GetMapping(path = "/carts/{cart-id}/items", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<EntityModel<CartItemDto>> getCartItems(@PathVariable("cart-id") Long cartId) {

		Cart cart = cartService.getCartById(cartId);

		return cartConverter.toDto(cart).getCartItems();
	}

	@Operation(summary = "Get item by id")
	@GetMapping(path = "/items/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CartItemDto> getCartItem(@PathVariable("item-id") Long itemId) {

		CartItem cartItem = cartItemService.getCartItemById(itemId);

		return new ResponseEntity<>(cartItemConverter.toDto(cartItem), HttpStatus.OK);
	}

	@Operation(summary = "Edit item by id")
	@PutMapping(path = "/items/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> editCartItem(@PathVariable("item-id") Long itemId,
			@RequestBody CartItemDto carItemDto) {

		return new ResponseEntity<>("Not implemented yet", HttpStatus.OK);
	}

	@Operation(summary = "Delete item by id")
	@DeleteMapping(path = "/items/{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Long> deleteCartItem(@PathVariable("item-id") Long itemId) {

		cartItemService.delete(itemId);

		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Get cart details(View Cart) by id")
	@GetMapping(path = "/carts/{cart-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<CartDto> getCart(@PathVariable("cart-id") Long cartId) {

		Cart cart = cartService.getCartById(cartId);

		CartDto cartDto = cartConverter.toDto(cart);
		
		//TODO add checkout link

		return EntityModel.of(cartDto, linkTo(methodOn(CartController.class).getCart(cartId)).withSelfRel());
	}

	@Operation(summary = "Checkout cart by id")
	@PostMapping(path = "/carts/{cart-id}/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CartDto> checkoutCart(@PathVariable("cart-id") Long cartId) {
		Cart cart = cartService.checkout(cartId);
		return new ResponseEntity<>(cartConverter.toDto(cart), HttpStatus.OK);
	}
}
