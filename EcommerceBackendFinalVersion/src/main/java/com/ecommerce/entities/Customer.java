package com.ecommerce.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

	@Column(nullable = false, length = 30)
	private String name;
	@Column(nullable = false, length = 30)
	private String city;
	@Column(unique = true, nullable = false, length = 30)
	private String email;
	@Column(nullable = false, length = 500)
	private String pwd;
	@Column(nullable = false, length = 15)
	private String phone;
	@Column(nullable = false, length = 7)
	private String gender;
	@Column(name = "created_timestamp", insertable = true, updatable = false)
	private LocalDateTime createdTimestamp;
	@Enumerated(EnumType.STRING)
	private Role role;

}
