package com.nhnacademy.marketgg.client.aspect;

import static org.springframework.http.HttpMethod.GET;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.dto.MemberInfo;
import com.nhnacademy.marketgg.client.dto.response.common.CommonResult;
import com.nhnacademy.marketgg.client.dto.response.common.ErrorEntity;
import com.nhnacademy.marketgg.client.exception.ClientException;
import com.nhnacademy.marketgg.client.exception.ServerException;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * Controller 클래스에서 메소드 파라미터에 @Member 어노테이션 사용시 Member 정보 주입하는 AOP.
 *
 * @author 윤동열
 * @version 1.0.0
 */
@Slf4j
@Aspect
@Order
@Component
@RequiredArgsConstructor
public class MemberAspect {

    @Value("${gg.gateway-origin}")
    private String gatewayOrigin;

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final JwtAspect jwtAspect;

    /**
     * Member 정보를 주입합니다.
     *
     * @param pjp - 메서드 원본의 정보를 가지고있는 객체입니다.
     * @return 메서드 정보
     * @throws Throwable 메서드를 실행시킬 때 발생할 수 있는 예외입니다.
     */
    @Around("@within(controller) && execution(* *.*(.., com.nhnacademy.marketgg.client.dto.MemberInfo, ..))")
    public Object injectMember(ProceedingJoinPoint pjp, Controller controller) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return pjp.proceed();
        }

        String sessionId = (String) authentication.getPrincipal();

        JwtInfo jwtInfo =
            (JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY);

        if (Objects.isNull(jwtInfo)) {
            return pjp.proceed();
        }

        HttpEntity<Void> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<CommonResult<MemberInfo>> exchange =
            restTemplate.exchange(gatewayOrigin + "/shop/v1/members", GET, entity, new ParameterizedTypeReference<>() {
            });

        if (Objects.isNull(exchange.getBody())) {
            throw new ServerException();
        }

        log.info("response object: {}", mapper.writeValueAsString(exchange.getBody()));

        if (exchange.getStatusCode().is4xxClientError()) {
            ErrorEntity error = Objects.requireNonNull(exchange.getBody()).getError();
            throw new ClientException(error.getMessage());
        }

        MemberInfo memberInfo = Objects.requireNonNull(exchange.getBody()).getData();

        Object[] args = Arrays.stream(pjp.getArgs())
                              .map(arg -> {
                                  if (arg instanceof MemberInfo) {
                                      arg = memberInfo;
                                  }
                                  return arg;
                              }).toArray();

        return pjp.proceed(args);
    }

}
