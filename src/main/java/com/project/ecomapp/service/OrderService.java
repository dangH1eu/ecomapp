package com.project.ecomapp.service;

import com.project.ecomapp.dto.OrderDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO) throws Exception;
    void deleteOrder(Long id);
    List<Order> findByUserId(Long userId);
    Page<Order> getOrdersByKeyword(String keyword, Pageable pageable);



}
