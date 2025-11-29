package com.example.foodsy.service.impl;

import com.example.foodsy.dto.AuthResponse;
import com.example.foodsy.dto.LoginRequestDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.exception.DuplicateResourceException;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.AuthService;
import com.example.foodsy.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    UserDetailsService userDetailsService;

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
    public AuthResponse verifyUser(LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()));

        if(authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = userRepository.findByUsername(userDetails.getUsername());
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            return new AuthResponse(accessToken, refreshToken, user.getUsername(), "Successful");
        } else {
            return new AuthResponse("Validation failed");
        }
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        String username = jwtUtil.extractUsername(refreshToken);
        UserDetails user = userDetailsService.loadUserByUsername(username);
        Boolean isValid = jwtUtil.validateToken(refreshToken, user);

        if(!isValid) {
            throw new RuntimeException("Token expired");
        }

        String accessToken = jwtUtil.generateAccessToken(user);

        return accessToken;
    }
}
