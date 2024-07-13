package com.project.ecomapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    private Float price;
    @JsonProperty("number_of_products")
    private Long numberOfProducts;
    @JsonProperty("total_money")
    private Long totalMoney;
    private String color;

}
