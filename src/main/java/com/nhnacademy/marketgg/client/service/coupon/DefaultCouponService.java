package com.nhnacademy.marketgg.client.service.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRequest;
import com.nhnacademy.marketgg.client.dto.coupon.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.coupon.CouponRepository;
import com.nhnacademy.marketgg.client.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException, UnAuthenticException,
        UnAuthorizationException {

        couponRepository.createCoupon(couponRequest);
    }

    @Override
    public CouponRetrieveResponse retrieveCoupon(final Long couponId) throws UnAuthenticException, UnAuthorizationException {
        return couponRepository.retrieveCoupon(couponId);
    }

    @Override
    public PageResult<CouponRetrieveResponse> retrieveCoupons(final Integer page) throws UnAuthenticException, UnAuthorizationException {
        return couponRepository.retrieveCoupons(page);
    }

    @Override
    public void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException,
        UnAuthenticException, UnAuthorizationException {
        couponRepository.updateCoupon(couponId, couponRequest);
    }

    @Override
    public void deleteCoupon(final Long couponId) throws UnAuthenticException, UnAuthorizationException {
        couponRepository.deleteCoupon(couponId);
    }
}
