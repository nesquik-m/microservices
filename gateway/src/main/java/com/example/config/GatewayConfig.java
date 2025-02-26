package com.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GatewayConfig {

    private final CorsParamsConfig corsParamsConfig;

    @Bean
    public RouteLocator routes(org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-microservice",
                        r -> r.path("/api/v1/auth/**")
                                .uri("lb://auth-microservice"))
                .route("account-microservice",
                        r -> r.path("/api/v1/account/**")
                                .uri("lb://account-microservice"))
                .build();
    }

    @Bean
    public GlobalFilter loggingFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Request: {} {} {}", request.getMethod(), request.getPath(), request.getURI());
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    log.info("Response: {} {} {}", exchange.getResponse().getStatusCode(), request.getPath(), request.getURI())));
        };
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(corsParamsConfig.getOrigins());
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(corsParamsConfig.getHeaders());
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

}