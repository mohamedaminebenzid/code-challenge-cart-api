package com.pixart.cartapi.exceptionhandler;

public class ElementNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3246232479524489206L;

	public ElementNotFoundException() {
		super();
	}

	public ElementNotFoundException(String elementType, Object id) {
		super("Could not find " + elementType + " : " + id);
	}

}