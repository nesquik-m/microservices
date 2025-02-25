package com.example.dto.response;

import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {

    private UUID id;

    @Builder.Default
    private Set<String> roles = new HashSet<>();

}