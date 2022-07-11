package com.nhnacademy.marketgg.client.adapter.impl;

import com.nhnacademy.marketgg.client.adapter.ProductAdapter;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultProductAdapter implements ProductAdapter {

    private static final String BASE_URL = "http://localhost:7080";
    private static final String SERVER_DEFAULT_PRODUCT = "/admin/v1/products";

    private final RestTemplate restTemplate;

    @Override
    public void createProduct(final MultipartFile image,
                              final ProductCreateRequest productRequest) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition", "form-data; name=image; filename=" + image.getOriginalFilename());
        headerMap.add("Content-type", "application/pdf");
        HttpEntity<byte[]> doc = new HttpEntity<>(image.getBytes(), headerMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("productRequest", productRequest);
        multipartReqMap.add("image", doc);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multipartReqMap, headers);
        ResponseEntity<Void> response = restTemplate.exchange(BASE_URL + SERVER_DEFAULT_PRODUCT,
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
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
