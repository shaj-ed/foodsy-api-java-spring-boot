package com.example.foodsy.service.impl;

import com.example.foodsy.dto.OrderDTO;
import com.example.foodsy.dto.OrderResponseDTO;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
}
