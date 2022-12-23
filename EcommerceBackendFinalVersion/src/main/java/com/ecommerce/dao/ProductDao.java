package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.entities.Product;
import com.ecommerce.entities.Seller;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
	
	List<Product> findBySeller(Seller sellerId);
	List<Product> findByPcatAndSubcat(String pcat,String subcat);

}
