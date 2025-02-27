package com.example.service.impl;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RefreshTokenRequest;
import com.example.dto.response.AuthResponse;
import com.example.entity.RefreshToken;
import com.example.entity.User;
import com.example.enums.ErrorCode;
import com.example.enums.Role;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.RefreshTokenException;
import com.example.security.JwtUtils;
import com.example.service.RefreshTokenService;
import com.example.service.SecurityService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        User user = userService.getUserByEmail(request.getEmail());
        if (user.getIsLocked()) {
            throw new InvalidCredentialsException(ErrorCode.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);
        }

        return generateTokens(user.getId(), user.getRoles());
    }

    private AuthResponse generateTokens(UUID id, Set<Role> roles) {
        String accessToken = jwtUtils.generateToken(id, tokenExpiration);
        String refreshToken = refreshTokenService.createRefreshToken(id);
        Long expiresIn = jwtUtils.getExpirationDateFromToken(accessToken);
        return new AuthResponse(accessToken, expiresIn, refreshToken, roles);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByRefreshToken(requestRefreshToken)
                .map(refreshTokenService::checkRefreshToken)
                .map(RefreshToken::getUserId)
                .map(userId -> {
                    User user = userService.getUserById(userId);
                    return generateTokens(user.getId(), user.getRoles());
                }).orElseThrow(() -> new RefreshTokenException(ErrorCode.TOKEN_EXPIRED));
    }

}