package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.custom_details.CustomUserDetails;
import com.ecommerce.dao.AdminDao;
import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dao.SellerDao;
import com.ecommerce.entities.Admin;
import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Seller;

@Service //
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
	// dep : user repository : based upon spring data JPA
	@Autowired
	private AdminDao adminRepo;

	@Autowired
	private CustomerDao customerRepo;

	@Autowired
	private SellerDao sellerRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("in load by user nm " + email);

		Admin admin = adminRepo.findByEmail(email);
		Customer cust = customerRepo.findByEmail(email);
		Seller seller = sellerRepo.findByEmail(email);

		if (cust != null)
			return new CustomUserDetails(cust);
		else if (seller != null)
			return new CustomUserDetails(seller);
		else
			return new CustomUserDetails(admin);

	}

}
