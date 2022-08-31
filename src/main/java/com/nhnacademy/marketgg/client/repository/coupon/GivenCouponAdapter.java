package com.nhnacademy.marketgg.client.repository.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.coupon.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.util.ResponseUtils;
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
public class GivenCouponAdapter implements GivenCouponRepository {

    private final String gateWayIp;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private static final String DEFAULT_COUPON = "/shop/v1/members/coupons";

    @Override
    public void registerCoupon(GivenCouponCreateRequest givenCouponRequest)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String request = objectMapper.writeValueAsString(givenCouponRequest);

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
    public PageResult<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Integer page)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CommonResult<PageResult<GivenCouponRetrieveResponse>>> response
            = restTemplate.exchange(gateWayIp + DEFAULT_COUPON + "?page=" + page,
                                    HttpMethod.GET, requestEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
