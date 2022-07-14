package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.domain.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.domain.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.domain.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.repository.ProductRepository;
import java.io.IOException;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductAdapter implements ProductRepository {

    // TODO: @Value로 고치기
    private static final String BASE_URL = "http://localhost:7080";
    private static final String SERVER_DEFAULT_PRODUCT = "/admin/v1/products";

    // TODO: AdapterTemplate 만들어서 공통 코드 분리하기
    private final RestTemplate restTemplate;

    // REVIEW 5
    @Override
    public void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
        throws IOException {

        // MEMO
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition",
            "form-data; name=image; filename=" + image.getOriginalFilename());
        headerMap.add("Content-type", "application/pdf");
        HttpEntity<byte[]> doc = new HttpEntity<>(image.getBytes(), headerMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("productRequest", productRequest);
        multipartReqMap.add("image", doc);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
            new HttpEntity<>(multipartReqMap, headers);
        ResponseEntity<Void> response =
            restTemplate.exchange(BASE_URL + SERVER_DEFAULT_PRODUCT, HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        // TODO: Log를 AOP로 뺴기
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

    @Override
    public List<ProductResponse> retrieveProducts() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ProductResponse>> response =
            restTemplate.exchange(BASE_URL + SERVER_DEFAULT_PRODUCT, HttpMethod.GET, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    public ProductResponse retrieveProductDetails(Long productId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ProductResponse> response =
            restTemplate.exchange(BASE_URL + SERVER_DEFAULT_PRODUCT + "/" + productId,
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    public void updateProduct(final Long productId, final MultipartFile image,
                              final ProductModifyRequest productRequest) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition",
            "form-data; name=image; filename=" + image.getOriginalFilename());
        headerMap.add("Content-type", "application/pdf");
        HttpEntity<byte[]> doc = new HttpEntity<>(image.getBytes(), headerMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("productRequest", productRequest);
        multipartReqMap.add("image", doc);

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
            new HttpEntity<>(multipartReqMap, headers);
        ResponseEntity<Void> response =
            restTemplate.exchange(BASE_URL + SERVER_DEFAULT_PRODUCT + "/" + productId,
                HttpMethod.PUT, httpEntity,
                new ParameterizedTypeReference<>() {
                });

        // TODO: Log를 AOP로 빼기
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

    @Override
    public void deleteProduct(Long productId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ProductResponse> response =
            restTemplate.exchange(BASE_URL + SERVER_DEFAULT_PRODUCT + "/" + productId + "/deleted",
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
