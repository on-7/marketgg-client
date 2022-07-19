package com.nhnacademy.marketgg.client.aspect;

import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class JwtAspect {

    private final RedisTemplate<String, JwtInfo> redisTemplate;

    // @Before()

}
