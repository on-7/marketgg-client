package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequestForCategory;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.PostRepository;
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
public class PostAdapter implements PostRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String USER = "/shop/v1/customer-services";
    private static final String ADMIN = "/shop/v1/admin/customer-services";
    private static final String CATEGORIES = "/categories/";

    @Override
    public void createPost(final PostRequest postRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String request = objectMapper.writeValueAsString(postRequest);
        ResponseEntity<ShopResult<Void>> response = restTemplate.exchange(
                gateWayIp + USER,
                HttpMethod.POST,
                new HttpEntity<>(request, this.buildHeaders()),
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
    }

    @Override
    public List<PostResponse> retrievePostList(final String categoryId, final Integer page)
            throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<List<PostResponse>>> response = restTemplate.exchange(
                gateWayIp + USER + CATEGORIES + categoryId + "?page=" + page,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postId, final String categoryId)
            throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<PostResponseForDetail>> response = restTemplate.exchange(
                gateWayIp + USER + "/" + postId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<PostResponse> searchForCategory(final SearchRequestForCategory searchRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String requestBody = objectMapper.writeValueAsString(searchRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<ShopResult<List<PostResponse>>> response = restTemplate.exchange(
                gateWayIp + USER + CATEGORIES + searchRequest.getCategoryCode() + "/search",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public List<PostResponse> searchForOption(final SearchRequestForCategory searchRequest,
                                              final String optionType, final String option)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String requestBody = objectMapper.writeValueAsString(searchRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<ShopResult<List<PostResponse>>> response = restTemplate.exchange(
                gateWayIp + ADMIN + CATEGORIES + searchRequest.getCategoryCode() + "/options/" + optionType +
                        "/search?option=" + option,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void updatePost(final Long postId, final PostRequest postRequest, final String categoryId)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String requestBody = objectMapper.writeValueAsString(postRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response = restTemplate.exchange(
                gateWayIp + ADMIN + CATEGORIES + categoryId + "/" + postId,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
    }

    @Override
    public void deletePost(final Long postId, final String categoryId)
            throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response = restTemplate.exchange(
                gateWayIp + USER + CATEGORIES + categoryId + "/" + postId,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
    }

    @Override
    public List<String> retrieveReason() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<List<String>>> response = restTemplate.exchange(
                gateWayIp + USER + "/reasons",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void changeStatus(final Long postId, final PostStatusUpdateRequest postRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String requestBody = objectMapper.writeValueAsString(postRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response = restTemplate.exchange(
                gateWayIp + ADMIN + "/" + postId + "/status",
                HttpMethod.PATCH,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
    }

    @Override
    public List<String> retrieveStatus() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<List<String>>> response = restTemplate.exchange(
                gateWayIp + USER + "/status",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponse(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponse(final ResponseEntity<ShopResult<T>> response)
            throws UnAuthenticException, UnAuthorizationException {

        ResponseUtils.checkErrorForResponse(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
