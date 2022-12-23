package com.ecommerce.image_utils;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;

public interface ImageService {

	Product saveImage(ProductDTO dto, MultipartFile multiFile) throws IOException;

	byte[] getImage(Long id) throws IOException;
}
