package com.example.store.dao.entity;

import com.example.store.constant.ProductCondition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
	@Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "unit_price")
    private Integer unitPrice;

    @NotNull
    @Column
    private Integer quantity;

    @Column
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @NotNull
    @Column
    private ProductCondition productCondition;

    @OneToMany(mappedBy = "product")
    private Set<OrderDetails> orderDetails = new HashSet<>();
}
