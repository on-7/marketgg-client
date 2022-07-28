package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.repository.SearchRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultSearchService;
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
class SearchRequestBodyServiceTest {

    @InjectMocks
    private DefaultSearchService searchProductService;

    @Mock
    private SearchRepository searchRepository;

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
    void testSearchForCategory() throws Exception {
        given(searchRepository.searchProductForCategory(anyString(),
                                                        any(SearchRequest.class),
                                                        any())).willReturn(
                List.of(searchProductResponse));

        searchProductService.searchProductForCategory("11", searchRequest, null);

        then(searchRepository).should()
                              .searchProductForCategory(anyString(),
                                                               any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("전체 목록 검색")
    void testSearchForKeyword() throws Exception {
        given(searchRepository.searchProductWithKeyword(
                any(SearchRequest.class), any())).willReturn(List.of(searchProductResponse));

        searchProductService.searchProductForKeyword(searchRequest, null);

        then(searchRepository).should()
                              .searchProductWithKeyword(any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("카테고리 목록 내 가격별 정렬 검색")
    void testSearchForCategorySortPrice() throws Exception {
        given(searchRepository.searchProductForCategory(anyString(),
                                                        any(SearchRequest.class),
                                                        anyString())).willReturn(
                List.of(searchProductResponse));

        searchProductService.searchProductForCategory("11", searchRequest, "desc");

        then(searchRepository).should()
                              .searchProductForCategory(anyString(),
                                                               any(SearchRequest.class),
                                                               anyString());
    }

    @Test
    @DisplayName("전체 목록 내 가격별 정렬 검색")
    void testSearchForKeywordSortPrice() throws Exception {
        given(searchRepository.searchProductWithKeyword(any(SearchRequest.class),
                                                        anyString())).willReturn(
                List.of(searchProductResponse));

        searchProductService.searchProductForKeyword(searchRequest, "desc");

        then(searchRepository).should()
                              .searchProductWithKeyword(any(SearchRequest.class),
                                                               anyString());
    }

}
