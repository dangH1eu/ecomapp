package com.project.ecomapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "order id must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "product id must be > 0")
    private Long productId;

    @Min(value = 0, message = "price id must be > 0")
    private Float price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "number of products must be > 0")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 0, message = "total money must be > 0")
    private Float totalMoney;

    private String color;

}
