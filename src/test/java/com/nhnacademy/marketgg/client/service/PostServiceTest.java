package com.nhnacademy.marketgg.client.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.repository.PostRepository;
import com.nhnacademy.marketgg.client.service.impl.DefaultPostService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    DefaultPostService postService;

    @Mock
    PostRepository postRepository;

    private PostResponse postResponse;
    private PostResponseForDetail postResponseForDetail;
    private PostRequest postRequest;
    private SearchRequestForCategory searchRequest;

    @BeforeEach
    void setUp() {
        postResponse = new PostResponse();
        postResponseForDetail = new PostResponseForDetail();
        ReflectionTestUtils.setField(postResponse, "id", 1L);
        ReflectionTestUtils.setField(postResponseForDetail, "id", 1L);
        postRequest = new PostRequest("701", "hi", "hello", "환불");
        searchRequest = new SearchRequestForCategory("702", "hi", 0, 1);
    }

    @Test
    @DisplayName("게시글 생성")
    void testCreatePost() throws Exception {
        willDoNothing().given(postRepository).createPost(any(PostRequest.class));

        postService.createPost(postRequest);

        then(postRepository).should(times(1)).createPost(any(PostRequest.class));
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void testRetrievePostList() {
        given(postRepository.retrievePostList(anyString(), anyInt())).willReturn(List.of(postResponse));

        List<PostResponse> list = postService.retrievePostList("702", 0);

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void testRetrievePost() {
        given(postRepository.retrievePost(anyLong(), anyString())).willReturn(postResponseForDetail);

        PostResponseForDetail response = postService.retrievePost(1L, "703");

        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("카테고리 별 게시글 검색")
    void testSearchForCategory() {
        given(postRepository.searchForCategory(any(SearchRequestForCategory.class))).willReturn(
                List.of(postResponse));

        List<PostResponse> list = postService.searchForCategory(searchRequest);

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("카테고리 내 사유별 게시글 검색")
    void testSearchForReason() {
        given(postRepository.searchForOption(any(SearchRequestForCategory.class), anyString(),
                                             anyString())).willReturn(List.of(postResponse));

        List<PostResponse> list = postService.searchForOption(searchRequest, "reason", "환불");

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws Exception {
        willDoNothing().given(postRepository).updatePost(anyLong(), any(PostRequest.class), anyString());

        postService.updatePost(1L, postRequest, "702");

        then(postRepository).should(times(1)).updatePost(anyLong(), any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() {
        willDoNothing().given(postRepository).deletePost(anyLong(), anyString());

        postService.deletePost(1L, "oto-inquiries");

        then(postRepository).should(times(1)).deletePost(anyLong(), anyString());
    }

    @Test
    @DisplayName("1:1문의 사유 목록 조회")
    void testRetrieveOtoReason() {
        given(postRepository.retrieveReason()).willReturn(List.of("hello"));

        List<String> list = postService.retrieveOtoReason();

        assertThat(list.get(0)).isEqualTo("hello");
    }

    @Test
    @DisplayName("1:1 문의 상태 변경")
    void testChangeStatus() throws Exception {
        willDoNothing().given(postRepository).changeStatus(anyLong(), any(PostStatusUpdateRequest.class));

        postService.changeStatus(1L, new PostStatusUpdateRequest("안녕"));

        then(postRepository).should(times(1)).changeStatus(anyLong(), any(PostStatusUpdateRequest.class));
    }

    @Test
    @DisplayName("1:1 문의 상태 목록 조회")
    void testRetrieveOtoStatus() {
        given(postRepository.retrieveStatus()).willReturn(List.of("hello"));

        List<String> list = postService.retrieveOtoStatus();

        assertThat(list.get(0)).isEqualTo("hello");
    }

}
