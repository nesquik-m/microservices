package com.example.security;

import com.example.enums.ErrorCode;
import com.example.exception.GlobalExceptionAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.error("Access denied error: {}", accessDeniedException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        GlobalExceptionAdvice.ErrorResponse.Error errorDetails = new GlobalExceptionAdvice.ErrorResponse.Error(
                ErrorCode.ACCESS_DENIED.name(), ErrorCode.ACCESS_DENIED.getMessage());
        objectMapper.writeValue(response.getOutputStream(), new GlobalExceptionAdvice.ErrorResponse(errorDetails));
    }

}