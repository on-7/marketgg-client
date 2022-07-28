package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
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

    private final ObjectMapper objectMapper;

    private static final String DEFAULT_COUPON = "/admin/coupons";

    @Override
    public void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(couponRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(gateWayIp + DEFAULT_COUPON,
                                                              HttpMethod.POST,
                                                              requestEntity,
                                                              Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public CouponRetrieveResponse retrieveCoupon(Long couponId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CouponRetrieveResponse>
                response = restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "/" + couponId,
                                                 HttpMethod.GET, requestEntity,
                                                 new ParameterizedTypeReference<>() {
                                                 });

        this.checkResponseUri(response);
        return response.getBody();
    }

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

    @Override
    public void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(couponRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "/" + couponId,
                                                              HttpMethod.PUT,
                                                              requestEntity,
                                                              Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response =
                restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "/" + couponId,
                                      HttpMethod.DELETE,
                                      requestEntity,
                                      Void.class);

        this.checkResponseUri(response);
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
