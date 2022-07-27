package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;

import java.util.List;

public interface GivenCouponRepository {

    List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(Long memberId);

}
