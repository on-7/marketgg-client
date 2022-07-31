package com.nhnacademy.marketgg.client.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.request.CategoryCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.CommentRequest;
import com.nhnacademy.marketgg.client.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CommentAdapter implements CommentRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_COMMENT = "/shop/v1/customer-services/oto-inquiries";

    @Override
    public void createComment(final Long csPostNo, final Long memberId, final CommentRequest commentRequest)
            throws JsonProcessingException {
        String request = objectMapper.writeValueAsString(commentRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(
                gateWayIp + DEFAULT_COMMENT + csPostNo + "/members" + memberId + "/comments",
                HttpMethod.POST,
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
