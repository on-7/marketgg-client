package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.service.PostService;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CsPostController.class)
class CsPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    private ObjectMapper mapper;

    private static final String DEFAULT_POST = "/customer-services";
    private static final String OTO_CODE = "702";

    private PostResponseForDetail responseForDetail;
    private PostResponse response;
    private PostRequest request;

    @BeforeEach
    void setUp() {
        responseForDetail = new PostResponseForDetail();
        response = new PostResponse();
        request = new PostRequest("701", "hi", "hello", "환불");
    }

    @ParameterizedTest
    @ValueSource(strings = {"701", "702", "703"})
    @DisplayName("인덱스 조회")
    void testIndex(String categoryCode) throws Exception {
        given(postService.retrievePostList(anyString(), anyInt())).willReturn(List.of(response));

        String type = "";

        switch (categoryCode) {
            case "701":
                type = "notices";
                break;
            case "702":
                type = "oto-inquiries";
                break;
            case "703":
                type = "faqs";
                break;
        }

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}", categoryCode)
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/" + type + "/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("인덱스 조회 (1:1 문의, 페이지 끝 X)")
    void testIndexIsPageEnd() throws Exception {
        given(postService.retrievePostList(anyString(), anyInt())).willReturn(List.of(response, response, response
                , response, response, response, response, response, response, response, response, response));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}", OTO_CODE)
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/oto-inquiries/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성하기 준비")
    void testDoCreatePost() throws Exception {
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/categories/" + OTO_CODE + "/create"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/oto-inquiries/create-form")).andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("reasons")).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성하기")
    void testCreatePost() throws Exception {
        willDoNothing().given(postService).createPost(any(PostRequest.class));

        this.mockMvc.perform(post(DEFAULT_POST + "/categories/" + OTO_CODE + "/create")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(mapper.writeValueAsString(request)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_POST + "/oto-inquiries?page=0"));
    }

    @Test
    @DisplayName("게시글 단건조회")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(responseForDetail);

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}/{postNo}", "703", 1L))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/faqs/detail"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("response")).isNotNull();
    }

    @Test
    @DisplayName("게시글 단건조회 (1:1 문의)")
    void testRetrievePostForOto() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(responseForDetail);

        MvcResult mvcResult =
                this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}/{postNo}", OTO_CODE, 1L))
                            .andExpect(status().isOk())
                            .andExpect(view().name("board/oto-inquiries/detail"))
                            .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("response")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 별 검색")
    void testSearchForCategory() throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class))).willReturn(List.of(response));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}/search", "701")
                                                           .param("keyword", "hi")
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/notices/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 별 검색 (카테고리 X)")
    void testSearchForCategoryCheckNotFoundType() throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class))).willReturn(
                List.of(response));

        this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}/search", "710")
                                     .param("keyword", "hi")
                                     .param("page", "0"))
                    .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException())
                                                           .getClass().isAssignableFrom(NotFoundException.class)))
                    .andReturn();
    }

    @Test
    @DisplayName("카테고리 별 검색 (FAQ)")
    void testSearchForCategoryForFaq() throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class))).willReturn(List.of(response));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_POST + "/categories/{categoryCode}/search", "703")
                                                           .param("keyword", "hi")
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("board/faqs/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong(), anyString());

        this.mockMvc.perform(delete(DEFAULT_POST + "/categories/"+  OTO_CODE + "/{postNo}/delete", 1L)
                                     .param("page", "0"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_POST + "/categories/702?page=0"));
    }

}
