package com.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.custom_details.CustomUserDetails;
import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.AuthResp;
import com.ecommerce.dto.CustomerDto;
import com.ecommerce.dto.Response;
import com.ecommerce.entities.Customer;
import com.ecommerce.jwt_utils.JwtUtils;
import com.ecommerce.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private JwtUtils utils;
	// dep : Auth mgr
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/signup")
	public ResponseEntity<?> save(@RequestBody @Valid CustomerDto cust) {
		customerService.registerCustomer(cust);
		return Response.success(cust);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<?> findAllCustomers() {
		List<Customer> result = customerService.allCustomers();
		return Response.success(result);
	}

	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> findCustomerById(@PathVariable("id") Long id) {
		Customer result = customerService.findUserById(id);
		CustomerDto customerDto = mapper.map(result, CustomerDto.class);
		return Response.success(customerDto);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> validateUserCreateToken(@RequestBody AuthRequest request) {
		// store incoming user details(not yet validated) into Authentication object
		// Authentication i/f ---> implemented by UserNamePasswordAuthToken
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		log.info("auth token " + authToken);
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = manager.authenticate(authToken);
			log.info("auth token again " + authenticatedDetails);
			CustomUserDetails user = (CustomUserDetails) authenticatedDetails.getPrincipal();
			CustomerDto customerDto = customerService.findCustomerByEmail(user.getUsername());
			// => auth succcess
			return Response.success(new AuthResp(customerDto, utils.generateJwtToken(authenticatedDetails)));
		} catch (BadCredentialsException e) {
			log.info("err " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProfile(@RequestBody @Valid CustomerDto cust, @PathVariable Long id) {
		customerService.updateProfile(id, cust);
		return Response.status(HttpStatus.OK);
	}

}
