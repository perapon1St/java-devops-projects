package com.tiktokshop.client;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.JsonNode;
import com.tiktokshop.exception.TiktokShopApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
=======
import com.tiktokshop.config.TiktokShopConfig;
import com.tiktokshop.exception.TiktokShopApiException;
import lombok.extern.slf4j.Slf4j;
>>>>>>> origin/main
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
<<<<<<< HEAD
import reactor.util.retry.Retry;

import java.time.Duration;
=======

import java.util.Map;
>>>>>>> origin/main

@Slf4j
@Component
public class TiktokShopApiClient {

    private final WebClient webClient;
<<<<<<< HEAD

    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final Duration RETRY_BACKOFF = Duration.ofSeconds(1);

    public TiktokShopApiClient(WebClient tiktokShopWebClient) {
        this.webClient = tiktokShopWebClient;
    }

    /**
     * Get products from a specific seller.
     *
     * @param userId  the seller's unique ID
     * @param region  target region (e.g., "TH", "US")
     * @param count   number of products per request
     * @param cursor  pagination cursor
     * @return raw JSON response
     */
    public JsonNode getSellerProducts(String userId, String region, int count, long cursor) {
        log.info("Fetching seller products for userId={}, region={}, count={}, cursor={}",
                userId, region, count, cursor);
=======
    private final TiktokShopConfig config;

    public TiktokShopApiClient(WebClient tiktokShopWebClient, TiktokShopConfig config) {
        this.webClient = tiktokShopWebClient;
        this.config = config;
    }

    public Mono<Map> getSellerProducts(String userId, String region, int count, int cursor) {
        log.info("Fetching products for seller: {}, region: {}", userId, region);
>>>>>>> origin/main
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/seller/products")
                        .queryParam("user_id", userId)
                        .queryParam("region", region)
                        .queryParam("count", count)
                        .queryParam("cursor", cursor)
                        .build())
                .retrieve()
<<<<<<< HEAD
                .bodyToMono(JsonNode.class)
                .retryWhen(buildRetrySpec())
                .onErrorMap(WebClientResponseException.class, this::mapApiException)
                .doOnError(e -> log.error("Error fetching seller products: {}", e.getMessage()))
                .block();
    }

    /**
     * Get detailed information about a product.
     *
     * @param productId the product ID
     * @param region    target region
     * @return raw JSON response
     */
    public JsonNode getProductDetail(String productId, String region) {
        log.info("Fetching product detail for productId={}, region={}", productId, region);
=======
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
>>>>>>> origin/main
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/detail")
                        .queryParam("product_id", productId)
                        .queryParam("region", region)
                        .build())
                .retrieve()
<<<<<<< HEAD
                .bodyToMono(JsonNode.class)
                .retryWhen(buildRetrySpec())
                .onErrorMap(WebClientResponseException.class, this::mapApiException)
                .doOnError(e -> log.error("Error fetching product detail: {}", e.getMessage()))
                .block();
    }

    /**
     * Get reviews for a product.
     *
     * @param productId the product ID
     * @param region    target region
     * @param count     number of reviews per request
     * @param cursor    pagination cursor
     * @param sortType  1 = relevance, 2 = recent
     * @return raw JSON response
     */
    public JsonNode getProductReviews(String productId, String region, int count, long cursor, int sortType) {
        log.info("Fetching reviews for productId={}, region={}, count={}, cursor={}, sortType={}",
                productId, region, count, cursor, sortType);
=======
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
>>>>>>> origin/main
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
<<<<<<< HEAD
                .bodyToMono(JsonNode.class)
                .retryWhen(buildRetrySpec())
                .onErrorMap(WebClientResponseException.class, this::mapApiException)
                .doOnError(e -> log.error("Error fetching product reviews: {}", e.getMessage()))
                .block();
    }

    /**
     * Search products by keyword.
     *
     * @param keyword search term
     * @param region  target region
     * @param count   number of results per request
     * @param cursor  pagination cursor
     * @return raw JSON response
     */
    public JsonNode searchProducts(String keyword, String region, int count, long cursor) {
        log.info("Searching products for keyword={}, region={}, count={}, cursor={}",
                keyword, region, count, cursor);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/products")
=======
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
>>>>>>> origin/main
                        .queryParam("keyword", keyword)
                        .queryParam("region", region)
                        .queryParam("count", count)
                        .queryParam("cursor", cursor)
                        .build())
                .retrieve()
<<<<<<< HEAD
                .bodyToMono(JsonNode.class)
                .retryWhen(buildRetrySpec())
                .onErrorMap(WebClientResponseException.class, this::mapApiException)
                .doOnError(e -> log.error("Error searching products: {}", e.getMessage()))
                .block();
    }

    private Retry buildRetrySpec() {
        return Retry.backoff(MAX_RETRY_ATTEMPTS, RETRY_BACKOFF)
                .filter(throwable -> !(throwable instanceof TiktokShopApiException))
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        new TiktokShopApiException(
                                "TikTok Shop API call failed after " + MAX_RETRY_ATTEMPTS + " retries",
                                HttpStatus.SERVICE_UNAVAILABLE));
    }

    private TiktokShopApiException mapApiException(WebClientResponseException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String message = "TikTok Shop API error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString();
        log.error(message);
        return new TiktokShopApiException(message, status);
=======
                .bodyToMono(Map.class)
                .timeout(config.getTimeout())
                .onErrorMap(WebClientResponseException.class, ex ->
                        new TiktokShopApiException(
                                "TikTok Shop API error: " + ex.getResponseBodyAsString(),
                                ex.getStatusCode().value(), ex))
                .onErrorMap(ex -> !(ex instanceof TiktokShopApiException),
                        ex -> new TiktokShopApiException("Failed to search products: " + ex.getMessage(), 500, ex));
>>>>>>> origin/main
    }
}
