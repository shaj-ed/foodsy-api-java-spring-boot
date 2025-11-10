package com.example.foodsy.service;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);
    List<CategoryEntity> getCategories();
    Optional<CategoryEntity> getCategoryById(Long id);
    void deleteCategoryById(Long id);
    CategoryEntity updateCategory(Long id, CategoryEntity categoryEntity);
}
