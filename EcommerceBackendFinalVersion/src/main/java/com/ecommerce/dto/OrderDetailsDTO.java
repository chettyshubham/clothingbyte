package com.ecommerce.dto;

import com.ecommerce.entities.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderDetailsDTO {
	
	private Long id;
	private Product product;
	private int qty;

}
