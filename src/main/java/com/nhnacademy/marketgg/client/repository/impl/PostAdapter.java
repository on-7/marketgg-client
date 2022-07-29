package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostAdapter implements PostRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT = "/shop/v1/customer-services";

    @Override
    public void createPost(final PostRequest postRequest, final String type) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(postRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + DEFAULT + "/" + type,
                HttpMethod.POST,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<PostResponse> retrievesPosts(final Pageable pageable, final String type) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(pageable);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                gateWayIp + DEFAULT + "/" + type,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public PostResponse retrievePost(final Long boardNo, final String type) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<PostResponse> response = restTemplate.exchange(
                gateWayIp + DEFAULT + "/" + type + "/" + boardNo,
                HttpMethod.GET,
                requestEntity,
                PostResponse.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long boardNo, final String type, final Pageable pageable) throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(pageable);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<PostResponseForOtoInquiry> response = restTemplate.exchange(
                gateWayIp + DEFAULT + "/" + type + "/" + boardNo,
                HttpMethod.GET,
                requestEntity,
                PostResponseForOtoInquiry.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void updatePost(final Long boardNo, final PostRequest postRequest, final String type) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + DEFAULT + "/" + type + "/" + boardNo,
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void deletePost(final Long boardNo, final String type) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + DEFAULT + "/" + type + "/" + boardNo,
                HttpMethod.DELETE,
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