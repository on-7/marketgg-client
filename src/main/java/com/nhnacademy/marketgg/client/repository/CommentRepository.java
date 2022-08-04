package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;

/**
 * 1:1문의 답글과 관련된 Repository 입니다.
 *
 * @version 1.0.0
 * @author 박세완
 */
public interface CommentRepository {

    /**
     * 입력한 정보로 1:1 문의 답글을 등록할 수 있는 Adapter 입니다.
     *
     * @param postNo - 1:1 문의 답글을 등록한 게시글의 식별번호입니다.
     * @param commentRequest - 등록할 1:1문의 답글의 정보를 담은 객체입니다.
     * @throws JsonProcessingException Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createComment(final Long postNo, final CommentRequest commentRequest)
            throws JsonProcessingException;

}
