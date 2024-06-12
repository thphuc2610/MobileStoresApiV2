package com.example.store.repository;

import com.example.store.dao.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // ====== Pagination ADD 2024/10/06 PhucTH START ======//
    @Query("SELECT p FROM Product p")
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByProductNameContainingKeywordIgnoreCase(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.unitPrice BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByUnitPriceBetween(@Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) LIKE LOWER(CONCAT('%', :category, '%'))")
    Page<Product> findByCategoryNameContainingKeywordIgnoreCase(@Param("category") String category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.manufacturer.name) LIKE LOWER(CONCAT('%', :manufacturer, '%'))")
    Page<Product> findByManufacturerNameContainingKeywordIgnoreCase(@Param("manufacturer") String manufacturer,
            Pageable pageable);
    // ====== Pagination ADD 2024/10/06 PhucTH END ======//
}
