package com.ecommerce.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetails extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	private int qty;
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

}
