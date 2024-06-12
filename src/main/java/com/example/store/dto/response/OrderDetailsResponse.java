package com.example.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponse {
    private Long id;
    private Integer productId;
    private Integer orderId;
    private Integer quantity;
    private Integer price;
}
