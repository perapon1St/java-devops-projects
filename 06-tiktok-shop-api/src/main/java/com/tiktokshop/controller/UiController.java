package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductSearchRequest;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.service.TiktokShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UiController {

    private final TiktokShopService tiktokShopService;

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "TH") String region,
            @RequestParam(defaultValue = "10") int count,
            Model model) {

        model.addAttribute("keyword", keyword != null ? keyword : "");
        model.addAttribute("region", region);
        model.addAttribute("count", count);

        if (keyword != null && !keyword.isBlank()) {
            try {
                ProductSearchRequest request = ProductSearchRequest.builder()
                        .keyword(keyword)
                        .region(region)
                        .count(count)
                        .cursor(0)
                        .build();
                ProductSearchResponse response = tiktokShopService.searchProducts(request);
                model.addAttribute("searchResult", response);
                log.info("Dashboard search: keyword={}, region={}, found={} products",
                        keyword, region, response.getProducts() != null ? response.getProducts().size() : 0);
            } catch (Exception e) {
                log.error("Dashboard search error: {}", e.getMessage());
                model.addAttribute("errorMessage", "Failed to fetch data: " + e.getMessage());
            }
        }

        return "dashboard";
    }
}
