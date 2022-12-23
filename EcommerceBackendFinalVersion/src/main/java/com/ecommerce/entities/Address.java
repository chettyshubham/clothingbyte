package com.ecommerce.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

	@Column(nullable = false, length = 20)
	private String city;
	@Column(nullable = false, length = 20)
	private String state;
	@Column(nullable = false, length = 10)
	private String zip;
	@Column(nullable = false, length = 20)
	private String country;

}
