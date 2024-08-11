package com.project.ecomapp.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderListResponse {
    private List<OrderResponse> orders;
    private int totalPages;
}
