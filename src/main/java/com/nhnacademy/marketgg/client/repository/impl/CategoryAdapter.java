package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CategoryUpdateRequest;
import com.nhnacademy.marketgg.client.dto.response.CategorizationRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.CategoryRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.CategoryRepository;
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
public class CategoryAdapter implements CategoryRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_CATEGORY = "/shop/v1/admin/categories";

    @Override
    public void createCategory(final CategoryCreateRequest categoryRequest)
            throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(categoryRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY,
                                                              HttpMethod.POST,
                                                              requestEntity,
                                                              Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public CategoryRetrieveResponse retrieveCategory(String id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<CategoryRetrieveResponse>
                response = restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY + "/" + id,
                                                 HttpMethod.GET, requestEntity,
                                                 CategoryRetrieveResponse.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<CategoryRetrieveResponse> retrieveCategories() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<CategoryRetrieveResponse>>
                response = restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY,
                                                 HttpMethod.GET, requestEntity,
                                                 new ParameterizedTypeReference<>() {
                                                 });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<CategorizationRetrieveResponse> retrieveCategorizations() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<CategorizationRetrieveResponse>>
                response = restTemplate.exchange(gateWayIp + "/shop/v1/admin/categorizations",
                                                 HttpMethod.GET, requestEntity,
                                                 new ParameterizedTypeReference<>() {
                                                 });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void updateCategory(final String id, final CategoryUpdateRequest categoryRequest)
            throws JsonProcessingException {

        String request = objectMapper.writeValueAsString(categoryRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response =
                restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY + "/" + id,
                                      HttpMethod.PUT,
                                      requestEntity,
                                      Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void deleteCategory(final String id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response =
                restTemplate.exchange(gateWayIp + DEFAULT_CATEGORY + "/" + id,
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
