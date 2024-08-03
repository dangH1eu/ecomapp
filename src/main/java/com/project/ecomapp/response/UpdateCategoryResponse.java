package com.project.ecomapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UpdateCategoryResponse {
    @JsonProperty("message")
    private String message;
}
