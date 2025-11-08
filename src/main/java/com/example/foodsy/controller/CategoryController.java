package com.example.foodsy.controller;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.mapper.CategoryMapper;
import com.example.foodsy.service.CategoryService;
import com.example.foodsy.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ImageService imageService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryEntity category = CategoryMapper.toEntity(categoryDTO);
        CategoryEntity categoryEntity = categoryService.createCategory(category);
        return new ResponseEntity<>(CategoryMapper.toDto(categoryEntity), HttpStatus.OK);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadCategoryImage(@PathVariable Long id, @RequestPart MultipartFile file) throws IOException {
        imageService.uploadImage(id, file);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }
}
