package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
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
    public List<PostResponse> retrievePostList(final String categoryId, final Integer page) {
        return postRepository.retrievePostList(categoryId, page);
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postId, final String categoryId) {
        return postRepository.retrievePost(postId, categoryId);
    }

    @Override
    public List<PostResponse> searchForCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException {
        return postRepository.searchForCategory(searchRequest);
    }

    @Override
    public List<PostResponse> searchForOption(final SearchRequestForCategory searchRequest,
                                              final String optionType, final String option)
            throws JsonProcessingException {

        return postRepository.searchForOption(searchRequest, optionType, option);
    }

    @Override
    public void updatePost(final Long postId, final PostRequest postRequest, final String categoryId)
            throws JsonProcessingException {

        postRepository.updatePost(postId, postRequest, categoryId);
    }

    @Override
    public void deletePost(final Long postId, final String categoryId) {
        postRepository.deletePost(postId, categoryId);
    }

    @Override
    public List<String> retrieveOtoReason() {
        return postRepository.retrieveReason();
    }

    @Override
    public void changeStatus(final Long postId, final PostStatusUpdateRequest postRequest) throws JsonProcessingException {
        postRepository.changeStatus(postId, postRequest);
    }

    @Override
    public List<String> retrieveOtoStatus() {
        return postRepository.retrieveStatus();
    }

}
