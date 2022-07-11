package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final String gateWayIp;
    private final HttpHeaders headers = buildHeaders();
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<List<CategoryRetrieveResponse>>
                response = restTemplate.exchange(gateWayIp + "/admin/v1/categories",
                                                 HttpMethod.GET, requestEntity,
                                                 new ParameterizedTypeReference<>() {
                                                 });
        return response.getBody();
    }

    private static HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
