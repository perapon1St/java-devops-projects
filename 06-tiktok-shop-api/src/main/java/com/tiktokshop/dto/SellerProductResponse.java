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
public class SellerProductResponse {

    private boolean success;
    private String message;

    @JsonProperty("seller_id")
    private String sellerId;

    @JsonProperty("next_cursor")
    private long nextCursor;

    @JsonProperty("has_more")
    private boolean hasMore;

    @JsonProperty("total_count")
    private long totalCount;

    private List<ProductSearchResponse.ProductInfo> products;
}
