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
    public List<PostResponse> retrievePostList(final String categoryid, final Integer page) {
        return postRepository.retrievePostList(categoryid, page);
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postId, final String categoryid) {
        return postRepository.retrievePost(postId, categoryid);
    }

    @Override
    public List<PostResponse> searchForCategory(final String categoryid, final SearchRequest searchRequest) {
        return postRepository.searchForCategory(categoryid, searchRequest);
    }

    @Override
    public List<PostResponse> searchForOption(final String categoryid, final SearchRequest searchRequest,
                                              final String optionType, final String option) {

        return postRepository.searchForOption(categoryid, searchRequest, optionType, option);
    }

    @Override
    public void updatePost(final Long postId, final PostRequest postRequest, final String categoryid)
            throws JsonProcessingException {

        postRepository.updatePost(postId, postRequest, categoryid);
    }

    @Override
    public void deletePost(final Long postId, final String categoryid) {
        postRepository.deletePost(postId, categoryid);
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
