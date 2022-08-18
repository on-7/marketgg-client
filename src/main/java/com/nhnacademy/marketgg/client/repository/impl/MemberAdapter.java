package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupToShopMember;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.MemberRepository;
import java.time.LocalDateTime;
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
public class MemberAdapter implements MemberRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;

    private static final String DEFAULT_MEMBER = "/shop/v1/members/";

    @Override
    public LocalDateTime retrievePassUpdatedAt() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<LocalDateTime>> response =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass", HttpMethod.GET, requestEntity,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void subscribePass() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass/subscribe", HttpMethod.POST, requestEntity,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(response);
    }

    @Override
    public void withdrawPass() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass/withdraw", HttpMethod.POST, requestEntity,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(response);
    }

    @Override
    public void signup(final MemberSignupToShopMember signupRequestToShopMember)
            throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<MemberSignupToShopMember> response = new HttpEntity<>(signupRequestToShopMember, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/signup", HttpMethod.POST, response,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(exchange);
    }

    @Override
    public void withdraw(final MemberWithdrawRequest deletedAt) throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<MemberWithdrawRequest> response = new HttpEntity<>(deletedAt, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.DELETE, response,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(exchange);
    }

    @Override
    public void update(final MemberUpdateToAuthResponse memberUpdateToAuthResponse, String sessionId)
            throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<MemberUpdateToAuthResponse> response = new HttpEntity<>(memberUpdateToAuthResponse, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
                restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.PUT, response,
                                      new ParameterizedTypeReference<>() {
                                      });

        this.checkResponseUri(exchange);
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
