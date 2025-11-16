package com.example.foodsy.service.impl;

import com.example.foodsy.dto.AuthResponse;
import com.example.foodsy.dto.LoginRequestDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.exception.DuplicateResourceException;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.AuthService;
import com.example.foodsy.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if(userRepository.existsByEmail(userEntity.getEmail())) {
            throw new DuplicateResourceException("Email already exist");
        }

        if(userRepository.existsByUsername(userEntity.getUsername())) {
            throw new DuplicateResourceException("Username already exist");
        }

        userEntity.setPassword(encoder().encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public AuthResponse verifyUser(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()));

        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = userRepository.findByUsername(userDetails.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return new AuthResponse(token, user.getUsername(), "Successful");
        } else {
            return new AuthResponse("Validation failed");
        }
    }
}
