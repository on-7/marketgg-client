package com.nhnacademy.marketgg.client.repository.impl;

import com.nhnacademy.marketgg.client.dto.request.EmailRequest;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.annotation.NoAuth;
import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.dto.request.MemberSignupToAuth;
import com.nhnacademy.marketgg.client.dto.request.MemberUpdateToAuth;
import com.nhnacademy.marketgg.client.dto.request.MemberWithdrawRequest;
import com.nhnacademy.marketgg.client.dto.response.EmailExistResponse;
import com.nhnacademy.marketgg.client.dto.response.EmailUseResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberSignupResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberUpdateToAuthResponse;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.LogoutException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Client Server 와 Auth Server 사이에서 데이터를 주고 받습니다.
 *
 * @version 1.0.0
 */
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthAdapter implements AuthRepository {

    @Value("${marketgg.gateway-origin}")
    private String requestUrl;

    private final RestTemplate restTemplate;
    private final RedisTemplate<String, JwtInfo> redisTemplate;

    @NoAuth
    @Override
    public void doLogin(final LoginRequest loginRequest, final String sessionId) {
        log.info("login start");
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(loginRequest, httpHeaders);

        ResponseEntity<Void> response =
                restTemplate.postForEntity(requestUrl + "/auth/login", httpEntity, Void.class);
            restTemplate.postForEntity(requestUrl + "/auth/login", httpEntity, Void.class);

        this.checkStatus(response);

        HttpHeaders headers = response.getHeaders();

        String jwt = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (jwt.startsWith("Bearer")) {
            jwt = jwt.substring(7);
        }
        String expire = Objects.requireNonNull(headers.get(JwtInfo.JWT_EXPIRE)).get(0);

        JwtInfo jwtInfo = new JwtInfo(jwt, expire);
        Instant instant = jwtInfo.getJwtExpireDate()
                                 .plus(6, ChronoUnit.DAYS)
                                 .plus(30, ChronoUnit.MINUTES)
                                 .atZone(ZoneId.systemDefault())
                                 .toInstant();
        Date expireDate = Date.from(instant);

        log.info("login success: {}", jwtInfo.getJwt());
        redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, jwtInfo);
        redisTemplate.expireAt(sessionId, expireDate);
    }

    @Override
    public MemberSignupResponse signup(final MemberSignupToAuth memberSignupToAuth) {
        log.info("signup: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<MemberSignupToAuth> httpEntity = new HttpEntity<>(memberSignupToAuth, headers);
        ResponseEntity<MemberSignupResponse> exchange = restTemplate.exchange(requestUrl + "/member/v1/members/signup"
                , HttpMethod.POST
                , httpEntity
                , MemberSignupResponse.class
        );
        log.info("signup success: {}", exchange.getStatusCode());

        return Objects.requireNonNull(exchange.getBody());
    }

    @Override
    public EmailExistResponse checkEmail(final EmailRequest emailRequest) {
        log.info("checkEmail: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);
        ResponseEntity<EmailExistResponse> exchange = restTemplate.exchange(requestUrl + "/auth/check/email"
                , HttpMethod.POST
                , httpEntity
                , EmailExistResponse.class
        );
        log.info("email exist {}", exchange.getBody());
        return Objects.requireNonNull(exchange.getBody());
    }

    @Override
    public EmailUseResponse useEmail(final EmailRequest emailRequest) {
        log.info("checkEmail: start");
        HttpHeaders headers = getHttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<EmailRequest> httpEntity = new HttpEntity<>(emailRequest, headers);
        ResponseEntity<EmailUseResponse> exchange = restTemplate.exchange(requestUrl + "/auth/use/email"
                , HttpMethod.POST
                , httpEntity
                , EmailUseResponse.class
        );
        log.info("email exist {}", exchange.getBody());

        return Objects.requireNonNull(exchange.getBody());
    }

    @Override
    public void withdraw(final MemberWithdrawRequest memberWithdrawRequest, final String sessionId) {
        log.info("withdraw: start");
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LocalDateTime> httpEntity = new HttpEntity<>(deletedAt, httpHeaders);
        ResponseEntity<Void> response = restTemplate.exchange(requestUrl + "/auth/info"
                , HttpMethod.DELETE
                , httpEntity
                , Void.class
        );
        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
        log.info("withdraw success: {}", response.getStatusCode());

    }

    @Override
    public MemberUpdateToAuthResponse update(final MemberUpdateToAuth memberUpdateToAuth, final String sessionId) {
        log.info("update: start");
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MemberUpdateToAuth> httpEntity = new HttpEntity<>(memberUpdateToAuth, httpHeaders);
        ResponseEntity<MemberUpdateToAuthResponse> response = restTemplate.exchange(requestUrl + "/auth/info"
                , HttpMethod.PUT
                , httpEntity
                , MemberUpdateToAuthResponse.class
        );

        HttpHeaders headers = response.getHeaders();
        String jwt = headers.get(AUTHORIZATION).get(0);
        String expire = Objects.requireNonNull(headers.get(JwtInfo.JWT_EXPIRE)).get(0);
        if (Objects.isNull(jwt) || Objects.isNull(expire)) {
            // TODO: Merge 후 예외 바꾸기
            throw new LoginFailException();
        }

        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);

        JwtInfo jwtInfo = new JwtInfo(jwt, expire);
        Instant instant = jwtInfo.getJwtExpireDate()
                                 .plus(6, ChronoUnit.DAYS)
                                 .plus(30, ChronoUnit.MINUTES)
                                 .atZone(ZoneId.systemDefault())
                                 .toInstant();
        Date expireDate = Date.from(instant);

        log.info("login success: {}", jwtInfo.getJwt());
        redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, jwtInfo);
        redisTemplate.expireAt(sessionId, expireDate);

        log.info("update success: {}", response.getStatusCode());
        if (Objects.isNull(response.getBody())) {
            throw new ServerException();
        }

        return response.getBody();
    }

    private void checkStatus(ResponseEntity<?> response) {
        if (response.getStatusCode().is4xxClientError()) {
            log.info("Login Fail - http status: {}", response.getStatusCode());
            throw new LoginFailException();
        }

        if (response.getStatusCode().is5xxServerError()) {
            log.info("Login Fail - http status: {}", response.getStatusCode());
            throw new ServerException();
        }

        if (Objects.isNull(response.getHeaders().get(AUTHORIZATION))
                || Objects.isNull(response.getHeaders().get(JwtInfo.JWT_EXPIRE))) {
            log.info("Empty header");
            throw new LoginFailException();
        }
    }

    private HttpHeaders getHttpHeaders() {
        return new HttpHeaders();
    }

    @Override
    public void logout(String sessionId) {
        if (Objects.isNull(redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY))) {
            log.info("already logout");
            return;
        }

        ResponseEntity<Void> forEntity =
            restTemplate.getForEntity(requestUrl + "/auth/logout", Void.class);

        HttpStatus statusCode = forEntity.getStatusCode();

        if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
            throw new LogoutException(statusCode);
        }

        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
    }

}
