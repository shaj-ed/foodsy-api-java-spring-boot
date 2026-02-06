package com.example.foodsy.service;

import com.example.foodsy.dto.OrderDTO;
import com.example.foodsy.dto.OrderDetailsResponseDTO;
import com.example.foodsy.dto.OrderEachDTO;
import com.example.foodsy.dto.OrderResponseDTO;
import com.example.foodsy.entity.Order;
import com.example.foodsy.enums.OrderStatus;
import com.example.foodsy.service.impl.CustomUserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface OrderService {
    OrderResponseDTO checkout(OrderDTO orderDTO, Long userId);
    Page<Order> getOrders(Pageable pageable, LocalDate from, LocalDate to, OrderStatus status, CustomUserDetailsImpl user);
    OrderDetailsResponseDTO getOrderDetails(Long orderId);
    OrderEachDTO orderStatusUpdate(Long orderId, OrderStatus orderStatus);
}
