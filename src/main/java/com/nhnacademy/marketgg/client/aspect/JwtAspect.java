package com.nhnacademy.marketgg.client.aspect;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 인증을 처리하기 위해 사용되는 AOP 입니다.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order(-1)
@Component
@RequiredArgsConstructor
public class JwtAspect {

    @Value("${gg.gateway-origin}")
    private String gatewayOrigin;

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final RestTemplate restTemplate;

    /**
     * JWT 가 만료되었을 때 JWT 갱신을 인증서버에 요청합니다.
     */
    @Before("within(com.nhnacademy.marketgg.client.repository.impl.*)"
        + " && !@target(com.nhnacademy.marketgg.client.annotation.NoAuth)")
    public void tokenRefresh() {
        log.info("Jwt Aspect");

        if (SessionContext.get().isEmpty()) {
            return;
        }

        String sessionId = SessionContext.get().get();

        JwtInfo jwtInfo =
            (JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY);

        if (Objects.isNull(jwtInfo)) {
            return;
        }

        LocalDateTime jwtExpireDate = jwtInfo.getJwtExpireDate();
        LocalDateTime now = LocalDateTime.now().withNano(0);

        if (jwtExpireDate.isAfter(now)) {
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtInfo.getJwt());

        HttpEntity<Void> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<Void> response
            = restTemplate.exchange(gatewayOrigin + "/auth/refresh", HttpMethod.GET, httpEntity,
            Void.class);

        if (this.isValid(response)) {
            log.info("Token Refresh");
            HttpHeaders responseHeaders = response.getHeaders();

            String jwt = Objects.requireNonNull(responseHeaders.get(AUTHORIZATION)).get(0);
            String expire = Objects.requireNonNull(responseHeaders.get(JwtInfo.JWT_EXPIRE)).get(0);

            JwtInfo newJwtInfo = new JwtInfo(jwt, expire);
            Instant instant = newJwtInfo.getJwtExpireDate()
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant();
            Date expireDate = Date.from(instant);

            redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
            redisTemplate.opsForHash().put(sessionId, JwtInfo.JWT_REDIS_KEY, newJwtInfo);
            redisTemplate.expireAt(sessionId, expireDate);
        }

    }

    private boolean isValid(ResponseEntity<?> response) {
        if (response.getStatusCode().is4xxClientError()) {
            log.info("Login Fail - http status: {}", response.getStatusCode());
            return false;
        }

        if (response.getStatusCode().is5xxServerError()) {
            log.info("Login Fail - http status: {}", response.getStatusCode());
            return false;
        }

        if (Objects.isNull(response.getHeaders().get(AUTHORIZATION))
            || Objects.isNull(response.getHeaders().get(JwtInfo.JWT_EXPIRE))) {
            log.info("Empty header");
            return false;
        }

        return true;
    }

}
