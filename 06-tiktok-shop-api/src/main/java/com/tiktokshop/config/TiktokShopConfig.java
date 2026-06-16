package com.tiktokshop.config;

<<<<<<< HEAD
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
=======
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
>>>>>>> origin/main

@Configuration
public class TiktokShopConfig {

<<<<<<< HEAD
    @Value("${tiktokshop.api.base-url:https://tiktok-shop-api.p.rapidapi.com}")
    private String baseUrl;

    @Value("${tiktokshop.api.rapidapi-key:}")
    private String rapidApiKey;

    @Value("${tiktokshop.api.rapidapi-host:tiktok-shop-api.p.rapidapi.com}")
    private String rapidApiHost;

    @Value("${tiktokshop.api.connect-timeout-ms:5000}")
    private int connectTimeoutMs;

    @Value("${tiktokshop.api.read-timeout-seconds:10}")
    private int readTimeoutSeconds;

    @Bean
    public WebClient tiktokShopWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofSeconds(readTimeoutSeconds))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(readTimeoutSeconds, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(readTimeoutSeconds, TimeUnit.SECONDS)));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-rapidapi-key", rapidApiKey)
                .defaultHeader("x-rapidapi-host", rapidApiHost)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
=======
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
>>>>>>> origin/main
}
