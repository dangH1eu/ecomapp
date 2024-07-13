package com.project.ecomapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CategoryDTO {
    @NotEmpty(message = "Category cannot be empty")
    private String name;
}
