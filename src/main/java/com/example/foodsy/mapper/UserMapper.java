package com.example.foodsy.mapper;

import com.example.foodsy.dto.UserRequestDTO;
import com.example.foodsy.dto.UserResponseDTO;
import com.example.foodsy.entity.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(UserRequestDTO userRequestDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequestDTO.getUsername());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setPhoneNumber(userRequestDTO.getPhoneNumber());
        userEntity.setAddress(userRequestDTO.getAddress());
        return userEntity;
    }

    public static UserResponseDTO toResponse(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userEntity.getId());
        userResponseDTO.setUsername(userEntity.getUsername());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setPassword(userEntity.getPassword());
        userResponseDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userResponseDTO.setAddress(userEntity.getAddress());
        userResponseDTO.setCreatedAt(userEntity.getCreatedAt());
        userResponseDTO.setUpdatedAt(userEntity.getUpdatedAt());
        return userResponseDTO;
    }
}
