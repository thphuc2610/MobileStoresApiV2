package com.example.store.service;

import com.example.store.dao.entity.Category;
import com.example.store.dao.entity.Product;
import com.example.store.dao.entity.ProductImage;
import com.example.store.exception.NotFoundException;
import com.example.store.repository.ProductImageRepository;
import com.example.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    public ProductImageService(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void storeImage(MultipartFile file, Integer id) {
        try {
            // Load the product associated with the order
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(id));

            productImageRepository.save(ProductImage.builder()
                     .product(product)
                    .imageName(file.getOriginalFilename())
                    .imageType(file.getContentType())
                    .imageData(file.getBytes())
                    .build());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public ProductImage getFileByProductId(Integer productId){
        try {
            return productImageRepository.getByProductId(productId);
        }catch (Exception e){
            return null;
        }
    }

    @Transactional
    public void deleteProductImage(Integer productId){
        try {
            productImageRepository.deleteByProductId(productId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
