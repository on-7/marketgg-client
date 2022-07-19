package com.nhnacademy.marketgg.client.repository;

import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;

import java.util.List;

public interface CouponRepository {
    List<CouponRetrieveResponse> retrieveCoupons();

}
