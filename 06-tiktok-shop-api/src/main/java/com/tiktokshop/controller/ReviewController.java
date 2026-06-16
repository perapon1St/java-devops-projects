package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductReviewResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ReviewController {

    private final TiktokShopService tiktokShopService;

    @GetMapping("/{id}/reviews")
    public ResponseEntity<ProductReviewResponse> getProductReviews(
            @PathVariable String id,
            @RequestParam(defaultValue = "TH") String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") int cursor,
            @RequestParam(defaultValue = "1") int sortType) {

        ProductReviewResponse response = tiktokShopService.getProductReviews(id, region, count, cursor, sortType);
        return ResponseEntity.ok(response);
    }
}
