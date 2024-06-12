package com.example.store.dao.entity;

import com.example.store.constant.OrderStatus;
import com.example.store.constant.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    private Long total;

    // ====== Payment ADD 2024/06/11 PhucHT START ======//
    @NotNull
    @Column
    private PaymentMethod paymentMethod;

    @NotNull
    @Column
    private OrderStatus orderStatus;
    // ====== Payment ADD 2024/06/11 PhucHT END ======//

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }
}
