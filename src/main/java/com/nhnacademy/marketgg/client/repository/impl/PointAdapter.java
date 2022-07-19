package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.response.PointRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.repository.PointRepository;
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
public class PointAdapter implements PointRepository {

    private static final String BASE_URL = "http://localhost:7080";

    private final RestTemplate restTemplate;

    @Override
    public List<PointRetrieveResponse> adminRetrievePointHistory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<PointRetrieveResponse>> response =
            this.restTemplate.exchange(BASE_URL + "/shop/v1/admin/points", HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }
}

