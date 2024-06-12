package com.example.store.repository;

import com.example.store.dao.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    ProductImage getByProductId(Integer productId);
    void deleteByProductId(Integer productId);
}
