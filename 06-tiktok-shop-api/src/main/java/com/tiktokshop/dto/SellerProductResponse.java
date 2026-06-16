package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SellerProductResponse {

    @JsonProperty("user_id")
    private String userId;

    private List<ProductSearchResponse.ProductItem> products;

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

    private int total;
}
