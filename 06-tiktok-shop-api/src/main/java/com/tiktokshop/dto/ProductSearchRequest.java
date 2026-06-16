package com.tiktokshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductSearchRequest {

    private String keyword;
    private String region = "TH";
    private int count = 10;
    private int cursor = 0;
}
