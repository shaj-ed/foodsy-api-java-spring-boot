package com.example.foodsy.dto;

import com.example.foodsy.entity.Carts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {
    private Long id;
    private Carts.STATUS status;
    private Double total;
    private List<CartItemDTO> items;
}
