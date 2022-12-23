package com.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {

	Admin findByEmail(String email);
}
