package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {

    private String keyword;
    private String region;

    @Builder.Default
    private int count = 10;

    @Builder.Default
    private long cursor = 0;
}
