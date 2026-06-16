package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductSearchResponse {

    private List<ProductItem> products;
    private long nextCursor;
    private boolean hasMore;
    private int total;

    @Data
    public static class ProductItem {

        @JsonProperty("product_id")
        private String productId;

        private String title;

        @JsonProperty("cover_image")
        private String coverImage;

        private PriceInfo price;

        @JsonProperty("sold_count")
        private long soldCount;

        private double rating;

        @JsonProperty("review_count")
        private long reviewCount;

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

        @JsonProperty("seller_id")
        private String sellerId;

        @JsonProperty("seller_name")
        private String sellerName;

        @JsonProperty("seller_tiktok_id")
        private String sellerTiktokId;
    }
}
