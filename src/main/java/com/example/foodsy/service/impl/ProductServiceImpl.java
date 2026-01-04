package com.example.foodsy.service.impl;

import com.example.foodsy.dto.ProductRequestDTO;
import com.example.foodsy.dto.ProductResponseDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ImageEntity;
import com.example.foodsy.entity.ProductEntity;
import com.example.foodsy.mapper.ProductMapper;
import com.example.foodsy.repository.CategoryRepository;
import com.example.foodsy.repository.ProductRepository;
import com.example.foodsy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);

        if(productEntity.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        CategoryEntity categoryEntity = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ProductEntity product = productEntity.get();
        product.setProduct_name(productRequestDTO.getProductName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setCategoryEntity(categoryEntity);
        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }

    @Override
    public void deleteProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.deleteById(id);
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<byte[]> getSingleProductImages(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<byte[]> images = new ArrayList<>();
        if (productEntity.getImageEntity() != null) {
            List<ImageEntity> imageEntities = productEntity.getImageEntity();
            for(ImageEntity imageEntity: imageEntities) {
                images.add(imageEntity.getData());
            }
            return images;
        } else {
            throw new RuntimeException("No images found");
        }

    }

    @Override
    public Page<ProductEntity> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductEntity> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryEntity_Id(categoryId, pageable);
    }
}
