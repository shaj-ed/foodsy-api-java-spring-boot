package com.example.foodsy.dto;

import com.example.foodsy.enums.OrderStatus;
import com.example.foodsy.enums.PAYMENT_TYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEachDTO {
    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private PAYMENT_TYPE paymentType;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private String fullName;
    private String phoneNumber;
    private String address;
    private LocalDateTime orderTime;
    private LocalDateTime updatedTime;
}
