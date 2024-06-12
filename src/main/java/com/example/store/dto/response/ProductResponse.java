package com.example.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;
    private String name;
    private Integer price;
    private Integer quantity;
    private String description;
    private String manufacturer;
    private String category;
    private String condition;
    private String image;
}
