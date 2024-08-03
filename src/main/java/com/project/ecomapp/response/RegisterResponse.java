package com.project.ecomapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ecomapp.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("user")
    private User user;
}
