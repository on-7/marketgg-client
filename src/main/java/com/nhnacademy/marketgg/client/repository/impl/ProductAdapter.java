package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductRepository {

    private final String gatewayIp;
    private static final String ADMIN_DEFAULT_PRODUCT = "/shop/v1/admin/products";
    private final RestTemplate restTemplate;

    @Override
    public void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
        throws IOException {

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
            getLinkedMultiValueMapHttpEntity(image, productRequest);

        ResponseEntity<Void> response =
            this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT, HttpMethod.POST, httpEntity,
                                       new ParameterizedTypeReference<>() {
                });

        log.info(getResponseURI(response.getHeaders()));
    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<List<ProductResponse>> response =
            this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT, HttpMethod.GET, request,

                                       new ParameterizedTypeReference<>() {
                });

        return (List<ProductResponse>) response.getBody();
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ProductResponse> response =
            this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + productId, HttpMethod.GET,
                                       httpEntity, new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    public List<ProductResponse> retrieveProductsByCategory(final String categorizationCode,
                                                            final String categoryCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ProductResponse>> response = this.restTemplate.exchange(
            gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + categorizationCode + "/" + categoryCode, HttpMethod.GET,
            httpEntity, new ParameterizedTypeReference<>() {
            });

        return response.getBody();
    }

    @Override
    public void updateProduct(final Long productId, final MultipartFile image,
                              final ProductModifyRequest productRequest) throws IOException {

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
            getLinkedMultiValueMapHttpEntity(image, productRequest);

        ResponseEntity<Void> response =
            this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + productId, HttpMethod.PUT,
                                       httpEntity, new ParameterizedTypeReference<>() {
                });

        log.info(getResponseURI(response.getHeaders()));
    }

    @Override
    public void deleteProduct(final Long productId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ProductResponse> response =
            this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + productId + "/deleted",
                                       HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
                });

        log.info(getResponseURI(response.getHeaders()));
    }

    private <T> HttpEntity<LinkedMultiValueMap<String, Object>> getLinkedMultiValueMapHttpEntity(
        MultipartFile image, T productRequest) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Content-disposition",
            "form-data; name=image; filename=" + image.getOriginalFilename());
        headerMap.add("Content-type", "application/octet-stream");
        HttpEntity<byte[]> imageBytes = new HttpEntity<>(image.getBytes(), headerMap);

        LinkedMultiValueMap<String, Object> multipartReqMap = new LinkedMultiValueMap<>();
        multipartReqMap.add("productRequest", productRequest);
        multipartReqMap.add("image", imageBytes);

        return new HttpEntity<>(multipartReqMap, headers);
    }

    private String getResponseURI(final HttpHeaders response) {
        return String.valueOf(response.getLocation());
    }

}
