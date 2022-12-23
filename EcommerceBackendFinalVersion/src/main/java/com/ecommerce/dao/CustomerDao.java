package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long> {
	
	Customer findByEmail(String email);
}
