package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.entities.Address;
import com.ecommerce.entities.Payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PlaceOrderDTO {

	private Address address;
	private List<CartDTO> cart;
	private Payment payment;
	private Long customerid;

}
