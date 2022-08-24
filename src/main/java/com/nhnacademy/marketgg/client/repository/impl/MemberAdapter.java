package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.ShopResult;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.request.DeliveryAddressUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.request.SignupRequest;
import com.nhnacademy.marketgg.client.dto.response.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ResponseUtils;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.repository.MemberRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
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

    private final RedisTemplate redisTemplate;

    private static final String DEFAULT_MEMBER = "/shop/v1/members/";
    private static final String DELIVERY_ADDRESS = "/delivery-address";

    @Override
    public void signup(final SignupRequest signupRequest)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<SignupRequest> response = new HttpEntity<>(signupRequest, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + "signup", HttpMethod.POST, response,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(exchange);
    }

    @Override
    public void withdraw()
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<MemberWithdrawRequest> response = new HttpEntity<>(deletedAt, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.DELETE, response,
                new ParameterizedTypeReference<>() {
                });

        //TODO : sessionId 알아내서 레디스에서 지우기.
        //redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);

        this.checkResponseUri(exchange);
    }

    @Override
    public void update(final MemberUpdateRequest memberUpdateRequest)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<MemberUpdateRequest> response =
            new HttpEntity<>(memberUpdateRequest, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.PUT, response,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(exchange);
    }

    // @Override
    // public MemberUpdateToAuthResponse update(final MemberUpdateRequest memberUpdateRequest) {
    //     log.info("update: start");
    //     HttpHeaders httpHeaders = getHttpHeaders();
    //     httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    //
    //     HttpEntity<MemberUpdateRequest> httpEntity =
    //         new HttpEntity<>(memberUpdateRequest, httpHeaders);
    //     ResponseEntity<MemberUpdateToAuthResponse> response =
    //         restTemplate.exchange(requestUrl + "/auth/v1/members/info", HttpMethod.PUT, httpEntity,
    //             MemberUpdateToAuthResponse.class
    //         );
    //
    //     HttpHeaders headers = response.getHeaders();
    //
    //     List<String> jwtHeader = headers.get(AUTHORIZATION);
    //     List<String> expiredHeader = headers.get(JwtInfo.JWT_EXPIRE);
    //
    //     if (Objects.isNull(jwtHeader) || Objects.isNull(expiredHeader)) {
    //         throw new LoginFailException();
    //     }
    //
    //     String jwt = jwtHeader.get(0);
    //     String expireAt = expiredHeader.get(0);
    //
    //     //TODO: sessionId 알아내서 토큰 정보 갱신하기.
    //     //JwtInfo.saveJwt(redisTemplate, sessionId, jwt, expireAt);
    //
    //     if (Objects.isNull(response.getBody())) {
    //         throw new ServerException();
    //     }
    //
    //     return response.getBody();
    // }

    @Override
    public List<DeliveryAddressResponse> retrieveDeliveryAddresses()
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<DeliveryAddressResponse> requestEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<ShopResult<DeliveryAddressResponse>> response = this.restTemplate.exchange(
            gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS,
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);

        return Collections.singletonList(Objects.requireNonNull(response.getBody()).getData());
    }

    @Override
    public void createDeliveryAddress(final DeliveryAddressCreateRequest addressRequest)
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<DeliveryAddressCreateRequest> response = new HttpEntity<>(addressRequest, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS, HttpMethod.POST, response,
                new ParameterizedTypeReference<ShopResult<Void>>() {
                });

        this.checkResponseUri(exchange);
    }

    @Override
    public void updateDeliveryAddress(final DeliveryAddressUpdateRequest updateRequest)
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<DeliveryAddressUpdateRequest> response = new HttpEntity<>(updateRequest, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS, HttpMethod.PUT, response,
                new ParameterizedTypeReference<ShopResult<Void>>() {
                });

        this.checkResponseUri(exchange);
    }

    @Override
    public void deleteDeliveryAddress(final Long deliveryAddressId)
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<Long> response = new HttpEntity<>(deliveryAddressId, buildHeaders());
        ResponseEntity<ShopResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS + "/" + deliveryAddressId,
                HttpMethod.DELETE, response,
                new ParameterizedTypeReference<ShopResult<Void>>() {
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
