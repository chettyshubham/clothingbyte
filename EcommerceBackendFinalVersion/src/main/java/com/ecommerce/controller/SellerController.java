package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.custom_details.CustomUserDetails;
import com.ecommerce.dto.AuthRequest;
import com.ecommerce.dto.AuthResp;
import com.ecommerce.dto.Response;
import com.ecommerce.dto.SellerDto;
import com.ecommerce.entities.Seller;
import com.ecommerce.jwt_utils.JwtUtils;
import com.ecommerce.service.SellerService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/api/sellers")
@Slf4j
public class SellerController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private JwtUtils utils;
	// dep : Auth mgr
	@Autowired
	private AuthenticationManager manager;

	@PostMapping("/signup")
	public ResponseEntity<?> save(@RequestBody SellerDto seller) {
		sellerService.registerSeller(seller);
		return Response.success(seller);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<?> findAllSellers() {
		List<Seller> result = sellerService.allSellers();
		return Response.success(result);
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> findSellerProfile(@PathVariable Long id) {
		Seller result = sellerService.findById(id);
		return Response.success(result);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteSeller(@PathVariable("id") Long id) {
		sellerService.deleteSeller(id);
		return Response.status(HttpStatus.OK);
	}

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
			SellerDto sellerDto = sellerService.findSellerByEmail(user.getUsername());

			return Response.success(new AuthResp(sellerDto, utils.generateJwtToken(authenticatedDetails)));
		} catch (BadCredentialsException e) {
			// send back err resp code
			log.info("err " + e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}

	}

}
