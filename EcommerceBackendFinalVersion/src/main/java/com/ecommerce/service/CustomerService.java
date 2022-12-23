package com.ecommerce.service;

import java.util.List;

import com.ecommerce.dto.CustomerDto;
import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.entities.Customer;

public interface CustomerService {
	void registerCustomer(CustomerDto cust);

	List<Customer> allCustomers();

	Customer findUserById(Long id);

	CustomerDto validate(LoginRequestDto dto);

	boolean verifyUserByEmail(String email);

	void updateProfile(Long id, CustomerDto cust);

	CustomerDto findCustomerByEmail(String email);

}
