package com.nhnacademy.marketgg.client.repository.product;

import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryReplyRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryRequest;
import com.nhnacademy.marketgg.client.dto.product.ProductInquiryResponse;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.util.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
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
    private static final String ADMIN_DEFAULT_PRODUCT = "/shop/v1/admin/products/";
    private static final String DEFAULT_PRODUCT = "/shop/v1/products/";


    @Override
    public void updateInquiryReply(final ProductInquiryReplyRequest replyRequest, final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<ProductInquiryReplyRequest> httpEntity = new HttpEntity<>(replyRequest, buildHeaders());

        ResponseEntity<CommonResult<String>> response = restTemplate.exchange(
            gatewayIp + ADMIN_DEFAULT_PRODUCT + "inquiries/" + inquiryId,
            HttpMethod.PUT,
            httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);
    }

    @Override
    public void createInquiry(final Long productId, final ProductInquiryRequest inquiryRequest)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<ProductInquiryRequest> httpEntity = new HttpEntity<>(inquiryRequest, buildHeaders());

        ResponseEntity<CommonResult<String>> response = restTemplate.exchange(
            gatewayIp + DEFAULT_PRODUCT + productId + "/inquiry",
            HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);

    }

    @Override
    public PageResult<ProductInquiryResponse> retrieveAllInquiry(final Integer page, final Long productId)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<String> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<PageResult<ProductInquiryResponse>>> response
            = restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + "/inquiries",
                                    HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public PageResult<ProductInquiryResponse> retrieveAllInquiry(final Integer page)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<String> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<PageResult<ProductInquiryResponse>>> response
            = restTemplate.exchange(gatewayIp + "/shop/v1/members/product-inquiries", HttpMethod.GET, httpEntity,
                                    new ParameterizedTypeReference<>() {
                                    });

        ResponseUtils.checkError(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void deleteInquiry(final Long productId, final Long inquiryId)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<Void> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<String>> response
            = restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + "/inquiry/" + inquiryId,
                                    HttpMethod.DELETE, httpEntity, new ParameterizedTypeReference<>() {
            });

        ResponseUtils.checkError(response);
    }

    @Override
    public PageResult<ProductInquiryResponse> retrieveInquiriesByAdmin(final Integer page) {
        HttpEntity<String> httpEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<PageResult<ProductInquiryResponse>>> response
            = restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + "inquiries", HttpMethod.GET, httpEntity,
                                    new ParameterizedTypeReference<>() {
                                    });
        ResponseUtils.checkError(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
