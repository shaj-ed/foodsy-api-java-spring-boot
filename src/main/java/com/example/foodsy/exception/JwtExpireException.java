package com.example.foodsy.exception;

public class JwtExpireException extends RuntimeException {
    public JwtExpireException(String message) {
        super(message);
    }
}
