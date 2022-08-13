package com.nhnacademy.marketgg.client.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.exception.NotFoundException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.PostService;

import java.util.List;
import java.util.Objects;

import com.nhnacademy.marketgg.client.web.admin.AdminCsPostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminCsPostController.class)
class AdminCsPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    PostService postService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    private static final String DEFAULT_ADMIN_POST = "/admin/customer-services";

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
    @DisplayName("인덱스 조회 (1:1 문의)")
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

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}", categoryCode)
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("pages/board/" + type + "/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("인덱스 조회 (1:1 문의, 페이지 끝 X)")
    void testIndexIsPageEnd() throws Exception {
        given(postService.retrievePostList(anyString(), anyInt())).willReturn(
                List.of(response, response, response, response, response, response, response, response, response,
                        response, response, response));

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}", "702")
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("pages/board/oto-inquiries/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성 준비")
    void testDoCreatePost() throws Exception {
        this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/create", "703"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("pages/board/faqs/create-form"));
    }

    @Test
    @DisplayName("게시글 생성하기 실패")
    void testCreatePostFail() throws Exception {
        this.mockMvc.perform(post(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/create", "703")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(mapper.writeValueAsString(request)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/703/create"));
    }

    @Test
    @DisplayName("게시글 생성하기 성공")
    void testCreatePostSuccess() throws Exception {
        willDoNothing().given(postService).createPost(any(PostRequest.class));

        this.mockMvc.perform(post(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/create", "703")
                                     .param("categoryCode", "703")
                                     .param("title", "hello")
                                     .param("content", "안녕하세요.")
                                     .param("reason", "환불"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/703?page=0"));

        then(postService).should(times(1)).createPost(any(PostRequest.class));
    }

    @Test
    @DisplayName("게시글 단건조회")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(responseForDetail);

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}", "703", 1L)
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("pages/board/faqs/detail"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("response")).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"701", "702", "703"})
    @DisplayName("카테고리 별 검색")
    void testSearchForCategory(String categoryCode) throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class))).willReturn(List.of(response));

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

        MvcResult mvcResult = this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/search", categoryCode)
                                                           .param("keyword", "hi")
                                                           .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("pages/board/" + type + "/index"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 별 검색 (카테고리 X)")
    void testSearchForCategoryCheckNotFoundType() throws Exception {
        given(postService.searchForCategory(anyString(), any(SearchRequest.class))).willReturn(
                List.of(response));

        this.mockMvc.perform(get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/search", "710")
                                     .param("keyword", "hi")
                                     .param("page", "0"))
                    .andExpect(result -> assertTrue(
                            Objects.requireNonNull(result.getResolvedException())
                                   .getClass().isAssignableFrom(NotFoundException.class)))
                    .andReturn();
    }

    @Test
    @DisplayName("카테고리 및 사유별 검색")
    void testSearchForReason() throws Exception {
        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString())).willReturn(
                List.of(response));

        MvcResult mvcResult =
                this.mockMvc.perform(
                            get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/options/{optionType}/search", "702",
                                 "reason")
                                    .param("keyword", "hi")
                                    .param("page", "0")
                                    .param("optionType", "reason")
                                    .param("option", "환불"))
                            .andExpect(status().isOk())
                            .andExpect(view().name("pages/board/oto-inquiries/index"))
                            .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("카테고리 및 상태별 검색")
    void testSearchForStatus() throws Exception {
        given(postService.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString())).willReturn(
                List.of(response));

        MvcResult mvcResult =
                this.mockMvc.perform(
                            get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/options/{option}/search", "702", "status")
                                    .param("keyword", "hi")
                                    .param("page", "0")
                                    .param("optionType", "status")
                                    .param("option", "종료"))
                            .andExpect(status().isOk())
                            .andExpect(view().name("pages/board/oto-inquiries/index"))
                            .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("responses")).isNotNull();
    }

    @Test
    @DisplayName("게시글 수정 준비")
    void testDoUpdatePost() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(responseForDetail);
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult = this.mockMvc.perform(
                                          get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}/update", "701", 1L)
                                                  .param("page", "0"))
                                          .andExpect(status().isOk())
                                          .andExpect(view().name("pages/board/notices/update-form"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("reasons")).isNotNull();
    }

    @Test
    @DisplayName("1:1 문의 게시글 수정 준비 시도")
    void testDoUpdatePostForOto() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(responseForDetail);
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        MvcResult mvcResult = this.mockMvc.perform(
                                          get(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}/update", "702", 1L)
                                                  .param("page", "0"))
                                          .andExpect(status().is3xxRedirection())
                                          .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/702?page=0"))
                                          .andReturn();

        assertThat(Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("reasons")).isNull();
    }

    @Test
    @DisplayName("1:1 문의 게시글 수정 실패")
    void testUpdatePostForOtoFail() throws Exception {
        this.mockMvc.perform(put(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}/update", "702", 1L)
                                     .param("page", "0")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(mapper.writeValueAsString(request)))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/702/1/update?page=0"));
    }

    @Test
    @DisplayName("1:1 문의 게시글 수정 성공")
    void testUpdatePostForOtoSuccess() throws Exception {
        willDoNothing().given(postService).updatePost(anyLong(), any(PostRequest.class), anyString());

        this.mockMvc.perform(put(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}/update", "702", 1L)
                                     .param("page", "0")
                                     .param("categoryCode", "702")
                                     .param("title", "hello")
                                     .param("content", "안녕하세요.")
                                     .param("reason", "환불"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/702?page=0"));

        then(postService).should(times(0)).updatePost(anyLong(), any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postService).updatePost(anyLong(), any(PostRequest.class), anyString());

        this.mockMvc.perform(put(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}/update", "701", 1L)
                                     .param("page", "0")
                                     .param("categoryCode", "701")
                                     .param("title", "hello")
                                     .param("content", "안녕하세요.")
                                     .param("reason", "환불"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/701?page=0"));

        then(postService).should(times(1)).updatePost(anyLong(), any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong(), anyString());

        this.mockMvc.perform(delete(DEFAULT_ADMIN_POST + "/categories/{categoryCode}/{postNo}/delete", "701", 1L)
                                     .param("page", "0"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/701?page=0"));

        then(postService).should(times(1)).deletePost(anyLong(), anyString());
    }

    @Test
    @DisplayName("게시글 상태 변경")
    void testChangeStatus() throws Exception {
        willDoNothing().given(postService).changeStatus(anyLong(), any(PostStatusUpdateRequest.class));

        this.mockMvc.perform(patch(DEFAULT_ADMIN_POST + "/categories/702/{postNo}/status", 1L)
                                     .param("page", "0")
                                     .param("status", "종료"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN_POST + "/categories/702?page=0"));

        then(postService).should(times(1)).changeStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

}
