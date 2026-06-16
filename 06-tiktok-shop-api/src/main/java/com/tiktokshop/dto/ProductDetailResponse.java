package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponse {

    @JsonProperty("product_id")
    private String productId;

    private String title;
    private String description;

    @JsonProperty("cover_image")
    private String coverImage;

    private List<String> images;

    private ProductSearchResponse.PriceInfo price;

    @JsonProperty("sold_count")
    private long soldCount;

    private double rating;

    @JsonProperty("review_count")
    private long reviewCount;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    private ProductSearchResponse.SellerInfo seller;

    private List<Specification> specifications;

    @Data
    public static class Specification {
        private String name;
        private String value;
    }
}
