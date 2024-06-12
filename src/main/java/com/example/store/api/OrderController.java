package com.example.store.api;

import com.example.store.constant.OrderStatus;
import com.example.store.constant.PaymentMethod;
import com.example.store.dao.entity.Order;
import com.example.store.dao.entity.OrderDetails;
import com.example.store.dao.entity.Product;
import com.example.store.dto.request.OrderRequest;
import com.example.store.dto.response.OrderResponse;
import com.example.store.exception.NotFoundException;
import com.example.store.service.OrderService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id) {
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderDTO) {
        OrderResponse createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    // ====== Payment ADD 2024/06/11 PhucHT START ======//


    @GetMapping("/search")
    public ResponseEntity<List<OrderResponse>> searchOrders(
            @RequestParam(required = false) PaymentMethod paymentMethod,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) Long minTotal,
            @RequestParam(required = false) Long maxTotal) {

        if (minTotal != null && maxTotal != null && minTotal >= maxTotal) {
            return ResponseEntity.badRequest().build();
        }

        if (paymentMethod != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.findByPaymentMethod(paymentMethod));
        } else if (orderStatus != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.findByOrderStatus(orderStatus));
        } else if (minTotal != null && maxTotal != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.findByTotal(minTotal, maxTotal));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Integer id, @RequestParam OrderStatus status) {
        OrderResponse updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful");
    }
    // ====== Payment ADD 2024/06/11 PhucHT END ======//
}
