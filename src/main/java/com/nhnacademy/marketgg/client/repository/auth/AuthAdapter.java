package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.annotation.NoAuth;
import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client Server 와 Auth Server 사이에서 데이터를 주고 받습니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthRepository {

    @Value("${gg.gateway-origin}")
    private String requestUrl;

    private final RestTemplate restTemplate;

    @NoAuth
    @Override
    public ResponseEntity<Void> doLogin(final LoginRequest loginRequest, final String sessionId) {
        log.debug("login start");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(loginRequest, httpHeaders);

        return restTemplate.postForEntity(requestUrl + "/auth/v1/members/login", httpEntity, Void.class);
    }

    @Override
    public ResponseEntity<CommonResult<String>> logout(String sessionId) {
        return restTemplate.exchange(requestUrl + "/auth/v1/members/logout", HttpMethod.GET, null,
            new ParameterizedTypeReference<>() {});
    }

}
