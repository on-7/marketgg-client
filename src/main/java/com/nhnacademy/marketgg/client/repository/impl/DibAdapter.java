package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.repository.DibRepository;
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
public class DibAdapter implements DibRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;

    @Override
    public void createDib(final Long memberId, final Long productId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response =
            restTemplate.exchange(gateWayIp + this.buildUri(memberId) + "/" + productId,
                HttpMethod.POST,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public List<DibRetrieveResponse> retrieveDibs(final Long id) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<List<DibRetrieveResponse>> response =
            restTemplate.exchange(gateWayIp + this.buildUri(id),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(response);
        return response.getBody();
    }

    @Override
    public void deleteDib(final Long memberId, final Long productId) {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response =
            restTemplate.exchange(gateWayIp + this.buildUri(memberId) + "/" + productId,
                HttpMethod.DELETE,
                requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    private String buildUri(final Long memberId) {
        return "/shop/v1/members/" + memberId + "/dibs";
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
