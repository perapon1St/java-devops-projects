package com.tiktokshop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiktokshop.client.TiktokShopApiClient;
import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductReviewResponse;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.dto.SellerProductResponse;
import com.tiktokshop.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
class TiktokShopServiceTest {

    @Mock
    private TiktokShopApiClient apiClient;

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
        assertThat(response.isHasMore()).isTrue();
        assertThat(response.getNextCursor()).isEqualTo(10L);
    }

    @Test
    void searchProducts_emptyData_returnsEmptyList() {
        when(apiClient.searchProducts("nothing", "TH", 10, 0)).thenReturn(Mono.just(Collections.emptyMap()));

        ProductSearchResponse response = service.searchProducts("nothing", "TH", 10, 0);

        assertThat(response).isNotNull();
        assertThat(response.getProducts()).isEmpty();
    }

    @Test
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

        assertThatThrownBy(() -> service.getProductDetail("missing", "TH"))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("missing");
    }

    @Test
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
    }
}
