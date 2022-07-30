package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;

public interface CommentRepository {

    void createComment(final Long csPostNo, final Long memberId, final CommentRequest commentRequest)
            throws JsonProcessingException;

}
