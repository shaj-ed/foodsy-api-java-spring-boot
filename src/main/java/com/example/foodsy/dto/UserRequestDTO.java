package com.example.foodsy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
