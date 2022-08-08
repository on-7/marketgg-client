package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.GivenCouponCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.GivenCouponRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.GivenCouponRepository;
import java.util.List;
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
    public void registerCoupon(Long memberId, GivenCouponCreateRequest givenCouponRequest) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(givenCouponRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(gateWayIp + "/members/" + memberId + "/coupons",
            HttpMethod.POST,
            requestEntity,
            Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<GivenCouponRetrieveResponse> retrieveOwnGivenCoupons(final Long memberId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        // memo url 원래 /shop/v1 이었는데 없어짐 오류나면 바꿔보도록..
        ResponseEntity<List<GivenCouponRetrieveResponse>> response =
            restTemplate.exchange(gateWayIp + "/members/" + memberId + "/coupons",
                HttpMethod.GET,
                requestEntity,
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
