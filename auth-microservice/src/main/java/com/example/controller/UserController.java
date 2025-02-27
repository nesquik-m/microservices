package com.example.controller;

import com.example.annotation.LogAspect;
import com.example.aop.LogType;
import com.example.dto.request.LoginRequest;
import com.example.dto.request.RefreshTokenRequest;
import com.example.dto.request.RegistrationRequest;
import com.example.dto.response.AuthResponse;
import com.example.dto.response.RegistrationResponse;
import com.example.dto.response.ValidateTokenResponse;
import com.example.security.JwtUtils;
import com.example.service.SecurityService;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final SecurityService securityService;

    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @LogAspect(type = LogType.CONTROLLER)
    public RegistrationResponse register(@RequestBody @Valid RegistrationRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    @LogAspect(type = LogType.CONTROLLER)
    public AuthResponse authenticate(@RequestBody LoginRequest request) {
        return securityService.authenticate(request);
    }

    @PostMapping("/refresh")
    @LogAspect(type = LogType.CONTROLLER)
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return securityService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/validate")
    @LogAspect(type = LogType.CONTROLLER)
    public ValidateTokenResponse validateToken(@RequestHeader("Authorization") String token) {
        return jwtUtils.validateToken(token);
    }

}