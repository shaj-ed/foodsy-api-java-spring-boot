package com.example.foodsy.service;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.entity.CategoryEntity;

public interface CategoryService {
    CategoryEntity createCategory(CategoryEntity category);
}
