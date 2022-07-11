package com.nhnacademy.marketgg.client.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
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
    public void createCategory(final CategoryCreateRequest categoryRequest) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(categoryRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
        restTemplate.postForEntity(gateWayIp + "/admin/v1/categories",
                                    requestEntity,
                                    Void.class);
    }

    @Override
    public void updateCategory(final String categoryId, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException {

        String request = objectMapper.writeValueAsString(categoryRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
        restTemplate.put(gateWayIp + "/admin/v1/categories/" + categoryId,
                                   requestEntity,
                                   Void.class);
    }
    
    @Override
    public void deleteCategory(final String categoryId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(gateWayIp + "/admin/v1/categories/" + categoryId,
                              HttpMethod.DELETE,
                              requestEntity,
                              Void.class);
    }

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
