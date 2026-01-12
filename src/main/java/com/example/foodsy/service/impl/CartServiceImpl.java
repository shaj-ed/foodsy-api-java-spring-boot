package com.example.foodsy.service.impl;

import com.example.foodsy.dto.AddItemRequestDTO;
import com.example.foodsy.dto.CartResponseDTO;
import com.example.foodsy.dto.UpdateQuantityDTO;
import com.example.foodsy.entity.CartItemsEntity;
import com.example.foodsy.entity.Carts;
import com.example.foodsy.entity.ProductEntity;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.exception.ResourceNotFoundException;
import com.example.foodsy.mapper.CartMapper;
import com.example.foodsy.repository.CartItemsRepository;
import com.example.foodsy.repository.CartRepository;
import com.example.foodsy.repository.ProductRepository;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemsRepository cartItemsRepository;

    @Override
    @Transactional
    public CartResponseDTO addItem(Long userId, AddItemRequestDTO addItemRequestDTO) {
        // 1. Check the user first
        // 2. Check a active cart is already have of that user
        // 3. If don't have active cart create a new cart
        // 4. Check if product exist in that cart items table
        // 5. If not exist than create one
        // 6. If exist than increment the quantity and total price
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if cart exist, if not than create one
        Carts cart = cartRepository.findByUserEntityAndStatus(userEntity, Carts.STATUS.ACTIVE)
                .orElseGet(() -> {
                    Carts newCart = new Carts();
                    newCart.setUserEntity(userEntity);
                    newCart.setStatus(Carts.STATUS.ACTIVE);
                    return cartRepository.save(newCart);
                });

        // Get the product
        ProductEntity product = productRepository.findById(addItemRequestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Find existing cart Item
        CartItemsEntity cartItem = cartItemsRepository
                .findByCart_IdAndProductEntity_Id(cart.getId(), addItemRequestDTO.getProductId())
                .orElse(null);

        if(cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + addItemRequestDTO.getQuantity());
            cartItem.setNotes(addItemRequestDTO.getNotes());
        } else {
            cartItem = new CartItemsEntity();
            cartItem.setNotes(addItemRequestDTO.getNotes());
            cartItem.setQuantity(addItemRequestDTO.getQuantity());
            cartItem.setProductEntity(product);
            cartItem.setUnitPrice(Double.valueOf(String.valueOf(product.getPrice())));
            cartItem.setCart(cart);
        }

        // Update the price
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getUnitPrice());

        // Save the product
        cartItemsRepository.save(cartItem);
        return CartMapper.toCartResponseDTO(cart, getCartTotalPrice(cart));
    }

    @Override
    public CartResponseDTO updateItemQuantity(Long productId, UpdateQuantityDTO updateQuantityDTO, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Carts cart = cartRepository.findByUserEntityAndStatus(userEntity, Carts.STATUS.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItemsEntity cartItem = cartItemsRepository.findByCart_IdAndProductEntity_Id(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cartItem.setQuantity(cartItem.getQuantity() + updateQuantityDTO.getQuantity());
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
        cartItemsRepository.save(cartItem);
        return CartMapper.toCartResponseDTO(cart, getCartTotalPrice(cart));
    }

    @Override
    public CartResponseDTO getActiveCart(Long userid) {
        UserEntity userEntity = userRepository.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Carts cart = cartRepository.findByUserEntityAndStatus(userEntity, Carts.STATUS.ACTIVE)
                .orElseGet(() -> {
                    Carts newCart = new Carts();
                    newCart.setUserEntity(userEntity);
                    newCart.setStatus(Carts.STATUS.ACTIVE);
                    return cartRepository.save(newCart);
                });
        return CartMapper.toCartResponseDTO(cart, getCartTotalPrice(cart));
    }

    @Override
    public CartResponseDTO removeItem(Long productId, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Carts cart = cartRepository.findByUserEntityAndStatus(userEntity, Carts.STATUS.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItemsEntity cartItem = cartItemsRepository.findByCart_IdAndProductEntity_Id(cart.getId(), productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cart.getCartItemsEntities().remove(cartItem);
        cartItemsRepository.delete(cartItem);
        return CartMapper.toCartResponseDTO(cart, getCartTotalPrice(cart));
    }

    private Double getCartTotalPrice(Carts cart) {
        double total = 0.0;
        for (CartItemsEntity cartItem: cart.getCartItemsEntities()) {
            total = total + cartItem.getTotalPrice();
        }
        return total;
    }
}
