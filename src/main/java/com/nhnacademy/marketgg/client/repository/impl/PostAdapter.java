package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.repository.PostRepository;

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

@Slf4j
@Component
@RequiredArgsConstructor
public class PostAdapter implements PostRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String USER = "/shop/v1/customer-services";
    private static final String ADMIN = "/shop/v1/admin/customer-services";

    @Override
    public void createPost(final PostRequest postRequest) throws JsonProcessingException {

        String request = objectMapper.writeValueAsString(postRequest);
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + USER,
                HttpMethod.POST,
                new HttpEntity<>(request, this.buildHeaders()),
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<PostResponse> retrievesPostList(final String categoryCode, final Integer page) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                gateWayIp + USER + "/categories/" + categoryCode + "?page=" + page,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postNo, final String categoryCode) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<PostResponseForDetail> response = restTemplate.exchange(
                gateWayIp + USER + "/" + postNo,
                HttpMethod.GET,
                requestEntity,
                PostResponseForDetail.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<PostResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest)
            throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(searchRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                gateWayIp + USER + "/categories/" + categoryCode + "/search",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<PostResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
                                                     final String optionType, final String option)
            throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(searchRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                gateWayIp + ADMIN + "/categories/" + categoryCode + "/options/" + optionType + "/search?option=" + option,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void updatePost(final Long postNo, final PostRequest postRequest, final String categoryCode) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + ADMIN + "/categories/" + categoryCode + "/" + postNo,
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void deletePost(final Long postNo, final String categoryCode) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + USER + "/categories/" + categoryCode + "/" + postNo,
                HttpMethod.DELETE,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<String> retrieveReason() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<String>> response = restTemplate.exchange(
                gateWayIp + USER + "/reasons",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void changeStatus(Long boardNo, PostStatusUpdateRequest postRequest) throws JsonProcessingException {
        String requestBody = objectMapper.writeValueAsString(postRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + ADMIN + "/" + boardNo,
                HttpMethod.PATCH,
                requestEntity,
                Void.class);

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
