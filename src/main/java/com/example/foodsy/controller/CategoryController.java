package com.example.foodsy.controller;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.dto.CategoryResponseDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.entity.ImageEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<String> uploadCategoryImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        imageService.uploadImage(id, file);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        List<CategoryEntity> categoryEntities = categoryService.getCategories();
        List<CategoryResponseDTO> categories = new ArrayList<>();
        for(CategoryEntity category: categoryEntities) {
            CategoryResponseDTO categoryDTO = CategoryMapper.toResponse(category);
            categories.add(categoryDTO);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriesById(@PathVariable Long id) {
        Optional<CategoryEntity> category = categoryService.getCategoryById(id);
        if(category.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "Category not found"));
        }

        CategoryEntity categoryEntity = category.get();
        return ResponseEntity.ok(Map.of(
                "message", "Success",
                "data", CategoryMapper.toResponse(categoryEntity)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(Map.of("message", "deleted successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryEntity category = categoryService.updateCategory(id, CategoryMapper.toEntity(categoryDTO));
        return ResponseEntity.ok(Map.of(
                "message", "Successfully updated",
                "data", CategoryMapper.toDto(category)
        ));
    }

    @PutMapping("/{id}/upload")
    public ResponseEntity<?> updateImage(@PathVariable Long id, @RequestPart("file") MultipartFile file) {
        try {
            ImageEntity imageEntity = imageService.updateCategoryImage(id, file);
            return ResponseEntity.ok(Map.of(
                    "message", "successfully updated",
                    "data", imageEntity.getData()
            ));
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
