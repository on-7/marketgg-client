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
    private static final String CATEGORIES = "/categories/";

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
    public List<PostResponse> retrievePostList(final String categoryId, final Integer page) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
            gateWayIp + USER + CATEGORIES + categoryId + "?page=" + page,
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postId, final String categoryId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<PostResponseForDetail> response = restTemplate.exchange(
            gateWayIp + USER + "/" + postId,
            HttpMethod.GET,
            requestEntity,
            PostResponseForDetail.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<PostResponse> searchForCategory(final String categoryId, final SearchRequest searchRequest) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
            gateWayIp + USER + CATEGORIES + categoryId + "/search?keyword=" + searchRequest.getKeyword() + "&page=" + searchRequest.getPage(),
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<PostResponse> searchForOption(final String categoryId, final SearchRequest searchRequest,
                                              final String optionType, final String option) {

        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
            gateWayIp + ADMIN + CATEGORIES + categoryId + "/options/" + optionType + "/search?option=" + option + "&keyword=" + searchRequest.getKeyword() + "&page=" + searchRequest.getPage(),
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void updatePost(final Long postId, final PostRequest postRequest, final String categoryId)
            throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(postRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
            gateWayIp + ADMIN + CATEGORIES + categoryId + "/" + postId,
            HttpMethod.PUT,
            requestEntity,
            Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void deletePost(final Long postId, final String categoryId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
            gateWayIp + USER + CATEGORIES + categoryId + "/" + postId,
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
    public void changeStatus(Long postId, PostStatusUpdateRequest postRequest) throws JsonProcessingException {
        String requestBody = objectMapper.writeValueAsString(postRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
            gateWayIp + ADMIN + "/" + postId,
            HttpMethod.PATCH,
            requestEntity,
            Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<String> retrieveStatus() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<String>> response = restTemplate.exchange(
            gateWayIp + ADMIN + "/status",
            HttpMethod.GET,
            requestEntity,
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

}
