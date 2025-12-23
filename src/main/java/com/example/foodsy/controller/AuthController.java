package com.example.foodsy.controller;

import com.example.foodsy.dto.AuthResponse;
import com.example.foodsy.dto.LoginRequestDTO;
import com.example.foodsy.dto.UserRequestDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.mapper.UserMapper;
import com.example.foodsy.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        System.out.println(userRequestDTO);
        System.out.println(UserMapper.toEntity(userRequestDTO));
        UserEntity userEntity =  authService.createUser(UserMapper.toEntity(userRequestDTO));

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", UserMapper.toResponse(userEntity)
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        AuthResponse authResponse =  authService.verifyUser(loginRequestDTO, response);

        ResponseCookie refreshCookie = ResponseCookie.from("foodsy_refresh_token", authResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 Days
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(name = "foodsy_refresh_token") String refreshToken) {
        String accessToken = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "foodsy_refresh_token") String refreshToke, HttpServletResponse response) {
        authService.logout(refreshToke,response);
        return ResponseEntity.ok(Map.of(
                "message", "Logout successful"
        ));
    }
}
