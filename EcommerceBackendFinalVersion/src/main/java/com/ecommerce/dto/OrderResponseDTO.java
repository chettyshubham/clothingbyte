package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.entities.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderResponseDTO {

	private Order order;
	private List<OrderDetailsDTO> details;

}
