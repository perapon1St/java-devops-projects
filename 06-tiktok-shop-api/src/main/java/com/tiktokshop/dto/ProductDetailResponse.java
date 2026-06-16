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
public class ProductDetailResponse {

    private boolean success;
    private String message;

=======
public class ProductDetailResponse {

>>>>>>> origin/main
    @JsonProperty("product_id")
    private String productId;

    private String title;
    private String description;

<<<<<<< HEAD
    @JsonProperty("cover_url")
    private String coverUrl;

    private List<String> images;
    private String price;

    @JsonProperty("original_price")
    private String originalPrice;

    private String currency;
    private double discount;
=======
    @JsonProperty("cover_image")
    private String coverImage;

    private List<String> images;

    private ProductSearchResponse.PriceInfo price;

    @JsonProperty("sold_count")
    private long soldCount;

>>>>>>> origin/main
    private double rating;

    @JsonProperty("review_count")
    private long reviewCount;

<<<<<<< HEAD
    @JsonProperty("sold_count")
    private long soldCount;

    private SellerInfo seller;
    private List<Specification> specifications;
    private List<Sku> skus;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SellerInfo {

        @JsonProperty("seller_id")
        private String sellerId;

        @JsonProperty("seller_name")
        private String sellerName;

        @JsonProperty("seller_tiktok_id")
        private String sellerTiktokId;

        @JsonProperty("shop_name")
        private String shopName;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
=======
    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    private ProductSearchResponse.SellerInfo seller;

    private List<Specification> specifications;

    @Data
>>>>>>> origin/main
    public static class Specification {
        private String name;
        private String value;
    }
<<<<<<< HEAD

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sku {

        @JsonProperty("sku_id")
        private String skuId;

        private String name;
        private String price;
        private int stock;
    }
=======
>>>>>>> origin/main
}
