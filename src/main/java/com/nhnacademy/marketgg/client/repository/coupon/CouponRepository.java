package com.nhnacademy.marketgg.client.repository.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;

/**
 * 쿠폰 레포지토리입니다.
 *
 * @version 1.0.0
 */
public interface CouponRepository {

    /**
     * 쿠폰을 등록하는 메소드입니다.
     *
     * @param couponRequest - 쿠폰을 등록하기 위한 정보를 담은 객체입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 쿠폰 단건 조회를 실행하는 메소드입니다.
     *
     * @param couponId - 단건 조회를 할 쿠폰 식별번호입니다.
     * @return 쿠폰 단건 조회 결과 정보를 담은 객체를 반환합니다.
     * @since 1.0.0
     */
    CouponRetrieveResponse retrieveCoupon(final Long couponId) throws UnAuthenticException, UnAuthorizationException;

    /**
     * 쿠폰 전체 목록 조회를 실행하는 메소드입니다.
     *
     * @return 쿠폰 전체 목록 조회 결과 정보를 담은 객체 List 를 반환합니다.
     * @since 1.0.0
     */
    PageResult<CouponRetrieveResponse> retrieveCoupons(final Integer page) throws UnAuthenticException, UnAuthorizationException;

    /**
     * 쿠폰을 수정하는 메소드입니다.
     *
     * @param couponId      - 수정할 쿠폰 식별번호입니다.
     * @param couponRequest - 쿠폰을 수정하기 위한 정보를 담은 객체입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException, UnAuthenticException, UnAuthorizationException;

    /**
     * 쿠폰을 삭제하는 메소드입니다.
     *
     * @param couponId - 삭제할 쿠폰 식별번호입니다.
     */
    void deleteCoupon(final Long couponId) throws UnAuthenticException, UnAuthorizationException;

}
