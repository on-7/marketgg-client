package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    void createPost(final PostRequest postRequest, final String type) throws JsonProcessingException;

    List<PostResponse> retrievesPosts(final Pageable pageable, final String type) throws JsonProcessingException;

    PostResponse retrievePost(final Long boardNo, final String type);

    PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long boardNo, final String type, final Pageable pageable) throws JsonProcessingException;

    void updatePost(final Long boardNo, final PostRequest postRequest, final String type);

    void deletePost(final Long boardNo, final String type);

}
