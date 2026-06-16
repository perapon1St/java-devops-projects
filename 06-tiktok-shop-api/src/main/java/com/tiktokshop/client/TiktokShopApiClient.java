package com.tiktokshop.client;

import com.tiktokshop.config.TiktokShopConfig;
import com.tiktokshop.exception.TiktokShopApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class TiktokShopApiClient {

    private final WebClient webClient;
    private final TiktokShopConfig config;

    public TiktokShopApiClient(WebClient tiktokShopWebClient, TiktokShopConfig config) {
        this.webClient = tiktokShopWebClient;
        this.config = config;
    }

    public Mono<Map> getSellerProducts(String userId, String region, int count, int cursor) {
        log.info("Fetching products for seller: {}, region: {}", userId, region);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/seller/products")
                        .queryParam("user_id", userId)
                        .queryParam("region", region)
                        .queryParam("count", count)
                        .queryParam("cursor", cursor)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(config.getTimeout())
                .onErrorMap(WebClientResponseException.class, ex ->
                        new TiktokShopApiException(
                                "TikTok Shop API error: " + ex.getResponseBodyAsString(),
                                ex.getStatusCode().value(), ex))
                .onErrorMap(ex -> !(ex instanceof TiktokShopApiException),
                        ex -> new TiktokShopApiException("Failed to fetch seller products: " + ex.getMessage(), 500, ex));
    }

    public Mono<Map> getProductDetail(String productId, String region) {
        log.info("Fetching product detail for id: {}, region: {}", productId, region);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/detail")
                        .queryParam("product_id", productId)
                        .queryParam("region", region)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(config.getTimeout())
                .onErrorMap(WebClientResponseException.class, ex ->
                        new TiktokShopApiException(
                                "TikTok Shop API error: " + ex.getResponseBodyAsString(),
                                ex.getStatusCode().value(), ex))
                .onErrorMap(ex -> !(ex instanceof TiktokShopApiException),
                        ex -> new TiktokShopApiException("Failed to fetch product detail: " + ex.getMessage(), 500, ex));
    }

    public Mono<Map> getProductReviews(String productId, String region, int count, int cursor, int sortType) {
        log.info("Fetching reviews for product: {}, region: {}", productId, region);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/reviews")
                        .queryParam("product_id", productId)
                        .queryParam("region", region)
                        .queryParam("count", count)
                        .queryParam("cursor", cursor)
                        .queryParam("sort_type", sortType)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(config.getTimeout())
                .onErrorMap(WebClientResponseException.class, ex ->
                        new TiktokShopApiException(
                                "TikTok Shop API error: " + ex.getResponseBodyAsString(),
                                ex.getStatusCode().value(), ex))
                .onErrorMap(ex -> !(ex instanceof TiktokShopApiException),
                        ex -> new TiktokShopApiException("Failed to fetch product reviews: " + ex.getMessage(), 500, ex));
    }

    public Mono<Map> searchProducts(String keyword, String region, int count, int cursor) {
        log.info("Searching products with keyword: {}, region: {}", keyword, region);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/search")
                        .queryParam("keyword", keyword)
                        .queryParam("region", region)
                        .queryParam("count", count)
                        .queryParam("cursor", cursor)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .timeout(config.getTimeout())
                .onErrorMap(WebClientResponseException.class, ex ->
                        new TiktokShopApiException(
                                "TikTok Shop API error: " + ex.getResponseBodyAsString(),
                                ex.getStatusCode().value(), ex))
                .onErrorMap(ex -> !(ex instanceof TiktokShopApiException),
                        ex -> new TiktokShopApiException("Failed to search products: " + ex.getMessage(), 500, ex));
    }
}
