package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;

import java.util.List;

/**
 * 쿠폰 서비스입니다.
 *
 * @version 1.0.0
 */
public interface CouponService {

    /**
     * 쿠폰을 등록하기 위해 Adapter 메소드를 실행합니다.
     *
     * @param couponRequest - 쿠폰을 등록하기 위한 정보를 담은 객체입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     *
     * @since 1.0.0
     */
    void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException;

    /**
     * 쿠폰 단건 조회를 위해 Adapter 메소드를 실행합니다.
     *
     * @param couponId - 조회할 쿠폰의 식별번호입니다.
     * @return 식별번호로 조회한 쿠폰의 정보를 담은 객체를 반환합니다.
     *
     * @since 1.0.0
     */
    CouponRetrieveResponse retrieveCoupon(Long couponId);

    /**
     * 쿠폰 전체 목록 조회를 위해 Adapter 메소드를 실행합니다.
     *
     * @return 쿠폰 전체 목록의 정보를 담은 객체로 이루어진 List 를 반환합니다.
     *
     * @since 1.0.0
     */
    List<CouponRetrieveResponse> retrieveCoupons();

    /**
     * 쿠폰 수정을 위해 Adapter 메소드를 실행합니다.
     *
     * @param couponId - 수정할 쿠폰의 식별번호입니다.
     * @param couponRequest - 쿠폰을 수정하기 위한 정보를 담은 객체입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     *
     * @since 1.0.0
     */
    void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException;

    /**
     * 쿠폰 삭제를 위해 Adapter 메소드를 실행합니다.
     *
     * @param couponId - 삭제할 쿠폰의 식별번호입니다.
     *
     * @since 1.0.0
     */
    void deleteCoupon(Long couponId);

}
