package com.example.store.api;

import com.example.store.dto.request.CategoryRequest;
import com.example.store.dto.request.ProductRequest;
import com.example.store.service.CategoryService;
import com.example.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<?> addCategory(
            @ModelAttribute("name") String name) throws Exception {
                return ResponseEntity.status(HttpStatus.OK).body(
                        categoryService.addCategory(CategoryRequest.builder()
                                        .name(name)
                                        .build())
                                        );
    }

    @GetMapping
    public ResponseEntity<?> getCategoryList(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getListCategory());
    }
}
