package com.tiktokshop.controller;

<<<<<<< HEAD
import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchRequest;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.exception.GlobalExceptionHandler;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchResponse;
>>>>>>> origin/main
import com.tiktokshop.exception.ProductNotFoundException;
import com.tiktokshop.service.TiktokShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
<<<<<<< HEAD
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
=======
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

>>>>>>> origin/main
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

<<<<<<< HEAD
@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
=======
@WebMvcTest(controllers = {ProductController.class, ReviewController.class})
>>>>>>> origin/main
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TiktokShopService tiktokShopService;

    @Test
    void searchProducts_returnsOk() throws Exception {
<<<<<<< HEAD
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
=======
        ProductSearchResponse.ProductItem item = new ProductSearchResponse.ProductItem();
        item.setProductId("p1");
        item.setTitle("Labubu");

        ProductSearchResponse response = new ProductSearchResponse();
        response.setProducts(List.of(item));
        response.setHasMore(false);
        response.setNextCursor(0L);
        response.setTotal(1);

        when(tiktokShopService.searchProducts("labubu", "TH", 10, 0)).thenReturn(response);
>>>>>>> origin/main

        mockMvc.perform(get("/api/products/search")
                        .param("keyword", "labubu")
                        .param("region", "TH")
<<<<<<< HEAD
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.products[0].product_id").value("p1"))
                .andExpect(jsonPath("$.products[0].title").value("Labubu Figure"));
=======
                        .param("count", "10")
                        .param("cursor", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].product_id").value("p1"))
                .andExpect(jsonPath("$.products[0].title").value("Labubu"))
                .andExpect(jsonPath("$.total").value(1));
>>>>>>> origin/main
    }

    @Test
    void searchProducts_missingKeyword_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/products/search")
<<<<<<< HEAD
                        .param("region", "TH")
=======
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchProducts_blankKeyword_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("keyword", "   ")
>>>>>>> origin/main
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductDetail_returnsOk() throws Exception {
<<<<<<< HEAD
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
=======
        ProductDetailResponse response = new ProductDetailResponse();
        response.setProductId("p1");
        response.setTitle("Test Product");

        when(tiktokShopService.getProductDetail("p1", "TH")).thenReturn(response);
>>>>>>> origin/main

        mockMvc.perform(get("/api/products/p1")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
<<<<<<< HEAD
                .andExpect(jsonPath("$.success").value(true))
=======
>>>>>>> origin/main
                .andExpect(jsonPath("$.product_id").value("p1"))
                .andExpect(jsonPath("$.title").value("Test Product"));
    }

    @Test
    void getProductDetail_notFound_returns404() throws Exception {
<<<<<<< HEAD
        when(tiktokShopService.getProductDetail(eq("missing"), eq("TH")))
                .thenThrow(new ProductNotFoundException("missing"));

        mockMvc.perform(get("/api/products/missing")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("PRODUCT_NOT_FOUND"));
=======
        when(tiktokShopService.getProductDetail("notexist", "TH"))
                .thenThrow(new ProductNotFoundException("notexist"));

        mockMvc.perform(get("/api/products/notexist")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id: notexist"));
>>>>>>> origin/main
    }
}
