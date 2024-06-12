package com.example.store.service;

import com.example.store.dao.custom.JwtGenerator;
import com.example.store.dao.entity.User;
import com.example.store.dto.request.RegisterRequest;
import com.example.store.dto.request.UserLoginRequest;
import com.example.store.dto.response.RegisterResponse;
import com.example.store.dto.response.UserLoginResponse;
import com.example.store.exception.NotFoundException;
import com.example.store.mapper.UserMapper;
import com.example.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtGenerator jwtGenerator;
    @Autowired
    PasswordEncoder passwordEncoder;

    public UserLoginResponse checkLoginUser(UserLoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) {
            throw new NullPointerException();
        }
        return new UserLoginResponse(user.getName(), jwtGenerator.returnToken(user));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ====== Register, Read and Delete ADD 2024/10/06 PhucHT START ======//
    public RegisterResponse saveUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new NotFoundException("Username already exists .");
        }

        request.setRole("MEMBER");

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        
        user.setRole(request.getRole());

        userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setPassword(user.getPassword());
        response.setRole(user.getRole());

        return response;

    }

    public List<RegisterResponse> getUserList() {
        try {
            List<User> userList = userRepository.findAll();
            List<RegisterResponse> responses = new ArrayList<>();
            userList.forEach(item -> {
                responses.add(userMapper.mapResult(item));
            });

            return responses;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Optional<User> getUserById(Integer id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);

            return userOptional;

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void deleteUserById(Integer id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new NotFoundException("");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ====== Register, Read and Delete ADD 2024/10/06 PhucHT END ======//
}
