package com.nhnacademy.marketgg.client.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.response.AuthResponse;
import com.nhnacademy.marketgg.client.dto.response.MemberResponse;
import com.nhnacademy.marketgg.client.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.client.dto.response.common.SingleResponse;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Controller 클래스에서 메소드 파라미터에 @Member 어노테이션 사용시 Member 정보 주입하는 AOP.
 *
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order(10)
@Component
@RequiredArgsConstructor
public class MemberAspect {

    @Value("${marketgg.gateway-origin}")
    private String gatewayOrigin;

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    /**
     * Member 정보를 주입합니다.
     *
     * @param pjp - 메서드 원본의 정보를 가지고있는 객체입니다.
     * @return 메서드 정보
     * @throws Throwable 메서드를 실행시킬 때 발생할 수 있는 예외입니다.
     */
    @Around("execution(* com.nhnacademy.marketgg.client.web.*.*(.., @com.nhnacademy.marketgg.client.annotation.Member (*), ..))")
    public Object injectMember(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());

        HttpSession session = AspectUtils.getHttpRequest().getSession(false);
        if (Objects.isNull(session)) {
            return pjp.proceed();
        }

        String sessionId = (String) session.getAttribute(JwtInfo.SESSION_ID);
        if (Objects.isNull(sessionId)) {
            return pjp.proceed();
        }

        JwtInfo jwtInfo =
            (JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY);

        if (Objects.isNull(jwtInfo)) {
            return pjp.proceed();
        }

        SingleResponse<AuthResponse> authEntity = getEntity("/auth/info");
        SingleResponse<MemberResponse> memberEntity = getEntity("/shop/v1/members");


        MemberInfo memberInfo =
            new MemberInfo(authEntity.getData(), memberEntity.getData());
        Object[] args = Arrays.stream(pjp.getArgs())
                              .map(arg -> {
                                  if (arg instanceof MemberInfo) {
                                      arg = memberInfo;
                                  }
                                  return arg;
                              }).toArray();

        return pjp.proceed(args);
    }

    private <T> SingleResponse<T> getEntity(String path) throws JsonProcessingException {
        ResponseEntity<String> exchange =
            restTemplate.getForEntity(gatewayOrigin + path, String.class);

        log.info("response object: {}", mapper.readValue(exchange.getBody(), String.class));

        if (exchange.getStatusCode().is4xxClientError()) {
            ErrorEntity error =
                mapper.readValue(exchange.getBody(), ErrorEntity.class);

            throw new ClientException(error.getMessage());
        }

        return mapper.readValue(exchange.getBody(), new TypeReference<>() {
        });
    }

}
