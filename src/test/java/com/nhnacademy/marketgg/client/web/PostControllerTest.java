package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.CommentResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    private static final String DEFAULT = "/shop/v1/customer-services";

    private PostResponseForOtoInquiry postResponseForOtoInquiry;
    private PostResponse postResponse;
    private CommentResponse commentResponse;

    @BeforeEach
    void setUp() {
        postResponseForOtoInquiry = new PostResponseForOtoInquiry();
        postResponse = new PostResponse();
        commentResponse = new CommentResponse();
    }

    @Test
    @DisplayName("전체 목록 조회")
    void testIndex() throws Exception {
        given(postService.retrievesPosts(any(PageRequest.class), anyString())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT + "/{type}", "faqs")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/index"));
    }

    @Test
    @DisplayName("전체 목록 조회(페이지 마지막 X)")
    void testIndexPageNoEnd() throws Exception {
        given(postService.retrievesPosts(any(PageRequest.class), anyString())).willReturn(
                List.of(postResponse, postResponse, postResponse, postResponse, postResponse, postResponse,
                        postResponse, postResponse, postResponse, postResponse, postResponse));

        this.mockMvc.perform(get(DEFAULT + "/{type}", "faqs")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/index"));
    }

    @Test
    @DisplayName("게시글 등록 준비")
    void testDoCreatePost() throws Exception {
        this.mockMvc.perform(get(DEFAULT + "/{type}/create", "faqs"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/input-form"));
    }

    @Test
    @DisplayName("게시글 등록")
    void testCreatePost() throws Exception {
        willDoNothing().given(postService).createPost(any(PostRequest.class), anyString());

        this.mockMvc.perform(post(DEFAULT + "/{type}/create", "faqs"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT + "/faqs"));

        then(postService).should().createPost(any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 상세 조회")
    void testRetrievePost() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(postResponse);

        this.mockMvc.perform(get(DEFAULT + "/{type}/{boardNo}/retrieve", "faqs", 1L)
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/detail"));
    }

    @Test
    @DisplayName("1:1 문의 게시글 상세 조회")
    void testRetrievePostForOtoInquiries() throws Exception {
        ReflectionTestUtils.setField(postResponseForOtoInquiry, "commentList", List.of(commentResponse));

        given(postService.retrievePostForOtoInquiry(anyLong(), anyString(), any(PageRequest.class))).willReturn(
                postResponseForOtoInquiry);

        this.mockMvc.perform(get(DEFAULT + "/{type}/{boardNo}/retrieve", "oto-inquiries", 1L)
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/oto-inquiries/detail"));
    }

    @Test
    @DisplayName("게시글 수정 준비")
    void testDoUpdatePost() throws Exception {
        this.mockMvc.perform(get(DEFAULT + "/{type}/{boardNo}/update", "faqs", 1L))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/input-form"));
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postService).updatePost(anyLong(), any(PostRequest.class), anyString());

        this.mockMvc.perform(put(DEFAULT + "/{type}/{boardNo}/update", "faqs", 1L))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT + "/faqs"));

        then(postService).should().updatePost(anyLong(), any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong(), anyString());

        this.mockMvc.perform(delete(DEFAULT + "/{type}/{boardNo}/delete", "faqs", 1L))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT + "/faqs"));

        then(postService).should().deletePost(anyLong(), anyString());
    }

}