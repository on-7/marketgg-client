package com.nhnacademy.marketgg.client.web;

import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    CommentService commentService;

    private static final String DEFAULT_POST = "/shop/v1/customer-services";

    @Test
    @DisplayName("1:1 문의 답글 등록")
    void testCreateComment() throws Exception {
        willDoNothing().given(commentService).createComment(anyLong(), anyLong(), any(CommentRequest.class));

        this.mockMvc.perform(post(DEFAULT_POST + "/oto-inquiries/{csPostNo}/members/{memberId}", 1L, 1L)
                                     .param("comment", "hello"))
                            .andExpect(status().is3xxRedirection())
                            .andExpect(view().name("redirect:" + DEFAULT_POST + "/oto-inquiries/1/retrieve"));

        then(commentService).should().createComment(anyLong(), anyLong(), any(CommentRequest.class));
    }

}
