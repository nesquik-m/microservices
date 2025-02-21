package com.example.dto.response;

import com.example.enums.Role;
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
    private List<Role> roles = new ArrayList<>();

}