package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchProductRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultSearchProductService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SearchProductServiceTest {

    @InjectMocks
    private DefaultSearchProductService searchProductService;

    @Mock
    private SearchProductRepository searchProductRepository;

    private static SearchRequest searchRequest;
    private static SearchProductResponse searchProductResponse;

    @BeforeAll
    static void beforeAll() {
        searchRequest = new SearchRequest("hello", PageRequest.of(0, 5));
        searchProductResponse = new SearchProductResponse();
        ReflectionTestUtils.setField(searchProductResponse, "productName", "hello");
    }

    @Test
    @DisplayName("카테고리 목록 내 검색")
    void searchForCategory() throws Exception {
        given(searchProductRepository.searchProductForCategory(anyString(), any(SearchRequest.class))).willReturn(List.of(searchProductResponse));

        searchProductService.searchForCategory("11", searchRequest);

        then(searchProductRepository).should().searchProductForCategory(anyString(), any(SearchRequest.class));
    }

    @Test
    @DisplayName("전체 목록 검색")
    void searchForKeyword() throws Exception {
        given(searchProductRepository.searchProductWithKeyword(any(SearchRequest.class))).willReturn(List.of(searchProductResponse));

        searchProductService.searchForKeyword(searchRequest);

        then(searchProductRepository).should().searchProductWithKeyword(any(SearchRequest.class));
    }

}