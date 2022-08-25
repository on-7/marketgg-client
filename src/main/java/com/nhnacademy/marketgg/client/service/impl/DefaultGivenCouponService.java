package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.coupon.GivenCouponRepository;
import com.nhnacademy.marketgg.client.service.GivenCouponService;
import java.util.List;
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
    public List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons()
        throws UnAuthenticException, UnAuthorizationException {

        return givenCouponRepository.retrieveOwnGivenCoupons();
    }

}
