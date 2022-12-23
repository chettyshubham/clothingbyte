package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Seller;

@Repository
public interface SellerDao extends JpaRepository<Seller, Long> {
	
	Seller findByEmail(String email);

}
