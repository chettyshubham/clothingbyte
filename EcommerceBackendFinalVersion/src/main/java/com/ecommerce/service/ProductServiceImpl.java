package com.ecommerce.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.SellerDao;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.Seller;
import com.ecommerce.image_utils.ImageService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao dao;

	@Autowired
	SellerDao sellerDao;

	@Autowired
	private ImageService imageService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Product addProduct(ProductDTO dto, MultipartFile pic) throws IOException {
//		// TODO Auto-generated method stub
//		String photo=storageService.store(pic);
//		p.setPhoto(photo);
//		dao.save(p);
		Seller seller = sellerService.findById(dto.getSellerId());
		Product product = imageService.saveImage(dto, pic);
		product.setSeller(seller);
		product.setCreatedTimestamp(LocalDateTime.now());
		dao.save(product);
		return product;
	}

	@Override
	public List<Product> findProducts(Long sellerId) {

		return dao.findBySeller(sellerService.findById(sellerId));
	}

	@Override
	public void updateProduct(ProductDTO dto) {
//		Product pp=dao.getById(p.getProdid());
//		p.setSeller(pp.getSeller());
//		dao.save(p);
		System.out.println("before updating the product details");
		Product product = dao.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!!!"));
		// Seller seller = sellerDao.findById(dto.getSellerId()).orElseThrow(() -> new
		// ResourceNotFoundException("Seller not found!!!"));
		// product.setSeller(seller);
		System.out.println("after updating the product details");
		product.setPname(dto.getPname());
		product.setBrand(dto.getBrand());
		product.setSubcat(dto.getSubcat());
		product.setPcat(dto.getPcat());
		product.setPrice(dto.getPrice());
		dao.flush();
	}

	@Override
	public void deleteProduct(Long id) {
		// TODO Auto-generated method stub
		System.out.println("Product id = " + id);
		Product p = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!!!"));
		dao.delete(p);
	}

	@Override
	public List<Product> allProducts() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public ProductResponseDTO findProductById(Long id) {
		// TODO Auto-generated method stub
		Product product = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found!!!"));
		ProductResponseDTO productResponseDto = mapper.map(product, ProductResponseDTO.class);
		return productResponseDto;
	}

	@Override
	public List<Product> categoryProducts(String pcat, String subcat) {
		// TODO Auto-generated method stub
		return dao.findByPcatAndSubcat(pcat, subcat);
	}

	@Override
	public List<ProductResponseDTO> allProductsBySellerId(Optional<Long> sellerId) {
		List<ProductResponseDTO> result = new ArrayList<ProductResponseDTO>();
		if (sellerId.isPresent()) {
			System.out.println(sellerId);
			for (Product p : findProducts(sellerId.get())) {
				result.add(findProductById(p.getId()));
			}

		} else {
			for (Product p : allProducts()) {
				result.add(findProductById(p.getId()));
			}
		}
		return result;
	}

	@Override
	public List<ProductResponseDTO> productsByCatAndSubCat(String cat, String subCat) {
		List<ProductResponseDTO> result = new ArrayList<ProductResponseDTO>();
		for (Product p : categoryProducts(cat, subCat)) {
			result.add(mapper.map(p, ProductResponseDTO.class));
		}
		return result;
	}

}
