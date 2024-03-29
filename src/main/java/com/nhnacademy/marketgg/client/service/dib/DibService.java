package com.nhnacademy.marketgg.client.service.dib;

import com.nhnacademy.marketgg.client.dto.dib.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 찜 Service 입니다.
 * 찜한 목록에 대해서 추가/삭제 만 가능하며 수정 불가 합니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
public interface DibService {

    /**
     * 지정한 회원의 찜 목록에 지정한 상품을 추가할 수 있는 메소드입니다.
     *
     * @param productId - 지정한 상품의 식별번호입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void createDib(final Long productId) throws UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 회원의 찜한 상품 전체 목록을 반환하는 메소드입니다.
     *
     * @return 회원의 찜 목록 전체를 List 로 반환합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    List<DibRetrieveResponse> retrieveDibs() throws UnAuthenticException, UnAuthorizationException;

    /**
     * 지정한 회원의 찜 목록에서 지정한 상품을 삭제시키는 메소드입니다.
     *
     * @param productId - 지정한 상품의 식별번호입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    void deleteDib(final Long productId) throws UnAuthenticException, UnAuthorizationException;

}

