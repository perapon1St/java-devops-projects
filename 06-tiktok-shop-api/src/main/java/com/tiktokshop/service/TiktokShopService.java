package com.tiktokshop.service;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.JsonNode;
import com.tiktokshop.client.TiktokShopApiClient;
import com.tiktokshop.dto.*;
import com.tiktokshop.exception.ProductNotFoundException;
import com.tiktokshop.exception.TiktokShopApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
=======
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
>>>>>>> origin/main

@Slf4j
@Service
@RequiredArgsConstructor
public class TiktokShopService {

    private final TiktokShopApiClient apiClient;
<<<<<<< HEAD

    /**
     * Search products by keyword with pagination support.
     */
    public ProductSearchResponse searchProducts(ProductSearchRequest request) {
        log.info("Searching products: keyword={}, region={}", request.getKeyword(), request.getRegion());

        JsonNode raw = apiClient.searchProducts(
                request.getKeyword(),
                request.getRegion(),
                request.getCount(),
                request.getCursor());

        return mapToProductSearchResponse(raw);
    }

    /**
     * Get detailed information about a product.
     */
    public ProductDetailResponse getProductDetail(String productId, String region) {
        log.info("Getting product detail: productId={}, region={}", productId, region);

        JsonNode raw = apiClient.getProductDetail(productId, region);

        if (raw == null || raw.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }

        return mapToProductDetailResponse(raw, productId);
    }

    /**
     * Get product reviews with pagination and sort support.
     */
    public ProductReviewResponse getProductReviews(
            String productId, String region, int count, long cursor, int sortType) {
        log.info("Getting product reviews: productId={}, region={}", productId, region);

        JsonNode raw = apiClient.getProductReviews(productId, region, count, cursor, sortType);
        return mapToProductReviewResponse(raw, productId);
    }

    /**
     * Get seller's products with pagination support.
     */
    public SellerProductResponse getSellerProducts(String userId, String region, int count, long cursor) {
        log.info("Getting seller products: userId={}, region={}", userId, region);

        JsonNode raw = apiClient.getSellerProducts(userId, region, count, cursor);
        return mapToSellerProductResponse(raw, userId);
    }

    private ProductSearchResponse mapToProductSearchResponse(JsonNode raw) {
        if (raw == null) {
            return ProductSearchResponse.builder()
                    .success(false)
                    .message("No response from API")
                    .products(new ArrayList<>())
                    .build();
        }

        List<ProductSearchResponse.ProductInfo> products = new ArrayList<>();
        JsonNode items = raw.path("data");
        if (items.isArray()) {
            for (JsonNode item : items) {
                products.add(mapToProductInfo(item));
            }
        }

        return ProductSearchResponse.builder()
                .success(true)
                .nextCursor(raw.path("next_cursor").asLong(0))
                .hasMore(raw.path("has_more").asBoolean(false))
                .products(products)
                .build();
    }

    private ProductSearchResponse.ProductInfo mapToProductInfo(JsonNode item) {
        return ProductSearchResponse.ProductInfo.builder()
                .productId(item.path("product_id").asText())
                .title(item.path("title").asText())
                .coverUrl(item.path("cover_url").asText())
                .price(item.path("price").asText())
                .currency(item.path("currency").asText())
                .soldCount(item.path("sold_count").asLong(0))
                .discount(item.path("discount").asDouble(0))
                .rating(item.path("rating").asDouble(0))
                .reviewCount(item.path("review_count").asLong(0))
                .sellerName(item.path("seller_name").asText())
                .sellerId(item.path("seller_id").asText())
                .sellerTiktokId(item.path("seller_tiktok_id").asText())
                .build();
    }

