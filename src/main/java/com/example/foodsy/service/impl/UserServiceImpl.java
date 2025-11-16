package com.example.foodsy.service.impl;

import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.exception.DuplicateResourceException;
import com.example.foodsy.exception.ResourceNotFoundException;
import com.example.foodsy.repository.UserRepository;
import com.example.foodsy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity userEntity) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(userRepository.existsByUsername(userEntity.getUsername())) {
            throw new DuplicateResourceException("Username already exist");
        }
        if(userRepository.existsByEmail(userEntity.getEmail())) {
            throw new DuplicateResourceException("Email already exist");
        }

        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setPassword(encoder().encode(userEntity.getPassword()));
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setAddress(userEntity.getAddress());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public Page<UserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.deleteById(user.getId());
    }
}
