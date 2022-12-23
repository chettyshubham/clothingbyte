package com.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.SellerDao;
import com.ecommerce.dto.LoginRequestDto;
import com.ecommerce.dto.SellerDto;
import com.ecommerce.entities.Role;
import com.ecommerce.entities.Seller;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerDao dao;

	@Autowired
	private PasswordEncoder passEncoder;

	@Autowired
	private ModelMapper mapper;

	@Override
	public void registerSeller(SellerDto dto) {
		// TODO Auto-generated method stub
		dto.setCreatedTimestamp(LocalDateTime.now());
		Seller seller = mapper.map(dto, Seller.class);
		seller.setRole(Role.ROLE_SELLER);
		seller.setPwd(passEncoder.encode(seller.getPwd()));
		dao.save(seller);
	}

	@Override
	public List<Seller> allSellers() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Seller findById(Long id) {
		// TODO Auto-generated method stub
		return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seller not found!!!"));
	}

	@Override
	public SellerDto validate(LoginRequestDto dto) {
		Seller ss = dao.findByEmail(dto.getEmail());
		if (ss.getPwd().equals(dto.getPwd())) {
			SellerDto seller = mapper.map(ss, SellerDto.class);
			return seller;
		}
		return null;
	}

	@Override
	public void deleteSeller(Long id) {
		// TODO Auto-generated method stub
		Seller seller = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Seller not found!!!"));
//		List<Product> product=pdao.findBySeller(seller);
//		pdao.deleteAll(product);
		dao.delete(seller);

	}

	@Override
	public SellerDto findSellerByEmail(String email) {
		Seller ss = dao.findByEmail(email);
		SellerDto seller = mapper.map(ss, SellerDto.class);
		return seller;
	}
}
