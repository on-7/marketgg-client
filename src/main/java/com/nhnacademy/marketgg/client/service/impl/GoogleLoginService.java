package com.nhnacademy.marketgg.client.service.impl;

import com.nhnacademy.marketgg.client.oauth2.GoogleProfile;
import com.nhnacademy.marketgg.client.service.OAuth2Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleLoginService implements OAuth2Service {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-key}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    @Value("${google.login-request-url}")
    private String loginRequestUrl;

    private final RestTemplate restTemplate;

    @Override
    public String getRedirectUrl() {
        String scope = "https://www.googleapis.com/auth/userinfo.profile "
        + "https://www.googleapis.com/auth/userinfo.email ";

        return "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + clientId
            + "&redirect_uri=" + redirectUri + "&scope=" + scope + "&response_type=code";
    }

    @Override
    public String getToken(String code) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> tokenRequest =
            new HttpEntity<>(parameters, headers);

        ResponseEntity<GoogleProfile> profileResponse =
            restTemplate.exchange(loginRequestUrl, HttpMethod.POST, tokenRequest,
                GoogleProfile.class);

        log.info("google result = {}", profileResponse.getBody());

        return Optional.ofNullable(profileResponse.getBody())
                       .orElseThrow().toString();
    }

}
