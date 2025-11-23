package com.example.foodsy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequestDTO {
    @NotNull(message = "Product id is required")
    private Long productId;
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    private String notes;
}
