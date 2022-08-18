package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.request.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.response.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.ProductInquiryRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
public class ProductInquiryAdapter implements ProductInquiryRepository {

    private final RestTemplate restTemplate;

    private final String gatewayIp;
    private static final String ADMIN_DEFAULT_PRODUCT = "/shop/v1/admin/products/inquiry-reply";
    private static final String DEFAULT_PRODUCT = "/shop/v1/products/";


    @Override
    public void updateInquiryReply(ProductInquiryReplyRequest replyRequest)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<ProductInquiryReplyRequest> httpEntity = new HttpEntity<>(replyRequest, buildHeaders());

        ResponseEntity<CommonResult<String>> response = restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT,
            HttpMethod.PUT,
            httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);
    }

    @Override
    public void createInquiry(Long productId, ProductInquiryRequest inquiryRequest)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<ProductInquiryRequest> httpEntity = new HttpEntity<>(inquiryRequest, buildHeaders());

        ResponseEntity<CommonResult<String>> response = restTemplate.exchange(
            gatewayIp + DEFAULT_PRODUCT + productId + "/inquiry",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);

    }

    @Override
    public List<ProductInquiryResponse> retrieveAllInquiry(Long productId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<String> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<List<ProductInquiryResponse>>> response
            = restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + "/inquiries",
            HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<ProductInquiryResponse> retrieveAllInquiry()
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<String> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<List<ProductInquiryResponse>>> response
            = restTemplate.exchange(gatewayIp + "/shop/v1/members/product-inquiries", HttpMethod.GET, httpEntity,
            new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void deleteInquiry(Long productId, Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException, JsonProcessingException {

        HttpEntity<Void> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<String>> response
            = restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + "/inquiry/" + inquiryId,
            HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
