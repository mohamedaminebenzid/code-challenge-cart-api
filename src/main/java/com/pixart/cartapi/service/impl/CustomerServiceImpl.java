package com.pixart.cartapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pixart.cartapi.exceptionhandler.ElementNotFoundException;
import com.pixart.cartapi.model.Customer;
import com.pixart.cartapi.repository.CustomerRepository;
import com.pixart.cartapi.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer getCustomerByUsername(String username) throws ElementNotFoundException {
		return customerRepository.findByUsername(username)
				.orElseThrow(() -> new ElementNotFoundException("Customer", username));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

}
