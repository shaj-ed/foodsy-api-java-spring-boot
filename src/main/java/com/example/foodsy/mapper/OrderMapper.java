package com.example.foodsy.mapper;

import com.example.foodsy.dto.OrderResponseDTO;
import com.example.foodsy.entity.Order;

public class OrderMapper {
    public static OrderResponseDTO toOrderResponse(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setSubTotal(order.getSubTotal());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());

        return  orderResponseDTO;
    }
}
