package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.repository.CommentRepository;
import com.nhnacademy.marketgg.client.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void createComment(final Long postNo, final CommentRequest commentRequest)
            throws JsonProcessingException {

        commentRepository.createComment(postNo, commentRequest);
    }

}
