package org.springBootAngular.service;

import org.springBootAngular.dto.ProductDTO;
import org.springBootAngular.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response saveProduct(ProductDTO productDTO, MultipartFile imgFile);
    Response getAllProduct();
    Response getProductById(Long id);
    Response updateProduct(ProductDTO productDTO , MultipartFile imgFile);
    Response deleteProduct(Long id);
}
