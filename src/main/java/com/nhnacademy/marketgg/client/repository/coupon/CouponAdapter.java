package com.nhnacademy.marketgg.client.repository.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.PageResult;
import com.nhnacademy.marketgg.client.dto.request.CouponRequest;
import com.nhnacademy.marketgg.client.dto.response.CouponRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import java.util.List;
import java.util.Objects;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponAdapter implements CouponRepository {

    private final String gateWayIp;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private static final String DEFAULT_COUPON = "/shop/v1/admin/coupons";

    @Override
    public void createCoupon(final CouponRequest couponRequest) throws JsonProcessingException,
        UnAuthenticException, UnAuthorizationException {

        String request = objectMapper.writeValueAsString(couponRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<CommonResult<String>> response
            = restTemplate.exchange(gateWayIp + DEFAULT_COUPON,
                                    HttpMethod.POST,
                                    requestEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);
    }

    @Override
    public CouponRetrieveResponse retrieveCoupon(final Long couponId) throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<CouponRetrieveResponse>> response
            = restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "/" + couponId, HttpMethod.GET, requestEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public PageResult<CouponRetrieveResponse> retrieveCoupons(final Integer page) throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<PageResult<CouponRetrieveResponse>>> response
            = restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "?page=" + page,
                                    HttpMethod.GET, requestEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void updateCoupon(final Long couponId, final CouponRequest couponRequest) throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {
        String request = objectMapper.writeValueAsString(couponRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<CommonResult<String>> response
            = restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "/" + couponId,
                                    HttpMethod.PUT,
                                    requestEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);
    }

    @Override
    public void deleteCoupon(final Long couponId) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<String>> response =
            restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "/" + couponId,
                                  HttpMethod.DELETE,
                                  requestEntity,
                                  new ParameterizedTypeReference<>() {
                                  });

        ResponseUtils.checkError(response);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
