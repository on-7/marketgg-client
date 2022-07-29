package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.marketgg.client.dto.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.elastic.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.service.SearchService;
import java.time.LocalDateTime;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SearchBoardController.class)
class SearchBoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SearchService searchService;

    private SearchBoardResponse searchBoardResponse;

    private static final String DEFAULT_SEARCH = "/shop/v1/search/boards";
    private static final String SEARCH_RESULT = "search/board-search-list";

    @BeforeEach
    void setUp(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                                 .alwaysDo(print())
                                 .build();
        searchBoardResponse =
                new SearchBoardResponse(1L, "hello", "hi", "ho", "he", LocalDateTime.now());
    }

    @Test
    @DisplayName("카테고리 내 게시글 검색")
    void searchForCategory() throws Exception {
        given(searchService.searchBoardForCategory(anyString(), any(SearchRequest.class), anyString()))
                .willReturn(List.of(searchBoardResponse));

        MvcResult mvcResult =
                mockMvc.perform(get(DEFAULT_SEARCH + "/categories/{categoryCode}", "11")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name(SEARCH_RESULT))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("response")).isNotNull();
    }

    @Test
    @DisplayName("사유 별 검색")
    void searchForReason() throws Exception {
        given(searchService.searchBoardForOption(anyString(), anyString(),
                                                 any(SearchRequest.class), anyString())).willReturn(
                List.of(searchBoardResponse));

        MvcResult mvcResult =
                mockMvc.perform(get(DEFAULT_SEARCH + "/categories/{categoryCode}/reason", "11")
                                        .param("reason", "환불")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name(SEARCH_RESULT))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("response")).isNotNull();
    }

    @Test
    @DisplayName("상태 별 검색")
    void searchForStatus() throws Exception {
        given(searchService.searchBoardForOption(anyString(), anyString(),
                                                 any(SearchRequest.class), anyString())).willReturn(
                List.of(searchBoardResponse));

        MvcResult mvcResult =
                mockMvc.perform(get(DEFAULT_SEARCH + "/categories/{categoryCode}/status", "11")
                                        .param("status", "종료")
                                        .param("keyword", "안녕")
                                        .param("page", "0")
                                        .param("size", "1"))
                       .andExpect(status().isOk())
                       .andExpect(view().name(SEARCH_RESULT))
                       .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView())
                          .getModel()
                          .get("response")).isNotNull();
    }

}
