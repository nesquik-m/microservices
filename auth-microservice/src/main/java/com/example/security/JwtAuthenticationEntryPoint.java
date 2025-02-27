package com.example.security;

import com.example.enums.ErrorCode;
import com.example.exception.GlobalExceptionAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        GlobalExceptionAdvice.ErrorResponse.Error errorDetails = new GlobalExceptionAdvice.ErrorResponse.Error(
                ErrorCode.UNAUTHORIZED.name(), ErrorCode.UNAUTHORIZED.getMessage());
        objectMapper.writeValue(response.getOutputStream(), new GlobalExceptionAdvice.ErrorResponse(errorDetails));
    }

}