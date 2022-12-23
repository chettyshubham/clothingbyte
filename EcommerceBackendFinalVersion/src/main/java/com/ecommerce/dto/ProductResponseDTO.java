package com.ecommerce.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductResponseDTO {
	
	private String brand;
	@JsonProperty(value="prodid")
	private Long id;
	private String pname;
	private String pcat;
	private String subcat;
	private double price;
	private Long sellerId;
	private String sellerName;
	private String photo;
	private LocalDateTime createdTimestamp;
	
}
