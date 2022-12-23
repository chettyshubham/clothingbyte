package com.ecommerce.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SellerDto {
	
	private Long id;
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "City is required")
	private String city;
	@Email(message = "Invalid Email")
	private String email;
	@NotBlank(message = "Password is required")
	private String pwd;
	@NotBlank(message = "Phone No is required")
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phone;
	private LocalDateTime createdTimestamp;
}
