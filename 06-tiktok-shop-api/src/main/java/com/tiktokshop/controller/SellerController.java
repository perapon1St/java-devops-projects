package com.tiktokshop.controller;

import com.tiktokshop.dto.SellerProductResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final TiktokShopService tiktokShopService;

    @GetMapping("/{userId}/products")
    public ResponseEntity<SellerProductResponse> getSellerProducts(
            @PathVariable String userId,
            @RequestParam(defaultValue = "TH") String region,
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(defaultValue = "0") int cursor) {

        SellerProductResponse response = tiktokShopService.getSellerProducts(userId, region, count, cursor);
        return ResponseEntity.ok(response);
    }
}
