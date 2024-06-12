package com.example.store.repository;

import com.example.store.constant.OrderStatus;
import com.example.store.constant.PaymentMethod;
import com.example.store.dao.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.paymentMethod = :paymentMethod")
    List<Order> findByPaymentMethod(PaymentMethod paymentMethod);

    @Query("SELECT o FROM Order o WHERE o.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(OrderStatus orderStatus);

    @Query("SELECT o FROM Order o WHERE o.total BETWEEN :minTotal AND :maxTotal")
    List<Order> findByTotalBetween(Long minTotal, Long maxTotal);

}
