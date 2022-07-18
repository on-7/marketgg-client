package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;

import java.util.List;

/**
 * 찜 Service 입니다.
 * 찜한 목록에 대해서 추가/삭제 만 가능하며 수정 불가 합니다.
 *
 * @version 1.0.0
 */
public interface DibService {

    /**
     * 지정한 회원의 찜 목록에 지정한 상품을 추가할 수 있는 메소드입니다.
     *
     * @param memberId - 지정한 회원의 식별번호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @since 1.0.0
     */
    void createDib(final Long memberId, final Long productId);

    /**
     * 지정한 회원의 찜한 상품 전체 목록을 반환하는 메소드입니다.
     *
     * @param id - 지정한 회원의 식별번호입니다.
     * @return 회원의 찜 목록 전체를 List 로 반환합니다.
     * @since 1.0.0
     */
    List<DibRetrieveResponse> retrieveDibs(final Long id);

    /**
     * 지정한 회원의 찜 목록에서 지정한 상품을 삭제시키는 메소드입니다.
     *
     * @param memberId - 지정한 회원의 식별번호입니다.
     * @param productId - 지정한 상품의 식별번호입니다.
     * @since 1.0.0
     */
    void deleteDib(final Long memberId, final Long productId);

}

