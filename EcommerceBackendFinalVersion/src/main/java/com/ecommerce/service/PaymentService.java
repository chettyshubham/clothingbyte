package com.ecommerce.service;

import com.ecommerce.entities.Payment;

public interface PaymentService {
	Payment savePayment(Payment payment);

	Payment findPaymentById(Long id);
}
