package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.CouponRepository;
import com.nhnacademy.marketgg.client.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public List<CouponRetrieveResponse> retrieveCoupons() {
        return couponRepository.retrieveCoupons();
    }

}
