package com.example.foodsy.service.impl;

import com.example.foodsy.dto.*;
import com.example.foodsy.entity.*;
import com.example.foodsy.enums.OrderStatus;
import com.example.foodsy.exception.ResourceNotFoundException;
import com.example.foodsy.mapper.OrderMapper;
import com.example.foodsy.repository.CartItemsRepository;
import com.example.foodsy.repository.CartRepository;
import com.example.foodsy.repository.OrderRepository;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.OrderService;
import com.example.foodsy.utils.GenerateOrderNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GenerateOrderNumber generateOrderNumber;

    @Override
    public OrderResponseDTO checkout(OrderDTO orderDTO, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Carts cart = cartRepository.findByUserEntityAndStatus(user, Carts.STATUS.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItemsEntity> cartItemsEntities = cartItemsRepository.findByCartId(cart.getId());

        if(cartItemsEntities.isEmpty()) {
            throw new ResourceNotFoundException("Cart product is empty");
        }

        Order order = new Order();
        order.setOrderNumber(generateOrderNumber.generateOrderNumber());
        order.setUserEntity(user);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentType(orderDTO.getPaymentType());
        order.setFullName(orderDTO.getFullName());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setAddress(orderDTO.getAddress());

        BigDecimal subTotal = BigDecimal.ZERO;
        List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();

        for (CartItemsEntity cartItemsEntity: cartItemsEntities) {
            OrderItemsEntity item = new OrderItemsEntity();
            item.setOrder(order);
            item.setProductId(cartItemsEntity.getProductEntity().getId());
            item.setProductName(cartItemsEntity.getProductEntity().getProduct_name());
            item.setUnitPrice(cartItemsEntity.getUnitPrice());
            item.setTotalPrice(cartItemsEntity.getTotalPrice());

            subTotal = subTotal.add(item.getTotalPrice());

            orderItemsEntities.add(item);
        }

        order.setOrderItemsEntities(orderItemsEntities);
        order.setSubTotal(subTotal);
        order.setShippingFree(BigDecimal.valueOf(80));
        order.setTotalAmount(subTotal.add(order.getShippingFree()));

        cartRepository.delete(cart);

        return OrderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public Page<Order> getOrders(Pageable pageable, LocalDate from, LocalDate to, OrderStatus status, CustomUserDetailsImpl user) {
        System.out.println("Current User");
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch((role) -> role.getAuthority().equals("ADMIN"));

        if(isAdmin) {
            LocalDateTime toFromTime = from != null ? from.atStartOfDay() : null;
            LocalDateTime toToTime = to != null ? to.atStartOfDay() : null;
            return orderRepository.findOrders(toFromTime, toToTime, status, pageable);
        } else {
            return orderRepository.findByUser(user.getId(), pageable);
        }

    }

    @Override
    public OrderDetailsResponseDTO getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not available"));
        OrderEachDTO currentOrder = OrderMapper.toOrdersListResponse(order);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();

        for (OrderItemsEntity orderItem: order.getOrderItemsEntities()) {
            orderItemDTOS.add(OrderMapper.orderItemDTO(orderItem));
        }

        OrderDetailsResponseDTO responseDTO = new OrderDetailsResponseDTO();
        responseDTO.setOrder(currentOrder);
        responseDTO.setItems(orderItemDTOS);

        return responseDTO;
    }

    @Override
    public OrderEachDTO orderStatusUpdate(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        boolean isValidStatus = isValidTransitionStatus(order.getStatus(), newStatus);
        if(!isValidStatus) {
            throw new IllegalStateException("Can not change status" + order.getStatus() + "to" + newStatus);
        }
        order.setStatus(newStatus);
        Order updatedStatusOrder = orderRepository.save(order);
        return OrderMapper.toOrdersListResponse(updatedStatusOrder);
    }

    private boolean isValidTransitionStatus(OrderStatus currentStatus, OrderStatus nextStatus) {
       return switch (currentStatus) {
           case PENDING -> nextStatus == OrderStatus.CONFIRMED || nextStatus == OrderStatus.CANCELLED;
           case CONFIRMED -> nextStatus == OrderStatus.PROCESSING;
           case PROCESSING -> nextStatus == OrderStatus.OUT_FOR_DELIVERY;
           case OUT_FOR_DELIVERY -> nextStatus == OrderStatus.DELIVERED;
           case DELIVERED, CANCELLED -> false;
       };
    }

}
