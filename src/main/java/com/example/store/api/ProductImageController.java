package com.example.store.api;

import com.example.store.dao.entity.ProductImage;
import com.example.store.service.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("product-images")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @GetMapping("/{productId}")
    public ResponseEntity<Resource> getProductImage(@Valid @PathVariable Integer productId){

        ProductImage image = productImageService.getFileByProductId(productId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getImageName() + "\"")
                .body(new ByteArrayResource(image.getImageData()));
    }
}
