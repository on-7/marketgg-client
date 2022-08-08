package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.request.ProductCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductModifyRequest;
import com.nhnacademy.marketgg.client.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.client.dto.response.ProductResponse;
import com.nhnacademy.marketgg.client.dto.response.SearchProductResponse;
import com.nhnacademy.marketgg.client.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.client.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    private static final String DEFAULT_PRODUCT = "/shop/v1/products";
    private final RestTemplate restTemplate;

    @Override
    public void createProduct(final MultipartFile image, final ProductCreateRequest productRequest)
            throws IOException {

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
                getLinkedMultiValueMapHttpEntity(image, productRequest);

        ResponseEntity<Void> response = this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT,
                                                                   HttpMethod.POST,
                                                                   httpEntity,
                                                                   new ParameterizedTypeReference<>() {
                                                                   });

        this.checkResponseUri(response);
    }

    @Override
    public List<ProductResponse> retrieveProducts() {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<DefaultPageResult<ProductResponse>> response =
                this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT,
                                           HttpMethod.GET,
                                           request,
                                           new ParameterizedTypeReference<>() {
                                           });


        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public ProductResponse retrieveProductDetails(final Long productId) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<SingleResponse<ProductResponse>> response =
                this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + productId,
                                           HttpMethod.GET,
                                           httpEntity,
                                           new ParameterizedTypeReference<>() {
                                           });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<ProductResponse> retrieveProductsByCategory(final String categorizationCode,
                                                            final String categoryCode) {

        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<List<ProductResponse>> response = this.restTemplate.exchange(
                gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + categorizationCode + "/" + categoryCode,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });

        return response.getBody();
    }

    @Override
    public void updateProduct(final Long productId, final MultipartFile image,
                              final ProductModifyRequest productRequest) throws IOException {

        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity =
                getLinkedMultiValueMapHttpEntity(image, productRequest);

        ResponseEntity<Void> response =
                this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + productId,
                                           HttpMethod.PUT,
                                           httpEntity,
                                           new ParameterizedTypeReference<>() {
                                           });

        this.checkResponseUri(response);
    }

    @Override
    public void deleteProduct(final Long productId) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ProductResponse> response =
                this.restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "/" + productId + "/deleted",
                                           HttpMethod.POST,
                                           httpEntity,
                                           new ParameterizedTypeReference<>() {
                                           });

        this.checkResponseUri(response);
    }

    @Override
    public List<SearchProductResponse> searchProductList(final String keyword, final Integer page) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<SearchProductResponse>> response =
                this.restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + "/search?keyword=" + keyword + "&page=" + page,
                                           HttpMethod.GET,
                                           httpEntity,
                                           new ParameterizedTypeReference<>() {
                                           });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<SearchProductResponse> searchProductListByCategory(final String categoryId, final String keyword, final Integer page) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<SearchProductResponse>> response =
                this.restTemplate.exchange(
                        gatewayIp + DEFAULT_PRODUCT + "/categories/" + categoryId + "/search?keyword=" + keyword + "&page=" + page,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<>() {
                        });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<SearchProductResponse> searchProductListByPrice(final String categoryId, final String option, final String keyword, final Integer page) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<SearchProductResponse>> response =
                this.restTemplate.exchange(
                        gatewayIp + DEFAULT_PRODUCT + "/categories/" + categoryId + "/price/" + option + "/search?keyword=" + keyword + "&page=" + page,
                        HttpMethod.GET,
                        httpEntity,
                        new ParameterizedTypeReference<>() {
                        });

        this.checkResponseUri(response);
        return response.getBody();
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

}
