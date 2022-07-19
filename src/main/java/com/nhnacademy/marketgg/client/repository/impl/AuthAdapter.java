package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.dto.response.LoginResponse;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client Server 와 Auth Server 사이에서 데이터를 주고 받습니다.
 *
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthRepository {

    @Value("${marketgg.gateway-origin}")
    private String authServerUrl;

    private final RestTemplate restTemplate;

    /**
     * 로그인 시 서버에 로그인 정보를 전송합니다.
     *
     * @param loginRequest - 사용자가 입력한 이메일과 비밀번호
     */
    public void doLogin(final LoginRequest loginRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(loginRequest, httpHeaders);

        ResponseEntity<LoginResponse> response =
            restTemplate.postForEntity(authServerUrl + "/auth/login", httpEntity, LoginResponse.class);

        checkStatus(response.getStatusCode());
    }

    private void checkStatus(HttpStatus status) {
        if (status.is4xxClientError()) {
            throw new LoginFailException();
        }

        if (status.is5xxServerError()) {
            throw new ServerException();
        }
    }

}
