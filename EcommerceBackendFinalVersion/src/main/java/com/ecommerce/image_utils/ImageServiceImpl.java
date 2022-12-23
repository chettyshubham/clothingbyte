package com.ecommerce.image_utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.custom_exception.ResourceNotFoundException;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImageServiceImpl implements ImageService {

	@Value("${file.upload.location}")
	private String folderLocation;

	@Autowired
	private ProductDao dao;

	@Autowired
	private ModelMapper mapper;

	@PostConstruct
	public void anyInit() {
		log.info(folderLocation);

		File folder = new File(folderLocation);

		if (!folder.exists())
			folder.mkdirs();
		else
			log.info("folder already exists");

	}

	@Override
	public Product saveImage(ProductDTO dto, MultipartFile multiFile) throws IOException {
		Product product = mapper.map(dto, Product.class);
		String path = folderLocation + File.separator + multiFile.getOriginalFilename();
		product.setPhoto(path);
		Files.copy(multiFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		return product;
	}

	@Override
	public byte[] getImage(Long id) throws IOException {
		Product product = dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not Found!!!"));
		String path = product.getPhoto();

		return Files.readAllBytes(Paths.get(path));
	}

}
