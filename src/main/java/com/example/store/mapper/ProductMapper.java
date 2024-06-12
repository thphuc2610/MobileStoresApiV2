package com.example.store.mapper;

import com.example.store.dao.entity.Category;
import com.example.store.dao.entity.Manufacturer;
import com.example.store.dao.entity.Product;
import com.example.store.dto.response.ProductResponse;
import com.example.store.exception.NotFoundException;
import com.example.store.repository.CategoryRepository;
import com.example.store.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ProductMapper {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    public ProductResponse mapResult(Product product){

        String imageLink = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/product-images/")
                .path(product.getId().toString())
                .toUriString();

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new NotFoundException(product.getCategory().getId()));

        Manufacturer manufacturer = manufacturerRepository.findById(product.getManufacturer().getId())
                .orElseThrow(() -> new NotFoundException(product.getManufacturer().getId()));

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getUnitPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .manufacturer(manufacturer.getName())
                .category(category.getName())
                .condition(product.getProductCondition().toString())
                .image(imageLink)
                .build();
    }
}
