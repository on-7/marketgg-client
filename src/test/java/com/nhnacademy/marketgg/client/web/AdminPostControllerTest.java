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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
@WebMvcTest(AdminPostController.class)
class AdminPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    private static final String DEFAULT_ADMIN = "/shop/v1/admin/customer-services";

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
        given(postService.retrievesPostList(anyInt(), anyString())).willReturn(List.of());

        this.mockMvc.perform(get(DEFAULT_ADMIN + "/{type}", "faqs")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/index"));
    }

    @Test
    @DisplayName("전체 목록 조회(페이지 마지막 X)")
    void testIndexPageNoEnd() throws Exception {
        given(postService.retrievesPostList(anyInt(), anyString())).willReturn(
                List.of(postResponse, postResponse, postResponse, postResponse, postResponse, postResponse,
                        postResponse, postResponse, postResponse, postResponse, postResponse));

        this.mockMvc.perform(get(DEFAULT_ADMIN + "/{type}", "faqs")
                                     .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/index"));
    }

    @Test
    @DisplayName("게시글 등록 준비")
    void testDoCreatePost() throws Exception {
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));

        this.mockMvc.perform(get(DEFAULT_ADMIN + "/{type}/create", "faqs"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/create-form"));
    }

    @Test
    @DisplayName("게시글 등록")
    void testCreatePost() throws Exception {
        willDoNothing().given(postService).createPost(any(PostRequest.class), anyString());

        this.mockMvc.perform(post(DEFAULT_ADMIN + "/{type}/create", "faqs"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN + "/faqs"));

        then(postService).should().createPost(any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 수정 준비")
    void testDoUpdatePost() throws Exception {
        given(postService.retrieveOtoReason()).willReturn(List.of("hi"));
        given(postService.retrievePost(anyLong(), anyString())).willReturn(postResponse);

        this.mockMvc.perform(get(DEFAULT_ADMIN + "/{type}/{boardNo}/update", "faqs", 1L))
                    .andExpect(status().isOk())
                    .andExpect(view().name("board/faqs/update-form"));
    }


    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postService).updatePost(anyLong(), any(PostRequest.class), anyString());

        this.mockMvc.perform(put(DEFAULT_ADMIN + "/{types}/{boardNo}/update", "faqs", 1L))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN + "/faqs"));

        then(postService).should().updatePost(anyLong(), any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() throws Exception {
        willDoNothing().given(postService).deletePost(anyLong(), anyString());

        this.mockMvc.perform(delete(DEFAULT_ADMIN + "/{type}/{boardNo}/delete", "faqs", 1L))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_ADMIN + "/faqs"));

        then(postService).should().deletePost(anyLong(), anyString());
    }

}
