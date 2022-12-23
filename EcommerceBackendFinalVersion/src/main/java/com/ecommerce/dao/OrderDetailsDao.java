package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderDetails;

@Repository
public interface OrderDetailsDao extends JpaRepository<OrderDetails, Long> {
	List<OrderDetails> findByOrder(Order order);
}
