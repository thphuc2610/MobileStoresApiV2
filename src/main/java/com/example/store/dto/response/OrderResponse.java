package com.example.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.example.store.constant.OrderStatus;
import com.example.store.constant.PaymentMethod;
import com.example.store.dao.entity.OrderDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer id;
    private LocalDateTime orderDate;
    private Long total;
    // ====== Payment ADD 2024/06/11 PhucHT START ======//
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    // ====== Payment ADD 2024/06/11 PhucHT END ======//
}
