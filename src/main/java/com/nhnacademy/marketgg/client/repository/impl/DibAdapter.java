package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.dto.response.DibRetrieveResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.DibRepository;
import java.util.List;
import java.util.Objects;
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
    public void createDib(final Long productId) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response =
                restTemplate.exchange(gateWayIp + "/shop/v1/members/dibs/" + productId,
                                      HttpMethod.POST,
                                      requestEntity,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(response);
    }

    @Override
    public List<DibRetrieveResponse> retrieveDibs() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<List<DibRetrieveResponse>>> response =
                restTemplate.exchange(gateWayIp + "/shop/v1/members/dibs",
                                      HttpMethod.GET,
                                      requestEntity,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(response);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void deleteDib(final Long productId) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response =
                restTemplate.exchange(gateWayIp + "/shop/v1/members/dibs/" + productId,
                                      HttpMethod.DELETE,
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

    private <T> void checkResponseUri(final ResponseEntity<ShopResult<T>> response)
            throws UnAuthenticException, UnAuthorizationException {

        ResponseUtils.checkErrorForResponse(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
