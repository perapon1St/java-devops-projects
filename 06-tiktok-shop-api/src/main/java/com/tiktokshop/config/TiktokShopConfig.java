package com.tiktokshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
public class TiktokShopConfig {

    @Value("${tiktokshop.api.base-url}")
    private String baseUrl;

    @Value("${tiktokshop.api.rapidapi-key}")
    private String rapidApiKey;

    @Value("${tiktokshop.api.rapidapi-host}")
    private String rapidApiHost;

    @Value("${tiktokshop.api.timeout-seconds:10}")
    private int timeoutSeconds;

    @Bean
    public WebClient tiktokShopWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                .defaultHeader("x-rapidapi-key", rapidApiKey)
                .defaultHeader("x-rapidapi-host", rapidApiHost)
                .build();
    }

    public Duration getTimeout() {
        return Duration.ofSeconds(timeoutSeconds);
    }
}
