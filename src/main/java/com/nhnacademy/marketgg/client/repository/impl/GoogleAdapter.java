package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.repository.OauthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleAdapter implements OauthRepository {

    private final RestTemplate restTemplate;

    @Override
    public <T> ResponseEntity<String> getProfile(String loginRequestUrl, HttpEntity<T> httpEntity) {
        return restTemplate.exchange(loginRequestUrl, HttpMethod.POST, httpEntity, String.class);
    }

}
