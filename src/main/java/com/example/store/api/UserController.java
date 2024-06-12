package com.example.store.api;

import com.example.store.config.TokenProvider;
import com.example.store.dto.request.RegisterRequest;
import com.example.store.dto.request.UserLoginRequest;
import com.example.store.dto.response.RegisterResponse;
import com.example.store.exception.InvalidCredentialsException;
import com.example.store.exception.NotFoundException;
import com.example.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("users")
public class UserController {

    // private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    public UserController(UserService userService, AuthenticationManager authenticationManager,
            TokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request) {
        // return
        // ResponseEntity.status(HttpStatus.OK).body(userService.checkLoginUser(request));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            return ResponseEntity.ok(tokenProvider.generateToken(authentication));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    
    // ====== Register, Read and Delete ADD 2024/10/06 PhucHT START ======//
    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            RegisterResponse response = userService.saveUser(request);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            // Xử lý ngoại lệ nếu tên người dùng đã tồn tại
            return ResponseEntity.badRequest().body("Username already exists.");
        }
    }

    @GetMapping("read")
    public ResponseEntity<?> getUserList() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserList());
    }

    @GetMapping("read/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete successful");
    }

    // ====== Register, Read and Delete ADD 2024/10/06 PhucHT END ======//
}
