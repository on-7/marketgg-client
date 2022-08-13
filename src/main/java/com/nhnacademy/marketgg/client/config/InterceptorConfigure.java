package com.nhnacademy.marketgg.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.client.interceptor.AuthenticationInterceptor;
import com.nhnacademy.marketgg.client.interceptor.CookieInterceptor;
import com.nhnacademy.marketgg.client.jwt.JwtInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Interceptor 추가를 위한 설정 클래스.
 *
 * @author 윤동열
 */
@Configuration
@RequiredArgsConstructor
public class InterceptorConfigure implements WebMvcConfigurer {

    private final RedisTemplate<String, JwtInfo> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor())
                .excludePathPatterns("/login", "/signup", "/plugins/**", "/css/**", "/images/**")
                .order(-1);

        registry.addInterceptor(new AuthenticationInterceptor(redisTemplate, objectMapper))
                .excludePathPatterns("/login", "/signup", "/plugins/**", "/css/**", "/images/**")
                .order(1);
    }

}
