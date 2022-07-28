package com.nhnacademy.marketgg.client.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;

import java.util.List;

public interface GivenCouponRepository {

    void registerCoupon(final Long memberId, final GivenCouponCreateRequest givenCouponRequest) throws JsonProcessingException;

    List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Long memberId);

}
