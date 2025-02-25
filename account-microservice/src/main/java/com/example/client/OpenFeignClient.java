package com.example.client;

import com.example.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "http://localhost:8082")
public interface OpenFeignClient {

    @PostMapping("/api/v1/auth/validate")
    ValidateTokenResponse validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}