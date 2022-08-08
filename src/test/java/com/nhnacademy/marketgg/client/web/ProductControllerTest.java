package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    private static final String DEFAULT_PRODUCT = "/products";

    private SearchProductResponse response;

    @BeforeEach
    void setUp() {
        response = new SearchProductResponse();
    }

    @Test
    @DisplayName("전체 목록에서 상품 검색")
    void testSearchProductList() throws Exception {
        given(productService.searchProductList(anyString(), anyInt())).willReturn(List.of(response));

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/search")
                                     .param("keyword", "안녕")
                                     .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/index"));

        then(productService).should(times(1)).searchProductList(anyString(), anyInt());
    }

    @Test
    @DisplayName("카테고리 목록에서 상품 검색")
    void testSearchProductListByCategory() throws Exception {
        given(productService.searchProductListByCategory(anyString(), anyString(), anyInt())).willReturn(List.of(response));

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/categories/{categoryId}/search", "100")
                                     .param("keyword", "안녕")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("products/index"));

        then(productService).should(times(1)).searchProductListByCategory(anyString(), anyString(), anyInt());
    }

    @Test
    @DisplayName("카테고리 목록 내에서 가격 옵션 별 검색")
    void testSearchProductListByPrice() throws Exception {
        given(productService.searchProductListByPrice(anyString(), anyString(), anyString(), anyInt())).willReturn(List.of(response));

        this.mockMvc.perform(get(DEFAULT_PRODUCT + "/categories/{categoryId}/price/{option}/search", "100", "asc")
                                     .param("keyword", "안녕")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("products/index"));

        then(productService).should(times(1)).searchProductListByPrice(anyString(), anyString(), anyString(), anyInt());
    }

}
