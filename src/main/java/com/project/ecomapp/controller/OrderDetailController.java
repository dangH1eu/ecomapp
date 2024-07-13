package com.project.ecomapp.controller;

import com.project.ecomapp.dto.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {

    @PostMapping()
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        return ResponseEntity.ok("create order detail");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id
    ){
        return ResponseEntity.ok("get order detail with id");
    }

    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("order_id") Long orderId
    ){
        return ResponseEntity.ok("get order details with id");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO

    ){
        return ResponseEntity.ok("update order detail with id");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(
            @Valid @PathVariable("id") Long id
    ){
        return ResponseEntity.noContent().build();
    }



}
