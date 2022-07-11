package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
    public void deleteCategory(String categoryId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(gateWayIp + "/admin/v1/categories/" + categoryId,
                              HttpMethod.DELETE,
                              requestEntity,
                              Void.class);
    }

    private static HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
