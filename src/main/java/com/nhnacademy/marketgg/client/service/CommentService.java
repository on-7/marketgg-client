package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;

public interface CommentService {

    void createComment(final Long csPostNo, final Long memberId, final CommentRequest commentRequest)
            throws JsonProcessingException;

}
