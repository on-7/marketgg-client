package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.GivenCouponRepository;
import com.nhnacademy.marketgg.client.service.GivenCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultGivenCouponService implements GivenCouponService {

    private final GivenCouponRepository givenCouponRepository;

    @Override
    public void registerCoupon(Long memberId, GivenCouponCreateRequest givenCouponRequest) throws JsonProcessingException {
        givenCouponRepository.registerCoupon(memberId, givenCouponRequest);
    }

    @Override
    public List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Long memberId) {
        return givenCouponRepository.retrieveOwnGivenCoupons(memberId);
    }

}
