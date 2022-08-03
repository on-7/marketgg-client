package com.nhnacademy.marketgg.client.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
import java.util.List;

/**
 * 지급 쿠폰 서비스입니다.
 *
 * @version 1.0.0
 */
public interface GivenCouponService {

    /**
     * 회원이 쿠폰을 등록하기 위해 Adapter 메소드를 실행합니다.
     *
     * @param memberId           - 쿠폰을 등록하는 회원의 식별번호입니다.
     * @param givenCouponRequest - 쿠폰을 등록하기 위한 정보를 담은 객체입니다.
     * @throws JsonProcessingException - Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @since 1.0.0
     */
    void registerCoupon(final Long memberId, final GivenCouponCreateRequest givenCouponRequest) throws JsonProcessingException;

    /**
     * 회원의 보유 쿠폰 목록을 조회하기 위해 Adapter 메소드를 실행합니다.
     *
     * @param memberId - 목록을 조회하는 회원의 식별번호입니다.
     * @return 조회한 보유 쿠폰 목록을 List 로 반환합니다.
     * @since 1.0.0
     */
    List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Long memberId);

}
