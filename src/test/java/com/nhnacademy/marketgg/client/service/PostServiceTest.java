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
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.dto.response.SearchBoardResponse;
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

    private SearchBoardResponse searchBoardResponse;
    private PostResponse postResponse;
    private PostResponseForDetail postResponseForDetail;
    private PostResponseForOtoInquiry postResponseForOtoInquiry;
    private PostRequest postRequest;
    private SearchRequest searchRequest;

    private static final String USER = "user";

    @BeforeEach
    void setUp() {
        searchBoardResponse = new SearchBoardResponse();
        postResponse = new PostResponse();
        postResponseForDetail = new PostResponseForDetail();
        postResponseForOtoInquiry = new PostResponseForOtoInquiry();
        ReflectionTestUtils.setField(searchBoardResponse, "id", 1L);
        ReflectionTestUtils.setField(postResponse, "id", 1L);
        ReflectionTestUtils.setField(postResponseForDetail, "id", 1L);
        ReflectionTestUtils.setField(postResponseForOtoInquiry, "id", 1L);
        postRequest = new PostRequest("701", "hi", "hello", "환불");
        searchRequest = new SearchRequest("hi", 0, 1);
    }

    @Test
    @DisplayName("게시글 생성")
    void testCreatePost() throws Exception {
        willDoNothing().given(postRepository).createPost(any(PostRequest.class), anyString());

        postService.createPost(postRequest, USER);

        then(postRepository).should(times(1)).createPost(any(PostRequest.class), anyString());
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void testRetrievesPostList() {
        given(postRepository.retrievesPostList(anyInt(), anyString(), anyString())).willReturn(List.of(postResponse));

        List<PostResponse> list = postService.retrievesPostList(1, "oto-inquiries", USER);

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("회원의 1:1 문의 게시글 조회")
    void testRetrievesPostListForMe() {
        given(postRepository.retrievesPostListForMe(anyInt(), anyString())).willReturn(List.of(postResponse));

        List<PostResponse> list = postService.retrievesPostListForMe(1, "oto-inquiries");

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void testRetrievePost() {
        given(postRepository.retrievePost(anyLong(), anyString(), anyString())).willReturn(postResponseForDetail);

        PostResponseForDetail response = postService.retrievePost(1L, "faqs", USER);

        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("1:1 문의 게시글 단건 조회")
    void testRetrievePostForOtoInquiry() {
        given(postRepository.retrievePostForOtoInquiry(anyLong(), anyString(), anyString())).willReturn(
                postResponseForOtoInquiry);

        PostResponseForOtoInquiry response = postService.retrievePostForOtoInquiry(1L, "oto-inquiries", USER);

        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("카테고리 별 게시글 검색")
    void testSearchForCategory() throws Exception {
        given(postRepository.searchForCategory(anyString(), any(SearchRequest.class), anyString())).willReturn(
                List.of(searchBoardResponse));

        List<SearchBoardResponse> list = postService.searchForCategory("701", searchRequest, USER);

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("카테고리 내 사유별 게시글 검색")
    void testSearchForReason() throws Exception {
        given(postRepository.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString(),
                                             anyString())).willReturn(List.of(searchBoardResponse));

        List<SearchBoardResponse> list = postService.searchForReason("702", searchRequest, "환불", USER);

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("카테고리 내 상태별 게시글 검색")
    void testSearchForStatus() throws Exception {
        given(postRepository.searchForOption(anyString(), any(SearchRequest.class), anyString(), anyString(),
                                             anyString())).willReturn(List.of(searchBoardResponse));

        List<SearchBoardResponse> list = postService.searchForStatus("702", searchRequest, "종료", USER);

        assertThat(list.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() {
        willDoNothing().given(postRepository).updatePost(anyLong(), any(PostRequest.class), anyString(), anyString());

        postService.updatePost(1L, postRequest, "oto-inquiries", USER);

        then(postRepository).should(times(1)).updatePost(anyLong(), any(PostRequest.class), anyString(), anyString());
    }

    @Test
    @DisplayName("게시글 삭제")
    void testDeletePost() {
        willDoNothing().given(postRepository).deletePost(anyLong(), anyString(), anyString());

        postService.deletePost(1L, "oto-inquiries", USER);

        then(postRepository).should(times(1)).deletePost(anyLong(), anyString(), anyString());
    }

    @Test
    @DisplayName("1:1문의 사유 목록 조회")
    void testRetrieveOtoReason() {
        given(postRepository.retrieveReason()).willReturn(List.of("hello"));

        List<String> list = postService.retrieveOtoReason();

        assertThat(list.get(0)).isEqualTo("hello");
    }

}
