package com.example.store.service;

import com.example.store.constant.ProductCondition;
import com.example.store.dao.entity.Category;
import com.example.store.dao.entity.Manufacturer;
import com.example.store.dao.entity.Product;
import com.example.store.dto.request.PaginationRequest;
import com.example.store.dto.request.ProductRequest;
import com.example.store.dto.response.PaginationResponse;
import com.example.store.dto.response.ProductResponse;
import com.example.store.exception.NotFoundException;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.CategoryRepository;
import com.example.store.repository.ManufacturerRepository;
import com.example.store.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final ManufacturerRepository manufacturerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductImageService productImageService,
            ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productImageService = productImageService;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    // ====== Pagination ADD 2024/10/06 PhucTH START ======//

    public PaginationResponse<Product> getProducts(PaginationRequest request) {
        int page = request.getPage();
        int size = request.getSize();

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> productList = productPage.getContent();

        PaginationResponse<Product> response = new PaginationResponse<>();
        response.setContent(productList);
        response.setFirst(productPage.isFirst());
        response.setLast(productPage.isLast());
        response.setTotalPages(productPage.getTotalPages());
        response.setTotalItems(productPage.getTotalElements());
        response.setSize(productPage.getSize());
        response.setPage(productPage.getNumber() + 1);

        return response;
    }


    public List<ProductResponse> searchProducts(String name, Integer minPrice, Integer maxPrice, String category, String manufacturer) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE); // Lấy tất cả kết quả

        if (name != null && !name.isEmpty()) {
            return productRepository.findByProductNameContainingKeywordIgnoreCase(name, pageable)
                    .stream()
                    .map(productMapper::mapResult)
                    .collect(Collectors.toList());
        }
    
        if (minPrice != null && maxPrice != null) {
            return productRepository.findByUnitPriceBetween(minPrice, maxPrice, pageable)
                    .stream()
                    .map(productMapper::mapResult)
                    .collect(Collectors.toList());
        }
    
        if (minPrice != null && maxPrice != null) {
            return productRepository.findByUnitPriceBetween(minPrice, maxPrice, pageable)
                    .stream()
                    .map(productMapper::mapResult)
                    .collect(Collectors.toList());
        }

        if (category != null && !category.isEmpty()) {
            return productRepository.findByCategoryNameContainingKeywordIgnoreCase(category, pageable)
                    .stream()
                    .map(productMapper::mapResult)
                    .collect(Collectors.toList());
        }
    
        if (manufacturer != null && !manufacturer.isEmpty()) {
            return productRepository.findByManufacturerNameContainingKeywordIgnoreCase(manufacturer, pageable)
                    .stream()
                    .map(productMapper::mapResult)
                    .collect(Collectors.toList());
        }

        return productRepository.findAll(pageable)
                .stream()
                .map(productMapper::mapResult)
                .collect(Collectors.toList());
    }
    // ====== Pagination ADD 2024/10/06 PhucTH END ======//

    public ProductResponse addProduct(ProductRequest request) throws FileNotFoundException {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(request.getImage().getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (imageName.contains("..")) {
                throw new FileNotFoundException();
            }

            // Load the manufacturer associated with the order
            Manufacturer manufacturer = manufacturerRepository.findById(request.getManufacturer())
                    .orElseThrow(() -> new NotFoundException(request.getManufacturer()));

            // Load the category associated with the order
            Category category = categoryRepository.findById(request.getCategory())
                    .orElseThrow(() -> new NotFoundException(request.getCategory()));

            Product newProduct = productRepository.save(Product.builder()
                    .id(request.getId())
                    .productName(request.getName())
                    .unitPrice(request.getPrice())
                    .quantity(request.getQuantity())
                    .description(request.getDescription())
                    .manufacturer(manufacturer)
                    .category(category)
                    .productCondition(ProductCondition.fromInteger(request.getCondition()))
                    .build());

            productImageService.storeImage(request.getImage(), newProduct.getId());

            return productMapper.mapResult(newProduct);
        } catch (IOException ex) {
            throw new FileNotFoundException();
        }
    }

    public List<ProductResponse> getListProduct() {
        try {
            List<Product> productList = productRepository.findAll();
            List<ProductResponse> responses = new ArrayList<>();
            productList.forEach(item -> {
                responses.add(productMapper.mapResult(item));
            });
            return responses;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public ProductResponse getProductDetail(Integer id) {
        try {
            return productMapper.mapResult(productRepository.getOne(id));
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void deleteProductById(Integer id) {
        try {
            productImageService.deleteProductImage(id);
            productRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
