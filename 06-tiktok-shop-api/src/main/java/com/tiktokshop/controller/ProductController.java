package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchRequest;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

>>>>>>> origin/main
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final TiktokShopService tiktokShopService;

<<<<<<< HEAD
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
=======
    @GetMapping("/search")
    public ResponseEntity<ProductSearchResponse> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "TH") String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") int cursor) {

        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("keyword must not be empty");
        }
        ProductSearchRequest request = new ProductSearchRequest();
        request.setKeyword(keyword);
        request.setRegion(region);
        request.setCount(count);
        request.setCursor(cursor);
        ProductSearchResponse response = tiktokShopService.searchProducts(
                request.getKeyword(), request.getRegion(), request.getCount(), request.getCursor());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(
            @PathVariable String id,
            @RequestParam(defaultValue = "TH") String region) {

        ProductDetailResponse response = tiktokShopService.getProductDetail(id, region);
        return ResponseEntity.ok(response);
>>>>>>> origin/main
    }
}
