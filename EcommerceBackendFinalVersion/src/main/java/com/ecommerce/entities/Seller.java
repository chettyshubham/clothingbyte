package com.ecommerce.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {

	@Column(nullable = false, length = 30)
	private String name;
	@Column(nullable = false, length = 30)
	private String city;
	@Column(unique = true, nullable = false, length = 30)
	private String email;
	@Column(nullable = false)
	private String pwd;
	@Column(nullable = false, length = 15)
	private String phone;
	@Column(name = "created_timestamp", insertable = true, updatable = false)
	private LocalDateTime createdTimestamp;
	@Enumerated(EnumType.STRING)
	private Role role;
	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL) // mandatory : o.w mapping exc
	@JsonIgnore
	private List<Product> products = new ArrayList<>();

}
