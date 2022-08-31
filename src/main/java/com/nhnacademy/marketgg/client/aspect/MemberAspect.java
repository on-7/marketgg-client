package com.nhnacademy.marketgg.client.aspect;

import static com.nhnacademy.marketgg.client.jwt.Role.ROLE_ANONYMOUS;

import com.nhnacademy.marketgg.client.context.SessionContext;
import com.nhnacademy.marketgg.client.dto.common.MemberInfo;
import com.nhnacademy.marketgg.client.dto.common.CommonResult;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import com.nhnacademy.marketgg.client.repository.auth.AuthRepository;
import com.nhnacademy.marketgg.client.util.GgUtils;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

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

    private final AuthRepository authRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Member 정보를 주입합니다.
     *
     * @param pjp - 메서드 원본의 정보를 가지고있는 객체입니다.
     * @return 메서드 정보
     * @throws Throwable 메서드를 실행시킬 때 발생할 수 있는 예외입니다.
     */
    @Around("@within(controller) && execution(* *.*(.., com.nhnacademy.marketgg.client.dto.common.MemberInfo, ..))")
    public Object injectMember(ProceedingJoinPoint pjp, Controller controller) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (GgUtils.hasRole(authentication, ROLE_ANONYMOUS)) {
            return pjp.proceed();
        }

        String sessionId = SessionContext.getSessionId();

        JwtInfo jwtInfo =
            (JwtInfo) redisTemplate.opsForHash().get(sessionId, JwtInfo.JWT_REDIS_KEY);

        if (Objects.isNull(jwtInfo)) {
            return pjp.proceed();
        }

        MemberInfo memberInfo;

        if (jwtInfo.getMemberInfo().isNull()) {
            ResponseEntity<CommonResult<MemberInfo>> exchange = authRepository.getMemberInfo();
            memberInfo = Objects.requireNonNull(exchange.getBody()).getData();
        } else {
            memberInfo = jwtInfo.getMemberInfo();
        }

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
