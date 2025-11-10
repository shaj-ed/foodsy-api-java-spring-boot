package com.example.foodsy.service.impl;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.entity.CategoryEntity;
import com.example.foodsy.repository.CategoryRepository;
import com.example.foodsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<CategoryEntity> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);

        if(categoryEntity.isEmpty()) {
            throw new RuntimeException("Not found");
        }

        categoryRepository.deleteById(categoryEntity.orElseThrow().getId());
    }

    @Override
    public CategoryEntity updateCategory(Long id, CategoryEntity categoryEntity) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);

        if(category.isEmpty()) {
            throw new RuntimeException("Category Not found");
        }

        CategoryEntity selectedCategory = category.get();
        selectedCategory.setCategory_name(categoryEntity.getCategory_name());
        selectedCategory.setDescription(categoryEntity.getDescription());

        return categoryRepository.save(selectedCategory);
    }
}
