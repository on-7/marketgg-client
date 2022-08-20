package com.nhnacademy.marketgg.client.aspect;

import static com.nhnacademy.marketgg.client.util.GgUrlUtils.WEEK_SECOND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            return;
        }

        String sessionId = authentication.getName();

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
            = restTemplate.exchange(gatewayOrigin + "/auth/v1/members/token/refresh", HttpMethod.GET, httpEntity,
            Void.class);

        if (this.isInvalid(response)) {
            return;
        }

        log.info("Token Refresh");
        HttpHeaders responseHeaders = response.getHeaders();

        String jwt = Objects.requireNonNull(responseHeaders.get(AUTHORIZATION)).get(0);
        String expireAt = Objects.requireNonNull(responseHeaders.get(JwtInfo.JWT_EXPIRE)).get(0);

        JwtInfo.saveJwt(redisTemplate, sessionId, jwt, expireAt);

        try {
            ServletWebRequest servletContainer =
                Objects.requireNonNull((ServletWebRequest) RequestContextHolder.getRequestAttributes());
            HttpServletResponse httpResponse = Objects.requireNonNull(servletContainer.getResponse());
            Cookie cookie = new Cookie(JwtInfo.SESSION_ID, sessionId);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(WEEK_SECOND);
            httpResponse.addCookie(cookie);
        } catch (NullPointerException e) {
            log.error("JwtAspect Response is Null, {}", e.toString());
        }
    }

    private boolean isInvalid(ResponseEntity<?> response) {
        if (response.getStatusCode().is4xxClientError()) {
            log.info("Login Fail - http status: {}", response.getStatusCode());
            return true;
        }

        if (response.getStatusCode().is5xxServerError()) {
            log.info("Login Fail - http status: {}", response.getStatusCode());
            return true;
        }

        if (Objects.isNull(response.getHeaders().get(AUTHORIZATION))
            || Objects.isNull(response.getHeaders().get(JwtInfo.JWT_EXPIRE))) {
            log.info("Empty header");
            return true;
        }

        return false;
    }

}
