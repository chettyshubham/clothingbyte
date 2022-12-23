package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.PaymentDao;
import com.ecommerce.entities.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao dao;

	@Override
	public Payment savePayment(Payment payment) {
		// TODO Auto-generated method stub
		return dao.save(payment);
	}

	@Override
	public Payment findPaymentById(Long id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment Id is not valid !!"));
	}

}
