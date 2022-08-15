package com.nhnacademy.marketgg.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

    @MockBean
    RedisTemplate<String, JwtInfo> redisTemplate;

    private static final String DEFAULT_POST = "/customer-services";

    @Test
    @DisplayName("1:1 문의 답글 등록")
    void testCreateComment() throws Exception {
        willDoNothing().given(commentService).createComment(anyLong(), any(CommentRequest.class));

        this.mockMvc.perform(post(DEFAULT_POST + "/{postNo}", 1L)
                                     .param("content", "hello!"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:" + DEFAULT_POST + "/categories/702/1"));

        then(commentService).should().createComment(anyLong(), any(CommentRequest.class));
    }

}
