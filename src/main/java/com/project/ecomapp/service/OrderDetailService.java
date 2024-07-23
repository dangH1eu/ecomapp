package com.project.ecomapp.service;

import com.project.ecomapp.dto.OrderDetailDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id) throws Exception;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws Exception;
    void deleteById(Long id);
    List<OrderDetail> findByOrderId(Long orderId);

}
