package com.nhnacademy.marketgg.client.adapter.impl;

import com.nhnacademy.marketgg.client.adapter.ProductAdapter;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultProductAdapter implements ProductAdapter {

    private static final String BASE_URL = "http://localhost:7080";
    private static final String SERVER_DEFAULT_PRODUCT = "/admin/v1/products";

    private final RestTemplate restTemplate;

    @Override
    public void createProduct(final ProductCreateRequest productRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductCreateRequest> httpEntity = new HttpEntity<>(productRequest, headers);
        ResponseEntity<Void> response =
            restTemplate.exchange(BASE_URL
                    + SERVER_DEFAULT_PRODUCT,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

    @Override
    public List<ProductResponse> retrieveProducts() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ProductResponse>> response =
            restTemplate.exchange(BASE_URL
                    + SERVER_DEFAULT_PRODUCT,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

}
