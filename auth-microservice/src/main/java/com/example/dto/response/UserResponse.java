package com.example.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("user_name")
    private String username;

}