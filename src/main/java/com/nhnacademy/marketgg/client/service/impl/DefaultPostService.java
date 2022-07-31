package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.repository.PostRepository;
import com.nhnacademy.marketgg.client.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public void createPost(final PostRequest postRequest, final String type) throws JsonProcessingException {
        postRepository.createPost(postRequest, type);
    }

    @Override
    public List<PostResponse> retrievesPostList(final Pageable pageable, final String type) throws JsonProcessingException {
        return postRepository.retrievesPostList(pageable, type);
    }

    @Override
    public PostResponse retrievePost(final Long boardNo, final String type) {
        return postRepository.retrievePost(boardNo, type);
    }

    @Override
    public PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long boardNo, final String type) {
        return postRepository.retrievePostForOtoInquiry(boardNo, type);
    }

    @Override
    public void updatePost(final Long boardNo, final PostRequest postRequest, final String type) {
        postRepository.updatePost(boardNo, postRequest, type);
    }

    @Override
    public void deletePost(final Long boardNo, final String type) {
        postRepository.deletePost(boardNo, type);
    }

}
