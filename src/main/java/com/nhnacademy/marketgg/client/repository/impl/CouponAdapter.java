package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponAdapter implements CouponRepository {

    private final String gateWayIp;

    private final RestTemplate restTemplate;

    private static final String DEFAULT_COUPON = "/shop/v1/admin/coupons";

    @Override
    public List<CouponRetrieveResponse> retrieveCoupons() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<CouponRetrieveResponse>>
                response = restTemplate.exchange(gateWayIp + DEFAULT_COUPON,
                                                 HttpMethod.GET, requestEntity,
                                                 new ParameterizedTypeReference<>() {
                                                 });

        this.checkResponseUri(response);
        return response.getBody();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponseUri(final ResponseEntity<T> response) {
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
