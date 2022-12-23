package com.ecommerce.service;

import java.util.List;

import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderDetails;

public interface OrderdetailService {

	void saveOrderDetails(OrderDetails od);

	OrderDetails findById(Long id);

	List<OrderDetails> findByOrder(Order order);
}
