package com.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dao.AdminDao;
import com.ecommerce.dto.AdminDto;
import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.entities.Admin;
import com.ecommerce.entities.Role;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao dao;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder password;

	@Override
	public Admin saveAdmin(Admin admin) {
		admin.setPwd(password.encode(admin.getPwd()));
		admin.setRole(Role.ROLE_ADMIN);
		return dao.save(admin);
	}

	@Override
	public AdminDto validate(LoginRequestDto dto) {
		Admin admin = dao.findByEmail(dto.getEmail());
		if (admin.getPwd().equals(dto.getPwd())) {
			AdminDto adminDto = mapper.map(admin, AdminDto.class);
			return adminDto;
		}

		return null;
	}

	@Override
	public void updateAdmin(AdminDto dto) {
		System.out.println(dto);
		Admin admin = dao.findByEmail(dto.getEmail());
		admin.setUname(dto.getUname());
		admin.setPwd(password.encode(dto.getPwd()));

		dao.flush();
	}

}
