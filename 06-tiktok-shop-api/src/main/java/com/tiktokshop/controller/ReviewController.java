package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductReviewResponse;
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
public class ReviewController {

    private final TiktokShopService tiktokShopService;

<<<<<<< HEAD
    /**
     * Get reviews for a product.
     *
     * GET /api/products/{productId}/reviews?region=TH&count=10&cursor=0&sortType=1
     */
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<ProductReviewResponse> getProductReviews(
            @PathVariable String productId,
            @RequestParam String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") long cursor,
            @RequestParam(defaultValue = "1") int sortType) {

        log.info("GET /api/products/{}/reviews region={}", productId, region);
        return ResponseEntity.ok(
                tiktokShopService.getProductReviews(productId, region, count, cursor, sortType));
=======
    @GetMapping("/{id}/reviews")
    public ResponseEntity<ProductReviewResponse> getProductReviews(
            @PathVariable String id,
            @RequestParam(defaultValue = "TH") String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") int cursor,
            @RequestParam(defaultValue = "1") int sortType) {

        ProductReviewResponse response = tiktokShopService.getProductReviews(id, region, count, cursor, sortType);
        return ResponseEntity.ok(response);
>>>>>>> origin/main
    }
}
