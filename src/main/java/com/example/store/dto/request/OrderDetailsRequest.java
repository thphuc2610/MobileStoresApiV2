package com.example.store.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsRequest {
    private Integer orderId;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer unitPrice;
}
