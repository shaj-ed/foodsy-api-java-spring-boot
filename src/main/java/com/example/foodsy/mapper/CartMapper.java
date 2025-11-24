package com.example.foodsy.mapper;

import com.example.foodsy.dto.CartItemDTO;
import com.example.foodsy.dto.CartResponseDTO;
import com.example.foodsy.entity.CartItemsEntity;
import com.example.foodsy.entity.Carts;
import com.example.foodsy.repository.CartItemsRepository;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static CartItemDTO toCartItemDto(CartItemsEntity cart) {
        return CartItemDTO.builder()
                .id(cart.getId())
                .productId(cart.getProductEntity().getId())
                .productName(cart.getProductEntity().getProduct_name())
                .quantity(cart.getQuantity())
                .unitPrice(cart.getUnitPrice())
                .totalPrice(cart.getTotalPrice())
                .notes(cart.getNotes())
                .build();
    }

    public static CartResponseDTO toCartResponseDTO(Carts cart, Double totalPrice) {
        List<CartItemDTO> cartItems = new ArrayList<>();
        for (CartItemsEntity cartItemsEntity: cart.getCartItemsEntities()) {
            CartItemDTO newCartItemDto = getCartItemDTO(cartItemsEntity);
            cartItems.add(newCartItemDto);
        }
        return CartResponseDTO.builder()
                .id(cart.getId())
                .status(cart.getStatus())
                .total(totalPrice)
                .items(cartItems)
                .build();
    }

    private static CartItemDTO getCartItemDTO(CartItemsEntity cartItemsEntity) {
        CartItemDTO newCartItemDto = new CartItemDTO();
        newCartItemDto.setId(cartItemsEntity.getId());
        newCartItemDto.setProductId(cartItemsEntity.getProductEntity().getId());
        newCartItemDto.setProductName(cartItemsEntity.getProductEntity().getProduct_name());
        newCartItemDto.setQuantity(cartItemsEntity.getQuantity());
        newCartItemDto.setUnitPrice(cartItemsEntity.getUnitPrice());
        newCartItemDto.setTotalPrice(cartItemsEntity.getTotalPrice());
        newCartItemDto.setNotes(cartItemsEntity.getNotes());
        return newCartItemDto;
    }
}
