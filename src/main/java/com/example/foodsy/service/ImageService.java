package com.example.foodsy.service;

import com.example.foodsy.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    ImageEntity uploadImage(Long categoryId, MultipartFile file) throws IOException;
    List<ImageEntity> uploadProductImages(Long productId, List<MultipartFile> files) throws IOException;
}
