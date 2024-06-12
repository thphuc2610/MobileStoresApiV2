package com.example.store.dto.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
