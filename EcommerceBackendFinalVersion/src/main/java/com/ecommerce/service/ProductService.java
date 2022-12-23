package com.ecommerce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.entities.Product;

public interface ProductService {
	Product addProduct(ProductDTO dto, MultipartFile pic) throws IOException;

	List<Product> findProducts(Long sellerId);

	void updateProduct(ProductDTO dto);

	void deleteProduct(Long prodid);

	List<Product> allProducts();

	List<Product> categoryProducts(String pcat, String subcat);

	ProductResponseDTO findProductById(Long prodid);

	List<ProductResponseDTO> allProductsBySellerId(Optional<Long> sellerId);

	List<ProductResponseDTO> productsByCatAndSubCat(String cat, String subCat);
}
