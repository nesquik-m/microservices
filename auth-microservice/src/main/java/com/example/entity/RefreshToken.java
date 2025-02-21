package com.example.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.UUID;

@RedisHash("user_refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RefreshToken {

    @Id
    @Indexed
    private Long id;

    @Indexed
    private UUID userId;

    @Indexed
    private String token;

    @Indexed
    private Instant expiryDate;

}