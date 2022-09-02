package com.nhnacademy.marketgg.client.repository.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.review.ReviewCreateRequest;
import com.nhnacademy.marketgg.client.dto.review.ReviewRatingResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewResponse;
import com.nhnacademy.marketgg.client.dto.review.ReviewUpdateRequest;
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
    private static final String DEFAULT_REVIEW = "/reviews";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void createReview(final Long productId, final MemberInfo memberInfo,
                             final ReviewCreateRequest reviewRequest) throws JsonProcessingException {

        String request = this.objectMapper.writeValueAsString(reviewRequest);

        ResponseEntity<Void> response =
                this.restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + DEFAULT_REVIEW,
                                           HttpMethod.POST,
                                           new HttpEntity<>(request, buildHeaders()),
                                           Void.class);

        checkResponseUri(response);
    }

    @Override
    public PageResult<ReviewResponse> retrieveReviews(final Long productId, final int page) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PageResult<ReviewResponse>> response =
                this.restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + DEFAULT_REVIEW + "?page=" + page,
                                           HttpMethod.GET,
                                           request,
                                           new ParameterizedTypeReference<>() {
                                           });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody());
    }

    @Override
    public CommonResult<List<ReviewRatingResponse>> retrieveReviewsByRating(Long productId) {
        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<CommonResult<List<ReviewRatingResponse>>> response =
                this.restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + DEFAULT_REVIEW + "/rating",
                                           HttpMethod.GET,
                                           request,
                                           new ParameterizedTypeReference<>() {
                                           });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody());
    }

    @Override
    public ReviewResponse retrieveReview(final Long productId, final Long reviewId) {

        HttpHeaders headers = new HttpHeaders(this.buildHeaders());

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<CommonResult<ReviewResponse>> response =
                this.restTemplate.exchange(
                        gatewayIp + DEFAULT_PRODUCT + productId + DEFAULT_REVIEW + "/" + reviewId,
                        HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<>() {
                        });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void updateReview(Long productId, Long reviewId, MemberInfo memberInfo,
                             ReviewUpdateRequest reviewRequest) throws JsonProcessingException {

        String request = this.objectMapper.writeValueAsString(reviewRequest);

        ResponseEntity<Void> response =
                this.restTemplate.exchange(
                        gatewayIp + DEFAULT_PRODUCT + productId + DEFAULT_REVIEW + "/" + reviewId,
                        HttpMethod.PUT,
                        new HttpEntity<>(request, buildHeaders()),
                        Void.class);

        checkResponseUri(response);
    }

    @Override
    public void deleteReview(final Long productId, final Long reviewId, final MemberInfo memberInfo) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());

        ResponseEntity<Void> response =
                restTemplate.exchange(gatewayIp + DEFAULT_PRODUCT + productId + DEFAULT_REVIEW + "/" + reviewId,
                                      HttpMethod.DELETE,
                                      requestEntity,
                                      Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void makeBestReview(Long productId, Long reviewId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());

        ResponseEntity<String> response =
                restTemplate.exchange(gatewayIp + ADMIN_DEFAULT_PRODUCT + productId + DEFAULT_REVIEW + "/" + reviewId + "/make-best",
                                      HttpMethod.POST,
                                      requestEntity,
                                      String.class);

        log.info(response.getBody());

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
