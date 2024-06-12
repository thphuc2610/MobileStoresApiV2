package com.example.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer quantity;

    private String description;

    @NotNull
    private Integer manufacturer;

    @NotNull
    private Integer category;

    @NotNull
    private Integer condition;

    private MultipartFile image;
}
