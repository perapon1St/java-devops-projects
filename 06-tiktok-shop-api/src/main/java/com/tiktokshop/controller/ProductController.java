package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchRequest;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final TiktokShopService tiktokShopService;

    @GetMapping("/search")
    public ResponseEntity<ProductSearchResponse> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "TH") String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") int cursor) {

        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("keyword must not be empty");
        }
        ProductSearchResponse response = tiktokShopService.searchProducts(keyword, region, count, cursor);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(
            @PathVariable String id,
            @RequestParam(defaultValue = "TH") String region) {

        ProductDetailResponse response = tiktokShopService.getProductDetail(id, region);
        return ResponseEntity.ok(response);
    }
}
