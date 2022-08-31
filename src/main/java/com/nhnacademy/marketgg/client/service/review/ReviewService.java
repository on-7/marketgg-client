package com.nhnacademy.marketgg.client.service.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.review.ReviewUpdateRequest;
import com.nhnacademy.marketgg.client.dto.review.ReviewRatingResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewResponse;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;

import java.util.List;

/**
 * 후기를 관리하기 위한 Service입니다.
 *
 * @author 조현진
 */
public interface ReviewService {

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
    PageResult<ReviewResponse> retrieveReviews(final Long productId, final int page);

    /**
     * 후기를 상세조회합니다.
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId  - 후기의 후기번호입니다.
     * @return - 후기의 상세정보를 반환합니다.
     */
    ReviewResponse retrieveReview(final Long productId, final Long reviewId);

    CommonResult<List<ReviewRatingResponse>> retrieveReviewsByRating(final Long productId);

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
     *
     * @param productId - 후기가 달린 상품의 상품번호입니다.
     * @param reviewId  - 베스트후기로 선정하려는 후기의 후기번호입니다.
     */
    void makeBestReview(final Long productId, final Long reviewId);
}
