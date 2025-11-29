package com.example.foodsy.service;

import com.example.foodsy.dto.AuthResponse;
import com.example.foodsy.dto.LoginRequestDTO;
import com.example.foodsy.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    UserEntity createUser(UserEntity userEntity);
    AuthResponse verifyUser(LoginRequestDTO loginRequestDTO, HttpServletResponse response);
    String refreshAccessToken(String refreshToken);
}
