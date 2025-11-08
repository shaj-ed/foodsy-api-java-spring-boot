package com.example.foodsy.mapper;

import com.example.foodsy.dto.CategoryDTO;
import com.example.foodsy.entity.CategoryEntity;

public class CategoryMapper {
    public static CategoryDTO toDto(CategoryEntity categoryEntity) {
        return new CategoryDTO(categoryEntity.getId(), categoryEntity.getCategory_name(), categoryEntity.getDescription());
    }

    public static CategoryEntity toEntity(CategoryDTO categoryDTO) {
        return new CategoryEntity(categoryDTO.getId(), categoryDTO.getCategoryName(), categoryDTO.getDescription());
    }
}
