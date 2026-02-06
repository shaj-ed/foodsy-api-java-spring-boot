package com.example.foodsy.repository;

import com.example.foodsy.entity.Order;
import com.example.foodsy.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            Select o from Order o
            Where o.userEntity.id = :userId
            """)
    Page<Order> findByUser(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            select o from Order o
            where (:status is null or o.status = :status)
                and (:from is null or o.createdAt >= :from)
                and (:to is null or o.createdAt <= :to)
            """)
    Page<Order> findOrders(@Param("from") LocalDateTime from,
                           @Param("to") LocalDateTime to,
                           @Param("status")OrderStatus status,
                           Pageable pageable);
}
