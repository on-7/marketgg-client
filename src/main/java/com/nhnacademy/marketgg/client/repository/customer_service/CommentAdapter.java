package com.nhnacademy.marketgg.client.repository.customer_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.customer_service.CommentRequest;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.util.ResponseUtils;
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
public class CommentAdapter implements CommentRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String DEFAULT_COMMENT = "/shop/v1/customer-services";

    @Override
    public void createComment(final Long postId, final CommentRequest commentRequest)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        String request = objectMapper.writeValueAsString(commentRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(request, this.buildHeaders());
        ResponseEntity<CommonResult<Void>> response = restTemplate.exchange(
                gateWayIp + DEFAULT_COMMENT + "/" + postId,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponseUri(final ResponseEntity<CommonResult<T>> response)
            throws UnAuthenticException, UnAuthorizationException {
        ResponseUtils.checkError(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }


}
