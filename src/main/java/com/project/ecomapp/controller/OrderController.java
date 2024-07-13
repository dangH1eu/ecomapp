package com.project.ecomapp.controller;

import com.project.ecomapp.dto.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderDTO orderDTO,
            BindingResult result
    ) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("create order");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(
            @Valid @PathVariable("user_id") Long userId
    ){
        try{
            return ResponseEntity.ok("retrieve order from user id");

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OrderDTO orderDTO
    ){
        return ResponseEntity.ok("update order");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @Valid @PathVariable Long id
    ){
        return ResponseEntity.ok("delete order success");
    }



}
