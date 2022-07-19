package com.nhnacademy.marketgg.client.service;

import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;

import java.util.List;

public interface CouponService {
    List<CouponRetrieveResponse> retrieveCoupons();

}
