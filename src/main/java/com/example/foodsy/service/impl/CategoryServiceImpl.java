package com.example.foodsy.service.impl;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.repository.CategoryRepository;
import com.example.foodsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }
}
