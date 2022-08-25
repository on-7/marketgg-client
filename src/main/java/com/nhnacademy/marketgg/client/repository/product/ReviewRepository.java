package com.nhnacademy.marketgg.client.repository.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import java.util.List;

/**
 * 후기를 관리하기 위한 Repository입니다.
 *
 * @author 조현진
 */
public interface ReviewRepository {

    /**
     * 후기를 새로 생성합니다.
     *
     * @param productId     - 후기가 달릴 상품의 상품번호입니다.
     * @param memberInfo    - 후기를 등록한 회원의 회원정보입니다.
     * @param reviewRequest - 등록할 후기의 정보가 담긴 DTO입니다.
     * @throws JsonProcessingException - JsonException을 던집니다.
     */
    void createReview(final Long productId, final MemberInfo memberInfo,
                      final ReviewCreateRequest reviewRequest) throws JsonProcessingException;

    /**
     * 후기의 전체 목록을 조회합니다.
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @return - 후기의 전체 목록을 반환합니다.
     */
    List<ReviewResponse> retrieveReviews(final Long productId);

    /**
     * 후기를 상세조회합니다.
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId  - 후기의 후기번호입니다.
     * @return - 후기의 상세정보를 반환합니다.
     */
    ReviewResponse retrieveReview(final Long productId, final Long reviewId);

    /**
     * 후기를 수정합니다.
     *
     * @param productId     - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId      - 수정할 후기의 후기번호입니다.
     * @param memberInfo    - 수정하려는 회원의 회원 정보입니다.
     * @param reviewRequest - 수정할 후기의 정보가 담긴 DTO 입니다.
     * @throws JsonProcessingException - JsonException을 던집니다.
     */
    void updateReview(final Long productId, final Long reviewId, final MemberInfo memberInfo,
                      final ReviewUpdateRequest reviewRequest) throws JsonProcessingException;

    /**
     * 후기를 삭제합니다.
     *
     * @param productId  - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId   - 지우려는 후기의 후기번호입니다.
     * @param memberInfo - 지우려는 회원의 회원 정보입니다.
     */
    void deleteReview(final Long productId, final Long reviewId, final MemberInfo memberInfo);

    /**
     * 후기를 베스트후기로 선정합니다.
     * 베스트후기 선정에 성공했을경우 로그를 출력합니다.
     * 실패했을 경우 실패 로그를 출력합니다.
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId  - 베스트후기로 선정하려는 후기의 후기번호입니다.
     */
    void makeBestReview(final Long productId, final Long reviewId);
}
