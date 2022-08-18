package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupToShopMember;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.Collections;
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
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass/subscribe",
                                  HttpMethod.POST,
                                  requestEntity,
                                  new ParameterizedTypeReference<>() {
                                  });

        this.checkResponseUri(response);
    }

    @Override
    public void withdrawPass() throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<String> requestEntity = new HttpEntity<>(this.buildHeaders());
        ResponseEntity<ShopResult<Void>> response =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/ggpass/withdraw",
                                  HttpMethod.POST,
                                  requestEntity,
                                  new ParameterizedTypeReference<>() {
                                  });

        this.checkResponseUri(response);
    }

    @Override
    public void signup(final MemberSignupToShopMember signupRequestToShopMember)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<MemberSignupToShopMember> response =
            new HttpEntity<>(signupRequestToShopMember, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "/signup", HttpMethod.POST, response,
                                  new ParameterizedTypeReference<>() {
                                  });

        this.checkResponseUri(exchange);
    }

    @Override
    public void withdraw(final MemberWithdrawRequest deletedAt)
        throws UnAuthenticException, UnAuthorizationException {
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

        HttpEntity<MemberUpdateToAuthResponse> response =
            new HttpEntity<>(memberUpdateToAuthResponse, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.PUT, response,
                                  new ParameterizedTypeReference<>() {
                                  });

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

    private <T> void checkResponseUri(final ResponseEntity<ShopResult<T>> response)
        throws UnAuthenticException, UnAuthorizationException {

        ResponseUtils.checkErrorForResponse(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
