package com.nhnacademy.marketgg.client.repository.point;

import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.point.PointRetrieveResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointAdapter implements PointRepository {

    private final String gatewayIp;
    private static final String DEFAULT_POINT_ADMIN = "/shop/v1/members/points";
    private static final String DEFAULT_POINT = "/shop/v1/members/points";
    private final RestTemplate restTemplate;

    @Override
    public List<PointRetrieveResponse> adminRetrievePointHistory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<PointRetrieveResponse>> response =
                this.restTemplate.exchange(gatewayIp + DEFAULT_POINT_ADMIN,
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<>() {
                        });

        return response.getBody();
    }

    @Override
    public PageResult<PointRetrieveResponse> retrievePointHistory(final int page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<PageResult<PointRetrieveResponse>> response =
                this.restTemplate.exchange(gatewayIp + DEFAULT_POINT +"/?page=" + page,
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<>() {
                        });

        return response.getBody();
    }
}

