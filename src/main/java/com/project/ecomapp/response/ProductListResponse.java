package com.project.ecomapp.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;

}
