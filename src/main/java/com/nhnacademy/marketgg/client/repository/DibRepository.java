package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;

import java.util.List;

/**
 * 찜 Repository 입니다.
 *
 * @version 1.0.0
 * @author 박세완
 */
public interface DibRepository {

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
