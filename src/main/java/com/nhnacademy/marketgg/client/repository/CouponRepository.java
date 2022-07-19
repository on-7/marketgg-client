package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;

import java.util.List;

public interface CouponRepository {

    void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException;

    CouponRetrieveResponse retrieveCoupon(Long couponId);

    List<CouponRetrieveResponse> retrieveCoupons();

    void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException;

}
