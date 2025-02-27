package com.example.service;

import com.example.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    String createRefreshToken(UUID id);

    Optional<RefreshToken> findByRefreshToken(String token);

    RefreshToken checkRefreshToken(RefreshToken token);

}
