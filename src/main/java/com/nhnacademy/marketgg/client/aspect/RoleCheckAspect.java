package com.nhnacademy.marketgg.client.aspect;

import static com.nhnacademy.marketgg.client.jwt.Role.ROLE_ADMIN;
import static com.nhnacademy.marketgg.client.jwt.Role.ROLE_USER;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.annotation.RoleCheck;
import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.jwt.Payload;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 사용자 권한 검증을 위한 AOP 입니다.
 *
 * @author 윤동열
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAspect {

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final ObjectMapper mapper;

    /**
     * JWT 를 BASE64 로 디코딩하여 간단하게 권한을 검증한다.
     *
     * @param roleCheck - 권한 검증 레벨
     * @throws JsonProcessingException  JSON 을 Java String 으로 변환 시 발생할 수 있다.
     * @throws UnAuthenticException     인증되지 않은 사용자일 때 발생한다.
     * @throws UnAuthorizationException 권한이 부족할 때 발생한다.
     */
    @Before(value = "@annotation(roleCheck) || @within(roleCheck)")
    public void checkRole(JoinPoint jp, RoleCheck roleCheck)
        throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        log.debug("Method: {}", jp.getSignature().getName());
        log.debug("Authorization: {}", roleCheck.accessLevel());

        String sessionId = SessionContext.get()
                                         .orElseThrow(UnAuthenticException::new);

        JwtInfo jwtInfo =
            Optional.ofNullable((JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY))
                    .orElseThrow(UnAuthenticException::new);

        String[] jwt = jwtInfo.getJwt().split("\\.");
        // JWT 는 header, payload, signature 로 이루어져 있다.
        String jwtPayload = jwt[1];

        byte[] decode = Base64.getDecoder().decode(jwtPayload);
        Payload payload = mapper.readValue(new String(decode, StandardCharsets.UTF_8), Payload.class);

        log.info("role = {}", payload.getAuthorities().toString());

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();

        RoleCheck check = method.getAnnotation(RoleCheck.class);
        log.info("accessLevel = {}", check.accessLevel());

        if (Objects.equals(check.accessLevel(), ROLE_USER)) {
            checkUser(payload);
        }

        if (Objects.equals(check.accessLevel(), ROLE_ADMIN)) {
            checkAdmin(payload);
        }

    }

    private void checkAdmin(Payload payload) throws UnAuthorizationException {
        if (!payload.getAuthorities().contains(ROLE_ADMIN.name())) {
            throw new UnAuthorizationException();
        }
    }

    private void checkUser(Payload payload) throws UnAuthorizationException {
        if (!(payload.getAuthorities().contains(ROLE_USER.name())
            || payload.getAuthorities().contains(ROLE_ADMIN.name()))) {
            throw new UnAuthorizationException();
        }
    }

}
