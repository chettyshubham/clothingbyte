package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.custom_details.CustomUserDetails;
import com.ecommerce.dto.AdminDto;
import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.AuthResp;
import com.ecommerce.dto.Response;
import com.ecommerce.entities.Admin;
import com.ecommerce.jwt_utils.JwtUtils;
import com.ecommerce.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private JwtUtils utils;
	// dep : Auth mgr
	@Autowired
	private AuthenticationManager manager;

	@PostMapping("/signin")
	public ResponseEntity<?> validateUserCreateToken(@RequestBody AuthRequest request) {
		// store incoming user details(not yet validated) into Authentication object
		// Authentication i/f ---> implemented by UserNamePasswordAuthToken
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
				request.getPassword());
		log.info("auth token " + authToken);
		try {
			// authenticate the credentials
			Authentication authenticatedDetails = manager.authenticate(authToken);
			log.info("auth token again " + authenticatedDetails);
			CustomUserDetails user = (CustomUserDetails) authenticatedDetails.getPrincipal();
			// => auth succcess
			return Response.success(new AuthResp(user, utils.generateJwtToken(authenticatedDetails)));
		} catch (BadCredentialsException e) {
			// send back err resp code
			log.info("err " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

	@PostMapping("/save")
	public ResponseEntity<?> saveAdmin(@RequestBody Admin admin) {
		return ResponseEntity.ok(adminService.saveAdmin(admin));

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<?> updateProfile(@RequestBody AdminDto admin) {
		adminService.updateAdmin(admin);
		return Response.status(HttpStatus.OK);
	}

}
