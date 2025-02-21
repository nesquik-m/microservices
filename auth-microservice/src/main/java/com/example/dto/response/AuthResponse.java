package com.example.dto.response;

import com.example.enums.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    private Long expiresIn;

    private String refreshToken;

    @Builder.Default
    private List<Role> roles = new ArrayList<>();

}