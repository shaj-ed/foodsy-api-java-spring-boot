package com.example.foodsy.service;

import com.example.foodsy.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity updateUser(Long id, UserEntity userEntity);
    UserEntity getUserById(Long id);
    Page<UserEntity> getUsers(Pageable pageable);
    void deleteUserById(Long id);
}
