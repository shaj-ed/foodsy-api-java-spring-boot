package com.example.foodsy.controller;

import com.example.foodsy.dto.UserRequestDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.mapper.UserMapper;
import com.example.foodsy.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/test")
    public ResponseEntity<?> test(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserEntity userEntity =  authService.createUser(UserMapper.toEntity(userRequestDTO));

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", UserMapper.toResponse(userEntity)
        ));
    }
}
