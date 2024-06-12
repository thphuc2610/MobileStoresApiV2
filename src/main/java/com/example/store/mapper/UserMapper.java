package com.example.store.mapper;

import org.springframework.stereotype.Component;

import com.example.store.dao.entity.User;
import com.example.store.dto.response.RegisterResponse;

@Component
public class UserMapper {
    public RegisterResponse mapResult(User user) {
        return RegisterResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
