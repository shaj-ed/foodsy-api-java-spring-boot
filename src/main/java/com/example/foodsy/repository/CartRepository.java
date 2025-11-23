package com.example.foodsy.repository;

import com.example.foodsy.entity.Carts;
import com.example.foodsy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts, Long> {
    Optional<Carts> findByUserEntityAndStatus(UserEntity userEntity, Carts.STATUS status);
}
