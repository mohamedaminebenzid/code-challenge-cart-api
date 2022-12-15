package com.pixart.cartapi.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3246232479524489206L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String elementType, Object id) {
		super("Could not find " + elementType + " : " + id);
	}

}