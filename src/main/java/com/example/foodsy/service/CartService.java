package com.example.foodsy.service;

import com.example.foodsy.dto.AddItemRequestDTO;
import com.example.foodsy.dto.CartResponseDTO;
import com.example.foodsy.dto.UpdateQuantityDTO;

public interface CartService {
    CartResponseDTO addItem(Long userId, AddItemRequestDTO addItemRequestDTO);
    CartResponseDTO updateItemQuantity(Long productId, UpdateQuantityDTO updateQuantityDTO, Long userId);
    CartResponseDTO getActiveCart(Long userid);
    CartResponseDTO removeItem(Long productId, Long userId);
}
