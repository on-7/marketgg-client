package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCouponService implements CouponService {

    private final CouponRepository couponRepository;


}
