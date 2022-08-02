package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.dto.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.service.PostService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    private ObjectMapper mapper;

    private static final String DEFAULT_POST = "/customer-services";

    private PostResponseForDetail responseForDetail;
    private PostResponseForOtoInquiry responseForOtoInquiry;
    private PostResponse response;
    private SearchBoardResponse boardResponse;
    private PostRequest request;

    @BeforeEach
    void setUp() {
        responseForDetail = new PostResponseForDetail();
        responseForOtoInquiry = new PostResponseForOtoInquiry();
        response = new PostResponse();
        boardResponse = new SearchBoardResponse();
        request = new PostRequest("701", "hi", "hello", "환불");
    }

    @Test
    @DisplayName("인덱스 조회 (1:1 문의)")
    void testIndex() throws Exception {
        given(postService.retrievesPostListForMe(anyInt(), anyString())).willReturn(List.of(response));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/{type}", "oto-inquiries"))
                                          .andExpect(view().name("board/oio-inquiries/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("인덱스 조회 (1:1문의 X)")
    void testIndexNotOto() throws Exception {
        given(postService.retrievesPostListForMe(anyInt(), anyString())).willReturn(List.of(response));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/{type}", "faqs"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/faqs/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성하기 준비")
    void testDoCreatePost() throws Exception {
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/oto-inquiries/create"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/oto-inquiries/create-form")).andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("reasons")).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성하기")
    void testCreatePost() throws Exception {
        willDoNothing().given(postService).createPost(any(PostRequest.class), anyString());

        this.mockMvc.perform(post(DEFAULT_POST + "/oto-inquiries/create")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(mapper.writeValueAsString(request)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + DEFAULT_POST + "/oto-inquiries"));
    }

    @Test
    @DisplayName("게시글 단건조회")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong(), anyString(), anyString())).willReturn(responseForDetail);

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/{type}/{boardNo}/retrieve", "faqs", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("board/faqs/detail"))
                .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("response")).isNotNull();
    }

    @Test
    @DisplayName("게시글 단건조회 (1:1 문의)")
    void testRetrievePostForOto() throws Exception {
        given(postService.retrievePostForOtoInquiry(anyLong(), anyString(), anyString())).willReturn(responseForOtoInquiry);

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/{type}/{boardNo}/retrieve", "oto-inquiries", 1L))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/oto-inquiries/detail"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("response")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 별 검색")
    void testSearchForCategory() throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class), anyString())).willReturn(List.of(boardResponse));

        MvcResult mvcResult = this.mockMvc.perform(post(DEFAULT_POST + "/search/categories/{categoryCode}", "701")
                                                           .param("keyword", "hi")
                                                           .param("page", "0")
                                                           .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/notices/index"))
                .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 별 검색 (FAQ)")
    void testSearchForCategoryForFaq() throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class), anyString())).willReturn(List.of(boardResponse));

        MvcResult mvcResult = this.mockMvc.perform(post(DEFAULT_POST + "/search/categories/{categoryCode}", "703")
                                                           .param("keyword", "hi")
                                                           .param("page", "0")
                                                           .param("size", "1"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/faqs/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 및 사유별 검색")
    void testSearchForReason() throws Exception {
        given(postService.searchForReason(anyString(), any(SearchRequest.class), anyString(), anyString())).willReturn(List.of(boardResponse));

        MvcResult mvcResult = this.mockMvc.perform(post(DEFAULT_POST + "/search/categories/{categoryCode}/reason", "702")
                                                           .param("keyword", "hi")
                                                           .param("page", "0")
                                                           .param("size", "1"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/oto-inquiries/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 및 상태별 검색")
    void testSearchForStatus() throws Exception {
        given(postService.searchForStatus(anyString(), any(SearchRequest.class), anyString(), anyString())).willReturn(List.of(boardResponse));

        MvcResult mvcResult = this.mockMvc.perform(post(DEFAULT_POST + "/search/categories/{categoryCode}/status", "702")
                                                           .param("keyword", "hi")
                                                           .param("page", "0")
                                                           .param("size", "1"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/oto-inquiries/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 수정 준비")
    void testDoUpdatePost() throws Exception {
        given(postService.retrievePost(anyLong(), anyString(), anyString())).willReturn(responseForDetail);
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/oto-inquiries/{boardNo}/update", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("board/oto-inquiries/update-form"))
                .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postService).updatePost(anyLong(), any(PostRequest.class), anyString(), anyString());

        this.mockMvc.perform(put(DEFAULT_POST + "/oto-inquiries/{boardNo}/update", 1L)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(mapper.writeValueAsString(request)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + DEFAULT_POST + "/oto-inquiries"));
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong(), anyString(), anyString());

        this.mockMvc.perform(delete(DEFAULT_POST + "oto/inquiries/{boardNo}/delete", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + DEFAULT_POST + "/oto-inquiries"));
    }

}
