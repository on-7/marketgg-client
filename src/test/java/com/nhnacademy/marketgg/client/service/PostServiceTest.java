package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.CommentResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.repository.PostRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private DefaultPostService postService;

    @Mock
    private PostRepository postRepository;

    private static PostRequest postRequest;
    private static PostResponse postResponse;
    private static PostResponseForOtoInquiry postResponseForOtoInquiry;

    @BeforeEach
    void setUp() {
        postRequest = new PostRequest("111", "안녕", "디지몬", "환불", "종료");
        postResponse = new PostResponse();
        postResponseForOtoInquiry = new PostResponseForOtoInquiry();
        CommentResponse commentResponse = new CommentResponse();

        ReflectionTestUtils.setField(postResponse, "postNo", 1L);
        ReflectionTestUtils.setField(commentResponse, "content", "hello");
        ReflectionTestUtils.setField(postResponseForOtoInquiry, "commentList", List.of(commentResponse));
    }

    @Test
    @DisplayName("게시글 등록")
    void testCreatePost() throws JsonProcessingException {
        willDoNothing().given(postRepository).createPost(any(PostRequest.class), anyString());

        postService.createPost(postRequest, "oto-inquiries");

        then(postRepository).should().createPost(any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void testRetrievesPosts() throws JsonProcessingException {
        given(postRepository.retrievesPosts(any(PageRequest.class), anyString())).willReturn(List.of(postResponse));

        List<PostResponse> list = postService.retrievesPosts(PageRequest.of(0, 1), "oto-inquiries");

        assertThat(list.get(0).getPostNo()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void testRetrievePost() {
        given(postRepository.retrievePost(anyLong(), anyString())).willReturn(postResponse);

        PostResponse response = postService.retrievePost(1L, "faqs");

        assertThat(response.getPostNo()).isEqualTo(1L);
    }

    @Test
    @DisplayName("1:1 문의 단건 조회")
    void testRetrievePostForOtoInquiry() {
        given(postRepository.retrievePostForOtoInquiry(anyLong(), anyString())).willReturn(postResponseForOtoInquiry);

        PostResponseForOtoInquiry response = postService.retrievePostForOtoInquiry(1L, "oto-inquiries");

        assertThat(response.getCommentList()).hasSize(1);
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() {
        willDoNothing().given(postRepository).updatePost(anyLong(), any(PostRequest.class), anyString());

        postService.updatePost(1L, postRequest, "oto-inquiries");

        then(postRepository).should().updatePost(anyLong(), any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() {
        willDoNothing().given(postRepository).deletePost(anyLong(), anyString());

        postService.deletePost(1L, "oto-inquiries");

        then(postRepository).should().deletePost(anyLong(), anyString());
    }

}
