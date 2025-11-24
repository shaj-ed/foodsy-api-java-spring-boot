package com.example.foodsy.controller;

import com.example.foodsy.dto.AddItemRequestDTO;
import com.example.foodsy.dto.CartResponseDTO;
import com.example.foodsy.dto.UpdateQuantityDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.service.CartService;
import com.example.foodsy.service.impl.CustomUserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<?> addItem(@Valid @RequestBody AddItemRequestDTO addItemRequestDTO, @AuthenticationPrincipal CustomUserDetailsImpl user) {
        CartResponseDTO cartResponseDTO = cartService.addItem(user.getId(), addItemRequestDTO);
        return ResponseEntity.ok(Map.of(
                "message", "Added item successfully",
                "data", cartResponseDTO
        ));
    }

    @PutMapping("/update-quantity/{id}")
    public ResponseEntity<?> updateProductQuantity(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuantityDTO updateQuantityDTO,
            @AuthenticationPrincipal CustomUserDetailsImpl user) {
        CartResponseDTO cartResponseDTO = cartService.updateItemQuantity(
                id, updateQuantityDTO, user.getId()
        );
        return ResponseEntity.ok(Map.of(
                "message", "Updated quantity successfully",
                "data", cartResponseDTO
        ));
    }

    @GetMapping
    public ResponseEntity<?> getActiveCart(@AuthenticationPrincipal CustomUserDetailsImpl user) {
        CartResponseDTO cartResponseDTO = cartService.getActiveCart(user.getId());
        return ResponseEntity.ok(Map.of(
                "data", cartResponseDTO
        ));
    }

    @DeleteMapping("/remove-item/{id}")
    public ResponseEntity<?> removeItem(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetailsImpl user) {
        CartResponseDTO cartResponseDTO = cartService.removeItem(id, user.getId());
        return ResponseEntity.ok(Map.of(
                "message", "Removed successfully",
                "data", cartResponseDTO
        ));
    }
}
