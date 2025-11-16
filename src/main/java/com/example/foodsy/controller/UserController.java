package com.example.foodsy.controller;

import com.example.foodsy.dto.PaginationResponseDTO;
import com.example.foodsy.dto.UserRequestDTO;
import com.example.foodsy.dto.UserResponseDTO;
import com.example.foodsy.entity.UserEntity;
import com.example.foodsy.mapper.UserMapper;
import com.example.foodsy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserEntity user = userService.updateUser(id, UserMapper.toEntity(userRequestDTO));
        return ResponseEntity.ok(Map.of(
                "message", "Successfully updated",
                "data", UserMapper.toResponse(user)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity userEntity = userService.getUserById(id);
        return ResponseEntity.ok(Map.of(
                "message", "Success",
                "data", UserMapper.toResponse(userEntity)
        ));
    }

    @GetMapping
    public ResponseEntity<?> getUsers(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userService.getUsers(pageable);

        if(usersPage.getContent().isEmpty()) {
            return ResponseEntity.ok(Map.of(
                    "message", "No users found"
            ));
        } else {
            List<UserResponseDTO> users = new ArrayList<>();
            for (UserEntity userEntity: usersPage.getContent()) {
                users.add(UserMapper.toResponse(userEntity));
            }

            PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO(
                    usersPage.getNumber(),
                    usersPage.getSize(),
                    usersPage.getTotalPages(),
                    usersPage.getTotalElements(),
                    usersPage.hasNext(),
                    usersPage.hasPrevious()
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Success",
                    "data", users,
                    "pagination", paginationResponseDTO
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(Map.of("message", "Successfully deleted"));
    }
}
