package com.nhnacademy.marketgg.client.repository.member;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.PageResult;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryAddressCreateRequest;
import com.nhnacademy.marketgg.client.dto.delivery.DeliveryAddressResponse;
import com.nhnacademy.marketgg.client.dto.member.LoginRequest;
import com.nhnacademy.marketgg.client.dto.member.MemberUpdateRequest;
import com.nhnacademy.marketgg.client.dto.member.SignupRequest;
import com.nhnacademy.marketgg.client.dto.response.AdminMemberResponse;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.util.ResponseUtils;
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

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String DEFAULT_MEMBER = "/shop/v1/members";
    private static final String DELIVERY_ADDRESS = "/delivery-address";
    private static final String DELIVERY_ADDRESSES = "/delivery-addresses";
    private static final String SIGNUP = "/signup";

    @Override
    public void signup(final SignupRequest signupRequest)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<SignupRequest> response = new HttpEntity<>(signupRequest, buildHeaders());
        ResponseEntity<CommonResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + SIGNUP, HttpMethod.POST, response,
                new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(exchange);
    }

    @Override
    public void withdraw(final LoginRequest loginRequest)
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest, buildHeaders());
        ResponseEntity<CommonResult<Void>> response =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.DELETE, request,
                new ParameterizedTypeReference<>() {
                });

        String sessionId = SessionContext.getSessionId();
        if (Objects.isNull(sessionId)) {
            throw new UnAuthenticException();
        }

        redisTemplate.opsForHash()
                     .delete(sessionId, JwtInfo.JWT_REDIS_KEY);

        this.checkResponseUri(response);
    }

    @Override
    public void update(final MemberUpdateRequest memberUpdateRequest, MemberInfo memberInfo)
        throws UnAuthenticException, UnAuthorizationException {


        HttpEntity<MemberUpdateRequest> request =
            new HttpEntity<>(memberUpdateRequest, buildHeaders());
        ResponseEntity<CommonResult<Void>> response =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER, HttpMethod.PUT, request,
                new ParameterizedTypeReference<>() {
                });

        HttpHeaders headers = response.getHeaders();

        List<String> jwtHeader = headers.get(AUTHORIZATION);
        List<String> expiredHeader = headers.get(JwtInfo.JWT_EXPIRE);

        if (Objects.isNull(jwtHeader) || Objects.isNull(expiredHeader)) {
            throw new LoginFailException();
        }

        String jwt = jwtHeader.get(0);
        String expireAt = expiredHeader.get(0);

        String sessionId = SessionContext.getSessionId();
        if (Objects.isNull(sessionId)) {
            throw new UnAuthenticException();
        }

        JwtInfo.saveJwt(redisTemplate, memberInfo, sessionId, jwt, expireAt);

        if (Objects.isNull(response.getBody())) {
            throw new ServerException();
        }

        this.checkResponseUri(response);
    }

    @Override
    public List<DeliveryAddressResponse> retrieveDeliveryAddresses()
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<Void> requestEntity = new HttpEntity<>(buildHeaders());

        ResponseEntity<CommonResult<List<DeliveryAddressResponse>>> response = this.restTemplate.exchange(
            gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESSES,
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public void createDeliveryAddress(final DeliveryAddressCreateRequest createRequest)
        throws UnAuthenticException, UnAuthorizationException {
        HttpEntity<DeliveryAddressCreateRequest> response = new HttpEntity<>(createRequest, buildHeaders());

        ResponseEntity<CommonResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS, HttpMethod.POST, response,
                new ParameterizedTypeReference<>() {
                });


        this.checkResponseUri(exchange);
    }

    @Override
    public void deleteDeliveryAddress(final Long deliveryAddressId)
        throws UnAuthenticException, UnAuthorizationException {

        HttpEntity<Long> response = new HttpEntity<>(deliveryAddressId, buildHeaders());
        ResponseEntity<CommonResult<Void>> exchange =
            restTemplate.exchange(gateWayIp + DEFAULT_MEMBER + DELIVERY_ADDRESS + "/" + deliveryAddressId,
                HttpMethod.DELETE, response, new ParameterizedTypeReference<>() {
                });

        this.checkResponseUri(exchange);
    }

    @Override
    public PageResult<AdminMemberResponse> retrieveMembers(int page) {
        HttpEntity<Void> httpEntity = new HttpEntity<>(buildHeaders());
        String requestUrl = gateWayIp + "/shop/v1/admin/members?page=" + page;


        ResponseEntity<CommonResult<PageResult<AdminMemberResponse>>> response =
            restTemplate.exchange(requestUrl, GET, httpEntity, new ParameterizedTypeReference<>() {
            });

        this.checkResponseUri(response);

        return Objects.requireNonNull(response.getBody()).getData();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

    private <T> void checkResponseUri(final ResponseEntity<CommonResult<T>> response)
        throws UnAuthenticException, UnAuthorizationException {

        ResponseUtils.checkError(response);
        log.info(String.valueOf(response.getHeaders().getLocation()));
    }

}
