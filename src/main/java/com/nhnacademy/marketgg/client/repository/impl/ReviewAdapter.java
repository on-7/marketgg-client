package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.response.DefaultPageResult;
import com.nhnacademy.marketgg.client.dto.response.ReviewResponse;
import com.nhnacademy.marketgg.client.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.client.repository.ReviewRepository;
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
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewAdapter implements ReviewRepository {

    private final String gatewayIp;
    private static final String ADMIN_DEFAULT_PRODUCT = "/shop/v1/admin/products/";
    private static final String DEFAULT_PRODUCT = "/shop/v1/products/";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void createReview(final Long productId, final Long reviewId, final MemberInfo memberInfo,
                             final ReviewCreateRequest reviewRequest) throws JsonProcessingException {

        String request = this.objectMapper.writeValueAsString(reviewRequest);
        ResponseEntity<Void> response = this.restTemplate.exchange(
            gatewayIp + DEFAULT_PRODUCT + productId + "/reviews",
            HttpMethod.POST,
            new HttpEntity<>(request, buildHeaders()),
            Void.class);

        checkResponseUri(response);
    }

    @Override
    public List<ReviewResponse> retrieveReviews(Long productId, MemberInfo memberInfo) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<DefaultPageResult<ReviewResponse>> response =
            this.restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + "/reviews",
                                       HttpMethod.GET,
                                       request,
                                       new ParameterizedTypeReference<>() {
                                       });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public ReviewResponse retrieveReview(final Long productId, final Long reviewId,
                                         final MemberInfo memberInfo) {

        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<SingleResponse<ReviewResponse>> response =
            this.restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + "/reviews/" + reviewId,
                                       HttpMethod.GET,
                                       request,
                                       new ParameterizedTypeReference<>() {
                                       });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
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
