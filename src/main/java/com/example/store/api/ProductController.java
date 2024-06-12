package com.example.store.api;

import com.example.store.constant.PaginationConstant;
import com.example.store.dao.entity.Category;
import com.example.store.dao.entity.Manufacturer;
import com.example.store.dao.entity.Product;
import com.example.store.dto.request.PaginationRequest;
import com.example.store.dto.request.ProductRequest;
import com.example.store.dto.response.PaginationResponse;
import com.example.store.dto.response.ProductResponse;
import com.example.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
    ProductService productService;

    // ====== Pagination ADD 2024/10/06 PhucTH START ======//
    @GetMapping("paged/{page}")
    public ResponseEntity<PaginationResponse<Product>> getProductsPaged(@PathVariable int page) {
        PaginationRequest request = new PaginationRequest(page, PaginationConstant.DEFAULT_PAGE_SIZE);
        PaginationResponse<Product> response = productService.getProducts(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String manufacturer) {
        
        List<ProductResponse> products = productService.searchProducts(name, minPrice, maxPrice, category, manufacturer);
        
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
    // ====== Pagination ADD 2024/10/06 PhucTH END ======//

    @PostMapping("add")
    public ResponseEntity<?> addProduct(
            @ModelAttribute("name") String name,
            @ModelAttribute("price") Integer price,
            @ModelAttribute("quantity") Integer quantity,
            @ModelAttribute("des") String des,
            @ModelAttribute("manu") Integer manu,
            @ModelAttribute("cate") Integer cate,
            @ModelAttribute("condition") Integer condition,
            @Valid @ModelAttribute("image") MultipartFile image) throws FileNotFoundException {

        return ResponseEntity.status(HttpStatus.OK).body(
                productService.addProduct(ProductRequest.builder()
                        .name(name)
                        .price(price)
                        .quantity(quantity)
                        .description(des)
                        .manufacturer(manu)
                        .category(cate)
                        .condition(condition)
                        .image(image)
                        .build()));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@Valid @PathVariable Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete successful");
    }

    @GetMapping
    public ResponseEntity<?> getProductList() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getListProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@Valid @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductDetail(id));
    }

}
