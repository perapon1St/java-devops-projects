package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
=======
import lombok.Data;
>>>>>>> origin/main

import java.util.List;

@Data
<<<<<<< HEAD
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewResponse {

    private boolean success;
    private String message;

    @JsonProperty("product_id")
    private String productId;
=======
public class ProductReviewResponse {

    private List<Review> reviews;
>>>>>>> origin/main

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

<<<<<<< HEAD
    @JsonProperty("total_count")
    private long totalCount;

    private List<Review> reviews;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
=======
    @Data
>>>>>>> origin/main
    public static class Review {

        @JsonProperty("review_id")
        private String reviewId;

        @JsonProperty("user_name")
        private String userName;

<<<<<<< HEAD
        private double rating;
        private String comment;
=======
        @JsonProperty("user_avatar")
        private String userAvatar;

        private double rating;
        private String content;
>>>>>>> origin/main

        @JsonProperty("created_at")
        private String createdAt;

        private List<String> images;

        @JsonProperty("helpful_count")
<<<<<<< HEAD
        private long helpfulCount;

        @JsonProperty("sku_name")
        private String skuName;
=======
        private int helpfulCount;
>>>>>>> origin/main
    }
}
