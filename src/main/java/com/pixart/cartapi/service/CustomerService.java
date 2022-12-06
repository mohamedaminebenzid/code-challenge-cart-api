package com.pixart.cartapi.service;

import java.util.List;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Customer;

public interface CustomerService {

	Customer getCustomerByUsername(String username) throws ElementNotFoundException;

	List<Customer> getAllCustomers();
}
