package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;

import java.util.List;

public interface GivenCouponService {

    List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Long memberId);

}
