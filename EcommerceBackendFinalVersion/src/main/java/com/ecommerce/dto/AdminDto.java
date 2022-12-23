package com.ecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdminDto {
	
	@Email(message = "Invalid Email")
	private String email;
	@NotBlank(message = "Password is required")
	private String pwd;
	@NotBlank(message = "User name is required")
	private String uname;

}
