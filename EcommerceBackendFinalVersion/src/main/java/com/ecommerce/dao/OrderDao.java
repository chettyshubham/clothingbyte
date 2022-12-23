package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
	List<Order> findByCustomer(Customer customer);
}
