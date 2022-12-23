package com.ecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartDTO {

	private Long prodid;
	private String pcat;
	private String pname;
	private double price;
	private int qty;
	
	
	
}
