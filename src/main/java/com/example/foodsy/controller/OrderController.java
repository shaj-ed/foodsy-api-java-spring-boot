package com.example.foodsy.controller;

import com.example.foodsy.dto.OrderDTO;
import com.example.foodsy.dto.OrderResponseDTO;
import com.example.foodsy.entity.Order;
import com.example.foodsy.service.OrderService;
import com.example.foodsy.service.impl.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    ResponseEntity<?> checkout(@Valid @RequestBody OrderDTO orderDTO, @AuthenticationPrincipal CustomUserDetailsImpl user) {
        OrderResponseDTO order = orderService.checkout(orderDTO, user.getId());

        return ResponseEntity.ok(Map.of(
                "message", "Successful",
                "data", order
        ));
    }
}
