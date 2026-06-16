package com.tiktokshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiktokshop.client.TiktokShopApiClient;
import com.tiktokshop.dto.*;
import com.tiktokshop.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TiktokShopService {

    private final TiktokShopApiClient apiClient;
    private final ObjectMapper objectMapper;

    public ProductSearchResponse searchProducts(String keyword, String region, int count, int cursor) {
        log.info("Service: searching products keyword={}, region={}, count={}, cursor={}", keyword, region, count, cursor);
        Map response = apiClient.searchProducts(keyword, region, count, cursor).block();
        return mapToProductSearchResponse(response);
    }

    public ProductDetailResponse getProductDetail(String productId, String region) {
        log.info("Service: fetching detail for productId={}, region={}", productId, region);
        Map response = apiClient.getProductDetail(productId, region).block();
        if (response == null || response.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        return mapToProductDetailResponse(response, productId);
    }

    public ProductReviewResponse getProductReviews(String productId, String region, int count, int cursor, int sortType) {
        log.info("Service: fetching reviews for productId={}, region={}, sortType={}", productId, region, sortType);
        Map response = apiClient.getProductReviews(productId, region, count, cursor, sortType).block();
        return mapToProductReviewResponse(response);
    }

    public SellerProductResponse getSellerProducts(String userId, String region, int count, int cursor) {
        log.info("Service: fetching products for seller userId={}, region={}, count={}, cursor={}", userId, region, count, cursor);
        Map response = apiClient.getSellerProducts(userId, region, count, cursor).block();
        return mapToSellerProductResponse(response, userId);
    }

    @SuppressWarnings("unchecked")
    private ProductSearchResponse mapToProductSearchResponse(Map raw) {
        ProductSearchResponse response = new ProductSearchResponse();
        if (raw == null) {
            response.setProducts(Collections.emptyList());
            return response;
        }
        Object data = raw.get("data");
        Map dataMap = (data instanceof Map) ? (Map) data : raw;

        response.setNextCursor(toLong(dataMap.get("next_cursor")));
        response.setHasMore(Boolean.TRUE.equals(dataMap.get("has_more")));
        response.setTotal(toInt(dataMap.get("total")));

        List rawProducts = (List) dataMap.getOrDefault("products", Collections.emptyList());
        List<ProductSearchResponse.ProductItem> items = rawProducts.stream()
                .map(p -> objectMapper.convertValue(p, ProductSearchResponse.ProductItem.class))
                .toList();
        response.setProducts(items);
        return response;
    }

    @SuppressWarnings("unchecked")
    private ProductDetailResponse mapToProductDetailResponse(Map raw, String productId) {
        Object data = raw.get("data");
        Map dataMap = (data instanceof Map) ? (Map) data : raw;
        if (dataMap.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        return objectMapper.convertValue(dataMap, ProductDetailResponse.class);
    }

    @SuppressWarnings("unchecked")
    private ProductReviewResponse mapToProductReviewResponse(Map raw) {
        ProductReviewResponse response = new ProductReviewResponse();
        if (raw == null) {
            response.setReviews(Collections.emptyList());
            return response;
        }
        Object data = raw.get("data");
        Map dataMap = (data instanceof Map) ? (Map) data : raw;

        response.setNextCursor(toLong(dataMap.get("next_cursor")));
        response.setHasMore(Boolean.TRUE.equals(dataMap.get("has_more")));

        List rawReviews = (List) dataMap.getOrDefault("reviews", Collections.emptyList());
        List<ProductReviewResponse.Review> reviews = rawReviews.stream()
                .map(r -> objectMapper.convertValue(r, ProductReviewResponse.Review.class))
                .toList();
        response.setReviews(reviews);
        return response;
    }

    @SuppressWarnings("unchecked")
    private SellerProductResponse mapToSellerProductResponse(Map raw, String userId) {
        SellerProductResponse response = new SellerProductResponse();
        response.setUserId(userId);
        if (raw == null) {
            response.setProducts(Collections.emptyList());
            return response;
        }
        Object data = raw.get("data");
        Map dataMap = (data instanceof Map) ? (Map) data : raw;

        response.setNextCursor(toLong(dataMap.get("next_cursor")));
        response.setHasMore(Boolean.TRUE.equals(dataMap.get("has_more")));
        response.setTotal(toInt(dataMap.get("total")));

        List rawProducts = (List) dataMap.getOrDefault("products", Collections.emptyList());
        List<ProductSearchResponse.ProductItem> items = rawProducts.stream()
                .map(p -> objectMapper.convertValue(p, ProductSearchResponse.ProductItem.class))
                .toList();
        response.setProducts(items);
        return response;
    }

    private long toLong(Object value) {
        if (value == null) return 0L;
        if (value instanceof Number) return ((Number) value).longValue();
        try { return Long.parseLong(value.toString()); } catch (NumberFormatException e) { return 0L; }
    }

    private int toInt(Object value) {
        if (value == null) return 0;
        if (value instanceof Number) return ((Number) value).intValue();
        try { return Integer.parseInt(value.toString()); } catch (NumberFormatException e) { return 0; }
    }
}
