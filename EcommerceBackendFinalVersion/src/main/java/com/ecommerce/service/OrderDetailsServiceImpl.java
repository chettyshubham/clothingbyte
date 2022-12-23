package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.OrderDetailsDao;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderDetails;

@Service
public class OrderDetailsServiceImpl implements OrderdetailService {

	@Autowired
	OrderDetailsDao dao;

	@Override
	public void saveOrderDetails(OrderDetails od) {
		// TODO Auto-generated method stub
		dao.save(od);
	}

	@Override
	public OrderDetails findById(Long id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Id is not valid !!"));
	}

	@Override
	public List<OrderDetails> findByOrder(Order order) {
		// TODO Auto-generated method stub
		return dao.findByOrder(order);
	}

}
