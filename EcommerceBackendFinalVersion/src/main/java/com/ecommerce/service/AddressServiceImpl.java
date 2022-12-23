package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.AddressDao;
import com.ecommerce.entities.Address;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressDao dao;

	@Override
	public Address saveAddress(Address address) {
		// TODO Auto-generated method stub
		return dao.save(address);
	}

	@Override
	public Address findAddress(Long id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address Id is not valid !!"));
	}

}
