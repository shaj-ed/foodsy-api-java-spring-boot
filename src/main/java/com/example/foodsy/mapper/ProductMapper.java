package com.example.foodsy.mapper;

import com.example.foodsy.dto.ProductRequestDTO;
import com.example.foodsy.dto.ProductResponseDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ProductEntity;

public class ProductMapper {
    public static ProductEntity toEntity(ProductRequestDTO productDTO, CategoryEntity category) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProduct_name(productDTO.getProductName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setCategoryEntity(category);
        return productEntity;
    }

    public static ProductResponseDTO toResponse(ProductEntity productEntity) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(productEntity.getId());
        productResponseDTO.setProductName(productEntity.getProduct_name());
        productResponseDTO.setPrice(productEntity.getPrice());
        productResponseDTO.setDescription(productEntity.getDescription());
        productResponseDTO.setCategoryId(productEntity.getCategoryEntity().getId());
        productResponseDTO.setCreatedAt(productEntity.getCreated_at());
        return productResponseDTO;
    }
}
