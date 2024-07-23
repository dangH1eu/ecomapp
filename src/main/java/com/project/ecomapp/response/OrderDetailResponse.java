package com.project.ecomapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ecomapp.model.OrderDetail;
import com.project.ecomapp.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long order_id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;
    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail){
        return OrderDetailResponse
                .builder()
                .id(orderDetail.getId())
                .order_id(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
    }


}
