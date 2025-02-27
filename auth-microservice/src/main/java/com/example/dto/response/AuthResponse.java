package com.example.dto.response;

import com.example.enums.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private Set<Role> roles = new HashSet<>();

}