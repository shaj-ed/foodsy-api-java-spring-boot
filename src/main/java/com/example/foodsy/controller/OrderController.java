package com.example.foodsy.controller;

import com.example.foodsy.dto.*;
import com.example.foodsy.entity.Order;
import com.example.foodsy.enums.OrderStatus;
import com.example.foodsy.mapper.OrderMapper;
import com.example.foodsy.service.OrderService;
import com.example.foodsy.service.impl.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    ResponseEntity<?> checkout(@Valid @RequestBody OrderDTO orderDTO, @AuthenticationPrincipal CustomUserDetailsImpl user) {
        OrderResponseDTO order = orderService.checkout(orderDTO, user.getId());

        return ResponseEntity.ok(Map.of(
                "message", "Successful",
                "data", order
        ));
    }

    @GetMapping
    ResponseEntity<?> getOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) OrderStatus status,
            @AuthenticationPrincipal CustomUserDetailsImpl user) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Order> orders = orderService.getOrders(pageable, from, to, status, user);

        if(orders.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No order available",
                    "data", ""));
        } else {
            List<OrderEachDTO> ordersList = new ArrayList<>();
            for(Order ord : orders.getContent()) {
                ordersList.add(OrderMapper.toOrdersListResponse(ord));
            }
            return ResponseEntity.ok(Map.of(
                    "message", "Order items",
                    "data", ordersList,
                    "pagination", Map.of(
                            "currentPage", orders.getNumber(),
                            "limit", orders.getSize(),
                            "totalPages", orders.getTotalPages(),
                            "totalItems", orders.getTotalElements(),
                            "hasNext", orders.hasNext(),
                            "hasPrev", orders.hasPrevious()
                    )
            ));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId) {
        OrderDetailsResponseDTO responseDTO = orderService.getOrderDetails(orderId);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,
                                               @Valid @RequestBody UpdateOrderStatusDTO orderStatusDTO) {
        OrderEachDTO orderEachDTO = orderService.orderStatusUpdate(orderId, orderStatusDTO.getStatus());
        return ResponseEntity.ok(Map.of(
                "message", "Updated status successfully",
                "data", orderEachDTO
        ));
    }
}
