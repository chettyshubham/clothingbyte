package com.ecommerce.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "products")
public class Product extends BaseEntity {

	@Column(nullable = false, length = 50)
	private String pname;
	@Column(nullable = false, length = 50)
	private String brand;
	@Column(nullable = false, length = 50)
	private String pcat;
	@Column(nullable = false, length = 50)
	private String subcat;
	@Column(nullable = false)
	private double price;
	private String photo;
	@Column(name = "created_timestamp", insertable = true, updatable = false)
	private LocalDateTime createdTimestamp;
	@ManyToOne
	@JoinColumn(name = "sellerId", nullable = false)
	private Seller seller;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL) // mandatory : o.w mapping exc
	@JsonIgnore
	private List<OrderDetails> orderDetails = new ArrayList<>();

}
