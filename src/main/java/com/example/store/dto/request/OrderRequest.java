package com.example.store.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import com.example.store.constant.OrderStatus;
import com.example.store.constant.PaymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    @NotNull
    private Long total;

    // ====== Payment ADD 2024/06/11 PhucHT START ======//
    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private OrderStatus orderStatus;
    // ====== Payment ADD 2024/06/11 PhucHT END ======//

    @NotNull
    private Set<OrderDetailsRequest> details;
}
