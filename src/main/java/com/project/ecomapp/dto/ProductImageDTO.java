package com.project.ecomapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "product id must be > 0")
    private Long productId;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "image name")
    private String imageUrl;

}
