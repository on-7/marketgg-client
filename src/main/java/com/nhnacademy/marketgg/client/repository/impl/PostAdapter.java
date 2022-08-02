package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.request.PostRequest;
import com.nhnacademy.marketgg.client.dto.request.PostStatusUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.SearchRequest;
import com.nhnacademy.marketgg.client.dto.response.PostResponse;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForDetail;
import com.nhnacademy.marketgg.client.dto.response.PostResponseForOtoInquiry;
import com.nhnacademy.marketgg.client.dto.response.SearchBoardResponse;
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
    public void createPost(final PostRequest postRequest, final String role)
            throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(postRequest);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role),
                HttpMethod.POST,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<PostResponse> retrievesPostList(final Integer page, final String type, final String role) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        log.error(gateWayIp + this.checkAdmin(role) + "/categories/" + type + "?page=" + page);
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/categories/" + type + "?page=" + page,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<PostResponse> retrievesPostListForMe(final Integer page, final String type) {

        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<PostResponse>> response = restTemplate.exchange(
                gateWayIp + USER + "/" + type + "?page=" + page,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public PostResponseForDetail retrievePost(final Long postNo, final String type, final String role) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<PostResponseForDetail> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/" + postNo,
                HttpMethod.GET,
                requestEntity,
                PostResponseForDetail.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public PostResponseForOtoInquiry retrievePostForOtoInquiry(final Long postNo, final String type,
                                                               final String role) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<PostResponseForOtoInquiry> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/" + type + "/" + postNo,
                HttpMethod.GET,
                requestEntity,
                PostResponseForOtoInquiry.class);

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<SearchBoardResponse> searchForCategory(final String categoryCode, final SearchRequest searchRequest,
                                                       final String role)
            throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(searchRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<List<SearchBoardResponse>> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/categories/" + categoryCode + "/search",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public List<SearchBoardResponse> searchForOption(final String categoryCode, final SearchRequest searchRequest,
                                                     final String optionValue, final String option, final String role)
            throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(searchRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, this.buildHeaders());
        ResponseEntity<List<SearchBoardResponse>> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/categories" + categoryCode + "/search/" + option + "/" + optionValue,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void updatePost(final Long postNo, final PostRequest postRequest, final String type, final String role) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/" + type + "/" + postNo,
                HttpMethod.PUT,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void deletePost(final Long postNo, final String type, final String role) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + this.checkAdmin(role) + "/" + type + "/" + postNo,
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
                gateWayIp + ADMIN + "/oto-inquiries/" + boardNo,
                HttpMethod.PATCH,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    private String checkAdmin(final String role) {
        if(role.compareTo("admin") == 0) {
            return ADMIN;
        }
        return USER;
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
