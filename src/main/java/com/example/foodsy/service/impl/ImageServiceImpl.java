package com.example.foodsy.service.impl;

import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ImageEntity;
import com.example.foodsy.entity.ProductEntity;
import com.example.foodsy.repository.CategoryRepository;
import com.example.foodsy.repository.ImageRepository;
import com.example.foodsy.repository.ProductRepository;
import com.example.foodsy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ImageEntity uploadImage(Long categoryId, MultipartFile file) throws IOException {
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ImageEntity image = new ImageEntity(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                category
        );

        category.setImageEntity(image);
        return imageRepository.save(image);
    }

    @Override
    public List<ImageEntity> uploadProductImages(Long productId, List<MultipartFile> files) throws IOException {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ImageEntity> imageEntities = new ArrayList<>();
        for (MultipartFile file: files) {
            ImageEntity image = new ImageEntity();
            image.setFile_name(file.getOriginalFilename());
            image.setFile_type(file.getContentType());
            image.setData(file.getBytes());
            image.setProductEntity(productEntity);

            imageEntities.add(image);
        }

        productEntity.getImageEntity().clear();
        productEntity.getImageEntity().addAll(imageEntities);
        return imageRepository.saveAll(imageEntities);
    }

    @Override
    public ImageEntity updateCategoryImage(Long categoryId, MultipartFile file) throws IOException {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ImageEntity existingImage = categoryEntity.getImageEntity();

        if(existingImage != null) { // has image
            existingImage.setFile_name(file.getOriginalFilename());
            existingImage.setFile_type(file.getContentType());
            existingImage.setData(file.getBytes());
            imageRepository.save(existingImage);

            return  existingImage;
        } else { // don't have image
            ImageEntity image = new ImageEntity();
            image.setFile_name(file.getOriginalFilename());
            image.setFile_type(file.getContentType());
            image.setData(file.getBytes());
            image.setCategoryEntity(categoryEntity);
            imageRepository.save(image);

            categoryEntity.setImageEntity(image);
            categoryRepository.save(categoryEntity);

            return  image;
        }
    }

    @Override
    public void updateProductImages(Long productId, List<MultipartFile> files) throws IOException {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productEntity.getImageEntity().clear();

        for(MultipartFile file: files) {
            ImageEntity image = new ImageEntity();
            image.setFile_type(file.getContentType());
            image.setFile_name(file.getOriginalFilename());
            image.setData(file.getBytes());
            image.setProductEntity(productEntity);

            productEntity.getImageEntity().add(image);
        }

        productRepository.save(productEntity);
    }
}
