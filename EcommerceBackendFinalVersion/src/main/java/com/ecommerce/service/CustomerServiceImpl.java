package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dto.CustomerDto;
import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Role;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao dao;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passEncoder;

	@Override
	public void registerCustomer(CustomerDto cust) {
		cust.setCreatedTimestamp(LocalDateTime.now());
		Customer customer = mapper.map(cust, Customer.class);
		customer.setRole(Role.ROLE_CUSTOMER);
		customer.setPwd(passEncoder.encode(cust.getPwd()));
		dao.save(customer);
	}

	@Override
	public List<Customer> allCustomers() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Customer findUserById(Long id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found!!!"));
	}

	@Override
	public CustomerDto validate(LoginRequestDto dto) {

		Customer cc = dao.findByEmail(dto.getEmail());
		if (cc.getPwd().equals(dto.getPwd())) {
			CustomerDto cust = mapper.map(cc, CustomerDto.class);
			System.out.println(cust);
			return cust;
		}

		return null;
	}

	@Override
	public boolean verifyUserByEmail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmail(email) != null;
	}

	@Override
	public void updateProfile(Long id, CustomerDto cust) {
		// finding customer by id provided in path variable
		Customer customer = dao.findById(id).orElseThrow(() -> new RuntimeException("Customer not found!!!"));

		customer.setName(cust.getName());
		customer.setCity(cust.getCity());
		customer.setPwd(passEncoder.encode(cust.getPwd()));
		customer.setPhone(cust.getPhone());

		dao.flush(); // flushing changes to database
	}

	@Override
	public CustomerDto findCustomerByEmail(String email) {

		Customer cc = dao.findByEmail(email);
		CustomerDto cust = mapper.map(cc, CustomerDto.class);

		return cust;
	}

}
