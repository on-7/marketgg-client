package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;

/**
 * 1:1문의 답변과 관련된 Service 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface CommentService {

    /**
     * 입력받은 정보로 1:1문의의 답글을 등록하는 서비스입니다.
     *
     * @param postId         - 답글이 작성된 게시글 식별번호입니다.
     * @param commentRequest - 등록할 답글의 정보를 담은 객체입니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void createComment(final Long postId, final CommentRequest commentRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

}
