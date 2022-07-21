package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.interceptor.JwtRefreshInterceptor;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InterceptorConfig {

    public InterceptorConfig(RestTemplate restTemplate,
                             RedisTemplate<String, JwtInfo> redisTemplate) {
        restTemplate.setInterceptors(List.of(new JwtRefreshInterceptor(redisTemplate)));
    }

}
