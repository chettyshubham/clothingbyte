package com.ecommerce.custom_details;

import static com.ecommerce.entities.Role.ROLE_ADMIN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.entities.Admin;
import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Role;
import com.ecommerce.entities.Seller;

import lombok.ToString;

@ToString
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -1711265124531152677L;

	private Admin admin;
	private Customer customer;
	private Seller seller;

	public CustomUserDetails(Admin admin) {
		super();
		this.admin = admin;
	}

	public CustomUserDetails(Customer customer) {
		super();
		this.customer = customer;
	}

	public CustomUserDetails(Seller seller) {
		super();
		this.seller = seller;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<Role> role = new ArrayList<>();

		if (admin != null) {
			role.add(ROLE_ADMIN);
			return role.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
		} else if (customer != null) {
			role.add(Role.ROLE_CUSTOMER);
			return role.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());

		} else {
			role.add(Role.ROLE_SELLER);
			return role.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
		}

	}

	@Override
	public String getPassword() {
		if (admin != null)
			return admin.getPwd();
		else if (customer != null)
			return customer.getPwd();
		else
			return seller.getPwd();
	}

	@Override
	public String getUsername() {
		if (admin != null)
			return admin.getEmail();
		else if (customer != null)
			return customer.getEmail();
		else
			return seller.getEmail();
	}

	public String getName() {

		if (admin != null)
			return admin.getUname();
		else if (customer != null)
			return customer.getName();
		else
			return seller.getName();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
