package com.example.store.config;

import com.example.store.dao.entity.User;
import com.example.store.repository.CategoryRepository;
import com.example.store.repository.ManufacturerRepository;
import com.example.store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, CategoryRepository categoryRepository,
                      ManufacturerRepository manufacturerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Load initial data into the database
        if (userRepository.findByUsername("admin").isPresent()) {
            return;
        }

        userRepository.save(
                User.builder()
                        .name("admin")
                        .username("admin")
                        .password(passwordEncoder.encode("Abc@12345"))
                        .role("ADMIN")
                                .build());
    }
}