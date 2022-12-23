package com.ecommerce.service;

import java.util.List;

import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.SellerDto;
import com.ecommerce.entities.Seller;

public interface SellerService {
	void registerSeller(SellerDto seller);

	List<Seller> allSellers();

	Seller findById(Long id);

	SellerDto validate(LoginRequestDto dto);

	void deleteSeller(Long id);

	SellerDto findSellerByEmail(String email);

}
