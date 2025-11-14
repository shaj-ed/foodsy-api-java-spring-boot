package com.example.foodsy.service.impl;

import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;

    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if(userRepository.existsByEmail(userEntity.getEmail())) {
            throw new RuntimeException("Email already exist");
        }

        userEntity.setPassword(encoder().encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
}
