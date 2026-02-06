package com.example.foodsy.mapper;

import com.example.foodsy.dto.OrderItemDTO;
import com.example.foodsy.dto.OrderResponseDTO;
import com.example.foodsy.dto.OrderEachDTO;
import com.example.foodsy.entity.Order;
import com.example.foodsy.entity.OrderItemsEntity;

public class OrderMapper {
    public static OrderResponseDTO toOrderResponse(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setSubTotal(order.getSubTotal());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());

        return  orderResponseDTO;
    }

    public static OrderEachDTO toOrdersListResponse(Order order) {
        OrderEachDTO orderEachDTO = new OrderEachDTO();
        orderEachDTO.setId(order.getId());
        orderEachDTO.setOrderNumber(order.getOrderNumber());
        orderEachDTO.setStatus(order.getStatus());
        orderEachDTO.setPaymentType(order.getPaymentType());
        orderEachDTO.setSubTotal(order.getSubTotal());
        orderEachDTO.setShippingFee(order.getShippingFree());
        orderEachDTO.setTotalAmount(order.getTotalAmount());
        orderEachDTO.setFullName(order.getFullName());
        orderEachDTO.setPhoneNumber(order.getPhoneNumber());
        orderEachDTO.setPhoneNumber(order.getPhoneNumber());
        orderEachDTO.setAddress(order.getAddress());
        orderEachDTO.setOrderTime(order.getCreatedAt());
        orderEachDTO.setUpdatedTime(order.getUpdatedAt());

        return orderEachDTO;
    }

    public static OrderItemDTO orderItemDTO(OrderItemsEntity orderItemsEntity) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItemsEntity.getId());
        orderItemDTO.setOrderId(orderItemsEntity.getOrder().getId());
        orderItemDTO.setProductId(orderItemsEntity.getProductId());
        orderItemDTO.setProductName(orderItemsEntity.getProductName());
        orderItemDTO.setUnitPrice(orderItemsEntity.getUnitPrice());
        orderItemDTO.setTotalPrice(orderItemsEntity.getTotalPrice());

        return orderItemDTO;
    }
}
