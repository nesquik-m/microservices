package com.example.service.impl;

import com.example.entity.RefreshToken;
import com.example.enums.ErrorCode;
import com.example.enums.Role;
import com.example.exception.RefreshTokenException;
import com.example.repository.RefreshTokenRepository;
import com.example.security.JwtUtils;
import com.example.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    private final JwtUtils jwtUtils;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public String createRefreshToken(UUID id) {
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUserId(id);

        String token = jwtUtils.generateToken(id, refreshTokenExpiration);
        Long expiryDate = jwtUtils.getExpirationDateFromToken(token);

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(id)
                .token(token)
                .expiryDate(Instant.now().plusSeconds(expiryDate))
                .build();

        existingRefreshToken.ifPresent(refreshTokenRepository::delete);

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken checkRefreshToken(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(ErrorCode.TOKEN_EXPIRED);
        }
        return token;
    }

}