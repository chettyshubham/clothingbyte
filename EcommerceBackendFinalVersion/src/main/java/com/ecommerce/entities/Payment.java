package com.ecommerce.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

	@Column(nullable = false, length = 12)
	private String cardno;
	@Column(nullable = false, length = 50)
	private String nameoncard;
	@Column(nullable = false)
	private double amount;
	@Column(name = "payment_date", insertable = false, updatable = false)
	private LocalDateTime paymentdate;

}
