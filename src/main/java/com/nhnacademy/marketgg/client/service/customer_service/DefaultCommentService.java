package com.nhnacademy.marketgg.client.service.customer_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.customer_service.CommentRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.customer_service.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void createComment(final Long postId, final CommentRequest commentRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        commentRepository.createComment(postId, commentRequest);
    }

}
