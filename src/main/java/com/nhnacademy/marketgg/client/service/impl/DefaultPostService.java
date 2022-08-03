package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
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
    public void createPost(final PostRequest postRequest) throws JsonProcessingException {
        postRepository.createPost(postRequest);
    }

    @Override
    public List<PostResponse> retrievePostList(final String categoryCode, final Integer page) {
        return postRepository.retrievePostList(categoryCode, page);
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postNo, final String categoryCode) {
        return postRepository.retrievePost(postNo, categoryCode);
    }

    @Override
    public List<PostResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest) {
        return postRepository.searchForCategory(categoryCode, searchRequest);
    }

    @Override
    public List<PostResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
                                              final String optionType, final String option) {

        return postRepository.searchForOption(categoryCode, searchRequest, optionType, option);
    }

    @Override
    public void updatePost(final Long postNo, final PostRequest postRequest, final String categoryCode) {
        postRepository.updatePost(postNo, postRequest, categoryCode);
    }

    @Override
    public void deletePost(final Long postNo, final String categoryCode) {
        postRepository.deletePost(postNo, categoryCode);
    }

    @Override
    public List<String> retrieveOtoReason() {
        return postRepository.retrieveReason();
    }

    @Override
    public void changeStatus(final Long postNo, final PostStatusUpdateRequest postRequest) throws JsonProcessingException {
        postRepository.changeStatus(postNo, postRequest);
    }

    @Override
    public List<String> retrieveOtoStatus() {
        return postRepository.retrieveStatus();
    }

}
