package com.nhnacademy.marketgg.client.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.config.RedisConfig;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.service.CommentService;
import com.nhnacademy.marketgg.client.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@Import(RedisConfig.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

    @MockBean
    PostService postService;

    private static final String DEFAULT_POST = "/customer-services";

    @Test
    @DisplayName("1:1 문의 답글 등록")
    void testCreateComment() throws Exception {
        willDoNothing().given(commentService).createComment(anyLong(), any(CommentRequest.class));

        this.mockMvc.perform(post(DEFAULT_POST + "/{postId}", 1L)
                .param("content", "hello!")
                .param("page", "0"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_POST + "/categories/702/1"));

        then(commentService).should(times(1)).createComment(anyLong(), any(CommentRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 답글 등록 실패")
    void testCreateCommentFail() throws Exception {
        given(postService.retrievePost(anyLong(), anyString())).willReturn(new PostResponseForDetail());

        this.mockMvc.perform(post(DEFAULT_POST + "/{postId}", 1L)
                .param("content", "")
                .param("page", "0"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("pages/board/oto-inquiries/detail"));

        then(postService).should(times(1)).retrievePost(anyLong(), anyString());
    }

}
