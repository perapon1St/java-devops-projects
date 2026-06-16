package com.tiktokshop.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class TiktokShopService {

    private final TiktokShopApiClient apiClient;

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
    }
}
