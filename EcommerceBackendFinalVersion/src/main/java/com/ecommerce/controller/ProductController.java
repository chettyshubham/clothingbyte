package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.dto.Response;
import com.ecommerce.entities.Product;
import com.ecommerce.image_utils.ImageService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.SellerService;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ImageService imageService;

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PostMapping
	public ResponseEntity<?> saveProduct(ProductDTO dto) throws IOException {
		Product product = productService.addProduct(dto, dto.getPic());
		return Response.success(product);
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto, @PathVariable Long id) {
		productService.updateProduct(dto);
		return Response.success(dto);
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> findProduct(@PathVariable Long id) {
		ProductResponseDTO productResponseDTO = productService.findProductById(id);
		return Response.success(productResponseDTO);
	}

	@GetMapping("/allproducts")
	public ResponseEntity<?> findAllProducts(@RequestParam Optional<Long> sellerId) {
		List<ProductResponseDTO> result = productService.allProductsBySellerId(sellerId);
		return Response.success(result);
	}

	@GetMapping("/cats")
	public ResponseEntity<?> findCategoryProducts(@RequestParam String cat, @RequestParam String subcat) {
		List<ProductResponseDTO> result = productService.productsByCatAndSubCat(cat, subcat);
		return Response.success(result);
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return Response.status(HttpStatus.OK);
	}

	@GetMapping(value = "/image/{id}", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> getImage(@PathVariable Long id) throws IOException {

		return ResponseEntity.ok(imageService.getImage(id));
	}
}
