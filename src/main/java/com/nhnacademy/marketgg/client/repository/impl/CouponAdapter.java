package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponAdapter implements CouponRepository {

    private final String gateWayIp;

    private final RestTemplate restTemplate;
    
}
