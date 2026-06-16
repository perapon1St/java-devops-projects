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
public class SellerProductResponse {

    private boolean success;
    private String message;

    @JsonProperty("seller_id")
    private String sellerId;
=======
public class SellerProductResponse {

    @JsonProperty("user_id")
    private String userId;

    private List<ProductSearchResponse.ProductItem> products;
>>>>>>> origin/main

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

<<<<<<< HEAD
    @JsonProperty("total_count")
    private long totalCount;

    private List<ProductSearchResponse.ProductInfo> products;
=======
    private int total;
>>>>>>> origin/main
}