    private ProductDetailResponse mapToProductDetailResponse(JsonNode raw, String productId) {
        JsonNode data = raw.path("data");
        if (data.isMissingNode() || data.isNull()) {
            throw new ProductNotFoundException(productId);
        }

        JsonNode sellerNode = data.path("seller");
        ProductDetailResponse.SellerInfo seller = ProductDetailResponse.SellerInfo.builder()
                .sellerId(sellerNode.path("seller_id").asText())
                .sellerName(sellerNode.path("seller_name").asText())
                .sellerTiktokId(sellerNode.path("seller_tiktok_id").asText())
                .shopName(sellerNode.path("shop_name").asText())
                .build();

        List<ProductDetailResponse.Specification> specs = new ArrayList<>();
        JsonNode specsNode = data.path("specifications");
        if (specsNode.isArray()) {
            for (JsonNode spec : specsNode) {
                specs.add(ProductDetailResponse.Specification.builder()
                        .name(spec.path("name").asText())
                        .value(spec.path("value").asText())
                        .build());
            }
        }

        List<ProductDetailResponse.Sku> skus = new ArrayList<>();
        JsonNode skusNode = data.path("skus");
        if (skusNode.isArray()) {
            for (JsonNode sku : skusNode) {
                skus.add(ProductDetailResponse.Sku.builder()
                        .skuId(sku.path("sku_id").asText())
                        .name(sku.path("name").asText())
                        .price(sku.path("price").asText())
                        .stock(sku.path("stock").asInt(0))
                        .build());
            }
        }

        List<String> images = new ArrayList<>();
        JsonNode imagesNode = data.path("images");
        if (imagesNode.isArray()) {
            for (JsonNode img : imagesNode) {
                images.add(img.asText());
            }
        }

        return ProductDetailResponse.builder()
                .success(true)
                .productId(data.path("product_id").asText(productId))
                .title(data.path("title").asText())
                .description(data.path("description").asText())
                .coverUrl(data.path("cover_url").asText())
                .images(images)
                .price(data.path("price").asText())
                .originalPrice(data.path("original_price").asText())
                .currency(data.path("currency").asText())
                .discount(data.path("discount").asDouble(0))
                .rating(data.path("rating").asDouble(0))
                .reviewCount(data.path("review_count").asLong(0))
                .soldCount(data.path("sold_count").asLong(0))
                .seller(seller)
                .specifications(specs)
                .skus(skus)
                .build();
    }

    private ProductReviewResponse mapToProductReviewResponse(JsonNode raw, String productId) {
        if (raw == null) {
            return ProductReviewResponse.builder()
                    .success(false)
                    .productId(productId)
                    .reviews(new ArrayList<>())
                    .build();
        }

        List<ProductReviewResponse.Review> reviews = new ArrayList<>();
        JsonNode items = raw.path("data");
        if (items.isArray()) {
            for (JsonNode item : items) {
                List<String> reviewImages = new ArrayList<>();
                JsonNode imgNode = item.path("images");
                if (imgNode.isArray()) {
                    for (JsonNode img : imgNode) {
                        reviewImages.add(img.asText());
                    }
                }
                reviews.add(ProductReviewResponse.Review.builder()
                        .reviewId(item.path("review_id").asText())
                        .userName(item.path("user_name").asText())
                        .rating(item.path("rating").asDouble(0))
                        .comment(item.path("comment").asText())
                        .createdAt(item.path("created_at").asText())
                        .images(reviewImages)
                        .helpfulCount(item.path("helpful_count").asLong(0))
                        .skuName(item.path("sku_name").asText())
                        .build());
            }
        }

        return ProductReviewResponse.builder()
                .success(true)
                .productId(productId)
                .nextCursor(raw.path("next_cursor").asLong(0))
                .hasMore(raw.path("has_more").asBoolean(false))
                .totalCount(raw.path("total_count").asLong(0))
                .reviews(reviews)
                .build();
    }

    private SellerProductResponse mapToSellerProductResponse(JsonNode raw, String userId) {
        if (raw == null) {
            return SellerProductResponse.builder()
                    .success(false)
                    .sellerId(userId)
                    .products(new ArrayList<>())
                    .build();
        }

        List<ProductSearchResponse.ProductInfo> products = new ArrayList<>();
        JsonNode items = raw.path("data");
        if (items.isArray()) {
            for (JsonNode item : items) {
                products.add(mapToProductInfo(item));
            }
        }

        return SellerProductResponse.builder()
                .success(true)
                .sellerId(userId)
                .nextCursor(raw.path("next_cursor").asLong(0))
                .hasMore(raw.path("has_more").asBoolean(false))
                .totalCount(raw.path("total_count").asLong(0))
                .products(products)
                .build();
=======
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
>>>>>>> origin/main
    }
}
