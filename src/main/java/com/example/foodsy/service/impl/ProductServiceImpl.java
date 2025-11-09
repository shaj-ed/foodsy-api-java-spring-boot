package com.example.foodsy.service.impl;

import com.example.foodsy.dto.ProductRequestDTO;
import com.example.foodsy.dto.ProductResponseDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ProductEntity;
import com.example.foodsy.mapper.ProductMapper;
import com.example.foodsy.repository.CategoryRepository;
import com.example.foodsy.repository.ProductRepository;
import com.example.foodsy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        CategoryEntity category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductEntity productEntity = ProductMapper.toEntity(productRequestDTO, category);
        ProductEntity saved = productRepository.save(productEntity);
        return ProductMapper.toResponse(saved);
    }
}
