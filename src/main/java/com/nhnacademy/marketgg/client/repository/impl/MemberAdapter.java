package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class MemberAdapter implements MemberRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;

    private static final String DEFAULT_MEMBER = "/shop/v1/members/";

    @Override
    public LocalDateTime retrievePassUpdatedAt(final Long id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(buildHeaders());
        ResponseEntity<LocalDateTime>
                response = restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + id + "/ggpass",
                                                 HttpMethod.GET,
                                                 requestEntity,
                                                 LocalDateTime.class);
        checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void subscribePass(final Long id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Void> response =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + id + "/ggpass/subscribe",
                                      HttpMethod.POST,
                                      requestEntity,
                                      Void.class);
        checkResponseUri(response);
    }

    @Override
    public void withdrawPass(final Long id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(buildHeaders());
        ResponseEntity<Void> response =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + id + "/ggpass/withdraw",
                                      HttpMethod.POST,
                                      requestEntity,
                                      Void.class);
        checkResponseUri(response);
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
