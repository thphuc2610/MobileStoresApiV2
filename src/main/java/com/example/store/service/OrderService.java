package com.example.store.service;

import com.example.store.constant.OrderStatus;
import com.example.store.constant.PaymentMethod;
import com.example.store.dao.entity.Order;
import com.example.store.dao.entity.OrderDetails;
import com.example.store.dao.entity.Product;
import com.example.store.dto.request.OrderDetailsRequest;
import com.example.store.dto.request.OrderRequest;
import com.example.store.dto.response.OrderResponse;
import com.example.store.exception.NotFoundException;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderDetailsRepository;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class OrderService {
    // DI
    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderDTO) {
        // Create Order object from OrderRequest
        Order order = Order.builder()
                .total(orderDTO.getTotal())
                // ====== Payment ADD 2024/06/11 PhucHT START ======//
                .paymentMethod(orderDTO.getPaymentMethod())
                .orderStatus(orderDTO.getOrderStatus())
                // ====== Payment ADD 2024/06/11 PhucHT END ======//
                .build();

        // Save Order to generate ID
        order = orderRepository.save(order);

        // Create OrderDetails and associate them with Order and Product
        for (OrderDetailsRequest detailsRequest : orderDTO.getDetails()) {
            // Fetch the product from the repository
            Product product = productRepository.findById(detailsRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // ====== Check stock ADD 2024/06/11 PhucHT START ======//

            // Check product stock
            if (product.getQuantity() < detailsRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for product" + product.getProductName());

            }

            // Reduce producy stock
            product.setQuantity(product.getQuantity() - detailsRequest.getQuantity());
            productRepository.save(product);
            // ====== Check stock ADD 2024/06/11 PhucHT END ======//

            // Create OrderDetails object
            OrderDetails orderDetails = OrderDetails.builder()
                    .order(order)
                    .product(product)
                    .quantity(detailsRequest.getQuantity())
                    .price(detailsRequest.getUnitPrice())
                    .build();

            // Save OrderDetails
            orderDetailsRepository.save(orderDetails);
        }

        // Create OrderResponse object
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

    // ====== Payment ADD 2024/06/11 PhucHT START ======//

    public List<OrderResponse> findByPaymentMethod(PaymentMethod paymentMethod) {
        List<Order> orders = orderRepository.findByPaymentMethod(paymentMethod);
        return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    public List<OrderResponse> findByOrderStatus(OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByOrderStatus(orderStatus);
        return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    public List<OrderResponse> findByTotal(Long minTotal, Long maxTotal) {
        List<Order> orders = orderRepository.findByTotalBetween(minTotal, maxTotal);
        return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(Integer id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        order.setOrderStatus(status);
        order = orderRepository.save(order);

        return OrderMapper.toDTO(order);
    }

    @Transactional
    public void deleteOrder(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        // Restore product stock
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findByOrder(order);
        for (OrderDetails orderDetails : orderDetailsList) {
            Product product = orderDetails.getProduct();
            product.setQuantity(product.getQuantity() + orderDetails.getQuantity());
            productRepository.save(product);
        }

        // Delete OrderDetails associated with the order
        orderDetailsRepository.deleteByOrder(order);

        // Delete the order
        orderRepository.delete(order);
    }
    // ====== Payment ADD 2024/06/11 PhucHT END ======//

}
