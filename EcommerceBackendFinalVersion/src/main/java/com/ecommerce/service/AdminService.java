package com.ecommerce.service;

import com.ecommerce.dto.AdminDto;
import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.entities.Admin;

public interface AdminService {
	AdminDto validate(LoginRequestDto dto);

	void updateAdmin(AdminDto admin);

	Admin saveAdmin(Admin admin);
}
