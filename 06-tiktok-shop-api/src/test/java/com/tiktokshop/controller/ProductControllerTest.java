package com.tiktokshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiktokshop.dto.ProductDetailResponse;
import com.tiktokshop.dto.ProductSearchResponse;
import com.tiktokshop.exception.ProductNotFoundException;
import com.tiktokshop.service.TiktokShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ProductController.class, ReviewController.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TiktokShopService tiktokShopService;

    @Test
    void searchProducts_returnsOk() throws Exception {
        ProductSearchResponse.ProductItem item = new ProductSearchResponse.ProductItem();
        item.setProductId("p1");
        item.setTitle("Labubu");

        ProductSearchResponse response = new ProductSearchResponse();
        response.setProducts(List.of(item));
        response.setHasMore(false);
        response.setNextCursor(0L);
        response.setTotal(1);

        when(tiktokShopService.searchProducts("labubu", "TH", 10, 0)).thenReturn(response);

        mockMvc.perform(get("/api/products/search")
                        .param("keyword", "labubu")
                        .param("region", "TH")
                        .param("count", "10")
                        .param("cursor", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].product_id").value("p1"))
                .andExpect(jsonPath("$.products[0].title").value("Labubu"))
                .andExpect(jsonPath("$.total").value(1));
    }

    @Test
    void searchProducts_missingKeyword_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchProducts_blankKeyword_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("keyword", "   ")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductDetail_returnsOk() throws Exception {
        ProductDetailResponse response = new ProductDetailResponse();
        response.setProductId("p1");
        response.setTitle("Test Product");

        when(tiktokShopService.getProductDetail("p1", "TH")).thenReturn(response);

        mockMvc.perform(get("/api/products/p1")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value("p1"))
                .andExpect(jsonPath("$.title").value("Test Product"));
    }

    @Test
    void getProductDetail_notFound_returns404() throws Exception {
        when(tiktokShopService.getProductDetail("notexist", "TH"))
                .thenThrow(new ProductNotFoundException("notexist"));

        mockMvc.perform(get("/api/products/notexist")
                        .param("region", "TH")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id: notexist"));
    }
}
