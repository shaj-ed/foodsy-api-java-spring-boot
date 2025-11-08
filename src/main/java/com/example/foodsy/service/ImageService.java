package com.example.foodsy.service;

import com.example.foodsy.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageEntity uploadImage(Long categoryId, MultipartFile file) throws IOException;
}
