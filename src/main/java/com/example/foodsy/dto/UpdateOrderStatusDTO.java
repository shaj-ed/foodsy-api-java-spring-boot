package com.example.foodsy.dto;

import com.example.foodsy.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusDTO {
    @NotNull(message = "Status is required")
    private OrderStatus status;
}
