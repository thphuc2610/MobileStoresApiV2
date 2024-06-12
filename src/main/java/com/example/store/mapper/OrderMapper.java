package com.example.store.mapper;

import com.example.store.dao.entity.Order;
import com.example.store.dto.request.OrderRequest;
import com.example.store.dto.response.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public static OrderResponse toDTO(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                // ====== Payment ADD 2024/06/11 PhucHT START ======//
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(order.getOrderStatus())
                // ====== Payment ADD 2024/06/11 PhucHT END ======//
                .build();
    }

    public static Order toEntity(OrderRequest dto) {
        return Order.builder()
                .total(dto.getTotal())
                .build();
    }
}
