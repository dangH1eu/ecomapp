package com.project.ecomapp.repository;

import com.project.ecomapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // find list order by user id
    List<Order> findByUserId(Long userId);


}
