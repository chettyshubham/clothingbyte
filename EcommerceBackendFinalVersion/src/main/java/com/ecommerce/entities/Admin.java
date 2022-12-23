package com.ecommerce.entities;

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
@NoArgsConstructor
@ToString
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {

	@Column(unique = true, nullable = false, length = 30)
	private String email;
	@Column(nullable = false)
	private String pwd;
	@Column(nullable = false, length = 30)
	private String uname;
	@Enumerated(EnumType.STRING)
	private Role role;
}
