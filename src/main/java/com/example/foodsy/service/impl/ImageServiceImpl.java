package com.example.foodsy.service.impl;

import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ImageEntity;
import com.example.foodsy.repository.CategoryRepository;
import com.example.foodsy.repository.ImageRepository;
import com.example.foodsy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

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
}
