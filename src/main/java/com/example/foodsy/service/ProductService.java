package com.example.foodsy.service;

import com.example.foodsy.dto.ProductRequestDTO;
import com.example.foodsy.dto.ProductResponseDTO;
import com.example.foodsy.entity.ImageEntity;
import com.example.foodsy.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProductById(Long id);
    ProductEntity getProductById(Long id);
    List<byte[]> getSingleProductImages(Long id);
    Page<ProductEntity> getProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
