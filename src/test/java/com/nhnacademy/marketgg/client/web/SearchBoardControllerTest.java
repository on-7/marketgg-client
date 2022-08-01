package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.service.PostService;
import com.nhnacademy.marketgg.client.service.SearchService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SearchBoardController.class)
class SearchBoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchService searchService;
    @MockBean
    PostService postService;

    private SearchBoardResponse searchBoardResponse;

    private static final String DEFAULT_SEARCH = "/shop/v1/search/boards";

    @BeforeEach
    void setUp(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                                 .alwaysDo(print())
                                 .build();
        searchBoardResponse =
                new SearchBoardResponse(1L, "hello", "hi", "ho", "he", LocalDateTime.now());
    }

    @ParameterizedTest
    @CsvSource({
            "701, notices",
            "702, oto-inquiries",
            "703, faqs"
    })
    @DisplayName("카테고리 내 게시글 검색")
    void searchForCategoryForNotice(String categoryCode, String type) throws Exception {
        given(searchService.searchBoardForCategory(anyString(), any(SearchRequest.class),
                                                   anyString()))
                .willReturn(List.of(searchBoardResponse));
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult =
                mockMvc.perform(post(DEFAULT_SEARCH + "/categories/{categoryCode}", categoryCode)
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name("board/" + type + "/index"))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("responses"))
                .isNotNull();
    }

    @Test
    @DisplayName("카테고리 내 게시글 검색(없는 카테고리번호)")
    void searchForCategoryForNotFoundException() throws Exception {
        given(searchService.searchBoardForCategory(anyString(), any(SearchRequest.class),
                                                   anyString()))
                .willReturn(List.of(searchBoardResponse));
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));
        MvcResult mvcResult = this.mockMvc.perform(
                                          post(DEFAULT_SEARCH + "/categories/{categoryCode}", "705")
                                                  .param("keyword", "안녕")
                                                  .param("page", "0")
                                                  .param("size", "1"))
                                          .andExpect(status().isOk())
                                          .andReturn();
        assertThat(mvcResult.getResolvedException()).isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("사유 별 검색")
    void searchForReason() throws Exception {
        given(searchService.searchBoardForOption(anyString(), anyString(), any(SearchRequest.class),
                                                 anyString())).willReturn(
                List.of(searchBoardResponse));
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult =
                mockMvc.perform(post(DEFAULT_SEARCH + "/categories/{categoryCode}/reason", "11")
                                        .param("reason", "환불")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name("board/oto-inquiries/index"))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("responses"))
                .isNotNull();
    }

    @Test
    @DisplayName("상태 별 검색")
    void searchForStatus() throws Exception {
        given(searchService.searchBoardForOption(anyString(), anyString(),
                                                 any(SearchRequest.class),
                                                 anyString())).willReturn(
                List.of(searchBoardResponse));
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult =
                mockMvc.perform(post(DEFAULT_SEARCH + "/categories/{categoryCode}/status", "11")
                                        .param("status", "종료")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name("board/oto-inquiries/index"))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("responses"))
                .isNotNull();
    }

    @Test
    @DisplayName("상태 별 검색 (페이지 끝 X)")
    void searchForStatusNotPageEnd() throws Exception {
        given(searchService.searchBoardForOption(anyString(), anyString(),
                                                 any(SearchRequest.class),
                                                 anyString())).willReturn(
                List.of(searchBoardResponse, searchBoardResponse, searchBoardResponse,
                        searchBoardResponse,
                        searchBoardResponse, searchBoardResponse, searchBoardResponse,
                        searchBoardResponse,
                        searchBoardResponse, searchBoardResponse, searchBoardResponse));
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult =
                mockMvc.perform(post(DEFAULT_SEARCH + "/categories/{categoryCode}/status", "11")
                                        .param("status", "종료")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name("board/oto-inquiries/index"))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("responses"))
                .isNotNull();
    }

}
