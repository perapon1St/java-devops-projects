package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
<<<<<<< HEAD
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
=======
import lombok.Data;

@Data
public class ProductSearchRequest {

    private String keyword;
    private String region = "TH";
    private int count = 10;
    private int cursor = 0;
>>>>>>> origin/main
}
