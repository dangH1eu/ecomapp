package com.project.ecomapp.service;

import com.project.ecomapp.dto.ProductDTO;
import com.project.ecomapp.dto.ProductImageDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.model.Product;
import com.project.ecomapp.model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(Long productId) throws Exception;
    Page<Product> getAllProducts(PageRequest pageRequest);
    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long id);
    boolean existsByName(String name);
    ProductImage createProductImage(
        Long productId,
        ProductImageDTO productImageDTO) throws Exception;

}
