package com.example.foodsy.mapper;

import com.example.foodsy.dto.CartItemDTO;
import com.example.foodsy.dto.CartResponseDTO;
import com.example.foodsy.entity.CartItemsEntity;
import com.example.foodsy.entity.Carts;
import com.example.foodsy.repository.CartItemsRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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

    public static CartResponseDTO toCartResponseDTO(Carts cart, BigDecimal totalPrice) {
        List<CartItemDTO> cartItems = new ArrayList<>();
        BigDecimal deliveryCost = BigDecimal.valueOf(80);
        BigDecimal totalAmount = totalPrice.add(deliveryCost);
        for (CartItemsEntity cartItemsEntity: cart.getCartItemsEntities()) {
            CartItemDTO newCartItemDto = getCartItemDTO(cartItemsEntity);
            cartItems.add(newCartItemDto);
        }
        return CartResponseDTO.builder()
                .id(cart.getId())
                .status(cart.getStatus())
                .total(totalPrice)
                .deliveryFee(deliveryCost)
                .totalAmount(totalAmount)
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

    public static CartResponseDTO empty() {
        return CartResponseDTO.builder()
                .id(null)
                .status(Carts.STATUS.ABANDONED)
                .total(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .deliveryFee(BigDecimal.ZERO)
                .items(Collections.emptyList())
                .build();
    }
}
