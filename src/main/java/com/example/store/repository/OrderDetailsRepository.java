package com.example.store.repository;

import com.example.store.dao.entity.Order;
import com.example.store.dao.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrder(Order order);

    void deleteByOrder(Order order);
}
