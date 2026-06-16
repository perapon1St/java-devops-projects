package com.tiktokshop.controller;

import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchRequest;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.exception.GlobalExceptionHandler;
import com.tiktokshop.exception.ProductNotFoundException;
import com.tiktokshop.service.TiktokShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
@WithMockUser
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TiktokShopService tiktokShopService;

    @Test
    void searchProducts_returnsOk() throws Exception {
        ProductSearchResponse mockResponse = ProductSearchResponse.builder()
                .success(true)
                .hasMore(false)
                .nextCursor(0)
                .products(List.of(
                        ProductSearchResponse.ProductInfo.builder()
                                .productId("p1")
                                .title("Labubu Figure")
                                .price("99.99")
                                .currency("THB")
                                .build()))
                .build();

        when(tiktokShopService.searchProducts(any(ProductSearchRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/products/search")
                        .param("keyword", "labubu")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.products[0].product_id").value("p1"))
                .andExpect(jsonPath("$.products[0].title").value("Labubu Figure"));
    }

    @Test
    void searchProducts_missingKeyword_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductDetail_returnsOk() throws Exception {
        ProductDetailResponse mockResponse = ProductDetailResponse.builder()
                .success(true)
                .productId("p1")
                .title("Test Product")
                .price("99.99")
                .currency("THB")
                .seller(ProductDetailResponse.SellerInfo.builder()
                        .sellerId("s1")
                        .sellerName("Best Seller")
                        .build())
                .build();

        when(tiktokShopService.getProductDetail(eq("p1"), eq("TH"))).thenReturn(mockResponse);

        mockMvc.perform(get("/api/products/p1")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.product_id").value("p1"))
                .andExpect(jsonPath("$.title").value("Test Product"));
    }

    @Test
    void getProductDetail_notFound_returns404() throws Exception {
        when(tiktokShopService.getProductDetail(eq("missing"), eq("TH")))
                .thenThrow(new ProductNotFoundException("missing"));

        mockMvc.perform(get("/api/products/missing")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("PRODUCT_NOT_FOUND"));
    }
}
