package com.tiktokshop.controller;

import com.tiktokshop.dto.SellerProductResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final TiktokShopService tiktokShopService;

    /**
     * Get products from a specific seller.
     *
     * GET /api/sellers/{userId}/products?region=TH&count=10&cursor=0
     */
    @GetMapping("/{userId}/products")
    public ResponseEntity<SellerProductResponse> getSellerProducts(
            @PathVariable String userId,
            @RequestParam String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") long cursor) {

        log.info("GET /api/sellers/{}/products region={}", userId, region);
        return ResponseEntity.ok(tiktokShopService.getSellerProducts(userId, region, count, cursor));
    }
}
