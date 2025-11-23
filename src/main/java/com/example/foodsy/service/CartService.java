package com.example.foodsy.service;

import com.example.foodsy.dto.AddItemRequestDTO;

public interface CartService {
    void addItem(Long userId, AddItemRequestDTO addItemRequestDTO);
}
