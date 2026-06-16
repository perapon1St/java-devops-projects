package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewResponse {

    private boolean success;
    private String message;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

    @JsonProperty("total_count")
    private long totalCount;

    private List<Review> reviews;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {

        @JsonProperty("review_id")
        private String reviewId;

        @JsonProperty("user_name")
        private String userName;

        private double rating;
        private String comment;

        @JsonProperty("created_at")
        private String createdAt;

        private List<String> images;

        @JsonProperty("helpful_count")
        private long helpfulCount;

        @JsonProperty("sku_name")
        private String skuName;
    }
}
