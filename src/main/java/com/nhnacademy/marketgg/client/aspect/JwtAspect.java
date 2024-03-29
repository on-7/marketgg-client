package com.nhnacademy.marketgg.client.aspect;

import static com.nhnacademy.marketgg.client.jwt.Role.ROLE_ANONYMOUS;
import static com.nhnacademy.marketgg.client.util.GgUtils.WEEK_SECOND;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.util.GgUtils;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;

    @Pointcut("@within(org.springframework.stereotype.Controller) " +
        "&& execution(* *.*(.., com.nhnacademy.marketgg.client.dto.common.MemberInfo, ..))")
    public void controller() {

    }

    /**
     * JWT 가 만료되었을 때 JWT 갱신을 인증서버에 요청합니다.
     */
    @Before(value = "within(com.nhnacademy.marketgg.client.repository..*)"
        + " || controller()"
        + " && !@target(com.nhnacademy.marketgg.client.annotation.NoAuth)")
    public void refreshToken() {
        log.info("Jwt Aspect");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (GgUtils.hasRole(authentication, ROLE_ANONYMOUS)) {
            return;
        }

        String sessionId = SessionContext.getSessionId();

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

        ResponseEntity<Void> response;
        try {
            response =
                restTemplate.exchange(gatewayOrigin + "/auth/v1/members/token/refresh", HttpMethod.GET, httpEntity,
                    Void.class);
        } catch (HttpClientErrorException e) {
            log.error(e.toString());
            redisTemplate.opsForHash().delete(sessionId, JwtInfo.JWT_REDIS_KEY);
            return;
        }

        if (this.isInvalid(response)) {
            return;
        }

        HttpHeaders responseHeaders = response.getHeaders();

        String jwt = Objects.requireNonNull(responseHeaders.get(AUTHORIZATION)).get(0);
        if (jwt.startsWith(JwtInfo.BEARER)) {
            jwt = jwt.substring(JwtInfo.BEARER_LENGTH);
        }
        String expireAt = Objects.requireNonNull(responseHeaders.get(JwtInfo.JWT_EXPIRE)).get(0);

        JwtInfo.saveJwt(redisTemplate, jwtInfo.getMemberInfo(), sessionId, jwt, expireAt);

        try {
            HttpServletResponse httpResponse = this.getResponse();

            Cookie cookie = new Cookie(JwtInfo.SESSION_ID, sessionId);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(WEEK_SECOND);
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), jwt,
                    authentication.getAuthorities()));
            log.info("Token Refresh");
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

    private HttpServletResponse getResponse() {
        ServletRequestAttributes servletContainer =
            Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return Objects.requireNonNull(servletContainer.getResponse());
    }

}
