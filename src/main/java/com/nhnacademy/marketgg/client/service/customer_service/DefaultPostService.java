package com.nhnacademy.marketgg.client.service.customer_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.customer_service.PostRequest;
import com.nhnacademy.marketgg.client.dto.customer_service.PostResponse;
import com.nhnacademy.marketgg.client.dto.customer_service.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.customer_service.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.search.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.customer_service.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(final PostRequest postRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        postRepository.createPost(postRequest);
    }

    @Override
    public List<PostResponse> retrievePostList(final String categoryId, final Integer page)
            throws UnAuthenticException, UnAuthorizationException {

        return postRepository.retrievePostList(categoryId, page);
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postId, final String categoryId)
            throws UnAuthenticException, UnAuthorizationException {

        return postRepository.retrievePost(postId, categoryId);
    }

    @Override
    public List<PostResponse> searchForCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        return postRepository.searchForCategory(searchRequest);
    }

    @Override
    public List<PostResponse> searchForOption(final SearchRequestForCategory searchRequest,
                                              final String optionType, final String option)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        return postRepository.searchForOption(searchRequest, optionType, option);
    }

    @Override
    public void updatePost(final Long postId, final PostRequest postRequest, final String categoryId)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        postRepository.updatePost(postId, postRequest, categoryId);
    }

    @Override
    public void deletePost(final Long postId, final String categoryId)
            throws UnAuthenticException, UnAuthorizationException {

        postRepository.deletePost(postId, categoryId);
    }

    @Override
    public List<String> retrieveOtoReason() throws UnAuthenticException, UnAuthorizationException {
        return postRepository.retrieveReason();
    }

    @Override
    public void changeStatus(final Long postId, final PostStatusUpdateRequest postRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        postRepository.changeStatus(postId, postRequest);
    }

    @Override
    public List<String> retrieveOtoStatus() throws UnAuthenticException, UnAuthorizationException {
        return postRepository.retrieveStatus();
    }

}
