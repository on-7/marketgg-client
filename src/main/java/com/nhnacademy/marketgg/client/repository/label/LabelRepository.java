package com.nhnacademy.marketgg.client.repository.label;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.label.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 라벨 Repository 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface LabelRepository {

    /**
     * 입력받은 정보로 라벨을 생성할 수 있는 메소드입니다.
     *
     * @param labelRequest - 라벨 생성을 위한 입력정보를 담은 객체입니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void createLabel(final LabelRegisterRequest labelRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 라벨 전체 목록을 반환하는 메소드입니다.
     *
     * @return 전체 라벨을 담은 List 를 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    List<LabelRetrieveResponse> retrieveResponse() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 라벨을 삭제하는 메소드입니다.
     *
     * @param id - 삭제할 라벨의 식별번호입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void deleteLabel(final Long id) throws UnAuthenticException, UnAuthorizationException;
}
