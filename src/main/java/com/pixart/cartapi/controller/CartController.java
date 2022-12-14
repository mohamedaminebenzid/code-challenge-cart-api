package com.pixart.cartapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
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

import com.pixart.cartapi.assembler.CartItemModelAssembler;
import com.pixart.cartapi.assembler.CartModelAssembler;
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
import com.pixart.cartapi.utils.CartStatus;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/cart-api/v1")
public class CartController {

	private static final String METHOD_NOT_ALLOWED = "Method not allowed";

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

	@Autowired
	private CartModelAssembler cartModelAssembler;

	@Autowired
	private CartItemModelAssembler cartItemModelAssembler;

	@Operation(summary = "Create new cart for the current customer")
	@PostMapping(path = "/customers/{customer-username}/carts", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EntityModel<CartDto>> createNewCart(@PathVariable("customer-username") String username) {

		Customer customer = customerService.getCustomerByUsername(username);

		Cart cart = cartService.save(Cart.newInstance(customer));

		return ResponseEntity.created(linkTo(methodOn(CartController.class).getCart(cart.getId())).toUri())
				.body(cartModelAssembler.toModel(cartConverter.toDto(cart)));
	}

	@Operation(summary = "Add new item to the current cart")
	@PostMapping(path = "/carts/{cart-id}/items", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addNewItemToCart(@PathVariable("cart-id") Long cartId,
			@RequestBody CartItemDto carItemDto) {

		Cart cart = cartService.getCartById(cartId);

		CartStatus status = cart.getStatus();
		if (status != CartStatus.CHECKOUT) {
			CartItem cartItem = cartItemConverter.toEntity(carItemDto);
			cart = cartService.addItemToCart(cart, cartItem);
			return ResponseEntity.created(linkTo(methodOn(CartController.class).getCart(cartId)).toUri())
					.body(cartModelAssembler.toModel(cartConverter.toDto(cart)));
		}

		return createMethodNotAllowedResponse("You can't add an item to a cart that is in the status" + status);

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
	public EntityModel<CartItemDto> getCartItem(@PathVariable("item-id") Long itemId) {

		CartItem cartItem = cartItemService.getCartItemById(itemId);

		return cartItemModelAssembler.toModel(cartItemConverter.toDto(cartItem));

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
	public ResponseEntity<?> deleteCartItem(@PathVariable("item-id") Long itemId) {

		CartItem cartItem = cartItemService.getCartItemById(itemId);

		Cart cart = cartItem.getCart();
		CartStatus status = cart.getStatus();
		if (status == CartStatus.CHECKOUT) {
			return createMethodNotAllowedResponse("You can't delete an item within a cart that is in the status " + status);
		}

		cartService.deleteItem(cartItem);

		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Get cart details(View Cart) by id")
	@GetMapping(path = "/carts/{cart-id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<CartDto> getCart(@PathVariable("cart-id") Long cartId) {

		Cart cart = cartService.getCartById(cartId);

		CartDto cartDto = cartConverter.toDto(cart);

		return cartModelAssembler.toModel(cartDto);

	}

	@Operation(summary = "Checkout cart by id")
	@PutMapping(path = "/carts/{cart-id}/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> checkoutCart(@PathVariable("cart-id") Long cartId) {

		Cart cart = cartService.getCartById(cartId);

		if (cart.getStatus() == CartStatus.BUILDING) {
			cart = cartService.checkout(cartId);
			return ResponseEntity.ok(cartModelAssembler.toModel(cartConverter.toDto(cart)));
		}

		return createMethodNotAllowedResponse("You can't checkout a cart that is in the status " + cart.getStatus());
	}

	private ResponseEntity<Problem> createMethodNotAllowedResponse(String detail) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle(METHOD_NOT_ALLOWED).withDetail(detail));
	}
}
