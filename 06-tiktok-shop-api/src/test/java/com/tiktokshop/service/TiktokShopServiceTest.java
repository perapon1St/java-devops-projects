package com.tiktokshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tiktokshop.client.TiktokShopApiClient;
import com.tiktokshop.dto.*;
=======
import com.tiktokshop.client.TiktokShopApiClient;
import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductReviewResponse;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.dto.SellerProductResponse;
>>>>>>> origin/main
import com.tiktokshop.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
<<<<<<< HEAD
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
=======
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
>>>>>>> origin/main

@ExtendWith(MockitoExtension.class)
class TiktokShopServiceTest {

    @Mock
    private TiktokShopApiClient apiClient;

<<<<<<< HEAD
    @InjectMocks
    private TiktokShopService service;

    private final ObjectMapper mapper = new ObjectMapper();

    private ObjectNode buildProductNode(String productId, String title) {
        ObjectNode node = mapper.createObjectNode();
        node.put("product_id", productId);
        node.put("title", title);
        node.put("cover_url", "https://example.com/img.jpg");
        node.put("price", "99.99");
        node.put("currency", "THB");
        node.put("sold_count", 100);
        node.put("discount", 10.0);
        node.put("rating", 4.5);
        node.put("review_count", 50);
        node.put("seller_name", "Test Seller");
        node.put("seller_id", "seller123");
        node.put("seller_tiktok_id", "tiktok_seller");
        return node;
    }

    @BeforeEach
    void setUp() {
        // No shared setup needed; each test configures its own mocks
    }

    // --- searchProducts ---

    @Test
    void searchProducts_returnsResults() {
        ObjectNode raw = mapper.createObjectNode();
        raw.put("next_cursor", 10L);
        raw.put("has_more", true);
        ArrayNode data = raw.putArray("data");
        data.add(buildProductNode("p1", "Labubu Figure"));

        when(apiClient.searchProducts("labubu", "TH", 10, 0)).thenReturn(raw);

        ProductSearchRequest request = ProductSearchRequest.builder()
                .keyword("labubu")
                .region("TH")
                .count(10)
                .cursor(0)
                .build();

        ProductSearchResponse response = service.searchProducts(request);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getProducts().get(0).getProductId()).isEqualTo("p1");
        assertThat(response.getProducts().get(0).getTitle()).isEqualTo("Labubu Figure");
=======
    private TiktokShopService service;

    @BeforeEach
    void setUp() {
        service = new TiktokShopService(apiClient, new ObjectMapper());
    }

    @Test
    void searchProducts_returnsResponse() {
        Map<String, Object> item = new HashMap<>();
        item.put("product_id", "p1");
        item.put("title", "Test Product");

        Map<String, Object> data = new HashMap<>();
        data.put("products", List.of(item));
        data.put("has_more", true);
        data.put("next_cursor", 10);
        data.put("total", 1);

        Map<String, Object> raw = new HashMap<>();
        raw.put("data", data);

        when(apiClient.searchProducts("test", "TH", 10, 0)).thenReturn(Mono.just(raw));

        ProductSearchResponse response = service.searchProducts("test", "TH", 10, 0);

        assertThat(response).isNotNull();
        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getProducts().get(0).getProductId()).isEqualTo("p1");
>>>>>>> origin/main
        assertThat(response.isHasMore()).isTrue();
        assertThat(response.getNextCursor()).isEqualTo(10L);
    }

    @Test
    void searchProducts_emptyData_returnsEmptyList() {
<<<<<<< HEAD
        ObjectNode raw = mapper.createObjectNode();
        raw.put("next_cursor", 0L);
        raw.put("has_more", false);
        raw.putArray("data");

        when(apiClient.searchProducts("nothing", "TH", 10, 0)).thenReturn(raw);

        ProductSearchRequest request = ProductSearchRequest.builder()
                .keyword("nothing")
                .region("TH")
                .build();

        ProductSearchResponse response = service.searchProducts(request);

        assertThat(response.isSuccess()).isTrue();
=======
        when(apiClient.searchProducts("nothing", "TH", 10, 0)).thenReturn(Mono.just(Collections.emptyMap()));

        ProductSearchResponse response = service.searchProducts("nothing", "TH", 10, 0);

        assertThat(response).isNotNull();
>>>>>>> origin/main
        assertThat(response.getProducts()).isEmpty();
    }

    @Test
