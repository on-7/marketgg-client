package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.service.SearchProductService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SearchProductController.class)
class SearchProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchProductService searchProductService;

    private SearchProductResponse searchProductResponse;

    @BeforeEach
    void setUp() {
        searchProductResponse = new SearchProductResponse(1L, "z", "zz",
                                                          "zzz", "hello",
                                                          1000L, 50L,"hello");
    }

    @Test
    @DisplayName("카테고리 목록에서 검색")
    void searchForCategory() throws Exception {
        given(searchProductService.searchForCategory(anyString(), any(SearchRequest.class))).willReturn(
                List.of(searchProductResponse));

        MvcResult mvcResult =
                mockMvc.perform(get("/shop/v1/products/categories/{categoryCode}/search", "11")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name("search/search-list"))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("response")).isNotNull();
    }

    @Test
    @DisplayName("전체 목록에서 검색")
    void searchForKeyword() throws Exception {
        given(searchProductService.searchForKeyword(any(SearchRequest.class))).willReturn(
                List.of(searchProductResponse));

        MvcResult mvcResult =
                mockMvc.perform(get("/shop/v1/products/search")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name("search/search-list"))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("response")).isNotNull();
    }
}