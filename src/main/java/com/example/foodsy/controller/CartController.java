package com.example.foodsy.controller;

import com.example.foodsy.dto.AddItemRequestDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.service.CartService;
import com.example.foodsy.service.impl.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<?> addItem(@Valid @RequestBody AddItemRequestDTO addItemRequestDTO, @AuthenticationPrincipal CustomUserDetailsImpl user) {
        cartService.addItem(user.getId(), addItemRequestDTO);
        return ResponseEntity.ok(Map.of(
                "message", "Added item successfully"
        ));
    }
}
