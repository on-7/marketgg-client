package com.nhnacademy.marketgg.client.repository.auth;

import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
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
    public <T> ResponseEntity<CommonResult<GoogleProfile>> getProfile(String loginRequestUrl,
                                                                      HttpEntity<T> httpEntity) {

        return restTemplate.exchange(loginRequestUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
        });
    }

}
