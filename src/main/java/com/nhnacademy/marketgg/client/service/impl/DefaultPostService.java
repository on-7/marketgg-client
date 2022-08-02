package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.dto.response.SearchBoardResponse;
import com.nhnacademy.marketgg.client.repository.PostRepository;
import com.nhnacademy.marketgg.client.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(final PostRequest postRequest, final String role) throws JsonProcessingException {
        postRepository.createPost(postRequest, role);
    }

    @Override
    public List<PostResponse> retrievesPostList(final Integer page, final String type, final String role) {
        return postRepository.retrievesPostList(page, type, role);
    }

    @Override
    public List<PostResponse> retrievesPostListForMe(final Integer page, final String type) {
        return postRepository.retrievesPostListForMe(page, type);
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postNo, final String type, final String role) {
        return postRepository.retrievePost(postNo, type, role);
    }

    @Override
    public PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long postNo, final String type,
                                                               final String role) {
        return postRepository.retrievePostForOtoInquiry(postNo, type, role);
    }

    @Override
    public List<SearchBoardResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest,
                                                       final String role)
            throws JsonProcessingException {

        return postRepository.searchForCategory(categoryCode, searchRequest, role);
    }

    @Override
    public List<SearchBoardResponse> searchForReason(final String categoryCode, final SearchRequest searchRequest,
                                                     final String reason, final String role)
            throws JsonProcessingException {

        return postRepository.searchForOption(categoryCode, searchRequest, reason, "reason", role);
    }

    @Override
    public List<SearchBoardResponse> searchForStatus(final String categoryCode, final SearchRequest searchRequest,
                                                     final String status, final String role)
            throws JsonProcessingException {

        return postRepository.searchForOption(categoryCode, searchRequest, status, "status", role);
    }

    @Override
    public void updatePost(final Long postNo, final PostRequest postRequest, final String type, final String role) {
        postRepository.updatePost(postNo, postRequest, type, role);
    }

    @Override
    public void deletePost(final Long postNo, final String type, final String role) {
        postRepository.deletePost(postNo, type, role);
    }

    @Override
    public List<String> retrieveOtoReason() {
        return postRepository.retrieveReason();
    }

}
