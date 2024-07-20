package com.project.ecomapp.service;

import com.project.ecomapp.dto.OrderDetailDTO;
import com.project.ecomapp.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetail getOrderDetail(Long id);
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(Long id);
    List<OrderDetail> getOrderDetails(Long orderId);

}
