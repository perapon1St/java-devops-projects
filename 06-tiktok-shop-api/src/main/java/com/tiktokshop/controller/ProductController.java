package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchRequest;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final TiktokShopService tiktokShopService;

    /**
     * Search products by keyword.
     *
     * GET /api/products/search?keyword=labubu&region=TH&count=10&cursor=0
     */
    @GetMapping("/search")
    public ResponseEntity<ProductSearchResponse> searchProducts(
            @RequestParam String keyword,
            @RequestParam String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") long cursor) {

        log.info("GET /api/products/search keyword={}, region={}", keyword, region);

        ProductSearchRequest request = ProductSearchRequest.builder()
                .keyword(keyword)
                .region(region)
                .count(count)
                .cursor(cursor)
                .build();

        return ResponseEntity.ok(tiktokShopService.searchProducts(request));
    }

    /**
     * Get product detail by product ID.
     *
     * GET /api/products/{productId}?region=TH
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(
            @PathVariable String productId,
            @RequestParam String region) {

        log.info("GET /api/products/{} region={}", productId, region);
        return ResponseEntity.ok(tiktokShopService.getProductDetail(productId, region));
    }
}
