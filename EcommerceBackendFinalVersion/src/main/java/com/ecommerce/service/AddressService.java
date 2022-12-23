package com.ecommerce.service;

import com.ecommerce.entities.Address;

public interface AddressService {
	Address saveAddress(Address address);

	Address findAddress(Long id);
}
