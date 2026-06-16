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
public class ProductSearchResponse {

    private boolean success;
    private String message;

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

    private List<ProductInfo> products;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductInfo {

        @JsonProperty("product_id")
        private String productId;

        private String title;

        @JsonProperty("cover_url")
        private String coverUrl;

        private String price;
        private String currency;

        @JsonProperty("sold_count")
        private long soldCount;

        private double discount;
        private double rating;

        @JsonProperty("review_count")
        private long reviewCount;

        @JsonProperty("seller_name")
        private String sellerName;

        @JsonProperty("seller_id")
        private String sellerId;

        @JsonProperty("seller_tiktok_id")
        private String sellerTiktokId;
    }
}
