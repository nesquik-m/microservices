package com.example.service;

import com.example.dto.request.LoginRequest;
import com.example.dto.request.RefreshTokenRequest;
import com.example.dto.response.AuthResponse;

public interface SecurityService {

    AuthResponse authenticate(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

}