package com.example.foodsy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuantityDTO {
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity can't be zero or negative")
    private Integer quantity;
}
