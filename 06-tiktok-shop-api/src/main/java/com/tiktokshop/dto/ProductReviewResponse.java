package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductReviewResponse {

    private List<Review> reviews;

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

    @Data
    public static class Review {

        @JsonProperty("review_id")
        private String reviewId;

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("user_avatar")
        private String userAvatar;

        private double rating;
        private String content;

        @JsonProperty("created_at")
        private String createdAt;

        private List<String> images;

        @JsonProperty("helpful_count")
        private int helpfulCount;
    }
}
