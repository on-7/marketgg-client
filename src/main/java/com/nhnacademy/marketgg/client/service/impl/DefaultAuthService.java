package com.nhnacademy.marketgg.client.service.impl;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.dto.request.LoginRequest;
import com.nhnacademy.marketgg.client.exception.LoginFailException;
import com.nhnacademy.marketgg.client.exception.LogoutException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.repository.AuthRepository;
import com.nhnacademy.marketgg.client.service.AuthService;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 로그인, 로그아웃 로직을 실행하는 구현체입니다.
 * {@link com.nhnacademy.marketgg.client.service.AuthService}
 *
 * @author 윤동열
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, JwtInfo> redisTemplate;

    @Override
    public void doLogin(final LoginRequest loginRequest, final String sessionId) {
        loginRequest.encodePassword(passwordEncoder);
        ResponseEntity<Void> response = authRepository.doLogin(loginRequest, sessionId);

        this.checkStatus(response);

        HttpHeaders headers = response.getHeaders();

        String jwt = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);
        if (jwt.startsWith(JwtInfo.BEARER)) {
            jwt = jwt.substring(7);
        }
        String expire = Objects.requireNonNull(headers.get(JwtInfo.JWT_EXPIRE)).get(0);

        JwtInfo jwtInfo = new JwtInfo(jwt, expire);
        Date expireDate = jwtInfo.localDateTimeToDateForRenewToken(jwtInfo.getJwtExpireDate());

        log.info("login success: {}", jwtInfo.getJwt());
        redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, jwtInfo);
        redisTemplate.expireAt(sessionId, expireDate);
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

    @Override
    public void logout(String sessionId) {
        if (Objects.isNull(redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY))) {
            log.info("already logout");
            return;
        }

        ResponseEntity<Void> response = authRepository.logout(sessionId);

        HttpStatus statusCode = response.getStatusCode();

        if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
            throw new LogoutException(statusCode);
        }

        redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
    }

}
