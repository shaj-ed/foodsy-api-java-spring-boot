package com.example.foodsy.repository;

import com.example.foodsy.entity.CartItemsEntity;
import com.example.foodsy.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItemsEntity, Long> {
    Optional<CartItemsEntity> findByCart_IdAndProductEntity_Id(Long cartId, Long productId);
    List<CartItemsEntity> findByCartId(Long cartId);
}
