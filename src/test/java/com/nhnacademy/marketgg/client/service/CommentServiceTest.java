package com.nhnacademy.marketgg.client.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.dto.customer_service.CommentRequest;
import com.nhnacademy.marketgg.client.repository.customer_service.CommentRepository;
import com.nhnacademy.marketgg.client.service.customer_service.DefaultCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private DefaultCommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private static CommentRequest commentRequest;

    @BeforeEach
    void setUp() {
        commentRequest = new CommentRequest("hello");
    }

    @Test
    @DisplayName("1:1 문의 답글 등록")
    void testCreateComment() throws Exception {
        willDoNothing().given(commentRepository).createComment(anyLong(), any(CommentRequest.class));

        commentService.createComment(1L, commentRequest);

        then(commentRepository).should(times(1)).createComment(anyLong(), any(CommentRequest.class));
    }

}
