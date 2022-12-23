package com.ecommerce.dto;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDTO {
	
	@JsonProperty(value ="prodid")
	private Long id;
	@Column(nullable = false, length = 30)
	private String pname;
	@Column(nullable = false, length = 30)
	private String pcat;
	@Column(nullable = false, length = 30)
	private String subcat;
	@Column(nullable = false)
	private double price;
	private Long sellerId;
	@Column(nullable = false, length = 30)
	private String brand;
	@Column(nullable = false)
	private MultipartFile pic;
	
}
