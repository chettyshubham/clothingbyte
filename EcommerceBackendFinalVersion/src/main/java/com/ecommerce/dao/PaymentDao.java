package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entities.Payment;

public interface PaymentDao extends JpaRepository<Payment, Long> {

}
