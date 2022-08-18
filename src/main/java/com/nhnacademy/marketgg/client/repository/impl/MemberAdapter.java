package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupToShopMember;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Collections;
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

/**
 * Client Server 와 Shop Server 사이에서 데이터를 주고 받습니다.
 *
 * @author 김훈민, 박세완
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAdapter implements MemberRepository {

    private final String gateWayIp;
    private final RestTemplate restTemplate;

    private static final String DEFAULT_MEMBER = "/shop/v1/members/";
    private static final String DELIVERY_ADDRESS = "/delivery-address";

    @Override
    public LocalDateTime retrievePassUpdatedAt() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<LocalDateTime> response =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass", HttpMethod.GET, requestEntity,
                LocalDateTime.class);

        this.checkResponseUri(response);

        return response.getBody();
    }

    @Override
    public void subscribePass() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass/subscribe", HttpMethod.POST, requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void withdrawPass() {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<Void> response =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass/withdraw", HttpMethod.POST, requestEntity,
                Void.class);

        this.checkResponseUri(response);
    }

    @Override
    public void signup(final MemberSignupToShopMember signupRequestToShopMember) {
        HttpEntity<MemberSignupToShopMember> response = new HttpEntity<>(signupRequestToShopMember, buildHeaders());
        ResponseEntity<Void> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/signup", HttpMethod.POST, response, Void.class);

        this.checkResponseUri(exchange);
    }

    @Override
    public void withdraw(final MemberWithdrawRequest deletedAt) {
        HttpEntity<MemberWithdrawRequest> response = new HttpEntity<>(deletedAt, buildHeaders());
        ResponseEntity<Void> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.DELETE, response, Void.class);

        this.checkResponseUri(exchange);
    }

    @Override
    public void update(final MemberUpdateToAuthResponse memberUpdateToAuthResponse, String sessionId) {
        HttpEntity<MemberUpdateToAuthResponse> response = new HttpEntity<>(memberUpdateToAuthResponse, buildHeaders());
        ResponseEntity<Void> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.PUT, response, Void.class);

        this.checkResponseUri(exchange);
    }

    @Override
    public List<DeliveryAddressResponse> retrieveDeliveryAddresses() {
        HttpEntity<DeliveryAddressResponse> requestEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<DeliveryAddressResponse> response = this.restTemplate.exchange(
            gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS,
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);

        return Collections.singletonList(response.getBody());
    }

    @Override
    public void createDeliveryAddress(final DeliveryAddressCreateRequest addressRequest) {
        HttpEntity<DeliveryAddressCreateRequest> response = new HttpEntity<>(addressRequest, buildHeaders());
        ResponseEntity<Void> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS, HttpMethod.POST, response,
                Void.class);

        this.checkResponseUri(exchange);
    }

    @Override
    public void updateDeliveryAddress(final DeliveryAddressUpdateRequest updateRequest) {
        HttpEntity<DeliveryAddressUpdateRequest> response = new HttpEntity<>(updateRequest, buildHeaders());
        ResponseEntity<Void> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS, HttpMethod.PUT, response,
                Void.class);

        this.checkResponseUri(exchange);
    }

    @Override
    public void deleteDeliveryAddress(final Long deliveryAddressId) {
        HttpEntity<Long> response = new HttpEntity<>(deliveryAddressId, buildHeaders());
        ResponseEntity<Void> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS + "/" + deliveryAddressId,
                HttpMethod.DELETE, response,
                Void.class);

        this.checkResponseUri(exchange);
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
