package com.example.foodsy.mapper;

import com.example.foodsy.dto.CreateProductResponse;
import com.example.foodsy.dto.ProductRequestDTO;
import com.example.foodsy.dto.ProductResponseDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ImageEntity;
import com.example.foodsy.entity.ProductEntity;

import java.util.List;

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
        byte[] image = new byte[0];

        List<ImageEntity> images = productEntity.getImageEntity();

        if (images != null && !images.isEmpty()) {
            image = images.getFirst().getData();
        }

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(productEntity.getId());
        productResponseDTO.setProductName(productEntity.getProduct_name());
        productResponseDTO.setPrice(productEntity.getPrice());
        productResponseDTO.setDescription(productEntity.getDescription());
        productResponseDTO.setCategoryId(productEntity.getCategoryEntity().getId());
        productResponseDTO.setImage(image);
        productResponseDTO.setCreatedAt(productEntity.getCreated_at());
        return productResponseDTO;
    }

    public static CreateProductResponse toCreateProductResponse(ProductEntity productEntity) {
        CreateProductResponse createProductResponse = new CreateProductResponse();
        createProductResponse.setId(productEntity.getId());
        createProductResponse.setMessage("Created successfully");
        return createProductResponse;
    }
}
