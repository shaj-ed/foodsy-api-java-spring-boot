package com.example.foodsy.service;

import com.example.foodsy.dto.OrderDTO;
import com.example.foodsy.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO checkout(OrderDTO orderDTO, Long userId);
}
