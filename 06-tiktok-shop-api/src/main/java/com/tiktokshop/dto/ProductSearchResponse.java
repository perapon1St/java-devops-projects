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
=======
public class ProductSearchResponse {

    private List<ProductItem> products;
    private long nextCursor;
    private boolean hasMore;
    private int total;

    @Data
    public static class ProductItem {
>>>>>>> origin/main

        @JsonProperty("product_id")
        private String productId;

        private String title;

<<<<<<< HEAD
        @JsonProperty("cover_url")
        private String coverUrl;

        private String price;
        private String currency;
=======
        @JsonProperty("cover_image")
        private String coverImage;

        private PriceInfo price;
>>>>>>> origin/main

        @JsonProperty("sold_count")
        private long soldCount;

<<<<<<< HEAD
        private double discount;
=======
>>>>>>> origin/main
        private double rating;

        @JsonProperty("review_count")
        private long reviewCount;

<<<<<<< HEAD
        @JsonProperty("seller_name")
        private String sellerName;
=======
        private SellerInfo seller;
    }

    @Data
    public static class PriceInfo {

        private String currency;

        @JsonProperty("original_price")
        private double originalPrice;

        @JsonProperty("sale_price")
        private double salePrice;

        @JsonProperty("discount_percent")
        private int discountPercent;
    }

    @Data
    public static class SellerInfo {
>>>>>>> origin/main

        @JsonProperty("seller_id")
        private String sellerId;

<<<<<<< HEAD
=======
        @JsonProperty("seller_name")
        private String sellerName;

>>>>>>> origin/main
        @JsonProperty("seller_tiktok_id")
        private String sellerTiktokId;
    }
}
