package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
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
    public void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException {
        couponRepository.createCoupon(couponRequest);
    }

    @Override
    public CouponRetrieveResponse retrieveCoupon(Long couponId) {
        return couponRepository.retrieveCoupon(couponId);
    }

    @Override
    public List<CouponRetrieveResponse> retrieveCoupons() {
        return couponRepository.retrieveCoupons();
    }

    @Override
    public void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException {
        couponRepository.updateCoupon(couponId, couponRequest);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        couponRepository.deleteCoupon(couponId);
    }
}
