package com.nhnacademy.marketgg.client.repository.coupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
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
public class GivenCouponAdapter implements GivenCouponRepository {

    private final String gateWayIp;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void registerCoupon(Long memberId, GivenCouponCreateRequest givenCouponRequest)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String request = objectMapper.writeValueAsString(givenCouponRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<CommonResult<String>> response
            = restTemplate.exchange(gateWayIp + "/members/" + memberId + "/coupons",
                                    HttpMethod.POST,
                                    requestEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);
    }

    @Override
    public List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Long memberId)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());

        ResponseEntity<CommonResult<List<GivenCouponRetrieveResponse>>> response =
            restTemplate.exchange(gateWayIp + "/members/" + memberId + "/coupons",
                                  HttpMethod.GET,
                                  requestEntity,
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
