package com.nhnacademy.marketgg.client.service.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.coupon.GivenCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;

    @Override
    public void registerCoupon(GivenCouponCreateRequest givenCouponRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        givenCouponRepository.registerCoupon(givenCouponRequest);
    }

    @Override
    public PageResult<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Integer page)
            throws UnAuthenticException, UnAuthorizationException {

        return givenCouponRepository.retrieveOwnGivenCoupons(page);
    }

}