<<<<<<< HEAD
    void searchProducts_nullResponse_returnsFailure() {
        when(apiClient.searchProducts("x", "TH", 10, 0)).thenReturn(null);

        ProductSearchRequest request = ProductSearchRequest.builder()
                .keyword("x")
                .region("TH")
                .build();

        ProductSearchResponse response = service.searchProducts(request);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getProducts()).isEmpty();
    }

    // --- getProductDetail ---

    @Test
    void getProductDetail_returnsDetail() {
        ObjectNode raw = mapper.createObjectNode();
        ObjectNode data = raw.putObject("data");
        data.put("product_id", "p1");
        data.put("title", "Test Product");
        data.put("description", "A great product");
        data.put("cover_url", "https://example.com/img.jpg");
        data.put("price", "99.99");
        data.put("original_price", "129.99");
        data.put("currency", "THB");
        data.put("discount", 10.0);
        data.put("rating", 4.5);
        data.put("review_count", 50);
        data.put("sold_count", 200);
        data.putArray("images").add("https://example.com/img1.jpg");
        ObjectNode seller = data.putObject("seller");
        seller.put("seller_id", "s1");
        seller.put("seller_name", "Best Seller");
        seller.put("seller_tiktok_id", "tiktok_s1");
        seller.put("shop_name", "Best Shop");
        data.putArray("specifications");
        data.putArray("skus");

        when(apiClient.getProductDetail("p1", "TH")).thenReturn(raw);

        ProductDetailResponse response = service.getProductDetail("p1", "TH");

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getProductId()).isEqualTo("p1");
        assertThat(response.getTitle()).isEqualTo("Test Product");
        assertThat(response.getSeller().getSellerName()).isEqualTo("Best Seller");
        assertThat(response.getImages()).hasSize(1);
    }

    @Test
    void getProductDetail_nullResponse_throwsProductNotFoundException() {
        when(apiClient.getProductDetail("missing", "TH")).thenReturn(null);
=======
    void getProductDetail_returnsResponse() {
        Map<String, Object> data = new HashMap<>();
        data.put("product_id", "p123");
        data.put("title", "Detail Product");

        Map<String, Object> raw = new HashMap<>();
        raw.put("data", data);

        when(apiClient.getProductDetail("p123", "TH")).thenReturn(Mono.just(raw));

        ProductDetailResponse response = service.getProductDetail("p123", "TH");

        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo("p123");
        assertThat(response.getTitle()).isEqualTo("Detail Product");
    }

    @Test
    void getProductDetail_emptyResponse_throwsProductNotFoundException() {
        when(apiClient.getProductDetail("missing", "TH")).thenReturn(Mono.just(Collections.emptyMap()));
>>>>>>> origin/main

        assertThatThrownBy(() -> service.getProductDetail("missing", "TH"))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("missing");
    }

    @Test
<<<<<<< HEAD
    void getProductDetail_missingDataNode_throwsProductNotFoundException() {
        ObjectNode raw = mapper.createObjectNode();
        // no "data" key
        when(apiClient.getProductDetail("p99", "TH")).thenReturn(raw);

        assertThatThrownBy(() -> service.getProductDetail("p99", "TH"))
                .isInstanceOf(ProductNotFoundException.class);
    }

    // --- getProductReviews ---

    @Test
    void getProductReviews_returnsReviews() {
        ObjectNode raw = mapper.createObjectNode();
        raw.put("next_cursor", 5L);
        raw.put("has_more", false);
        raw.put("total_count", 2L);
        ArrayNode data = raw.putArray("data");
        ObjectNode review = data.addObject();
        review.put("review_id", "r1");
        review.put("user_name", "Alice");
        review.put("rating", 5.0);
        review.put("comment", "Excellent!");
        review.put("created_at", "2024-01-01");
        review.put("helpful_count", 3);
        review.put("sku_name", "Blue / M");
        review.putArray("images");

        when(apiClient.getProductReviews("p1", "TH", 10, 0, 1)).thenReturn(raw);

        ProductReviewResponse response = service.getProductReviews("p1", "TH", 10, 0, 1);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getReviews()).hasSize(1);
        assertThat(response.getReviews().get(0).getReviewId()).isEqualTo("r1");
        assertThat(response.getReviews().get(0).getUserName()).isEqualTo("Alice");
        assertThat(response.getTotalCount()).isEqualTo(2L);
    }

    @Test
    void getProductReviews_nullResponse_returnsFailure() {
        when(apiClient.getProductReviews("p1", "TH", 10, 0, 1)).thenReturn(null);

        ProductReviewResponse response = service.getProductReviews("p1", "TH", 10, 0, 1);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getReviews()).isEmpty();
    }

    // --- getSellerProducts ---

    @Test
    void getSellerProducts_returnsProducts() {
        ObjectNode raw = mapper.createObjectNode();
        raw.put("next_cursor", 10L);
        raw.put("has_more", true);
        raw.put("total_count", 100L);
        ArrayNode data = raw.putArray("data");
        data.add(buildProductNode("p2", "Seller Product"));

        when(apiClient.getSellerProducts("seller1", "TH", 10, 0)).thenReturn(raw);

        SellerProductResponse response = service.getSellerProducts("seller1", "TH", 10, 0);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getSellerId()).isEqualTo("seller1");
        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getProducts().get(0).getTitle()).isEqualTo("Seller Product");
        assertThat(response.getTotalCount()).isEqualTo(100L);
    }

    @Test
    void getSellerProducts_nullResponse_returnsFailure() {
        when(apiClient.getSellerProducts("seller1", "TH", 10, 0)).thenReturn(null);

        SellerProductResponse response = service.getSellerProducts("seller1", "TH", 10, 0);

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getProducts()).isEmpty();
=======
    void getProductReviews_returnsResponse() {
        Map<String, Object> review = new HashMap<>();
        review.put("review_id", "r1");
        review.put("user_name", "Alice");
        review.put("rating", 5.0);
        review.put("content", "Great!");

        Map<String, Object> data = new HashMap<>();
        data.put("reviews", List.of(review));
        data.put("has_more", false);
        data.put("next_cursor", 0);

        Map<String, Object> raw = new HashMap<>();
        raw.put("data", data);

        when(apiClient.getProductReviews("p1", "TH", 10, 0, 1)).thenReturn(Mono.just(raw));

        ProductReviewResponse response = service.getProductReviews("p1", "TH", 10, 0, 1);

        assertThat(response).isNotNull();
        assertThat(response.getReviews()).hasSize(1);
        assertThat(response.getReviews().get(0).getReviewId()).isEqualTo("r1");
        assertThat(response.isHasMore()).isFalse();
    }

    @Test
    void getSellerProducts_returnsResponse() {
        Map<String, Object> product = new HashMap<>();
        product.put("product_id", "sp1");
        product.put("title", "Seller Product");

        Map<String, Object> data = new HashMap<>();
        data.put("products", List.of(product));
        data.put("has_more", false);
        data.put("next_cursor", 0);
        data.put("total", 1);

        Map<String, Object> raw = new HashMap<>();
        raw.put("data", data);

        when(apiClient.getSellerProducts("user1", "TH", 10, 0)).thenReturn(Mono.just(raw));

        SellerProductResponse response = service.getSellerProducts("user1", "TH", 10, 0);

        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo("user1");
        assertThat(response.getProducts()).hasSize(1);
        assertThat(response.getProducts().get(0).getProductId()).isEqualTo("sp1");
>>>>>>> origin/main
    }
}
