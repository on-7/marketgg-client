package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
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
class SearchServiceTest {

    @InjectMocks
    private DefaultSearchService searchService;

    @Mock
    private SearchRepository searchRepository;

    private static SearchRequest searchRequest;
    private static SearchProductResponse searchProductResponse;
    private static SearchBoardResponse searchBoardResponse;

    @BeforeAll
    static void beforeAll() {
        searchRequest = new SearchRequest("hello", PageRequest.of(0, 5));
        searchProductResponse = new SearchProductResponse();
        searchBoardResponse = new SearchBoardResponse();
        ReflectionTestUtils.setField(searchProductResponse, "productName", "hello");
        ReflectionTestUtils.setField(searchBoardResponse, "title", "hello");
    }

    @Test
    @DisplayName("상품 카테고리 목록 내 검색")
    void testSearchProductForCategory() throws Exception {
        given(searchRepository.searchProductForCategory(anyString(),
                                                        any(SearchRequest.class),
                                                        any())).willReturn(
                List.of(searchProductResponse));

        searchService.searchProductForCategory("11", searchRequest, null);

        then(searchRepository).should()
                              .searchProductForCategory(anyString(), any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("상품 전체 목록 검색")
    void testSearchProductForKeyword() throws Exception {
        given(searchRepository.searchProductWithKeyword(
                any(SearchRequest.class), any())).willReturn(List.of(searchProductResponse));

        searchService.searchProductForKeyword(searchRequest, null);

        then(searchRepository).should().searchProductWithKeyword(any(SearchRequest.class), any());
    }

    @Test
    @DisplayName("상품 카테고리 목록 내 가격별 정렬 검색")
    void testSearchProductForCategorySortPrice() throws Exception {
        given(searchRepository.searchProductForCategory(anyString(),
                                                        any(SearchRequest.class),
                                                        anyString())).willReturn(
                List.of(searchProductResponse));

        searchService.searchProductForCategory("11", searchRequest, "desc");

        then(searchRepository).should().searchProductForCategory(
                                      anyString(), any(SearchRequest.class), anyString());
    }

    @Test
    @DisplayName("상품 전체 목록 내 가격별 정렬 검색")
    void testSearchProductForKeywordSortPrice() throws Exception {
        given(searchRepository.searchProductWithKeyword(any(SearchRequest.class),
                                                        anyString())).willReturn(
                List.of(searchProductResponse));

        searchService.searchProductForKeyword(searchRequest, "desc");

        then(searchRepository).should()
                              .searchProductWithKeyword(any(SearchRequest.class), anyString());
    }

    @Test
    @DisplayName("옵션 별 검색")
    void testSearchBoardForOption() throws Exception {
        given(searchRepository.searchBoardWithOption(anyString(), any(SearchRequest.class),
                                                     anyString())).willReturn(
                List.of(searchBoardResponse));

        searchService.searchBoardForOption("공지사항", searchRequest, "categoryCode");

        then(searchRepository).should()
                              .searchBoardWithOption(anyString(), any(SearchRequest.class),
                                                     anyString());
    }

}
