package com.example.foodsy.utils;

import org.springframework.stereotype.Component;

@Component
public class GenerateOrderNumber {
    public String generateOrderNumber() {
        return "ODR-" + System.currentTimeMillis();
    }
}
