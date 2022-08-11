package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.interceptor.CookieInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor())
                .excludePathPatterns("**/login/**");
    }

}
