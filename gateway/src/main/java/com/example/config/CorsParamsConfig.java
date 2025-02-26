package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "app")
@Configuration
public class CorsParamsConfig {

    private List<String> origins;

    private List<String> methods;

    private List<String> headers;

}